package com.lld.ofds.app.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryAgent {
	String id;
	String name;
	String phone;
	String address;
	volatile boolean isAvailable;
}
