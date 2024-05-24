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
                    <header style="text-decoration: #007bff; background-color:#000; color: #ffffff; padding-left:200px; padding-right: 200px   " >DANH SÁCH NHÂN VIÊN</header>  
                </div>

            </div>
            <style>
                /* Hide the placeholder in the dropdown options */
                option[hidden] {
                    display: none;
                }
            </style>
            <div>
                <div style="display: flex ;justify-content: space-between">
                    <div>
                        <button type="button" onclick="redirect()">Thêm nhân viên</button>
                    </div>
                    <div style="display: flex ;justify-content: space-between">
                        <form action="acclistpersonnel" method="post">    
                            <select name="role" onchange="this.form.submit()" id="role">
                                <option value="" hidden selected>Chức vụ</option>
                                <option value="0">Admin</option>
                                <option value="1">Hiệu trưởng</option>
                                <option value="2">Academic staff</option>
                                <option value="3">Nhân viên kế toán</option>
                                <option value="4">Giáo viên</option>
                            </select>
                        </form>  
                        <form action="acclistpersonnel" method="post" >    
                            <input  type="text" name="search" placeholder="Nhập Tên hoặc ID ">    

                        </form>
                    </div>
                </div>

            </div>

            <table border="10" cellspacing="2">
                <thead>
                    <tr>
                        <th>STT</th>
                        <th>Mã Nhân Viên</th>
                        <th>Tên</th>
                        <th>Giới Tính</th>
                        <th>Ngày sinh</th>
                        <th>Chức vụ</th>
                        <th>Trạng thái</th>
                        <th>Chi tiết</th>
                    </tr>
                </thead>
                <tbody>
                    <c:set var="count" value="0" />



                    <c:forEach items="${persons}" var="p">
                        <c:set var="count" value="${count + 1}" />      
                        <tr>
                            <td>${count}</td>
                            <td>${p.getId()}</td>
                            <td>${p.getLastName()} ${p.getFirstName()}</td>
                            <td>
                                <c:if test="${p.getGender()==true}">     
                                    Nam
                                </c:if>
                                <c:if test="${p.getGender()==false}">     
                                    Nữ
                                </c:if>
                            </td>
                            <td>${p.getBirthday()} </td>
                            <td>
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
                                </c:if>
                            </td>


                            <td>${p.getStatus()} </td>
                            <td>
                                <form action="accviewpersonnel" method="post">
                                    <input type="hidden" value="${p.getId()}" name="id">
                                    <input type="submit" value="Xem thông tin chi tiết" name="viewdetail" />     
                                </form>

                            </td>
                        </tr>
                    </c:forEach>

                </tbody>
            </table>

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
        </script>
        <script>
            // JavaScript Function to Redirect to Product Details Page
            function redirect() {
                // Assuming x is your result set containing product details
                // Replace this line with the correct way to retrieve product ID from your data

                // Redirect to the product details page with the product ID
                window.location.href = "acccreatepersonnel";
            }
        </script> 
    </body>



</html>