package com.budcoded.orderservice.Service;

import com.budcoded.orderservice.DTO.InventoryResponse;
import com.budcoded.orderservice.DTO.OrderLineItemDTO;
import com.budcoded.orderservice.DTO.OrderRequest;
import com.budcoded.orderservice.Model.Order;
import com.budcoded.orderservice.Model.OrderLineItem;
import com.budcoded.orderservice.Repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClientBuilder;

    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItems = orderRequest.getOrderLineItemDTOS()
                .stream()
                .map(this::mapToDTO)
                .toList();
        order.setOrderLineItemList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemList().stream().map(OrderLineItem::getSkuCode).toList();
        // Calling Inventory Service...
        // Placing order only if the product is in stock...
        InventoryResponse[] result = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductsInStock = Arrays.stream(result).allMatch(InventoryResponse::isInStock);

        if (allProductsInStock) {
            orderRepository.save(order);
            return "Order Placed Successfully.";
        } else
            throw new IllegalArgumentException("Product is not in stock. Please try again later.");
    }

    private OrderLineItem mapToDTO(OrderLineItemDTO orderLineItemDTO) {
        OrderLineItem orderLineItem = new OrderLineItem();
        orderLineItem.setPrice((orderLineItemDTO.getPrice()));
        orderLineItem.setQuantity((orderLineItemDTO.getQuantity()));
        orderLineItem.setSkuCode((orderLineItemDTO.getSkuCode()));
        return orderLineItem;
    }
}