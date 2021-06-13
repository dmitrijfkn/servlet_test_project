<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>DeleteDepartment</title>
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
    String id = (String) request.getAttribute("id");
    String name = (String) request.getAttribute("name");
    int id_department = 0;
    if (request.getAttribute("id_department") != null){
        id_department = (int) request.getAttribute("id_department");
    }


    boolean unexpectedError = false;
    boolean successDelete = false;

    if (request.getAttribute("unexpectedError") != null)
        unexpectedError = (boolean) request.getAttribute("unexpectedError");
    if (request.getAttribute("successDelete") != null)
        successDelete = (boolean) request.getAttribute("successDelete");


    if (unexpectedError) { %>
<p style="color:#DB2801">An unexpected error occurred</p><br>
<%} else if (successDelete) {%>
<p style="color:#00AA0E">Employee "<%= name%>" was successful deleted</p><br>
<%} else {%>
<table>
    <tr>Are you sure you want to delete a employee: "<%= name%>"?</tr>
    <tr>
        <td align="center">
            <form action="deleteEmployee" method="post">
                <input type="submit" value=" Yes ">
                <none><input type="text" name=id value="<%= id%>"></none>
            </form>
        </td>
        <td align="center">
            <form action="employeesTable" method="get">
                <input type="submit" value=" No ">
                <%if (request.getAttribute("id_department") != null) {%>
                <none><input type="text" name=id value="<%=id_department%>"></none>
                <%}%>
            </form>
        </td>
    </tr>
</table>
<%}%>
</body>
</html>
