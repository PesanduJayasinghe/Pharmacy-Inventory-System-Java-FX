package Repository.Impl;

import Model.DTO.MedicineDto;
import Repository.WarningRepository;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class WarningRepositoryImpl implements WarningRepository {

    @Override
    public ObservableList<MedicineDto> getExpiringMedicines() {

        ObservableList<MedicineDto> list = FXCollections.observableArrayList();

        try {
            Connection con = DBConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM medicines " +
                            "WHERE expiryDate <= DATE_ADD(CURDATE(), INTERVAL 3 MONTH)"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MedicineDto(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("supplierId")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to load expiring medicines", e);
        }
        return list;
    }

    @Override
    public ObservableList<MedicineDto> getLowStockMedicines() {

        ObservableList<MedicineDto> list = FXCollections.observableArrayList();

        try {
            Connection con = DBConnection.getInstance().getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM medicines WHERE quantity < 50"
            );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MedicineDto(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDate("expiryDate").toLocalDate(),
                        rs.getInt("quantity"),
                        rs.getDouble("price"),
                        rs.getString("supplierId")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to load low stock medicines", e);
        }
        return list;
    }
}
