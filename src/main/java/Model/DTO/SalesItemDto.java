package Model.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SalesItemDto {

    private String saleItemId;
    private String saleId;
    private String medicineId;
    private int quantity;
    private Double price;

}
