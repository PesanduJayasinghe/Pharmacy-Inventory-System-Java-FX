package Service.Medicine;

import Model.DTO.MedicineDto;
import Model.DTO.MedicineSale;
import Repository.Impl.MedicineRepositoryImpl;
import Repository.MedicineRepository;
import javafx.collections.ObservableList;

import java.util.List;

public class MedicineControl implements MedicineService{

    MedicineRepository medicineRepository=new MedicineRepositoryImpl();

    @Override
    public void addMedicine(MedicineDto medicineDto) {
        medicineRepository.addMedicine(medicineDto);
    }

    @Override
    public ObservableList<MedicineDto> getMedicineDetails() {
        return medicineRepository.getMedicineDetails();
    }

    @Override
    public void updateMedicine(MedicineDto medicineDto) {
        medicineRepository.updateMedicine(medicineDto);
    }

    @Override
    public void deleteMedicine(String id) {
        medicineRepository.deleteMedicine(id);
    }

    @Override
    public List<MedicineSale> getMedicineChoiceList() {
       return medicineRepository.getMedicineChoiceList();
    }


}
