package FormController.Cashier;

import Model.DTO.MedicineDto;
import Service.Warnings.WarningControl;
import Service.Warnings.WarningService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CashierWarningFormController implements Initializable {

    @FXML
    private AnchorPane buttonPane;

    @FXML
    private TableColumn<?, ?> col_exp_date;

    @FXML
    private TableColumn<?, ?> col_low_stock;

    @FXML
    private TableColumn<?, ?> col_medicine_id_1;

    @FXML
    private TableColumn<?, ?> col_medicine_id_2;

    @FXML
    private TableColumn<?, ?> col_medicine_name_1;

    @FXML
    private TableColumn<?, ?> col_medicine_name_2;

    @FXML
    private TableView<MedicineDto> tblExpired;

    @FXML
    private TableView<MedicineDto> tblLowStock;

    @FXML
    void btnHomepage(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/Admin/LoginForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Homepage");
        stage.show();
    }

    @FXML
    void btnWarnings(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        WarningService warningService = new WarningControl();

        col_medicine_id_1.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        col_medicine_name_1.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_exp_date.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

        tblExpired.setItems(warningService.getExpiringMedicines());

        col_medicine_id_2.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        col_medicine_name_2.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_low_stock.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tblLowStock.setItems(warningService.getLowStockMedicines());
    }

}
