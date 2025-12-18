package Model.DTO;

import jakarta.persistence.Id;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StockAlertDto {

    @Id
    private int medicineId;
    private String medicineName;
    private String alertType; // "LOW_STOCK" or "EXPIRY"
    private String message;   // e.g. "Paracetamol expires in 5 days"
    private Date expiryDate;
    private int quantity;

}
