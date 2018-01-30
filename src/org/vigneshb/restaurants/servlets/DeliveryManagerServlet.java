package org.vigneshb.restaurants.servlets;

import java.io.IOException;
import java.util.HashMap;

import org.vigneshb.restaurants.beans.OrderBean;
import org.vigneshb.restaurants.delegates.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DeliveryManagerServlet
 */
@WebServlet("/DeliveryManagerServlet")
public class DeliveryManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HashMap<String, Integer> foodItems;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeliveryManagerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(foodItems == null) {
			foodItems = new HashMap<String, Integer>();
		}
		request.setAttribute("foodItemsHashMap", foodItems);
		DeliveryManagerDelegate deliveryManagerDelegate = new DeliveryManagerDelegate();
		RequestDispatcher rd;
		String destinationPageURL = deliveryManagerDelegate.doServiceForDeliveryManagers(request, response);
		if(destinationPageURL.equals("/NewDelivery.jsp")) {
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
