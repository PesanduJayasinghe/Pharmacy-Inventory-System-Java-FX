package Model.DTO;

import jakarta.persistence.Id;
import lombok.*;
import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class MedicineDto {

    @Id
    private String medicineId;
    private String name;
    private String brand;
    private LocalDate expiryDate;
    private int quantity;
    private double price;
    private String supplierId;

}
