package org.vigneshb.restaurants.beans;

public class OrderBean {// used to handle parcel and delivery details
	int orderId, foodQuantity;
	String orderType, customerName, foodName;

	public int getOrderId() {
		return orderId;
	}
	
	public int getFoodQuantity() {
		return foodQuantity;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getFoodName() {
		return foodName;
	}

	public String getOrderType() {
		return orderType;
	}

	public int setOrderId(int takeoutId) {
		this.orderId = takeoutId;
		return 0;
	}
	
	public int setFoodQuantity(int foodQuantity) {
		this.foodQuantity = foodQuantity;
		return 0;
	}

	public int setCustomerName(String customerName) {
		if (customerName != null)
			this.customerName = customerName;
		else
			return -1;
		return 0;
	}
	
	public int setFoodName(String foodName) {
		if (foodName != null)
			this.foodName = foodName;
		else
			return -1;
		return 0;
	}

	public int setFoodId(String foodId) {
		if (foodId != null)
			this.foodName = foodId;
		else
			return -1;
		return 0;
	}

	public int setOrderType(String orderType) {
		if (orderType.equals("parcel"))
			this.orderType = "parcel";
		else if (orderType.equals("delivery"))
			this.orderType = "delivery";
		else
			this.orderType = "dine-in";
		return 0;
	}
}
