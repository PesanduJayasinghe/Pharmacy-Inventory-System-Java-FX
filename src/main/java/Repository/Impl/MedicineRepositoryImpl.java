package Repository.Impl;

import Model.DTO.MedicineDto;
import Model.DTO.MedicineSale;
import Repository.MedicineRepository;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepositoryImpl implements MedicineRepository {

    @Override
    public void addMedicine(MedicineDto medicineDto) {

        try {
            Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO medicines (medicineId, brand, expiryDate, name, price, quantity, supplierId) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)");

            ps.setString(1, medicineDto.getMedicineId());
            ps.setString(2, medicineDto.getBrand());
            ps.setString(3, String.valueOf(medicineDto.getExpiryDate()));
            ps.setString(4, medicineDto.getName());
            ps.setDouble(5, medicineDto.getPrice());
            ps.setInt(6, medicineDto.getQuantity());
            ps.setString(7, medicineDto.getSupplierId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add medicine: " + e.getMessage(), e);
        }
    }

    @Override
    public ObservableList<MedicineDto> getMedicineDetails() {
        ObservableList<MedicineDto> list = FXCollections.observableArrayList();
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM medicines");
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
            throw new RuntimeException("Failed to fetch medicine details: " + e.getMessage(), e);
        }

        return list;
    }

    @Override
    public void updateMedicine(MedicineDto medicineDto) {

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("UPDATE medicines SET brand = ?, expiryDate = ?, name = ?, price = ?, quantity = ?, supplierId = ? " +
                    "WHERE medicineId = ?");

            ps.setString(1, medicineDto.getBrand());
            ps.setString(2, String.valueOf(medicineDto.getExpiryDate()));
            ps.setString(3, medicineDto.getName());
            ps.setDouble(4, medicineDto.getPrice());
            ps.setInt(5, medicineDto.getQuantity());
            ps.setString(6, medicineDto.getSupplierId());
            ps.setString(7, medicineDto.getMedicineId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update medicine: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteMedicine(String id) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM medicines WHERE medicineId = ?");
            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete medicine: " + e.getMessage(), e);
        }
    }

    @Override
    public List<MedicineSale> getMedicineChoiceList() {
        List<MedicineSale> list = new ArrayList<>();

        try{
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT medicineId, name, price, quantity FROM medicines");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new MedicineSale(
                        rs.getString("medicineId"),
                        rs.getString("name"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch medicine choices: " + e.getMessage(), e);
        }

        return list;
    }

    @Override
    public void updateStock(String id, int qty) {
        try{
            Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE medicines SET quantity = quantity - ? WHERE medicineId = ? AND quantity >= ?");

            ps.setInt(1, qty);
            ps.setString(2, id);
            ps.setInt(3, qty);

            int updated = ps.executeUpdate();
            if (updated == 0) {
                throw new RuntimeException("Insufficient stock or medicine not found");
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update stock: " + e.getMessage(), e);
        }
    }
}
