package Service.Sales;

import Model.DTO.SalesDto;
import javafx.collections.ObservableList;

public interface SalesService {
    ObservableList<SalesDto> getAllItems();

    boolean saveSale(SalesDto sale);
}
