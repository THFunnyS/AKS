package org.example.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDto {
    private Long id;
    private LocalDate orderDate;
    private BigDecimal totalSum;
    private String status;
    private Long customerId;
    private String customerName;

    public OrderDto() {}

    public OrderDto(Long id, LocalDate orderDate, BigDecimal totalSum, String status, Long customerId, String customerName) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalSum = totalSum;
        this.status = status;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getOrderDate() {
        return this.orderDate;
    }

    public BigDecimal getTotalSum() {
        return totalSum;
    }

    public String getStatus() {
        return status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public void setTotalSum(BigDecimal totalSum) {
        this.totalSum = totalSum;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}