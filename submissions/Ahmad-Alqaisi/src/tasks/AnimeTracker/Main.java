package tasks.AnimeTracker;

import java.io.*;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;


/**
 * This is the main class for the AnimeTracker application.
 */
public class Main {
    // File paths
    private static final String ANIMESET_FILE_PATH = "./src/tasks/AnimeTracker/sample-input.txt";
    private static final String LOG_FILE_PATH = "./src/tasks/AnimeTracker/LOG.txt";

    // Size of the cache
    private static final int CACHE_SIZE = 10;

    /**
     * The main entry point of the AnimeTracker application.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        // Create a pattern for matching floating-point numbers
        Pattern floatingNumbersPat = Pattern.compile("[0-9]+\\.?[0-9]*");

        // Create an LRU cache to store anime ratings
        LRUCache<String, Double> lruCache = new LRUCache<>(CACHE_SIZE);

        // Create a PriorityQueue to store anime data, sorted by rating (highest first)
        PriorityQueue<Anime> setOfAnimeThatOmarHasWatched = new PriorityQueue<>(Comparator.reverseOrder());

        // Read anime rating data from the file and populate the PriorityQueue
        readAnimeRatingFile(setOfAnimeThatOmarHasWatched);

        // Initialize a scanner to read user input
        Scanner scanner = new Scanner(System.in);
        String command;

        // Prompt the user for input
        System.out.print("You can enter an Anime rate, Anime, or \"man of a culture\".\n> ");
        while (!(command = scanner.nextLine().toLowerCase().strip()).equals("man of a culture")) {
            StringBuilder programExecution = new StringBuilder("> " + command + " -> ");

            // Check if the user input matches a floating-point number
            if (floatingNumbersPat.matcher(command).matches()) {
                double rate = Double.parseDouble(command);

                try {
                    // Validate the entered rate is within [0-10]
                    if (rate < 0 || rate > 10) {
                        throw new Exception("Anime rate should be in [0-10].\n");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    programExecution.append("failed: Anime rate should be in [0-10].");

                    // Write the program execution details to the LOG.txt file
                    writeToFile(programExecution + "\n", LOG_FILE_PATH);

                    System.out.print("> ");
                    continue;
                }

                programExecution.append('{');

                // Iterate through anime in the PriorityQueue and print those with a rating >= rate
                setOfAnimeThatOmarHasWatched.forEach(anime -> {
                    if (anime.rate() >= rate) {
                        programExecution.append(anime.anime() + ", ");
                        lruCache.add(anime.anime(), anime.rate());
                        System.out.println(anime.anime());
                    }
                });

                // If no anime matched the criteria, remove the '{' character
                if (programExecution.charAt(programExecution.length() - 1) != '{') {
                    programExecution.delete(programExecution.length() - 2, programExecution.length());
                }
                programExecution.append('}');
            } else {
                String animeName = command;

                // Check if the anime is not in the cache
                if (!lruCache.contains(animeName)) {
                    AtomicReference<Double> rate = new AtomicReference<>(-1.0);

                    // Search for the anime in the PriorityQueue and update the rate if found
                    setOfAnimeThatOmarHasWatched.forEach(anime -> {
                        if (anime.anime().equals(animeName)) {
                            rate.set(anime.rate());
                            lruCache.add(anime.anime(), anime.rate());
                            return;
                        }
                    });

                    if (rate.get() != -1) {
                        System.out.println(rate.get());
                        programExecution.append("from file, `" + animeName + "` rate is " + rate.get());
                    } else {
                        System.out.print("Enter its rate within [0-10]:\n> ");
                        programExecution.append("Enter its rate within [0-10] :> ");

                        try {
                            rate.set(Double.parseDouble(scanner.nextLine()));

                            // Validate the entered rate is within [0-10]
                            if (rate.get() < 0 || rate.get() > 10) {
                                programExecution.append("failed: entered rate is out of [0-10].");
                                throw new Exception("entered rate is out of [0-10].");
                            }
                        } catch (NullPointerException | NumberFormatException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();

                            // Write the program execution details to the LOG.txt file
                            writeToFile(programExecution + "\n", LOG_FILE_PATH);

                            System.out.print("> ");
                            continue;
                        }

                        // Update the anime file with the new entry
                        writeToFile(animeName + " " + rate.get() + "\n", ANIMESET_FILE_PATH);
                        programExecution.append("Anime file has been updated. %s:%.2f added".formatted(animeName, rate.get()));
                        System.out.println("Anime file has been updated. %s:%.2f added".formatted(animeName, rate.get()));

                        // Add the new anime to the cache and PriorityQueue
                        lruCache.add(animeName, rate.get());
                        setOfAnimeThatOmarHasWatched.add(new Anime(animeName, rate.get()));
                    }
                } else {
                    // If the anime is in the cache, retrieve it from the cache
                    if (lruCache.get(animeName).iterator().hasNext()) {
                        Double rate = lruCache.get(animeName).iterator().next();
                        System.out.println(rate);
                        programExecution.append("from cache, `" + animeName + "` rate is " + rate);
                    }
                }
            }

            // Write the program execution details to the LOG.txt file
            writeToFile(programExecution + "\n", LOG_FILE_PATH);

            // Prompt the user for the next input
            System.out.print("> ");
        }
        //Session ended
        writeToFile("> " + command + " -> Session ended\n", LOG_FILE_PATH);
        System.out.println("Session ended.");
    }

    /**
     * Reads anime rating data from a file and populates a PriorityQueue.
     *
     * @param output A PriorityQueue to store the anime data.
     */
    private static void readAnimeRatingFile(PriorityQueue<Anime> output) {
        try {
            // Open the anime rating file for reading
            BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(ANIMESET_FILE_PATH)
            );
            String line;

            // Read each line in the file
            while ((line = bufferedReader.readLine()) != null) {
                // Extract the rating and anime name from each line
                int indexWhereRatingIsStarted = line.lastIndexOf(' ');
                Double rating = Double.parseDouble(line.substring(indexWhereRatingIsStarted + 1));
                String animeName = line.substring(0, indexWhereRatingIsStarted).toLowerCase().strip();

                // Add the anime to the PriorityQueue
                output.add(new Anime(animeName, rating));
            }
            bufferedReader.close(); // Close the file
        } catch (IOException e) {
            System.out.println(e.fillInStackTrace());
        }
    }

    /**
     * Writes the specified output to a file at the given path.
     *
     * @param output The output to write to the file.
     * @param path   The path of the file to write to.
     */
    private static void writeToFile(String output, String path) {
        BufferedWriter bufferedWriter = null;
        try {
            // Open the file for writing (append mode)
            bufferedWriter = new BufferedWriter(new FileWriter(path, true));

            // Write the output to the file
            bufferedWriter.write(output);
            bufferedWriter.flush(); // Flush the buffer to ensure data is written
            bufferedWriter.close(); // Close the file
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
