package db;

import entities.Department;
import entities.Employee;
import org.apache.commons.validator.routines.EmailValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {
    private static final Logger logger = Logger.getLogger(DBManager.class.getName());
    Connection connection;
    String connectionUrl = "jdbc:mysql://localhost:3306/servletsdb?useUnicode=true&serverTimezone=UTC&user=ServletsDbUser&password=qwerty";

    private DBManager(String connectionUrl) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Unable to load class", e);
        }
        try {
            this.connection = getConnection(connectionUrl);
            logger.log(Level.FINE, "New DBManager instance was created");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in constructor", e);
        }
    }

    public static DBManager getInstance() {
        logger.log(Level.FINE, "DBManager constructor called");
        return new DBManager(ConnectionURL.connectionUrl);
    }

    private Connection getConnection(String connectionUrl) throws SQLException {
        return DriverManager.getConnection(connectionUrl);
    }

    public List<Employee> getDepartmentEmployees(Department department) {
        ResultSet resultSet;
        ArrayList<Employee> listOfEmployees = new ArrayList<>();
        ArrayList<Integer> listOfEmployeesById = new ArrayList<>();
        String sqlQuery = "SELECT id_employee FROM employees_department WHERE id_department = ?";
        try ( PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)){
            stmt.setInt(1, department.getId());
            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    listOfEmployeesById.add(resultSet.getInt("id_employee"));
                }
            }
            resultSet.close();
            for (Integer integer : listOfEmployeesById) {
                listOfEmployees.add(getEmployeeById(integer));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in getDepartmentEmployees", e);
            return listOfEmployees;
        }

        return listOfEmployees;
    }

    public List<Department> getDepartments() {
        ResultSet resultSet;
        ArrayList<Department> listOfDepartments = new ArrayList<>();
        String sqlQuery = "SELECT * FROM department";

        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    listOfDepartments.add(new Department(resultSet.getInt("id"), resultSet.getString("name")));
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in getDepartments", e);
            return listOfDepartments;
        }
        return listOfDepartments;
    }

    public List<Employee> getEmployees() {
        ResultSet resultSet;
        ArrayList<Employee> listOfEmployees = new ArrayList<>();
        String sqlQuery = "SELECT * FROM employee";

        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                resultSet.beforeFirst();
                while (resultSet.next()) {
                    listOfEmployees.add(new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                            resultSet.getString("surname"), resultSet.getString("email"),
                            resultSet.getDouble("salary"), resultSet.getDate("date_of_birth")));
                }
            }
            resultSet.close();
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in getDepartments", e);
            return listOfEmployees;
        }
        return listOfEmployees;
    }

    public Employee getEmployeeById(int idEmployee) {
        ResultSet resultSet;

        String sqlQuery = "SELECT * FROM employee WHERE id = ? LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);) {

            stmt.setInt(1, idEmployee);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                Employee employee = new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("email"),
                        resultSet.getDouble("salary"), resultSet.getDate("date_of_birth"));
                resultSet.close();
                return employee;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Sql Exception in getEmployeeById, searched id: %s", idEmployee), e);
        }
        return null;
    }

    public Department getDepartmentById(int idDepartment) {
        ResultSet resultSet;

        String sqlQuery = "SELECT * FROM department WHERE id = ? LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE,
                ResultSet.CONCUR_UPDATABLE);) {

            stmt.setInt(1, idDepartment);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                Department department = new Department(resultSet.getInt("id"), resultSet.getString("name"));
                resultSet.close();
                return department;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Sql Exception in getDepartmentById, searched id: %s", idDepartment), e);
        }
        return null;
    }

    public Employee getEmployeeByEmail(String email) {
        ResultSet resultSet;
        String sqlQuery = "SELECT * FROM employee WHERE email = ?  LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
            stmt.setString(1, email);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                Employee employee = new Employee(resultSet.getInt("id"), resultSet.getString("name"),
                        resultSet.getString("surname"), resultSet.getString("email"),
                        resultSet.getDouble("salary"), resultSet.getDate("date_of_birth"));
                resultSet.close();
                return employee;
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Sql Exception in getEmployeeById, searched email: %s", email), e);
        }
        return null;
    }

    public Department getDepartmentByName(String name) {
        ResultSet resultSet;
        Department department = null;
        String sqlQuery = "SELECT * FROM department WHERE name = ? LIMIT 1";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);){
            stmt.setString(1, name);
            resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                resultSet.first();
                department = new Department(resultSet.getInt("id"), resultSet.getString("name"));
                resultSet.close();
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, String.format("Sql Exception in getDepartmentById, searched name: %s", name), e);
        }
        return department;
    }

    public boolean insertEmployee(Employee employee) {
        if (EmailValidator.getInstance().isValid(employee.getEmail()) && !emailExist(employee.getEmail())) {
            Format f = new SimpleDateFormat("yyyy-MM-dd");
            String strDate = f.format(employee.getDateOfBirth());
            String sqlQuery = "INSERT INTO employee (name , surname, email, salary, date_of_birth) VALUES (?,?,?,?, STR_TO_DATE(?,\'%Y-%m-%d\'))";
            try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);) {
                stmt.setString(1, employee.getName());
                stmt.setString(2, employee.getSurname());
                stmt.setString(3, employee.getEmail());
                stmt.setString(4, new DecimalFormat(".##", new DecimalFormatSymbols(Locale.US)).format(employee.getSalary()));
                stmt.setString(5, strDate);
                stmt.executeUpdate();
                logger.log(Level.FINE, "New employee inserted in db");
                return true;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Sql Exception in insertEmployee", e);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean insertDepartment(Department department) {
        if (departmentNameValidation(department.getName()) && !departmentNameExist(department.getName())) {
            String sqlQuery = "INSERT INTO department (name) VALUES (?)";
            try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                stmt.setString(1, department.getName());
                stmt.executeUpdate();
                logger.log(Level.FINE, "New department inserted in db");
                return true;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Sql Exception in insertDepartment", e);
                return false;
            }
        } else {
            return false;
        }
    }

    public boolean deleteEmployee(int id) {
        String sqlQuery = "DELETE FROM employee WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

            stmt.setInt(1, id);
            stmt.execute();

            logger.log(Level.FINE, "Employee was deleted");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in deleteEmployee", e);
            return false;
        }
    }

    public boolean deleteDepartment(int id) {
        String sqlQuery = "DELETE FROM department WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1, id);
            stmt.execute();

            logger.log(Level.FINE, "Department was deleted");
            return (deleteDepartmentConnections(id));
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in deleteDepartment", e);
            return false;
        }
    }

    public boolean deleteDepartmentConnections(int id) {
        String sqlQuery = "DELETE FROM employees_department WHERE id_department = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1, id);
            stmt.execute();

            logger.log(Level.FINE, "Department connections was deleted");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in deleteDepartmentConnections", e);
            return false;
        }
    }

    public boolean deleteEmployeeConnections(int id) {
        String sqlQuery = "DELETE FROM employees_department WHERE id_employee = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1, id);
            stmt.execute();

            logger.log(Level.FINE, "Employee connections was deleted");
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in deleteEmployeeConnections", e);
            return false;
        }
    }

    public boolean emailExist(String email) {
        String sqlQuery = "SELECT * FROM employee WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setString(1, email);
            ResultSet resultSet = stmt.executeQuery();
            return (resultSet.next());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in emailExist", e);
        }
        return true;
    }

    public boolean departmentNameExist(String departmentName) {
        String sqlQuery = "SELECT * FROM department WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setString(1, departmentName);
            ResultSet resultSet = stmt.executeQuery();
            return (resultSet.next());
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in departmentNameExist", e);
        }
        return true;
    }

    public boolean updateEmployee(Employee employee) {
        Format f = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = f.format(employee.getDateOfBirth());

        String sqlQuery = "UPDATE employee SET name = ? , surname = ?, email = ?, salary = ?, date_of_birth =  STR_TO_DATE(?, '%Y-%m-%d') WHERE id = ?";


        if (EmailValidator.getInstance().isValid(employee.getEmail()) || (!emailExist(employee.getEmail()) || (getEmployeeByEmail(employee.getEmail()).getId() == employee.getId()))) {

            try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {

                stmt.setString(1, employee.getName());
                stmt.setString(2, employee.getSurname());
                stmt.setString(3, employee.getEmail());
                stmt.setString(4, new DecimalFormat(".##", new DecimalFormatSymbols(Locale.US)).format(employee.getSalary()));
                stmt.setString(5, strDate);
                stmt.setInt(6, employee.getId());

                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.log(Level.SEVERE, "Sql Exception in updateEmployee", e);
            }
        }
        return false;
    }

    public boolean updateDepartment(Department department) {
        String sqlQuery = "UPDATE department SET name = ? WHERE id = ?";

        if (!departmentNameExist(department.getName()) || (getDepartmentByName(department.getName()).getId() == department.getId())) {
            try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
                stmt.setString(1, department.getName());
                stmt.setInt(2, department.getId());
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                logger.log(Level.FINE, "Sql Exception in updateDepartment", e);
            }
        }
        return false;
    }

    public boolean setDepartmentForEmployee(Employee employee, Department department) {
        String sqlQuery = "INSERT INTO employees_department (id_employee, id_department) VALUES (? , ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sqlQuery, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE)) {
            stmt.setInt(1, employee.getId());
            stmt.setInt(2, department.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Sql Exception in setDepartmentForEmployee", e);
            return false;
        }
    }


    public boolean nameValidation(String string) {
        return (nameSurnameValidation(string) && (string.length() <= 12));
    }

    public boolean surnameValidation(String string) {
        return (nameSurnameValidation(string) && (string.length() <= 30));
    }

    public boolean nameSurnameValidation(String string) {
        String regex = "^[\\p{L}.'-]+$";
        return (string.matches(regex));
    }

    /*
    Department name validation according to order of the Ministry of Justice of Ukraine dated 05.03.2012 № 368/5
    plus russian ёЁъЪ
    */
    public boolean departmentNameValidation(String string) {
        String regex = "^[0-9а-яёіїєА-ЯЁІЇЄa-zA-Z ,\"'()-?№@!`]+$";
        return (string.matches(regex) && (string.length() <= 30));
    }
}
