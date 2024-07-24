import java.io.*;
import java.util.*;

class Expense {
    String date;
    String category;
    double amount;

    Expense(String date, String category, double amount) {
        this.date = date;
        this.category = category;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Date: " + date + ", Category: " + category + ", Amount: " + amount;
    }
}

class User {
    String username;
    List<Expense> expenses;

    User(String username) {
        this.username = username;
        this.expenses = new ArrayList<>();
    }

    void addExpense(Expense expense) {
        expenses.add(expense);
    }

    void listExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
        } else {
            for (Expense expense : expenses) {
                System.out.println(expense);
            }
        }
    }

    void categoryWiseSummation() {
        Map<String, Double> categorySum = new HashMap<>();
        for (Expense expense : expenses) {
            categorySum.put(expense.category, categorySum.getOrDefault(expense.category, 0.0) + expense.amount);
        }
        for (String category : categorySum.keySet()) {
            System.out.println("Category: " + category + ", Total Amount: " + categorySum.get(category));
        }
    }

    void saveExpenses() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(username + "_expenses.txt"))) {
            for (Expense expense : expenses) {
                writer.println(expense.date + "," + expense.category + "," + expense.amount);
            }
        } catch (IOException e) {
            System.out.println("Error saving expenses: " + e.getMessage());
        }
    }

    void loadExpenses() {
        try (BufferedReader reader = new BufferedReader(new FileReader(username + "_expenses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                expenses.add(new Expense(parts[0], parts[1], Double.parseDouble(parts[2])));
            }
        } catch (IOException e) {
            System.out.println("Error loading expenses: " + e.getMessage());
        }
    }
}

public class ExpenseTracker {
    static Map<String, User> users = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter username: ");
                    String username = scanner.nextLine();
                    if (users.containsKey(username)) {
                        System.out.println("Username already exists.");
                    } else {
                        users.put(username, new User(username));
                        System.out.println("User registered successfully.");
                    }
                    break;
                case 2:
                    System.out.print("Enter username: ");
                    username = scanner.nextLine();
                    User user = users.get(username);
                    if (user == null) {
                        System.out.println("User not found.");
                    } else {
                        user.loadExpenses();
                        userMenu(scanner, user);
                        user.saveExpenses();
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    static void userMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("1. Add Expense");
            System.out.println("2. List Expenses");
            System.out.println("3. Category-wise Summation");
            System.out.println("4. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter date (YYYY-MM-DD): ");
                    String date = scanner.nextLine();
                    System.out.print("Enter category: ");
                    String category = scanner.nextLine();
                    System.out.print("Enter amount: ");
                    double amount = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline
                    user.addExpense(new Expense(date, category, amount));
                    System.out.println("Expense added successfully.");
                    break;
                case 2:
                    user.listExpenses();
                    break;
                case 3:
                    user.categoryWiseSummation();
                    break;
                case 4:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}
