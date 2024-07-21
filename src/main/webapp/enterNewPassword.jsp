<!doctype html>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
    <head>
        <title>Nhập mật khẩu mới </title>
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
                <img src="./assets/img/logoVIP.png" alt="" width="150" height="150" class="rounded-image">
                <h4 style="color: #e2dcdc">Nhập mật khẩu</h4>
                
            </div>
            <form class="mt-4" id="register-form" action="ValidatePassword" role="form" autocomplete="off" method="post">
                <div class="input-group uf-input-group input-group-lg mb-3">
                    <span class="input-group-text fa fa-key"></span>
                    <input type="password" class="form-control" id="otp" name="currentPassword" placeholder="Nhập mật khẩu" type="password" required>
                </div>
                <span  style="color:black; display: block; margin: 10px">${message}</span>
                <span  style="color:red; display: block; margin: 10px">${error}</span>
                <div class="d-grid mb-4">
                    <button type="submit" class="btn uf-btn-primary btn-lg">Đổi mật khẩu</button>
                </div>
            </form>
        </div>
        <script type='text/javascript' src='https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js'></script>
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="js/bootstrap.min.js"></script>
    </body>
</html>
