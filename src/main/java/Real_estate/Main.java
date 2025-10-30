package Real_estate;

import java.util.logging.Logger;

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

    private static final Logger logger = LoggingUtil.getLogger();

    String city;
    double price;
    int sqm;
    double numberOfRooms;
    Genre genre;

    enum Genre {
        FAMILYHOUSE, CONDOMINIUM, FARM
    }

    /**
     * Constructs a new Real_Estate object.
     *
     * @param city the city where the property is located
     * @param price the price per square meter
     * @param sqm the total square meters of the property
     * @param numberOfRooms the number of rooms in the property
     * @param genre the genre/type of the property
     */
    Real_Estate(String city, double price, int sqm, double numberOfRooms, Genre genre) {
        logger.info(String.format("Creating Real_Estate: %s, Price: %.2f, Sqm: %d, Rooms: %.1f, Genre: %s",
                city, price, sqm, numberOfRooms, genre));

        this.city = city;
        this.price = price;
        this.sqm = sqm;
        this.numberOfRooms = numberOfRooms;
        this.genre = genre;
    }

    /**
     * Gets the city of the property.
     *
     * @return the city name
     */
    String getCity() {
        logger.info("lets get the city: " + this.city);
        return this.city;
    }

    /**
     * Gets the price per square meter.
     *
     * @return the price per square meter
     */
    double getPrice() {
        logger.info("lets get the price: " + this.price);
        return this.price;
    }

    /**
     * Gets the total square meters.
     *
     * @return the square meters
     */
    int getSqm() {
        logger.info("lets get the sqm: " + this.sqm);
        return this.sqm;
    }

    /**
     * Gets the number of rooms.
     *
     * @return the number of rooms
     */
    double getNumberOfRooms() {
        logger.info("Getting number of rooms: " + this.numberOfRooms);
        return this.numberOfRooms;
    }

    /**
     * Gets the genre of the property.
     *
     * @return the genre
     */
    Genre getGenre() {
        logger.info("lets get the genre: " + this.genre);
        return this.genre;
    }

    /**
     * Applies a discount to the property price.
     *
     * @param discount the discount percentage to apply
     */
    @Override
    public void makeDiscount(int discount) {
        logger.info(String.format("Applying %d%% discount to property. Original price: %.2f", discount, this.price));
        this.price = this.price - (this.price * (discount / 100.0));
        logger.info("New price after discount: " + this.price);
    }

    /**
     * Calculates the total price including city-based surcharges.
     *
     * @return the total price as integer
     */
    @Override
    public int getTotalPrice() {
        logger.info("Calculating total price for property in " + this.city);
        int totalPrice = (int) (this.price * this.sqm);

        switch (this.city) {
            case "Budapest":
                totalPrice += totalPrice * 0.30;
                logger.info("Applied 30% Budapest surcharge");
                break;
            case "Debrecen":
                totalPrice += totalPrice * 0.20;
                logger.info("Applied 20% Debrecen surcharge");
                break;
            case "Nyíregyháza":
                totalPrice += totalPrice * 0.15;
                logger.info("Applied 15% Nyíregyháza surcharge");
                break;
        }
        logger.info("Final total price: " + totalPrice);
        return totalPrice;
    }

    /**
     * Calculates the average square meters per room.
     *
     * @return the average square meters per room
     */
    @Override
    public double averageSqmPerRoom() {
        double average = this.sqm / this.numberOfRooms;
        logger.info(String.format("Calculated average sqm per room: %.2f / %.1f = %.2f",
                this.sqm, this.numberOfRooms, average));
        return average;
    }

    /**
     * Returns a string representation of the property.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        logger.info("Generating string representation of property");
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

    private static final Logger logger = LoggingUtil.getLogger();

    int floor;
    boolean isInsulated;

    /**
     * Constructs a new Panel apartment.
     *
     * @param city the city where the panel is located
     * @param price the price per square meter
     * @param sqm the total square meters
     * @param numberOfRooms the number of rooms
     * @param genre the genre of the property
     * @param floor the floor number
     * @param isInsulated whether the panel is insulated
     */
    Panel(String city, double price, int sqm, double numberOfRooms, Genre genre, int floor, boolean isInsulated) {
        super(city, price, sqm, numberOfRooms, genre);
        logger.info(String.format("Creating Panel: Floor: %d, Insulated: %s", floor, isInsulated));
        this.floor = floor;
        this.isInsulated = isInsulated;
    }

    /**
     * Calculates total price with panel-specific adjustments.
     *
     * @return the adjusted total price
     */
    @Override
    public int getTotalPrice() {
        logger.info("Calculating total price for panel apartment");
        int totalPrice = super.getTotalPrice();

        if (this.floor < 2 && this.floor > 0 && this.isInsulated) {
            totalPrice += totalPrice * 0.10;
            logger.info("Applied 10% bonus for insulated first floor");
        } else if (this.floor == 10) {
            totalPrice -= totalPrice * 0.05;
            logger.info("Applied 5% discount for 10th floor");
        }
        logger.info("Final panel total price: " + totalPrice);
        return totalPrice;
    }

    /**
     * Checks if base price equals adjusted price.
     *
     * @return true if prices are equal, false otherwise
     */
    @Override
    public boolean hasSameAmount() {
        boolean same = super.getTotalPrice() == this.getTotalPrice();
        logger.info("Checking if prices are same: " + same);
        return same;
    }

    /**
     * Calculates price per room.
     *
     * @return the price per room
     */
    public double roomPrice() {
        double roomPrice = (double) this.getTotalPrice() / super.numberOfRooms;
        logger.info(String.format("Calculated room price: %.2f", roomPrice));
        return roomPrice;
    }

    /**
     * Gets the floor number.
     *
     * @return the floor number
     */
    public int getFloor(){
        logger.info("Getting floor: " + this.floor);
        return this.floor;
    }

    /**
     * Checks if the panel is insulated.
     *
     * @return true if insulated, false otherwise
     */
    public boolean IsInsulated(){
        logger.info("Checking insulation: " + this.isInsulated);
        return this.isInsulated;
    }

    /**
     * Returns string representation of the panel.
     *
     * @return string representation
     */
    @Override
    public String toString() {
        logger.info("Generating string representation of panel");
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
