class Customer {
    City chosenCity;
    Hotel chosenHotel;
    Room chosenRoom;
    boolean includeBreakfast;
    int numberOfPeople;
    int numberOfDays;

    public Customer(City chosenCity, Hotel chosenHotel, Room chosenRoom, boolean includeBreakfast, int numberOfPeople, int numberOfDays) {
        this.chosenCity = chosenCity;
        this.chosenHotel = chosenHotel;
        this.chosenRoom = chosenRoom;
        this.includeBreakfast = includeBreakfast;
        this.numberOfPeople = numberOfPeople;
        this.numberOfDays = numberOfDays;
    }
}
