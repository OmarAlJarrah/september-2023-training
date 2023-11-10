import global.Constants;
import global.TxtFile;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Program {
    private final LRUCache lruCache;
    private final Scanner scanner;
    public Program (LRUCache lruCache) {
        this.lruCache = lruCache;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            System.out.println("Enter an anime rate or name: ");

            // Write to log file -> PROGRAM
            TxtFile.writeToLogFile(Constants.LOG_MESSAGE, "Enter an anime rate or name: ");

            // Check user input if it's Anime rate or name
            if (scanner.hasNextFloat()) {
                // Anime Rate
                float query = scanner.nextFloat();
                scanner.nextLine();

                getAnimeByRate(query);
            }
            else {
                // Anime Name
                String query = scanner.nextLine();

                // Stop program if input is the stop_word
                if (Anime.normalizeName(query).equals(Anime.normalizeName(Constants.STOP_WORD))) {

                    // Write to log file -> USER
                    TxtFile.writeToLogFile(Constants.LOG_ENTER_ANIME_NAME, Constants.STOP_WORD);

                    break;
                }

                float animeRate = getAnimeRateByName(query);

                // Check if Anime doesn't exist
                if (animeRate < 0) {
                    String message = "There is no such anime, If you want to add this anime please enter its rate.\n" +
                            "Enter a floating number between (0 - 10): ";
                    System.out.println(message);

                    // Write to log file -> PROGRAM
                    TxtFile.writeToLogFile(Constants.LOG_MESSAGE, "Enter a floating number between (0 - 10): ");

                    addNewAnime(query);
                }
            }
        }
        scanner.close();
    }

    private void getAnimeByRate(Float userRate) {
        // Write to Log file -> USER
        TxtFile.writeToLogFile(Constants.LOG_ENTER_ANIME_RATE, Float.toString(userRate));

        // Open Anime txt file
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.ANIME_FILE_PATH))) {
            // Check if rate is valid
            Anime.checkRate(userRate);

            String line;
            String[] lineSplit;
            String animeName;
            float animeRate;

            // Reading the file line by line
            while ((line = br.readLine()) != null) {

                // Split line into two parts
                lineSplit = line.split(" ");

                // 1st part is the Anime Name
                animeName = String.join(" ", Arrays.copyOf(lineSplit, lineSplit.length - 1));

                // 2nd part is the Anime Rate
                animeRate = Float.parseFloat(lineSplit[lineSplit.length - 1]);

                if (animeRate >= userRate) {
                    // Write to Log file -> PROGRAM
                    TxtFile.writeToLogFile(Constants.LOG_PRINT_ANIME_NAME, animeName);

                    System.out.println(animeName);
                }
            }

        }
        catch (IllegalArgumentException e) {
            // rate doesn't valid
            System.out.println(e.getMessage());

            // Write to Log file -> PROGRAM
            TxtFile.writeToLogFile(Constants.LOG_PRINT_ERROR, e.getMessage());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private float getAnimeRateByName(String query) {
        // Write to Log file -> USER
        TxtFile.writeToLogFile(Constants.LOG_ENTER_ANIME_NAME, query);

        // Apply standard naming rules on the query
        String processedQuery = Anime.normalizeName(query);

        // Search for Anime in the cache
        if (lruCache.get(processedQuery) != null) {
            // Get anime from the cache
            Anime anime = lruCache.get(processedQuery);
            System.out.println(anime.getRate());

            // Write to Log file -> PROGRAM
            TxtFile.writeToLogFile(Constants.LOG_PRINT_ANIME_RATE, Float.toString(anime.getRate()));

            return anime.getRate();
        }

        // Open Anime txt file
        // Search for Anime in the txt file
        try (BufferedReader br = new BufferedReader(new FileReader(Constants.ANIME_FILE_PATH))) {
            String line;
            String[] lineSplit;
            String animeName;
            float animeRate;

            // Reading the file line by line
            while ((line = br.readLine()) != null) {

                // Split line into two parts
                lineSplit = line.split(" ");

                // 1st part is the Anime Name
                animeName = String.join(" ", Arrays.copyOf(lineSplit, lineSplit.length - 1));

                // 2nd part is the Anime Rate
                animeRate = Float.parseFloat(lineSplit[lineSplit.length - 1]);

                if (Anime.normalizeName(animeName).equals(processedQuery)) {
                    // Add anime to the cache
                    this.lruCache.put(animeName, animeRate);

                    // Print Anime
                    System.out.println(animeRate);

                    // Write to Log file -> PROGRAM
                    TxtFile.writeToLogFile(Constants.LOG_PRINT_ANIME_RATE, Float.toString(animeRate));

                    return animeRate;
                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Write to Log file -> PROGRAM
        TxtFile.writeToLogFile(Constants.LOG_MESSAGE, "There is no such anime, If you want to add this anime please enter its rate.");

        return -1f;
    }

    private void addNewAnime(String name) {
        try {
            // Check if input is a valid rate
            if (scanner.hasNextFloat()) {
                float rate = scanner.nextFloat();
                scanner.nextLine(); // Consume the newline character

                // Write to Log file -> USER
                TxtFile.writeToLogFile(Constants.LOG_ENTER_ANIME_RATE, Float.toString(rate));

                // Check if user input rate is a valid rate
                Anime.checkRate(rate);

                // Add new anime to anime txt file
                TxtFile.writeToAnimeFile(name + " " + rate);

                // Add anime to the cache
                this.lruCache.put(name, rate);

                // Write to Log file -> PROGRAM
                TxtFile.writeToLogFile(Constants.LOG_ADD_NEW_ANIME, name + " | "+ rate);

                System.out.println("New anime added successfully");
            }
            else {
                System.out.println("Rate must be a float number between 0 and 10");
                String wrongInput = scanner.nextLine(); // Consume the invalid input

                // Write to Log file -> USER
                TxtFile.writeToLogFile(Constants.LOG_ENTER_ANIME_RATE, wrongInput);

                // Write to Log file -> PROGRAM
                TxtFile.writeToLogFile(Constants.LOG_PRINT_ERROR, "Rate must be a float number between 0 and 10");
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());

            TxtFile.writeToLogFile(Constants.LOG_PRINT_ERROR, e.getMessage());
        }
    }

}
