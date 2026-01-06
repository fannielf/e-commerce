package com.buy01.order.service;

import com.buy01.order.dto.ItemDTO;
import com.buy01.order.dto.OrderResponseDTO;
import com.buy01.order.exception.ForbiddenException;
import com.buy01.order.exception.NotFoundException;
import com.buy01.order.model.Order;
import com.buy01.order.model.OrderItem;
import com.buy01.order.model.Role;
import com.buy01.order.repository.OrderRepository;
import com.buy01.order.security.AuthDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// Service layer is responsible for business logic, validation, verification and data manipulation.
// It chooses how to handle data and interacts with the repository layer.
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<OrderResponseDTO> getClientOrders(AuthDetails currentUser) {
        List<Order> orders = orderRepository.findOrdersByUserId(currentUser.getCurrentUserId());

        return orders.stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<OrderResponseDTO> getSellerOrders(AuthDetails currentUser) {
        List<Order> orders = orderRepository.findByItemsSellerId(currentUser.getCurrentUserId());

        return orders.stream()
                .map(order -> filterOrderForSeller(order, currentUser)) // filter items for the current seller
                .map(this::mapToDTO)
                .toList();

}

    public OrderResponseDTO getOrderById(String orderId, AuthDetails currentUser) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found with orderId: " + orderId));

       return (order.getUserId().equals(currentUser.getCurrentUserId())
                    || currentUser.getRole().equals(Role.ADMIN))
                    ? mapToDTO(order) // normal mapping for client and admin
                    : mapToDTO(filterOrderForSeller(order, currentUser)); // seller gets only filtered items
    }

    // Helper method to convert OrderItem to ItemDTO
    private ItemDTO toItemDTO(OrderItem item) {
        return new ItemDTO(
                item.getProductId(),
                item.getProductName(),
                item.getQuantity(),
                item.getPrice(),
                item.getPrice()*item.getQuantity(),
                item.getSellerId()
        );
    }

    // Helper method to map Order to OrderResponseDTO
    private OrderResponseDTO mapToDTO(Order order) {
        return new OrderResponseDTO(
                order.getId(),
                order.getItems().stream()
                        .map(this::toItemDTO)
                        .toList(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getShippingAddress(),
                order.getCreatedAt()
        );
    }

    // Helper method to filter order items for a specific seller
    private Order filterOrderForSeller(Order order, AuthDetails currentUser) {
        if (!currentUser.getRole().equals(Role.SELLER)) {
            throw new ForbiddenException("Access denied to order with orderId: " + order.getId() + " for userId: " + currentUser.getCurrentUserId());
        }
        List<OrderItem> filteredItems = order.getItems().stream()
                .filter(item -> item.getSellerId().equals(currentUser.getCurrentUserId()))
                .toList();
        if (filteredItems.isEmpty()) {
            throw new ForbiddenException("Access denied to order with orderId: " + order.getId() + " for userId: " + currentUser.getCurrentUserId());
        }
        order.setItems(filteredItems);
        double sellerTotal = filteredItems.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        order.setTotalPrice(sellerTotal);
        return order;
    }


}
