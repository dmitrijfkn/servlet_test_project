package servlets;

import db.DBManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteDepartment")
public class DeleteDepartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DBManager dbManager = DBManager.getInstance();
        String idStr = req.getParameter("id");
        req.setAttribute("id", idStr);
        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        if (dbManager.getDepartmentById(id) != null) {
            req.setAttribute("name", dbManager.getDepartmentById(id).getName());
        }
        getServletContext().getRequestDispatcher("/deleteDepartment.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        DBManager dbManager = DBManager.getInstance();

        int id = -1;
        try {
            id = Integer.parseInt(idStr);
        } catch (NumberFormatException ex) {
            req.setAttribute("unexpectedError", true);
        }
        if (dbManager.getDepartmentById(id) != null) {
            req.setAttribute("name", dbManager.getDepartmentById(id).getName());
        }

        req.setAttribute("successDelete", dbManager.deleteDepartment(id));

        getServletContext().getRequestDispatcher("/deleteDepartment.jsp").forward(req, resp);
    }
}