import java.util.ArrayList;
import java.util.Scanner;

class Item {
    String name;
    int quantity;
    double price;

    Item(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    double getTotalPrice() {
        return quantity * price;
    }
}

public class ShoppingCart {
    public static void main(String[] args) {
        ArrayList<Item> cart = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- 🛒 Shopping Cart Menu ---");
            System.out.println("1. Add item");
            System.out.println("2. Remove item");
            System.out.println("3. View cart");
            System.out.println("4. Exit & View Bill");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter item name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter quantity: ");
                    int quantity = sc.nextInt();
                    System.out.print("Enter price per unit: ");
                    double price = sc.nextDouble();
                    cart.add(new Item(name, quantity, price));
                    System.out.println("✅ Item added successfully!");
                    break;

                case 2:
                    System.out.print("Enter item name to remove: ");
                    String removeName = sc.nextLine();
                    boolean found = false;
                    for (Item item : cart) {
                        if (item.name.equalsIgnoreCase(removeName)) {
                            cart.remove(item);
                            System.out.println("🗑 Item removed.");
                            found = true;
                            break;
                        }
                    }
                    if (!found) System.out.println("❌ Item not found.");
                    break;

                case 3:
                    if (cart.isEmpty()) {
                        System.out.println("🛒 Cart is empty.");
                    } else {
                        System.out.printf("%-15s %-10s %-10s %-10s%n", "Item", "Quantity", "Price", "Total");
                        double grandTotal = 0;
                        for (Item item : cart) {
                            System.out.printf("%-15s %-10d %-10.2f %-10.2f%n", item.name, item.quantity, item.price, item.getTotalPrice());
                            grandTotal += item.getTotalPrice();
                        }
                        System.out.println("-----------------------------------------");
                        System.out.printf("Grand Total: %.2f%n", grandTotal);
                    }
                    break;

                case 4:
                    System.out.println("\n🧾 Final Bill");
                    System.out.printf("%-15s %-10s %-10s %-10s%n", "Item", "Quantity", "Price", "Total");
                    double grandTotal = 0;
                    for (Item item : cart) {
                        System.out.printf("%-15s %-10d %-10.2f %-10.2f%n", item.name, item.quantity, item.price, item.getTotalPrice());
                        grandTotal += item.getTotalPrice();
                    }
                    System.out.println("-----------------------------------------");
                    System.out.printf("Grand Total: %.2f%n", grandTotal);
                    System.out.println("💡 Thank you for shopping!");
                    running = false;
                    break;

                default:
                    System.out.println("⚠️ Invalid choice. Try again.");
            }
        }
        sc.close();
    }
}
