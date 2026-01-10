package Repository.Impl;

import Model.DTO.SupplierDto;
import Repository.SupplierRepository;
import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SupplierRepositoryImpl implements SupplierRepository {

    @Override
    public ObservableList<SupplierDto> getSupplierDetails() {
        ObservableList<SupplierDto> list = FXCollections.observableArrayList();

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("SELECT * FROM supplier");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new SupplierDto(
                        rs.getString("supplierId"),
                        rs.getString("name"),
                        rs.getString("contact"),
                        rs.getString("address")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to fetch supplier details: " + e.getMessage(), e);
        }
        return list;
    }

    @Override
    public void addSupplier(SupplierDto supplierDto) {

        try {
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO supplier (supplierId, name, address, contact) VALUES (?, ?, ?, ?)");

            ps.setString(1, supplierDto.getSupplierId());
            ps.setString(2, supplierDto.getName());

            ps.setString(3, supplierDto.getAddress());
            ps.setString(4, supplierDto.getContact());


            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add supplier: " + e.getMessage(), e);
        }
    }

    @Override
    public void updateSupplier(SupplierDto supplierDto) {

        try{
            Connection con = DBConnection.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE supplier SET name = ?, address = ?, contact = ? WHERE supplierId = ?");

            ps.setString(1, supplierDto.getName());
            ps.setString(2, supplierDto.getAddress());
            ps.setString(3, supplierDto.getContact());
            ps.setString(4, supplierDto.getSupplierId());

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to update supplier: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteSupplier(String supplierId) {
        try{
            Connection con = DBConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement("DELETE FROM supplier WHERE supplierId = ?");

            ps.setString(1, supplierId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Failed to delete supplier: " + e.getMessage(), e);
        }
    }
}
