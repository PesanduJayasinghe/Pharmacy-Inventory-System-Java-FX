package FormController.Admin;

import Model.DTO.MedicineSale;
import Model.DTO.SaleItemsDto;
import Model.DTO.SalesDto;
import Service.Medicine.MedicineControl;
import Service.Medicine.MedicineService;
import Service.Sales.SalesControl;
import Service.Sales.SalesService;
import Service.SalesItems.SalesItemControl;
import Service.SalesItems.SalesItemService;
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

    SalesItemService salesItemService=new SalesItemControl();

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
    private TableColumn<?, ?> col_sale_id;

    @FXML
    private TableColumn<?, ?> col_total_of_sales;

    @FXML
    private TableColumn<?, ?> col_date;

    @FXML
    private TableView<SalesDto> tblSales;

    @FXML
    void btnAdd(ActionEvent event) {
        String id = txtMedID.getValue().substring(0, txtMedID.getValue().indexOf(" - "));
        String name = txtMedID.getValue().split(" - ")[1];
        int qty = Integer.parseInt(txtQuantity.getText());
        double total = Double.parseDouble(txtTotal.getText());

        saleItemList.add(new MedicineSale(id, name, qty, total));
        tblSalesItem.setItems(saleItemList);

        medicineService.updateStock(id, qty);

        totalAmount += total;
        txtTotalAmount.setText(String.valueOf(totalAmount));

        list = medicineService.getMedicineChoiceList();

        updateSelectedMedicineDetails(txtMedID.getValue());

        loadMedicineChoiceBox();

        clearFields();

    }

    @FXML
    void btnCancel(ActionEvent event) {

        for (MedicineSale item : saleItemList) {
            medicineService.updateStock(
                    item.getMedicineId(),
                    -item.getQuantity()   // negative = add back
            );
        }

        saleItemList.clear();
        tblSalesItem.refresh();

        totalAmount = 0.0;
        txtTotalAmount.clear();

        clearFields();

        loadMedicineChoiceBox();

        txtSaleID.setText(saleItemId());

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

        if (!saved) {
            new Alert(Alert.AlertType.ERROR, "Sale not saved!").show();
            return;
        }

        String saleId = txtSaleID.getText();
        int index = 1;

        for (MedicineSale item : saleItemList) {

            String saleItemId = saleItemId(index);

            SaleItemsDto dto = new SaleItemsDto(
                    saleItemId,
                    saleId,
                    item.getMedicineId(),
                    item.getQuantity(),
                    item.getPrice()
            );
            salesItemService.saveSaleItem(dto);
            index++;
        }


        saleItemList.clear();
        tblSalesItem.refresh();
        txtTotalAmount.clear();
        txtSaleID.setText(saleItemId());

        new Alert(Alert.AlertType.INFORMATION, "Sale completed successfully!").show();
    }



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
    void btnSaleItems(ActionEvent event) throws IOException {
        Stage stage;
        Parent root= FXMLLoader.load(getClass().getResource("/View/Admin/SaleItemForm.fxml"));
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
        Parent root= FXMLLoader.load(getClass().getResource("/View/Admin/SupplierForm.fxml"));
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

        selected.setQuantity(newQty);
        selected.setPrice(newTotal);
        tblSalesItem.refresh();

        int difference = newQty - oldQty;
        medicineService.updateStock(id, difference);

        totalAmount = saleItemList.stream().mapToDouble(MedicineSale::getPrice).sum();
        txtTotalAmount.setText(String.valueOf(totalAmount));

        list = medicineService.getMedicineChoiceList();

        updateSelectedMedicineDetails(txtMedID.getValue());

        loadMedicineChoiceBox();

        clearFields();
    }

    @FXML
    void btnRefresh(ActionEvent event) {
        txtSaleID.setText(saleItemId());
        txtSaleDate.setText(LocalDate.now().toString());
        txtTotalAmount.clear();
        txtTotalAmount.setPromptText("0.0");

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
    void btndelete(ActionEvent event) {
        MedicineSale selected = tblSalesItem.getSelectionModel().getSelectedItem();
        if (selected == null) return;

        medicineService.updateStock(selected.getMedicineId(), -selected.getQuantity());

        saleItemList.remove(selected);

        totalAmount = saleItemList.stream().mapToDouble(MedicineSale::getPrice).sum();
        txtTotalAmount.setText(String.valueOf(totalAmount));

        list = medicineService.getMedicineChoiceList();

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

        loadMedicineChoiceBox();

        col_medicine_id.setCellValueFactory(new PropertyValueFactory<>("medicineId"));
        col_medicine_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        col_quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        col_total.setCellValueFactory(new PropertyValueFactory<>("price"));


        tblSalesItem.getSelectionModel().selectedItemProperty().addListener((observableValue, medicineDto, t1) -> {
            if (t1 != null) {
                txtMedID.setValue(t1.getMedicineId()+" - "+t1.getName());
                txtQuantity.setText(String.valueOf(t1.getQuantity()));
                txtTotal.setText(String.valueOf(t1.getPrice()));
            }
        });

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

        col_sale_id.setCellValueFactory(new PropertyValueFactory<>("saleId"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_total_of_sales.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        tblSales.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, selected) -> {
                    if (selected != null) {
                        txtSaleID.setText(selected.getSaleId());
                        txtSaleDate.setText(selected.getDate().toString());
                        txtTotalAmount.setText(String.valueOf(selected.getTotalAmount()));

                    }
                }
        );
        loadSalesTable();
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

    private String saleItemId(int index) {
        ObservableList<SaleItemsDto>  salesItemDtos = salesItemService.getAllSalesItems();
        if (salesItemDtos == null || salesItemDtos.isEmpty()) {
            return "SI001";
        }
        String saleItemId = salesItemDtos.getLast().getSaleItemId();
        String part="SI";
        String numericPart=saleItemId.substring(3);

        int number = Integer.parseInt(numericPart)+1;

        return part + String.format("%03d", number);
    }


    private void clearFields() {
        txtMedID.setValue(null);
        txtQuantity.clear();
        txtTotal.clear();
    }

    private void loadSalesTable(){
        tblSales.setItems(salesService.getAllItems());
    }


}
