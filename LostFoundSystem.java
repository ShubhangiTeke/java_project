package code;

import java.util.ArrayList;
import java.util.Scanner;

class LostFoundItem {
    int id;
    String name;
    String description;
    String location;
    String status;

    LostFoundItem(int id, String name, String description,
                  String location, String status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.status = status;
    }

    void display() {
        System.out.println("\nItem ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Description: " + description);
        System.out.println("Location: " + location);
        System.out.println("Status: " + status);
    }
}

public class LostFoundSystem {
    static ArrayList<LostFoundItem> items = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static int nextId = 1;

    static void reportItem(String status) {
        System.out.print("Enter item name: ");
        String name = sc.nextLine();

        System.out.print("Enter description: ");
        String description = sc.nextLine();

        System.out.print("Enter location: ");
        String location = sc.nextLine();

        items.add(new LostFoundItem(
            nextId++, name, description, location, status
        ));

        System.out.println("Item reported successfully!");
    }

    static void viewItems() {
        if (items.isEmpty()) {
            System.out.println("No items reported yet.");
            return;
        }

        for (LostFoundItem item : items) {
            item.display();
        }
    }

    static void searchItem() {
        System.out.print("Enter item name to search: ");
        String search = sc.nextLine();

        boolean found = false;

        for (LostFoundItem item : items) {
            if (item.name.toLowerCase().contains(
                    search.toLowerCase())) {
                item.display();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No matching items found.");
        }
    }

    static void returnItem() {
        System.out.print("Enter item ID: ");
        int id;

        try {
            id = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid ID.");
            return;
        }

        for (LostFoundItem item : items) {
            if (item.id == id) {
                if (item.status.equals("Returned")) {
                    System.out.println("Item is already returned.");
                } else {
                    item.status = "Returned";
                    System.out.println("Item marked as returned!");
                }
                return;
            }
        }

        System.out.println("Invalid item ID.");
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== LOST & FOUND SYSTEM =====");
            System.out.println("1. Report Lost Item");
            System.out.println("2. Report Found Item");
            System.out.println("3. View All Items");
            System.out.println("4. Search Item");
            System.out.println("5. Mark Item as Returned");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {
                case "1":
                    reportItem("Lost");
                    break;
                case "2":
                    reportItem("Found");
                    break;
                case "3":
                    viewItems();
                    break;
                case "4":
                    searchItem();
                    break;
                case "5":
                    returnItem();
                    break;
                case "6":
                    System.out.println("Thank you!");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
