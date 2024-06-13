<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 6/9/2024
  Time: 9:05 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Trường Mầm Non BoNo</title>
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
    <script>
        function confirmAccept() {
            if (confirm('Bạn chắc chắn muốn phê duyệt các học sinh này vào lớp chứ ?')) {
                document.getElementById('accept-form').submit();
            }
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

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh</h1>
                <div class="row align-items-center">
                    <!-- Form section with select elements -->
                    <div class="me-3 mb-4 mr-3 ml-3">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target=".addPupilToClass">
                            Thêm Học Sinh Vào Lớp
                        </button>
                    </div>
                    <div class="mb-4 mr-3">
                        <a class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Phân Công Giáo Viên</a>
                    </div>
                    <div class="mb-4 mr-3">
                        <a class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Điểm Danh</a>
                    </div>
                    <div class="mb-4 mr-3">
                        <a class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Thời khóa biểu</a>
                    </div>
                    <div class="mb-4 mr-3">
                        <a class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal">Báo Cáo Học tập</a>
                    </div>
                </div>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Giáo viên: <a style="color: red">${requestScope.teacherName eq 'null null' ?"Chưa được phân công":requestScope.teacherName}</a></h6>
                        <h6 class="m-0 font-weight-bold text-primary">Lớp : <a style="margin-right: 60px; color: red" >${requestScope.classes}</a></h6>
                        <h6 class="m-0 font-weight-bold text-primary">Khối : <a style="color: red">${requestScope.grade}</a></h6>
                    </div>
                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered"  width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Mã học sinh</th>
                                <th>Ảnh</th>
                                <th>Họ và tên</th>
                                <th>Ngày sinh</th>
                                <th>Địa chỉ</th>
                                <th>Hành động</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="pupil" items="${requestScope.listPupil}" varStatus="status">
                             <tr>
                            <th scope="row">${status.index + 1}</th>
                                 <td>${pupil.id}</td>
                                 <td style="width: 20%;"><img src="../images/${pupil.avatar}"
                                                              class="mx-auto d-block"
                                                              style="width:100px; height:100px; object-fit: cover;"></td>
                                 <td>${pupil.lastName} ${pupil.firstName}</td>
                                 <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                                 <td>${pupil.address}</td>
                                 <td class="d-flex justify-content-center align-items-center" style="height: 150px;">
                                     <a href="pupilprofile?id=${pupil.id}"
                                        class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Thông tin chi tiết</a>
                                 </td>                            </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                </div>

            <%--    Add Pupil To Class Modal            --%>
                <div class="modal fade addPupilToClass" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <div class="modal-dialog modal-lg">
                        <div class="modal-content">
                            <div class="row">
                                <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5 style="margin: 14px">Thêm học sinh mới vào lớp</h5>

                            </span>
                                    <div class="d-flex justify-content-end mt-3 mr-2">
                                        <button class="btn btn-primary" onclick="toggleCheckboxes()">Chọn / Bỏ 10 học sinh</button>
                                    </div>
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <form method="post" action="classdetail?action=addPupil" id="accept-form">
                                    <input hidden="" value="${requestScope.classId}" name="classId">
                                <div class="card-header py-3 d-flex justify-content-between align-items-center">
                                    <h6 class="m-0 font-weight-bold text-primary">Danh Sách Lớp Học</h6>
                                    <button id="add-button" type="button" class="btn btn-outline-success" onclick="confirmAccept()" >
                                        <i class="fas fa-plus"></i> Thêm học sinh
                                    </button>

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
                                                <th>Địa chỉ</th>
                                                <th>Hành động</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach var="pupil" items="${requestScope.listPupilWithoutClass}" varStatus="status">
                                                <tr>
                                                    <th scope="row">${status.index + 1}</th>
                                                    <td>${pupil.id}</td>
                                                    <td style="width: 20%;">
                                                        <img src="../images/${pupil.avatar}"
                                                             class="mx-auto d-block"
                                                             style="width:100px; height:100px; object-fit: cover;">
                                                    </td>
                                                    <td>${pupil.lastName} ${pupil.firstName}</td>
                                                    <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                                                    <td>${pupil.address}</td>
                                                    <td class="align-middle text-center">
                                                        <div class="form-check custom-checkbox d-flex justify-content-center align-items-center">
                                                            <input style="cursor: pointer;" class="form-check-input" type="checkbox" value="${pupil.id}" id="myCheckbox" name="pupilSelected">
                                                        </div>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>

                <%-- End modal Add pupil to class --%>
                </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<script>
    // Variable to track the current state (true: checked, false: unchecked)
    var areChecked = false;

    // Function to toggle the first 30 checkboxes
    function toggleCheckboxes() {
        // Get all checkboxes with the class 'myCheckbox'
        var checkboxes = document.querySelectorAll('#myCheckbox');

        // Loop through the first 30 checkboxes and toggle their checked state
        for (var i = 0; i < 10 && i < checkboxes.length; i++) {
            checkboxes[i].checked = !areChecked;
        }

        // Toggle the state variable
        areChecked = !areChecked;
    }
</script>
<!-- Page level plugins -->
    <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
    <script src="../js/demo/datatables-demo.js"></script>
</body>

</html>
