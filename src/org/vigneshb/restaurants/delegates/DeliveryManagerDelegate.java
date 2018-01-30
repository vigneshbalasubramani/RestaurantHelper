package org.vigneshb.restaurants.delegates;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vigneshb.restaurants.beans.*;
import org.zilker.vigneshb.restaurants.*;

public class DeliveryManagerDelegate {
	private static final String BUTTON_TO_DELETE_FOOD_ITEM = "<td><form method = 'post' action = 'DeliveryManagerServlet'><input type = 'hidden' name = 'option' value = '8'/><input type = 'hidden' name = 'removedFoodName' value = '%s'/><input type = 'submit' value = 'X'/></form></td></tr>";
	HashMap<String, Integer> foodItems;
	RestaurantUserDAO restaurantUserDAO;
	RestaurantAdminDAO restaurantAdminDAO;

	public boolean addNewUser(String userName, String password, String phone) {
		restaurantUserDAO = new RestaurantUserDAO();
		try {
			String userType = "3";
			int response = restaurantUserDAO.createUserBean(userName, password, phone, userType);
			if (response == 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public String checkUserDeliveries(String userName, String typeOfOrder) {
		restaurantUserDAO = new RestaurantUserDAO();
		String deliveryList = null;
		try {
			deliveryList = restaurantUserDAO.checkDeliveries(userName, typeOfOrder);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deliveryList;
	}

	public boolean updateDeliveryStatus(String userName, String typeOfOrder) {
		restaurantUserDAO = new RestaurantUserDAO();
		boolean updatedDeliveryStatus = false;
		try {
			if (restaurantUserDAO.updateDeliveryStatus(userName, typeOfOrder) == 0) {
				updatedDeliveryStatus = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return updatedDeliveryStatus;
	}

	public int checkDeliverySpeed(String deliveryType) {
		restaurantUserDAO = new RestaurantUserDAO();
		int noOfLateDeliveries = 0;
		try {
			switch (deliveryType) {
			case "parcel":
				noOfLateDeliveries = restaurantUserDAO.checkSpeed(1);
				break;
			case "delivery":
				noOfLateDeliveries = restaurantUserDAO.checkSpeed(2);
				break;
			default:
				noOfLateDeliveries = -1;
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noOfLateDeliveries;
	}

	public OrderBean addNewOrder(String userName, String orderType) {
		restaurantUserDAO = new RestaurantUserDAO();
		OrderBean orderBean = null;
		try {
			orderBean = restaurantUserDAO.parcelOrDelivery(orderType, userName);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderBean;
	}

	public void insertNewOrder(int orderId, String foodName, int foodCount) {
		restaurantUserDAO = new RestaurantUserDAO();
		OrderBean orderBean = new OrderBean();
		orderBean.setOrderId(orderId);
		orderBean.setFoodName(foodName);
		orderBean.setFoodQuantity(foodCount);
		try {
			restaurantUserDAO.insertFoodOrders(orderBean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String[] getFoodNames() {
		String[] foodNames = null;
		restaurantAdminDAO = new RestaurantAdminDAO();
		try {
			foodNames = restaurantAdminDAO.getFoodNames();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return foodNames;
	}

	public int getFoodCount() {
		int noOfFoods = 0;
		restaurantAdminDAO = new RestaurantAdminDAO();
		try {
			noOfFoods = restaurantAdminDAO.getFoodCount();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return noOfFoods;
	}

	public boolean userAlreadyExists(String userName) {
		try {
			restaurantUserDAO = new RestaurantUserDAO();
			return restaurantUserDAO.userAlreadyExists(userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String[] getCustomersWithOrders(String orderType) {
		try {
			restaurantUserDAO = new RestaurantUserDAO();
			return restaurantUserDAO.getCustomersWithOrders(orderType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public String doServiceForDeliveryManagers(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		foodItems = (HashMap<String, Integer>)request.getAttribute("foodItemsHashMap");
		String destinationPageURL = null;
		boolean isValidEntry = true;
		OrderBean orderBean = null;
		response.setContentType("text/html");
		StringBuilder orderedFoods = new StringBuilder("");
		try {
			switch (Integer.parseInt(request.getParameter("option"))) {
			case 1:// To add a new customer
				isValidEntry = addNewUser(request.getParameter("userName"), request.getParameter("password"),
						request.getParameter("phone"));
				if (isValidEntry) {
					response.getWriter().append(
							"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Added the new user successfully!<span class = 'close'>X</span></div></div>");
				} else {
					response.getWriter().append(
							"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>User name already exists<span class = 'close'>X</span></div></div>");
				}
				destinationPageURL = "/DeliveryHomePage.jsp";
				break;
			case 2:// To add a new order for any customer in the restaurant
				if (userAlreadyExists(request.getParameter("userName"))) {
					orderBean = addNewOrder(request.getParameter("userName"), request.getParameter("orderType"));
				}
				if (orderBean == null) {
					response.getWriter().append(
							"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Invalid user name, please try again<span class = 'close'>X</span></div></div>");
					destinationPageURL = "/NewDelivery.jsp";
				} else {
					session.setAttribute("orderId", orderBean.getOrderId());
					session.setAttribute("orderedFoods", "");
					destinationPageURL = "/DeliveryDetailsRequest.jsp";
				}
				break;
			case 3:// To check the orders made by the users
				String deliveryDetails = checkUserDeliveries(request.getParameter("userName"),
						request.getParameter("typeOfOrder"));
				request.setAttribute("deliveries", deliveryDetails);
				if (request.getParameter("typeOfOrder").equals("delivery")) {
					destinationPageURL = "/DeliveryBoyPage.jsp";
				} else {
					destinationPageURL = "/DeliveryPage.jsp";
				}
				break;
			case 4:// To update the order status after delivery
				isValidEntry = updateDeliveryStatus(request.getParameter("userName"),
						request.getParameter("typeOfOrder"));
				if (isValidEntry) {
					response.getWriter().append(
							"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Updated the delivery status!<span class = 'close'>X</span></div></div>");
				} else {
					response.getWriter().append(
							"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Delivery status cannot be updated<span class = 'close'>X</span></div></div>");
				}
				if (request.getParameter("typeOfOrder").equals("delivery")) {
					destinationPageURL = "/DeliveryBoyPage.jsp";
				} else {
					destinationPageURL = "/DeliveryPage.jsp";
				}
				break;
			case 5:// To check the overall speed of parcel and delivery
				int lateDeliveries = checkDeliverySpeed(request.getParameter("deliveryType"));
				response.getWriter()
						.append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>" + lateDeliveries
								+ " " + request.getParameter("deliveryType")
								+ "(s) are delivered late<span class = 'close'>X</span></div></div>");
				destinationPageURL = "/DeliveryCheckPage.jsp";
				break;
			case 6:// To insert a dish in an order
				String foodName = request.getParameter("foodName");
				int foodCount = Integer.parseInt(request.getParameter("foodCount").toString());
				if(foodItems.containsKey(foodName)) {
					foodItems.replace(foodName, foodItems.get(foodName) + foodCount);
				}
				else {
					foodItems.putIfAbsent(foodName, foodCount);
				}
				session.setAttribute("orderedFoods", getAllFoodOrders());
				//orderedFoods.append(session.getAttribute("orderedFoods"));
				/*insertNewOrder(Integer.parseInt(session.getAttribute("orderId").toString()),
						request.getParameter("foodName"), Integer.parseInt(request.getParameter("foodCount")));
				orderedFoods.append("<tr><td>" + request.getParameter("foodName") + "</td><td>"
						+ request.getParameter("foodCount") + "</td></tr>");
				session.setAttribute("orderedFoods", orderedFoods.toString());*/
				destinationPageURL = "/DeliveryDetailsRequest.jsp";
				break;
			case 7:// To confirm the inserted dishes and finalise the order
				session.removeAttribute("orderedFoods");
				insertFoodOrder(Integer.parseInt(session.getAttribute("orderId").toString()), foodItems);
				response.getWriter().append(
						"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Successfully added the new order!!<span class = 'close'>X</span></div></div>");
				destinationPageURL = "/NewDelivery.jsp";
				break;
			case 8:// To delete a particular food item while ordering
				String removedFoodName = request.getParameter("removedFoodName");
				foodItems.remove(removedFoodName);
				session.setAttribute("orderedFoods", getAllFoodOrders());
				destinationPageURL = "/DeliveryDetailsRequest.jsp";
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destinationPageURL;
	}
	
	public String getAllFoodOrders() {
		StringBuilder foodOrders = new StringBuilder("");
		Set<String> foodNames = foodItems.keySet();
		for(String foodName : foodNames) {
			foodOrders.append("<tr><td>" + foodName + "</td><td>");
			foodOrders.append(foodItems.get(foodName) + "</td>");
			foodOrders.append(String.format(BUTTON_TO_DELETE_FOOD_ITEM, foodName));
		}
		return foodOrders.toString();
	}
	
	public void insertFoodOrder(int orderId, HashMap<String, Integer> foodItems) {
		Set<String> foodNames = foodItems.keySet();
		for(String foodName: foodNames) {
			insertNewOrder(orderId, foodName, foodItems.get(foodName));
		}
	}
}
