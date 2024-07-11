<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 7/9/2024
  Time: 9:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <title>Phiếu bé ngoan</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Phiếu Bé Ngoan</h1>
                <form action="evaluationreport" method="post" id="myForm">
                    <div class="row">
                        <!-- Dropdown Chọn Năm Học -->
                        <div class="col-lg-3 mb-4">
                            <c:set var="yearChecked" value="${requestScope.checkYear}"/>
                            <label for="selectYear">Chọn năm học</label>
                            <select class="custom-select" id="selectYear" aria-label="Default select example" onchange="resetWeekAndSubmitForm()" name="schoolYear">
                                <c:forEach items="${requestScope.listSchoolYear}" var="year">
                                    <option ${yearChecked eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <!-- Dropdown Chọn Tuần -->
                        <div class="col-lg-3 mb-4">
                            <c:set var="weekId" value="${requestScope.checkWeek}"/>
                            <label for="selectWeek">Chọn tuần</label>
                            <select class="custom-select" id="selectWeek" name="week" onchange="submitForm()">
                                <option value="">Chọn tuần</option>
                                <c:forEach var="listWeek" items="${requestScope.listWeeks}">
                                    <option value="${listWeek.id}" <c:if test="${weekId == listWeek.id}">selected</c:if>>
                                        <fmt:formatDate value="${listWeek.startDate}" pattern="dd/MM/yyyy" /> đến
                                        <fmt:formatDate value="${listWeek.endDate}" pattern="dd/MM/yyyy" />
                                    </option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
                <%--This script to reset value of week after select year--%>
                <script>
                    function resetWeekAndSubmitForm() {
                        document.getElementById("selectWeek").selectedIndex = 0;
                        document.getElementById("myForm").submit();
                    }

                    function submitForm() {
                        document.getElementById("myForm").submit();
                    }
                </script>
                <%----%>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <div class="row">
                            <div class="col-md-8">
                                <c:choose>
                                    <c:when test="${requestScope.classes.name == null}">
                                        <h6 class="m-0 font-weight-bold text-primary">Lớp: <a style="color: red">Chưa có lớp</a></h6>
                                    </c:when>
                                    <c:otherwise>
                                        <h6 class="m-0 font-weight-bold text-primary">Lớp: <a>${requestScope.classes.name}</a></h6>
                                    </c:otherwise>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestScope.classes.grade.name == null}">
                                        <h6 class="m-0 font-weight-bold text-primary">Khối: <a style="color: red">Chưa có lớp</a></h6>
                                    </c:when>
                                    <c:otherwise>
                                        <h6 class="m-0 font-weight-bold text-primary">Khối: <a>${requestScope.classes.grade.name}</a></h6>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <c:if test="${requestScope.classes.name != null && weekId  ne '' }">
                                <div class="col-md-4 text-right">
                                    <a class="btn btn-success" href="evaluationdetail?weekId=${weekId}">
                                        <i class="fas fa-fw fa-id-badge"></i> Thông tin chi tiết
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã học sinh</th>
                                    <th>Ảnh</th>
                                    <th>Họ và tên</th>
                                    <th>Ngày sinh</th>
                                    <th>Tổng số buổi nghỉ không phép trong tuần</th>
                                    <th>Số phiếu bé ngoan của trẻ</th>
                                </tr>
                                </thead>
                                <tbody>
                                <jsp:useBean id="evaluationDAO" class="models.evaluation.EvaluationDAO"/>
                                <c:forEach var="pupil" items="${requestScope.listPupil}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${pupil.id}</td>
                                        <td style="width: 20%;">
                                            <img alt="" src="../images/${pupil.avatar}"
                                                 class="mx-auto d-block"
                                                 style="width:100px; height:100px; object-fit: cover;">
                                        </td>
                                        <td>${pupil.lastName} ${pupil.firstName}</td>
                                        <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                                        <td class="text-center align-middle">${evaluationDAO.getNumberOfStatus('Nghỉ học',pupil.id,weekId)}</td>
                                        <td class="text-center align-middle">${evaluationDAO.getNumberOfStatus('Ngoan',pupil.id,weekId)}</td>
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
