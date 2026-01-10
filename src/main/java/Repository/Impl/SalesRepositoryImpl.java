package Repository.Impl;

import Model.DTO.SalesDto;
import Repository.SalesRepository;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SalesRepositoryImpl implements SalesRepository {

    @Override
    public ObservableList<SalesDto> getAllItems() {
        ObservableList<SalesDto> list = FXCollections.observableArrayList();
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM sales");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new SalesDto(
                        rs.getString("saleId"),
                        rs.getDate("date"),
                        rs.getDouble("totalAmount")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch Sales ID: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public boolean saveSale(SalesDto sale) {
        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO sales (saleId, date, totalAmount) VALUES (?, ?, ?)");

            ps.setString(1, sale.getSaleId());
            ps.setDate(2, new java.sql.Date(sale.getDate().getTime()));
            ps.setDouble(3, sale.getTotalAmount());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to save sale: " + e.getMessage(), e);
        }

    }
}
