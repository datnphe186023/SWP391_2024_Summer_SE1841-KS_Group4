<%@page contentType="text/html" pageEncoding="UTF-8" %>
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
</head>

<body onload="time()" class="app sidebar-mini rtl">
<!-- Navbar-->
<header class="app-header">
    <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                    aria-label="Hide Sidebar"></a>
    <!-- Navbar Right Menu-->
    <ul class="app-nav">


        <!-- User Menu-->
        <li><a class="app-nav__item" href="login"><i class='bx bx-log-out bx-rotate-180'></i> Logout </a>

        </li>
    </ul>
</header>
<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
    <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="images/${account.image}" width="50px"
                                        alt="User Image">
        <div>
            <p class="app-sidebar__user-name"><b>${sessionScope.account.fullName}</b></p>
            <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
        </div>
    </div>
    <hr>
    <ul class="app-menu">
        <li><a class="app-menu__item" href="admin"><i class='app-menu__icon fa fa-money'></i><span
                class="app-menu__label">HỌC PHÍ</span></a></li>
        <li><a class="app-menu__item" href="customermanager"><i class='app-menu__icon fa fa-user'></i><span
                class="app-menu__label">ĐIỂM DANH</span></a></li>
        <li><a class="app-menu__item" href="productmanager"><i
                class='app-menu__icon fa fa-users'></i><span class="app-menu__label">QUẢN LÝ NHÂN SỰ</span></a>
        </li>
        <li><a class="app-menu__item" href="ordermanager"><i class='app-menu__icon fa fa-notes-medical'></i><span
                    class="app-menu__label" style="text-wrap: pretty;">BÁO CÁO SỨC KHỎE CỦA HỌC SINH </span></a></li>
        <li><a class="app-menu__item" href="admin"><i class='app-menu__icon fa fa-utensils'></i><span
                class="app-menu__label">THỰC ĐƠN</span></a></li>
                <li><a class="app-menu__item" href="admin"><i class='app-menu__icon fa fa-bell'></i><span
                class="app-menu__label">THÔNG BÁO</span></a></li>
    </ul>
</aside>

            





<script src="../js/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="../js/popper.min.js"></script>
<script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
<!--===============================================================================================-->
<script src="../js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="../js/main.js"></script>
<!--===============================================================================================-->
<script src="../js/plugins/pace.min.js"></script>
<!--===============================================================================================-->
<!--===============================================================================================-->
</body>

</html>