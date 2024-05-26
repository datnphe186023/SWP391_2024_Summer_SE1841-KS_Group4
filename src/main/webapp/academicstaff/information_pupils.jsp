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
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f7f7f7;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }

            .student-info {
                width: 600px;
                background-color: white;
                border-radius: 10px;
                box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                padding: 20px;
            }

            .header {
                display: flex;
                align-items: center;
                justify-content: space-between;
                margin-bottom: 20px;
            }

            .avatar {
                display: flex;
                flex-direction: column;
                align-items: center;
            }

            .avatar img {
                width: 70px;
                height: 70px;
                border-radius: 50%;
                margin-bottom: 10px;
            }

            .avatar button {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 5px 10px;
                border-radius: 5px;
                cursor: pointer;
            }

            .avatar button:hover {
                background-color: #0056b3;
            }

            .id {
                font-size: 18px;
                font-weight: bold;
            }

            form {
                display: flex;
                flex-direction: column;
            }

            .form-group {
                margin-bottom: 15px;
            }

            label {
                margin-bottom: 5px;
                display: block;
                font-weight: bold;
            }

            input, textarea {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            textarea {
                height: 80px;
                resize: none;
            }

            .form-buttons {
                display: flex;
                justify-content: space-between;
            }

            button.cancel {
                background-color: red;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
            }

            button.save {
                background-color: green;
                color: white;
                border: none;
                padding: 10px 20px;
                border-radius: 5px;
                cursor: pointer;
            }

            button.cancel:hover {
                background-color: darkred;
            }

            button.save:hover {
                background-color: darkgreen;
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

            <ul class="app-menu">
                <li><a class="app-menu__item" href="#"><i class='app-menu__icon fa fa-bell'></i><span
                            class="app-menu__label">Thông báo</span></a></li>
                <li><a class="app-menu__item" href="../academicstaff/listpupil"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý học sinh</span></a></li>

                <li><a class="app-menu__item" href="#"><i class='app-menu__icon bx bxs-user-detail'></i><span
                            class="app-menu__label">Quản lý lớp học</span></a></li>
                <li><a class="app-menu__item"
                       href="#"
                       target="_blank"><i class='app-menu__icon bx bx-task'></i><span
                            class="app-menu__label">Đơn từ</span></a></li>
            </ul>


            <!--  teacher dashboard end-->




        </aside>
        <div class="student-info" style="width: 60%">
            <div class="header" style="margin-top: 250px" >
                <div class="avatar" >
                    <img  src="${p.avatar}" alt="Avatar"/>
                </div>
                <div class="id">ID: <span>${p.id}</span></div>
            </div>
            <form action="updatePulpil" method="POST">
                <div class="form-group">
                    <label for="fullname">Họ và tên:</label>
                    <input type="text" id="fullname" value="${p.lastName} ${p.firstName}">
                </div>
                <div class="form-group">
                    <label for="birthday">Ngày sinh:</label>
                    <input type="date" id="birthday" name="date" placeholder="DD/MM/YYYY" value="${p.birthday}">
                </div>
                <div class="form-group">
                    <label for="mother-name">Họ và tên mẹ:</label>
                    <input type="text" id="mother-name" value="${p.motherName}">
                </div>
                <div class="form-group">
                    <label for="mother-phone">Số điện thoại mẹ:</label>
                    <input type="text" id="mother-phone" value="(+84)${p.motherPhoneNumber}">
                </div>
                <div class="form-group">
                    <label for="father-name">Họ và tên bố:</label>
                    <input type="text" id="father-name" value="${p.fatherName}">
                </div>
                <div class="form-group">
                    <label for="father-phone">Số điện thoại bố:</label>
                    <input type="text" id="father-phone" value="(+84)${p.fatherPhoneNumber}">
                </div>
                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <input type="text" id="address" value="${p.address}">
                </div>
                <div class="form-group special-notes">
                    <label for="notes">Ghi chú đặc biệt của phụ huynh:</label>
                    <input id="notes" value="${p.parentSpecialNote}"></input>
                </div>
                <div class="form-buttons">
                    <button class="cancel" type="button" onclick="cancelAction()">HUỶ</button>
                    <button type="submit" class="save">LƯU</button>
                </div>
            </form>
        </div>






        <script src="../js/jquery-3.2.1.min.js"></script>
        <script src="../js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="../js/bootstrap.min.js"></script>
        <script src="../js/main.js"></script>
        <script src="../js/plugins/pace.min.js"></script>
        <script>
                        function cancelAction() {   
                            window.location.href = '${pageContext.request.contextPath}/academicstaff/listpupil';
                        }
                        function previewAvatar(event) {
                            const reader = new FileReader();
                            reader.onload = function () {
                                const output = document.getElementById('avatarDisplay');
                                output.src = reader.result;
                            };
                            reader.readAsDataURL(event.target.files[0]);
                        }

                        function redirectToInfoPage() {
                            window.location.href = '${pageContext.request.contextPath}/academicstaff/information';
                        }
        </script>
    </body>
</html>
