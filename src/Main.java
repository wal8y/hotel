import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

class ReceiptGenerator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //NewYork
        City newYork = new City("New York");
        newYork.addHotel("NY hotel",4.3f);
        newYork.addHotel("Royal hotel",4.6f);
        ((Hotel)newYork.hotels.get("NY hotel")).addRoom("Economy", 25.0);
        ((Hotel)newYork.hotels.get("NY hotel")).addRoom("Business", 35.0);
        ((Hotel)newYork.hotels.get("NY hotel")).addRoom("FirstClass", 50.0);
        ((Hotel)newYork.hotels.get("Royal hotel")).addRoom("Economy", 30.0);
        ((Hotel)newYork.hotels.get("Royal hotel")).addRoom("Business", 40.0);
        ((Hotel)newYork.hotels.get("Royal hotel")).addRoom("FirstClass", 55.0);

        //London
        City London = new City("London");
        London.addHotel("Kingsland",4.2f);
        London.addHotel("Zedwell",3.9f);
        ((Hotel)London.hotels.get("Kingsland")).addRoom("Economy", 22.0);
        ((Hotel)London.hotels.get("Kingsland")).addRoom("Business", 55.0);
        ((Hotel)London.hotels.get("Kingsland")).addRoom("FirstClass", 80.0);
        ((Hotel)London.hotels.get("Zedwell")).addRoom("Economy", 30.0);
        ((Hotel)London.hotels.get("Zedwell")).addRoom("Business", 65.0);
        ((Hotel)London.hotels.get("Zedwell")).addRoom("FirstClass", 95.0);


        City Istanbul = new City("Istanbul");
        Istanbul.addHotel("Hilton Istanbul",5.0f);
        Istanbul.addHotel("Crowne Plaza",4.5f);
        ((Hotel)Istanbul.hotels.get("Hilton Istanbul")).addRoom("Economy", 70.0);
        ((Hotel)Istanbul.hotels.get("Hilton Istanbul")).addRoom("Business", 95.0);
        ((Hotel)Istanbul.hotels.get("Hilton Istanbul")).addRoom("FirstClass", 130.0);
        ((Hotel)Istanbul.hotels.get("Crowne Plaza")).addRoom("Economy", 40.0);
        ((Hotel)Istanbul.hotels.get("Crowne Plaza")).addRoom("Business", 65.0);
        ((Hotel)Istanbul.hotels.get("Crowne Plaza")).addRoom("FirstClass", 75.0);

        City Tokyo = new City("Tokyo");
        Tokyo.addHotel("Asakusa",4.4f);
        Tokyo.addHotel("Komatsu Ryokan",3.7f);
        ((Hotel)Tokyo.hotels.get("Asakusa")).addRoom("Economy", 50.0);
        ((Hotel)Tokyo.hotels.get("Asakusa")).addRoom("Business", 75.0);
        ((Hotel)Tokyo.hotels.get("Asakusa")).addRoom("FirstClass", 110.0);
        ((Hotel)Tokyo.hotels.get("Komatsu Ryokan")).addRoom("Economy", 30.0);
        ((Hotel)Tokyo.hotels.get("Komatsu Ryokan")).addRoom("Business", 40.0);
        ((Hotel)Tokyo.hotels.get("Komatsu Ryokan")).addRoom("FirstClass", 55.0);

        System.out.println("Choose a city:");
        System.out.println("1. New York");
        System.out.println("2. London");
        System.out.println("3. Istanbul");
        System.out.println("4. Tokyo");

        System.out.print("Enter the number of your choice: ");
        int cityChoice = scanner.nextInt();
        City chosenCity = null;
        switch (cityChoice) {
            case 1:
                chosenCity = newYork;
                break;
            case 2:
                chosenCity = London;
                break;
            case 3:
                chosenCity = Istanbul;
                break;
            case 4:
                chosenCity = Tokyo;
                break;
            default:
                System.out.println("Invalid choice. Defaulting to New York.");

                chosenCity = newYork;
        }

        System.out.print("Choose temperature unit (C/F): ");
        char temperatureUnit = scanner.next().toUpperCase().charAt(0);

        switch (temperatureUnit) {
            case 'C':
                System.out.println("The Temperature in " + chosenCity.name + " is: " + chosenCity.getTemperature() + "°C");
                break;
            case 'F':
                double temperatureFahrenheit = (chosenCity.getTemperature() * 9/5) + 32;
                System.out.println("The Temperature in " + chosenCity.name + " is: " + temperatureFahrenheit + "°F");
                break;
            default:
                System.out.println("Invalid temperature unit. Displaying in Celsius.");
                System.out.println("The Temperature in " + chosenCity.name + " is: " + chosenCity.getTemperature() + "°C");
        }

        System.out.println("Choose a hotel:");
        int hotelIndex = 1;
        for (Iterator<Map.Entry<String, Hotel>> iterator = chosenCity.hotels.entrySet().iterator(); iterator.hasNext(); ++hotelIndex) {
            Map.Entry<String, Hotel> entry = iterator.next();
            String hotelName = entry.getKey();
            Hotel hotel = entry.getValue();
            float hotelRate = hotel.getRating();

            System.out.print("" + hotelIndex + ". " + hotelName + " Rating: " + hotelRate+" ");

            for (int i = 1; i <= hotelRate; i++) {
                System.out.print('*');
            }

            System.out.println();
        }

        System.out.print("Enter the number of your choice: ");
        int hotelChoice = scanner.nextInt() - 1;
        Hotel chosenHotel = (Hotel)chosenCity.hotels.getOrDefault(chosenCity.hotels.keySet().toArray()[hotelChoice], (Hotel)newYork.hotels.get("Hotel A"));
        System.out.print("How many People: ");
        int numberOfPeople = scanner.nextInt();
        System.out.println("Choose a room:");
        int roomIndex = 1;

        for (Map.Entry<String, Room> entry : chosenHotel.rooms.entrySet()) {
            Room room = entry.getValue();
            System.out.println(roomIndex + ". " + room.type + " (" + room.pricePerPerson + " dollars each person)");
            roomIndex++;
        }

        System.out.print("Enter the number of your choice: ");
        int roomChoice = scanner.nextInt() - 1;

        Room chosenRoom = chosenHotel.rooms.values().toArray(new Room[0])[roomChoice];

        System.out.print("Do you want breakfast? (Y/N): ");
        boolean includeBreakfast = scanner.next().toUpperCase().charAt(0) == 'Y';
        System.out.print("How many days: ");
        int numberOfDays = scanner.nextInt();
        Customer customer = new Customer(chosenCity, chosenHotel, chosenRoom, includeBreakfast, numberOfPeople, numberOfDays);
        generateReceipt(customer);
        scanner.close();
    }

    private static void generateReceipt(Customer customer)  {
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

    private static void displayAverageRating(City city) {
        double totalRating = 0;
        int hotelCount = city.hotels.size();

        for (Hotel hotel : city.hotels.values()) {
            totalRating += hotel.getRating();
        }

        double averageRating = totalRating / hotelCount;

        System.out.println("Average Hotel Rating in " + city.name + ": " + averageRating);
    }
}
