package FormController;

import Model.DTO.MedicineSale;
import Model.DTO.SalesDto;
import Service.Medicine.MedicineControl;
import Service.Medicine.MedicineService;
import Service.Sales.SalesControl;
import Service.Sales.SalesService;
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
import java.util.List;
import java.util.ResourceBundle;

public class SalesFormController implements Initializable {

    MedicineService medicineService= new MedicineControl();

    SalesService salesService=new SalesControl();

    private ObservableList<MedicineSale> saleItemList = FXCollections.observableArrayList();
    private List<MedicineSale> originalStockList;  // For rollback on cancel

    private Integer quantity;
    private Double price;
    private Double totalAmount=0.0;
    List<MedicineSale> list;

    @FXML
    private AnchorPane buttonPane;

    @FXML
    private TableColumn<?, ?> col_medicine_id;

    @FXML
    private TableColumn<?, ?> col_quantity;

    @FXML
    private TableColumn<?, ?> col_total;

    @FXML
    private ChoiceBox<String> txtMedID;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtSaleDate;

    @FXML
    private TextField txtSaleID;

    @FXML
    private TextField txtTotal;

    @FXML
    private TextField txtTotalAmount;

    @FXML
    private TableView<MedicineSale> tblSalesItem;

    @FXML
    private TableColumn<?, ?> col_medicine_name;

    @FXML
    void btnAdd(ActionEvent event) {
        String id = txtMedID.getValue().substring(0, txtMedID.getValue().indexOf(" - "));
        String name = txtMedID.getValue().split(" - ")[1];
        int qty = Integer.parseInt(txtQuantity.getText());
        double total = Double.parseDouble(txtTotal.getText());

        // 1. Add to table
        saleItemList.add(new MedicineSale(id, name, qty, total));
        tblSalesItem.setItems(saleItemList);

        // 2. Reduce stock in database
        medicineService.updateStock(id, qty);

        // 3. Update front-end total
        totalAmount += total;
        txtTotalAmount.setText(String.valueOf(totalAmount));

        list = medicineService.getMedicineChoiceList();

        // 3. Update prompt text using refreshed list
        updateSelectedMedicineDetails(txtMedID.getValue());

        loadMedicineChoiceBox();

        clearFields();

    }

    @FXML
    void btnCancel(ActionEvent event) {

        // 1. Restore stock for each added sale item
        for (MedicineSale item : saleItemList) {
            // add quantity back to DB
            medicineService.updateStock(
                    item.getMedicineId(),
                    -item.getQuantity()   // negative = add back
            );
        }

        // 2. Clear table data
        saleItemList.clear();
        tblSalesItem.refresh();

        // 3. Reset totals
        totalAmount = 0.0;
        txtTotalAmount.clear();

        // 4. Clear input fields
        clearFields();

        // 5. Reload medicine list (to show correct stock again)
        loadMedicineChoiceBox();

        // 6. Generate new Sale ID
        txtSaleID.setText(saleItemId());

        // Optional info alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cancelled");
        alert.setHeaderText(null);
        alert.setContentText("Sale cancelled. Stock restored.");
        alert.showAndWait();

    }

