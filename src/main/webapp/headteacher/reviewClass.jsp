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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function() {
            var toastMessage = '<%= request.getAttribute("toastMessage") %>';
            var toastType = '<%= request.getAttribute("toastType") %>';
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
    <style>
        .btn-custom-width {
            width: 120px; /* Adjust the width as needed */
        }
    </style>

    <script>
        function confirmAccept(formId) {
            if (confirm('Bạn chắc chắn muốn phê duyệt lớp này ?')) {
                document.getElementById(formId).submit();
            }
        }

        function confirmDecline(formId) {
            if (confirm('Bạn không muốn phê duyệt lớp này ?')) {
                document.getElementById(formId).submit();
            }
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
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh sách lớp đang chờ duyệt</h1>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh sách lớp đang chờ duyệt</h6>
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
                                    <th>Tạo Bởi</th>
                                    <th>Hành Động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${classes.name}</td>
                                        <td>${classes.grade.name}</td>
                                        <td>${classes.teacher.lastName} ${classes.teacher.firstName}</td>
                                        <td>${classes.createdBy.lastName} ${classes.createdBy.firstName}</td>
                                        <td>
                                            <div class="d-flex flex-column align-items-center">
                                                <form method="post" action="class" id="accept-form-${classes.id}" class="d-inline mb-2">
                                                    <input type="hidden" name="action" value="accept">
                                                    <input type="hidden" name="id" value="${classes.id}">
                                                    <button type="button" class="btn btn-sm btn-success shadow-sm btn-custom-width" onclick="confirmAccept('accept-form-${pupil.id}')">Chấp nhận</button>
                                                </form>

                                                <form method="post" action="class" id="decline-form-${classes.id}" class="d-inline mb-2">
                                                    <input type="hidden" name="action" value="decline">
                                                    <input type="hidden" name="id" value="${classes.id}">
                                                    <button type="button" class="btn btn-sm btn-danger shadow-sm btn-custom-width" onclick="confirmDecline('decline-form-${pupil.id}')">Từ chối</button>
                                                </form>
                                            </div>
                                        </td>
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
