package Model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class SaleItemsDto {

    @Id
    private String saleItemId;
    private String saleId;
    private String medicineId;
    private int quantity;
    private Double price;

}