    @FXML
    void btnConfirm(ActionEvent event) {

        SalesDto sale = new SalesDto(
                txtSaleID.getText(),
                java.sql.Date.valueOf(txtSaleDate.getText()),
                Double.valueOf(txtTotalAmount.getText())
        );

        boolean saved = salesService.saveSale(sale);

        if (saved) {
            new Alert(Alert.AlertType.INFORMATION, "Sale completed!").show();
            saleItemList.clear();
            txtTotalAmount.clear();
            txtSaleID.setText(saleItemId());
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
        stage.setTitle("Sale Items");
        stage.show();
    }

    @FXML
    void btnSales(ActionEvent event) throws IOException {

    }

    @FXML
    void btnSupplier(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/SupplierForm.fxml"));
        stage= (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Supplier");
        stage.show();
    }

    @FXML
    void btnUpdate(ActionEvent event) {
        MedicineSale selected = tblSalesItem.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        int oldQty = selected.getQuantity();

        String id = txtMedID.getValue().substring(0, txtMedID.getValue().indexOf(" - "));
        int newQty = Integer.parseInt(txtQuantity.getText());
        double newTotal = Double.parseDouble(txtTotal.getText());

        // Update table entry
        selected.setQuantity(newQty);
        selected.setPrice(newTotal);
        tblSalesItem.refresh();

        // Update DB stock difference
        int difference = newQty - oldQty;
        medicineService.updateStock(id, difference);

        // Update total amount
        totalAmount = saleItemList.stream().mapToDouble(MedicineSale::getPrice).sum();
        txtTotalAmount.setText(String.valueOf(totalAmount));

        list = medicineService.getMedicineChoiceList();

        // 3. Update prompt text using refreshed list
        updateSelectedMedicineDetails(txtMedID.getValue());

        loadMedicineChoiceBox();

        clearFields();
    }



    @FXML
    void btnWarnings(ActionEvent event) {

    }

    @FXML
    void btndelete(ActionEvent event) {
        MedicineSale selected = tblSalesItem.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        // restore stock because item is deleted
        medicineService.updateStock(selected.getMedicineId(), -selected.getQuantity());

        saleItemList.remove(selected);

        // update total amount
        totalAmount = saleItemList.stream().mapToDouble(MedicineSale::getPrice).sum();
        txtTotalAmount.setText(String.valueOf(totalAmount));

        list = medicineService.getMedicineChoiceList();

        // 3. Update prompt text using refreshed list
        updateSelectedMedicineDetails(txtMedID.getValue());

        loadMedicineChoiceBox();

        clearFields();
    }

    @Override @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

        saveOriginalStock();
        saleItemId();

        LocalDate date = LocalDate.now();
        txtSaleDate.setText(date.toString());

        txtSaleID.setText(saleItemId());

        col_medicine_id.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        col_medicine_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadMedicineChoiceBox();

        txtMedID.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal != null) {
                updateSelectedMedicineDetails(newVal);
            }
        });

        txtQuantity.textProperty().addListener((obs, oldVal, newVal) -> {

            if (newVal == null || newVal.isEmpty()) {
                txtTotal.clear();
                return;
            }

            try {
                int enteredQty = Integer.parseInt(newVal);
                if (enteredQty <= quantity) {
                    calculateTotal();
                } else {
                    showQuantityWarning();
                    txtQuantity.clear();
                }
            } catch (NumberFormatException e) {
                txtQuantity.clear();
            }
        });

        tblSalesItem.getSelectionModel().selectedItemProperty().addListener((observableValue, medicineDto, t1) -> {
            if (t1 != null) {
                txtMedID.setValue(t1.getMedicineId()+" - "+t1.getName());
                txtQuantity.setText(String.valueOf(t1.getQuantity()));
                txtTotal.setText(String.valueOf(t1.getPrice()));
            }
        });
    }

    private void showQuantityWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid Quantity");
        alert.setHeaderText(null);
        alert.setContentText("Quantity cannot be higher than available stock!");
        alert.showAndWait();
    }

    private void updateSelectedMedicineDetails(String selected) {
        String Id = selected.substring(0, selected.indexOf(" - "));

        for (MedicineSale med : list) {
            if (med.getMedicineId().equals(Id)) {
                txtQuantity.setPromptText(String.valueOf(med.getQuantity()));
                price = med.getPrice();
                txtTotal.setPromptText(String.valueOf(price));
                quantity=med.getQuantity();
                break;
            }
        }
    }


    private void saveOriginalStock() {
        originalStockList = medicineService.getMedicineChoiceList();
    }


    private void loadMedicineChoiceBox() {
        txtMedID.getItems().clear();

        list = medicineService.getMedicineChoiceList();

        for (MedicineSale medicine : list) {
            String displayText = medicine.getMedicineId() + " - " + medicine.getName();
            txtMedID.getItems().add(displayText);
        }
    }


    private void calculateTotal(){
        if (price == null){
            return;
        }
        try {
            int qty = Integer.parseInt(txtQuantity.getText());
            double total = qty * price;
            txtTotal.setText(String.valueOf(total));
        } catch (NumberFormatException e) {
            txtTotal.setText(null);
        }

    }

    private String saleItemId(){
        ObservableList<SalesDto> salesDtos=salesService.getAllItems();

        if(salesDtos == null || salesDtos.isEmpty()) {
            return "SAL001";
        }

        String saleId=salesDtos.getLast().getSaleId();
        String part = "SAL";
        String numericPart = saleId.substring(3);

        int nextNumber = Integer.parseInt(numericPart) + 1;
        return part + String.format("%03d", nextNumber);

    }

    private void clearFields() {

        txtMedID.setValue(null);
        txtQuantity.clear();
        txtTotal.clear();
    }


}
