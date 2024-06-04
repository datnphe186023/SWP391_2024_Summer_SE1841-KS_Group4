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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
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

            .change-password-btn {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin-top: 10px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .change-password-btn:hover {
                background-color: #0056b3;
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
                <li><a class="app-nav__item" href="${pageContext.request.contextPath}/logout"><i class='bx bx-log-out bx-rotate-180'></i> Logout </a>
                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user">
                <img class="app-sidebar__user-avatar" id="avatarDisplay" src="${pageContext.request.contextPath}/images/${sessionScope.pupil.avatar}" alt="User Image" onclick="redirectToInfoPage()">
                <input class="avatar-input" id="avatarInput" type="file" name="avatar" accept="image/*" onchange="previewAvatar(event)">
                <div>
                    <p class="app-sidebar__user-name"><b>${pupil.lastName} ${pupil.firstName}</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>

            <!-- parent dashboard start-->
            <ul class="app-menu">
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-bell'></i><span
                            class="app-menu__label">Thông báo</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-check-square'></i><span
                            class="app-menu__label">Điểm danh</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-time'></i><span
                            class="app-menu__label">Thời khóa biểu</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-food-menu'></i><span
                            class="app-menu__label">Thực đơn</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-envelope'></i><span
                            class="app-menu__label">Gửi đơn</span></a></li>
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bx-file'></i><span
                            class="app-menu__label">Báo cáo</span></a></li>
            </ul>
        </aside>

        <!-- Head Teacher Information Section -->
        <main class="app-content">
            <div class="app-title">
                <div>
                    <h1><i class="fa fa-edit"></i> Thông tin phụ huynh</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="app-sidebar__user">
                        <img class="app-sidebar__user-avatar" id="avatarDisplay" src="${pageContext.request.contextPath}/images/${sessionScope.pupil.avatar}" >
                        <input class="avatar-input" id="avatarInput" type="file" name="avatar">
                        <div>
                            <p class="app-sidebar__user-name"><b style="color: #000">${pupil.lastName} ${pupil.firstName}</b></p>
                            <button class="change-password-btn" data-toggle="modal" data-target="#changePasswordModal">Đổi mật khẩu</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tile">
                        <div class="tile-body">

                            <form action="update" method="post">
                                <input type="hidden" name="id" value="${personnel.userId}"/>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>ID người dùng:</h5><input placeholder="User Id" type="text" name="userId" value="${pupil.userId}" disabled/>
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>ID học sinh: </h5> <input placeholder="First Name" type="text" name="id" value="${pupil.id}" disabled/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Họ tên mẹ : </h5> <input type="text" name="mother_name" value="${pupil.motherName}"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Số điện thoại mẹ :</h5> <input type="text" name="mother_phone" value="${pupil.motherPhoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại."/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Họ tên bố : </h5> <input type="text" name="father_name" value="${pupil.fatherName}"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Số điện thoại bố :</h5> <input type="text" name="father_phone" value="${pupil.fatherPhoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại."/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">    
                                                    <h5>Họ tên bé :</h5> <input type="text" name="name_pupil" value="${pupil.lastName} ${pupil.firstName}" disabled/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Ngày sinh của bé : </h5> <input type="date" name="birthday" value="${pupil.birthday}" disabled/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Email :</h5> <input type="email" name="email" value="${pupil.email}" style="width: 170%"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Địa chỉ : </h5> <input type="text" name="address" value="${pupil.address}" style="width: 170%"/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Tình trạng :</h5> <input type="text" name="status" value="${pupil.status}" style="width: 150%" disabled/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Ghi chú : </h5> <input type="text" name="note" value="${pupil.parentSpecialNote}" style="width: 200%"/><br />
                                                </div></td>
                                        </tr>
                                    </tbody>
                                </table>
                                </br>
                                <input type="submit" value="Lưu thông tin" style="width: 20%"/>
                            </form>

                            <!-- Password Change Modal -->
                            <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
                                <div class="modal-dialog" role="document">
                                    <div class="modal-content">
                                        
                                        <div class="modal-body">
                                            <form action="${pageContext.request.contextPath}/new-password" method="post">
                                                <div class="form-group">
                                                    <label for="oldPassword">Mật khẩu cũ:</label>
                                                    <input type="password" id="oldPassword" name="oldPassword" class="form-control" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="newPassword">Mật khẩu mới:</label>
                                                    <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                                                </div>
                                                <div class="form-group">
                                                    <label for="confirmPassword">Xác nhận mật khẩu:</label>
                                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                                                </div>
                                                <input type="submit" value="Đổi mật khẩu" class="btn btn-primary">
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- End Password Change Modal -->

                        </div>
                    </div>
                </div>
            </div>
        </main>

        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/pace.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>
        
    </body>

</html>
