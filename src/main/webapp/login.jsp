<%-- 
    Document   : login
    Created on : Mar 5, 2024, 3:37:45 PM
    Author     : Admin
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Login Page</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Style CSS -->
        <link rel="stylesheet" type="text/css" href="css/style-login.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <style>
            /* CSS for circular image */
            .logo img {
                border-radius: 50%;
                width: 180px;
                height: 180px;
                position: absolute;
                top: 30px;
                left: 50%;
                transform: translateX(-50%);
            }

        </style>
    </head>

    <body class="app sidebar-mini rtl">

        <div class="logo">
            <a href="#">
                <img src="image/logo.png" alt="" />
            </a>
        </div>
        <div class="login-container">
            <div class="login-box">
                <h2>Đăng nhập</h2>
                <form action="login" method="POST">
                    <div class="input-box">
                        <i class="bx bx-user"></i>
                        <input type="text" id="username" name="username" required>
                        <label>Tài khoản</label>
                    </div>
                    <div class="input-box">
                        <i class="bx bx-lock"></i>
                        <input type="password" id="password" name="password" required>
                        <label>Mật khẩu</label>
                    </div>
                    <button type="submit" class="btn">Đăng nhập</button>
                    <span style="color:red">${error}</span>
                    <p class="forgot-password"><a href="forgotPassword">Quên mật khẩu?</a></p>
                </form>
            </div>
        </div>

        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>
        <script src="js/plugins/pace.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>

        <script>
            <c:if test="${not empty status}">
            swal({
                title: "Notification",
                text: "${status}",
                icon: "${status eq 'Đã đổi mật khẩu thành công !' ? 'success' : 'error'}",
                button: "OK"
            });
            </c:if>
        </script>

    </body>
</html>
