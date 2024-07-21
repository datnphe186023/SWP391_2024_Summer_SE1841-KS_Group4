<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 6/15/2024
  Time: 6:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Danh sách sự kiện</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <%
        String toastMessage = (String) session.getAttribute("toastMessage");
        String toastType = (String) session.getAttribute("toastType");
        session.removeAttribute("toastMessage");
        session.removeAttribute("toastType");
    %>
    <script>
        $(document).ready(function () {
            var toastMessage = '<%= toastMessage %>';
            var toastType = '<%= toastType %>';
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
    <!-- Custom fonts for this template-->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
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
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Sự Kiện</h1>
                <div class="card shadow mb-4">
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên sự kiện</th>
                                    <th>Ngày</th>
                                    <th>Người nhận</th>
                                    <th>Chi tiết</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="event" items="${requestScope.listEvents}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${event.heading}</td>
                                        <td><fmt:formatDate value="${event.date}" pattern="yyyy/MM/dd"/> </td>
                                        <jsp:useBean id="eventDao" class="models.event.EventDAO"/>
                                        <c:set var="index" value="1"/>
                                        <td>
                                            <c:forEach items="${eventDao.getReceiver(event.id)}" var="i">
                                                <c:if test="${index < eventDao.getReceiver(event.id).size()}">
                                                    <c:if test="${i eq 'teacher'}">Giáo viên</c:if>
                                                    <c:if test="${i eq 'academic staff'}">Giáo vụ</c:if>
                                                    <c:if test="${i eq 'parent'}">Phụ huynh</c:if>
                                                    <c:if test="${i eq 'accountant'}">Kế toán</c:if>,
                                                </c:if>
                                                <c:if test="${index == eventDao.getReceiver(event.id).size()}">
                                                    <c:if test="${i eq 'teacher'}">Giáo viên</c:if>
                                                    <c:if test="${i eq 'academic staff'}">Giáo vụ</c:if>
                                                    <c:if test="${i eq 'parent'}">Phụ huynh</c:if>
                                                    <c:if test="${i eq 'accountant'}">Kế toán</c:if>
                                                </c:if>
                                                <c:set var="index" value="${index+1}"/>
                                            </c:forEach>
                                        </td>
                                        <td class="text-center"><a href="eventDetail?id=${event.id}"
                                                                   class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Chi tiết</a></td>
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
