package view;

import controller.*;
import model.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;


public class MainView extends Application {

    private static UserHandler userHandler = new UserHandler();
    private static ProductHandler productHandler = new ProductHandler();
    private static CartItemHandler cartHandler = new CartItemHandler();
    private static PromoHandler promoHandler = new PromoHandler();
    private static OrderHandler orderHandler = new OrderHandler();
    private static DeliveryHandler deliveryHandler = new DeliveryHandler();
    private static User currentUser;

    static {
        deliveryHandler.setUserHandler(userHandler);
    }

    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginScene();
        primaryStage.setTitle("JoymarKet - Digital Marketplace");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
    private void showLoginScene() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("JoymarKet");
        title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold;");

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(250);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(250);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(250);
        applySimpleButtonStyle(loginBtn);

        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(250);
        applySimpleButtonStyle(registerBtn);

        loginBtn.setOnAction(e -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            User user = userHandler.getUser(email, password);
            if (user == null) {
                errorLabel.setText("Invalid credentials!");
                return;
            }

            currentUser = user;
            switch (user.getRole()) {
                case "CUSTOMER":
                    showCustomerMenu((Customer) user);
                    break;
                case "ADMIN":
                    showAdminMenu();
                    break;
                case "COURIER":
                    showCourierMenu((Courier) user);
                    break;
            }
        });

        registerBtn.setOnAction(e -> showRegisterScene());

        root.getChildren().addAll(title, emailField, passwordField, errorLabel, loginBtn, registerBtn);
        Scene scene = new Scene(root, 400, 400);
        primaryStage.setScene(scene);
    }

    private void showRegisterScene() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("Register Account");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField fullNameField = new TextField();
        fullNameField.setPromptText("Full Name");
        fullNameField.setPrefWidth(300);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setPrefWidth(300);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(300);

        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm Password");
        confirmPasswordField.setPrefWidth(300);

        TextField phoneField = new TextField();
        phoneField.setPromptText("Phone");
        phoneField.setPrefWidth(300);

        TextField addressField = new TextField();
        addressField.setPromptText("Address");
        addressField.setPrefWidth(300);

        Label errorLabel = new Label();
        errorLabel.setStyle("-fx-text-fill: red;");
        errorLabel.setWrapText(true);

        Button registerBtn = new Button("Register");
        registerBtn.setPrefWidth(300);
        applySimpleButtonStyle(registerBtn);

        Button backBtn = new Button("Back");
        backBtn.setPrefWidth(300);
        applySimpleButtonStyle(backBtn);

        registerBtn.setOnAction(e -> {
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String phone = phoneField.getText();
            String address = addressField.getText();

            System.out.println("Registering...");
            userHandler.registerAccount(fullName, email, password, confirmPassword, phone, address);
            errorLabel.setText("Registration submitted. Check console for result.");
        });

        backBtn.setOnAction(e -> showLoginScene());

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, fullNameField, emailField, passwordField, confirmPasswordField, 
                                phoneField, addressField, errorLabel, registerBtn, backBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 600);
        primaryStage.setScene(scene);
    }

    private void showCustomerMenu(Customer customer) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("Customer Menu - " + customer.getFullName());
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label balanceLabel = new Label("Balance: Rp " + String.format("%.0f", customer.getBalance()));
        balanceLabel.setStyle("-fx-font-size: 16px;");

        Button topUpBtn = new Button("Top Up Balance");
        Button viewProductsBtn = new Button("View Products");
        Button viewCartBtn = new Button("View Cart");
        Button checkoutBtn = new Button("Checkout");
        Button viewOrderHistoryBtn = new Button("View Order History");
        Button editProfileBtn = new Button("Edit Profile");
        Button logoutBtn = new Button("Logout");

        VBox.setMargin(topUpBtn, new Insets(5));
        VBox.setMargin(viewProductsBtn, new Insets(5));
        VBox.setMargin(viewCartBtn, new Insets(5));
        VBox.setMargin(checkoutBtn, new Insets(5));
        VBox.setMargin(viewOrderHistoryBtn, new Insets(5));
        VBox.setMargin(editProfileBtn, new Insets(5));
        VBox.setMargin(logoutBtn, new Insets(5));

        topUpBtn.setPrefWidth(300);
        viewProductsBtn.setPrefWidth(300);
        viewCartBtn.setPrefWidth(300);
        checkoutBtn.setPrefWidth(300);
        viewOrderHistoryBtn.setPrefWidth(300);
        editProfileBtn.setPrefWidth(300);
        logoutBtn.setPrefWidth(300);

        applySimpleButtonStyle(topUpBtn);
        applySimpleButtonStyle(viewProductsBtn);
        applySimpleButtonStyle(viewCartBtn);
        applySimpleButtonStyle(checkoutBtn);
        applySimpleButtonStyle(viewOrderHistoryBtn);
        applySimpleButtonStyle(editProfileBtn);
        applySimpleButtonStyle(logoutBtn);

        topUpBtn.setOnAction(e -> showTopUpDialog(customer));
        viewProductsBtn.setOnAction(e -> showProductsList(customer));
        viewCartBtn.setOnAction(e -> showCart(customer));
        checkoutBtn.setOnAction(e -> showCheckoutDialog(customer));
        viewOrderHistoryBtn.setOnAction(e -> showOrderHistory(customer));
        editProfileBtn.setOnAction(e -> showEditProfileDialog(customer));
        logoutBtn.setOnAction(e -> showLoginScene());

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, balanceLabel, topUpBtn, viewProductsBtn, viewCartBtn, 
                                checkoutBtn, viewOrderHistoryBtn, editProfileBtn, logoutBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 700);
        primaryStage.setScene(scene);
    }

    private void showAdminMenu() {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("Admin Menu");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button editStockBtn = new Button("Edit Product Stock");
        Button viewOrdersBtn = new Button("View All Orders");
        Button viewCouriersBtn = new Button("View All Couriers");
        Button assignCourierBtn = new Button("Assign Courier");
        Button logoutBtn = new Button("Logout");

        VBox.setMargin(editStockBtn, new Insets(5));
        VBox.setMargin(viewOrdersBtn, new Insets(5));
        VBox.setMargin(viewCouriersBtn, new Insets(5));
        VBox.setMargin(assignCourierBtn, new Insets(5));
        VBox.setMargin(logoutBtn, new Insets(5));

        editStockBtn.setPrefWidth(300);
        viewOrdersBtn.setPrefWidth(300);
        viewCouriersBtn.setPrefWidth(300);
        assignCourierBtn.setPrefWidth(300);
        logoutBtn.setPrefWidth(300);

        applySimpleButtonStyle(editStockBtn);
        applySimpleButtonStyle(viewOrdersBtn);
        applySimpleButtonStyle(viewCouriersBtn);
        applySimpleButtonStyle(assignCourierBtn);
        applySimpleButtonStyle(logoutBtn);

        editStockBtn.setOnAction(e -> showEditStockDialog());
        viewOrdersBtn.setOnAction(e -> showAllOrders());
        viewCouriersBtn.setOnAction(e -> showAllCouriers());
        assignCourierBtn.setOnAction(e -> showAssignCourierDialog());
        logoutBtn.setOnAction(e -> showLoginScene());

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, editStockBtn, viewOrdersBtn, viewCouriersBtn, assignCourierBtn, logoutBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 500);
        primaryStage.setScene(scene);
    }

    private void showCourierMenu(Courier courier) {
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: #f5f5f5;");

        Label title = new Label("Courier Menu - " + courier.getFullName());
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button viewDeliveriesBtn = new Button("View Assigned Deliveries");
        Button updateStatusBtn = new Button("Update Delivery Status");
        Button logoutBtn = new Button("Logout");

        VBox.setMargin(viewDeliveriesBtn, new Insets(5));
        VBox.setMargin(updateStatusBtn, new Insets(5));
        VBox.setMargin(logoutBtn, new Insets(5));

        viewDeliveriesBtn.setPrefWidth(300);
        updateStatusBtn.setPrefWidth(300);
        logoutBtn.setPrefWidth(300);

        applySimpleButtonStyle(viewDeliveriesBtn);
        applySimpleButtonStyle(updateStatusBtn);
        applySimpleButtonStyle(logoutBtn);

        viewDeliveriesBtn.setOnAction(e -> showAssignedDeliveries(courier));
        updateStatusBtn.setOnAction(e -> showUpdateDeliveryStatusDialog(courier));
        logoutBtn.setOnAction(e -> showLoginScene());

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, viewDeliveriesBtn, updateStatusBtn, logoutBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 400);
        primaryStage.setScene(scene);
    }

    private void showTopUpDialog(Customer customer) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Top Up Balance");
        dialog.setHeaderText("Enter amount to top up (minimum: 10000)");

        TextField amountField = new TextField();
        amountField.setPromptText("Amount");

        VBox vbox = new VBox(10, new Label("Amount:"), amountField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return amountField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(amount -> {
            userHandler.topUpBalance(customer, amount);
            Customer updatedCustomer = (Customer) userHandler.getUserById(customer.getIdUser());
            if (updatedCustomer != null) {
                showCustomerMenu(updatedCustomer);
            }
        });
    }

    private void showProductsList(Customer customer) {
        Stage stage = new Stage();
        stage.setTitle("Products");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Available Products");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<Product> products = productHandler.getAllProducts();
        VBox productsBox = new VBox(10);
        
        for (Product p : products) {
            HBox productRow = new HBox(10);
            productRow.setPadding(new Insets(5));
            productRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");
            
            VBox productInfo = new VBox(5);
            productInfo.getChildren().addAll(
                new Label(p.getName()),
                new Label("Rp " + String.format("%.0f", p.getPrice())),
                new Label("Stock: " + p.getStock())
            );
            
            Button addBtn = new Button("Add to Cart");
            applySimpleButtonStyle(addBtn);
            addBtn.setOnAction(e -> {
                stage.close();
                showAddToCartQuantityDialog(customer, p);
            });
            
            productRow.getChildren().addAll(productInfo, addBtn);
            HBox.setHgrow(productInfo, javafx.scene.layout.Priority.ALWAYS);
            productsBox.getChildren().add(productRow);
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(productsBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showAddToCartQuantityDialog(Customer customer, Product product) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Add to Cart");
        dialog.setHeaderText("Quantity for " + product.getName() + "\nPrice: Rp " + String.format("%.0f", product.getPrice()) + 
                            " | Stock: " + product.getStock());

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        VBox vbox = new VBox(10, new Label("Quantity:"), quantityField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return quantityField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(qtyStr -> {
            try {
                int qty = Integer.parseInt(qtyStr);
                cartHandler.createCartItem(customer.getIdUser(), product, qty);
                showAlert("Product added to cart!");
            } catch (NumberFormatException e) {
                showAlert("Invalid quantity!");
            }
        });
    }

    private void showCart(Customer customer) {
        Stage stage = new Stage();
        stage.setTitle("Shopping Cart");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Shopping Cart");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<CartItem> cartItems = cartHandler.getCartItems(customer.getIdUser());
        VBox cartBox = new VBox(10);

        if (cartItems.isEmpty()) {
            cartBox.getChildren().add(new Label("Cart is empty!"));
        } else {
            double total = 0;
            for (CartItem item : cartItems) {
                Product p = productHandler.getProduct(item.getIdProduct());
                if (p != null) {
                    double itemTotal = p.getPrice() * item.getCount();
                    total += itemTotal;

                    HBox itemRow = new HBox(10);
                    itemRow.setPadding(new Insets(5));
                    itemRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");

                    VBox itemInfo = new VBox(5);
                    itemInfo.getChildren().addAll(
                        new Label(p.getName()),
                        new Label("Rp " + String.format("%.0f", p.getPrice()) + " x " + item.getCount()),
                        new Label("Subtotal: Rp " + String.format("%.0f", itemTotal))
                    );

                    Button updateBtn = new Button("Update");
                    applySimpleButtonStyle(updateBtn);
                    Stage cartStage = stage;
                    Product selectedProduct = p;
                    int selectedQty = item.getCount();
                    updateBtn.setOnAction(e -> {
                        cartStage.close();
                        showUpdateCartQuantityDialog(customer, selectedProduct, selectedQty);
                    });

                    Button removeBtn = new Button("Remove");
                    applySimpleButtonStyle(removeBtn);
                    removeBtn.setOnAction(e -> {
                        cartHandler.removeCartItem(customer.getIdUser(), item.getIdProduct());
                        stage.close();
                        showCart(customer);
                    });

                    itemRow.getChildren().addAll(itemInfo, updateBtn, removeBtn);
                    HBox.setHgrow(itemInfo, javafx.scene.layout.Priority.ALWAYS);
                    cartBox.getChildren().add(itemRow);
                }
            }

            Label totalLabel = new Label("Total: Rp " + String.format("%.0f", total));
            totalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
            cartBox.getChildren().add(totalLabel);
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(cartBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showUpdateCartQuantityDialog(Customer customer, Product product, int currentQty) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Cart");
        dialog.setHeaderText("Update quantity for " + product.getName() + 
                            "\nCurrent: " + currentQty + " | Stock: " + product.getStock());

        TextField quantityField = new TextField(String.valueOf(currentQty));
        quantityField.setPromptText("New Quantity");

        VBox vbox = new VBox(10, new Label("New Quantity:"), quantityField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return quantityField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(qtyStr -> {
            try {
                int qty = Integer.parseInt(qtyStr);
                cartHandler.editCartItem(customer.getIdUser(), product, qty);
                showAlert("Cart updated!");
                showCart(customer);
            } catch (NumberFormatException e) {
                showAlert("Invalid quantity!");
                showCart(customer);
            }
        });
    }

    private void showCheckoutDialog(Customer customer) {
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
                    showCustomerMenu(updatedCustomer);
                }
            }
        });
    }

    private void showOrderHistory(Customer customer) {
        Stage stage = new Stage();
        stage.setTitle("Order History");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Order History");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<OrderHeader> orders = orderHandler.getOrdersByCustomer(customer.getIdUser());
        VBox ordersBox = new VBox(10);

        if (orders.isEmpty()) {
            ordersBox.getChildren().add(new Label("No orders found!"));
        } else {
            for (OrderHeader order : orders) {
                VBox orderBox = new VBox(5);
                orderBox.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
                
                orderBox.getChildren().addAll(
                    new Label("Order ID: " + order.getIdOrder()),
                    new Label("Date: " + order.getOrderedAt()),
                    new Label("Status: " + order.getStatus()),
                    new Label("Total: Rp " + String.format("%.0f", order.getTotalAmount()))
                );
                
                ordersBox.getChildren().add(orderBox);
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ordersBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showEditProfileDialog(Customer customer) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle("Edit Profile");
        dialog.setHeaderText("Update your profile information");

        TextField fullNameField = new TextField(customer.getFullName());
        TextField phoneField = new TextField(customer.getPhone());
        TextField addressField = new TextField(customer.getAddress());

        VBox vbox = new VBox(10, 
            new Label("Full Name:"), fullNameField,
            new Label("Phone:"), phoneField,
            new Label("Address:"), addressField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return new String[]{fullNameField.getText(), phoneField.getText(), addressField.getText()};
            }
            return null;
        });

        dialog.showAndWait().ifPresent(result -> {
            userHandler.editProfile(customer, result[0], result[1], result[2]);
            Customer updatedCustomer = (Customer) userHandler.getUserById(customer.getIdUser());
            if (updatedCustomer != null) {
                showCustomerMenu(updatedCustomer);
            }
        });
    }

    private void showEditStockDialog() {
        Stage stage = new Stage();
        stage.setTitle("Edit Product Stock");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Edit Product Stock");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<Product> products = productHandler.getAllProducts();
        VBox productsBox = new VBox(10);
        
        for (Product p : products) {
            HBox productRow = new HBox(10);
            productRow.setPadding(new Insets(5));
            productRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");
            
            VBox productInfo = new VBox(5);
            productInfo.getChildren().addAll(
                new Label(p.getName()),
                new Label("Rp " + String.format("%.0f", p.getPrice())),
                new Label("Current Stock: " + p.getStock())
            );
            
            Button editBtn = new Button("Edit Stock");
            applySimpleButtonStyle(editBtn);
            editBtn.setOnAction(e -> {
                stage.close();
                showEditStockQuantityDialog(p);
            });
            
            productRow.getChildren().addAll(productInfo, editBtn);
            HBox.setHgrow(productInfo, javafx.scene.layout.Priority.ALWAYS);
            productsBox.getChildren().add(productRow);
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(productsBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showEditStockQuantityDialog(Product product) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Edit Product Stock");
        dialog.setHeaderText("Update stock for " + product.getName() + 
                            "\nCurrent Stock: " + product.getStock());

        TextField stockField = new TextField(String.valueOf(product.getStock()));
        stockField.setPromptText("New Stock");

        VBox vbox = new VBox(10, new Label("New Stock:"), stockField);
        vbox.setPadding(new Insets(20));
        dialog.getDialogPane().setContent(vbox);

        ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButtonType) {
                return stockField.getText();
            }
            return null;
        });

        dialog.showAndWait().ifPresent(stockStr -> {
            try {
                int stock = Integer.parseInt(stockStr);
                productHandler.editProductStock(product.getIdProduct(), stock);
                showAlert("Stock updated!");
            } catch (NumberFormatException e) {
                showAlert("Invalid stock value!");
            }
        });
    }

    private void showAllOrders() {
        Stage stage = new Stage();
        stage.setTitle("All Orders");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("All Orders");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<OrderHeader> orders = orderHandler.getAllOrders();
        VBox ordersBox = new VBox(10);

        if (orders.isEmpty()) {
            ordersBox.getChildren().add(new Label("No orders found!"));
        } else {
            for (OrderHeader order : orders) {
                HBox orderRow = new HBox(10);
                orderRow.setPadding(new Insets(5));
                orderRow.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
                
                User customer = userHandler.getUserById(order.getIdCustomer());
                String customerName = customer != null ? customer.getFullName() : "Unknown";
                
                VBox orderInfo = new VBox(5);
                orderInfo.getChildren().addAll(
                    new Label("Order ID: " + order.getIdOrder()),
                    new Label("Customer: " + customerName),
                    new Label("Date: " + order.getOrderedAt()),
                    new Label("Status: " + order.getStatus()),
                    new Label("Total: Rp " + String.format("%.0f", order.getTotalAmount()))
                );
                
                orderRow.getChildren().add(orderInfo);
                HBox.setHgrow(orderInfo, javafx.scene.layout.Priority.ALWAYS);
                ordersBox.getChildren().add(orderRow);
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ordersBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 700, 600);
        stage.setScene(scene);
        stage.show();
    }

    private void showAllCouriers() {
        Stage stage = new Stage();
        stage.setTitle("All Couriers");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("All Couriers");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<User> couriers = userHandler.getAllCouriers();
        VBox couriersBox = new VBox(10);

        if (couriers.isEmpty()) {
            couriersBox.getChildren().add(new Label("No couriers found!"));
        } else {
            for (User courier : couriers) {
                if (courier instanceof Courier) {
                    Courier c = (Courier) courier;
                    VBox courierBox = new VBox(5);
                    courierBox.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
                    
                    courierBox.getChildren().addAll(
                        new Label("ID: " + c.getIdUser()),
                        new Label("Name: " + c.getFullName()),
                        new Label("Email: " + c.getEmail()),
                        new Label("Phone: " + c.getPhone()),
                        new Label("Vehicle: " + c.getVehicleType() + " - " + c.getVehiclePlate())
                    );
                    
                    couriersBox.getChildren().add(courierBox);
                }
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(couriersBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showAssignCourierDialog() {
        Stage stage = new Stage();
        stage.setTitle("Assign Courier to Order");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Select Order to Assign Courier");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<OrderHeader> orders = orderHandler.getAllOrders();
        VBox ordersBox = new VBox(10);

        if (orders.isEmpty()) {
            ordersBox.getChildren().add(new Label("No orders found!"));
        } else {
            for (OrderHeader order : orders) {
                Delivery existingDelivery = deliveryHandler.getDeliveryByOrder(order.getIdOrder());
                if (existingDelivery != null) {
                    continue;
                }

                HBox orderRow = new HBox(10);
                orderRow.setPadding(new Insets(5));
                orderRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");
                
                User customer = userHandler.getUserById(order.getIdCustomer());
                String customerName = customer != null ? customer.getFullName() : "Unknown";
                
                VBox orderInfo = new VBox(5);
                orderInfo.getChildren().addAll(
                    new Label("Order ID: " + order.getIdOrder()),
                    new Label("Customer: " + customerName),
                    new Label("Date: " + order.getOrderedAt()),
                    new Label("Total: Rp " + String.format("%.0f", order.getTotalAmount()))
                );
                
                Button assignBtn = new Button("Assign Courier");
                applySimpleButtonStyle(assignBtn);
                assignBtn.setOnAction(e -> {
                    stage.close();
                    showCourierSelectionDialog(order);
                });
                
                orderRow.getChildren().addAll(orderInfo, assignBtn);
                HBox.setHgrow(orderInfo, javafx.scene.layout.Priority.ALWAYS);
                ordersBox.getChildren().add(orderRow);
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(ordersBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showCourierSelectionDialog(OrderHeader order) {
        Stage stage = new Stage();
        stage.setTitle("Select Courier");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Select Courier for Order: " + order.getIdOrder());
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<User> couriers = userHandler.getAllCouriers();
        VBox couriersBox = new VBox(10);

        if (couriers.isEmpty()) {
            couriersBox.getChildren().add(new Label("No couriers available!"));
        } else {
            for (User courier : couriers) {
                if (courier instanceof Courier) {
                    Courier c = (Courier) courier;
                    
                    HBox courierRow = new HBox(10);
                    courierRow.setPadding(new Insets(5));
                    courierRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");
                    
                    VBox courierInfo = new VBox(5);
                    courierInfo.getChildren().addAll(
                        new Label(c.getFullName()),
                        new Label("Phone: " + c.getPhone()),
                        new Label("Vehicle: " + c.getVehicleType() + " - " + c.getVehiclePlate())
                    );
                    
                    Button selectBtn = new Button("Select");
                    applySimpleButtonStyle(selectBtn);
                    selectBtn.setOnAction(e -> {
                        deliveryHandler.assignCourier(order.getIdOrder(), c.getIdUser());
                        stage.close();
                        showAlert("Courier assigned successfully!");
                    });
                    
                    courierRow.getChildren().addAll(courierInfo, selectBtn);
                    HBox.setHgrow(courierInfo, javafx.scene.layout.Priority.ALWAYS);
                    couriersBox.getChildren().add(courierRow);
                }
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(couriersBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showAssignedDeliveries(Courier courier) {
        Stage stage = new Stage();
        stage.setTitle("Assigned Deliveries");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Assigned Deliveries");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<Delivery> deliveries = deliveryHandler.getDeliveriesByCourier(courier.getIdUser());
        VBox deliveriesBox = new VBox(10);

        if (deliveries.isEmpty()) {
            deliveriesBox.getChildren().add(new Label("No deliveries assigned!"));
            } else {
            for (Delivery delivery : deliveries) {
                OrderHeader order = orderHandler.getOrderById(delivery.getIdOrder());
                if (order != null) {
                    HBox deliveryRow = new HBox(10);
                    deliveryRow.setPadding(new Insets(5));
                    deliveryRow.setStyle("-fx-border-color: #ccc; -fx-border-width: 1; -fx-padding: 10;");
                    
                    User customer = userHandler.getUserById(order.getIdCustomer());
                    String customerName = customer != null ? customer.getFullName() : "Unknown";
                    
                    VBox deliveryInfo = new VBox(5);
                    deliveryInfo.getChildren().addAll(
                        new Label("Order ID: " + delivery.getIdOrder()),
                        new Label("Customer: " + customerName),
                        new Label("Status: " + delivery.getStatus()),
                        new Label("Total: Rp " + String.format("%.0f", order.getTotalAmount()))
                    );
                    
                    deliveryRow.getChildren().add(deliveryInfo);
                    HBox.setHgrow(deliveryInfo, javafx.scene.layout.Priority.ALWAYS);
                    deliveriesBox.getChildren().add(deliveryRow);
                }
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(deliveriesBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showUpdateDeliveryStatusDialog(Courier courier) {
        Stage stage = new Stage();
        stage.setTitle("Update Delivery Status");

        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Update Delivery Status");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ArrayList<Delivery> deliveries = deliveryHandler.getDeliveriesByCourier(courier.getIdUser());
        VBox deliveriesBox = new VBox(10);

        if (deliveries.isEmpty()) {
            deliveriesBox.getChildren().add(new Label("No deliveries assigned!"));
        } else {
            for (Delivery delivery : deliveries) {
                OrderHeader order = orderHandler.getOrderById(delivery.getIdOrder());
                if (order != null) {
                    HBox deliveryRow = new HBox(10);
                    deliveryRow.setPadding(new Insets(5));
                    deliveryRow.setStyle("-fx-border-color: #ddd; -fx-border-width: 1; -fx-padding: 10;");
                    
                    User customer = userHandler.getUserById(order.getIdCustomer());
                    String customerName = customer != null ? customer.getFullName() : "Unknown";
                    
                    VBox deliveryInfo = new VBox(5);
                    deliveryInfo.getChildren().addAll(
                        new Label("Order ID: " + delivery.getIdOrder()),
                        new Label("Customer: " + customerName),
                        new Label("Current Status: " + delivery.getStatus()),
                        new Label("Total: Rp " + String.format("%.0f", order.getTotalAmount()))
                    );
                    
                    Button updateBtn = new Button("Update Status");
                    updateBtn.setOnAction(e -> {
                        stage.close();
                        showDeliveryStatusSelectionDialog(delivery);
                    });
                    
                    deliveryRow.getChildren().addAll(deliveryInfo, updateBtn);
                    HBox.setHgrow(deliveryInfo, javafx.scene.layout.Priority.ALWAYS);
                    deliveriesBox.getChildren().add(deliveryRow);
                }
            }
        }

        Button closeBtn = new Button("Close");
        applySimpleButtonStyle(closeBtn);
        closeBtn.setOnAction(e -> stage.close());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(deliveriesBox);
        scrollPane.setFitToWidth(true);

        root.getChildren().addAll(title, scrollPane, closeBtn);
        Scene scene = new Scene(root, 600, 500);
        stage.setScene(scene);
        stage.show();
    }

    private void showDeliveryStatusSelectionDialog(Delivery delivery) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Update Delivery Status");
        dialog.setHeaderText("Select new status for Order: " + delivery.getIdOrder() + 
                            "\nCurrent Status: " + delivery.getStatus());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(20));

        Label label = new Label("Select Status:");
        Button pendingBtn = new Button("Pending");
        Button inProgressBtn = new Button("In Progress");
        Button deliveredBtn = new Button("Delivered");

        pendingBtn.setPrefWidth(200);
        inProgressBtn.setPrefWidth(200);
        deliveredBtn.setPrefWidth(200);

        applySimpleButtonStyle(pendingBtn);
        applySimpleButtonStyle(inProgressBtn);
        applySimpleButtonStyle(deliveredBtn);

        pendingBtn.setOnAction(e -> {
            dialog.setResult("Pending");
            dialog.close();
        });

        inProgressBtn.setOnAction(e -> {
            dialog.setResult("In Progress");
            dialog.close();
        });

        deliveredBtn.setOnAction(e -> {
            dialog.setResult("Delivered");
            dialog.close();
        });

        vbox.getChildren().addAll(label, pendingBtn, inProgressBtn, deliveredBtn);
        dialog.getDialogPane().setContent(vbox);

        ButtonType cancelButtonType = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().add(cancelButtonType);

        dialog.showAndWait().ifPresent(status -> {
            if (status != null && !status.isEmpty()) {
                deliveryHandler.editDeliveryStatus(delivery.getIdOrder(), status);
                showAlert("Delivery status updated!");
            }
        });
    }

    // Styling button simple
    private void applySimpleButtonStyle(Button button) {
        button.setStyle("-fx-background-color: white; " +
                       "-fx-text-fill: black; " +
                       "-fx-border-color: #ccc; " +
                       "-fx-border-width: 1; " +
                       "-fx-font-size: 14px; " +
                       "-fx-padding: 8px 16px; " +
                       "-fx-cursor: hand;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #f5f5f5; " +
                                                      "-fx-text-fill: black; " +
                                                      "-fx-border-color: #999; " +
                                                      "-fx-border-width: 1; " +
                                                      "-fx-font-size: 14px; " +
                                                      "-fx-padding: 8px 16px; " +
                                                      "-fx-cursor: hand;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: white; " +
                                                     "-fx-text-fill: black; " +
                                                     "-fx-border-color: #ccc; " +
                                                     "-fx-border-width: 1; " +
                                                     "-fx-font-size: 14px; " +
                                                     "-fx-padding: 8px 16px; " +
                                                     "-fx-cursor: hand;"));
    }

    //  alert dialog
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
