package Real_estate;

interface PropertyInterface {
    void makeDiscount(int discount);
    int getTotalPrice();
    double averageSqmPerRoom();
}

interface PanelInterface {
    boolean hasSameAmount();
}

// Real_Estate class
class Real_Estate implements PropertyInterface {

    String city;
    double price;
    int sqm;
    double numberOfRooms;
    Genre genre;

    enum Genre {
        FAMILYHOUSE, CONDOMINIUM, FARM
    }

    Real_Estate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    String getCity() {
        return this.city;
    }

    double getPrice() {
        return this.price;
    }
    int getSqm() {
        return this.sqm;
    }
    double getNumberOfRooms() {
        return this.numberOfRooms;
    }
    Genre getGenre() {
        return this.genre;
    }

    @Override
    public void makeDiscount(int discount) {
        this.price = this.price - (this.price * (discount / 100.0));
    }

    @Override
    public int getTotalPrice() {
        int totalPrice = (int) (this.price * this.sqm);

        switch (this.city) {
            case "Budapest":
                totalPrice += totalPrice * 0.30;
                break;
            case "Debrecen":
                totalPrice += totalPrice * 0.20;
                break;
            case "Nyíregyháza":
                totalPrice += totalPrice * 0.15;
                break;
        }
        return totalPrice;
    }

    @Override
    public double averageSqmPerRoom() {
        return this.sqm / this.numberOfRooms;
    }

    @Override
    public String toString() {
        return "City: " + this.city +
                "\nPrice: R" + this.price +
                "\nSize: " + this.sqm + "sqm" +
                "\nNumber of rooms: " + this.numberOfRooms +
                "\nGenre: " + this.genre + "\n" +
                this.getTotalPrice() + "\n" +
                this.averageSqmPerRoom();
    }
}

// Panel class
class Panel extends Real_Estate implements PanelInterface {

    int floor;
    boolean isInsulated;

    Panel(String city, double price, int sqm, double numberOfRooms, Genre genre, int floor, boolean isInsulated) {
        super(city, price, sqm, numberOfRooms, genre);
        this.floor = floor;
        this.isInsulated = isInsulated;
    }

    @Override
    public int getTotalPrice() {
        int totalPrice = super.getTotalPrice();

        if (this.floor < 2 && this.floor > 0 && this.isInsulated) {
            totalPrice += totalPrice * 0.10;
        } else if (this.floor == 10) {
            totalPrice -= totalPrice * 0.05;
        }
        return totalPrice;
    }

    @Override
    public boolean hasSameAmount() {
        return super.getTotalPrice() == this.getTotalPrice();
    }

    public double roomPrice() {
        return (double) this.getTotalPrice() / super.numberOfRooms;
    }

    public int getFloor(){
        return this.floor;
    }

    public boolean IsInsulated(){
        return this.isInsulated;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nFloor: " + this.floor +
                "\nIs insulated: " + this.isInsulated;
    }
}




public class Main {
    public static void main(String[] args) {
        // Real_Estate estates[] = {
        //     new Real_Estate("Budapest", 35000, 120, 4, Real_Estate.Genre.FAMILYHOUSE),
        //     new Real_Estate("Debrecen", 25000, 100, 3, Real_Estate.Genre.CONDOMINIUM),
        //     new Real_Estate("Nyíregyháza", 20000, 80, 2, Real_Estate.Genre.FARM)
        // };

        // Panel panelo = new Panel("Debrecen",12, 234, 3, Real_Estate.Genre.FARM, 6, false);

        // System.out.println(panelo.toString());
        // System.out.println("\n");
        // System.out.println("\n new line here");


        // for(Real_Estate estate : estates){
        //     System.out.println(estate.toString());
        //     System.out.println();
        // }

        RealEstateAgent.loadFromFile("C:\\Users\\Mathew.DESKTOP-1SJQ6P4\\OneDrive\\Documents\\Java_practice_progs\\Real_estate\\properties.txt");
        RealEstateAgent.writeOutput("C:\\Users\\Mathew.DESKTOP-1SJQ6P4\\OneDrive\\Documents\\Java_practice_progs\\Real_estate\\outputRealEstate.txt");



    }
}
