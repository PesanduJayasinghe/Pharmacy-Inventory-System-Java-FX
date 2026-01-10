package Service.Supplier;

import Model.DTO.SupplierDto;
import javafx.collections.ObservableList;

public interface SupplierService {
    ObservableList<SupplierDto> getSupplierDetails();

    void addSupplier(SupplierDto supplierDto);

    void updateSupplier(SupplierDto supplierDto);

    void deleteSupplier(String text);
}
