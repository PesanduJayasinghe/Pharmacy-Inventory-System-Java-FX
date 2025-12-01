package Service.Medicine;

import Model.DTO.MedicineDto;
import Repository.Impl.MedicineRepositoryImpl;
import Repository.MedicineRepository;

public class MedicineControl implements MedicineService{

    MedicineRepository medicineRepository=new MedicineRepositoryImpl();

    @Override
    public void addMedicine(MedicineDto medicineDto) {
        medicineRepository.addMedicine(medicineDto);
    }



}
