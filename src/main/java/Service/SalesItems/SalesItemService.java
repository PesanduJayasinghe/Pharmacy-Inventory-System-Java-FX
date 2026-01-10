package Service.SalesItems;

import Model.DTO.SaleItemsDto;
import javafx.collections.ObservableList;

public interface SalesItemService {

    ObservableList<SaleItemsDto> getAllSalesItems();

    void deleteSalesItem(String saleItemId,String medicineId,int quantity,String saleId, Double price);

    void updateSalesItem(SaleItemsDto selected);

    void saveSaleItem(SaleItemsDto dto);
}
