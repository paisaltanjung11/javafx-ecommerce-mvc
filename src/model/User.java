package model;

// Base class untuk semua user (Customer, Admin, Courier)
public class User {
    protected String idUser;
    protected String fullName;
    private String email;
    private String password;
    protected String phone;
    protected String address;
    protected String role;

    public User(String idUser, String fullName, String email, String password,
                String phone, String address, String role) {
        this.idUser = idUser;
        this.fullName = fullName;
        this.setEmail(email);
        this.setPassword(password);
        this.phone = phone;
        this.address = address;
        this.role = role;
    }

    public String getIdUser() { return idUser; }
    public String getRole() { return role; }
    public String getFullName() { return fullName; }
    public String getPhone() { return phone; }
    public String getAddress() { return address; }

    public void editProfile(String fullName, String phone, String address) {
        this.fullName = fullName;
        this.phone = phone;
        this.address = address;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


	public static User getUserByEmailPassword(String email, String password) {
		User user = null;
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM users WHERE email = ? AND password = ?"
			);
			stmt.setString(1, email);
			stmt.setString(2, password);
			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				user = createUserFromResultSet(rs);
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static User getUserById(String idUser) {
		User user = null;
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM users WHERE idUser = ?"
			);
			stmt.setString(1, idUser);
			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				user = createUserFromResultSet(rs);
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static boolean emailExists(String email) {
		boolean exists = false;
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT COUNT(*) FROM users WHERE email = ?"
			);
			stmt.setString(1, email);
			java.sql.ResultSet rs = stmt.executeQuery();

			if (rs.next() && rs.getInt(1) > 0) {
				exists = true;
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return exists;
	}

	public boolean save() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"INSERT INTO users (idUser, fullName, email, password, phone, address, role, balance, emergencyContact, vehicleType, vehiclePlate) " +
				"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
			);
			stmt.setString(1, this.idUser);
			stmt.setString(2, this.fullName);
			stmt.setString(3, this.email);
			stmt.setString(4, this.password);
			stmt.setString(5, this.phone);
			stmt.setString(6, this.address);
			stmt.setString(7, this.role);
			
			if (this instanceof Customer) {
				Customer c = (Customer) this;
				stmt.setDouble(8, c.getBalance());
				stmt.setString(9, null);
				stmt.setString(10, null);
				stmt.setString(11, null);
			} else if (this instanceof Admin) {
				Admin a = (Admin) this;
				stmt.setDouble(8, 0);
				stmt.setString(9, a.getEmergencyContact());
				stmt.setString(10, null);
				stmt.setString(11, null);
			} else if (this instanceof Courier) {
				Courier c = (Courier) this;
				stmt.setDouble(8, 0);
				stmt.setString(9, null);
				stmt.setString(10, c.getVehicleType());
				stmt.setString(11, c.getVehiclePlate());
			} else {
				stmt.setDouble(8, 0);
				stmt.setString(9, null);
				stmt.setString(10, null);
				stmt.setString(11, null);
			}

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateProfile() {
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"UPDATE users SET fullName = ?, phone = ?, address = ? WHERE idUser = ?"
			);
			stmt.setString(1, this.fullName);
			stmt.setString(2, this.phone);
			stmt.setString(3, this.address);
			stmt.setString(4, this.idUser);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateBalance(double balance) {
		if (!(this instanceof Customer)) {
			return false;
		}
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"UPDATE users SET balance = ? WHERE idUser = ?"
			);
			stmt.setDouble(1, balance);
			stmt.setString(2, this.idUser);

			int result = stmt.executeUpdate();
			stmt.close();
			return result > 0;
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	private static User createUserFromResultSet(java.sql.ResultSet rs) throws java.sql.SQLException {
		String idUser = rs.getString("idUser");
		String fullName = rs.getString("fullName");
		String email = rs.getString("email");
		String password = rs.getString("password");
		String phone = rs.getString("phone");
		String address = rs.getString("address");
		String role = rs.getString("role");

		if ("CUSTOMER".equals(role)) {
			double balance = rs.getDouble("balance");
			return new Customer(idUser, fullName, email, password, phone, address, balance);
		} else if ("ADMIN".equals(role)) {
			String emergencyContact = rs.getString("emergencyContact");
			return new Admin(idUser, fullName, email, password, phone, address, emergencyContact);
		} else if ("COURIER".equals(role)) {
			String vehicleType = rs.getString("vehicleType");
			String vehiclePlate = rs.getString("vehiclePlate");
			return new Courier(idUser, fullName, email, password, phone, address, vehicleType, vehiclePlate);
		}

		return new User(idUser, fullName, email, password, phone, address, role);
	}

	public static java.util.ArrayList<User> getAllCouriers() {
		java.util.ArrayList<User> couriers = new java.util.ArrayList<>();
		try {
			java.sql.Connection conn = DatabaseConnection.getConnection();
			java.sql.PreparedStatement stmt = conn.prepareStatement(
				"SELECT * FROM users WHERE role = 'COURIER'"
			);
			java.sql.ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				couriers.add(createUserFromResultSet(rs));
			}

			rs.close();
			stmt.close();
		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}
		return couriers;
	}
}
