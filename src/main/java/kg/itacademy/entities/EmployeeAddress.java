package kg.itacademy.entities;

import javax.persistence.*;

@Entity
@Table(name = "employee_address")
public class EmployeeAddress {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "address")
    private String address;

    public EmployeeAddress() {
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
