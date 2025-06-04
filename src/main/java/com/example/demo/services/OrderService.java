package com.example.demo.services;

import org.springframework.stereotype.Service;
import com.example.demo.dtos.AdditionDTO;
import com.example.demo.dtos.CreateOrderDTO;
import com.example.demo.dtos.OrderDTO;
import com.example.demo.dtos.OrderItemDTO;
import com.example.demo.entities.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderStatusRepository orderStatusRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AdditionRepository additionRepository;

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    public OrderDTO createOrder(CreateOrderDTO dto) {
        OrderStatusEntity status = orderStatusRepository.findById(1)
                .orElseThrow(() -> new RuntimeException("Stato ordine non trovato"));

        OrderEntity order = new OrderEntity();
        order.setStatus(status);
        order.setCreatedAt(LocalDateTime.now());
        order = orderRepository.save(order);

        List<OrderItemDTO> orderItemDTOs = new ArrayList<>();

        for (OrderItemDTO itemDTO : dto.getItems()) {
            PizzaEntity pizza = pizzaRepository.findById(itemDTO.getPizzaId())
                    .orElseThrow(() -> new RuntimeException("Pizza non trovata con ID: " + itemDTO.getPizzaId()));

            OrderItemEntity item = new OrderItemEntity();
            item.setOrder(order);
            item.setPizza(pizza);
            item = orderItemRepository.save(item);

            List<AdditionDTO> additionDTOs = new ArrayList<>();

            for (AdditionDTO additionDTO : itemDTO.getAdditions()) {
                IngredientEntity ingredient = ingredientRepository.findById(additionDTO.getIngredientId())
                        .orElseThrow(() -> new RuntimeException("Ingrediente non trovato con ID: " + additionDTO.getIngredientId()));

                AdditionEntity addition = new AdditionEntity();
                addition.setOrderItem(item);
                addition.setIngredient(ingredient);
                addition.setIsAddition(additionDTO.getIsAddition());
                additionRepository.save(addition);

                additionDTOs.add(new AdditionDTO(
                        addition.getId(),
                        order.getId(),
                        item.getId(),
                        addition.getIsAddition()
                ));
            }

            orderItemDTOs.add(new OrderItemDTO(
                    item.getId(),
                    additionDTOs
            ));
        }

        return new OrderDTO(
                order.getId(),
                order.getStatus().getName(),
                order.getCreatedAt(),
                orderItemDTOs);
    }

    public List<OrderDTO> getOrdersByStatusId(Integer statusId) {
        List<OrderEntity> orders;

        if (statusId != null) {
            OrderStatusEntity status = orderStatusRepository.findById(statusId)
                    .orElseThrow(() -> new RuntimeException("Stato ordine non trovato con ID: " + statusId));
            orders = orderRepository.findByStatus(status);
        } else {
            orders = orderRepository.findAll();
        }

        List<OrderDTO> result = new ArrayList<>();

        for (OrderEntity order : orders) {
            List<OrderItemDTO> itemDTOs = new ArrayList<>();

            for (OrderItemEntity item : order.getItems()) {
                List<AdditionDTO> additionDTOs = new ArrayList<>();

                for (AdditionEntity addition : item.getAdditions()) {
                    AdditionDTO additionDTO = new AdditionDTO(
                            addition.getId(),
                            item.getId(),
                            addition.getIngredient().getId(),
                            addition.getIsAddition()
                    );
                    additionDTOs.add(additionDTO);
                }

                OrderItemDTO itemDTO = new OrderItemDTO(
                        item.getPizza().getId(),
                        additionDTOs
                );
                itemDTOs.add(itemDTO);
            }

            OrderDTO orderDTO = new OrderDTO(
                    order.getId(),
                    order.getStatus().getName(),
                    order.getCreatedAt(),
                    itemDTOs
            );
            result.add(orderDTO);
        }

        return result;
    }

    public OrderDTO updateOrderStatus(Integer orderId, Integer statusId) {
        OrderEntity order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Ordine non trovato"));

        OrderStatusEntity newStatus = orderStatusRepository.findById(statusId)
                .orElseThrow(() -> new RuntimeException("Stato ordine non trovato"));

        order.setStatus(newStatus);
        order = orderRepository.save(order);

        List<OrderItemDTO> itemDTOs = order.getItems().stream().map(item -> {
            List<AdditionDTO> additionDTOs = item.getAdditions().stream().map(add ->
                    new AdditionDTO(add.getId(), item.getId(), add.getIngredient().getId(), add.getIsAddition())
            ).toList();

            return new OrderItemDTO(item.getPizza().getId(), additionDTOs);
        }).toList();

        return new OrderDTO(order.getId(), order.getStatus().getName(), order.getCreatedAt(), itemDTOs);
    }



}
