package model;

public class Promo {
    private String idPromo;
    private String code;
    private String headline;
    private double discountPercentage;

    public Promo(String idPromo, String code, String headline, double discountPercentage) {
        this.setIdPromo(idPromo);
        this.setCode(code);
        this.headline = headline;
        this.setDiscountPercentage(discountPercentage);
    }

	public double getDiscountPercentage() {
		return discountPercentage;
	}

	public void setDiscountPercentage(double discountPercentage) {
		this.discountPercentage = discountPercentage;
	}

	public String getIdPromo() {
		return idPromo;
	}

	public void setIdPromo(String idPromo) {
		this.idPromo = idPromo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHeadline() {
		return headline;
	}


	public static Promo getPromoByCode(String code) {
		Promo promo = null;
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM promos WHERE code = ?"
			);
			stmt.setString(1, code);
			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				promo = new Promo(
					rs.getString("idPromo"),
					rs.getString("code"),
					rs.getString("headline"),
					rs.getDouble("discountPercentage")
				);
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return promo;
	}

	public boolean save() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO promos (idPromo, code, headline, discountPercentage) VALUES (?, ?, ?, ?)"
			);
			stmt.setString(1, this.idPromo);
			stmt.setString(2, this.code);
			stmt.setString(3, this.headline);
			stmt.setDouble(4, this.discountPercentage);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
