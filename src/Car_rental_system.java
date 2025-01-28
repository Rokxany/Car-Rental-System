import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Stack;

// CarNode: Represents each car
class CarNode {
    String carID;
    String carCategory;
    String model;
    boolean isAvailable;
    CarNode next;

    CarNode(String carID, String model, String carCategory) {
        this.carID = carID;
        this.model = model;
        this.carCategory = carCategory;
        this.isAvailable = true;
        this.next = null;
    }
}

// CarLL: Linked List of Cars
class CarLL {
    CarNode head;
    CarNode tail;

    CarLL() {
        this.head = this.tail = null;
    }

    public void addCar(String carID, String model, String carCategory) {
        CarNode newCar = new CarNode(carID, model, carCategory);
        if (head == null) {
            head = tail = newCar; // First car in the list
        } else {
            tail.next = newCar; // Add at the end
            tail = newCar;
        }
        System.out.println("Car added: " + model);
    }

    public void displayAvailableCars() {
        if (head == null) {
            System.out.println("No cars in the system.");
            return;
        }
        System.out.println("Available Cars:");
        CarNode temp = head;
        boolean anyAvailable = false;
        while (temp != null) {
            if (temp.isAvailable) {
                System.out.println("Car ID: " + temp.carID + ", Model: " + temp.model + ", Category: " + temp.carCategory);
                anyAvailable = true;
            }
            temp = temp.next;
        }
        if (!anyAvailable) {
            System.out.println("No cars available.");
        }
    }

