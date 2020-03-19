package kg.itacademy;

import kg.itacademy.entities.Employee;
import kg.itacademy.entities.EmployeeAddress;
import kg.itacademy.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Employee e1 = new Employee(3, "I am 3", 19);
        Employee e2 = new Employee(4, "I am 4", 28);
        EmployeeAddress address = new EmployeeAddress(4,"Bishkek");
        create(address);
        e2.setEmployeeAddress(address);
        create(e2);
        //Employee e3 = new Employee(5, "I am 5", 52);
//
//        create(e1);
//        create(e2);
//        create(e3);
//        e2.setName("NEW NAME2");
//        update(e2);
//        deleteById(1);
        deleteAll();
        HibernateUtil.shutdown();
    }

    public static <T> void create(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
        System.out.println("Создали запись успешно");
    }


    @SuppressWarnings("unchecked")
    public static List<Employee> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("FROM Employee").list();
        session.close();
        System.err.println("Found: " + employees.size() + " employees");
        return employees;
    }

    public static void update(Employee e) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Employee em = (Employee) session.load(Employee.class, e.getId());
        em.setName(e.getName());
        em.setAge(e.getAge());
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully updated: " + e);
    }

    public static void deleteById(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Employee em = (Employee) session.load(Employee.class, id);
        session.delete(em);
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted");
    }

    public static void deleteAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        Query query = session.createQuery("delete from Employee");
        query.executeUpdate();
        session.getTransaction().commit();
        session.close();
        System.out.println("Successfully deleted");
    }

}
