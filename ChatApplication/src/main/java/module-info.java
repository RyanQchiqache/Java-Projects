module com.PremierChat {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    // Corrected: Removed trailing dots and fixed package names
    opens com.PremierChat to javafx.fxml;
    exports com.PremierChat;

    // Assuming these are correct package names and they exist in your project
    // Remove or adjust these lines according to your actual package structure

    exports com.PremierChat.clientServerCommunication;
    opens com.PremierChat.clientServerCommunication to javafx.fxml;
}
