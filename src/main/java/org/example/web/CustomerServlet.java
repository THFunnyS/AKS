package org.example.web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Customer;
import org.example.service.CustomerService;

import java.io.IOException;
import java.util.List;

@WebServlet("/customers")
public class CustomerServlet extends HttpServlet {
    @EJB
    private CustomerService customerService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        String action = request.getParameter("action");
        
        if (action == null || action.equals("list")) {
            showCustomerList(request, response);
        } else if (action.equals("new")) {
            showCreateForm(request, response);
        } else if (action.equals("edit")) {
            showEditForm(request, response);
        } else if (action.equals("delete")) {
            deleteCustomer(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idParameter = request.getParameter("id");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("error", "Имя покупателя обязательно");
            request.getRequestDispatcher("/customer-form.jsp").forward(request, response);
            return;
        }

        if (email != null && email.trim().isEmpty()) {
            email = null;
        }

        if (phone != null && phone.trim().isEmpty()) {
            phone = null;
        }

        if (idParameter == null || idParameter.trim().isEmpty()) {
            // CREATE
            Customer customer = new Customer();

            customer.setName(name.trim());
            customer.setEmail(email);
            customer.setPhone(phone);

            customerService.create(customer);

        } else {
            // UPDATE
            Long id = Long.parseLong(idParameter);

            Customer customer = customerService.findById(id);

            if (customer == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Покупатель не найден");
                return;
            }

            customer.setName(name.trim());
            customer.setEmail(email);
            customer.setPhone(phone);

            customerService.update(customer);
        }
        response.sendRedirect(request.getContextPath() + "/customers");
    }

    private void showCustomerList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Customer> customerList = customerService.findAll();
        request.setAttribute("customers", customerList);
        request.getRequestDispatcher("/customers.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("customer", new Customer());
        request.setAttribute("formTitle", "Добавление покупателя");
        request.getRequestDispatcher("/customer-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        Customer customer = customerService.findById(id);

        if (customer == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Покупатель не найден");
            return;
        }

        request.setAttribute("customer", customer);
        request.setAttribute("formTitle", "Редактирование покупателя");
        request.getRequestDispatcher("/customer-form.jsp").forward(request, response);
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        try {
            customerService.delete(id);
            response.sendRedirect(request.getContextPath() + "/customers");
        } catch (IllegalStateException e) {
            response.setContentType("text/html;charset=UTF-8");

            response.getWriter().println("<h2>Ошибка удаления</h2>");

            response.getWriter().println("<p>" + e.getMessage() + "</p>");

            response.getWriter().println("<a href='" + request.getContextPath() + "/customers'>Вернуться к списку</a>");
        }
    }
}
