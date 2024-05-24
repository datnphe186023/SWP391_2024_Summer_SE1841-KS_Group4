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

            <!-- Admin homepage start-->
            <ul class="app-menu">
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-plus'></i><span
                            class="app-menu__label">Tạo tài khoản</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý tài khoản</span></a></li>

            </ul>
            <!-- Admin homepage end-->
        </aside>

        <!-- Head Teacher Information Section -->
        <main class="app-content">
            <div class="app-title">
                <div>
                    <h1><i class="fa fa-edit"></i> Thông tin giáo viên</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="app-sidebar__user">
                        <img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${sessionScope.personnel.avatar}" alt="User Image" onclick="document.getElementById('avatarInput').click()">
                        <input class="avatar-input" id="avatarInput" type="file" name="avatar" accept="image/*" onchange="previewAvatar(event)">
                    </div>
                    <div class="mt-3">
                        <button class="btn btn-primary btn-block" onclick="location.href = 'changePasswordPage'">Đổi mật khẩu</button>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tile">
                        <div class="tile-body">
                            <form method="post" action="/admin/information" enctype="multipart/form-data">

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="id">ID</label>
                                        <input class="form-control" id="id" type="text" name="id" value="${user.id}" readonly style="width: 250px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="username">Username</label>
                                        <input class="form-control" id="username" type="text" name="username" value="${user.username}" readonly style="width: 250px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="firstName">Họ</label>
                                        <input class="form-control" type="text" name="firstName" value="${personnel.firstName}" style="width: 250px" disabled>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="lastName">Tên</label>
                                        <input class="form-control" type="text" name="lastName" value="${personnel.lastName}" style="width: 250px" disabled>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="gender">Giới tính</label>
                                        <select class="form-control" id="gender" name="gender" style="width: 250px" disabled>
                                            <option value="Male" ${sessionScope.personnel.gender ? 'selected' : ''}>Nam</option>
                                            <option value="Female" ${!sessionScope.personnel.gender ? 'selected' : ''}>Nữ</option>
                                        </select>
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label for="phone">Số điện thoại</label>
                                        <input class="form-control" id="phone" type="text" name="phone" value="${personnel.phoneNumber}" style="width: 250px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="email">Email</label>
                                        <input class="form-control" id="email" type="email" name="email" value="${personnel.email}" style="width: 350px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="address">Địa chỉ</label>
                                        <input class="form-control" id="address" type="textarea" name="address" value="${personnel.address}">
                                    </div>
                                </div>
                                <!-- Remove Avatar input from form since it's handled separately in sidebar -->
                                <div class="form-group">
                                    <button class="btn btn-primary" type="submit">Cập nhật thông tin</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>





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
                                window.location.href = '${pageContext.request.contextPath}/admin/information';
                            }
        </script>
    </body>
</html>
