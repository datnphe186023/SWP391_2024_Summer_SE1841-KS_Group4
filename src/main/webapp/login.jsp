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
        <title>Đăng nhập</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Style CSS -->
        <link rel="stylesheet" type="text/css" href="css/style-login.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="./assets/css/bootstrap.min.css">
        <!-- FontAwesome CSS -->
        <link rel="stylesheet" href="./assets/css/all.min.css">
        <link rel="stylesheet" href="./assets/css/uf-style.css">
        
    </head>

    <body>


        <div class="uf-form-signin">
            <div class="text-center" >
                <img src="./assets/img/logoVIP.png" alt="" width="200" height="200" class="rounded-image">
            </div>
            <form action="login" method="POST" class="mt-4">

                <div class="input-group uf-input-group input-group-lg mb-3">
                    <span class="input-group-text fa fa-user"></span>
                    <input type="text" class="form-control" name="username" placeholder="Tài khoản" required>
                </div>
                <div class="input-group uf-input-group input-group-lg mb-3">
                    <span class="input-group-text fa fa-lock"></span>
                    <input type="password" class="form-control" name="password" placeholder="Mật khẩu" required>
                    <span  style="color:red; display: block;" class="mt-2">${requestScope.error}</span>
                </div>
                <div class="d-grid mb-3">
                    <button type="submit" class="btn uf-btn-primary btn-lg">Đăng nhập</button>
                </div>
                <div class="d-flex justify-content-center">
                    <a href="forgotPassword">Quên mật khẩu ?</a>
                </div>

            </form>
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
