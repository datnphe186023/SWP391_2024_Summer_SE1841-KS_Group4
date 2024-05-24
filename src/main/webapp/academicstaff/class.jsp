<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Danh Sách Lớp Học</title>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
</head>

<body>
<jsp:include page="dashboard_staff.jsp"/>
<main class="app-content">
    <div class="container my-4">
        <div class="row justify-content-center">
            <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách lớp học</span>
        </div>
        <div class="row">
            <div class="col-lg-6">
                <form action="#">
                    <div class="search-field">
                        <div class="form-group has-search">
                            <span style="margin-top: 5px" class="fa fa-search form-control-feedback"></span>
                            <input style="border-radius: 30px" type="text" class="form-control" placeholder="Tìm kiếm theo tên" name="information">
                        </div>
                    </div>
                </form>
            </div>
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
        </div>
        <div>
        <table class="table table-bordered">
            <tr class="table">
                <th scope="col">STT</th>
                <th scope="col">ID</th>
                <th scope="col">Tên Lớp</th>
                <th scope="col">Khối</th>
                <th scope="col">Giáo viên</th>
                <th scope="col">Điểm Danh</th>
                <th scope="col">Báo Cáo Học Tập</th>
                <th scope="col">Thời Khoá Biểu</th>
                <th scope="col">Danh Sách Học Sinh</th>
                <th scope="col">Thông Tin Lớp</th>
            </tr>
            <tbody>
            <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>${classes.id}</td>
                    <td>${classes.name}</td>
                    <td>${classes.grade.name}</td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="pupilclass?classId=${classes.id}">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chi Tiết</a></button></td>
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
                                <label for="schoolYear">Khối</label>
                                <select class="form-control" id="schoolYear" name="schoolYear" required>
                                    <option value="">-- Chọn Năm Học --</option>
                                    <c:forEach var="schoolYear" items="${requestScope.schoolYears}">
                                        <option value="${schoolYear.id}">${schoolYear.name}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <br>
                        <button class="btn btn-save" type="submit">Lưu lại</button>
                        <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                        <br>
                    </div>
                </div>
            </div>
        </form>
    </div>
</main>
</body>

</html>