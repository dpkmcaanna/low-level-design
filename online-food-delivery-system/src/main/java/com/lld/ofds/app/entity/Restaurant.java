package com.lld.ofds.app.entity;

import java.util.List;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Restaurant {	
	String id;
	String name;
	String address;
	List<MenuItem> menuItems;
}
