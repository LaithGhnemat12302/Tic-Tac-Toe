module org.example.tic_tac_toe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tic_tac_toe to javafx.fxml;
    exports org.example.tic_tac_toe;
}