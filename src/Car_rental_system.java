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
                System.out.println("Car ID: " + temp.carID + ", Model: " + temp.model);
                anyAvailable = true;
            }
            temp = temp.next;
        }
        if (!anyAvailable) {
            System.out.println("No cars available.");
        }
    }

    public boolean returnCar(String carID) {
        CarNode temp = head;
        while (temp != null) {
            if (temp.carID.equals(carID)) {
                temp.isAvailable = true;
                return true;
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
}

// ClientRequestQueue: Queue for handling client car rental requests
class ClientRequestQueue {
    ClientsNode front;
    ClientsNode rear;

    ClientRequestQueue() {
        this.front = this.rear = null;
    }

    void enqueue(ClientsNode client) {
        if (rear == null) {
            rear = front = client; // First client in the queue
        } else {
            rear.next = client; // Add to the rear
            rear = client;
        }
        System.out.println("Client request added: " + client.clientName);
    }

    ClientsNode dequeue() {
        if (isEmpty()) {
            System.out.println("No requests to process.");
            return null;
        }
        ClientsNode temp = front;
        front = front.next;
        if (front == null) {
            rear = null;
        }
        System.out.println("Processing request for: " + temp.clientName);
        return temp;
    }

    public boolean isEmpty() {
        return front == null;
    }
}

// Main Class: Car Rental System
class Car_rental_system {
    public static void main(String[] args) {
        CarLL carList = new CarLL();
        ClientLL clientList = new ClientLL();
        ClientRequestQueue requestQueue = new ClientRequestQueue();

        // Adding cars to the system
        carList.addCar("C001", "Toyota Corolla", "Sedan");
        carList.addCar("C002", "Honda Civic", "Sedan");
        carList.addCar("C003", "Suzuki Swift", "Hatchback");

        carList.displayAvailableCars();

        // Adding clients to the system
        clientList.addClient("John Doe", 123456789);
        clientList.addClient("Jane Smith", 987654321);
        clientList.addClient("Alice Johnson", 112233445);

        clientList.displayClients();

        // Adding client requests to the queue
        requestQueue.enqueue(clientList.head); // John Doe
        requestQueue.enqueue(clientList.head.next); // Jane Smith
        requestQueue.enqueue(clientList.head.next.next); // Alice Johnson

        // Processing car rental requests
        while (!requestQueue.isEmpty()) {
            ClientsNode client = requestQueue.dequeue(); // Get the next client
            System.out.println("Processing rental for: " + client.clientName);

            // Find the first available car
            CarNode temp = carList.head;
            boolean carAssigned = false;
            while (temp != null) {
                if (temp.isAvailable) {
                    // Assign the car to the client
                    client.rentedCars[client.rentedCarCount++] = temp;
                    temp.isAvailable = false;
                    carAssigned = true;
                    System.out.println("Car rented to " + client.clientName + ": " + temp.model);
                    break;
                }
                temp = temp.next;
            }

            if (!carAssigned) {
                System.out.println("No cars available for " + client.clientName);
            }
        }

        carList.displayAvailableCars(); // Check remaining cars
    }
}