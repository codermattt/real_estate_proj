package Real_estate;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;


public class RealEstateAgent {
    private static final Logger logger = LoggingUtil.getLogger();

    static TreeSet<Real_Estate> estates = new TreeSet<>(Comparator.comparing(Real_Estate::getCity)
            .thenComparing(Real_Estate::getPrice)
            .thenComparing(Real_Estate::getSqm));

    /**
     * Loads properties from a file.
     *
     * @param filename the path to the file to load
     */
    public static void loadFromFile(String filename) {
        logger.info("Loading properties from file: " + filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;
            while ((line = reader.readLine()) != null) {
                logger.info("Processing line: " + line);
                String[] parts = line.split("#");
                String className = parts[0];
                String city = parts[1];
                double price = Double.parseDouble(parts[2]);
                int sqm = Integer.parseInt(parts[3]);
                double rooms = Double.parseDouble(parts[4]);
                Real_Estate.Genre genre = Real_Estate.Genre.valueOf(parts[5]);

                if (className.equalsIgnoreCase("PANEL")) {
                    int floor = Integer.parseInt(parts[6]);
                    boolean insulated = parts[7].equalsIgnoreCase("yes");
                    estates.add(new Panel(city, price, sqm, rooms, genre, floor, insulated));
                    logger.info("Added Panel property: " + city);
                } else {
                    estates.add(new Real_Estate(city, price, sqm, rooms, genre));
                    logger.info("Added Real_Estate property: " + city);
                }
                count++;
            }
            logger.info("Successfully loaded " + count + " properties");
        } catch (Exception e) {
            logger.severe("Error loading file " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Writes analysis output to a file.
     *
     * @param filename the path to the output file
     */
    public static void writeOutput(String filename) {
        logger.info("Writing output to file: " + filename);
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            // 1️⃣ Average square meter price
            logger.info("Calculating average square meter price");
            double totalPricey = estates.stream()
                    .mapToDouble(e -> e.getPrice())
                    .sum();
            double totalSqm = estates.stream().mapToDouble(Real_Estate::getSqm).sum();
            double avgSqmPrice = totalPricey / totalSqm;
            writer.println("Average square meter price: " + avgSqmPrice);
            logger.info("Average square meter price calculated: " + avgSqmPrice);

            // 2️⃣ Cheapest property
            logger.info("Finding cheapest property");
            Real_Estate cheapest = estates.stream()
                    .min(Comparator.comparing(Real_Estate::getTotalPrice))
                    .orElse(null);
            writer.println("Cheapest property total price: " + cheapest.getTotalPrice());
            logger.info("Cheapest property found with price: " + cheapest.getTotalPrice());

            // 3️⃣ Most expensive apartment in Budapest
            logger.info("Finding most expensive Budapest property");
            Optional<Real_Estate> budapestExpensive = estates.stream()
                    .filter(e -> e.getCity().equalsIgnoreCase("Budapest"))
                    .max(Comparator.comparing(Real_Estate::getPrice));
            if (budapestExpensive.isPresent()) {
                writer.println("Avg sqm/room (most expensive Budapest): " +
                        budapestExpensive.get().averageSqmPerRoom());
                logger.info("Most expensive Budapest property processed");
            }

            // 4️⃣ Total price of all properties
            logger.info("Calculating total price of all properties");
            int totalPrice = estates.stream()
                    .mapToInt(Real_Estate::getTotalPrice)
                    .sum();
            writer.println("Total price of all properties: " + totalPrice);
            logger.info("Total price calculated: " + totalPrice);

            // 5️⃣ Condominiums ≤ average total price
            logger.info("Finding condominiums below average price");
            double avgTotalPrice = totalPrice / estates.size();
            writer.println("Condominiums below average price:");
            estates.stream()
                    .filter(e -> e.getGenre() == Real_Estate.Genre.CONDOMINIUM)
                    .filter(e -> e.getTotalPrice() <= avgTotalPrice)
                    .forEach(e -> {
                        writer.println(e.getCity() + " " + e.getTotalPrice());
                    });
            logger.info("Output file completed successfully");

        } catch (IOException e) {
            logger.severe("Error writing output file " + filename + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
