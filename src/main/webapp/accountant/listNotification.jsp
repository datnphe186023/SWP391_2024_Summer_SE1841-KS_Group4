<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>QUẢN LÝ THÔNG BÁO</title>
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
        <style>
            .notification {
                margin-top: 10px;
                background-color: wheat;
                margin: 10px 0;
                padding: 10px;
                display: flex;
                justify-content: space-between;
                align-items: center;
                border-radius: 8px;
                .btn-detail {
                    background-color: #555555;
                    color: white;
                    border: none;
                    padding: 10px;
                    border-radius: 8px;
                }
                .notification input {
                    background-color: transparent;
                    border: none;
                    font-size: inherit;
                    font-weight: bold;
                    color: inherit;
                    width: auto;
                    outline: none;
                }
            </style>
        </head>

        <body id="page-top">
            <div id="wrapper">
                <jsp:include page="navbar.jsp"/>
                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">
                        <jsp:include page="../header.jsp"/>
                        <div class="container-fluid">

                            <h1 class="h3 mb-4 text-gray-800 text-center">QUẢN LÝ THÔNG BÁO</h1>
                            <div class="row">
                                <div class="col-lg-6 mb-4">
                                </div>
                            </div>
                            <div class="card shadow mb-4">
                                <div class="d-flex justify-content-between card-header py-3">
                                    <h6 class="m-0 font-weight-bold text-primary">DANH SÁCH THÔNG BÁO</h6>
                                    <a href="createnotifi"><button class="btn btn-success">TẠO THÔNG BÁO</button></a>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">

                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <c:forEach items="${notifi}" var="notifi"><div class="notification">

                                                    <a href="notificationdetails?id=${notifi.getId()}"><button class="btn-detail">XEM CHI TIẾT</button></a>
                                                    <div>
                                                        THÔNG BÁO: ${notifi.getHeading()}
                                                    </div>
                                                    <div>
                                                        <div>NGÀY TẠO:  ${notifi.getCreatedAt()}</div>
                                                    </div>

                                                </div></c:forEach>
                                            </table>
                                        </div>
                                    </div>
                                </div>        
                            </div>

                        </div>
                    <jsp:include page="../footer.jsp"/>
                </div>
            </div>
            <!-- Page level plugins -->
            <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
            <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

            <!-- Page level custom scripts -->
            <script src="../js/demo/datatables-demo.js"></script>
            <script>
            function redirectToServlet() {
                var selectedRole = document.getElementById("roleSelect").value;
                if (selectedRole !== "") {
                    window.location.href = "categoryRoleManager?role=" + selectedRole;
                }
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
        </body>
    </html>