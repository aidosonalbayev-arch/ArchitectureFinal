package com.shop.service;

import com.shop.dto.OrderRequest;
import com.shop.exception.ResourceNotFoundException;
import com.shop.model.*;
import com.shop.repository.OrderRepository;
import com.shop.repository.ProductRepository;
import com.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public Order create(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.getUserId()));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PENDING);

        BigDecimal total = BigDecimal.ZERO;

        for (OrderRequest.OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product not found: " + itemReq.getProductId()));

            if (product.getStock() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Not enough stock for product: " + product.getName());
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemReq.getQuantity());
            item.setUnitPrice(product.getPrice());

            order.getItems().add(item);
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity())));

            // Decrease stock
            product.setStock(product.getStock() - itemReq.getQuantity());
            productRepository.save(product);
        }

        order.setTotalPrice(total);
        return orderRepository.save(order);
    }

    @Transactional(readOnly = true)
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Order findById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Order> findByUser(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order updateStatus(Long id, Order.Status status) {
        Order order = findById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public void cancel(Long id) {
        Order order = findById(id);
        if (order.getStatus() == Order.Status.DELIVERED) {
            throw new IllegalStateException("Cannot cancel a delivered order");
        }
        order.setStatus(Order.Status.CANCELLED);
        // Restore stock
        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setStock(product.getStock() + item.getQuantity());
            productRepository.save(product);
        }
        orderRepository.save(order);
    }
}
