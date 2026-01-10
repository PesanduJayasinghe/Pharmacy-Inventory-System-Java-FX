package Service.SalesItems;

import Model.DTO.SaleItemsDto;
import Repository.Impl.SaleItemRepositoryImpl;
import Repository.SaleItemsRepository;
import javafx.collections.ObservableList;

public class SalesItemControl implements SalesItemService{

    SaleItemsRepository saleItemsRepository=new SaleItemRepositoryImpl();

    @Override
    public ObservableList<SaleItemsDto> getAllSalesItems() {
        return saleItemsRepository.getAllSalesItems();
    }

    @Override
    public void deleteSalesItem(String saleItemId,String medicineId,int quantity,String saleId, Double price) {
        saleItemsRepository.deleteSaleItem(saleItemId,medicineId,quantity,saleId,price);
    }

    @Override
    public void updateSalesItem(SaleItemsDto selected) {
        saleItemsRepository.updateSaleItem(selected);
    }

    @Override
    public void saveSaleItem(SaleItemsDto dto) {
        saleItemsRepository.saveSaleItem(dto);
    }
}
