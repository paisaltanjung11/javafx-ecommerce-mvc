package model;

public class Delivery {
    private String idOrder;
    private String idCourier;
    private String status;

    public Delivery(String idOrder, String idCourier, String status) {
        this.setIdOrder(idOrder);
        this.idCourier = idCourier;
        this.status = status;
    }

    public void editStatus(String status) {
        this.status = status;
    }

	public String getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}

	public String getIdCourier() {
		return idCourier;
	}

	public String getStatus() {
		return status;
	}


	public boolean save() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO deliveries (idOrder, idCourier, status) VALUES (?, ?, ?)"
			);
			stmt.setString(1, this.idOrder);
			stmt.setString(2, this.idCourier);
			stmt.setString(3, this.status);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateStatus() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"UPDATE deliveries SET status = ? WHERE idOrder = ?"
			);
			stmt.setString(1, this.status);
			stmt.setString(2, this.idOrder);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Delivery getDeliveryByOrder(String idOrder) {
		Delivery delivery = null;
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM deliveries WHERE idOrder = ?"
			);
			stmt.setString(1, idOrder);
			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				delivery = new Delivery(
					rs.getString("idOrder"),
					rs.getString("idCourier"),
					rs.getString("status")
				);
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return delivery;
	}

	public static java.util.ArrayList<Delivery> getDeliveriesByCourier(String idCourier) {
		java.util.ArrayList<Delivery> deliveries = new java.util.ArrayList<>();
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM deliveries WHERE idCourier = ?"
			);
			stmt.setString(1, idCourier);
			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				deliveries.add(new Delivery(
					rs.getString("idOrder"),
					rs.getString("idCourier"),
					rs.getString("status")
				));
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return deliveries;
	}
}
