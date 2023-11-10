import global.Constants;
import global.TxtFile;

public class Main {
    public static void main(String[] args) {
        // Write to Log file -> PROGRAM
        TxtFile.writeToLogFile(Constants.LOG_START_PROGRAM, "");

        // Create new program
        Program program = new Program(new LRUCache(Constants.LRU_CAPACITY));

        // Start program
        program.run();

        // Write to Log file -> PROGRAM
        TxtFile.writeToLogFile(Constants.LOG_STOP_PROGRAM, "");
    }
}