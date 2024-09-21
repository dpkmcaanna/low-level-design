package com.lld.ofds.app.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
	double quantity;
	MenuItem menuItem;
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((menuItem == null) ? 0 : menuItem.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderItem other = (OrderItem) obj;
		if (menuItem == null) {
			if (other.menuItem != null)
				return false;
		} else if (!menuItem.equals(other.menuItem))
			return false;
		return true;
	}
}
