package Repository;

import Model.DTO.MedicineDto;
import javafx.collections.ObservableList;

public interface WarningRepository {

    ObservableList<MedicineDto> getExpiringMedicines();

    ObservableList<MedicineDto> getLowStockMedicines();

}
