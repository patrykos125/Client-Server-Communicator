module com.example.komunikator_klient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.komunikator_klient to javafx.fxml;
    exports com.example.komunikator_klient;
}