package Model.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString


public class SalesDto {

    @Id
    @Column(name = "saleId")
    private String saleId;
    private Date date;
    private Double totalAmount;


}
