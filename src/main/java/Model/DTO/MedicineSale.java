package Model.DTO;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class MedicineSale {

    private String medicineId;
    private String name;
    private int quantity;
    private double price;

    public double getTotal() {
        return price * quantity;
    }

}
