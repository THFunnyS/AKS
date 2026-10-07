package org.example.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.model.CustomerOrder;

import java.util.List;

@Stateless
public class OrderService {
    @PersistenceContext(unitName = "ShopPU")
    private EntityManager entityManager;

    public List<CustomerOrder> findAll() {
        return entityManager.createQuery("SELECT o FROM CustomerOrder o ORDER BY o.id", CustomerOrder.class)
                .getResultList();
    }

    public CustomerOrder findById(Long id) {
        if (id == null) return null;
        return entityManager.find(CustomerOrder.class, id);
    }

    public void create(CustomerOrder order) {
        if (order == null) {
            throw new IllegalArgumentException("Заказ не можеть быть null");
        }
        entityManager.persist(order);
    }

    public void update(CustomerOrder order) {
        if (order == null || order.getId() == null) {
            throw new IllegalArgumentException("Заказ не можеть быть null");
        }
        entityManager.merge(order);
    }

    public void delete(Long id) {
        if (id == null) return;
        CustomerOrder order = entityManager.find(CustomerOrder.class, id);

        if (order != null) {
            entityManager.remove(order);
        }
    }
}
