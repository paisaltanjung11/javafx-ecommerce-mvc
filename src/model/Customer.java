package model;

// Customer dengan balance untuk transaksi
public class Customer extends User {
    private double balance;

    public Customer(String idUser, String fullName, String email, String password,
                    String phone, String address, double balance) {
        super(idUser, fullName, email, password, phone, address, "CUSTOMER");
        this.balance = balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() { return balance; }

    public void topUpBalance(double amount) {
        this.balance += amount;
    }
}
	