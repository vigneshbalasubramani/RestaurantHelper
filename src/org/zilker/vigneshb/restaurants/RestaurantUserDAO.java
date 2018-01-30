package org.zilker.vigneshb.restaurants;

import java.sql.Connection;
import org.vigneshb.restaurants.dbutils.*;
import java.sql.PreparedStatement;

import org.vigneshb.restaurants.constants.*;
import org.vigneshb.restaurants.beans.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class RestaurantUserDAO implements DAOInterfaceUser {
	Connection connection;
	StringBuilder stringBuilder;
	PreparedStatement statement, statement1;
	ResultSet resultSet, resultSet1;
	RestaurantAdminDAO restaurantAdminDAO;
	public int users, foods, orders, noOfFoodItems = 0, foodItemNo = 0;
	Date date;
	Timestamp timestamp;
	FoodBean[] foodBeans;

	public int checkSpeed(int choice) throws SQLException {// checks no:of orders delivered on time
		try {
			connection = new Connectivity().connect(connection);
			if (choice == 1) {
				statement = connection.prepareStatement(SQLConstants.FIND_LATE_PARCELS);
				resultSet = statement.executeQuery();
				resultSet.next();
				return resultSet.getInt("count(*)");
			} else {
				statement = connection.prepareStatement(SQLConstants.FIND_LATE_DELIVERIES);
				resultSet = statement.executeQuery();
				resultSet.next();
				return resultSet.getInt("count(*)");
			}
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return 0;
	}

	public OrderBean parcelOrDelivery(String type, String customerName) throws SQLException {// adds new parcel or a
																								// delivery
		OrderBean orderBean = new OrderBean();
		orderBean.setOrderType(type);
		if (orderBean.setCustomerName(customerName) == -1) {
			return null;
		}
		checkTime();
		try {// set parcel/delivery id
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.FIND_ORDERS_COUNT);
			resultSet = statement.executeQuery();
			resultSet.next();
			orders = resultSet.getInt("max(order_id)") + 1;
			orderBean.setOrderId(orders);
			// insert parcel/delivery
			if (orderBean.getOrderType().equals("dine-in")) {
				statement = connection.prepareStatement(SQLConstants.INSERT_DINE_IN);
			} else {
				statement = connection.prepareStatement(SQLConstants.INSERT_ORDER);
			}
			statement.setInt(1, orderBean.getOrderId());
			statement.setString(2, orderBean.getOrderType());
			statement.setString(3, orderBean.getCustomerName());
			statement.setTimestamp(4, timestamp);
			statement.execute();
			// insert food items and update counts of ordered food
			// insertFoodOrders(orderBean);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return orderBean;
	}

	public String viewOffers() throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_NEW_OFFER);
			resultSet = statement.executeQuery();
			resultSet.next();
			return "The Latest combo offer : " + resultSet.getString("food_1") + " is FREE with "
					+ resultSet.getString("food_2");
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return null;
	}

	public int updateDeliveryStatus(String userName, String typeOfOrder) throws SQLException {// after a parcel or																								// delivery is done
		Timestamp timeStamp = checkTime();
		try {
			connection = new Connectivity().connect(connection);
			UserBean userBean = new UserBean();
			if (userBean.setUserName(userName) == -1) {
				return -1;
			} else {// do the update
				statement = connection.prepareStatement(SQLConstants.UPDATE_DELIVERY_STATUS);
				statement.setTimestamp(1, timeStamp);
				statement.setString(2, userName);
				statement.setString(3, typeOfOrder);
				statement.executeUpdate();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return 0;
	}

	public int checkDeliveryStatus(String userName) throws SQLException {
		OrderBean orderBean = new OrderBean();
		orderBean.setCustomerName(userName);
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.FIND_ORDER_COUNT);
			statement.setString(1, orderBean.getCustomerName());
			resultSet = statement.executeQuery();
			resultSet.next();
			return resultSet.getInt("count(*)");
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return 0;
	}

	public String viewMenu() throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_FOOD_MENU);
			resultSet = statement.executeQuery();
			if (resultSet.last()) {
				noOfFoodItems = resultSet.getRow();
				resultSet.beforeFirst();
			}
			foodBeans = new FoodBean[noOfFoodItems];
			foodItemNo = 0;
			while (resultSet.next()) {
				foodBeans[foodItemNo] = new FoodBean();
				foodBeans[foodItemNo].setFoodName(resultSet.getString("food_name"));
				foodBeans[foodItemNo].setFoodCost(resultSet.getInt("food_cost"));
				foodItemNo++;
			}
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return printMenu(foodBeans, noOfFoodItems);
	}

	public String checkDeliveries(String userName, String typeOfOrder) throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_FOODS_OF_CUSTOMER);
			statement.setString(1, typeOfOrder);
			statement.setString(2, userName);
			resultSet = statement.executeQuery();
			statement1 = connection.prepareStatement(SQLConstants.GET_FOOD_QUANTITIES_OF_CUSTOMER);
			statement1.setString(1, typeOfOrder);
			statement1.setString(2, userName);
			resultSet1 = statement1.executeQuery();
			if (resultSet.last()) {
				noOfFoodItems = resultSet.getRow();
				resultSet.beforeFirst();
			}
			foodBeans = new FoodBean[noOfFoodItems];
			foodItemNo = 0;
			while (resultSet.next() && resultSet1.next()) {
				foodBeans[foodItemNo] = new FoodBean();
				foodBeans[foodItemNo].setFoodName(resultSet.getString("food_name"));
				foodBeans[foodItemNo].setFoodId(resultSet.getInt("order_id"));
				foodBeans[foodItemNo].setFoodCount(resultSet1.getInt("food_quantity"));
				foodItemNo++;
			}
			if (noOfFoodItems == 0) {
			}
			return printDeliveries(foodBeans, noOfFoodItems);

		} catch (Exception e) {
			e.printStackTrace();
			// RestaurantConsole.inform("no deliveries for this customer");
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return null;
	}

	public int insertFoodOrders(OrderBean orderBean) throws SQLException, ClassNotFoundException {
		if (checkRepeatingFoodOrder(orderBean.getOrderId(), orderBean.getFoodName())) {
			updateRepeatingFoodOrder(orderBean.getOrderId(), orderBean.getFoodName(), orderBean.getFoodQuantity());
		} else {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.INSERT_FOOD_ORDERS_WITH_QUANTITY);
			statement.setInt(1, orderBean.getOrderId());
			statement.setString(2, orderBean.getFoodName());
			statement.setInt(3, orderBean.getFoodQuantity());
			statement.execute();
		}
		// update no:of orders made for each food item
		statement1 = connection.prepareStatement(SQLConstants.INCREMENT_FOOD_COUNT);
		statement1.setInt(1, orderBean.getFoodQuantity());
		statement1.setString(2, orderBean.getFoodName());
		statement1.executeUpdate();
		// update no:of food orders made for the customer
		statement1 = connection.prepareStatement(SQLConstants.UPDATE_USER_ORDERS);
		statement1.setInt(1, orderBean.getFoodQuantity());
		statement1.setString(2, orderBean.getCustomerName());
		statement1.executeUpdate();
		connection = new Connectivity().close(connection, resultSet, statement, statement1);
		return 0;
	}

	public Timestamp checkTime() {// gives current time
		date = new Date();
		timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	public int createUserBean(String userName, String password, String phone, String type) throws SQLException {
		restaurantAdminDAO = new RestaurantAdminDAO();
		if (userAlreadyExists(userName)) {
			return -1;
		}
		int response = 0;
		UserBean userBean = new UserBean();
		userBean.setUserType(type);
		if (userBean.setUserName(userName) == -1 || userBean.setUserPhone(phone) == -1
				|| userBean.setUserPassword(password) == -1)
			response = -1;
		if (response == 0)
			restaurantAdminDAO.addNewUser(userBean, 1);
		return response;
	}

	public String printDeliveries(FoodBean[] foodBeans, int noOfFoodItems) {
		stringBuilder = new StringBuilder("");
		int foodItemNo = 0;
		while (noOfFoodItems > 0) {
			noOfFoodItems--;
			stringBuilder.append("<tr><td> " + ++foodItemNo + " </td><td> ");
			stringBuilder.append(foodBeans[noOfFoodItems].getFoodId() + "</td><td>");
			stringBuilder.append(foodBeans[noOfFoodItems].getFoodName());
			stringBuilder.append(" </td><td> " + foodBeans[noOfFoodItems].getFoodCount() + "</td></tr>");
		}
		return stringBuilder.toString();
	}

	public String printMenu(FoodBean[] foodBeans, int noOfFoodItems) {
		stringBuilder = new StringBuilder("");
		while (noOfFoodItems > 0) {
			noOfFoodItems--;
			stringBuilder.append("<tr><td>" + foodBeans[noOfFoodItems].getFoodName() + "</td><td>");
			stringBuilder.append("Rs. " + foodBeans[noOfFoodItems].getFoodCost() + "</td></tr>");
		}
		return stringBuilder.toString();
	}

	public boolean userAlreadyExists(String userName) throws SQLException {
		boolean flag = false;
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_USER_NAMES);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (userName.equals(resultSet.getString("user_name"))) {
					flag = true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return flag;
	}

	public int[] getDeliveryIds(String userName) throws SQLException {
		int[] deliveryIds = new int[checkDeliveryStatus(userName)];
		int count = 0;
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_ORDERS_OF_CUSTOMER);
			statement.setString(1, userName);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				deliveryIds[count++] = resultSet.getInt("order_id");
			}
			return deliveryIds;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return null;
	}

	public boolean deleteAnOrder(int orderId) throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.DELETE_ORDERED_FOODS);
			statement.setInt(1, orderId);
			statement.execute();
			statement1 = connection.prepareStatement(SQLConstants.DELETE_ORDER);
			statement1.setInt(1, orderId);
			statement1.execute();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return false;
	}

	public String[] getCustomersWithOrders(String orderType) throws SQLException {
		String[] customersWithOrders;
		int noOfCustomersWithOrders = 0, count = 0;
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_CUSTOMERS_WITH_ORDERS);
			statement.setString(1, orderType);
			resultSet = statement.executeQuery();
			if (resultSet.last()) {
				noOfCustomersWithOrders = resultSet.getRow();
				resultSet.beforeFirst();
			}
			customersWithOrders = new String[noOfCustomersWithOrders];
			while (resultSet.next()) {
				customersWithOrders[count++] = resultSet.getString("user_name");
			}
			return customersWithOrders;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return null;
	}

	public boolean checkRepeatingFoodOrder(int orderID, String foodName) throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.CHECK_REPEATING_FOOD_ITEMS);
			statement.setInt(1, orderID);
			statement.setString(2, foodName);
			resultSet = statement.executeQuery();
			resultSet.next();
			if (resultSet.getInt("count(*)") > 0) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return false;
	}

	public boolean updateRepeatingFoodOrder(int orderId, String foodName, int foodQuantity) throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.UPDATE_REPEATING_FOOD_COUNT);
			statement.setInt(1, foodQuantity);
			statement.setInt(2, orderId);
			statement.setString(3, foodName);
			statement.executeUpdate();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}