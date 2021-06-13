package servlets;

import db.DBManager;
import entities.Employee;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/employeesTable")
public class EmployeesTableServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = 0;
        boolean parseExs = false;

        if (req.getParameter("id") != null) {
            try {
                id = Integer.parseInt(req.getParameter("id"));
            } catch (NumberFormatException e) {
                parseExs = true;
            }
            if (!parseExs) {
                DBManager dbManager = DBManager.getInstance();
                List<Employee> employees = dbManager.getDepartmentEmployees(dbManager.getDepartmentById(id));

                req.setAttribute("employees", employees);
                req.setAttribute("selectedDepartment", dbManager.getDepartmentById(id).getName());
                getServletContext().getRequestDispatcher("/employeesTable.jsp").forward(req, resp);
            } else {
                req.setAttribute("parseExs", true);
            }

        }else{
            DBManager dbManager = DBManager.getInstance();
            List<Employee> employees = dbManager.getEmployees();

            req.setAttribute("employees", employees);
            getServletContext().getRequestDispatcher("/employeesTable.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
