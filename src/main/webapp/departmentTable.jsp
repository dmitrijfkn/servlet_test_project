<%@ page import="java.util.List" %>
<%@ page import="entities.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hello</title>

    <style>
        none{display: none}
    </style>

</head>
<body>
<table>
    <tr>
        <td><a href="index">Main page</a></td>
        <td><a href="departmentTable">List of departments</a></td>
        <td><a href="employeesTable">List of employees</a></td>
    </tr>
</table>

<%
    List<Department> departments = (List) request.getAttribute("departments");
%>

<table border="1">
    <caption>List of departments</caption>
    <tr>
        <td align="center">Name</td>
        <td align="center">Change</td>
        <td align="center">Delete</td>
        <td align="center">List of employees</td>
    </tr>
    <% for (Department department : departments) {%>
    <tr>
        <td><%= department.getName() %></td>
        <td align="center"><form action="changeDepartment" method="get">
            <input type="submit" value=" change ">
            <none><input type="text" name = id value="<%=department.getId()%>"></none>
        </form></td>
        <td align="center"><form action="deleteDepartment" method="get">
            <input type="submit" value=" delete ">
            <none><input type="text" name = id value="<%=department.getId()%>"></none>
        </form></td>
        <td align="center"><form action="employeesTable" method="get">
            <input type="submit" value=" show list">
            <none><input type="text" name = id value="<%=department.getId()%>"></none>
        </form></td>
    </tr>
    <% } %>
</table>
<form action="addDepartment" method="get">
    <input type="submit" value="Add new department">
</form>

</body>
</html>
