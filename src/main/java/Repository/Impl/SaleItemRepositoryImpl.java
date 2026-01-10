package Repository.Impl;

import Model.DTO.SaleItemsDto;
import Repository.SaleItemsRepository;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SaleItemRepositoryImpl implements SaleItemsRepository {


    @Override
    public ObservableList<SaleItemsDto> getAllSalesItems() {

        ObservableList<SaleItemsDto> list = FXCollections.observableArrayList();

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM salesitem");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new SaleItemsDto(
                        rs.getString("saleItemId"),
                        rs.getString("saleId"),
                        rs.getString("medicineId"),
                        rs.getInt("quantity"),
                        rs.getDouble("price")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch sale items: " + e.getMessage(), e);
        }

        return list;
    }

    @Override
    public void deleteSaleItem(String saleItemId,String medicineId,int quantity,String saleId, Double price) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            con.setAutoCommit(false);

            PreparedStatement ps = con.prepareStatement("DELETE FROM salesitem WHERE saleItemId = ?");
            PreparedStatement ps2= con.prepareStatement("UPDATE medicines SET quantity = quantity + ? WHERE medicineId = ? ");
            PreparedStatement ps3= con.prepareStatement("UPDATE sales SET totalAmount = ROUND(totalAmount - ?, 2) WHERE saleId = ? ");

            ps.setString(1, saleItemId);

            ps2.setObject(1,quantity);
            ps2.setObject(2,medicineId);
            ps2.executeUpdate();

            ps3.setObject(1,price);
            ps3.setObject(2,saleId);
            ps3.executeUpdate();

            ps.executeUpdate();

            con.commit();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete Sale Item: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateSaleItem(SaleItemsDto selected) {

    }

    @Override
    public void saveSaleItem(SaleItemsDto dto) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO salesitem (saleItemId, medicineId, price, quantity, saleId) VALUES (?, ?, ?, ?, ?)"
            );

            ps.setString(1, dto.getSaleItemId());
            ps.setString(2, dto.getMedicineId());
            ps.setDouble(3, dto.getPrice());
            ps.setInt(4, dto.getQuantity());
            ps.setString(5, dto.getSaleId());

            System.out.println(dto);

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save Sale Item: " + e.getMessage(), e);
        }
    }

}
