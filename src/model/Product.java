package model;

public class Product {
    private String idProduct;
    private String name;
    private double price;
    private int stock;
    private String category;

    public Product(String idProduct, String name, double price, int stock, String category) {
        this.setIdProduct(idProduct);
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.category = category;
    }

    public int getStock() { return stock; }

    public void editStock(int stock) {
        this.stock = stock;
    }

	public String getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(String idProduct) {
		this.idProduct = idProduct;
	}

	public String getName() {
		return name;
	}

	public double getPrice() {
		return price;
	}

	public String getCategory() {
		return category;
	}


	public static Product getProductById(String idProduct) {
		Product product = null;
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM products WHERE idProduct = ?"
			);
			stmt.setString(1, idProduct);
			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				product = new Product(
					rs.getString("idProduct"),
					rs.getString("name"),
					rs.getDouble("price"),
					rs.getInt("stock"),
					rs.getString("category")
				);
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return product;
	}

	public static java.util.ArrayList<Product> getAllProducts() {
		java.util.ArrayList<Product> products = new java.util.ArrayList<>();
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM products"
			);
			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				products.add(new Product(
					rs.getString("idProduct"),
					rs.getString("name"),
					rs.getDouble("price"),
					rs.getInt("stock"),
					rs.getString("category")
				));
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return products;
	}

	public boolean save() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO products (idProduct, name, price, stock, category) VALUES (?, ?, ?, ?, ?)"
			);
			stmt.setString(1, this.idProduct);
			stmt.setString(2, this.name);
			stmt.setDouble(3, this.price);
			stmt.setInt(4, this.stock);
			stmt.setString(5, this.category);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateStock() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"UPDATE products SET stock = ? WHERE idProduct = ?"
			);
			stmt.setInt(1, this.stock);
			stmt.setString(2, this.idProduct);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
