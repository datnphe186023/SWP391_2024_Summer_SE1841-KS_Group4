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
                <li><a class="app-menu__item" href="createuser"><i class='app-menu__icon bx bx-user-plus'></i><span
                            class="app-menu__label">Tạo tài khoản</span></a></li>
                <li><a class="app-menu__item" href="manageruser"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý tài khoản</span></a></li>
                <li><a class="app-menu__item" href="view-information"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Thông tin cá nhân</span></a></li>
            </ul>
            <!-- Admin homepage end-->


            <!--  teacher dashboard end-->




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
                        <img class="app-sidebar__user-avatar" id="avatarDisplay" src="${pageContext.request.contextPath}/images/${sessionScope.personnel.avatar}" >
                        <input class="avatar-input" id="avatarInput" type="file" name="avatar">
                        <div>
                            <p class="app-sidebar__user-name"><b style="color: #000">${personnel.lastName} ${personnel.firstName}</b></p>
                            <button class="change-password-btn" data-toggle="modal" data-target="#changePasswordModal">Đổi mật khẩu</button>
                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tile">
                        <div class="tile-body">
                            <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+"/>
                            <form action="${pageContext.request.contextPath}/update-information" method="post">
                                <input type="hidden" name="id" value="${personnel.userId}"/>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>ID người dùng :</h5><input placeholder="User Id" type="text" name="userId" value="${personnel.userId}" disabled/>
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Tên:</h5> <input placeholder="First Name" type="text" name="first_name" value="${personnel.firstName}" pattern="^[a-zA-Z${vietnamesePattern}\s]{1,50}$" title="Tên không được chứa số hoặc kí tự đặc biệt (Tối đa 50 kí tự)" /><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Họ:</h5> <input type="text" name="last_name" value="${personnel.lastName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,20}$" title="Họ không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <label for="gender">Giới tính</label>
                                                    <select class="form-control" id="gender" name="gender" style="width: 250px">
                                                        <option value="true" ${sessionScope.personnel.gender ? 'selected' : ''}>Nam</option>
                                                        <option value="false" ${!sessionScope.personnel.gender ? 'selected' : ''}>Nữ</option>
                                                    </select>
                                                </div>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">    
                                                    <h5>Ngày sinh:</h5> <input type="date" name="birthday" value="${personnel.birthday}" disabled/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Địa chỉ:</h5> <input type="text" name="address" value="${personnel.address}" style="width: 200%" pattern="^[A-Za-z1-9,${vietnamesePattern}\s]{1,100}$" title="Địa chỉ không được quá 100 kí tự"/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Email:</h5> <input type="email" name="email" value="${personnel.email}" style="width: 170%"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <input type="text" id="phone_number" name="phone_number" value="${personnel.phoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại." required /><br />
                                                </div>
                                            </td>
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
