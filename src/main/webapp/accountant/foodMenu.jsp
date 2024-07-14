<%@ page import="models.foodmenu.FoodMenu" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 19-Jun-24
  Time: 8:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <%
        FoodMenu foodMenu = (FoodMenu) session.getAttribute("foodMenu");
        session.removeAttribute("foodMenu");
        String foodMenuId = null, details = null;
        if (foodMenu != null) {
            foodMenuId = foodMenu.getId();
            details = foodMenu.getFoodDetails();
        }
    %>
    <script>
        $(document).ready(function () {
            var toastMessage = '<%= request.getAttribute("toastMessage") %>';
            var toastType = '<%= request.getAttribute("toastType") %>';
            var foodMenuId = '<%=foodMenuId%>';
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                    $('#editFoodMenu' + foodMenuId).modal('show');
                    document.getElementById(foodMenuId + 'details').value = '<%=details%>';
                }
            }
        });

        function confirmEdit(formId, msg) {
            formIdToSubmit = formId;

            document.getElementById('confirmationMessage').innerText = msg;
            $('#confirmationModal').modal('show');
        }

        $(document).ready(function () {
            $('#confirmButton').click(function () {
                document.getElementById(formIdToSubmit).submit();

            });
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
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Các Thực Đơn</h1>
                <form action="foodmenus" id="myForm">
                    <div class="row">
                        <div class="col-lg-2 mb-4">
                            <label for="selectStatus">Chọn trạng thái</label>
                            <select class="custom-select" id="selectStatus" aria-label="Default select example" onchange="submitForm()" name="status">
                                <option ${param.status eq 'all'? "selected" :""} value="all">Tất cả</option>
                                <option  ${param.status eq 'đang chờ xử lý'? "selected" :""} value="đang chờ xử lý">Đang chờ xử lý</option>
                                <option ${param.status eq 'đã được duyệt'? "selected" :""}  value="đã được duyệt">Đã được duyệt</option>
                                <option  ${param.status eq 'không được duyệt'? "selected" :""} value="không được duyệt">Không được duyệt</option>
                            </select>
                        </div>
                    </div>
                </form>
                <div class="card shadow mb-4">
                    <div class="card-header py-3 d-flex justify-content-between align-items-center">
                        <h6 class="m-0 font-weight-bold text-primary">Danh Sách Các Thực Đơn</h6>
                        <button type="button" class="btn btn-outline-primary" data-toggle="modal"
                                data-target=".create-food-menu">
                            <i class="fas fa-upload"></i> Thêm Thực Đơn
                        </button>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Chi tiết thực đơn</th>
                                    <th>Trạng thái</th>
                                    <th>Hành động</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="foodMenu" items="${requestScope.foodMenus}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${foodMenu.foodDetails}</td>
                                        <c:set value="${foodMenu.status}" var="s"/>
                                        <c:if test="${s eq 'đã được duyệt'}">
                                            <td><span class="badge badge-success">${s}</span></td>
                                        </c:if>
                                        <c:if test="${s eq 'đang chờ xử lý'}">
                                            <td><span class="badge badge-warning">${s}</span></td>
                                        </c:if>
                                        <c:if test="${s eq 'không được duyệt'}">
                                            <td><span class="badge badge-danger">${s}</span></td>
                                        </c:if>
                                        <td class="text-center">
                                            <button type="button" class="btn btn-primary" data-toggle="modal"
                                                    data-target="#editFoodMenu${foodMenu.id}">
                                                Chỉnh Sửa
                                            </button>
                                        </td>
                                    </tr>

                                    <%-- Begin modal for edit food menu --%>
                                    <div class="modal fade" id="editFoodMenu${foodMenu.id}" tabindex="-1" role="dialog"
                                         aria-labelledby="myLargeModalLabel" aria-hidden="true">
                                        <form action="foodmenus?action=edit" method="POST" id="editFoodMenuForm${foodMenu.id}">
                                            <input hidden="hidden" type="text" readonly
                                                   name="foodMenuId" value="${foodMenu.id}">
                                            <div class="modal-dialog modal-dialog-centered" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-body">
                                                        <div class="row">
                                                            <div class="form-group col-md-12">
                                                                <span class="thong-tin-thanh-toan">
                                                                    <h5>Chỉnh sửa thực đơn</h5>
                                                                </span>
                                                            </div>
                                                        </div>
                                                        <div class="row">
                                                            <div class="col-md-12">
                                                                <div class="form-group">
                                                                    <label for="${foodMenu.id}details">Chi tiết thực đơn<a
                                                                            style="color: red">(*)</a></label>
                                                                    <textarea class="form-control" type="text"
                                                                              name="details" id="${foodMenu.id}details" rows="5"
                                                                              required maxlength="255">${foodMenu.foodDetails}</textarea>
                                                                    <p style="display: none" id="charCount">255
                                                                        characters remaining</p>
                                                                    <p style="display: none" class="alert-warning"
                                                                       id="warning">Không được vượt quá 255 kí tự.</p>
                                                                </div>
                                                                <script>
                                                                    const textarea = document.querySelector('textarea[name="details"]');
                                                                    const charCount = document.getElementById('charCount');
                                                                    const warning = document.getElementById('warning');

                                                                    textarea.addEventListener('input', () => {
                                                                        const remaining = 255 - textarea.value.length;
                                                                        charCount.textContent = `${remaining} characters remaining`;

                                                                        if (remaining <= 0) {
                                                                            warning.style.display = 'block';
                                                                        } else {
                                                                            warning.style.display = 'none';
                                                                        }
                                                                    });
                                                                </script>
                                                            </div>
                                                        </div>
                                                        <br>
                                                        <button class="btn btn-success" type="button"
                                                                onclick="confirmEdit('editFoodMenuForm${foodMenu.id}', 'Bạn có chắc chắn chỉnh sửa thực đơn này?')">
                                                            Lưu lại
                                                        </button>
                                                        <a class="btn btn-danger" data-dismiss="modal"
                                                           id="cancel-button">Hủy bỏ</a>
                                                        <br>
                                                    </div>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <%-- End modal for edit food menu --%>
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

    <%--     Create Food Menu Modal           --%>
    <div class="modal fade create-food-menu" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel"
         aria-hidden="true">
        <form action="foodmenus?action=create" method="POST">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class="form-group col-md-12">
                            <span>
                                <h5>Tạo Thực Đơn Mới</h5>
                            </span>
                            </div>
                        </div>
                        <div class="row">
                            <p style="margin-left: 11px;font-weight: bold">Ghi chú: <a style="font-weight: normal">Các
                                thông tin có dấu</a><a style="color: red"> (*) </a><a style="font-weight: normal">
                                là thông tin bắt buộc phải nhập</a></p>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="details">Thực Đơn Mới<a style="color: red">(*)</a></label>
                                    <textarea class="form-control" type="text"
                                              placeholder="Không được vượt quá 255 kí tự"
                                              name="details" id="details" rows="5" required maxlength="255"></textarea>
                                    <p style="display: none" id="charCount">255 characters remaining</p>
                                    <p style="display: none" class="alert-warning" id="warning">Không được vượt quá 255
                                        kí tự.</p>
                                </div>
                                <script>
                                    const textarea = document.querySelector('textarea[name="details"]');
                                    const charCount = document.getElementById('charCount');
                                    const warning = document.getElementById('warning');

                                    textarea.addEventListener('input', () => {
                                        const remaining = 255 - textarea.value.length;
                                        charCount.textContent = `${remaining} characters remaining`;

                                        if (remaining <= 0) {
                                            warning.style.display = 'block';
                                        } else {
                                            warning.style.display = 'none';
                                        }
                                    });
                                </script>
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
    <%--     End Modal Create Food Menu           --%>


    <%-- Begin confirmation modal--%>
    <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel"
         aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="confirmationModalLabel">Xác nhận thao tác</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body" id="confirmationMessage">
                    <!-- Dynamic message will be inserted here -->
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                    <button type="button" class="btn btn-primary" id="confirmButton">Xác Nhận</button>
                </div>
            </div>
        </div>
    </div>
    <%-- End confirmation modal--%>
</div>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>
</html>
