import java.util.ArrayList;
import java.util.Scanner;

// Abstract Vehicle class
abstract class Vehicle {
    private String vehicleId;
    private String brand;
    private double pricePerDay;

    public Vehicle(String vehicleId, String brand, double pricePerDay) {
        this.vehicleId = vehicleId;
        this.brand = brand;
        this.pricePerDay = pricePerDay;
    }

    public String getVehicleId() { return vehicleId; }
    public String getBrand() { return brand; }
    public double getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(double pricePerDay) { this.pricePerDay = pricePerDay; }

    public abstract void displayInfo();

    public double calculateCost(int days) {
        return pricePerDay * days;
    }
}

// Car class
class Car extends Vehicle {
    private int seats;

    public Car(String vehicleId, String brand, double pricePerDay, int seats) {
        super(vehicleId, brand, pricePerDay);
        this.seats = seats;
    }

    @Override
    public void displayInfo() {
        System.out.println("Car ID: " + getVehicleId() + ", Brand: " + getBrand() +
                ", Price/Day: $" + getPricePerDay() + ", Seats: " + seats);
    }

    @Override
    public double calculateCost(int days) {
        double base = super.calculateCost(days);
        double insurance = 10 * days; // Extra insurance charge
        return base + insurance;
    }
}

// Bike class
class Bike extends Vehicle {
    private boolean hasCarrier;

    public Bike(String vehicleId, String brand, double pricePerDay, boolean hasCarrier) {
        super(vehicleId, brand, pricePerDay);
        this.hasCarrier = hasCarrier;
    }

    @Override
    public void displayInfo() {
        System.out.println("Bike ID: " + getVehicleId() + ", Brand: " + getBrand() +
                ", Price/Day: $" + getPricePerDay() + ", Carrier: " + (hasCarrier ? "Yes" : "No"));
    }

    @Override
    public double calculateCost(int days) {
        double base = super.calculateCost(days);
        if (hasCarrier) base += 5 * days; // Extra charge if carrier available
        return base;
    }
}

// Rental class
class Rental {
    private Vehicle vehicle;
    private int days;

    public Rental(Vehicle vehicle, int days) {
        this.vehicle = vehicle;
        this.days = days;
    }

    public double getTotalCost() {
        return vehicle.calculateCost(days);
    }

    public void showRentalDetails() {
        vehicle.displayInfo();
        System.out.println("Days Rented: " + days);
        System.out.println("Total Cost: $" + getTotalCost());
        System.out.println("-----------------------------");
    }
}

// Main class
public class VehicleRentalSystem {
    private static ArrayList<Vehicle> vehicles = new ArrayList<>();
    private static ArrayList<Rental> rentals = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeVehicles();
        boolean exit = false;

        while (!exit) {
            System.out.println("\n--- Vehicle Rental System ---");
            System.out.println("1. View all vehicles");
            System.out.println("2. Rent a vehicle");
            System.out.println("3. Add new vehicle");
            System.out.println("4. View all rentals & total revenue");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> viewVehicles();
                case 2 -> rentVehicle();
                case 3 -> addVehicle();
                case 4 -> viewRentals();
                case 5 -> {
                    System.out.println("Exiting system. Goodbye!");
                    exit = true;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
        sc.close();
    }

    private static void initializeVehicles() {
        vehicles.add(new Car("Car1", "Toyota", 50, 4));
        vehicles.add(new Car("Car2", "Honda", 60, 5));
        vehicles.add(new Car("Car3", "Ford", 55, 4));
        vehicles.add(new Car("Car4", "BMW", 100, 4));
        vehicles.add(new Car("Car5", "Hyundai", 45, 5));

        vehicles.add(new Bike("Bike1", "Yamaha", 20, true));
        vehicles.add(new Bike("Bike2", "Suzuki", 15, false));
        vehicles.add(new Bike("Bike3", "Honda", 18, true));
        vehicles.add(new Bike("Bike4", "Royal Enfield", 25, false));
        vehicles.add(new Bike("Bike5", "KTM", 30, true));
    }

    private static void viewVehicles() {
        System.out.println("\n--- Available Vehicles ---");
        for (Vehicle v : vehicles) {
            v.displayInfo();
        }
    }

    private static void rentVehicle() {
        System.out.print("Enter Vehicle ID to rent: ");
        String id = sc.nextLine();
        Vehicle selectedVehicle = null;

        for (Vehicle v : vehicles) {
            if (v.getVehicleId().equalsIgnoreCase(id)) {
                selectedVehicle = v;
                break;
            }
        }

        if (selectedVehicle == null) {
            System.out.println("Vehicle not found!");
            return;
        }

        System.out.print("Enter number of days: ");
        int days = sc.nextInt();
        sc.nextLine(); // consume newline

        Rental rental = new Rental(selectedVehicle, days);
        rentals.add(rental);
        System.out.println("\n--- Rental Confirmed ---");
        rental.showRentalDetails();
    }

    private static void addVehicle() {
        System.out.print("Enter type (Car/Bike): ");
        String type = sc.nextLine();

        System.out.print("Enter Vehicle ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Brand: ");
        String brand = sc.nextLine();
        System.out.print("Enter Price per Day: ");
        double price = sc.nextDouble();
        sc.nextLine(); // consume newline

        if (type.equalsIgnoreCase("Car")) {
            System.out.print("Enter number of seats: ");
            int seats = sc.nextInt();
            sc.nextLine();
            vehicles.add(new Car(id, brand, price, seats));
        } else if (type.equalsIgnoreCase("Bike")) {
            System.out.print("Has carrier (true/false): ");
            boolean carrier = sc.nextBoolean();
            sc.nextLine();
            vehicles.add(new Bike(id, brand, price, carrier));
        } else {
            System.out.println("Invalid type! Vehicle not added.");
        }
        System.out.println("Vehicle added successfully!");
    }

    private static void viewRentals() {
        System.out.println("\n--- All Rentals ---");
        double totalRevenue = 0;
        for (Rental r : rentals) {
            r.showRentalDetails();
            totalRevenue += r.getTotalCost();
        }
        System.out.println("Total Revenue: $" + totalRevenue);
    }
}
