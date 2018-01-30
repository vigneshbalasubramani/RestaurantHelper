package org.vigneshb.restaurants.beans;

public class UserBean {// used to handle customer details
	int userId, userOrders;
	String userName, userPassword, userType, userPhone;

	public int getUserId() {
		return userId;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public String getUserType() {
		return userType;
	}

	public int getUserOrders() {
		return userOrders;
	}

	public String getUserName() {
		return userName;
	}

	public int setUserId(int userId) {
		this.userId = userId;
		return 0;
	}

	public int setUserName(String userName) {
		if (userName != null)
			this.userName = userName;
		else
			return -1;
		return 0;
	}

	public int setUserType(int userType) {
		if (userType == 1)
			this.userType = "admin";
		else if (userType == 2)
			this.userType = "deliverer";
		else
			this.userType = "customer";
		return 0;
	}

	public int setUserType(String userType) {
		if (userType.equals("admin"))
			this.userType = "admin";
		else if (userType.equals("deliverer"))
			this.userType = "deliverer";
		else
			this.userType = "customer";
		return 0;
	}

	public int setUserPassword(String userPassword) {
		if (userPassword != null)
			this.userPassword = userPassword;
		else
			return -1;
		return 0;
	}

	public int setUserPhone(String userPhone) {
		if (userPhone.length() == 10)
			this.userPhone = userPhone;
		else
			return -1;
		return 0;
	}

	public int setUserOrders(int userOrders) {
		if (userOrders >= 0)
			this.userOrders = userOrders;
		else
			return -1;
		return 0;
	}
}
