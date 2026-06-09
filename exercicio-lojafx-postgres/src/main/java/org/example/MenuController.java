package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MenuController {

    @FXML
    private Button btnBarbeira;

    @FXML
    private Button btnFeira;

    @FXML
    private Button btnFarmacia;

    @FXML
    void trocarTelaBarbearia(ActionEvent event) throws IOException {
        App.setRoot("barbearia");
    }

    @FXML
    void trocarTelaFeira(ActionEvent event) throws IOException {
        App.setRoot("feira");
    }

    @FXML
    void trocarTelaFarmacia(ActionEvent event) throws IOException {
        App.setRoot("farmacia");
    }

}
