package controller;

import model.Delivery;
import model.User;
import model.OrderHeader;
import java.util.ArrayList;

// Handler untuk delivery (assign courier, update status)
public class DeliveryHandler {
    private UserHandler userHandler;

    public DeliveryHandler() {
    }

    public void setUserHandler(UserHandler userHandler) {
        this.userHandler = userHandler;
    }

    // Assign courier ke order
    public void assignCourier(String idOrder, String idCourier) {
        User courier = userHandler.getUserById(idCourier);
        if (courier == null || !courier.getRole().equals("COURIER")) {
            System.out.println("Courier not found!");
            return;
        }

        OrderHeader order = OrderHeader.getOrderById(idOrder);
        if (order == null) {
            System.out.println("Order not found!");
            return;
        }

        if (!order.getStatus().equals("PAID")) {
            System.out.println("Order is not ready for delivery! Status: " + order.getStatus());
            return;
        }

        Delivery delivery = new Delivery(idOrder, idCourier, "Pending");
        if (delivery.save()) {
        System.out.println("Courier assigned!");
        } else {
            System.out.println("Failed to assign courier!");
        }
    }

    // Update status delivery
    public void editDeliveryStatus(String idOrder, String status) {
        if (!isValidStatus(status)) {
            System.out.println("Invalid status! Allowed statuses: Pending, In Progress, Delivered");
            return;
        }

        Delivery delivery = Delivery.getDeliveryByOrder(idOrder);
        if (delivery == null) {
            System.out.println("Delivery not found!");
                return;
            }

        delivery.editStatus(status);
        if (delivery.updateStatus()) {
            System.out.println("Delivery status updated!");
        } else {
            System.out.println("Failed to update delivery status!");
        }
    }

    private boolean isValidStatus(String status) {
        return status.equals("Pending") || status.equals("In Progress") || status.equals("Delivered");
    }

    public ArrayList<Delivery> getDeliveriesByCourier(String idCourier) {
        return Delivery.getDeliveriesByCourier(idCourier);
    }

    public Delivery getDeliveryByOrder(String idOrder) {
        return Delivery.getDeliveryByOrder(idOrder);
    }
}
