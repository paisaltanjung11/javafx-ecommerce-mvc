package model;

// Item di cart customer
public class CartItem {
    private String idCustomer;
    private String idProduct;
    private int count;

    public CartItem(String idCustomer, String idProduct, int count) {
        this.setIdCustomer(idCustomer);
        this.setIdProduct(idProduct);
        this.count = count;
    }

    public void editCount(int count) {
        this.count = count;
    }

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public String getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}

	public int getCount() {
		return count;
	}


	public static java.util.ArrayList<CartItem> getCartItemsByCustomer(String idCustomer) {
		java.util.ArrayList<CartItem> cartItems = new java.util.ArrayList<>();
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM cart_items WHERE idCustomer = ?"
			);
			stmt.setString(1, idCustomer);
			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				cartItems.add(new CartItem(
					rs.getString("idCustomer"),
					rs.getString("idProduct"),
					rs.getInt("count")
				));
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return cartItems;
	}

	public boolean save() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO cart_items (idCustomer, idProduct, count) VALUES (?, ?, ?) " +
				"ON DUPLICATE KEY UPDATE count = ?"
			);
			stmt.setString(1, this.idCustomer);
			stmt.setString(2, this.idProduct);
			stmt.setInt(3, this.count);
			stmt.setInt(4, this.count);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean update() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"UPDATE cart_items SET count = ? WHERE idCustomer = ? AND idProduct = ?"
			);
			stmt.setInt(1, this.count);
			stmt.setString(2, this.idCustomer);
			stmt.setString(3, this.idProduct);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean delete() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"DELETE FROM cart_items WHERE idCustomer = ? AND idProduct = ?"
			);
			stmt.setString(1, this.idCustomer);
			stmt.setString(2, this.idProduct);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public static boolean clearCart(String idCustomer) {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"DELETE FROM cart_items WHERE idCustomer = ?"
			);
			stmt.setString(1, idCustomer);

			int result = stmt.executeUpdate();
			stmt.close();
			return result >= 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
