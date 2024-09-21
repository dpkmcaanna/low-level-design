package com.lld.ofds.app.entity;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
	String id;
	Customer customer;
	String restaurantId;
	DeliveryAgent deliveryAgent;
	List<OrderItem> items;
	OrderStatus status;
	LocalDateTime createAt;
	LocalDateTime lastUpdatedAt;
	double totalAmount;
}
