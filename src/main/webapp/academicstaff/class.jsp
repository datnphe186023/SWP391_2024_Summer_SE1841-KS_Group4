<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Danh Sách Lớp Học</title>
</head>

<body>
<jsp:include page="dashboard_staff.jsp"/>
<main class="app-content">
    <div class="container my-4">
        <div class="text-center mb-3">
            <h2>Danh Sách Lớp Học</h2>
        </div>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <div class="input-group w-50">
                <input type="text" class="form-control" placeholder="Search by keyword" aria-label="Search">
                <div class="input-group-append">
                    <span class="input-group-text"><i class="fa fa-search"></i></span>
                </div>
            </div>
        </div>
        <div class="input-group w-25">
            <select class="form-control" id="schoolYearSelect" name="schoolYearId">
                <c:forEach var="year" items="${requestScope.schoolYears}">
                    <option value="${year.id}" <c:if test="${year.id.equals(requestScope.selectedSchoolYearId)}">selected</c:if>>${year.name}</option>
                </c:forEach>
            </select>
        </div>

        <div>
        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
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
            </thead>
            <tbody>
            <c:forEach var="classes" items="${requestScope.classes}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>${classes.id}</td>
                    <td>${classes.name}</td>
                    <td>${classes.gradeId.name}</td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
                    <td><button type="button" class="btn btn-primary"><a href="#">Chỉnh Sửa</a></button></td>
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