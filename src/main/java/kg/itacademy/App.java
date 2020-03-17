package kg.itacademy;

import kg.itacademy.entities.Employee;
import kg.itacademy.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Employee e1 = new Employee();
        e1.setId(1);
        e1.setName("Employee1");
        e1.setAge(25);

        create(e1);
    }

    public static void create(Employee employee) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(employee);
        session.getTransaction().commit();
        session.close();
        System.out.println("Создали запись успешно");
    }
}
