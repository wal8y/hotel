import java.util.HashMap;
import java.util.Map;

class Hotel {
    String name;
    Map<String, Room> rooms;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new HashMap();
    }

    public void addRoom(String roomType, double pricePerPerson) {
        this.rooms.put(roomType, new Room(roomType, pricePerPerson));
    }
}
