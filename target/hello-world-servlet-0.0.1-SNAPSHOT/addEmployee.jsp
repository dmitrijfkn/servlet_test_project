<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.Department" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add</title>
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

    boolean successfulInsert = false;

    String name = (String) request.getAttribute("name");
    String surname = (String) request.getAttribute("surname");
    String email = (String) request.getAttribute("email");
    String salary = (String) request.getAttribute("salary");
    String dateOfBirth = (String) request.getAttribute("dateOfBirth");
    String selectedDepartment = (String) request.getAttribute("selectedDepartment");

    if (request.getAttribute("successfulInsert") != null) {
        successfulInsert = (Boolean) request.getAttribute("successfulInsert");
    }

%>
<form action="addEmployee" method="post">
    <%if (request.getAttribute("nameValidationError") != null) {%>
    <p style="color:#DB2801">Name doesn't not pass validation</p>
    <%
        }
        if (request.getAttribute("nameIsEmpty") != null) {
    %>
    <p style="color:#DB2801">Name shouldn`t be empty</p>
    <%}%>
    <input type="text" name="name" value="<%= name%>" placeholder="Name"><br>
    <%if (request.getAttribute("surnameValidationError") != null) {%>
    <p style="color:#DB2801">Surname doesn't not pass validation</p>
    <%
        }
        if (request.getAttribute("surnameIsEmpty") != null) {
    %>
    <p style="color:#DB2801">Surname shouldn`t be empty</p>
    <%}%>
    <input type="text" name="surname" value="<%= surname%>" placeholder="Surname"><br>
    <%if (request.getAttribute("emailValidationError") != null) {%>
    <p style="color:#DB2801">Email doesn't not pass validation</p>
    <%
        }
        if (request.getAttribute("emailIsEmpty") != null) {
    %>
    <p style="color:#DB2801">Email shouldn`t be empty</p>
    <%
        }
        if (request.getAttribute("emailExist") != null) {
    %>
    <p style="color:#DB2801">Email already registered</p>
    <%}%>
    <input type="text" name="email" value="<%= email%>" placeholder="Email"><br>
    <%if (request.getAttribute("salaryValidationError") != null) {%>
    <p style="color:#DB2801">Salary doesn't not pass validation</p>
    <%
        }
        if (request.getAttribute("salaryIsEmpty") != null) {
    %>
    <p style="color:#DB2801">Salary shouldn`t be empty</p>
    <%}%>
    <input type="number" step="0.01" min="0" name="salary" value="<%= salary%>" placeholder="Salary"><br>
    <%if (request.getAttribute("dateValidationError") != null) {%>
    <p style="color:#DB2801">Date of birth doesn't not pass validation</p>
    <%
        }
        if (request.getAttribute("dateOfBirthIsEmpty") != null) {
    %>
    <p style="color:#DB2801">Date of birth shouldn`t be empty</p>
    <%}%>
    <p>Date of birth:</p>
    <input type="date" name="dateOfBirth" value="<%= dateOfBirth%>"><br>

    <p>Department:</p>
    <select size="1" name="selectedDepartment">
        <% for (Department department : departments) {
            if (department.getName().equals(selectedDepartment)) {%>
        <option value="<%= department.getName()%>" selected ><%= department.getName()%>
        </option>
        <%} else { %>
        <option value="<%= department.getName()%>"><%= department.getName()%>
        </option>
        <%
                }
            }
        %>
    </select><br>

    <input type="submit" value="Add Employee">
</form>

<%if (successfulInsert) {%>
<p style="color:#00AA0E">New employee has been successfully added</p>
<%}%>
</body>
</html>
