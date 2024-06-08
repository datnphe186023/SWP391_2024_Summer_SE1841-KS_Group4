
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
    <style>
        #imagePreview img {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
        }
    </style>
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
        <div id="content-wrapper" class="d-flex flex-column" >
            <div id="content">
                <jsp:include page="../header.jsp"/>
                <div class="container-fluid">
                    <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Học Sinh</h1>
                    <div class="card shadow mb-4">
                        <div class="card-header py-3 d-flex justify-content-between align-items-center">
                            <h6 class="m-0 font-weight-bold text-primary">Danh Sách Lớp Học</h6>
                            <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target=".bd-example-modal-lg">
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

                    <!-- New School Year Modal -->
                    <div class="modal fade bd-example-modal-lg" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg">
                            <div class="modal-content">
                                <div class="row">
                                    <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5 style="margin: 14px">Thêm học sinh mới</h5>
                            </span>
                                    </div>
                                </div>
                                <form action="pupil?action=create" method="post" id="create-form">
                                <div class="row">
                                    <div class="col-md-3">
                                        <label for="imageUpload" class="form-label" style="cursor: pointer ;margin-left: 14px">Chọn hình ảnh<a style="color: red">(*)</a></label>
                                        <input type="file" class="form-control" id="imageUpload" required accept="image/*" onchange="previewImage(event)" name="avatar" value="${param.avatar}">

                                        <div id="imagePreview" class="mt-3 text-center" style="display: none;">
                                            <img id="preview" src="../images${param.avatar}" class="img-fluid rounded-circle" alt="Preview Image">
                                        </div>
                                    </div>
                                    <script>
                                        function previewImage(event) {
                                            var reader = new FileReader();
                                            reader.onload = function() {
                                                var preview = document.getElementById('preview');
                                                preview.src = reader.result;
                                                document.getElementById('imagePreview').style.display = 'block';
                                            };
                                            reader.readAsDataURL(event.target.files[0]);
                                        }
                                    </script>

                                    <div class="col-md-9">
                                        <c:set var="vietnamesePattern" value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                                        <div class="tile">
                                            <div class="tile-body">

                                                <div class="container-fluid pl-0">
                                                    <div class="row">

                                                <p style="margin-left: 11px;font-weight: bold">Ghi chú: <a style="font-weight: normal">Các thông tin có dấu</a><a style="color: red"> (*) </a><a style="font-weight: normal">là thông tin bắt buộc phải nhập</a></p>
                                                            <div class="form-group col-md-6">
                                                                <label for="id">Mã học sinh</label>
                                                                <input type="text" class="form-control" id="id" style="width: 70%"  value="${requestScope.newPupilId}" readonly>
                                                            </div>
                                                             <div class="form-group col-md-6">
                                                            <label for="address">Địa chỉ<a style="color: red">(*)</a></label>
                                                            <input class="form-control" id="address"  name="address" required value="${param.address}" pattern="^[A-Za-z1-9,${vietnamesePattern}\s]{1,100}$" title="Địa chỉ không được quá 100 kí tự">
                                                             </div>
                                                        <div class="form-group col-md-6">
                                                            <label for="lastName">Họ học sinh<a style="color: red">(*)</a></label>
                                                            <input type="text" class="form-control" id="lastName" style="width: 70%" name="lastName" required  value="${param.lastName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,20}$" title="Họ không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)">
                                                        </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="firstName">Tên học sinh<a style="color: red">(*)</a></label>
                                                                <input type="text" class="form-control" id="firstName" required name="firstName" value="${param.firstName}" pattern="^[a-zA-Z${vietnamesePattern}\s]{1,50}$" title="Tên không được chứa số hoặc kí tự đặc biệt (Tối đa 50 kí tự)">
                                                            </div>

                                                            <div class="form-group col-md-6">
                                                                <label for="fatherName">Họ tên bố <a style="color: red">(*)</a></label>
                                                                <input type="text" class="form-control" id="fatherName" name="fatherName" required value="${param.fatherName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,80}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 80 kí tự)">
                                                            </div>
                                                        <div class="form-group col-md-6">
                                                            <label for="fatherPhone">Số điện thoại bố<a style="color: red">(*)</a></label><br>
                                                            <input style="width: 50%" type="text"  class="form-control" id="fatherPhone" name="fatherPhone"  value="${param.fatherPhone}" pattern="^(0[23578]|09)\d{8}$" title="Vui lòng nhập đúng định dạng số điện thoại">
                                                        </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="motherName">Họ tên mẹ<a style="color: red">(*)</a></label>
                                                                <input type="text" class="form-control" id="motherName" name="motherName" required value="${param.motherName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,80}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 80 kí tự)">
                                                            </div>
                                                        <div class="form-group col-md-6">
                                                            <label for="motherPhone">Số điện thoại mẹ<a style="color: red">(*)</a></label><br>
                                                            <input style="width: 50%" type="text" class="form-control" id="motherPhone" name="motherPhone" value="${param.motherPhone}" pattern="^(0[23578]|09)\d{8}$" title="Vui lòng nhập đúng định dạng số điện thoại">
                                                        </div>


                                                                    <div class=" form-group col-md-6">
                                                                        <label for="birth" class="form-label">Ngày sinh<a style="color: red">(*)</a></label><br>
                                                                        <input type="date" id="birth" class="form-control" style="width: 70%" name="birth" required value="${param.birth}">
                                                                    </div>
                                                                    <div class="form-group col-md-6" >
                                                                        <label for="gender" class="form-label" >Giới tính<a style="color: red; margin-right: 60px">(*)</a></label>
                                                                        <select  name="gender" id="gender" required class="form-select" aria-label="Default select example" style="width: 50%;height: 50%;" >
                                                                            <option ${param.gender==1?"selected":""}  value="1">Nam</option>
                                                                            <option ${param.gender==0?"selected":""} value="0">Nữ</option>
                                                                        </select>
                                                                    </div>

                                                            <div class="form-group col-md-6">
                                                                <label for="email">Email<a style="color: red">(*)</a></label>
                                                                <input type="email" class="form-control" id="email" name="email" required value="${param.email}">
                                                                <div class="row" style="margin-top: 20px">
                                                                    <button style="margin:0px 10px" class="btn btn-success" type="submit">Lưu lại</button>
                                                                    <a class="btn btn-danger" data-dismiss="modal" href="#">Hủy bỏ</a>
                                                                </div>
                                                            </div>
                                                            <div class="form-group col-md-6">
                                                                <label for="parentNote">Ghi chú của phụ huynh<a style="color: red">(*)</a></label>
                                                                <textarea name="note" class="form-control" id="parentNote" rows="4" style="height: 60%" required>${param.note}</textarea>
                                                            </div>

                                                    </div>
                                                </div>


                                            </div>

                                        </div>
                                    </div>

                                </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                </div>
            <jsp:include page="../footer.jsp"/>
        </div>
        <script>
            document.addEventListener("DOMContentLoaded", function() {
                const birthInput = document.getElementById("birth");

                // Calculate the minimum date (3 years ago from today)
                const today = new Date();
                const minDate = new Date(today.setFullYear(today.getFullYear() - 3));
                const minDateString = minDate.toISOString().split('T')[0]; // Format to YYYY-MM-DD

                // Set the minimum date on the input field
                birthInput.setAttribute("max", minDateString);

                // Add event listener to validate the date
                document.getElementById("create-form").addEventListener("submit", function(event) {
                    const selectedDate = new Date(birthInput.value);
                    if (selectedDate > minDate) {
                        alert("The birth date must be at least 3 years ago.");
                        event.preventDefault(); // Prevent the form from being submitted
                    }
                });
            });
        </script>
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>

</body>
</html>


