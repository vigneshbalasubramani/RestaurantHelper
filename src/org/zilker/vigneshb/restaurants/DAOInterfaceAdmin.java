package org.zilker.vigneshb.restaurants;

import java.sql.SQLException;

import org.vigneshb.restaurants.beans.UserBean;

public interface DAOInterfaceAdmin {
	public int newFoodItem(String foodName, int foodCost) throws SQLException;

	public String newOffer() throws SQLException;

	public int deleteOffer() throws SQLException;

	public String login(String userName, String password) throws SQLException;

	public int addNewUser(UserBean userBean, int userAccess) throws SQLException;

}
