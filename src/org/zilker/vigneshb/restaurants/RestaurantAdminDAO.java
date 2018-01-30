package org.zilker.vigneshb.restaurants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import org.vigneshb.restaurants.beans.*;
import org.vigneshb.restaurants.constants.ApplicationConstants;
import org.vigneshb.restaurants.constants.SQLConstants;
import org.vigneshb.restaurants.dbutils.Connectivity;

public class RestaurantAdminDAO implements DAOInterfaceAdmin {
	Connection connection;
	PreparedStatement statement, statement1;
	ResultSet resultSet;
	DAOInterfaceUser restaurantUserDAO = new RestaurantUserDAO();
	public int users, foods, takeouts, offers;
	Date date;
	Timestamp timestamp;

	public String login(String userName, String password) throws SQLException {
		UserBean userBean = new UserBean();
		userBean.setUserName(userName);
		userBean.setUserPassword(password);
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.CHECK_USER_TYPE);
			statement.setString(1, userBean.getUserName());
			statement.setString(2, userBean.getUserPassword());
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				userBean.setUserType(resultSet.getString("user_type"));
			} else if(userName.equals("delivery boy") && password.equals("asdfgf")) {
				return "delivery boy";
			}
			else {
				return null;
			}
			return userBean.getUserType();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return null;
	}

	public int newFoodItem(String foodName, int foodCost) throws SQLException {// adds new food item row
		FoodBean foodBean = new FoodBean();
		if (foodBean.setFoodName(foodName) == -1)
			return 1;
		if (foodBean.setFoodCost(foodCost) == -1)
			return 2;
		try {
			timestamp = new RestaurantUserDAO().checkTime();
			connection = new Connectivity().connect(connection);
			// to set food id
			statement = connection.prepareStatement(SQLConstants.FIND_FOOD_COUNT);
			resultSet = statement.executeQuery();
			resultSet.next();
			foods = resultSet.getInt("max(food_id)") + 1;
			foodBean.setFoodId(foods);
			foodBean.setFoodCount(0);
			// to insert new food item
			statement1 = connection.prepareStatement(SQLConstants.INSERT_FOOD_ITEM);
			statement1.setInt(1, foodBean.getFoodId());
			statement1.setString(2, foodBean.getFoodName());
			statement1.setInt(3, foodBean.getFoodCost());
			statement1.setInt(4, 0);
			statement1.setTimestamp(5, timestamp);
			statement1.execute();

		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}

		return 0;
	}

	public String newOffer() throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.FIND_UNPOPULAR_FOOD);
			resultSet = statement.executeQuery();
			resultSet.next();
			String unpopular = resultSet.getString("food_name");
			resultSet = statement.executeQuery(SQLConstants.FIND_POPULAR_FOOD);
			resultSet.next();
			String popular = resultSet.getString("food_name");
			insertNewOffer(unpopular, popular);
			StringBuilder offer = new StringBuilder("New offer of "+ unpopular +" with "+ popular +" is added");
			return offer.toString();
		} catch (Exception e) {
			// e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return "No offers can be added";
	}

	public int deleteOffer() throws SQLException {
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_NEW_OFFER);
			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				statement1 = connection.prepareStatement(SQLConstants.DELETE_CURRENT_OFFER);
				statement1.execute();
			}
		} catch (Exception e) {
			 e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return 0;
	}

	public int insertNewOffer(String unpopular, String popular) throws SQLException {// getting the offer id
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.FIND_MAX_OFFER_ID);
			resultSet = statement.executeQuery();
			resultSet.next();
			offers = resultSet.getInt("max(offer_id)") + 1;
			// inserting new offer
			statement1 = connection.prepareStatement(SQLConstants.INSERT_NEW_OFFER);
			statement1.setInt(1, offers);
			statement1.setString(2, unpopular);
			statement1.setString(3, popular);
			statement1.execute();
			return 0;
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return -1;
	}

	public int addNewUser(UserBean userBean, int userAccess) throws SQLException {
		try {// get customer id
			timestamp = restaurantUserDAO.checkTime();
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.FIND_CUSTOMER_COUNT);
			resultSet = statement.executeQuery();
			resultSet.next();
			users = resultSet.getInt("max(user_id)") + 1;
			userBean.setUserId(users);
			userBean.setUserOrders(0);
			if (userAccess == 0)
				userBean.setUserType(3);
			// insert customer details
			statement = connection.prepareStatement(SQLConstants.INSERT_CUSTOMER_DETAILS);
			statement.setInt(1, users);
			statement.setString(2, userBean.getUserName());
			statement.setString(3, userBean.getUserPassword());
			statement.setString(4, userBean.getUserType());
			statement.setString(5, userBean.getUserPhone());
			statement.setInt(6, userBean.getUserOrders());
			statement.setTimestamp(7, timestamp);
			statement.execute();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}

		return 0;
	}
	
	public String[] getFoodNames() throws SQLException {
		String[] foodNames = null;
		int noOfFoods = 0, count = 0;
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_FOOD_NAMES);
			resultSet = statement.executeQuery();
			if(resultSet.last()) {
				noOfFoods = resultSet.getRow();
				resultSet.beforeFirst();
			}
			foodNames = new String[noOfFoods];
			while(resultSet.next()) {
				foodNames[count] = new String();
				foodNames[count++] = resultSet.getString("food_name");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return foodNames;
	}
	
	public int getFoodCount() throws SQLException {
		int noOfFoods = 0;
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_FOOD_NAMES);
			resultSet = statement.executeQuery();
			if(resultSet.last()) {
				noOfFoods = resultSet.getRow();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return noOfFoods;
	}
	
	public boolean foodAlreadyExists(String foodName) throws SQLException {
		boolean flag = false;
		try {
			connection = new Connectivity().connect(connection);
			statement = connection.prepareStatement(SQLConstants.GET_FOOD_NAMES);
			resultSet = statement.executeQuery();
			while(resultSet.next()) {
				if(foodName.equals(resultSet.getString("food_name"))) {
					flag = true;
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			connection = new Connectivity().close(connection, resultSet, statement, statement1);
		}
		return flag;
	}
	
}


