package Model.DTO;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "sales")
public class SalesDto {

    private String saleId;
    private Date date;
    private Double totalAmount;

}
