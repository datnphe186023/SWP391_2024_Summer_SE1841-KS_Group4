<%-- 
    Document   : dashboard_admin_editUser
    Created on : 28 thg 5, 2024, 04:45:42
    Author     : TuyenCute
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
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

            .form-buttons {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 20px;
            }

            .update-btn, .cancel-btn {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                font-size: 16px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .update-btn:hover, .cancel-btn:hover {
                background-color: #0056b3;
            }

            .cancel-btn {
                background-color: #dc3545;
            }

            .cancel-btn:hover {
                background-color: #c82333;
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
                <li><a class="app-menu__item" href="createuser"><i class='app-menu__icon bx bx-user-plus'></i><span
                            class="app-menu__label">Tạo tài khoản</span></a></li>
                <li><a class="app-menu__item" href="manageruser"><i class='app-menu__icon bx bx-user-voice'></i><span
                            class="app-menu__label">Quản lý tài khoản</span></a></li>

            </ul>
            <!-- Admin homepage end-->


            <!--  teacher dashboard end-->




        </aside>
        <main class="app-content">
            <div class="app-title">
                <div>
                    <h1><i class="fa fa-edit"></i> Chi tiết tài khoản</h1>
                </div>
            </div>
        </div>
        <div class="col-md-9">
            <div class="tile">
                <div class="tile-body">

                    <form action="userprofile" method="post">

                        <table>
                            <tbody>
                                <tr>
                                    <td><div class="form-group col-md-6">
                                            <h5>Personnel ID :</h5><input placeholder="Personnel Id" type="text" name="userId" value="${user.getId()}" readonly=""/>
                                        </div></td>
                                    <td><div class="form-group col-md-6">
                                            <h5>User Name : </h5> <input placeholder="User Name" type="text" name="userName" value="${user.getUsername()}" readonly=""/><br />
                                        </div></td>
                                </tr>
                                <tr>
                                    <td><div class="form-group col-md-6">
                                            <h5>Vai Trò : ${roleMap[user.getRoleId()]}</h5> 
                                            <select name="role">
                                                <option value="0">Admin</option>
                                                <option value="1">Head Teacher</option>
                                                <option value="2">Academic Staff</option>
                                                <option value="3">Accountant</option>
                                                <option value="4">Teacher</option>
                                                <option value="5">Parent</option>
                                            </select><br />
                                        </div></td>
                                    <td><div class="form-group col-md-6">

                                            <h5>Email :</h5> <input style="width: 200%" type="text" name="email" value="${user.getEmail()}"/><br />
                                        </div></td>
                                </tr>
                                <tr>
                                    <td><div class="form-group col-md-6">
                                            <h5>Trạng Thái : ${roleDis[user.getIsDisabled()]} </h5> 
                                            <select name="active">
                                                <option value="0">Active</option>
                                                <option value="1">Disable</option>
                                            </select><br />
                                        </div></td>

                                </tr>                        
                            </tbody>
                        </table>
                        <div class="form-buttons">
                            <button type="button" class="cancel-btn" onclick="cancelAction()">Quay Lại</button>
                            <button type="submit" class="update-btn">Chỉnh Sửa</button></a>
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
                                function cancelAction() {
                                    window.location.href = '${pageContext.request.contextPath}/admin/manageruser';
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
                                    window.location.href = '${pageContext.request.contextPath}/admin/information';
                                }
</script>
</body>
</html>
