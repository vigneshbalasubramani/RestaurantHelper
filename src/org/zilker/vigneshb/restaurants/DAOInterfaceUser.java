package org.zilker.vigneshb.restaurants;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.vigneshb.restaurants.beans.OrderBean;

public interface DAOInterfaceUser {

	public int checkSpeed(int choice) throws SQLException;

	public OrderBean parcelOrDelivery(String type, String customerName) throws SQLException;

	public int updateDeliveryStatus(String customerName, String typeOfOrder) throws SQLException;

	public Timestamp checkTime() throws SQLException;

	public int checkDeliveryStatus(String customerName) throws SQLException;

	public String viewMenu() throws SQLException;

	public String viewOffers() throws SQLException;

	public String checkDeliveries(String customerName, String typeOfOrder) throws SQLException;
}
