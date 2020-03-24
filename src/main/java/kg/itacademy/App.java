package kg.itacademy;

import kg.itacademy.entities.Country;
import kg.itacademy.entities.Department;
import kg.itacademy.entities.Employee;
import kg.itacademy.entities.EmployeeAddress;
import kg.itacademy.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Employee e1 = new Employee("Aidin", 19, 1000);
        Employee e2 = new Employee("Askhat", 28, 500);
        Employee e3 = new Employee("Pasha", 28, 2000);
        Employee e4 = new Employee("Dastan", 28, 1200);
        Department itDepartment = new Department("IT");
        Department marketingDepartment = new Department("Marketing");
        Country kg = new Country("KG");
        Country kz = new Country("KZ");
        Country uz = new Country("UZ");
        Country ru = new Country("RU");

        e1.setDepartment(itDepartment);
        e2.setDepartment(marketingDepartment);
        e3.setDepartment(marketingDepartment);
        e4.setDepartment(marketingDepartment);
        e1.setCountry(kg);
        e2.setCountry(kg);
        e3.setCountry(kg);
        e4.setCountry(kg);

        create(kg);
        create(kz);
        create(uz);
        create(ru);
        create(itDepartment);
        create(marketingDepartment);
        create(e1);
        create(e2);
        create(e3);
        create(e4);
//        System.out.println(getAllByDepartmentNameAndCountryName(itDepartment.getName(), kg.getName()));
//        System.out.println(getAllByCountryName(kg.getName()));
        System.out.println(get3BySalary());

        HashMap<Integer, String> map = new HashMap<>();
        map.keySet().stream().filter(x -> x > 5).forEach(x ->map.get(x));
        //HibernateUtil.shutdown();
    }

    public static List<Department> getAllByCountryName(String countryName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Department> departments = session.createQuery("select distinct e.department from Employee e where e.country.name = :p_country_name")
                .setParameter("p_country_name", countryName).list();
        session.close();
        System.out.println("Найдено " + departments.size() + " отделов");
        return departments;
    }
    public static List<Employee> getAllByDepartmentNameAndCountryName(String departmentName, String countryName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("select e from Employee e where e.department.name = :p_dept_name and e.country.name = :p_country_name")
                .setParameter("p_dept_name", departmentName)
                .setParameter("p_country_name", countryName).list();
        session.close();
        System.out.println("Найдено " + employees.size() + " сотрудников");
        return employees;
    }
    public static List<Employee> get3BySalary() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("select e from Employee e order by e.salary desc NULLS last").setMaxResults(3)
                .list();
        session.close();
        System.out.println("Найдено " + employees.size() + " сотрудников");
        return employees;
    }

    public static List<Employee> getAllByDepartmentName(String departmentName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> result =
                session.createQuery("select e from Employee e where e.department.name = :p_dept_name")
                        .setParameter("p_dept_name", departmentName)
                .list();
        session.close();
        return result;
    }

    public static List<Employee> getAllByDepartment(Integer departmentId) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> result =
                session.createQuery("select e from Employee e where e.department.id = :p_dept_id")
                        .setParameter("p_dept_id", departmentId)
                .list();
        session.close();
        return result;
    }

    public static List<Employee> getAllByNameAndAge(String name, Integer age) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> result =
                session.createQuery("FROM Employee where lower(name) like :p_name and age = :p_age")
                .setParameter("p_name", "%" + name.toLowerCase() + "%")
                .setParameter("p_age", age)
                .list();
        session.close();
        return result;
    }


    public static <T> void create(T entity) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        session.saveOrUpdate(entity);
        session.getTransaction().commit();
        session.close();
        System.out.println("Создали запись успешно");
    }
    public static Employee getByLoadId(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = (Employee) session.load(Employee.class, id);
        session.close();
        return employee;
    }

    public static Employee getByGetId(Integer id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Employee employee = (Employee) session.get(Employee.class, id);
        session.close();
        return employee;
    }

    @SuppressWarnings("unchecked")
    public static List<Employee> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("FROM Employee").list();
        session.close();
        System.err.println("Found: " + employees.size() + " employees");
        return employees;
    }

    @SuppressWarnings("unchecked")
    public static List<Employee> getAllByName(String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Employee> employees = session.createQuery("select e FROM Employee e where name = :p_name")
                .setParameter("p_name", name)
                .list();
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
