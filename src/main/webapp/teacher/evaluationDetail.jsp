<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 6/30/2024
  Time: 1:07 AM
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
                    <div class="row">
                        <!-- Dropdown Chọn Năm Học -->
                        <div class="col-lg-6 mb-4">
                            <h4>Tuần từ: <fmt:formatDate value="${requestScope.week.startDate}" pattern="dd/MM/yyyy" /> đến
                                <fmt:formatDate value="${requestScope.week.endDate}" pattern="dd/MM/yyyy" /></h4>
                        </div>
                    </div>
                <div class="card shadow mb-4">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã học sinh</th>
                                    <th>Họ và tên</th>
                                    <th>Thứ 2</th>
                                    <th>Thứ 3</th>
                                    <th>Thứ 4</th>
                                    <th>Thứ 5</th>
                                    <th>Thứ 6</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="pupil" items="${requestScope.listPupil}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${pupil.id}</td>
                                        <td>${pupil.lastName} ${pupil.firstName}</td>
                                        <c:forEach items="${requestScope.dayList}" var="day">
                                            <td>
                                            <c:forEach var="evaluation" items="${requestScope.evaluationList}">
                                                <c:if test="${pupil.id eq evaluation.pupil.id &&
                                                evaluation.date.id eq day.id}">
                                                    ${evaluation.evaluation}

                                                </c:if>
                                            </c:forEach>
                                            </td>
                                        </c:forEach>

                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="btn-group-right float-left">
                                <button type="button" class="btn btn-danger" onclick="backHistory()"  style="width: 100px">Quay lại</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<script>
    function backHistory(){
        window.history.back();
    }
</script>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>
</html>
