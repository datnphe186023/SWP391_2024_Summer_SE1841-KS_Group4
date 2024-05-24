<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Title</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
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
                    <header style="text-decoration: #007bff; background-color:#000; color: #ffffff; padding-left:200px; padding-right: 200px   " >THÔNG TIN NHÂN VIÊN</header>  
                </div>

            </div>
            <c:set var="p" value="${requestScope.person}"/>

            

            <section>
                <div class="rt-container">
                    <div class="col-rt-12">
                        <div class="Scriptcontent">

                            <!-- Student Profile -->
                            <div class="student-profile py-4">
                                <div class="container">
                                    <div class="row">
                                        <div class="col-lg-4">
                                            <div class="card shadow-sm">
                                                <div class="card-header bg-transparent text-center">
                                                    <img class="profile_img" src="images/${p.getAvatar()}" alt="ảnh nhân viên" width="191px" height="263px" object-fit: cover>

                                                </div>
                                                <div class="card-body">
                                                    <p class="mb-0"><strong class="pr-1">Mã nhân viên:</strong>${p.getId()}</p>
                                                    <p class="mb-0"><strong class="pr-1">Tên:</strong>${p.getLastName()} ${p.getFirstName()}</p>
                                                    <p class="mb-0"><strong class="pr-1">Chức vụ:</strong> 
                                                        <c:if test="${p.getRoleId()== 0}">     
                                                            Admin
                                                        </c:if>
                                                        <c:if test="${p.getRoleId()==1}">     
                                                            Hiệu trưởng
                                                        </c:if>
                                                        <c:if test="${p.getRoleId()==2}">     
                                                            Accademic Staff
                                                        </c:if>
                                                        <c:if test="${p.getRoleId()==3}">     
                                                            Nhân viên kế toán
                                                        </c:if>
                                                        <c:if test="${p.getRoleId()==4}">     
                                                            Giáo viên
                                                        </c:if></p>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-lg-8">
                                            <div class="card shadow-sm">
                                                <div class="card-header bg-transparent border-0">
                                                    <h3 class="mb-0"><i class="far fa-clone pr-1"></i>Thông tin chung</h3>
                                                </div>
                                                <div class="card-body pt-0">
                                                    <table class="table table-bordered">
                                                        <tr>
                                                            <th width="30%">Ngày sinh</th>
                                                            <td width="2%">:</td>
                                                            <td>${p.getBirthday()}</td>
                                                        </tr>
                                                        <tr>
                                                            <th width="30%">Tình trạng công tác</th>
                                                            <td width="2%">:</td>
                                                            <td>${p.getStatus()}</td>
                                                        </tr>
                                                        <tr>
                                                            <th width="30%">Giới tính</th>
                                                            <td width="2%">:</td>
                                                            <td>
                                                                <c:if test="${p.getGender()==true}">     
                                                                    Nam
                                                                </c:if>
                                                                <c:if test="${p.getGender()==false}">     
                                                                    Nữ
                                                                </c:if>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <th width="30%">Địa chỉ</th>
                                                            <td width="2%">:</td>
                                                            <td>${p.getAddress()}</td>
                                                        </tr>

                                                        <tr>
                                                            <th width="30%">Email</th>
                                                            <td width="2%">:</td>
                                                            <td>${p.getEmail()}</td>
                                                        </tr>
                                                        <tr>
                                                            <th width="30%">Số điện thoại</th>
                                                            <td width="2%">:</td>
                                                            <td>${p.getPhoneNumber()}</td>
                                                        </tr>

                                                    </table>
                                                </div>
                                            </div>
                                            <div style="height: 26px"></div>
                                            <div class="button">
                                                
                                                <div style="text-align: center">
                                                    <button style="background: #996fcb; color: #ffffff; border: none" type="button" onclick="redirect()">Quay lại </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- partial -->

                        </div>
                    </div>
                </div>
            </section>



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
                                                                                                        // JavaScript Function to Redirect to Product Details Page
                                                                                                        function redirect() {
                                                                                                          // Assuming x is your result set containing product details
                                                                                                          // Replace this line with the correct way to retrieve product ID from your data
                                                                                                          
                                                                                                          // Redirect to the product details page with the product ID
                                                                                                          window.location.href = "acclistpersonnel";
                                                                                                                         }
                                                                                                        </script> 
    </body>

</html>