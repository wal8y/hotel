import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import java.io.PrintStream;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

class ReceiptGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PythonInterpreter py = new PythonInterpreter();

        //NewYork
        City newYork = new City("New York");
        newYork.addHotel("NY hotel");
        newYork.addHotel("Royal hotel");
        ((Hotel)newYork.hotels.get("NY hotel")).addRoom("Economy", 25.0);
        ((Hotel)newYork.hotels.get("NY hotel")).addRoom("Business", 35.0);
        ((Hotel)newYork.hotels.get("NY hotel")).addRoom("FirstClass", 50.0);
        ((Hotel)newYork.hotels.get("Royal hotel")).addRoom("Economy", 30.0);
        ((Hotel)newYork.hotels.get("Royal hotel")).addRoom("Business", 40.0);
        ((Hotel)newYork.hotels.get("Royal hotel")).addRoom("FirstClass", 55.0);

        //London
        City London = new City("London");
        London.addHotel("Kingsland");
        London.addHotel("Zedwell");
        ((Hotel)London.hotels.get("Kingsland")).addRoom("Economy", 22.0);
        ((Hotel)London.hotels.get("Kingsland")).addRoom("Business", 55.0);
        ((Hotel)London.hotels.get("Kingsland")).addRoom("FirstClass", 80.0);
        ((Hotel)London.hotels.get("Zedwell")).addRoom("Economy", 30.0);
        ((Hotel)London.hotels.get("Zedwell")).addRoom("Business", 65.0);
        ((Hotel)London.hotels.get("Zedwell")).addRoom("FirstClass", 95.0);


        City Istanbul = new City("Istanbul");
        Istanbul.addHotel("Kingsland");
        Istanbul.addHotel("Zedwell");
        //TODO : fix this homie
        ((Hotel)Istanbul.hotels.get("Kingsland")).addRoom("Economy", 22.0);
        ((Hotel)Istanbul.hotels.get("Kingsland")).addRoom("Business", 55.0);
        ((Hotel)Istanbul.hotels.get("Kingsland")).addRoom("FirstClass", 80.0);
        ((Hotel)Istanbul.hotels.get("Zedwell")).addRoom("Economy", 30.0);
        ((Hotel)Istanbul.hotels.get("Zedwell")).addRoom("Business", 65.0);
        ((Hotel)Istanbul.hotels.get("Zedwell")).addRoom("FirstClass", 95.0);


        City Tokyo = new City("Tokyo");
        Tokyo.addHotel("Kingsland");
        Tokyo.addHotel("Zedwell");
        //TODO: nigga fix this too pls
        ((Hotel)Tokyo.hotels.get("Kingsland")).addRoom("Economy", 22.0);
        ((Hotel)Tokyo.hotels.get("Kingsland")).addRoom("Business", 55.0);
        ((Hotel)Tokyo.hotels.get("Kingsland")).addRoom("FirstClass", 80.0);
        ((Hotel)Tokyo.hotels.get("Zedwell")).addRoom("Economy", 30.0);
        ((Hotel)Tokyo.hotels.get("Zedwell")).addRoom("Business", 65.0);
        ((Hotel)Tokyo.hotels.get("Zedwell")).addRoom("FirstClass", 95.0);

        System.out.println("Choose a city:");
        System.out.println("1. New York");
        System.out.println("2. London");
        System.out.println("3. Istanbul");
        System.out.println("4. Tokyo");

        System.out.print("Enter the number of your choice: ");
        py.execfile("src/gui.py");

        PyObject result = py.eval("button_click()");
        int cityChoice = Integer.parseInt(result.toString());
        City chosenCity = null;
        switch (cityChoice) {
            case 1:
                chosenCity = newYork;
                System.out.println("The Temperature in New york is :"+newYork.getTemperature()+"°C");
                break;
            case 2:
                chosenCity = London;
                System.out.println("The Temperature in London is :"+London.getTemperature()+"°C");
                break;
            case 3:
                chosenCity = London;
                System.out.println("The Temperature in Istanbul is :"+London.getTemperature()+"°C");
                break;
            case 4:
                chosenCity = London;
                System.out.println("The Temperature in Tokyo is :"+London.getTemperature()+"°C");
                break;
            default:
                System.out.println("Invalid choice. Defaulting to New York.");
                chosenCity = newYork;
        }

        System.out.println("Choose a hotel:");
        int hotelIndex = 1;

        for(Iterator var6 = chosenCity.hotels.keySet().iterator(); var6.hasNext(); ++hotelIndex) {
            String hotelName = (String)var6.next();
            System.out.println("" + hotelIndex + ". " + hotelName);
        }

        System.out.print("Enter the number of your choice: ");
        int hotelChoice = scanner.nextInt() - 1;
        Hotel chosenHotel = (Hotel)chosenCity.hotels.getOrDefault(chosenCity.hotels.keySet().toArray()[hotelChoice], (Hotel)newYork.hotels.get("Hotel A"));
        System.out.print("How many people: ");
        int numberOfPeople = scanner.nextInt();
        System.out.println("Choose a room type:");
        Iterator var9 = chosenHotel.rooms.entrySet().iterator();

        while(var9.hasNext()) {
            Map.Entry<String, Room> entry = (Map.Entry)var9.next();
            PrintStream var10000 = System.out;
            String var10001 = (String)entry.getKey();
            var10000.println(var10001 + ". " + ((Room)entry.getValue()).type + " (" + ((Room)entry.getValue()).pricePerPerson + " dollars each person)");
        }

        System.out.print("Enter your choice: ");
        String roomTypeChoice = scanner.next().toUpperCase();
        Room chosenRoom = (Room)chosenHotel.rooms.getOrDefault(roomTypeChoice, (Room)((Hotel)newYork.hotels.get("Hotel A")).rooms.get("Economy"));
        System.out.print("Do you want breakfast? (Y/N): ");
        boolean includeBreakfast = scanner.next().toUpperCase().charAt(0) == 'Y';
        System.out.print("How many days: ");
        int numberOfDays = scanner.nextInt();
        Customer customer = new Customer(chosenCity, chosenHotel, chosenRoom, includeBreakfast, numberOfPeople, numberOfDays);
        generateReceipt(customer);
        scanner.close();
    }

    private static void generateReceipt(Customer customer) {
        double roomCost = (double)customer.numberOfPeople * customer.chosenRoom.pricePerPerson * (double)customer.numberOfDays;
        double breakfastCost = customer.includeBreakfast ? (double)(30 * customer.numberOfPeople * customer.numberOfDays) : 0.0;
        double totalCost = roomCost + breakfastCost;
        System.out.println("\nReceipt:");
        System.out.println("City: " + customer.chosenCity.name);
        System.out.println("Hotel: " + customer.chosenHotel.name);
        System.out.println("Room Type: " + customer.chosenRoom.type);
        System.out.println("Number of People: " + customer.numberOfPeople);
        System.out.println("Number of Days: " + customer.numberOfDays);
        System.out.println("Room Cost: $" + roomCost);
        System.out.println("Breakfast Cost: $" + breakfastCost);
        System.out.println("Total Cost: $" + totalCost);
    }
}
