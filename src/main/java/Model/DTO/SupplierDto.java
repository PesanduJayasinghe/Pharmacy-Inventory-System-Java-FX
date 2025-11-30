package Model.DTO;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
@Table(name = "supplier")
public class SupplierDto {

    private String supplierId;
    private String name;
    private String contact;
    private String address;
}
