import java.util.*;

class User {
    private String username;
    private String password;
    private int totalBottlesRecycled;
    private double rewardsEarned;
    private List<String> transactionHistory;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.totalBottlesRecycled = 0;
        this.rewardsEarned = 0.0;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }

    public void addRecycledBottles(int count, double rewardPerBottle) {
        this.totalBottlesRecycled += count;
        this.rewardsEarned += count * rewardPerBottle;
        String transaction = "Recycled " + count + " bottles. Earned $" + (count * rewardPerBottle);
        transactionHistory.add(transaction);
    }

    public void displayProfile() {
        System.out.println("\nUser Profile:");
        System.out.println("Username: " + username);
        System.out.println("Total Bottles Recycled: " + totalBottlesRecycled);
        System.out.println("Rewards Earned: $" + rewardsEarned);
    }

    public void displayTransactionHistory() {
        System.out.println("\nTransaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions available.");
        } else {
            for (String transaction : transactionHistory) {
                System.out.println(transaction);
            }
        }
    }

    public double getRewardsEarned() {
        return rewardsEarned;
    }

    public void redeemRewards(double amount) {
        if (amount > 0 && amount <= rewardsEarned) {
            rewardsEarned -= amount;
            transactionHistory.add("Redeemed $" + amount + " rewards.");
            System.out.println("Successfully redeemed $" + amount + " rewards.");
        } else {
            System.out.println("Invalid amount or insufficient rewards.");
        }
    }
}

class RVMSystem {
    private static final double REWARD_PER_BOTTLE = 0.10;
    private Map<String, User> users = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public void start() {
        while (true) {
            System.out.println("\n--- Reverse Vending Machine System ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    loginUser();
                    break;
                case 3:
                    System.out.println("Exiting system. Thank you!");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void registerUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Try another.");
            return;
        }

        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        users.put(username, new User(username, password));
        System.out.println("Registration successful!");
    }

    private void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.checkPassword(password)) {
            System.out.println("Login successful!");
            userMenu(user);
        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private void userMenu(User user) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Recycle Bottles");
            System.out.println("2. View Profile");
            System.out.println("3. View Transaction History");
            System.out.println("4. Redeem Rewards");
            System.out.println("5. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    recycleBottles(user);
                    break;
                case 2:
                    user.displayProfile();
                    break;
                case 3:
                    user.displayTransactionHistory();
                    break;
                case 4:
                    redeemRewards(user);
                    break;
                case 5:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private void recycleBottles(User user) {
        System.out.print("Enter number of bottles to recycle: ");
        int count = scanner.nextInt();
        if (count > 0) {
            user.addRecycledBottles(count, REWARD_PER_BOTTLE);
            System.out.println("Successfully recycled " + count + " bottles.");
            System.out.println("Rewards earned: $" + (count * REWARD_PER_BOTTLE));
        } else {
            System.out.println("Invalid number of bottles.");
        }
    }

    private void redeemRewards(User user) {
        System.out.print("Enter amount to redeem: ");
        double amount = scanner.nextDouble();
        user.redeemRewards(amount);
    }
}

public class RVMUserManagement {
    public static void main(String[] args) {
        RVMSystem system = new RVMSystem();
        system.start();
    }
}
