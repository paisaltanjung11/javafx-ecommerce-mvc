package view;

import controller.CartItemHandler;
import controller.OrderHandler;
import controller.ProductHandler;
import controller.PromoHandler;
import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import model.CartItem;
import model.Customer;
import model.Product;
import model.Promo;

import java.util.ArrayList;

public class CheckoutView {
    
    private CartItemHandler cartHandler = new CartItemHandler();
    private ProductHandler productHandler = new ProductHandler();
    private PromoHandler promoHandler = new PromoHandler();
    private OrderHandler orderHandler = new OrderHandler();
    private UserHandler userHandler = new UserHandler();
    
    public void show(Customer customer, HomeView homeView) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Checkout");
        dialog.setHeaderText("Enter promo code (optional, leave empty to skip)");

        TextField promoField = new TextField();
        promoField.setPromptText("Promo Code (optional)");

        VBox vbox = new VBox(10, new Label("Promo Code:"), promoField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return promoField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(promoCode -> {
            ArrayList<CartItem> cart = cartHandler.getCartItems(customer.getIdUser());
            if (cart.isEmpty()) {
                showAlert("Cart is empty!");
                return;
            }

            double total = 0;
            for (CartItem c : cart) {
                Product p = productHandler.getProduct(c.getIdProduct());
                total += p.getPrice() * c.getCount();
            }

            Promo promo = null;
            if (!promoCode.isEmpty()) {
                promo = promoHandler.getPromo(promoCode);
                if (promo == null) {
                    showAlert("Promo code not found!");
                    return;
                }
            }

            String result = orderHandler.checkout(customer, cart, promo, total);
            showAlert(result);
            
            if (result.contains("successful")) {
                cartHandler.clearCart(customer.getIdUser());
                Customer updatedCustomer = (Customer) userHandler.getUserById(customer.getIdUser());
                if (updatedCustomer != null) {
                    homeView.showCustomerMenu(updatedCustomer);
                }
            }
        });
    }
    
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

