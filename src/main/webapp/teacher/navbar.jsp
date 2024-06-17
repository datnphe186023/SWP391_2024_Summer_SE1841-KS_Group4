<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Profile</title>

    <!-- Custom fonts for this template-->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
    
</head>

<body id="page-top">

    <!-- Sidebar -->
    <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

        <!-- Sidebar - Brand -->
        <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/teacher/dashboard">
            <div class="sidebar-brand-icon rotate-n-15">
                <i class="fas fa-laugh-wink"></i>
            </div>
            <div class="sidebar-brand-text mx-3">Bono Kindergarten</div>
        </a>

        <!-- Divider -->
        <hr class="sidebar-divider my-0">

        <!-- Nav Item - Thời khóa biểu -->
        <li class="nav-item">
            <a class="nav-link" href="#">
                <i class='app-menu__icon bx bx-time'></i>
                <span>Thời khóa biểu</span>
            </a>
        </li>

        <!-- Nav Item - Thực đơn -->
        <li class="nav-item">
            <a class="nav-link" href="#">
                <i class='app-menu__icon bx bx-food-menu'></i>
                <span>Thực đơn</span>
            </a>
        </li>

        <!-- Nav Item - Điểm danh -->
        <li class="nav-item">
            <a class="nav-link" href="#">
                <i class='app-menu__icon bx bx-check-square'></i>
                <span>Điểm danh</span>
            </a>
        </li>

        <!-- Nav Item - Quản lý học sinh -->
        <li class="nav-item">
            <a class="nav-link" href="${pageContext.request.contextPath}/teacher/listpupil?schoolYear=${requestScope.schoolYearLastest}">
                <i class='app-menu__icon bx bx-user'></i>
                <span>Quản lý học sinh</span>
            </a>
        </li>

        <!-- Nav Item - Thông báo -->
        <li class="nav-item">
            <a class="nav-link" href="listnotification">
                <i class='app-menu__icon bx bx-bell'></i>
                <span>Thông báo</span>
            </a>
        </li>

        <!-- Nav Item - Phiếu bé ngoan -->
        <li class="nav-item">
            <a class="nav-link" href="#">
                <i class='app-menu__icon bx bx-badge-check'></i>
                <span>Phiếu bé ngoan</span>
            </a>
        </li>

        <!-- Nav Item - Báo cáo sức khỏe -->
        <li class="nav-item">
            <a class="nav-link" href="#">
                <i class='app-menu__icon bx bx-heart'></i>
                <span>Báo cáo sức khỏe</span>
            </a>
        </li>

        <!-- Nav Item - Đánh giá học sinh hằng ngày -->
        <li class="nav-item">
            <a class="nav-link" href="#">
                <i class='app-menu__icon bx bx-star'></i>
                <span>Đánh giá học sinh <br> hằng ngày</span>
            </a>
        </li>

        <li class="nav-item">
            <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
               aria-expanded="true" aria-controls="collapseTwo">
                <i class="fas fa-fw fa-cog"></i>
                <span>Đơn Từ</span>
            </a>
            <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                <div class="bg-white py-2 collapse-inner rounded">
                    <a class="collapse-item" href="application">Xử Lý Đơn Từ</a>
                    <a class="collapse-item" href="sendapplication">Gửi Đơn</a>
                    <a class="collapse-item" href="sentapplications">Xem Đơn Đã Gửi</a>
                </div>
            </div>
        </li>


        <!-- Divider -->
        <hr class="sidebar-divider d-none d-md-block">

        <!-- Sidebar Toggler (Sidebar) -->
        <div class="text-center d-none d-md-inline">
            <button class="rounded-circle border-0" id="sidebarToggle"></button>
        </div>

    </ul>
    <!-- End of Sidebar -->


    <!-- Bootstrap core JavaScript-->
    <script src="../vendor/jquery/jquery.min.js"></script>
    <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Core plugin JavaScript-->
    <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

    <!-- Custom scripts for all pages-->
    <script src="../js/sb-admin-2.min.js"></script>

    <!-- Page level plugins -->
    <script src="../vendor/chart.js/Chart.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="../js/demo/chart-area-demo.js"></script>
    <script src="../js/demo/chart-pie-demo.js"></script>

</body>

</html>
