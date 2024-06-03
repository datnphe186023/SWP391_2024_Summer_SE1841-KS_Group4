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
<jsp:include page="dashboard.jsp"/>
<main class="app-content">
    <div class="container my-4">
        <div class="row justify-content-center">
            <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách lớp học</span>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <form action="class"  id="myForm">
                    <div class="year-form">
                        <label >Chọn năm học</label>
                        <select class="form-select"  aria-label="Default select example" onchange="submitForm()" name="schoolYearId">
                            <c:forEach items="${requestScope.schoolYears}" var="year">
                                <option ${requestScope.selectedSchoolYearId eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
                <div class="col-lg-6">
                    <a class="add-button" href="reviewclass?schoolYearId=${requestScope.selectedSchoolYearId}">
                        ĐANG CHỜ PHÊ DUYỆT (${requestScope.numberOfPendingClasses})</a>
                </div>
        </div>
        <div>${requestScope.error}</div>
        <table class="table table-bordered">
            <tr class="table">
                <th scope="col">STT</th>
                <th scope="col">Tên lớp</th>
                <th scope="col">ID Lớp</th>
                <th scope="col">Khối</th>
                <th scope="col">Giáo viên</th>
                <th scope="col">Trạng Thái</th>
                <th scope="col">Tạo Bởi</th>
            </tr>
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
