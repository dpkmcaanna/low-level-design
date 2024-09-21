package com.lld.ofds.app.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer {
	String id;
	String name;
	String address;
	String email;
	String phone;
}
