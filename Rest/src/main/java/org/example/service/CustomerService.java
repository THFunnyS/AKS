package org.example.service;

import org.example.dto.CustomerDto;
import org.example.model.Customer;
import org.example.repository.CustomerRepo;
import org.example.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepo customerRepo;
    private final OrderRepo orderRepo;

    public CustomerService(CustomerRepo customerRepo, OrderRepo orderRepo) {
        this.customerRepo = customerRepo;
        this.orderRepo = orderRepo;
    }

    // READ: Получить всех покупателей
    @Transactional(readOnly = true)
    public List<CustomerDto> getAllCustomers() {
        return customerRepo.findAll().stream().map(this::convertToDto).toList();
    }

    // READ: Получить одного покупателя
    @Transactional(readOnly = true)
    public CustomerDto getCustomerById(Long id) {
        return convertToDto(findCustomer(id));
    }

    // Получить данные для формы редактирования
    @Transactional(readOnly = true)
    public CustomerDto getCustomerFormById(Long id) {
        Customer customer = findCustomer(id);

        CustomerDto dto = new CustomerDto();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());

        return dto;
    }

    // CREATE: создать покупателя
    @Transactional
    public CustomerDto createCustomer(CustomerDto dto) {
        validateCustomer(dto, null);

        Customer customer = new Customer();
        copyToEntity(dto, customer);

        return convertToDto(customerRepo.save(customer));
    }

    // UPDATE: обновить покупателя
    @Transactional
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer customer = findCustomer(id);
        validateCustomer(dto, id);
        copyToEntity(dto, customer);

        return convertToDto(customerRepo.save(customer));
    }

    // DELETE: удалить покупателя
    @Transactional
    public void deleteCustomer(Long id) {
        Customer customer = findCustomer(id);

        if (!customer.getOrders().isEmpty()) {
            throw new IllegalStateException("Нельзя удалить покупателя, у которого есть заказы");
        }

        customerRepo.delete(customer);
    }

    private Customer findCustomer(Long id) {
        return customerRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Покупатель с ID " + id + " не найден"));
    }

    private void validateCustomer(CustomerDto dto, Long id) {
        if (dto.getName() == null || dto.getName().isBlank()) {
            throw new IllegalArgumentException("Имя обязательно");
        }

        String email = dto.getEmail();

        if (email != null && !email.isBlank()) {
            boolean emailExists = id == null
                    ? customerRepo.existsByEmail(email)
                    : customerRepo.existsByEmailAndIdNot(email, id);

            if (emailExists) {
                throw new IllegalArgumentException(
                        "Покупатель с таким email уже существует"
                );
            }
        }
    }

    private void copyToEntity(CustomerDto dto, Customer customer) {
        customer.setName(dto.getName().trim());
        customer.setEmail(dto.getEmail() == null || dto.getEmail().isBlank() ? null : dto.getEmail().trim());
        customer.setPhone(dto.getPhone());
    }

    private CustomerDto convertToDto(Customer customer) {
        return new CustomerDto(customer.getId(), customer.getName(), customer.getEmail(), customer.getPhone());
    }
}
