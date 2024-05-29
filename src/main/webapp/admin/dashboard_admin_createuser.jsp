<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Title</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
        <style>
            .search-container {
                width: 70%;
                margin: 0 auto 20px;
                display: flex;
                justify-content: space-evenly;
                align-items: center; /* Thêm dòng này để căn chỉnh nút theo chiều dọc */
            }
            .search-container select,
            .search-container input[type="text"],
            .search-container button {
                margin-right: 10px;
                height: 40px; /* Đặt chiều cao cho các phần tử là 40px */
                box-sizing: border-box; /* Đảm bảo kích thước bao gồm cả padding và border */
            }
            .search-icon-btn {
                height: 100%; /* Đặt chiều cao của nút là 100% của phần tử cha */
                padding: 0 15px; /* Khoảng cách nút với biên là 0 15px */
                background-color: #007bff; /* Màu nền cho nút */
                color: #fff; /* Màu chữ cho nút */
                border: none; /* Loại bỏ đường viền của nút */
                border-radius: 4px; /* Định hình góc của nút */
                cursor: pointer; /* Biểu tượng con trỏ khi di chuột vào nút */
            }
            .search-icon-btn i {
                font-size: 20px; /* Kích thước của biểu tượng tìm kiếm */
            }
            table {
                width: 70%;
                margin: 50px auto;
                border-collapse: collapse;
            }
            th, td {
                border: 1px solid black;
                padding: 10px;
                text-align: left;
            }
            th {
                background-color: #f2f2f2;
            }
            h1 {
                text-align: center;
                margin-top: 50px;
            }
            .app-sidebar__user-avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                cursor: pointer;
                object-fit: cover;
            }
            .avatar-input {
                display: none;
            }
        </style>
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

    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">
                <!-- User Menu-->
                <li><a class="app-nav__item" href="../logout"><i class='bx bx-log-out bx-rotate-180'></i> Logout </a>
                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user">
                <img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${sessionScope.personnel.avatar}" alt="User Image" onclick="redirectToInfoPage()">
                <input class="avatar-input" id="avatarInput" type="file" name="avatar" accept="image/*" onchange="previewAvatar(event)">
                <div>
                    <p class="app-sidebar__user-name"><b>${personnel.lastName} ${personnel.firstName}</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>

            <!-- Admin homepage start-->
            <ul class="app-menu">
                <li><a class="app-menu__item" href="createUser"><i class='app-menu__icon bx bx-user-plus'></i><span
                            class="app-menu__label">Tạo tài khoản</span></a></li>
                <li><a class="app-menu__item" href="managerUser"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý tài khoản</span></a></li>

            </ul>
            <!-- Admin homepage end-->


            <!--  teacher dashboard end-->




        </aside>


        <div>
            <h1 style="text-align: center; margin-top:  50px">Danh sách người dùng chưa có tài khoản</h1>
            <div class="search-container">
                <select name="role" id="roleSelect" onchange="redirectToServlet()">
                    <option value="">All (Role)</option>
                    <option value="0">Admin</option>
                    <option value="1">Headteacher</option>
                    <option value="2">Academic Staff</option>
                    <option value="3">Accountant</option>
                    <option value="4">Teacher</option>
                    <option value="5">Parent</option>
                </select>
                <form action="searchPersonnel" method="Post">
                    <input type="text" name="search" placeholder="Search By ID">
                    <button type="submit" class="search-icon-btn"><i class="material-icons">search</i></button>
                </form>
                <div>
                    <button onclick="selectAll()" class="btn-add">Chọn tất cả</button>
                    <button onclick="deselectAll()" class="btn-danger">Bỏ chọn tất cả</button>
                </div>
            </div>
            <form id="createAccountForm" action="registeraccount" method="POST">
                <table style="width: 70%; margin-left: 300px">
                    <thead>
                    <th>STT</th>
                    <th>Họ Và Tên</th>
                    <th>ID</th>
                    <th>Email</th>
                    <th>Vai Trò</th>
                    <th>Trạng Thái</th>
                    <th>Hành Động</th>
                    </thead>
                    <tbody>
                        <c:forEach items="${requestScope.list}" var="p" varStatus="status">
                            <tr>
                                <td>${status.index + 1}</td>
                                <td>${p.getLastName()} ${p.getFirstName()}</td>
                                <td>${p.getId()}</td>
                                <td>${p.getEmail()}</td>
                                <td>${roleMap[p.getRoleId()]}</td>
                                <td>${p.getStatus()}</td>
                                <td>
                                    <input type="checkbox" name="user_checkbox" value="${p.getId()}">
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
        </div>
        <div style="text-align: right; margin-right: 150px; margin-bottom: 20px;">
            <button type="submit" class="btn-add">Tạo tài khoản</button>
        </div>
    </form>

    <script>

        function redirectToServlet() {
            var selectedRole = document.getElementById("roleSelect").value;
            if (selectedRole !== "") {
                window.location.href = "categoryRole?role=" + selectedRole;
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
        
    </script>

    <script src="js/jquery-3.2.1.min.js"></script>
    <!--===============================================================================================-->
    <script src="js/popper.min.js"></script>
    <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
    <!--===============================================================================================-->
    <script src="js/bootstrap.min.js"></script>
    <!--===============================================================================================-->
    <script src="js/main.js"></script>
    <!--===============================================================================================-->
    <script src="js/plugins/pace.min.js"></script>
    <!--===============================================================================================-->
    <!--===============================================================================================-->
</body>

</html>