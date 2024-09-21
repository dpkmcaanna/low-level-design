package com.lld.ofds.app;

import java.util.ArrayList;
import java.util.List;

import com.lld.ofds.app.entity.Customer;
import com.lld.ofds.app.entity.DeliveryAgent;
import com.lld.ofds.app.entity.MenuItem;
import com.lld.ofds.app.entity.MenuItemCategory;
import com.lld.ofds.app.entity.Order;
import com.lld.ofds.app.entity.OrderItem;
import com.lld.ofds.app.entity.OrderStatus;
import com.lld.ofds.app.entity.Restaurant;
import com.lld.ofds.app.service.FoodDeliveryService;

public class FoodDeliveryServiceApp {

	public static void main(String[] args) {
		FoodDeliveryService foodDeliveryService = FoodDeliveryService.getInstance();
		initialiseApp(foodDeliveryService);

		List<Restaurant> restaurants = foodDeliveryService.getAvailableRestaurants();
		List<MenuItem> menuItems = foodDeliveryService.getRestaurantMenu(restaurants.get(0).getId());

		List<OrderItem> orderItems = new ArrayList<>();
		orderItems.add(OrderItem.builder().menuItem(menuItems.get(0)).quantity(2).build());
		orderItems.add(OrderItem.builder().menuItem(menuItems.get(1)).quantity(1).build());
		
		List<Customer> cutomers = foodDeliveryService.getCustomers();
		
		Order order = foodDeliveryService.placeOrder(cutomers.get(0).getId(), restaurants.get(0).getId(), orderItems);

		// Update order status
		foodDeliveryService.updateOrderStatus(order.getId(), OrderStatus.CONFIRMED);
		
		System.out.println("Order status updated: " + order.getStatus());

		menuItems = foodDeliveryService.getRestaurantMenu(restaurants.get(1).getId());
		orderItems = new ArrayList<>();
		orderItems.add(OrderItem.builder().menuItem(menuItems.get(0)).quantity(1).build());

		Order order2 = foodDeliveryService.placeOrder(cutomers.get(1).getId(), restaurants.get(1).getId(), orderItems);
		foodDeliveryService.cancelOrder(order2.getId());
		
	}

	public static void initialiseApp(FoodDeliveryService foodDeliveryService) {
		Customer customer1 = Customer.builder().id("C001").name("Jack Daniel").email("test@test.com").phone("8844775511").build();
		Customer customer2 = Customer.builder().id("C002").name("Daniel JAck").email("test@test.com").phone("8844775512").build();
		foodDeliveryService.registerCustomer(customer1);
		foodDeliveryService.registerCustomer(customer2);

		List<MenuItem> restaurant1Menu = new ArrayList<>();

		restaurant1Menu.add(MenuItem.builder().id("M001").name("Burger").description("Delicious burger")
				.isAvailable(true).category(MenuItemCategory.VEG).rate(9.99).build());
		restaurant1Menu.add(MenuItem.builder().id("M002").name("Pizza").description("Cheesy pizza").isAvailable(true)
				.category(MenuItemCategory.NON_VEG).rate(19.99).build());

		Restaurant restaurant1 = Restaurant.builder().id("R001").name("Restaurant 1").address("Address 1")
				.menuItems(restaurant1Menu).build();

		foodDeliveryService.registerRestaurant(restaurant1);

		List<MenuItem> restaurant2Menu = new ArrayList<>();

		restaurant2Menu.add(MenuItem.builder().id("M003").name("North thali").description("North indian thali")
				.isAvailable(true).category(MenuItemCategory.VEG).rate(9.99).build());
		restaurant2Menu.add(MenuItem.builder().id("M004").name("South thali").description("South indian thali")
				.isAvailable(true).category(MenuItemCategory.NON_VEG).rate(19.99).build());

		Restaurant restaurant2 = Restaurant.builder().id("R002").name("Restaurant 2").address("Address 1")
				.menuItems(restaurant2Menu).build();

		foodDeliveryService.registerRestaurant(restaurant2);

		DeliveryAgent agent1 = DeliveryAgent.builder().id("D001").name("Agent 1").build();
		DeliveryAgent agent2 = DeliveryAgent.builder().id("D002").name("Agent 2").build();

		foodDeliveryService.registerDeliveryAgent(agent1);
		foodDeliveryService.registerDeliveryAgent(agent2);
	}

}
