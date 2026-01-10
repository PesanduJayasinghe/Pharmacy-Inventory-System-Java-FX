package FormController.Admin;

import Model.DTO.SaleItemsDto;
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
import java.util.ResourceBundle;

public class SaleItemFormController implements Initializable {

    Service.SalesItems.SalesItemService salesItemService=new Service.SalesItems.SalesItemControl();

    @FXML
    private AnchorPane buttonPane;

    @FXML
    private TableColumn<SaleItemsDto, String> col_Medicine_Id;

    @FXML
    private TableColumn<SaleItemsDto, Double> col_Price;

    @FXML
    private TableColumn<SaleItemsDto, Integer> col_Quantity;

    @FXML
    private TableColumn<SaleItemsDto, String> col_Sale_Id;

    @FXML
    private TableColumn<?, ?> col_SaleItem_Id;

    @FXML
    private TableView<SaleItemsDto>  tblSaleItems;

    @FXML
    private TextField txtMedicineId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSaleId;

    @FXML
    private TextField txtSaleItemId;

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
    void btnMedicine(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/Admin/MedicineForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Medicine");
        stage.show();
    }

    @FXML
    void btnSaleItems(ActionEvent event) {

    }

    @FXML
    void btnSales(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/Admin/SalesForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Sales");
        stage.show();
    }

    @FXML
    void btnSupplier(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/Admin/SupplierForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Supplier");
        stage.show();
    }

    @FXML
    void btnWarnings(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/Admin/WarningForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Warnings");
        stage.show();
    }

    @FXML
    void btnAdd(ActionEvent event) {
    }

    @FXML
    void btnUpdate(ActionEvent event) {

        SaleItemsDto selected = tblSaleItems.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        selected.setMedicineId(txtMedicineId.getText());
        selected.setQuantity(Integer.parseInt(txtQuantity.getText()));
        selected.setPrice(Double.parseDouble(txtPrice.getText()));
        selected.setSaleId(txtSaleId.getText());

        salesItemService.updateSalesItem(selected);

        loadSalesItemTable();
        clearFields();
    }

    @FXML
    void btndelete(ActionEvent event) {
        SaleItemsDto selected = tblSaleItems.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        salesItemService.deleteSalesItem(
                selected.getSaleItemId(),
                selected.getMedicineId(),
                selected.getQuantity(),
                selected.getSaleId(),
                selected.getPrice());

        loadSalesItemTable();
        clearFields();
    }

    private void clearFields() {
        txtSaleItemId.clear();
        txtMedicineId.clear();
        txtQuantity.clear();
        txtPrice.clear();
        txtSaleId.clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        txtSaleItemId.setEditable(false);
        txtMedicineId.setEditable(false);
        txtPrice.setEditable(false);
        txtQuantity.setEditable(false);
        txtSaleId.setEditable(false);

        col_SaleItem_Id.setCellValueFactory(new PropertyValueFactory<>("saleItemId"));
        col_Medicine_Id.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        col_Quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_Price.setCellValueFactory(new PropertyValueFactory<>("price"));
        col_Sale_Id.setCellValueFactory(new PropertyValueFactory<>("saleId"));

        loadSalesItemTable();

        tblSaleItems.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                txtSaleItemId.setText(newVal.getSaleItemId());
                txtMedicineId.setText(newVal.getMedicineId());
                txtQuantity.setText(String.valueOf(newVal.getQuantity()));
                txtPrice.setText(String.valueOf(newVal.getPrice()));
                txtSaleId.setText(newVal.getSaleId());
            }
        });
    }

    private void loadSalesItemTable() {
        tblSaleItems.setItems(salesItemService.getAllSalesItems());
    }

}
