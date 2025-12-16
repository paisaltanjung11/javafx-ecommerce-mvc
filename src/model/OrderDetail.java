package model;

public class OrderDetail {
    private String idOrder;
    private String idProduct;
    private int qty;

    public OrderDetail(String idOrder, String idProduct, int qty) {
        this.idOrder = idOrder;
        this.idProduct = idProduct;
        this.qty = qty;
    }

    public String getIdOrder() { return idOrder; }
    public String getIdProduct() { return idProduct; }
    public int getQty() { return qty; }


	public boolean save() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO order_details (idOrder, idProduct, qty) VALUES (?, ?, ?)"
			);
			stmt.setString(1, this.idOrder);
			stmt.setString(2, this.idProduct);
			stmt.setInt(3, this.qty);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static java.util.ArrayList<OrderDetail> getOrderDetailsByOrder(String idOrder) {
		java.util.ArrayList<OrderDetail> details = new java.util.ArrayList<>();
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM order_details WHERE idOrder = ?"
			);
			stmt.setString(1, idOrder);
			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				details.add(new OrderDetail(
					rs.getString("idOrder"),
					rs.getString("idProduct"),
					rs.getInt("qty")
				));
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return details;
	}
}
