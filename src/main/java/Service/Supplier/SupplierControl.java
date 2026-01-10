package Service.Supplier;

import Model.DTO.SupplierDto;
import Repository.Impl.SupplierRepositoryImpl;
import Repository.SupplierRepository;
import javafx.collections.ObservableList;

public class SupplierControl implements SupplierService{

    SupplierRepository supplierRepository= new SupplierRepositoryImpl();

    @Override
    public ObservableList<SupplierDto> getSupplierDetails() {
        return supplierRepository.getSupplierDetails();
    }

    @Override
    public void addSupplier(SupplierDto supplierDto) {
        supplierRepository.addSupplier(supplierDto);
    }

    @Override
    public void updateSupplier(SupplierDto supplierDto) {
        supplierRepository.updateSupplier(supplierDto);
    }

    @Override
    public void deleteSupplier(String text) {
        supplierRepository.deleteSupplier(text);
    }
}
