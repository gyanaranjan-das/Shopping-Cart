import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

class Product {
    String name;
    double price;
    int quantity;

    Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    double getTotalPrice() {
        return price * quantity;
    }

    @Override
    public String toString() {
        return String.format("%-15s %5d x ₹%7.2f = ₹%7.2f", name, quantity, price, getTotalPrice());
    }
}

public class ShoppingCart {
    static final double GST_RATE = 0.18; // 18% GST

    public static void main(String[] args) {
        ArrayList<Product> cart = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        double discount = 0.0;

        while (true) {
            System.out.println("\n--- 🛒 Shopping Cart Menu ---");
            System.out.println("1. Add item");
            System.out.println("2. Remove item");
            System.out.println("3. View cart");
            System.out.println("4. Apply discount (%)");
            System.out.println("5. Checkout & Save Bill");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    System.out.print("Enter price per unit: ₹");
                    double price = sc.nextDouble();
                    if(quantity <= 0 || price <= 0){
                        System.out.println(" Quantity and Price must be positive!");
                        break;
                    }
                    cart.add(new Product(name, price, quantity));
                    System.out.println("Item added successfully!");
                    break;

                case 2:
                    if(cart.isEmpty()){
                        System.out.println("Cart is empty.");
                        break;
                    }
                    System.out.print("Enter item name to remove: ");
                    String removeName = sc.nextLine();
                    boolean found = false;
                    for (int i = 0; i < cart.size(); i++) {
                        if (cart.get(i).name.equalsIgnoreCase(removeName)) {
                            cart.remove(i);
                            System.out.println("🗑 Item removed.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println(" Item not found.");
                    break;

                case 3:
                    printCart(cart, discount);
                    break;

                case 4:
                    System.out.print("Enter discount percentage (0-100): ");
                    double discInput = sc.nextDouble();
                    if(discInput < 0 || discInput > 100){
                        System.out.println("Invalid discount value!");
                    } else {
                        discount = discInput / 100.0;
                        System.out.println("Discount of " + discInput + "% applied!");
                    }
                    break;

                case 5:
                    checkoutAndSaveBill(cart, discount);
                    cart.clear();
                    discount = 0.0;
                    break;

                case 6:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void printCart(ArrayList<Product> cart, double discount) {
        if(cart.isEmpty()){
            System.out.println("Cart is empty.");
            return;
        }
        System.out.printf("%-15s %10s %10s %12s%n", "Item", "Qty", "Price", "Total");
        double subtotal = 0;
        for(Product p : cart){
            System.out.println(p);
            subtotal += p.getTotalPrice();
        }
        System.out.println("----------------------------------------");
        System.out.printf("Subtotal: ₹%.2f%n", subtotal);

        double discountAmt = subtotal * discount;
        if(discountAmt > 0) {
            System.out.printf("Discount: -₹%.2f%n", discountAmt);
        }
        double gstAmt = (subtotal - discountAmt) * GST_RATE;
        System.out.printf("GST @18%%: ₹%.2f%n", gstAmt);

        double finalTotal = subtotal - discountAmt + gstAmt;
        System.out.printf("Grand Total: ₹%.2f%n", finalTotal);
    }

    static void checkoutAndSaveBill(ArrayList<Product> cart, double discount) {
        if(cart.isEmpty()){
            System.out.println("Cart is empty, nothing to checkout.");
            return;
        }
        try(FileWriter fw = new FileWriter("Bill.txt")){
            fw.write("========== Shopping Cart Bill ==========\n");
            fw.write(String.format("%-15s %10s %10s %12s%n", "Item", "Qty", "Price", "Total"));

            double subtotal = 0;
            for(Product p : cart){
                fw.write(p.toString() + "\n");
                subtotal += p.getTotalPrice();
            }
            fw.write("----------------------------------------\n");
            fw.write(String.format("Subtotal: ₹%.2f%n", subtotal));

            double discountAmt = subtotal * discount;
            if(discountAmt > 0) {
                fw.write(String.format("Discount: -₹%.2f%n", discountAmt));
            }

            double gstAmt = (subtotal - discountAmt) * GST_RATE;
            fw.write(String.format("GST @18%%: ₹%.2f%n", gstAmt));

            double finalTotal = subtotal - discountAmt + gstAmt;
            fw.write(String.format("Grand Total: ₹%.2f%n", finalTotal));
            fw.write("========================================\n");
            fw.write("Thank you for shopping!\n");
            System.out.println("Bill saved successfully to Bill.txt");
        } catch(IOException e){
            System.out.println("Error saving bill: " + e.getMessage());
        }
    }
}
