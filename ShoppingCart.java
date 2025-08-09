import java.util.ArrayList;
import java.util.Scanner;

public class ShoppingCart {
    public static void main(String[] args)
    {
        ArrayList<String> cart = new ArrayList<>();
        Scanner sc = new Scanner(System.in);

        while (true){
            System.out.println("\n---Shopping Cart Menu ---");
            System.out.println("1. Add item.");
            System.out.println("2. Remove item");
            System.out.println("3. view item");
            System.out.println("4.exit");
            System.out.println("Choose an option: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter item to add:");
                    String addItem = sc.nextLine();
                    cart.add(addItem);
                    System.out.println(addItem+"added to cart.");
                    break;
                case 2:
                    System.out.println("Enter item to remove:");
                    String removeItem = sc.nextLine();
                    if(cart.remove(removeItem))
                    {
                        System.out.println(removeItem+ "removed from cart.");
                    }else{
                        System.out.println("Item not found");
                    }
                    break;
                case 3:
                    System.out.println("Your cart: " + cart);
                    break;

                case 4:
                    System.out.println("Exiting... thank you for shopping!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. try again.");
            }
        }
    }
}
