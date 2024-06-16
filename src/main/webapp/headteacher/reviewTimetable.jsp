<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 24-May-24
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
    <head>
        <title>Quản Lý Lớp Học</title>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <style>
            .btn-custom-width {
                width: 120px; /* Adjust the width as needed */
            }
        </style>
        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Thời Khóa Biểu</h1>


                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Thời Khóa Biểu</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>Tên lớp</th>
                                                <th>Tạo Bởi</th>
                                                <th>Hiệu lực</th>
                                                <th>Trạng thái</th>
                                                <th>Giáo Viên</th>

                                                <th>Hành động</th>
                                            </tr>


                                        </thead>
                                        <tbody>
                                            <c:forEach var="listTimetable" items="${requestScope.listTimetable}">
                                                <tr>
                                                    <td>${listTimetable.aClass.name}</td>
                                                    <td>${listTimetable.createdBy.lastName} ${listTimetable.createdBy.firstName}</td>
                                                    <td>
                                                        ${listTimetable.startDate} đến ${listTimetable.endDate}
                                                    </td>
                                                    <td style="color: <c:choose>
                                                            <c:when test="${listTimetable.status eq 'chưa xét duyệt'}">red</c:when>
                                                        </c:choose>;">
                                                        ${listTimetable.status}
                                                    </td>
                                                    <td>${listTimetable.teacher.lastName} ${listTimetable.teacher.firstName}</td>
                                                    <c:if test="${listTimetable.status eq 'chưa xét duyệt'}">
                                                        <td>
                                                            <div class="d-flex flex-column align-items-center">
                                                                <form method="post" action="#" class="d-inline mb-2">
                                                                    <input type="hidden" name="action" value="accept">
                                                                    <input type="hidden" name="id" >
                                                                    <button type="submit" class="btn btn-sm btn-success shadow-sm btn-custom-width">Chấp nhận</button>
                                                                </form>

                                                                <form method="post" action="#" class="d-inline mb-2">
                                                                    <input type="hidden" name="action" value="decline">
                                                                    <input type="hidden" name="id">
                                                                    <button type="submit" class="btn btn-sm btn-danger shadow-sm btn-custom-width">Từ chối</button>
                                                                </form>
                                                                <a href="#" class="btn btn-sm btn-primary shadow-sm btn-custom-width">Chi tiết</a>
                                                            </div>
                                                        </td>

                                                    </c:if>
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
