<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Title</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
    </head>

    <body onload="time()" class="app sidebar-mini rtl">
        <!-- Navbar-->
        <header class="app-header">
            <!-- Sidebar toggle button--><a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
                                            aria-label="Hide Sidebar"></a>
            <!-- Navbar Right Menu-->
            <ul class="app-nav">


                <!-- User Menu-->
                <li><a class="app-nav__item" href="login"><i class='bx bx-log-out bx-rotate-180'></i> Logout </a>

                </li>
            </ul>
        </header>
        <!-- Sidebar menu-->
        <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
        <aside class="app-sidebar">
            <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="images/${account.image}" width="50px"
                                                alt="User Image">
                <div>
                    <p class="app-sidebar__user-name"><b>${sessionScope.account.fullName}</b></p>
                    <p class="app-sidebar__user-designation">Chào mừng bạn trở lại</p>
                </div>
            </div>
            <hr>
            <ul class="app-menu">
                <li><a class="app-menu__item" href="admin"><i class='app-menu__icon fa fa-money'></i><span
                            class="app-menu__label">HỌC PHÍ</span></a></li>
                <li><a class="app-menu__item" href="customermanager"><i class='app-menu__icon fa fa-user'></i><span
                            class="app-menu__label">ĐIỂM DANH</span></a></li>
                <li><a class="app-menu__item" href="productmanager"><i
                            class='app-menu__icon fa fa-users'></i><span class="app-menu__label">QUẢN LÝ NHÂN SỰ</span></a>
                </li>
                <li><a class="app-menu__item" href="ordermanager"><i class='app-menu__icon fa fa-notes-medical'></i><span
                            class="app-menu__label" style="text-wrap: pretty;">BÁO CÁO SỨC KHỎE CỦA HỌC SINH </span></a></li>
                <li><a class="app-menu__item" href="admin"><i class='app-menu__icon fa fa-utensils'></i><span
                            class="app-menu__label">THỰC ĐƠN</span></a></li>
                <li><a class="app-menu__item" href="admin"><i class='app-menu__icon fa fa-bell'></i><span
                            class="app-menu__label">THÔNG BÁO</span></a></li>
            </ul>
        </aside>
        <main class="app-content">
            <div style="display: flex; justify-content: center">
                <div style="display: block; border: 2px solid black;
                     border-radius: 5px; ">
                    <header style="text-decoration: #007bff; background-color:#000; color: #ffffff; padding-left:200px; padding-right: 200px   " >THÊM NHÂN VIÊN MỚI</header>  
                </div>
            </div>
            <div>
                <form  name="create" action="htcreatepersonnel" method="post">
                    <label>Chức vụ :</label>
                    <select name="role"  id="role" required>
                        <option value="" hidden selected>Chọn chức vụ</option>
                        <option value="0">Admin</option>
                        <option value="1">Hiệu trưởng</option>
                        <option value="2">Academic staff</option>
                        <option value="3">Nhân viên kế toán</option>
                        <option value="4">Giáo viên</option>
                    </select>
                    </br>
                    <label for="firstname">Tên đệm và tên :</label> <input type="text" name="firstname" required></br>
                    <label for="lastname">Họ   :</label> <input type="text" name="lastname" required></br>
                    <label>Giới tính :</label>
                    <select name="gender" id="gender" required>
                        <option value="" hidden selected>Chọn giới tính</option>
                        <option value="0">Nữ</option>
                        <option value="1">Nam</option>
                    </select>
                    </br>
                    <label for="birthday">Ngày sinh :</label><input type="date" name="birthday" required></br>
                    <label for="address">Địa chỉ :</label><input type="text" name="address" required></br>
                    <label for="email">Email :</label><input type="text" name="email" required></br>
                    <label for="phone">Số điện thoại :</label><input type="text" name="phone" required></br>
                    <label>Ảnh :</label>
                    <div class="form-group col-md-12">

                        <div id="myfileupload">
                            <input type="file" id="uploadfile" name="avatar" onchange="readURL(this);" required/>
                        </div>
                        <div id="thumbbox">
                            <img height="200" width="200" alt="Thumb image" id="thumbimage" style="display: none" />
                            <a class="removeimg" href="javascript:"></a>
                        </div>
                        
                
            </div>
            </br>
            <input type="submit" name="SUBMIT">
            </form>
            </div>

        </main>






        <script src="js/jquery-3.2.1.min.js"></script>
        <!--===============================================================================================-->
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <!--===============================================================================================-->
        <script src="js/bootstrap.min.js"></script>
        <!--===============================================================================================-->
        <script src="js/main.js"></script>
        <!--===============================================================================================-->
        <script src="js/plugins/pace.min.js"></script>
        <!--===============================================================================================-->
        <!--===============================================================================================-->
        <script>
                                document.getElementById('role').addEventListener('change', function () {
                                    this.querySelector('option[hidden]').disabled = true;
                                });
                                document.getElementById('id').addEventListener('change', function () {
                                    this.querySelector('option[hidden]').disabled = true;
                                });
        </script>
        <script>

            function readURL(input, thumbimage) {
                if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
                    var reader = new FileReader();
                    reader.onload = function (e) {
                        $("#thumbimage").attr('src', e.target.result);
                    }
                    reader.readAsDataURL(input.files[0]);
                } else { // Sử dụng cho IE
                    $("#thumbimage").attr('src', input.value);

                }
                $("#thumbimage").show();
                $('.filename').text($("#uploadfile").val());
                $('.Choicefile').css('background', '#14142B');
                $('.Choicefile').css('cursor', 'default');
                $(".removeimg").show();
                $(".Choicefile").unbind('click');

            }
            $(document).ready(function () {
                $(".Choicefile").bind('click', function () {
                    $("#uploadfile").click();

                });
                $(".removeimg").click(function () {
                    $("#thumbimage").attr('src', '').hide();
                    $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
                    $(".removeimg").hide();
                    $(".Choicefile").bind('click', function () {
                        $("#uploadfile").click();
                    });
                    $('.Choicefile').css('background', '#14142B');
                    $('.Choicefile').css('cursor', 'pointer');
                    $(".filename").text("");
                });
            });
        </script>
        <script>
            const inpFile = document.getElementById("inpFile");
            const loadFile = document.getElementById("loadFile");
            const previewContainer = document.getElementById("imagePreview");
            const previewContainer = document.getElementById("imagePreview");
            const previewImage = previewContainer.querySelector(".image-preview__image");
            const previewDefaultText = previewContainer.querySelector(".image-preview__default-text");
            const object = new ActiveXObject("Scripting.FileSystemObject");
            inpFile.addEventListener("change", function () {
                const file = this.files[0];
                if (file) {
                    const reader = new FileReader();
                    previewDefaultText.style.display = "none";
                    previewImage.style.display = "block";
                    reader.addEventListener("load", function () {
                        previewImage.setAttribute("src", this.result);
                    });
                    reader.readAsDataURL(file);
                }
            });


        </script>
    </body>

</html>