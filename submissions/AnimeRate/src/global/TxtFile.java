package global;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TxtFile {
    // Txt File Methods
    public static void writeToAnimeFile(String text) {
        try {
            // Create a FileWriter in append mode (true as the second parameter)
            FileWriter fileWriter = new FileWriter(Constants.ANIME_FILE_PATH, true);

            // Create a BufferedWriter for efficient writing
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Append the new line to the file
            bufferedWriter.write(text);
            bufferedWriter.newLine(); // Add a new line separator

            // Close the BufferedWriter
            bufferedWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToLogFile(int transaction, String log) {
        try {
            // Create a FileWriter object with the option to append to the file (true) or create a new file if it doesn't exist
            FileWriter fileWriter = new FileWriter(Constants.LOG_FILE_PATH, true);

            // Create a BufferedWriter to write text efficiently
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            // Get the current date and time
            LocalDateTime currentTime = LocalDateTime.now();

            // Define a date and time format (e.g., "yyyy-MM-dd HH:mm:ss")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            // Format the current time as a string
            String formattedTime = currentTime.format(formatter);

            // Create log line
            String line = switch (transaction) {
                case Constants.LOG_STOP_PROGRAM -> "[ PROGRAM ] Stop program.";

                case Constants.LOG_START_PROGRAM -> "[ PROGRAM ] Start program.";

                case Constants.LOG_ENTER_ANIME_RATE -> "[ USER ] ( Enter Anime Rate ) " + log;

                case Constants.LOG_ENTER_ANIME_NAME -> "[ USER ] ( Enter Anime Name ) " + log;

                case Constants.LOG_ADD_NEW_ANIME -> "[ PROGRAM ] ( Add New Anime ) " + log;

                case Constants.LOG_PRINT_ANIME_NAME -> "[ PROGRAM ] ( Print Anime Name ) " + log;

                case Constants.LOG_PRINT_ANIME_RATE -> "[ PROGRAM ] ( Print Anime Rate ) " + log;

                case Constants.LOG_PRINT_WARNING -> "[ PROGRAM ] ( WARNING ) " + log;

                case Constants.LOG_PRINT_ERROR -> "[ PROGRAM ] ( ERROR ) " + log;

                case Constants.LOG_MESSAGE -> "[ PROGRAM ] ( MESSAGE ) " + log;

                default -> throw new IllegalArgumentException();
            };

            // Write the text and the formatted time to the file
            bufferedWriter.write(formattedTime + " " + line);
            bufferedWriter.newLine(); // Add a newline character

            // Close the BufferedWriter
            bufferedWriter.close();

        } catch (IOException e) {
            System.err.println("An error occurred: " + e.getMessage());
        }
    }
}
