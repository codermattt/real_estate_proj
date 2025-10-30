package Real_estate;

import java.io.*;
import java.util.*;

public class RealEstateAgent {
    static TreeSet<Real_Estate> estates = new TreeSet<>(Comparator.comparing(Real_Estate::getCity)
            .thenComparing(Real_Estate::getPrice)
            .thenComparing(Real_Estate::getSqm));



    public static void loadFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
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
                } else {
                    estates.add(new Real_Estate(city, price, sqm, rooms, genre));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeOutput(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {

            double totalPricey = estates.stream()
                    .mapToDouble(e -> e.getPrice())
                    .sum();
            double totalSqm =
                    estates.stream().mapToDouble(Real_Estate::getSqm).sum();

            double avgSqmPrice = totalPricey / totalSqm;

            writer.println("Average square meter price: " + avgSqmPrice);
            System.out.println("Average square meter price: " + avgSqmPrice);

            Real_Estate cheapest = estates.stream()
                    .min(Comparator.comparing(Real_Estate::getTotalPrice))
                    .orElse(null);
            writer.println("Cheapest property total price: " + cheapest.getTotalPrice());
            System.out.println("Cheapest property total price: " + cheapest.getTotalPrice());

            Optional<Real_Estate> budapestExpensive = estates.stream()
                    .filter(e -> e.getCity().equalsIgnoreCase("Budapest"))
                    .max(Comparator.comparing(Real_Estate::getPrice));
            if (budapestExpensive.isPresent()) {
                writer.println("Avg sqm/room (most expensive Budapest): " +
                        budapestExpensive.get().averageSqmPerRoom());
                System.out.println("Avg sqm/room (most expensive Budapest): " +
                        budapestExpensive.get().averageSqmPerRoom());
            }

            int totalPrice = estates.stream()
                    .mapToInt(Real_Estate::getTotalPrice)
                    .sum();
            writer.println("Total price of all properties: " + totalPrice);
            System.out.println("Total price of all properties: " + totalPrice);

            double avgTotalPrice = totalPrice / estates.size();
            writer.println("Condominiums below average price:");
            System.out.println("Condominiums below average price:");
            estates.stream()
                    .filter(e -> e.getGenre() == Real_Estate.Genre.CONDOMINIUM)
                    .filter(e -> e.getTotalPrice() <= avgTotalPrice)
                    .forEach(e -> {
                        writer.println(e.getCity() + " " + e.getTotalPrice());
                        System.out.println(e.getCity() + " " + e.getTotalPrice());
                    });



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}