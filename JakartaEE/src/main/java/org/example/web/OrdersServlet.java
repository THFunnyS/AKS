package org.example.web;

import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.model.Customer;
import org.example.model.CustomerOrder;
import org.example.service.CustomerService;
import org.example.service.OrderService;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    @EJB
    private OrderService orderService;
    @EJB
    private CustomerService customerService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            showOrderList(request, response);
        } else if (action.equals("new")) {
            showCreateForm(request, response);
        } else if (action.equals("edit")) {
            showEditForm(request, response);
        } else if (action.equals("delete")) {
            deleteOrder(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неизвестное действие");
        }
    }

    @Override
    protected void doPost (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String idParameter = request.getParameter("id");

        String orderDateParameter = request.getParameter("orderDate");
        String totalSumParameter = request.getParameter("totalSum");
        String status = request.getParameter("status");
        String customerIdParameter = request.getParameter("customerId");

        try {
            LocalDate orderDate = LocalDate.parse(orderDateParameter);
            BigDecimal totalSum = new BigDecimal(totalSumParameter);
            Long customerId = Long.parseLong(customerIdParameter);
            Customer customer = customerService.findById(customerId);

            if (customer == null) {
                throw new IllegalArgumentException("Покупатель не найден");
            }

            if (idParameter == null || idParameter.trim().isEmpty()) {
                // CREATE
                CustomerOrder order = new CustomerOrder();

                order.setOrderDate(orderDate);
                order.setTotalSum(totalSum);
                order.setStatus(status);
                order.setCustomer(customer);

                orderService.create(order);

            } else {
                // UPDATE
                Long id = Long.parseLong(idParameter);
                CustomerOrder order = orderService.findById(id);

                if (order == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Заказ не найден");
                    return;
                }

                order.setOrderDate(orderDate);
                order.setTotalSum(totalSum);
                order.setStatus(status);
                order.setCustomer(customer);

                orderService.update(order);
            }

            response.sendRedirect(request.getContextPath() + "/orders");

        } catch (Exception e) {
            request.setAttribute("error", "Ошибка сохранения заказа: " + e.getMessage());
            request.setAttribute("customers", customerService.findAll());
            request.getRequestDispatcher("/order-form.jsp").forward(request, response);
        }
    }

    private void showOrderList(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<CustomerOrder> orders = orderService.findAll();
        request.setAttribute("orders", orders);
        request.getRequestDispatcher("/orders.jsp").forward(request, response);
    }

    private void showCreateForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("order", new CustomerOrder());
        request.setAttribute("customers", customerService.findAll());
        request.setAttribute("formTitle", "Добавление заказа");
        request.getRequestDispatcher("/order-form.jsp").forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = Long.parseLong(request.getParameter("id"));

        CustomerOrder order = orderService.findById(id);

        if (order == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Заказ не найден");
            return;
        }

        request.setAttribute("order", order);
        request.setAttribute("customers", customerService.findAll());
        request.setAttribute("formTitle", "Редактирование заказа");
        request.getRequestDispatcher("/order-form.jsp").forward(request, response);
    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        Long id = Long.parseLong(request.getParameter("id"));
        orderService.delete(id);
        response.sendRedirect(request.getContextPath() + "/orders");
    }
}
