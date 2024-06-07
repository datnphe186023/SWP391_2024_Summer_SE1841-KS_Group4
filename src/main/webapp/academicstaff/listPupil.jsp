
<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 5/22/2024
  Time: 9:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Quản lý học sinh</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
        $(document).ready(function() {
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
                    <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh</h1>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 d-flex justify-content-between align-items-center">
                            <h6 class="m-0 font-weight-bold text-primary">Danh Sách Lớp Học</h6>
                            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target="#newClassModal">
                                <i class="fas fa-upload"></i> Thêm học sinh
                            </button>

                        </div>
            <div class="card-body">
                <div class="table-responsive">
                <table  class="table table-bordered" id="dataTable">
                    <thead>
                    <tr class="table" >
                        <th>STT</th>
                        <th>Mã học sinh</th>
                        <th>Họ và tên</th>
                        <th>Ngày sinh</th>
                        <th>Trạng thái</th>
                        <th>Hành động</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.listPupil}" var="pupil" varStatus="status">
                        <tr >
                            <th scope="row">${status.index + 1}</th>
                            <td>${pupil.id}</td>
                            <td>${pupil.lastName} ${pupil.firstName}</td>
                            <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                            <c:set value="${pupil.status}" var="status"/>
                            <c:if test="${status eq 'đang theo học'}">
                                <td><span class="badge badge-success">${status}</span></td>
                            </c:if>
                            <c:if test="${status eq 'đã thôi học'}">
                                <td><span class="badge badge-danger">${status}</span> </td>
                            </c:if>
                            <c:if test="${status eq 'đang chờ xử lý'}">
                                <td><span class="badge badge-warning">${status}</span>  </td>
                            </c:if>

                            <td class="text-center"><a href="pupilprofile?id=${pupil.id}" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm ">Thông tin chi tiết</a></td>
                        </tr>
                    </c:forEach>
                    <tbody>
                </table>
                </div>
            </div>
                        </div>
                </div>
                </div>
            <jsp:include page="../footer.jsp"/>
        </div>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>

</body>
</html>


