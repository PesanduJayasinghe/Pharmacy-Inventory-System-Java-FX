package Repository.Impl;

import Config.HibernateUtil;
import Model.DTO.MedicineDto;
import Repository.MedicineRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class MedicineRepositoryImpl implements MedicineRepository{

    Session session= HibernateUtil.getSessionFactory().openSession();

    @Override
    public void addMedicine(MedicineDto medicineDto) {
        Transaction transaction= session.beginTransaction();
        session.persist(medicineDto);
        transaction.commit();
    }
}
