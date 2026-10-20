package org.example.service;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.model.Customer;

import java.util.List;

@Stateless
public class CustomerService {
    @PersistenceContext(unitName = "ShopPU")
    private EntityManager entityManager;

    public List<Customer> findAll() {
        return entityManager.createQuery("SELECT c FROM Customer c ORDER BY c.id", Customer.class)
                .getResultList();
    }

    public Customer findById(Long id) {
        if (id == null) return null;
        return entityManager.find(Customer.class, id);
    }

    public void create(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Покупатель не может быть null");
        }
        entityManager.persist(customer);
    }

    public void update(Customer customer) {
        if (customer == null || customer.getId() == null) {
            throw new IllegalArgumentException("Покупатель не может быть null");
        }
        entityManager.merge(customer);
    }

    public void delete(Long id) {
        Customer customer = entityManager.find(Customer.class, id);

        if (customer != null) {
            Long orders = entityManager.createQuery("SELECT COUNT(o) FROM CustomerOrder o WHERE o.customer.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();
            if (orders > 0) {
                throw new IllegalStateException("Нельзя удалить покупателя с заказами (количество заказов: " + orders + ")");
            }
            entityManager.remove(customer);
        }
    }
}
