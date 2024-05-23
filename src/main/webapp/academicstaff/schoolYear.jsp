<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <title>Danh Sách Năm Học</title>
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
    <ul class="app-menu">
        <li><a class="app-menu__item" href="#"><i class='app-menu__icon fa fa-bell'></i><span
                class="app-menu__label">Thông báo</span></a></li>
        <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                class="app-menu__label">Quản lý học sinh</span></a></li>

        <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bxs-user-detail'></i><span
                class="app-menu__label">Quản lý lớp học</span></a></li>
        <li><a class="app-menu__item"
               href="schoolyear"
               target="_blank"><i class='app-menu__icon bx bx-task'></i><span
                class="app-menu__label">Quản lý năm học</span></a></li>
        <li><a class="app-menu__item"
               href="#"
               target="_blank"><i class='app-menu__icon bx bx-task'></i><span
                class="app-menu__label">Đơn từ</span></a></li>
    </ul>
</aside>
<main>
    <div class="container my-4">
        <div class="text-center mb-3">
            <h2>Danh Sách Năm Học</h2>
        </div>

        <div class="d-flex justify-content-between align-items-center mb-3">
            <div class="input-group w-50">
                <input type="text" class="form-control" placeholder="Search by keyword" aria-label="Search">
                <div class="input-group-append">
                    <span class="input-group-text"><i class="fa fa-search"></i></span>
                </div>
            </div>
        </div>

        <table class="table table-bordered">
            <thead class="thead-light">
            <tr>
                <th scope="col">STT</th>
                <th scope="col">Năm học</th>
                <th scope="col">ID</th>
                <th scope="col">Ngày bắt đầu</th>
                <th scope="col">Ngày kết thúc</th>
                <th scope="col">Người tạo</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="schoolYear" items="${schoolYears}" varStatus="status">
                <tr>
                    <th scope="row">${status.index + 1}</th>
                    <td>${schoolYear.name}</td>
                    <td>${schoolYear.id}</td>
                    <td>${schoolYear.startDate}</td>
                    <td>${schoolYear.endDate}</td>
                    <td>${schoolYear.createdBy}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div class="d-flex justify-content-end">
            <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#newSchoolYearModal">
                TẠO NĂM HỌC MỚI
            </button>
        </div>

    </div>

    <!-- New School Year Modal -->
    <div class="modal fade" id="newSchoolYearModal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static" data-keyboard="false">
        <form action="schoolyear?action=create" method="POST">
            <div class="modal-dialog modal-dialog-centered" role="document">
                <div class="modal-content">
                    <div class="modal-body">
                        <div class="row">
                            <div class="form-group col-md-12">
                            <span class="thong-tin-thanh-toan">
                                <h5>Tạo Năm Học Mới</h5>
                            </span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="form-group col-md-6">
                                <label class="control-label">Năm học</label>
                                <input class="form-control" type="text" name="name" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Ngày bắt đầu</label>
                                <input class="form-control" type="date" name="startDate" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Ngày kết thúc</label>
                                <input class="form-control" type="date" name="endDate" required>
                            </div>
                            <div class="form-group col-md-6">
                                <label class="control-label">Mô Tả</label>
                                <input class="form-control" type="text" name="description" required>
                            </div>
                        </div>
                        <br>
                        <button class="btn btn-save" type="submit">Lưu lại</button>
                        <a class="btn btn-cancel" data-dismiss="modal" href="#">Hủy bỏ</a>
                        <br>
                    </div>
                </div>
            </div>
        </form>
    </div>
</main>
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