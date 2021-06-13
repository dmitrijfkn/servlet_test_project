<%@ page import="java.util.List" %>
<%@ page import="entities.Department" %>
<%@ page import="entities.Employee" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hello</title>
    <style>
        none {
            display: none
        }
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
    List<Employee> employees = (List) request.getAttribute("employees");
    String selectedDepartment = (String) request.getAttribute("selectedDepartment");
%>

<table border="1">
    <caption>List of employees<%if (selectedDepartment != null) {%> "<%= selectedDepartment%>"<%}%></caption>
    <tr>
        <td align="center">Name</td>
        <td align="center">Surname</td>
        <td align="center">Email</td>
        <td align="center">Salary</td>
        <td align="center">Date of birth</td>
        <td align="center">Change</td>
        <td align="center">Delete</td>
    </tr>
    <% if (employees != null) {
        for (Employee employee : employees) {%>
    <tr>
        <td><%= employee.getName() %>
        </td>
        <td><%= employee.getSurname() %>
        </td>
        <td><%= employee.getEmail() %>
        </td>
        <td><%= employee.getSalary() %>
        </td>
        <td><%= employee.getDateOfBirth() %>
        </td>
        <td align="center">
            <form action="changeEmployee" method="get">
                <input type="submit" value=" change ">
                <none><input type="text" name=id value="<%=employee.getId()%>"></none>
                <none><input type="text" name=selectedDepartment value="<%=selectedDepartment%>"></none>
            </form>
        </td>
        <td align="center">
            <form action="deleteEmployee" method="get">
                <input type="submit" value=" delete ">
                <none><input type="text" name=id value="<%=employee.getId()%>"></none>
                <%if (selectedDepartment != null) {%>
                    <none><input type="text" value="<%= selectedDepartment%>" name="selectedDepartment"></none>
                <%}%>
            </form>
        </td>
    </tr>
    <% }
    } %>
</table>
<%if (selectedDepartment != null) {%>
<form action="addEmployee" method="get">
    <input type="submit" value="Add new employee to this department">
    <none><input type="text" value="<%= selectedDepartment%>" name="selectedDepartment"></none>
</form>
<br>
<%}%>
<form action="addEmployee" method="get">
    <input type="submit" value="Add new employee">
</form>

</body>
</html>
