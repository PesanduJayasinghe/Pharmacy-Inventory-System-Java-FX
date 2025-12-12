package Service.Medicine;

import Model.DTO.MedicineDto;
import Model.DTO.MedicineSale;
import javafx.collections.ObservableList;

import java.util.List;

public interface MedicineService {
    void addMedicine(MedicineDto medicineDto);

    ObservableList<MedicineDto> getMedicineDetails();

    void updateMedicine(MedicineDto medicineDto);

    void deleteMedicine(String text);

    List<MedicineSale> getMedicineChoiceList();

    void updateStock(String id, int qty);
}
