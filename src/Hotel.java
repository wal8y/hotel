import java.net.URL;
import java.util.HashMap;
import java.util.Map;

class Hotel {
    String name;
    Float Rating;
    Map<String, Room> rooms;

    public Hotel(String name,Float Rating) {
        this.name = name;
        this.Rating=Rating;
        this.rooms = new HashMap();
    }
    public float getRating(){
        return Rating;
    }
    public void addRoom(String roomType, double pricePerPerson) {
        this.rooms.put(roomType, new Room(roomType, pricePerPerson));
    }
}
