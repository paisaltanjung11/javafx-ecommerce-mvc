package controller;

import model.*;
import java.util.ArrayList;
import java.util.Date;

// Handler untuk order (checkout, get orders)
public class OrderHandler {

    // Checkout cart menjadi order
    public String checkout(Customer customer,
                         ArrayList<CartItem> cart,
                         Promo promo,
                         double total) {

        if (promo != null) {
            total -= total * (promo.getDiscountPercentage() / 100);
        }

        if (customer.getBalance() < total) {
            double shortage = total - customer.getBalance();
            return "Insufficient balance! Your balance: Rp " + String.format("%.0f", customer.getBalance()) + 
                   ", Total: Rp " + String.format("%.0f", total) + 
                   ". You need Rp " + String.format("%.0f", shortage) + " more.";
        }

        String orderId = "ORD" + System.currentTimeMillis();

        OrderHeader order = new OrderHeader(
                orderId,
                customer.getIdUser(),
                promo != null ? promo.getIdPromo() : null,
                "PAID",
                new Date(),
                total
        );

        if (!order.save()) {
            return "Failed to create order! Please try again.";
        }

        for (CartItem item : cart) {
            OrderDetail detail = new OrderDetail(orderId, item.getIdProduct(), item.getCount());
            if (!detail.save()) {
                return "Failed to save order detail! Please try again.";
            }

            Product product = Product.getProductById(item.getIdProduct());
            if (product != null) {
                product.editStock(product.getStock() - item.getCount());
                if (!product.updateStock()) {
                    return "Failed to update product stock! Please try again.";
                }
            }
        }

        customer.topUpBalance(-total);
        if (!customer.updateBalance(customer.getBalance())) {
            return "Failed to update customer balance! Please try again.";
        }

        return "Checkout successful! Order ID: " + orderId;
    }

    public ArrayList<OrderHeader> getAllOrders() {
        return OrderHeader.getAllOrders();
    }

    public ArrayList<OrderHeader> getOrdersByCustomer(String idCustomer) {
        return OrderHeader.getOrdersByCustomer(idCustomer);
    }

    public OrderHeader getOrderById(String idOrder) {
        return OrderHeader.getOrderById(idOrder);
    }
}
