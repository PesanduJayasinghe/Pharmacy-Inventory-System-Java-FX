package Repository.Impl;

import Config.HibernateUtil;
import Model.DTO.MedicineDto;
import Model.DTO.MedicineSale;
import Repository.MedicineRepository;
import db.DB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepositoryImpl implements MedicineRepository{

    Session session= HibernateUtil.getSessionFactory().openSession();

    @Override
    public void addMedicine(MedicineDto medicineDto) {
        Transaction transaction= session.beginTransaction();
        session.persist(medicineDto);
        transaction.commit();
    }

    @Override
    public ObservableList<MedicineDto> getMedicineDetails() {
        return FXCollections.observableArrayList(
                session.createQuery("FROM MedicineDto", MedicineDto.class).list());
    }

    @Override
    public void updateMedicine(MedicineDto medicineDto) {
        Transaction transaction= session.beginTransaction();
        session.merge(medicineDto);
        transaction.commit();
}

    @Override
    public void deleteMedicine(String id) {
        Transaction transaction= session.beginTransaction();
        session.remove(session.find(MedicineDto.class,id));
        transaction.commit();
    }

    @Override
    public List<MedicineSale> getMedicineChoiceList() {
            List<MedicineSale> list = new ArrayList<>();

            String sql = "SELECT medicineId, name, price, quantity FROM medicines";

            try (Connection con = DB.getInstance().getConnection();
                 PreparedStatement ps = con.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    list.add(new MedicineSale(
                            rs.getString("medicineId"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price")
                    ));
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        return list;
    }


}
