<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Title</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" type="text/css" href="../css/restyle.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <style>
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

            <!-- head teacher dashboard start-->
            <ul class="app-menu">
                <li><a class="app-menu__item" href="class"><i class='app-menu__icon bx bx-group'></i><span
                            class="app-menu__label">Quản lý lớp học</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-calendar-event'></i><span
                            class="app-menu__label">Quản lý thời khóa biểu</span></a></li>
                <li><a class="app-menu__item" href="../headteacher/listpupil"><i class='app-menu__icon bx bx-user'></i><span
                            class="app-menu__label">Quản lý học sinh</span></a></li>
                <li><a class="app-menu__item" href="../headteacher/listpersonnel"><i class='app-menu__icon bx bx-user-check'></i><span
                            class="app-menu__label">Quản lý nhân sự</span></a></li>
                            <li><a class="app-menu__item" href="../headteacher/listpersonnel"><i class='app-menu__icon bx bx-food-menu'></i><span
                            class="app-menu__label">Quản lý thực đơn</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-calendar'></i><span
                            class="app-menu__label">Sự kiện</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-medal'></i><span
                            class="app-menu__label">Tổng kết <br> khen thưởng học sinh</span></a></li>
            </ul>
            <!-- head teacher dashboard end-->
        </aside>

                    





        <script src="../js/jquery-3.2.1.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/main.js"></script>
        <script src="../js/plugins/pace.min.js"></script>
        <script>
                            function previewAvatar(event) {
                                const reader = new FileReader();
                                reader.onload = function () {
                                    const output = document.getElementById('avatarDisplay');
                                    output.src = reader.result;
                                }
                                reader.readAsDataURL(event.target.files[0]);
                            }

                            function redirectToInfoPage() {
                                window.location.href = '${pageContext.request.contextPath}/headteacher/information';
                            }
        </script>
    </body>
</html>
