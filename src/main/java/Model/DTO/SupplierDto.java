package Model.DTO;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplierDto {

    private String supplierId;
    private String name;
    private String contact;
    private String address;
}
