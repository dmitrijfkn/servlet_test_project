package servlets;

import db.DBManager;
import entities.Department;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addDepartment")
public class AddDepartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "";

        req.setAttribute("name", name);
        getServletContext().getRequestDispatcher("/addDepartment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");

        String name = req.getParameter("name");

        if (name == null) {
            name = "";
        } else {
            DBManager dbManager = DBManager.getInstance();

            if (!dbManager.departmentNameValidation(name)) {
                req.setAttribute("validationError", true);
            } else if (dbManager.departmentNameExist(name)) {
                req.setAttribute("repeatingError", true);
            } else {
                boolean successfulInsert = dbManager.insertDepartment(new Department(name));
                req.setAttribute("successfulInsert", successfulInsert);
            }
        }
        req.setAttribute("name", name);

        getServletContext().getRequestDispatcher("/addDepartment.jsp").forward(req, resp);

    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
