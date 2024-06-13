<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>TẠO THÔNG BÁO</title>
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
            </style>
        </head>

        <body id="page-top">
            <div id="wrapper">
                <jsp:include page="navbar.jsp"/>
                <div id="content-wrapper" class="d-flex flex-column">
                    <div id="content">
                        <jsp:include page="../header.jsp"/>
                        <div class="container-fluid">

                            <h1 class="h3 mb-4 text-gray-800 text-center">TẠO THÔNG BÁO</h1>
                            <div class="card shadow mb-4">
                                <form>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <h5>TIÊU ĐỀ : </h5>
                                            <input style="width: 100%"></input>
                                                <h5 style="margin-top: 10px">NỘI DUNG : </h5>
                                                <div>
                                                    <textarea id="id" name="content" rows="10" cols="131"></textarea>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="d-flex justify-content-around">
                                            <button class="btn btn-danger">QUAY LẠI</button>
                                            <button type="submit" class="btn btn-success">XÁC NHẬN</button>
                                        </div>
                                    </form>
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