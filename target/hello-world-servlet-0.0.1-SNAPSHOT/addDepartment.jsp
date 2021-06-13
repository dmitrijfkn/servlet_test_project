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
    String name = "";
    boolean successfulInsert = false;
    boolean validationError = false;
    boolean repeatingError = false;
    if(request.getAttribute("name")!=null){
        name = (String) request.getAttribute("name");
    }
    if(request.getAttribute("successfulInsert")!=null){
        successfulInsert = (Boolean) request.getAttribute("successfulInsert");
    }
    if(request.getAttribute("validationError")!=null){
        validationError = (Boolean) request.getAttribute("validationError");
    }
    if(request.getAttribute("repeatingError")!=null){
        repeatingError = (Boolean) request.getAttribute("repeatingError");
    }

%>
<%if(validationError) {%>
<p style="color:#DB2801">Department name doesn't not pass validation</p>
<%} if(repeatingError) {%>
<p style="color:#DB2801">Entered department name already exist</p>
<%}%>

<form action="addDepartment" method="post">
    <input type = "text" name = "name" value="<%= name%>" placeholder="Department name"><br>
    <input type="submit" value="Add department">
</form>

<%if(successfulInsert) {%>
<p style="color:#00AA0E">New department has been successfully added</p>
<%}%>
</body>
</html>
