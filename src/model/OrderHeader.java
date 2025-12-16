package model;

import java.util.Date;

// Header order (transaksi)
public class OrderHeader {
    private String idOrder;
    private String idCustomer;
    private String idPromo;
    private String status;
    private Date orderedAt;
    private double totalAmount;

    public OrderHeader(String idOrder, String idCustomer, String idPromo,
                       String status, Date orderedAt, double totalAmount) {
        this.idOrder = idOrder;
        this.idCustomer = idCustomer;
        this.idPromo = idPromo;
        this.status = status;
        this.orderedAt = orderedAt;
        this.totalAmount = totalAmount;
    }

    public String getIdOrder() { return idOrder; }
    public String getIdCustomer() { return idCustomer; }
    public String getIdPromo() { return idPromo; }
    public String getStatus() { return status; }
    public java.util.Date getOrderedAt() { return orderedAt; }
    public double getTotalAmount() { return totalAmount; }


	public boolean save() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO order_headers (idOrder, idCustomer, idPromo, status, orderedAt, totalAmount) " +
				"VALUES (?, ?, ?, ?, ?, ?)"
			);
			stmt.setString(1, this.idOrder);
			stmt.setString(2, this.idCustomer);
			if (this.idPromo == null) {
				stmt.setString(3, null);
			} else {
				stmt.setString(3, this.idPromo);
			}
			stmt.setString(4, this.status);
			stmt.setTimestamp(5, new java.sql.Timestamp(this.orderedAt.getTime()));
			stmt.setDouble(6, this.totalAmount);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static java.util.ArrayList<OrderHeader> getOrdersByCustomer(String idCustomer) {
		java.util.ArrayList<OrderHeader> orders = new java.util.ArrayList<>();
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM order_headers WHERE idCustomer = ? ORDER BY orderedAt DESC"
			);
			stmt.setString(1, idCustomer);
			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				orders.add(new OrderHeader(
					rs.getString("idOrder"),
					rs.getString("idCustomer"),
					rs.getString("idPromo"),
					rs.getString("status"),
					rs.getTimestamp("orderedAt"),
					rs.getDouble("totalAmount")
				));
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public static java.util.ArrayList<OrderHeader> getAllOrders() {
		java.util.ArrayList<OrderHeader> orders = new java.util.ArrayList<>();
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM order_headers ORDER BY orderedAt DESC"
			);
			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				orders.add(new OrderHeader(
					rs.getString("idOrder"),
					rs.getString("idCustomer"),
					rs.getString("idPromo"),
					rs.getString("status"),
					rs.getTimestamp("orderedAt"),
					rs.getDouble("totalAmount")
				));
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public static OrderHeader getOrderById(String idOrder) {
		OrderHeader order = null;
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM order_headers WHERE idOrder = ?"
			);
			stmt.setString(1, idOrder);
			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				order = new OrderHeader(
					rs.getString("idOrder"),
					rs.getString("idCustomer"),
					rs.getString("idPromo"),
					rs.getString("status"),
					rs.getTimestamp("orderedAt"),
					rs.getDouble("totalAmount")
				);
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return order;
    }
}
