package servlets;

import db.DBManager;
import entities.Department;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/changeDepartment")
public class ChangeDepartmentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");

        DBManager dbManager = DBManager.getInstance();
        String idStr = req.getParameter("id");
        req.setAttribute("id", idStr);
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        Department department = dbManager.getDepartmentById(id);

        req.setAttribute("name", department.getName());
        getServletContext().getRequestDispatcher("/changeDepartment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");

        DBManager dbManager = DBManager.getInstance();

        String idStr = req.getParameter("id");
        req.setAttribute("id", idStr);
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        Department department = dbManager.getDepartmentById(id);

        String name = req.getParameter("name");

        if (name == null) {
            name = "";
        } else {
            if (!dbManager.departmentNameValidation(name)) {
                req.setAttribute("validationError", true);
            } else if (dbManager.departmentNameExist(name) && dbManager.getDepartmentByName(name).getId()!=department.getId()) {
                req.setAttribute("repeatingError", true);
            } else {
                department.setName(name);
                boolean successfulInsert = dbManager.updateDepartment(department);
                req.setAttribute("successfulInsert", successfulInsert);
            }
        }
        req.setAttribute("name", name);

        getServletContext().getRequestDispatcher("/changeDepartment.jsp").forward(req, resp);

    }
}
