<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 6/16/2024
  Time: 2:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Tạo sự kiện</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>

    <script>
        $(document).ready(function () {
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
        function confirmAccept(formId, msg) {
            formIdToSubmit = formId;
            document.getElementById('confirmationMessage').innerText = msg;
            $('#confirmationModal').modal('show');
        }
        $(document).ready(function() {
            $('#confirmButton').click(function() {
                document.getElementById(formIdToSubmit).submit();

            });
        });
    </script>
</head>
<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h4 class="h3 mb-4 text-gray-800 text-center">Tạo Sự Kiện</h4>'
                <form action="sendevent" method="post" id="sendEvent">
                    <div class="form-group">
                        <h4 class="h4 mb-2 text-gray-800">Đối tượng gửi<a style="color: red">(*)</a></h4>
                        <input placeholder="Tên sự kiện ( Tối đa 100 kí tự)" id="heading" class="form-control" type="text" name="heading"  required value="${param.heading}">
                    </div>
                    <h4 class="h4 mb-2 text-gray-800">Đối tượng gửi<a style="color: red">(*)</a></h4>
                    <select class="form-select dropdown mb-5" aria-label="Default select example" name="receiver">
                        <option ${param.receiver eq 'all' ? "selected" :""} value="all">Tất cả</option>
                        <option ${param.receiver eq 'staff' ? "selected" :""} value="staff">Giáo vụ</option>
                        <option ${param.receiver eq 'teacher' ? "selected" :""} value="teacher">Giáo viên</option>
                        <option ${param.receiver eq 'accountant' ? "selected" :""}value="accountant">Kế toán</option>
                        <option ${param.receiver eq 'parent' ? "selected" :""} value="parent">Phụ huynh</option>
                        <option ${param.receiver eq 'school' ? "selected" :""} value="school">Giáo vụ, Giáo viên, Kế toán</option>
                    </select>
                    <h4 class="h4 mb-2 text-gray-800">Chi tiết<a style="color: red">(*)</a></h4>
                    <textarea class="form-control mb-5" type="text" placeholder="Chi tiết sự kiện"
                              name="details" rows="5" required>${param.details}</textarea>
                    <button type="button" class="btn btn-primary float-right" onclick="confirmAccept('sendEvent','Bạn có chắc chắn muốn gửi sự kiện này ?')">Gửi</button>
                </form>
            </div>
            <%-- Begin confirmation modal--%>
            <div class="modal fade" id="confirmationModal" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
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
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>
</html>
