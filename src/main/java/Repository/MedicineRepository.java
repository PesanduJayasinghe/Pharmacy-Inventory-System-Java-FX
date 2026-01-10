package Repository;

import Model.DTO.MedicineDto;
import Model.DTO.MedicineSale;
import javafx.collections.ObservableList;

import java.util.List;

public interface MedicineRepository {

    void addMedicine(MedicineDto medicineDto);

    ObservableList<MedicineDto> getMedicineDetails();

    void updateMedicine(MedicineDto medicineDto);

    void deleteMedicine(String id);

    List<MedicineSale> getMedicineChoiceList();

    void updateStock(String id, int qty);
}
