package FormController;

import Model.DTO.SupplierDto;
import Service.Supplier.SupplierControl;
import Service.Supplier.SupplierService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class SupplierFormController implements Initializable {

    SupplierService supplierService=new SupplierControl();


    @FXML
    private AnchorPane buttonPane;

    @FXML
    private TableColumn<?, ?> col_address;

    @FXML
    private TableColumn<?, ?> col_contact;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private TableColumn<?, ?> col_supplier_id;

    @FXML
    private AnchorPane supplierPane;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContact;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TableView<SupplierDto> tblSupplier;


    @FXML
    void btnAdd(ActionEvent event) {
        String id = txtSupplierId.getText();
        String name = txtName.getText();

        if (id.isEmpty() || name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields ( Supplier ID & Name )").show();
        } else {
            String contact = txtContact.getText();
            String address = txtAddress.getText();

            SupplierDto supplierDto = new SupplierDto(id, name, contact, address);

            supplierService.addSupplier(supplierDto);

            loadSupplierTable();
            clearFields();
        }
    }




    @FXML
    void btnHomepage(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/LoginForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Homepage");
        stage.show();
    }

    @FXML
    void btnMedicine(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/MedicineForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Medicine");
        stage.show();
    }

    @FXML
    void btnSaleItems(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/SaleItemForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Sales Items");
        stage.show();
    }

    @FXML
    void btnSales(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/SalesForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Sales");
        stage.show();
    }

    @FXML
    void btnSupplier(ActionEvent event) {

    }

    @FXML
    void btnUpdate(ActionEvent event) {

        String id = txtSupplierId.getText();
        String name = txtName.getText();

        if (id.isEmpty() || name.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please fill all required fields ( Supplier ID & Name )").show();
        } else {
            String contact = txtContact.getText();
            String address = txtAddress.getText();

            SupplierDto supplierDto = new SupplierDto(id, name, contact, address);

            supplierService.updateSupplier(supplierDto);

            loadSupplierTable();
            clearFields();
        }

    }

    @FXML
    void btnWarnings(ActionEvent event) {

    }

    @FXML
    void btndelete(ActionEvent event) {
        supplierService.deleteSupplier(txtSupplierId.getText());
        loadSupplierTable();
        clearFields();
    }

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        col_supplier_id.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        col_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        col_address.setCellValueFactory(new PropertyValueFactory<>("address"));

        loadSupplierTable();

        tblSupplier.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtSupplierId.setText(newVal.getSupplierId());
                txtName.setText(newVal.getName());
                txtContact.setText(newVal.getContact());
                txtAddress.setText(newVal.getAddress());
            }
        });
    }

    private void clearFields() {
        txtSupplierId.clear();
        txtName.clear();
        txtContact.clear();
        txtContact.clear();
    }

    private void loadSupplierTable() {
        tblSupplier.setItems(supplierService.getSupplierDetails());
    }



}
