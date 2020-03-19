package kg.itacademy.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "employee_address")
public class EmployeeAddress {
    @Id
    @Column(name = "id_employee")
    private Integer id;

    @Column(name = "address")
    private String address;

    public EmployeeAddress() {
    }

    public EmployeeAddress(Integer id, String address) {
        this.id = id;
        this.address = address;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
