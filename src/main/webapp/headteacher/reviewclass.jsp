<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 24-May-24
  Time: 6:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản Lý Lớp Học</title>
</head>
<body>
<jsp:include page="dashboard_headteacher.jsp"/>
<main class="app-content">
    <div class="container my-4">
        <div class="row justify-content-center">
            <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách lớp đang chờ duyệt</span>
        </div>
        <table class="table table-bordered">
            <tr class="table">
                <th scope="col">STT</th>
                <th scope="col">Tên lớp</th>
                <th scope="col">ID Lớp</th>
                <th scope="col">Khối</th>
                <th scope="col">Giáo viên</th>
                <th scope="col">Tạo Bởi</th>
                <th scope="col">Hành Động</th>
            </tr>
            <tbody>
            <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>${classes.name}</td>
                    <td>${classes.id}</td>
                    <td>${classes.grade.name}</td>
                    <td>${classes.teacher.lastName} ${classes.teacher.firstName}</td>
                    <td>${classes.createdBy.lastName} ${classes.createdBy.firstName}</td>
                    <td>
                        <a href="reviewclass?id=${classes.id}&action=accept" class="btn-accept">ACCEPT</a>
                        <a href="reviewclass?id=${classes.id}&action=decline" class="btn-decline">DECLINE</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
