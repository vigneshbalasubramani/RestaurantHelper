package org.vigneshb.restaurants.delegates;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.zilker.vigneshb.restaurants.*;

public class RestaurantLoginDelegate {

	public int login(String userName, String password) {
		RestaurantAdminDAO restaurantAdminDAO = new RestaurantAdminDAO();
		try {
			String userType = restaurantAdminDAO.login(userName, password);
			Logger logger = Logger.getLogger(RestaurantLoginDelegate.class.getName());
			logger.info(userType);
			if (userType == null) {
				return 0;
			}
			switch (userType) {
			case "admin":
				return 1;
			case "deliverer":
				return 2;
			case "customer":
				return 3;
			case "delivery boy":
				return 4;
			default:
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public boolean signupNewUser(String userName, String password, String phone) {
		RestaurantUserDAO restaurantUserDAO = new RestaurantUserDAO();
		try {
			if (restaurantUserDAO.createUserBean(userName, password, phone, "customer") == -1) {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	public String doLogoutServiceForUsers(HttpServletRequest request, HttpServletResponse response) {
		String destinationPageURL = null;
		int option = Integer.parseInt(request.getParameter("option"));
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		boolean isValidEntry = false;
		try {
			switch (option) {
			case 1:// To logout the session
				session.invalidate();
				destinationPageURL = "/WelcomePage.jsp";
				break;
			case 2:// To signup a new user
				isValidEntry = signupNewUser(request.getParameter("userName"), request.getParameter("password"),
						request.getParameter("phone"));
				if (isValidEntry) {
					response.getWriter().append(
							"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Successfully signed up!!<span class = 'close'>X</span></div></div>");
				} else {
					response.getWriter().append(
							"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>User name already exists<span class = 'close'>X</span></div></div>");
				}
				destinationPageURL = "/WelcomePage.jsp";
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destinationPageURL;
	}

	public String doLoginServiceForUsers(HttpServletRequest request, HttpServletResponse response) {
		String destinationPageURL = null;
		CustomerDelegate customerDelegate = new CustomerDelegate();
		int userType;
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		session.setAttribute("userName", request.getParameter("userName"));
		try {
			userType = login(request.getParameter("userName"), request.getParameter("password"));
			switch (userType) {
			case 1:// If the user is a restaurant manager
				destinationPageURL = "/AdminHomePage.jsp";
				break;
			case 2:// If the user is a supervisor
				destinationPageURL = "/DeliveryHomePage.jsp";
				break;
			case 3:// If the user is a customer
				session.setAttribute("orderedFoods", null);
				session.setAttribute("offer", customerDelegate.viewOffers());
				destinationPageURL = "/CustomerHomePage.jsp";
				break;
			case 4:// If the user is a delivery boy
				destinationPageURL = "/DeliveryBoyPage.jsp";
				break;
			default:// If it is an invalid user
				response.getWriter().append(
						"<div id = 'myModal' class = 'modal'><div class = 'modal-content'>Invalid credentails, please try again<span class = 'close'>X</span></div></div>");
				System.out.println(userType);
				destinationPageURL = "/WelcomePage.jsp";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return destinationPageURL;
	}
}
