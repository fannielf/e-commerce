package com.buy01.order.service;

import com.buy01.order.dto.OrderCreateDTO;
import com.buy01.order.dto.OrderResponseDTO;
import com.buy01.order.dto.OrderUpdateRequest;
import com.buy01.order.exception.ForbiddenException;
import com.buy01.order.exception.NotFoundException;
import com.buy01.order.model.Order;
import com.buy01.order.model.OrderItem;
import com.buy01.order.repository.OrderRepository;
import com.buy01.order.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    static class TestOrder extends Order {
        TestOrder(String id, String userId, List<OrderItem> items, double totalPrice, String status) {
            super(id, userId, items, totalPrice, status);
        }
    }

    @BeforeEach
    void setUp() throws IOException {
        orderService = new OrderService(orderRepository);
    }

    @Test
    void createOrder_validRequest_returnsOrderResponseDTO() throws IOException {

        OrderCreateDTO request = mock(OrderCreateDTO.class);
//        when(request.getName()).thenReturn("Valid Name");
//        when(request.getDescription()).thenReturn("A valid description");
//        when(request.getPrice()).thenReturn(9.99);
//        when(request.getQuantity()).thenReturn(5);
//        when(request.getUserId()).thenReturn("current-user-1"); // Use the actual user ID
//        when(request.getImagesList()).thenReturn(null);

        // Mock auth check

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order o = invocation.getArgument(0);
//            return new TestOrder();
            return null;
        });

        //OrderResponseDTO resp = orderService.createOrder(request, "SELLER", "current-user-1");

//        assertNotNull(resp);
//        assertEquals("prod-1", resp.getProductId());
//        assertEquals("Valid Name", resp.getName());
//        assertEquals("current-user-1", resp.getOwnerId());
    }

}
