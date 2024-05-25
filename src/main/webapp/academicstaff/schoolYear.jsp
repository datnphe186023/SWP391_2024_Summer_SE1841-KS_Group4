<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Danh Sách Năm Học</title>
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
</head>
<body>
<jsp:include page="dashboard_staff.jsp"/>
<main class="app-content">
    <div class="container my-4">
        <div class="row justify-content-center">
            <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách năm học</span>
        </div>

        <div class="col-lg-6">
            <form action="#">
                <div class="search-field">
                    <div class="form-group has-search">
                        <span style="margin-top: 5px" class="fa fa-search form-control-feedback"></span>
                        <input style="border-radius: 30px" type="text" class="form-control" placeholder="Tìm kiếm theo năm học" name="information">
                    </div>
                </div>
            </form>
        </div>

        <table class="table table-bordered">
            <tr>
                <th scope="col">STT</th>
                <th scope="col">Năm học</th>
                <th scope="col">ID</th>
                <th scope="col">Ngày bắt đầu</th>
                <th scope="col">Ngày kết thúc</th>
                <th scope="col">Người tạo</th>
            </tr>
            <tbody>
            <c:forEach var="schoolYear" items="${requestScope.schoolYears}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>${schoolYear.name}</td>
                    <td>${schoolYear.id}</td>
                    <td>${schoolYear.startDate}</td>
                    <td>${schoolYear.endDate}</td>
                    <td>${schoolYear.createdBy.lastName} ${schoolYear.createdBy.firstName}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newSchoolYearModal">
                TẠO NĂM HỌC MỚI
            </button>
        </div>

    </div>

    <!-- New School Year Modal -->
    <div class="modal fade" id="newSchoolYearModal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <form action="schoolyear?action=create" method="POST">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5>Tạo Năm Học Mới</h5>
                            </span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label class="control-label">Năm học</label>
                                <input class="form-control" type="text" name="name" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Ngày bắt đầu</label>
                                <input class="form-control" type="date" name="startDate" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Ngày kết thúc</label>
                                <input class="form-control" type="date" name="endDate" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Mô Tả</label>
                                <input class="form-control" type="text" name="description" required>
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