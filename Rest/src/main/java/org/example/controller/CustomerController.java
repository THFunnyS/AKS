package org.example.controller;

import org.example.dto.CustomerDto;
import org.example.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // READ: Список покупателей
    @GetMapping
    public String listCustomers(Model model){
        model.addAttribute("customers", customerService.getAllCustomers());
        return "customers";
    }

    // Показать форму создания
    @GetMapping("/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", new CustomerDto());
        return "customer-form";
    }

    // Показать форму редактирования
    @GetMapping("/{id}/edit")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerFormById(id));
        return "customer-form";
    }

    // CREATE: Сохранить нового покупателя
    @PostMapping("/create")
    public String createCustomer(@ModelAttribute("customer") CustomerDto dto, Model model, RedirectAttributes attributes) {
        try {
            customerService.createCustomer(dto);
            attributes.addFlashAttribute("successMessage", "Покупатель добавлен");
            return "redirect:/customers";
        }
        catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer-form";
        }
    }

    // UPDATE: Сохранить изменения покупателя
    @PostMapping("/{id}/update")
    public String updateCustomer(@PathVariable Long id, @ModelAttribute("customer") CustomerDto dto, Model model, RedirectAttributes attributes) {
        try {
            customerService.updateCustomer(id, dto);
            attributes.addFlashAttribute("successMessage", "Данные покупателя обновлены");
            return "redirect:/customers";
        }
        catch (IllegalArgumentException e) {
            dto.setId(id);
            model.addAttribute("errorMessage", e.getMessage());
            return "customer-form";
        }
    }

    // DELETE: Удалить покупателя
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Покупатель удалён");
        }
        catch (IllegalArgumentException | IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/customers";
    }
}
