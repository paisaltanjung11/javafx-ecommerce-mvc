package view;

import controller.CartItemHandler;
import controller.DeliveryHandler;
import controller.OrderHandler;
import controller.ProductHandler;
import controller.PromoHandler;
import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Admin;
import model.Courier;
import model.Customer;
import utils.StageManager;

public class HomeView {
    
    private UserHandler userHandler = new UserHandler();
    private CartItemHandler cartHandler = new CartItemHandler();
    private ProductHandler productHandler = new ProductHandler();
    private PromoHandler promoHandler = new PromoHandler();
    private OrderHandler orderHandler = new OrderHandler();
    private DeliveryHandler deliveryHandler = new DeliveryHandler();
    
    public HomeView() {
        deliveryHandler.setUserHandler(userHandler);
    }
    
    public void showCustomerMenu(Customer customer) {
        Stage stage = StageManager.getInstance().getPrimaryStage();
        
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

        topUpBtn.setOnAction(e -> {
            TopUpView topUpView = new TopUpView();
            topUpView.show(customer, this);
        });
        
        viewProductsBtn.setOnAction(e -> {
            ProductWindow productWindow = new ProductWindow();
            productWindow.show(customer);
        });
        
        viewCartBtn.setOnAction(e -> {
            CartItemWindow cartItemWindow = new CartItemWindow();
            cartItemWindow.show(customer, this);
        });
        
        checkoutBtn.setOnAction(e -> {
            CheckoutView checkoutView = new CheckoutView();
            checkoutView.show(customer, this);
        });
        
        viewOrderHistoryBtn.setOnAction(e -> {
            OrderHistoryWindow orderHistoryWindow = new OrderHistoryWindow();
            orderHistoryWindow.show(customer);
        });
        
        editProfileBtn.setOnAction(e -> {
            UserWindow userWindow = new UserWindow();
            userWindow.show(customer, this);
        });
        
        logoutBtn.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.show();
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, balanceLabel, topUpBtn, viewProductsBtn, viewCartBtn, 
                                checkoutBtn, viewOrderHistoryBtn, editProfileBtn, logoutBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 700);
        stage.setScene(scene);
    }

    public void showAdminMenu() {
        Stage stage = StageManager.getInstance().getPrimaryStage();
        
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

        editStockBtn.setOnAction(e -> {
            EditStockView editStockView = new EditStockView();
            editStockView.show();
        });
        
        viewOrdersBtn.setOnAction(e -> {
            AllOrdersWindow allOrdersWindow = new AllOrdersWindow();
            allOrdersWindow.show();
        });
        
        viewCouriersBtn.setOnAction(e -> {
            AllCouriersWindow allCouriersWindow = new AllCouriersWindow();
            allCouriersWindow.show();
        });
        
        assignCourierBtn.setOnAction(e -> {
            AssignCourierWindow assignCourierWindow = new AssignCourierWindow();
            assignCourierWindow.show();
        });
        
        logoutBtn.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.show();
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, editStockBtn, viewOrdersBtn, viewCouriersBtn, assignCourierBtn, logoutBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 500);
        stage.setScene(scene);
    }

    public void showCourierMenu(Courier courier) {
        Stage stage = StageManager.getInstance().getPrimaryStage();
        
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

        viewDeliveriesBtn.setOnAction(e -> {
            AssignedDeliveriesWindow assignedDeliveriesWindow = new AssignedDeliveriesWindow();
            assignedDeliveriesWindow.show(courier);
        });
        
        updateStatusBtn.setOnAction(e -> {
            UpdateDeliveryStatusView updateDeliveryStatusView = new UpdateDeliveryStatusView();
            updateDeliveryStatusView.show(courier);
        });
        
        logoutBtn.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.show();
        });

        ScrollPane scrollPane = new ScrollPane();
        VBox content = new VBox(10, title, viewDeliveriesBtn, updateStatusBtn, logoutBtn);
        content.setAlignment(Pos.CENTER);
        scrollPane.setContent(content);
        scrollPane.setFitToWidth(true);

        root.getChildren().add(scrollPane);
        Scene scene = new Scene(root, 500, 400);
        stage.setScene(scene);
    }
    
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
}

