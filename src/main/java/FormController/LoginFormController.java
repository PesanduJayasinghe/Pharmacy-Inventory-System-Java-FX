package FormController;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;


public class LoginFormController {

    private AnchorPane currentPane;
    private TextField username;
    private TextField password;

    Stage stage=new Stage();

    @FXML
    private AnchorPane adminPane;

    @FXML
    private AnchorPane btnLoginOption;

    @FXML
    private AnchorPane cashierPane;

    @FXML
    private AnchorPane loginOptionsPane;

    @FXML
    private AnchorPane errorMessagePane;

    @FXML
    private TextField txtAdminPassword;

    @FXML
    private TextField txtAdminUsername;

    @FXML
    private TextField txtCashierPassword;

    @FXML
    private TextField txtCashierUsername;

    @FXML
    void btnAdmin(ActionEvent event) {
        loginOptionsPane.setVisible(false);
        adminPane.setVisible(true);
        username=txtAdminUsername;
        password=txtAdminPassword;
    }

    @FXML
    void btnAdminBack(ActionEvent event) {
        adminPane.setVisible(false);
        loginOptionsPane.setVisible(true);
        username.clear();
        password.clear();
    }

    @FXML
    void btnAdminLogin(ActionEvent event) throws IOException {
        if (txtAdminUsername.getText().equalsIgnoreCase("owner") && txtAdminPassword.getText().equalsIgnoreCase("1234")){
            Parent root= FXMLLoader.load(getClass().getResource("/View/MedicineForm.fxml"));
            stage=(Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Medicines");
            stage.show();

        }else{
            errorMessagePane.setVisible(true);
            adminPane.setVisible(false);
            currentPane=adminPane;
        }
    }

    @FXML
    void btnCashier(ActionEvent event) {
        loginOptionsPane.setVisible(false);
        cashierPane.setVisible(true);
        username=txtCashierUsername;
        password=txtCashierPassword;

    }

    @FXML
    void btnCashierBack(ActionEvent event) {
        cashierPane.setVisible(false);
        loginOptionsPane.setVisible(true);
        username.clear();
        password.clear();
    }

    @FXML
    void btnCashierLogin(ActionEvent event) {
        if (txtCashierUsername.getText().equalsIgnoreCase("user") && txtCashierPassword.getText().equalsIgnoreCase("1234")){

        }else{
            errorMessagePane.setVisible(true);
            cashierPane.setVisible(false);
            currentPane=cashierPane;
        }

    }

    //----------ERROR MESSAGE---------

    @FXML
    void btnMenu(ActionEvent event) {
        errorMessagePane.setVisible(false);
        loginOptionsPane.setVisible(true);
        username.clear();
        password.clear();
    }

    @FXML
    void btnRetry(ActionEvent event) {
        errorMessagePane.setVisible(false);
        username.clear();
        password.clear();
        currentPane.setVisible(true);
    }

}
