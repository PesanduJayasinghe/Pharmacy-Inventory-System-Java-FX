package Repository;

import Model.DTO.SaleItemsDto;
import javafx.collections.ObservableList;

public interface SaleItemsRepository {

    ObservableList<SaleItemsDto> getAllSalesItems();

    void deleteSaleItem(String saleItemId,String medicineId,int quantity, String saleId, Double price);

    void updateSaleItem(SaleItemsDto selected);

    void saveSaleItem(SaleItemsDto dto);
}
