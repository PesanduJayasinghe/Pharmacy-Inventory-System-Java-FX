package Service.Warnings;

import Model.DTO.MedicineDto;
import javafx.collections.ObservableList;

public interface WarningService {

        ObservableList<MedicineDto> getExpiringMedicines();

        ObservableList<MedicineDto> getLowStockMedicines();


}
