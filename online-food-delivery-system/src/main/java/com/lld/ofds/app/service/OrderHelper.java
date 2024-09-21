package com.lld.ofds.app.service;

import java.time.LocalDateTime;
import java.util.LinkedList;

import com.lld.ofds.app.entity.DeliveryAgent;
import com.lld.ofds.app.entity.Order;
import com.lld.ofds.app.entity.OrderItem;


public class OrderHelper {
	
	public void removeItem(Order order, OrderItem item) {
		if(order.getItems().remove(item)) {
			order.setLastUpdatedAt(LocalDateTime.now());
		}
	}
	
	public void assignDeliveryAgent(Order order, DeliveryAgent agent) {
		order.setDeliveryAgent(agent);
	}
	
	public void addItem(Order order, OrderItem item) {
		if(order.getItems() == null) {
			order.setItems(new LinkedList<OrderItem>());
		}
		order.getItems().add(item);
	}

}
