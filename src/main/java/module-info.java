module com.example.pacmanoblig {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.pacmanoblig to javafx.fxml;
    exports com.example.pacmanoblig;
    exports com.example.pacmanoblig.Ghosts;
    opens com.example.pacmanoblig.Ghosts to javafx.fxml;
    exports com.example.pacmanoblig.GameObjects;
    opens com.example.pacmanoblig.GameObjects to javafx.fxml;
}