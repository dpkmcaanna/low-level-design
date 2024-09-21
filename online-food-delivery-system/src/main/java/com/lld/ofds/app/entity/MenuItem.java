package com.lld.ofds.app.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class MenuItem {
	String id;
	String name;
	String description;
	double rate;
	boolean isAvailable;
	MenuItemCategory category;
}
