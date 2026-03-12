package infrastructure.log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerService {
    
    // The "Filing Cabinet" that maps an Admin's Email to their personal Logger
    private Map<String, Logger> adminLoggers = new HashMap<>();

    public LoggerService() {
        // Empty constructor. We create the loggers dynamically below!
    }

    // HELPER METHOD: Gets or creates a specific text file for the admin
    private Logger getLogger(String adminEmail) {
        // If this admin doesn't have a logger yet, create one
        if (!adminLoggers.containsKey(adminEmail)) {
            try {
                // 1. Create a unique logger for this admin
                Logger newLogger = Logger.getLogger("Logger_" + adminEmail);
                
                // 2. Create a unique text file using their email (e.g., logs_admin@test.com.txt)
                // Note: Emails contain "@" and ".", which are safe for file names in Java!
                FileHandler fileHandler = new FileHandler("logs_" + adminEmail + ".txt", true);
                fileHandler.setFormatter(new SimpleFormatter());
                
                newLogger.addHandler(fileHandler);
                newLogger.setUseParentHandlers(false); // Turn off console duplicate printing
                
                // 3. Save it to our HashMap
                adminLoggers.put(adminEmail, newLogger);

            } catch (IOException e) {
                System.out.println("Failed to create log file for " + adminEmail);
            }
        }
        
        // Return their specific logger
        return adminLoggers.get(adminEmail);
    }

    // UPDATE LOGGING METHODS: Now they require the adminEmail!
    public void logInfo(String adminEmail, String message) {
        getLogger(adminEmail).info(message);
    }

    public void logWarning(String adminEmail, String message) {
        getLogger(adminEmail).warning(message);
    }

    public void logError(String adminEmail, String message) {
        getLogger(adminEmail).severe(message); 
    }

    // UPDATE DISPLAY METHOD: Only show the logs for the specific admin
    public void displayLogs(String adminEmail) {
        System.out.println("\n========= YOUR SYSTEM LOGS =========");
        String fileName = "logs_" + adminEmail + ".txt"; // Look for their specific file

        try {
            List<String> lines = Files.readAllLines(Paths.get(fileName));
            if (lines.isEmpty()) {
                System.out.println("Your log file is currently empty.");
            } else {
                for (String line : lines) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.out.println("Could not find log file. Have you done any actions yet?");
        }
        System.out.println("====================================\n");
    }
}