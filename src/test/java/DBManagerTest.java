import db.DBManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import entities.Department;
import entities.Employee;
import nl.altindag.log.LogCaptor;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

public class DBManagerTest {

    @Test
    public void DBManagerGetInstanceTest() {
        String expectedInfoMessage1 = "DBManager constructor called";
        String expectedInfoMessage2 = "New DBManager instance was created";

        LogCaptor logCaptor = LogCaptor.forClass(DBManager.class);
        DBManager dbManager = DBManager.getInstance();

        assertThat(logCaptor.getLogs())
                .hasSize(2)
                .containsExactly(expectedInfoMessage1, expectedInfoMessage2);
    }

    @Test
    public void InsertEmployeeTest() {
        String expectedInfoMessage1 = "DBManager constructor called";
        String expectedInfoMessage2 = "New DBManager instance was created";
        String expectedInfoMessage3 = "New employee inserted in db";
        String expectedInfoMessage4 = "Employee was deleted";

        LogCaptor logCaptor = LogCaptor.forClass(DBManager.class);
        DBManager dbManager = DBManager.getInstance();
        Employee employee = new Employee("testName", "testSurname", "myName@example.com", 10000, new Date(991785600000L));
        dbManager.insertEmployee(employee);
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail("myName@example.com").getId());
        assertThat(logCaptor.getLogs())
                .hasSize(4)
                .containsExactly(expectedInfoMessage1, expectedInfoMessage2, expectedInfoMessage3, expectedInfoMessage4);
    }

    @Test
    public void InsertDepartmentTest() {
        String expectedInfoMessage1 = "DBManager constructor called";
        String expectedInfoMessage2 = "New DBManager instance was created";
        String expectedInfoMessage3 = "New department inserted in db";
        String expectedInfoMessage4 = "Department was deleted";
        String expectedInfoMessage5 = "Department connections was deleted";

        LogCaptor logCaptor = LogCaptor.forClass(DBManager.class);
        DBManager dbManager = DBManager.getInstance();
        Department department = new Department("testName1");
        dbManager.insertDepartment(department);
        dbManager.deleteDepartment(dbManager.getDepartmentByName(department.getName()).getId());
        assertThat(logCaptor.getLogs())
                .hasSize(5)
                .containsExactly(expectedInfoMessage1, expectedInfoMessage2, expectedInfoMessage3, expectedInfoMessage4, expectedInfoMessage5);
    }

    @Test
    public void emailRepeatCheckTrueTest() {
        DBManager dbManager = DBManager.getInstance();
        Employee employee = new Employee("testName", "testSurname", "myName@example.com", 10000, new Date(991785600000L));
        dbManager.insertEmployee(employee);
        assertTrue(dbManager.emailExist("myName@example.com"));
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail("myName@example.com").getId());
    }

    @Test
    public void emailRepeatCheckFalseTest() {
        DBManager dbManager = DBManager.getInstance();
        assertFalse(dbManager.emailExist("myName@example.com"));
    }

    @Test
    public void departmentNameExistTrueTest() {
        DBManager dbManager = DBManager.getInstance();
        Department department = new Department("testName");
        dbManager.insertDepartment(department);
        assertTrue(dbManager.departmentNameExist("testName"));
        dbManager.deleteDepartment(dbManager.getDepartmentByName(department.getName()).getId());
    }

    @Test
    public void departmentNameExistFalseTest() {
        DBManager dbManager = DBManager.getInstance();
        assertFalse(dbManager.departmentNameExist("testName"));
    }

    @Test
    public void updateEmployeeTest1() {
        DBManager dbManager = DBManager.getInstance();
        Employee employee = new Employee("testName", "testSurname", "myName@example.com", 10000, new Date(991785600000L));
        dbManager.insertEmployee(employee);
        employee = dbManager.getEmployeeByEmail("myName@example.com");
        employee.setName("changedName");
        assertTrue(dbManager.updateEmployee(employee));
        assertEquals(dbManager.getEmployeeByEmail(employee.getEmail()), employee);
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail("myName@example.com").getId());
    }

    @Test
    public void updateEmployeeTest2() {
        DBManager dbManager = DBManager.getInstance();
        Employee employee = new Employee("testName", "testSurname", "myName@example.com", 10000, new Date(991785600000L));
        dbManager.insertEmployee(employee);
        employee = dbManager.getEmployeeByEmail("myName@example.com");
        employee.setName("changedName");
        employee.setEmail("myNameChanged@example.com");
        assertTrue(dbManager.updateEmployee(employee));
        assertEquals(dbManager.getEmployeeByEmail(employee.getEmail()), employee);
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail(employee.getEmail()).getId());
    }

    @Test
    public void updateDepartmentTest() {
        DBManager dbManager = DBManager.getInstance();
        Department department = new Department("testName");
        dbManager.insertDepartment(department);
        department = dbManager.getDepartmentByName("testName");
        department.setName("changedName");
        assertTrue(dbManager.updateDepartment(department));
        assertEquals(dbManager.getDepartmentByName(department.getName()), department);
        dbManager.deleteDepartment(dbManager.getDepartmentByName(department.getName()).getId());
    }

    @Test
    public void connectionTest() {
        DBManager dbManager = DBManager.getInstance();
        Department department = new Department("testName");
        Employee employee1 = new Employee("testName1", "testSurname1", "myName1@example.com", 10000, new Date(991785600000L));
        Employee employee2 = new Employee("testName2", "testSurname2", "myName2@example.com", 10000, new Date(233539200000L));
        Employee employee3 = new Employee("testName3", "testSurname3", "myName3@example.com", 10000, new Date(1019520000000L));
        Employee employee4 = new Employee("testName4", "testSurname4", "myName4@example.com", 10000, new Date(100000000000L));

        dbManager.insertDepartment(department);
        dbManager.insertEmployee(employee1);
        dbManager.insertEmployee(employee2);
        dbManager.insertEmployee(employee3);
        dbManager.insertEmployee(employee4);

        department = dbManager.getDepartmentByName("testName");
        employee1 = dbManager.getEmployeeByEmail(employee1.getEmail());
        employee2 = dbManager.getEmployeeByEmail(employee2.getEmail());
        employee3 = dbManager.getEmployeeByEmail(employee3.getEmail());
        employee4 = dbManager.getEmployeeByEmail(employee4.getEmail());

        dbManager.setDepartmentForEmployee(employee1, department);
        dbManager.setDepartmentForEmployee(employee2, department);
        dbManager.setDepartmentForEmployee(employee3, department);

        ArrayList<Employee> list_got = (ArrayList<Employee>) dbManager.getDepartmentEmployees(department);
        ArrayList<Employee> list_exp = new ArrayList<>();
        list_exp.add(employee1);
        list_exp.add(employee2);
        list_exp.add(employee3);

        assertArrayEquals(list_exp.toArray(), list_got.toArray());
        dbManager.deleteDepartment(dbManager.getDepartmentByName(department.getName()).getId());
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail(employee1.getEmail()).getId());
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail(employee2.getEmail()).getId());
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail(employee3.getEmail()).getId());
        dbManager.deleteEmployee(dbManager.getEmployeeByEmail(employee4.getEmail()).getId());
    }


    @Test
    public void surnameValidationTest() {
        DBManager dbManager = DBManager.getInstance();
        assertTrue(dbManager.surnameValidation("Kostenko"));
        assertTrue(dbManager.surnameValidation("Їжак"));
        assertTrue(dbManager.surnameValidation("Ёжик"));
        assertTrue(dbManager.surnameValidation("Дмитрий"));
        assertTrue(dbManager.surnameValidation("Müller"));
        assertTrue(dbManager.surnameValidation("François"));
        assertTrue(dbManager.surnameValidation("O'Brian"));
        assertTrue(dbManager.surnameValidation("Koch-Mehrin"));
    }

    @Test
    public void departmentNameValidationTest() {
        DBManager dbManager = DBManager.getInstance();
        assertTrue(dbManager.departmentNameValidation("Отдел №9"));
        assertTrue(dbManager.departmentNameValidation("Эко +"));
        assertTrue(dbManager.departmentNameValidation("Ёжик@V-tumane"));
    }

}


