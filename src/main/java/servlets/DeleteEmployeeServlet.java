package servlets;

import db.DBManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteEmployee")
public class DeleteEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager dbManager = DBManager.getInstance();
        String idStr = req.getParameter("id");
        String selectedDepartment = req.getParameter("selectedDepartment");

        req.setAttribute("id", idStr);
        if (req.getParameter("selectedDepartment") != null) {
            req.setAttribute("id_department", dbManager.getDepartmentByName(selectedDepartment).getId());
        }

        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        String fullName = "";
        if (dbManager.getEmployeeById(id) != null) {
            fullName = dbManager.getEmployeeById(id).getName() + " " + dbManager.getEmployeeById(id).getSurname();
            req.setAttribute("name", fullName);
        }
        getServletContext().getRequestDispatcher("/deleteEmployee.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager dbManager = DBManager.getInstance();
        String idStr = req.getParameter("id");
        String selectedDepartment = req.getParameter("selectedDepartment");

        req.setAttribute("id", idStr);
        if (req.getParameter("selectedDepartment") != null) {
            req.setAttribute("id_department", dbManager.getDepartmentByName(selectedDepartment).getId());
        }
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        String fullName = "";
        if (dbManager.getEmployeeById(id) != null) {
            fullName = dbManager.getEmployeeById(id).getName() + " " + dbManager.getEmployeeById(id).getSurname();
            req.setAttribute("name", fullName);
        }

        req.setAttribute("successDelete", dbManager.deleteEmployee(id));

        getServletContext().getRequestDispatcher("/deleteEmployee.jsp").forward(req, resp);
    }
}