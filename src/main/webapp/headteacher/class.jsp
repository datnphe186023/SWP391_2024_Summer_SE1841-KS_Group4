<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 24-May-24
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Quản Lý Lớp Học</title>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
</head>
<body>
<jsp:include page="dashboard_headteacher.jsp"/>
<main class="app-content">
    <div class="container my-4">
        <div class="text-center mb-3">
            <h2>Danh Sách Lớp Học</h2>
        </div>
        <div class="term-select-container mb-3">
            <div class="form-group">
                <label for="schoolYearSelect">Chọn Năm Học</label>
                <select class="form-control" id="schoolYearSelect" name="schoolYearId" onchange="submitForm()">
                    <c:forEach var="year" items="${requestScope.schoolYears}">
                        <option value="${year.id}" <c:if test="${year.id.equals(requestScope.selectedSchoolYearId)}">selected</c:if>>${year.name}</option>
                    </c:forEach>
                </select>
            </div>
            <button class="btn approve-btn">ĐANG CHỜ PHÊ DUYỆT (6)</button>
        </div>
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th scope="col">STT</th>
                <th scope="col">Tên lớp</th>
                <th scope="col">ID Lớp</th>
                <th scope="col">Khối</th>
                <th scope="col">Giáo viên</th>
                <th scope="col">Trạng Thái</th>
                <th scope="col">Tạo Bởi</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>${classes.name}</td>
                    <td>${classes.id}</td>
                    <td>${classes.grade.name}</td>
                    <td>${classes.teacher.lastName} ${classes.teacher.firstName}</td>
                    <td>${classes.status}</td>
                    <td>${classes.createdBy.lastName} ${classes.createdBy.firstName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</main>
</body>
</html>
