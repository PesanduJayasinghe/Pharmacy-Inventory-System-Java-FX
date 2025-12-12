package Model.DTO;

import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierDto {

    @Id
    private String supplierId;
    private String name;
    private String contact;
    private String address;
}
