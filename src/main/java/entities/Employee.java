package entities;

import java.util.Date;
import java.util.Objects;

public class Employee {
    private int id;
    private String name;
    private String surname;
    private String email;
    private double salary;
    private Date dateOfBirth;

    public Employee(String name, String surname, String email, double salary, Date dateOfBirth) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
    }

    public Employee(int id, String name, String surname, String email, double salary, Date dateOfBirth) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.salary = salary;
        this.dateOfBirth = dateOfBirth;
    }

    public int getId() { return id; }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public double getSalary() {
        return salary;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id == employee.id &&
                Double.compare(employee.salary, salary) == 0 &&
                name.equals(employee.name) &&
                surname.equals(employee.surname) &&
                email.equals(employee.email) &&
                dateOfBirth.equals(employee.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, email, salary, dateOfBirth);
    }
}
