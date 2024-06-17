<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 6/10/2024
  Time: 10:00 AM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Danh sách môn học</title>

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
                    $('.create-subject').modal('show'); // Show the modal if the toast type is fail
                }
            }
        });
    </script>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

    <!-- Custom fonts for this template-->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">

</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <di id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Môn Học</h1>
                <div class="card shadow mb-4">
                    <div class="card-header py-3 d-flex justify-content-between align-items-center">
                        <h6 class="m-0 font-weight-bold text-primary">Danh Sách Môn Học</h6>
                        <button type="button" class="btn btn-outline-primary" data-toggle="modal" data-target=".create-subject">
                            <i class="fas fa-upload"></i> Thêm Môn Học
                        </button>

                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Tên môn học</th>
                                    <th>Khối</th>
                                    <th>Trạng thái</th>
                                    <th>Mô tả</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="subject" items="${requestScope.listAllSubject}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${subject.name}</td>
                                        <td>${subject.grade.name}</td>
                                        <c:set value="${subject.status}" var="status"/>
                                        <c:if test="${status eq 'đã được phê duyệt'}">
                                            <td><span class="badge badge-success">${status}</span></td>
                                        </c:if>
                                        <c:if test="${status eq 'đang chờ phê duyệt'}">
                                            <td><span class="badge badge-warning">${status}</span>  </td>
                                        </c:if>
                                        <c:if test="${status eq 'không được duyệt'}">
                                            <td><span class="badge badge-danger">${status}</span>  </td>
                                        </c:if>
                                        <td>${subject.description}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>

                <%--     Create Subject Modal           --%>
                <div class="modal fade create-subject" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel" aria-hidden="true">
                    <form action="subject?action=create" method="POST">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5>Tạo Môn Học Mới</h5>
                            </span>
                                        </div>
                                    </div>
                                    <c:set var="vietnamesePattern"
                                           value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                                    <div class="row">
                                        <p style="margin-left: 11px;font-weight: bold">Ghi chú: <a style="font-weight: normal">Các thông tin có dấu</a><a style="color: red"> (*) </a><a style="font-weight: normal">là thông tin bắt buộc phải nhập</a></p>
                                        <div class="col-md-6">
                                        <div class="form-group">
                                            <label class="control-label">Tên Môn Học<a style="color: red">(*)</a></label>
                                            <input id="name" class="form-control" type="text" name="name" pattern="^[a-zA-Z0-9${vietnamesePattern}\s]{1,50}$"
                                                   title="Tên không được chứa kí tự đặc biệt (Tối đa 50 kí tự)" required value="${param.name}">
                                        </div>
                                    </div>
                                    <div class="col-md-6">
                                        <div class="form-group">
                                            <label for="grade">Khối<a style="color: red">(*)</a></label>
                                            <select class="form-control" id="grade" name="grade" required>
                                                <option value="">-- Chọn Khối --</option>
                                                <c:forEach var="grade" items="${requestScope.listGrade}">
                                                    <option value="${grade.id}" ${param.grade eq grade.id ? "selected":""}>${grade.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                                    <div class="row">
                                        <div class="col-md-12">
                                            <div class="form-group">
                                                <label for="description">Mô tả<a style="color: red">(*)</a></label>
                                                <textarea class="form-control" name="description" id="description" cols="30" rows="5" required>${param.description}</textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <button class="btn btn-success" type="submit">Lưu lại</button>
                                    <a class="btn btn-danger" data-dismiss="modal" id="cancel-button">Hủy bỏ</a>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <%--     End Modal Create Subject           --%>
            </div>
        </di>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<script>
    document.getElementById('cancel-button').addEventListener('click', function() {
        document.getElementById('name').value = '';
        document.getElementById('grade').value = '';
        document.getElementById('description').value = '';

    });
</script>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>

</html>
