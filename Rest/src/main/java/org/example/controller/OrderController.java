package org.example.controller;

import org.example.dto.OrderDto;
import org.example.service.CustomerService;
import org.example.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final CustomerService customerService;

    public OrderController(OrderService orderService, CustomerService customerService) {
        this.orderService = orderService;
        this.customerService = customerService;
    }

    // READ: Список заказов
    @GetMapping
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.getAllOrders());
        return "orders";
    }

    // Показать форму создания
    @GetMapping("/new")
    public String newOrderForm(Model model) {
        model.addAttribute("order", new OrderDto());
        addCustomerToModel(model);
        return "order-form";
    }

    // Показать форму редактирования
    @GetMapping("/{id}/edit")
    public String editOrderForm(@PathVariable Long id, Model model) {
        model.addAttribute("order", orderService.getOrderFormById(id));
        addCustomerToModel(model);
        return "order-form";
    }

    // CREATE: Сохранить новый заказ
    @PostMapping("/create")
    public String createOrder(@ModelAttribute("order") OrderDto dto, Model model, RedirectAttributes attributes) {
        try {
            orderService.createOrder(dto);
            attributes.addFlashAttribute("successMessage", "Заказ добавлен");
            return "redirect:/orders";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            addCustomerToModel(model);
            return "order-form";
        }
    }

    // UPDATE: Сохранить изменения заказа
    @PostMapping("{id}/update")
    public String updateOrder(@PathVariable Long id, @ModelAttribute("order") OrderDto dto, Model model, RedirectAttributes attributes) {
        try {
            orderService.updateOrder(id, dto);
            attributes.addFlashAttribute("successMessage", "Заказ обновлён");
            return "redirect:/orders";
        }
        catch (IllegalArgumentException e) {
            dto.setId(id);
            model.addAttribute("errorMessage", e.getMessage());
            addCustomerToModel(model);
            return "order-form";
        }
    }

    // DELETE: Удалить заказ
    @PostMapping("/{id}/delete")
    public String deleteOrder(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            orderService.deleteOrder(id);
            redirectAttributes.addFlashAttribute("successMessage", "Заказ удалён");
        }
        catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/orders";
    }

    private void addCustomerToModel(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
    }
}