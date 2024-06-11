<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Tạo tài khoản</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
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
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <form id="createAccountForm" action="registeraccount" method="POST">
                        <div class="container-fluid">
                            <h1 class="h3 mb-4 text-gray-800 text-center">TẠO TÀI KHOẢN</h1>
                            <div class="row">
                                <div class="col-lg-6 mb-4">                            
                                    <div>
                                        <label >VAI TRÒ</label>
                                        <select class="form-control-sm" name="role" id="roleSelect" onchange="redirectToServlet()">
                                            <option value="6">ALL (ROLE)</option>
                                            <option value="0">ADMIN</option>
                                            <option value="1">HEADTEACHER</option>
                                            <option value="2">ACADEMIC STAFF</option>
                                            <option value="3">ACCOUNTANT</option>
                                            <option value="4">TEACHER</option>
                                            <option value="5">PARENT</option>
                                        </select>  
                                    </div>
                                    <div class="d-flex justify-content-lg-between">
                                        <button type="button" onclick="selectAll()" class="btn btn-success btn-sm mb-1">CHỌN TẤT CẢ</button>
                                        <button type="button" onclick="deselectAll()" class="btn btn-danger btn-sm">BỎ CHỌN TẤT CẢ</button>
                                        <button type="submit" class="btn btn-primary btn-sm mb-1">TẠO TÀI KHOẢN</button>
                                    </div>

                                </div>

                            </div>
                        </div>

                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">DANH SÁCH NGƯỜI DÙNG CHƯA CÓ TÀI KHOẢN</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">                              
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                        <th>STT</th>
                                        <th>HỌ VÀ TÊN</th>
                                        <th>ID</th>
                                        <th>EMAIL</th>
                                        <th>VAI TRÒ</th>
                                        <th>TRẠNG THÁI</th>
                                        <th>HÀNH ĐỘNG</th>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:set var="count" value="1" />
                                        <c:forEach var="personnel" items="${requestScope.listPersonnel}" varStatus="status">
                                            <tr>
                                                <th scope="row">${count}</th>
                                                <td>${personnel.getLastName()} ${personnel.getFirstName()}</td>
                                                <td>${personnel.getId()}</td>
                                                <td>${personnel.getEmail()}</td>
                                                <td>${roleMap[personnel.getRoleId()]}</td>
                                                <td>${personnel.getStatus()}</td>
                                                <td>
                                                    <input type="checkbox" name="user_checkbox" value="${personnel.getId()}">
                                                </td>
                                            </tr>
                                            <c:set var="count" value="${count + 1}" />
                                        </c:forEach>
                                        <c:forEach var="pupil" items="${requestScope.listPupils}" varStatus="status">
                                            <tr>
                                                <th scope="row">${count}</th>
                                                <td>${pupil.getLastName()} ${pupil.getFirstName()}</td>
                                                <td>${pupil.getId()}</td>
                                                <td>${pupil.getEmail()}</td>
                                                <td>Parent</td>
                                                <td>${pupil.getStatus()}</td>
                                                <td>
                                                    <input type="checkbox" name="user_checkbox" value="${pupil.getId()}">
                                                </td>
                                            </tr>
                                            <c:set var="count" value="${count + 1}" />
                                        </c:forEach>
                                        </tbody>
                                    </table>

                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-around">
                            <div>
                                <button type="button" onclick="Back()" class="btn btn-danger btn-sm">QUAY LẠI</button>
                            </div>
                            <div>
                                <button type="submit" class="btn btn-primary btn-sm mb-1">TẠO TÀI KHOẢN</button>
                            </div>
                        </div>
                    </form>  
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
    <!-- Page level plugins -->
    <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>
    <script>
                                    function Back() {
                                        window.location.href = '${pageContext.request.contextPath}/admin/manageruser';
                                    }
                                    function redirectToServlet() {
                                        var selectedRole = document.getElementById("roleSelect").value;
                                        if (selectedRole !== "") {
                                            window.location.href = "categoryRole?role=" + selectedRole;
                                        }
                                    }
                                    function selectAll() {
                                        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
                                        checkboxes.forEach(function (checkbox) {
                                            checkbox.checked = true;
                                        });
                                    }

                                    function deselectAll() {
                                        var checkboxes = document.querySelectorAll('input[type="checkbox"]');
                                        checkboxes.forEach(function (checkbox) {
                                            checkbox.checked = false;
                                        });
                                    }
                                    // Function to get query parameter value
                                    function getQueryParam(param) {
                                        var urlParams = new URLSearchParams(window.location.search);
                                        return urlParams.get(param);
                                    }

                                    // Set the selected value on page load
                                    document.addEventListener('DOMContentLoaded', (event) => {
                                        var selectedRole = getQueryParam('role');
                                        if (selectedRole) {
                                            document.getElementById('roleSelect').value = selectedRole;
                                        }
                                    });
    </script>
    <!-- Page level custom scripts -->
    <script src="../js/demo/datatables-demo.js"></script>
</body>
</html>