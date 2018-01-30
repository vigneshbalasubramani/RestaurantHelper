package org.vigneshb.restaurants.delegates;

import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zilker.vigneshb.restaurants.RestaurantAdminDAO;
import org.zilker.vigneshb.restaurants.RestaurantUserDAO;

public class RestaurantManagerDelegate {
	RestaurantUserDAO restaurantUserDAO;
	RestaurantAdminDAO restaurantAdminDAO;
	public boolean addNewUser(String userName, String password, String phone, String userType) {
		restaurantUserDAO = new RestaurantUserDAO();
		try {
			int response = restaurantUserDAO.createUserBean(userName, password, phone, userType);
			if(response == 0) {
				return true;
			}
			else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean addNewFoodItem(String foodName, String foodCost) {
		restaurantAdminDAO = new RestaurantAdminDAO();
		try {
			int cost = Integer.parseInt(foodCost);
			if(restaurantAdminDAO.foodAlreadyExists(foodName)) {
				return false;
			}
			int response = restaurantAdminDAO.newFoodItem(foodName, cost);
			if(response == 0) {
				return true;
			}
			else {
				return false;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public String addNewOffer() {
		restaurantAdminDAO = new RestaurantAdminDAO();
		String newOffer = null;
		try {
			newOffer = restaurantAdminDAO.newOffer();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return newOffer;
		
	}
	
	public void deleteOffers() {
		restaurantAdminDAO = new RestaurantAdminDAO();
		try {
			restaurantAdminDAO.deleteOffer();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public boolean addNewOffer(String foodName1, String foodName2) {
		restaurantAdminDAO = new RestaurantAdminDAO();
		try {
			if(restaurantAdminDAO.insertNewOffer(foodName1, foodName2) == 0) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String doServiceForRestaurantManagers(HttpServletRequest request, HttpServletResponse response) {
		String destinationPageURL = null;
		boolean validEntry;
		response.setContentType("text/html");
		try {
		switch(Integer.parseInt(request.getParameter("option"))){
		case 1:// To add a new admin or a supervisor or a user
			validEntry = addNewUser(request.getParameter("userName"), request.getParameter("password"), request.getParameter("phone"), request.getParameter("userType"));
			if(validEntry) {
				response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Added the new user successfully!!<span class = 'close'>X</span></div></div>");
			}
			else{
				response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>User name already exists<span class = 'close'>X</span></div></div>");
			}
			destinationPageURL = "/AdminHomePage.jsp";
			break;
		case 2:// To add a new food item to the restaurant menu
			validEntry = addNewFoodItem(request.getParameter("foodName"), request.getParameter("foodCost"));
//			validEntry = true;
			if(validEntry) {
				response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Added the new food item successfully<span class = 'close'>X</span></div></div>" + "<div class='food'>" + request.getParameter("foodName") + "</div>");
			}
			else{
				response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>invalid details or food already exists<span class = 'close'>X</span></div></div>");
			}
			destinationPageURL = "/AdminNewFood.jsp";
			break;
		case 3:// To add a new offer to the food items
			String newOffer = addNewOffer();
			response.getWriter().append("<h3>" + newOffer + "</h3>");
			destinationPageURL = "/AdminNewOffer.jsp";
			break;
		case 4:// To delete the existing offers on food items
			deleteOffers();
			response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Succesfully deleted the current offer!!<span class = 'close'>X</span></div></div>");
			destinationPageURL = "/AdminNewOffer.jsp";
			break;
		case 5:// To add a new offer manually by the restaurant manager
			if(addNewOffer(request.getParameter("foodName1"), request.getParameter("foodName2"))) {
				response.getWriter().append("<h3>Successfully added the new offer!!</h3>");
			}
			else {
				response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Cannot add this offer<span class = 'close'>X</span></div></div>");
			}
			destinationPageURL = "/AdminNewOffer.jsp";
			break;
			default:break;
		}}
		catch(Exception e) {
			e.printStackTrace();
		}
		return destinationPageURL;
	}
}
