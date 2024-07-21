<!doctype html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>Đổi mật khẩu</title>
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
    <body >

        <div class="uf-form-signin">
            <div class="text-center" >
                <img src="./assets/img/logoVIP.png" alt="" width="150" height="150" class="rounded-image">
                <h4 style="color: #e2dcdc">Đổi mật khẩu</h4>
            </div>
            <form class="form-horizontal" action="newPassword" method="POST">

                <div class="input-group uf-input-group input-group-lg mb-3">
                    <span class="input-group-text fa fa-key"></span>
                    <input class="form-control" type="password" name="password" placeholder="Nhập mật khẩu mới"
                           pattern=".{8,12}" required title="Mật khẩu phải từ 8 đến 12 ký tự">
                </div>
                <div class="input-group uf-input-group input-group-lg mb-3">
                    <span class="input-group-text fa fa-key"></span>
                    <input class="form-control" type="password" name="confPassword" placeholder="Nhập lại mật khẩu mới"
                           pattern=".{8,12}" required title="Mật khẩu phải từ 8 đến 12 ký tự">
                </div>

                <div class="d-grid mb-4">
                    <button type="submit" class="btn uf-btn-primary btn-lg">Lưu mật khẩu</button>
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
            <c:if test="${not empty err}">
            swal({
                title: "Error",
                text: "${err}",
                icon: "error",
                button: "OK"
            });
            </c:if>
        </script>
    </body>
</html>