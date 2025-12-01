package Config;

import Model.DTO.MedicineDto;
import Model.DTO.SalesDto;
import Model.DTO.SaleItemsDto;
import Model.DTO.SupplierDto;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Configuration configuration=new Configuration();

    public static SessionFactory getSessionFactory() {

        if (sessionFactory ==null){

            configuration.configure("hibernate.cfg.xml");
            configuration.addAnnotatedClasses(MedicineDto.class);
            configuration.addAnnotatedClasses(SalesDto.class);
            configuration.addAnnotatedClasses(SaleItemsDto.class);
            configuration.addAnnotatedClasses(SupplierDto.class);

            sessionFactory=configuration.buildSessionFactory();

        }

        return sessionFactory;
    }
}
