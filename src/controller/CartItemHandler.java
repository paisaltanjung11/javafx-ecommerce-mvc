package controller;

import model.CartItem;
import model.Product;
import java.util.ArrayList;

// Handler untuk cart item (add, update, remove)
public class CartItemHandler {

    // Tambah item ke cart
    public void createCartItem(String idCustomer, Product product, int count) {
        if (count < 1 || count > product.getStock()) {
            System.out.println("Invalid item count!");
            return;
        }

        CartItem cartItem = new CartItem(idCustomer, product.getIdProduct(), count);
        if (cartItem.save()) {
        System.out.println("Product added to cart");
        } else {
            System.out.println("Failed to add product to cart!");
        }
    }

    public void editCartItem(String idCustomer, Product product, int count) {
        if (count < 1 || count > product.getStock()) {
            System.out.println("Invalid item count!");
            return;
        }

        CartItem cartItem = new CartItem(idCustomer, product.getIdProduct(), count);
        if (cartItem.update()) {
                System.out.println("Cart updated!");
        } else {
            System.out.println("Failed to update cart!");
        }
    }

    public ArrayList<CartItem> getCartItems(String idCustomer) {
        return CartItem.getCartItemsByCustomer(idCustomer);
    }

    public void clearCart(String idCustomer) {
        if (CartItem.clearCart(idCustomer)) {
        } else {
            System.out.println("Failed to clear cart!");
        }
    }

    public void removeCartItem(String idCustomer, String idProduct) {
        CartItem cartItem = new CartItem(idCustomer, idProduct, 0);
        if (cartItem.delete()) {
            System.out.println("Item removed from cart!");
        } else {
            System.out.println("Failed to remove item from cart!");
        }
    }
}
