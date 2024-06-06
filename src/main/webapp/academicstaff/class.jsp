<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Danh Sách Lớp Học</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
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
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <div class="container my-4">
                    <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Lớp Học</h1>
                    <div class="row">
                        <div class="col-lg-6 mb-4">
                            <form action="class" method="post"
                                    class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100 navbar-search">
                                <div class="input-group">
                                    <input type="text" class="form-control bg-white border-0 small" placeholder="Tìm kiếm theo tên" name="name"
                                           aria-label="Search" aria-describedby="basic-addon2">
                                    <input name="schoolYearId" value="${requestScope.selectedSchoolYear.id}" hidden>
                                    <input name="action" value="search" hidden>
                                    <div class="input-group-append">
                                        <button class="btn btn-primary" type="submit">
                                            <i class="fas fa-search fa-sm"></i>
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="col-lg-6 mb-4">
                            <form action="class"  id="myForm">
                                <div>
                                    <label >Chọn năm học</label>
                                    <select class="form-select dropdown"  aria-label="Default select example" onchange="submitForm()" name="schoolYearId">
                                        <c:forEach items="${requestScope.schoolYears}" var="year">
                                            <option ${requestScope.selectedSchoolYear.id eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </form>
                        </div>
                    </div>

                    <div class="row">
                        <table class="table table-bordered">
                            <tr class="table">
                                <th scope="col">STT</th>
                                <th scope="col">ID</th>
                                <th scope="col">Tên Lớp</th>
                                <th scope="col">Khối</th>
                                <th scope="col">Trạng thái</th>
                                <th scope="col">Thông Tin Lớp</th>
                            </tr>
                            <tbody>
                            <div style="color: red">${requestScope.error}</div>
                            <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                                <tr>
                                    <th scope="row">${status.index + 1}</th>
                                    <td>${classes.id}</td>
                                    <td>${classes.name}</td>
                                    <td>${classes.grade.name}</td>
                                    <td>${classes.status}</td>
                                    <td><a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Thông tin chi tiết</a></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>

                    <div class="d-flex justify-content-end">
                        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newClassModal">
                            TẠO LỚP MỚI
                        </button>
                    </div>
                </div>

                <!-- New School Year Modal -->
                <div class="modal fade" id="newClassModal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <form action="class?action=create" method="POST">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5>Tạo Lớp Mới</h5>
                            </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Tên Lớp</label>
                                            <input class="form-control" type="text" name="name" required>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="teacherSelect">Giáo viên</label>
                                            <select class="form-control" id="teacherSelect" name="teacher" required>
                                                <option value="">-- Chọn Giáo Viên --</option>
                                                <c:forEach var="teacher" items="${requestScope.teachers}">
                                                    <option value="${teacher.id}">${teacher.lastName} ${teacher.firstName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label for="gradeSelect">Khối</label>
                                            <select class="form-control" id="gradeSelect" name="grade" required>
                                                <option value="">-- Chọn Khối --</option>
                                                <c:forEach var="grade" items="${requestScope.grades}">
                                                    <option value="${grade.id}">${grade.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Năm học</label>
                                            <input class="form-control" type="text" value="${requestScope.selectedSchoolYear.name}" readonly>
                                            <input name="schoolYear" value="${requestScope.selectedSchoolYear.id}" hidden>
                                        </div>
                                    </div>
                                    <br>
                                    <button class="btn btn-success" type="submit">Lưu lại</button>
                                    <a class="btn btn-danger" data-dismiss="modal" href="#">Hủy bỏ</a>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>
</html>