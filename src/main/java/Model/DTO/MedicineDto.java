package Model.DTO;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MedicineDto {

    private String medicineId;
    private String name;
    private String brand;
    private Date expiryDate;
    private int quantity;
    private double price;
    private String supplierId;

}
