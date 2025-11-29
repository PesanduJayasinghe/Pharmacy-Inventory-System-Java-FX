package Model.DTO;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SalesDto {

    private String saleId;
    private Date date;
    private Double totalAmount;

}
