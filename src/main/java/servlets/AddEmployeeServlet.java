package servlets;

import db.DBManager;
import entities.Employee;
import org.apache.commons.validator.routines.EmailValidator;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/addEmployee")
public class AddEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "";
        String surname = "";
        String email = "";
        String salary = "";
        String dateOfBirth = "";
        if(req.getParameter("selectedDepartment")!=null){
            String selectedDepartment = req.getParameter("selectedDepartment");
            req.setAttribute("selectedDepartment", selectedDepartment);
        }


        req.setAttribute("name", name);
        req.setAttribute("surname", surname);
        req.setAttribute("email", email);
        req.setAttribute("salary", salary);
        req.setAttribute("dateOfBirth", dateOfBirth);

        DBManager dbManager = DBManager.getInstance();
        req.setAttribute("departments", dbManager.getDepartments());

        getServletContext().getRequestDispatcher("/addEmployee.jsp").forward(req, resp);

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
        req.setAttribute("dateOfBirth", dateOfBirth);
        req.setAttribute("selectedDepartment", selectedDepartment);

        boolean successfulInsert = false;
        DBManager dbManager = DBManager.getInstance();

        req.setAttribute("departments", dbManager.getDepartments());

        if (name == null || name.equals("")) {
            req.setAttribute("nameIsEmpty", true);
        } else if (!dbManager.nameValidation(name)) {
            req.setAttribute("nameValidationError", true);
        } else if (surname == null || surname.equals("")) {
            req.setAttribute("surnameIsEmpty", true);
        } else if (!dbManager.surnameValidation(surname)) {
            req.setAttribute("surnameValidationError", true);
        } else if (email == null || email.equals("")) {
            req.setAttribute("emailIsEmpty", true);
        } else if (!EmailValidator.getInstance().isValid(email)) {
            req.setAttribute("emailValidationError", true);
        } else if (dbManager.emailExist(email)) {
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
                    Employee employee = new Employee(name, surname, email, sal, date);
                    successfulInsert = (dbManager.insertEmployee(employee));
                    employee = dbManager.getEmployeeByEmail(email);
                    successfulInsert = successfulInsert && dbManager.setDepartmentForEmployee(employee, dbManager.getDepartmentByName(selectedDepartment));
                    req.setAttribute("successfulInsert", successfulInsert);
                }
            } catch (ParseException e) {
                req.setAttribute("dateValidationError", true);
            }

        }
        getServletContext().getRequestDispatcher("/addEmployee.jsp").forward(req, resp);

    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
