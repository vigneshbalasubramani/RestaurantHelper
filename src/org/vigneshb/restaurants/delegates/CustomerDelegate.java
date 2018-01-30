package org.vigneshb.restaurants.delegates;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vigneshb.restaurants.beans.OrderBean;
import org.zilker.vigneshb.restaurants.RestaurantUserDAO;

public class CustomerDelegate {
	RestaurantUserDAO restaurantUserDAO;
	HashMap<String, Integer> foodItems;
	private static final String BUTTON_TO_DELETE_FOOD_ITEM = "<td><form method = 'post' action = 'CustomerServlet'><input type = 'hidden' name = 'option' value = '6'/><input type = 'hidden' name = 'removedFoodName' value = '%s'/><input type = 'submit' value = 'X'/></form></td></tr>";
	public String viewMenu() {
		restaurantUserDAO = new RestaurantUserDAO();
		String menu = null;
		try{
			 menu = restaurantUserDAO.viewMenu();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return menu;
	}
	
	public String viewOffers() {
		restaurantUserDAO = new RestaurantUserDAO();
		String offers = null;
		try {
			offers = restaurantUserDAO.viewOffers();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return offers;
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

	public String getNoOfDeliveries(String userName, String typeOfOrder) {
		restaurantUserDAO = new RestaurantUserDAO();
		String deliveries = null;
		try {
			deliveries = restaurantUserDAO.checkDeliveries(userName, typeOfOrder);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return deliveries;
	}
	
	public int[] getDeliveryIds(String userName) {
		restaurantUserDAO = new RestaurantUserDAO();
		try {
			return restaurantUserDAO.getDeliveryIds(userName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public int getDeliveryCount(String userName) {
		restaurantUserDAO = new RestaurantUserDAO();
		try {
			return restaurantUserDAO.checkDeliveryStatus(userName);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean deleteAnOrder(int orderId) {
		restaurantUserDAO = new RestaurantUserDAO();
		try {
			if(restaurantUserDAO.deleteAnOrder(orderId)) {
				return true;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public String doServiceForCustomers(HttpServletRequest request, HttpServletResponse response) {
		String destinationPageURL = null;
		foodItems = (HashMap<String, Integer>)request.getAttribute("foodItemsHashMap");
		HttpSession session = request.getSession();
		response.setContentType("text/html");
		OrderBean orderBean;
		int orderId;
		StringBuilder orderedFoods = new StringBuilder("");
		try {
			int choice = Integer.parseInt(request.getParameter("option"));
			switch(choice) {
			case 1:// To view the menu of the restaurant
				session.setAttribute("menu", viewMenu());
				destinationPageURL = "/CustomerHomePage.jsp";
				break;
			case 2:// To make a new order
				if(session.getAttribute("orderId") == null) {
					orderBean = addNewOrder(session.getAttribute("userName").toString(), "delivery");
					session.setAttribute("orderId", orderBean.getOrderId());
				}
				String foodName = request.getParameter("foodName");
				int foodCount = Integer.parseInt(request.getParameter("foodCount").toString());
				if(foodItems.containsKey(foodName)) {
					foodItems.replace(foodName, foodItems.get(foodName) + foodCount);
				}
				else {
					foodItems.putIfAbsent(foodName, foodCount);
				}
				session.setAttribute("orderedFoods", getAllFoodOrders());
				/*orderedFoods.append(session.getAttribute("orderedFoods"));
				orderId = Integer.parseInt(session.getAttribute("orderId").toString());
				insertNewOrder(orderId, request.getParameter("foodName"), Integer.parseInt(request.getParameter("foodCount")));
				orderedFoods.append("<tr><td>" + request.getParameter("foodName") + "</td><td>" + request.getParameter("foodCount") + "</td></tr>");
				session.setAttribute("orderedFoods", orderedFoods.toString());*/
				destinationPageURL = "/CustomerNewDelivery.jsp";
				break;
			case 3:// To finish the order process
				foodItems = (HashMap<String, Integer>)request.getAttribute("foodItemsHashMap");				
				insertFoodOrder(Integer.parseInt(session.getAttribute("orderId").toString()), foodItems);
				session.setAttribute("orderId", null);
				foodItems.clear();
				session.removeAttribute("orderedFoods");
				response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Successfully added the order<span class = 'close'>X</span></div></div>");
				destinationPageURL = "/CustomerHomePage.jsp";
				break;
			case 4:// To get the orders made by a user
				String deliveries = getNoOfDeliveries(session.getAttribute("userName").toString(), "delivery");
				session.setAttribute("orders", deliveries);
				destinationPageURL = "/CustomerDeliveriesStatus.jsp";
				break;
			case 5:// To delete an order made earlier by a user
				if(deleteAnOrder(Integer.parseInt(request.getParameter("orderNumber")))) {
					response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Deleted the order successfully<span class = 'close'>X</span></div></div>");
				}	
				else {
					response.getWriter().append("<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Unable to delete the order at the moment<span class = 'close'>X</span></div></div>");
				}	
				destinationPageURL = "/CustomerDeliveriesStatus.jsp";
				break;
			case 6:// To delete a particular food item while ordering
				String removedFoodName = request.getParameter("removedFoodName");
				foodItems.remove(removedFoodName);
				session.setAttribute("orderedFoods", getAllFoodOrders());
				destinationPageURL = "/CustomerNewDelivery.jsp";
				break;
			default:break;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return destinationPageURL;
	}
	
	public void insertFoodOrder(int orderId, HashMap<String, Integer> foodItems) {
		Set<String> foodNames = foodItems.keySet();
		for(String foodName: foodNames) {
			insertNewOrder(orderId, foodName, foodItems.get(foodName));
		}
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
}
