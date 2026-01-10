package Service.Warnings;

import Model.DTO.MedicineDto;
import Repository.Impl.WarningRepositoryImpl;
import Repository.WarningRepository;
import javafx.collections.ObservableList;

public class WarningControl implements WarningService {

    private final WarningRepository repository = new WarningRepositoryImpl();

    @Override
    public ObservableList<MedicineDto> getExpiringMedicines() {
        return repository.getExpiringMedicines();
    }

    @Override
    public ObservableList<MedicineDto> getLowStockMedicines() {
        return repository.getLowStockMedicines();
    }
}
