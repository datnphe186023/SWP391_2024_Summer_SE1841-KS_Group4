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
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Lớp Học</h1>
                <div class="row">
                    <div class="col-lg-6">
                        <form action="class" id="myForm">
                            <div class="year-form">
                                <label>Chọn năm học</label>
                                <select class="form-select" aria-label="Default select example" onchange="submitForm()"
                                        name="schoolYearId">
                                    <c:forEach items="${requestScope.schoolYears}" var="year">
                                        <option ${requestScope.selectedSchoolYearId eq year.id ? "selected" : ""}
                                                value="${year.id}">${year.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                    </div>
                    <div class="col-lg-6">
                        <a class="btn btn-primary" href="reviewclass?schoolYearId=${requestScope.selectedSchoolYearId}">
                            ĐANG CHỜ PHÊ DUYỆT (${requestScope.numberOfPendingClasses})</a>
                    </div>
                </div>
                <div>${requestScope.error}</div>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh Sách Lớp Học</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên lớp</th>
                                    <th>Khối</th>
                                    <th>Giáo viên</th>
                                    <th>Trạng Thái</th>
                                    <th>Tạo Bởi</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${classes.name}</td>
                                        <td>${classes.grade.name}</td>
                                        <td>
                                            <c:if test="${classes.teacher.id == null}">
                                                Chưa được sắp xếp
                                            </c:if>
                                            <c:if test="${classes.teacher != null}">
                                                ${classes.teacher.lastName} ${classes.teacher.firstName}
                                            </c:if>
                                        </td>
                                        <td>${classes.status}</td>
                                        <td>${classes.createdBy.lastName} ${classes.createdBy.firstName}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>
</html>
