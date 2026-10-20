package Spring.service;

import Spring.dto.OrderDto;
import Spring.model.Customer;
import Spring.model.CustomerOrder;
import Spring.repository.CustomerRepo;
import Spring.repository.OrderRepo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepo orderRepo;
    private final CustomerRepo customerRepo;

    public OrderService(OrderRepo orderRepo, CustomerRepo customerRepo) {
        this.orderRepo = orderRepo;
        this.customerRepo = customerRepo;
    }

    // READ: Получить все заказы
    @Transactional(readOnly = true)
    public List<OrderDto> getAllOrders() {
        return orderRepo.findAll().stream().map(this::convertToDto).toList();
    }

    // READ: Получить один заказ
    @Transactional(readOnly = true)
    public OrderDto getOrderById(Long id) {
        return convertToDto(findOrder(id));
    }

    // Получить все данные для формы редактирования
    @Transactional(readOnly = true)
    public OrderDto getOrderFormById(Long id) {
        CustomerOrder order = findOrder(id);

        OrderDto dto = new OrderDto();
        dto.setId(order.getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());
        dto.setTotalSum(order.getTotalSum());
        dto.setCustomerId(order.getCustomer() == null ? null : order.getCustomer().getId());
        return dto;
    }

    // CREATE: Создать заказ
    @Transactional
    public OrderDto createOrder(OrderDto dto) {
        validateOrder(dto);

        CustomerOrder order = new CustomerOrder();
        copyToEntity(dto, order);

        return convertToDto(orderRepo.save(order));
    }

    // UPDATE: Обновить заказ
    @Transactional
    public OrderDto updateOrder(Long id, OrderDto dto){
        CustomerOrder order = findOrder(id);
        validateOrder(dto);
        copyToEntity(dto, order);
        return convertToDto(orderRepo.save(order));
    }

    // DELETE: Удалить заказ
    @Transactional
    public void deleteOrder(Long id) {
        orderRepo.delete(findOrder(id));
    }

    private CustomerOrder findOrder(Long id) {
        return orderRepo.findById(id).
                orElseThrow(() -> new IllegalArgumentException(
                        "Заказ с ID " + id + " не найден"
                ));
    }

    private void validateOrder(OrderDto dto) {
        if (dto.getTotalSum() == null || dto.getTotalSum().signum() <= 0) {
            throw new IllegalArgumentException("Сумма заказа должна быть больше нуля");
        }

        if (dto.getCustomerId() != null && !customerRepo.existsById(dto.getCustomerId())) {
            throw new IllegalArgumentException("Выбранный покупатель не найден");
        }
    }

    private void copyToEntity(OrderDto dto, CustomerOrder order) {
        order.setOrderDate(dto.getOrderDate());
        order.setTotalSum(dto.getTotalSum());
        order.setStatus(dto.getStatus() == null || dto.getStatus().isBlank() ? "not ready" : dto.getStatus().trim());

        if (dto.getCustomerId() == null) {
            order.setCustomer(null);
        } else {
            Customer customer = customerRepo.findById(dto.getCustomerId())
                    .orElseThrow(() -> new IllegalArgumentException("Покупатель не найден"));

            order.setCustomer(customer);
        }
    }

    private OrderDto convertToDto(CustomerOrder order) {
        Long customerId = order.getCustomer() != null ? order.getCustomer().getId() : null;

        String customerName = order.getCustomer() != null ? order.getCustomer().getName() : null;

        return new OrderDto(order.getId(), order.getOrderDate(), order.getTotalSum(), order.getStatus(), customerId, customerName);
    }
}