package Repository;

import Model.DTO.SalesDto;
import javafx.collections.ObservableList;

public interface SalesRepository {
    ObservableList<SalesDto> getAllItems();

    boolean saveSale(SalesDto sale);
}
