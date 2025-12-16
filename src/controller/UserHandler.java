package controller;

import model.User;
import model.Customer;
import java.util.ArrayList;

// Handler untuk operasi user (login, register, edit profile, top up)
public class UserHandler {

    // Login user
    public User getUser(String email, String password) {
        return User.getUserByEmailPassword(email, password);
    }

    // Edit profile user, cuma nama, phone sama address doang
    public void editProfile(User user, String fullName, String phone, String address) {
        if (fullName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            System.out.println("All fields must be filled!");
            return;
        }

        if (!isNumeric(phone)) {
            System.out.println("Phone number must be numeric!");
            return;
        }

        if (phone.length() < 10 || phone.length() > 13) {
            System.out.println("Phone must be 10–13 digits");
            return;
        }

        user.editProfile(fullName, phone, address);
        if (user.updateProfile()) {
            System.out.println("Profile updated!");
        } else {
            System.out.println("Failed to update profile!");
        }
    }

    public void addUser(User user) {
        user.save();
    }

    public User getUserById(String idUser) {
        return User.getUserById(idUser);
    }

    public ArrayList<User> getAllCouriers() {
        return User.getAllCouriers();
    }

    // Register new acc
    public void registerAccount(String fullName, String email, String password, String confirmPassword,
                                String phone, String address) {
        if (fullName.isEmpty() || email.isEmpty() || password.isEmpty() || 
            confirmPassword.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            System.out.println("All fields must be filled!");
            return;
        }

        if (!email.endsWith("@gmail.com")) {
            System.out.println("Email must end with @gmail.com");
            return;
        }

        if (User.emailExists(email)) {
            System.out.println("Email already exists!");
            return;
        }

        if (password.length() < 6) {
            System.out.println("Password minimum length is 6 characters!");
            return;
        }

        if (!password.equals(confirmPassword)) {
            System.out.println("Confirm password must match password!");
            return;
        }

        if (!isNumeric(phone)) {
            System.out.println("Phone number must be numeric!");
            return;
        }

        if (phone.length() < 10 || phone.length() > 13) {
            System.out.println("Phone must be 10–13 digits");
            return;
        }

        String idUser = "USR" + System.currentTimeMillis();

        Customer customer = new Customer(idUser, fullName, email, password, phone, address, 0.0);

        if (customer.save()) {
            System.out.println("Registration successful! Your User ID: " + idUser);
        } else {
            System.out.println("Registration failed! Please try again.");
        }
    }

    // Top up balance cust
    public void topUpBalance(Customer customer, String amountStr) {
        if (amountStr == null || amountStr.isEmpty()) {
            System.out.println("Amount cannot be empty!");
            return;
        }

        double amount = 0;
        try {
            amount = Double.parseDouble(amountStr);
        } catch (NumberFormatException e) {
            System.out.println("Amount must be numeric!");
            return;
        }

        if (amount < 10000) {
            System.out.println("Minimum top up amount is 10000!");
            return;
        }

        customer.topUpBalance(amount);
        if (customer.updateBalance(customer.getBalance())) {
            System.out.println("Balance topped up successfully!");
        } else {
            System.out.println("Failed to top up balance!");
        }
    }

    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }
}
