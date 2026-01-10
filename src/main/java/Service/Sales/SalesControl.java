package Service.Sales;

import Model.DTO.SalesDto;
import Repository.Impl.SalesRepositoryImpl;
import Repository.SalesRepository;
import javafx.collections.ObservableList;

public class SalesControl implements SalesService{

    SalesRepository salesRepository=new SalesRepositoryImpl();

    @Override
    public ObservableList<SalesDto> getAllItems() {
        return salesRepository.getAllItems();
    }

    @Override
    public boolean saveSale(SalesDto sale) {
        return salesRepository.saveSale(sale);
    }
}
