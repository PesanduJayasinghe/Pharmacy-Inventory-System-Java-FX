package Model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "salesItem")
public class SaleItemsDto {

    private String saleItemId;
    private String saleId;
    private String medicineId;
    private int quantity;
    private Double price;

}
