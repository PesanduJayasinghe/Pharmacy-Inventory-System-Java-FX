package Config;

import Model.DTO.MedicineDto;
import Model.DTO.SalesDto;
import Model.DTO.SalesItemDto;
import Model.DTO.SupplierDto;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Configuration configuration=new Configuration();

    public static SessionFactory getSessionFactory() {

        if (sessionFactory ==null){

            configuration.configure();
            configuration.addAnnotatedClasses(MedicineDto.class);
            configuration.addAnnotatedClasses(SalesDto.class);
            configuration.addAnnotatedClasses(SalesItemDto.class);
            configuration.addAnnotatedClasses(SupplierDto.class);

            sessionFactory=configuration.buildSessionFactory();

        }

        return sessionFactory;
    }
}
