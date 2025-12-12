package FormController;

import Model.DTO.MedicineDto;
import Service.Medicine.MedicineControl;
import Service.Medicine.MedicineService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
    private TableView<MedicineDto> tblMedicine;

    @FXML
    private AnchorPane medicinePane;

    @FXML
    void btnAdd(ActionEvent event) {
        String id = txtMediId.getText();
        String name = txtName.getText();

        if (id.isEmpty() || name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields ( ID & Name )").show();
        }else{
            String brand = txtBrand.getText();
            LocalDate exDate= LocalDate.parse(txtDate.getValue().toString());
            int quantity= Integer.parseInt(txtQuantity.getText());
            double price= Double.parseDouble(txtPrice.getText());
            String supplierId= txtSupplierId.getText();
            medicineService.addMedicine(new MedicineDto(id, name, brand, exDate, quantity, price, supplierId));

            loadMedicineTable();
            clearFields();
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
    void btnSaleItems(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/SaleItemForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Order");
        stage.show();
    }

    @FXML
    void btnSales(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/SalesForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Order");
        stage.show();
    }

    @FXML
    void btnSupplier(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/SupplierForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Order");
        stage.show();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        String id = txtMediId.getText();
        String name = txtName.getText();

        if (id.isEmpty() || name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields ( ID & Name )").show();
        }else{
            String brand = txtBrand.getText();
            LocalDate exDate= LocalDate.parse(txtDate.getValue().toString());
            int quantity= Integer.parseInt(txtQuantity.getText());
            double price= Double.parseDouble(txtPrice.getText());
            String supplierId= txtSupplierId.getText();

            medicineService.updateMedicine(new MedicineDto(id, name, brand, exDate, quantity, price, supplierId));
        }

        loadMedicineTable();
        clearFields();
    }

    @FXML
    void btnWarnings(ActionEvent event) {

    }

    @FXML
    void btndelete(ActionEvent event) {

        medicineService.deleteMedicine(txtMediId.getText());
        loadMedicineTable();
        clearFields();

    }

    private void clearFields() {
        txtMediId.clear();
        txtName.clear();
        txtBrand.clear();
        txtPrice.clear();
        txtQuantity.clear();
        txtSupplierId.clear();
        txtDate.setValue(null);
    }

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        col_med_id.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        col_exp_date.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
        col_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_supplier_id.setCellValueFactory(new PropertyValueFactory<>("supplierId"));


        loadMedicineTable();

        tblMedicine.getSelectionModel().selectedItemProperty().addListener((observableValue, medicineDto, t1) -> {
            if (t1 != null) {
                txtMediId.setText(t1.getMedicineId());
                txtName.setText(t1.getName());
                txtBrand.setText(t1.getBrand());
                txtDate.setValue(LocalDate.parse(t1.getExpiryDate().toString()));
                txtQuantity.setText(String.valueOf(t1.getQuantity()));
                txtPrice.setText(String.valueOf(t1.getPrice()));
                txtSupplierId.setText(t1.getSupplierId());

            }

        });


    }

    private void loadMedicineTable() {

        tblMedicine.setItems(medicineService.getMedicineDetails());
    }
}
