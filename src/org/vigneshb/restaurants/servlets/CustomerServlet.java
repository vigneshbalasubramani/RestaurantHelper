package org.vigneshb.restaurants.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.vigneshb.restaurants.beans.OrderBean;
import org.vigneshb.restaurants.delegates.CustomerDelegate;
import org.zilker.vigneshb.restaurants.RestaurantUserDAO;

/**
 * Servlet implementation class CustomerServlet
 */
@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, Integer> foodItems;
	String destinationPageURL;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CustomerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		if(foodItems == null) {
			foodItems = new HashMap<String, Integer>();
		}
		request.setAttribute("foodItemsHashMap", foodItems);
		CustomerDelegate customerDelegate = new CustomerDelegate();
		destinationPageURL = customerDelegate.doServiceForCustomers(request, response);
		if(destinationPageURL.equals("/CustomerHomePage.jsp")) {
			foodItems = null;
		}
		rd = request.getRequestDispatcher(destinationPageURL);
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
