package Model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table (name = "medicines")
public class MedicineDto {

    @Id
    private String medicineId;
    private String name;
    private String brand;
    private String expiryDate;
    private int quantity;
    private double price;
    private String supplierId;

}
