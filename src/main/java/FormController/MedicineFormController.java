package FormController;

import Model.DTO.MedicineDto;
import Service.Medicine.MedicineControl;
import Service.Medicine.MedicineService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class MedicineFormController implements Initializable {

    MedicineService medicineService=new MedicineControl();


    @FXML
    private TableColumn<?, ?> col_brand;

    @FXML
    private TableColumn<?, ?> col_exp_date;

    @FXML
    private TableColumn<?, ?> col_med_id;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private TableColumn<?, ?> col_price;

    @FXML
    private TableColumn<?, ?> col_qty;

    @FXML
    private TableColumn<?, ?> col_supplier_id;

    @FXML
    private TextField txtBrand;

    @FXML
    private TextField txtMediId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private DatePicker txtDate;

    @FXML
    void btnAdd(ActionEvent event) {
        String id = txtMediId.getText();
        String name = txtName.getText();

        if (id.isEmpty() || name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields ( ID & Name )").show();
            return;
        }else{
            String brand = txtBrand.getText();
            String exDate= String.valueOf(txtDate.getValue());
            int quantity= Integer.parseInt(txtQuantity.getText());
            double price= Double.parseDouble(txtPrice.getText());
            String supplierId= txtSupplierId.getText();
            medicineService.addMedicine(new MedicineDto(id, name, brand, exDate, quantity, price, supplierId));
        }

    }

    @FXML
    void btnHomepage(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Order");
        stage.show();
    }

    @FXML
    void btnMedicine(ActionEvent event) throws IOException {

    }

    @FXML
    void btnOrderDetails(ActionEvent event) {

    }

    @FXML
    void btnOrders(ActionEvent event) {

    }

    @FXML
    void btnSupplier(ActionEvent event) {

    }

    @FXML
    void btnUpdate(ActionEvent event) {

    }

    @FXML
    void btnWarnings(ActionEvent event) {

    }

    @FXML
    void btndelete(ActionEvent event) {

    }

    private void clearFields() {
        txtMediId.clear();
        txtName.clear();
        txtBrand.clear();
        txtPrice.clear();
        txtQuantity.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
