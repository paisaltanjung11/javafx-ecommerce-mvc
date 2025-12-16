package controller;

import model.Product;
import java.util.ArrayList;

// Handler untuk product
public class ProductHandler {

    public Product getProduct(String idProduct) {
        return Product.getProductById(idProduct);
    }

    public ArrayList<Product> getAllProducts() {
        return Product.getAllProducts();
    }

    public void editProductStock(String idProduct, int stock) {
        if (stock < 0) {
            System.out.println("Stock cannot be negative!");
            return;
        }

        Product p = getProduct(idProduct);
        if (p != null) {
            p.editStock(stock);
            if (p.updateStock()) {
            System.out.println("Stock updated!");
            } else {
                System.out.println("Failed to update stock!");
            }
        } else {
            System.out.println("Product not found!");
        }
    }

    public void addProduct(Product product) {
        if (product.save()) {
        } else {
            System.out.println("Failed to add product!");
        }
    }
}
