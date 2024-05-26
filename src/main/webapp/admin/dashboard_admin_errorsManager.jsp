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
        </style>
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">


                <!-- User Menu-->
                <li><a class="app-nav__item" href="../login"><i class='bx bx-log-out bx-rotate-180'></i> Logout </a>

                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="images/${account.image}" width="50px"
                                                alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b>${sessionScope.user.username}</b></p>
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



        </aside>


        <div>
            <h1 style="text-align: center; margin-top:  50px">Danh sách tài khoản</h1>
            <div class="search-container">
                <select name="role">
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
            </div>

           <h4 style="color: red;text-align: center">${mess}</h4>
            <form action="managerUser">
                <button style="display: block; margin: 0 auto;">Xem Lại Danh Sách</button>
            </form>
        </div>



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