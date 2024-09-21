package com.lld.ofds.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import com.lld.ofds.app.entity.Customer;
import com.lld.ofds.app.entity.DeliveryAgent;
import com.lld.ofds.app.entity.MenuItem;
import com.lld.ofds.app.entity.Order;
import com.lld.ofds.app.entity.OrderItem;
import com.lld.ofds.app.entity.OrderStatus;
import com.lld.ofds.app.entity.Restaurant;

public class FoodDeliveryService {

	Map<String, Order> orders;
	Map<String, Customer> customers;
	Map<String, DeliveryAgent> deliveryAgents;
	Map<String, Restaurant> restaurants;

	OrderHelper orderHelper;

	static FoodDeliveryService instance;

	public FoodDeliveryService() {
		orders = new ConcurrentHashMap<String, Order>();
		customers = new ConcurrentHashMap<String, Customer>();
		deliveryAgents = new ConcurrentHashMap<String, DeliveryAgent>();
		restaurants = new ConcurrentHashMap<String, Restaurant>();
		orderHelper = new OrderHelper();
	}

	public static FoodDeliveryService getInstance() {
		synchronized (FoodDeliveryService.class) {
			if (instance == null) {
				instance = new FoodDeliveryService();
			}
		}
		return instance;
	}

	public void registerCustomer(Customer customer) {
		customers.put(customer.getId(), customer);
	}

	public void registerRestaurant(Restaurant restaurant) {
		restaurants.put(restaurant.getId(), restaurant);
	}

	public void registerDeliveryAgent(DeliveryAgent agent) {
		deliveryAgents.put(agent.getId(), agent);
	}

	public List<Restaurant> getAvailableRestaurants() {
		return new ArrayList<>(restaurants.values());
	}
	
	public List<Customer> getCustomers() {
		return new ArrayList<>(customers.values());
	}

	public List<MenuItem> getRestaurantMenu(String restaurantId) {
		Restaurant restaurant = restaurants.get(restaurantId);
		if (restaurant != null) {
			return restaurant.getMenuItems();
		}
		return new ArrayList<>();
	}

	public Order placeOrder(String customerId, String restaurantId, List<OrderItem> items) {
		Customer customer = customers.get(customerId);
		Restaurant restaurant = restaurants.get(restaurantId);
		if (customer != null && restaurant != null) {
			Order order = Order.builder().id(generateOrderId()).customer(customer).restaurantId(restaurantId).build();
			for (OrderItem item : items) {
				orderHelper.addItem(order, item);
			}
			orders.put(order.getId(), order);
			notifyRestaurant(order);
			System.out.println("Order placed: " + order.getId());
			return order;
		}
		return null;
	}

	public void updateOrderStatus(String orderId, OrderStatus status) {
		Order order = orders.get(orderId);
		if (order != null) {
			order.setStatus(status);
			notifyCustomer(order);
			if (status == OrderStatus.CONFIRMED) {
				assignDeliveryAgent(order);
			}
		}
	}

	public void cancelOrder(String orderId) {
		Order order = orders.get(orderId);
		if (order != null && order.getStatus() == OrderStatus.PENDING) {
			order.setStatus(OrderStatus.CANCELLED);
			notifyCustomer(order);
			notifyRestaurant(order);
			System.out.println("Order cancelled: " + order.getId());
		}
	}

	private String generateOrderId() {
		return "ORD" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
	}

	private void assignDeliveryAgent(Order order) {
		for (DeliveryAgent agent : deliveryAgents.values()) {
			if (agent.isAvailable()) {
				agent.setAvailable(false);
				orderHelper.assignDeliveryAgent(order, agent);
				notifyDeliveryAgent(order);
				break;
			}
		}
	}

	private void notifyCustomer(Order order) {
		System.out.println("Customer notified");
	}

	private void notifyRestaurant(Order order) {
		System.out.println("Restaurant notified");
	}
	
	private void notifyDeliveryAgent(Order order) {
		System.out.println("DeliveryAgent notified");
	}
}