    public CarNode rentCar(String carID) {
        CarNode temp = head;
        while (temp != null) {
            if (temp.carID.equals(carID) && temp.isAvailable) {
                temp.isAvailable = false;
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public boolean returnCar(String carID) {
        CarNode temp = head;
        while (temp != null) {
            if (temp.carID.equals(carID)) {
                if (!temp.isAvailable) {
                    temp.isAvailable = true;
                    return true;
                } else {
                    System.out.println("Car is already available.");
                    return false;
                }
            }
            temp = temp.next;
        }
        return false;
    }
}

// ClientsNode: Represents each client
class ClientsNode {
    String clientName;
    int clientPhoneNo;
    CarNode[] rentedCars; // Cars rented by this client
    int rentedCarCount;
    ClientsNode next;

    ClientsNode(String clientName, int clientPhoneNo) {
        this.clientName = clientName;
        this.clientPhoneNo = clientPhoneNo;
        this.rentedCars = new CarNode[10];
        this.rentedCarCount = 0;
        this.next = null;
    }
}

// ClientLL: Linked List of all registered clients
class ClientLL {
    ClientsNode head;
    ClientsNode tail;

    ClientLL() {
        this.head = this.tail = null;
    }

    public void addClient(String clientName, int clientPhoneNo) {
        ClientsNode newClient = new ClientsNode(clientName, clientPhoneNo);
        if (head == null) {
            head = tail = newClient; // First client in the list
        } else {
            tail.next = newClient; // Add at the end
            tail = newClient;
        }
        System.out.println("Client added: " + clientName);
    }

    public ClientsNode findClient(String clientName) {
        ClientsNode temp = head;
        while (temp != null) {
            if (temp.clientName.equals(clientName)) {
                return temp;
            }
            temp = temp.next;
        }
        return null;
    }

    public void displayClients() {
        if (head == null) {
            System.out.println("No clients registered.");
            return;
        }
        System.out.println("Registered Clients:");
        ClientsNode temp = head;
        while (temp != null) {
            System.out.println("Name: " + temp.clientName + ", Phone: " + temp.clientPhoneNo);
            temp = temp.next;
        }
    }

    public void displayClientRentals() {
        if (head == null) {
            System.out.println("No clients registered.");
            return;
        }
        System.out.println("Client Rentals:");
        ClientsNode temp = head;
        boolean anyRentals = false;
        while (temp != null) {
            if (temp.rentedCarCount > 0) {
                anyRentals = true;
                System.out.print("Client Name: " + temp.clientName + " (Phone: " + temp.clientPhoneNo + ") - Rented Cars: ");
                for (int i = 0; i < temp.rentedCarCount; i++) {
                    System.out.print(temp.rentedCars[i].model + ",");
                }
                System.out.println();
            }
            temp = temp.next;
        }
        if (!anyRentals) {
            System.out.println("No cars have been rented out yet.");
        }
    }
}

// Payment class
class Payment {
    String clientName;
    String carID;
    double amount;
    String date;

    Payment(String clientName, String carID, double amount, String date) {
        this.clientName = clientName;
        this.carID = carID;
        this.amount = amount;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Client: " + clientName + ", Car ID: " + carID + ", Amount: " + amount + ", Date: " + date;
    }
}

// PaymentStack: Tracks payments using a stack
class PaymentStack {
    Stack<Payment> payments;

    PaymentStack() {
        payments = new Stack<>();
    }

    public void addPayment(String clientName, String carID, double amount, String date) {
        Payment payment = new Payment(clientName, carID, amount, date);
        payments.push(payment);
        System.out.println("Payment recorded: " + payment);
    }

    public void viewRecentPayment() {
        if (payments.isEmpty()) {
            System.out.println("No payments recorded.");
        } else {
            System.out.println("Recent Payment: " + payments.peek());
        }
    }

    public void displayAllPayments() {
        if (payments.isEmpty()) {
            System.out.println("No records available.");
        } else {
            System.out.println("All Records of Payments (most recent first):");
            for (Payment payment : payments) {
                System.out.println(payment);
            }
        }
    }
}

// Main Class: Car Rental System
class Car_rental_system {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CarLL carList = new CarLL();
        ClientLL clientList = new ClientLL();
        PaymentStack paymentStack = new PaymentStack();

        while (true) {
            System.out.println("\n--- Car Rental System ---");
            System.out.println("1. Admin Login");
            System.out.println("2. Customer Login ");
            System.out.println("3. Exit ");
            System.out.print("Enter your choice: ");

            int choice = -1;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
                continue;
            }

            // Admin Password Check
            while (true) {
                if (choice == 1) {
                    String password = "admin123";
                    System.out.println("(Default Password: admin123)");
                    System.out.print("Enter Password: ");
                    String pass = scanner.nextLine();

                    if (pass.equals(password)) {
                        break;
                    } else {
                        System.out.println("Wrong password. Try again.");
                    }
                } else if (choice == 2 || choice == 3) {
                    break;
                }
            }

            switch (choice) {
                case 1: // Admin Interface
                    while (true) {
                        System.out.println("\n--- Admin Menu ---");
                        System.out.println("1. Add a Car");
                        System.out.println("2. Add a Client");
                        System.out.println("3. View All Clients");
                        System.out.println("4. View Client Rentals");
                        System.out.println("5. View Recent Payment");
                        System.out.println("6. View All Records");
                        System.out.println("7. Logout");
                        System.out.print("Enter your choice: ");

                        int adminChoice = -1;
                        try {
                            adminChoice = scanner.nextInt();
                            scanner.nextLine(); // Consume newline
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.nextLine(); // Clear the invalid input
                            continue; // Restart the loop
                        }

                        switch (adminChoice) {
                            case 1:
                                System.out.print("Enter Car ID: ");
                                String carID = scanner.nextLine();
                                System.out.print("Enter Car Model: ");
                                String model = scanner.nextLine();
                                System.out.print("Enter Car Category: ");
                                String category = scanner.nextLine();
                                carList.addCar(carID, model, category);
                                break;

                            case 2:
                                System.out.print("Enter Client Name: ");
                                String clientName = scanner.nextLine();
                                System.out.print("Enter Client Phone Number: ");
                                int clientPhone = -1;
                                try {
                                    clientPhone = scanner.nextInt();
                                    scanner.nextLine(); // Consume newline
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid phone number! Please enter a valid number.");
                                    scanner.nextLine(); // Clear the invalid input
                                    break;
                                }
                                clientList.addClient(clientName, clientPhone);
                                break;

                            case 3:
                                clientList.displayClients();
                                break;

                            case 4:
                                clientList.displayClientRentals();
                                break;

                            case 5:
                                paymentStack.viewRecentPayment();
                                break;

                            case 6:
                                paymentStack.displayAllPayments();
                                break;

                            case 7:
                                System.out.println("Logging out of Admin Menu.");
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                        if (adminChoice == 7) break;
                    }
                    break;

                case 2: // Customer Interface
                    while (true) {
                        System.out.println("\n--- Customer Menu ---");
                        System.out.println("1. View Available Cars");
                        System.out.println("2. Rent a Car");
                        System.out.println("3. Return a Car");
                        System.out.println("4. Logout");
                        System.out.print("Enter your choice: ");

                        int customerChoice = -1;
                        try {
                            customerChoice = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input! Please enter a valid number.");
                            scanner.nextLine();
                            continue;
                        }

                        switch (customerChoice) {
                            case 1:
                                carList.displayAvailableCars();
                                break;

                            case 2:
                                System.out.print("Enter Your Name: ");
                                String clientName = scanner.nextLine();
                                ClientsNode client = clientList.findClient(clientName);
                                if (client == null) {
                                    System.out.println("Client not found. Please register with admin.");
                                    break;
                                }
                                carList.displayAvailableCars();
                                System.out.print("Enter the Car ID to rent: ");
                                String carID = scanner.nextLine();
                                CarNode rentedCar = carList.rentCar(carID);
                                if (rentedCar != null) {
                                    client.rentedCars[client.rentedCarCount++] = rentedCar;
                                    System.out.print("Enter rental amount: ");
                                    double amount = -1;
                                    try {
                                        amount = scanner.nextDouble();
                                        scanner.nextLine();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid amount! Please enter a valid number.");
                                        scanner.nextLine();
                                        break;
                                    }
                                    System.out.print("Enter rental date (e.g., 2025-01-25): ");
                                    String date = scanner.nextLine();
                                    paymentStack.addPayment(clientName, carID, amount, date);
                                    System.out.println("Car rented successfully: " + rentedCar.model);
                                } else {
                                    System.out.println("Car not available or invalid Car ID.");
                                }
                                break;

                            case 3:
                                System.out.print("Enter Car ID to return: ");
                                String returnCarID = scanner.nextLine();
                                if (carList.returnCar(returnCarID)) {
                                    System.out.println("Car returned successfully.");
                                } else {
                                    System.out.println("Car ID not found or already available.");
                                }
                                break;

                            case 4:
                                System.out.println("Logging out of Customer Menu.");
                                break;

                            default:
                                System.out.println("Invalid choice. Please try again.");
                        }
                        if (customerChoice == 4) break;
                    }
                    break;

                case 3:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}