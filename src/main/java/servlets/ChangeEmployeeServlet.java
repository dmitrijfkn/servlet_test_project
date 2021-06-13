package servlets;

import db.DBManager;
import entities.Department;
import entities.Employee;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@WebServlet("/changeEmployee")
public class ChangeEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");

        DBManager dbManager = DBManager.getInstance();
        req.setAttribute("departments", dbManager.getDepartments());

        String selectedDepartment = req.getParameter("selectedDepartment");
        req.setAttribute("selectedDepartment", selectedDepartment);

        String idStr = req.getParameter("id");
        req.setAttribute("id", idStr);
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        Employee employee = dbManager.getEmployeeById(id);

        req.setAttribute("name", employee.getName());
        req.setAttribute("surname", employee.getSurname());
        req.setAttribute("email", employee.getEmail());
        req.setAttribute("salary", new DecimalFormat("0.##", new DecimalFormatSymbols(Locale.US)).format(employee.getSalary()));
        req.setAttribute("dateOfBirth", new SimpleDateFormat("yyyy-MM-dd").format(employee.getDateOfBirth()));


        getServletContext().getRequestDispatcher("/changeEmployee.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");

        String name = req.getParameter("name");
        String surname = req.getParameter("surname");
        String email = req.getParameter("email");
        String salary = req.getParameter("salary");
        String dateOfBirth = req.getParameter("dateOfBirth");
        String selectedDepartment = req.getParameter("selectedDepartment");

        req.setAttribute("name", name);
        req.setAttribute("surname", surname);
        req.setAttribute("email", email);
        req.setAttribute("salary", salary);
        req.setAttribute("dateOfBirth", dateOfBirth);
        req.setAttribute("selectedDepartment", selectedDepartment);

        boolean successfulInsert;
        DBManager dbManager = DBManager.getInstance();
        req.setAttribute("departments", dbManager.getDepartments());

        String idStr = req.getParameter("id");
        req.setAttribute("id", idStr);
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        Employee employee = dbManager.getEmployeeById(id);


        if (name == null || name.equals("")) {
            req.setAttribute("nameIsEmpty", true);
        } else if (!dbManager.nameValidation(name)) {
            req.setAttribute("nameValidationError", true);
        } else if (surname == null || surname.equals("")) {
            req.setAttribute("surnameIsEmpty", true);
        }else if (!dbManager.surnameValidation(surname)) {
            req.setAttribute("surnameValidationError", true);
        }else if (email == null || email.equals("")) {
            req.setAttribute("emailIsEmpty", true);
        }  else if (!EmailValidator.getInstance().isValid(email)) {
            req.setAttribute("emailValidationError", true);
        }  else if (dbManager.emailExist(email) && dbManager.getEmployeeByEmail(email).getId() != id) {
            req.setAttribute("emailExist", true);
        } else if (salary == null || salary.equals("")) {
            req.setAttribute("salaryIsEmpty", true);
        } else if (dateOfBirth == null || dateOfBirth.equals("")) {
            req.setAttribute("dateOfBirthIsEmpty", true);
        } else {
            double sal = 0;
            boolean salaryValidationError = false;

            try {
                sal = Double.parseDouble(salary);
            } catch (NumberFormatException ex) {
                req.setAttribute("salaryValidationError", true);
                salaryValidationError = true;
            }
            SimpleDateFormat format = new SimpleDateFormat();
            format.applyPattern("yyyy-MM-dd");

            try {
                Date date = format.parse(dateOfBirth);
                if (!salaryValidationError) {
                    employee.setName(name);
                    employee.setSurname(surname);
                    employee.setEmail(email);
                    employee.setSalary(sal);
                    employee.setDateOfBirth(date);

                    successfulInsert = dbManager.updateEmployee(employee);
                    dbManager.deleteEmployeeConnections(dbManager.getEmployeeByEmail(email).getId());
                    dbManager.setDepartmentForEmployee(dbManager.getEmployeeByEmail(email),dbManager.getDepartmentByName(selectedDepartment));
                    req.setAttribute("successfulInsert", successfulInsert);
                }
            } catch (ParseException e) {
                req.setAttribute("dateValidationError", true);
            }

        }
        getServletContext().getRequestDispatcher("/changeEmployee.jsp").forward(req, resp);

    }
}
