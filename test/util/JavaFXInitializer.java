package util;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFXInitializer extends Application {

    private static volatile boolean initialized = false;

    @Override
    public void start(Stage primaryStage) {
        // This method is required to start the JavaFX Application Thread.
    }

    public synchronized void init() {
        if (initialized) {
            return; // Prevent multiple initializations
        }

        initialized = true;

        Thread fxThread = new Thread(() -> {
            try {
                // Start the JavaFX runtime
                Application.launch(JavaFXInitializer.class);
            } catch (IllegalStateException e) {
                // JavaFX is already initialized; this exception is expected.
            }
        });
        fxThread.setDaemon(true); // Ensure the thread does not block JVM shutdown
        fxThread.start();

        // Allow some time for JavaFX to initialize
        waitForInitialization();
    }

    public static boolean isInitialized() {
        return initialized;
    }

    private static void waitForInitialization() {
        // Wait for JavaFX Toolkit to be initialized
        try {
            while (!Platform.isFxApplicationThread()) {
                Thread.sleep(10);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore interrupted state
        }
    }
}
