<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <link rel="shortcut icon" type="image/x-icon" href="../image/logo.png" />
    <title>Quản lý nhân sự</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function () {
            var toastMessage = '<%= request.getAttribute("message") %>';
            var toastType = '<%= request.getAttribute("type") %>';
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'fail') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    <style >
        #style-span{
            padding: 11px 150px;
            margin-top: 10px;
            border-radius: 20px;
            margin-bottom: 15px;
        }
        table.table-bordered, table.table-bordered th, table.table-bordered td {
            border: 2px solid black;
            text-align: center;
        }
        .accept-button , decltine-button , return-button {
            color: #001C41;
            background-color: #4CB5FB;
            cursor: pointer;

            padding: 5px 0px;
            display: block;
        }
        .decline-button{
            color: #001C41;
            background-color: red;
            cursor: pointer;

            padding: 5px 0px;
            display: block;
        }
        .return-button{
            color: #001C41;
            background-color: #99ff99;
            cursor: pointer;

            padding: 5px 0px;
            display: block;
        }
        .accept-button:hover{
            background-color: white;
            border: 1px grey solid;
        }
        .decline-button:hover{
            background-color: white;
            border: 1px grey solid;
        }
        .return-button:hover{
            background-color: white;
            border: 1px grey solid;
        }

        #myForm{
            display: flex;
            justify-content: space-evenly;
            font-weight: bold;
        }


    </style>
</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">

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
                                                        <img class="profile_img" src="../images/${p.getAvatar()}" alt="ảnh nhân viên" width="191px" height="263px" object-fit: cover>

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
                                                                Giáo vụ
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
                                                        <h3 class="mb-0"><i class="far fa-clone pr-1"></i>Thông tin chi tiết</h3>
                                                    </div>
                                                    <div class="card-body pt-0">
                                                        <table class="table table-bordered">
                                                            <tr>
                                                                <th style="text-align: left;">Ngày sinh</th>

                                                                <td style="text-align: left;">${p.getBirthday()}</td>
                                                            </tr>
                                                            <tr>
                                                                <th style="text-align: left;">Tình trạng công tác</th>
                                                                <c:if test="${p.getStatus() != null}">
                                                                    <c:choose>
                                                                        <c:when test="${p.getStatus() == 'đang làm việc'}">
                                                                            <td style="text-align: left; color: #4fff33;">${p.getStatus()}</td>
                                                                        </c:when>
                                                                        <c:when test="${p.getStatus() == 'đang chờ xử lý'}">
                                                                            <td style="text-align: left; color: #ff2848;">${p.getStatus()}</td>
                                                                        </c:when>
                                                                        <c:otherwise>
                                                                            <td style="text-align: left; color: #4c67ff;">${p.getStatus()}</td>
                                                                        </c:otherwise>
                                                                    </c:choose>
                                                                </c:if>
                                                            </tr>
                                                            <tr>
                                                                <th style="text-align: left;">Giới tính</th>

                                                                <td style="text-align: left;">
                                                                    <c:if test="${p.getGender()==true}">
                                                                        Nam
                                                                    </c:if>
                                                                    <c:if test="${p.getGender()==false}">
                                                                        Nữ
                                                                    </c:if>
                                                                </td>
                                                            </tr>
                                                            <tr>
                                                                <th style="text-align: left;">Địa chỉ</th>

                                                                <td style="text-align: left;">${p.getAddress()}</td>
                                                            </tr>

                                                            <tr>
                                                                <th style="text-align: left;">Email</th>

                                                                <td style="text-align: left;">${p.getEmail()}</td>
                                                            </tr>
                                                            <tr>
                                                                <th style="text-align: left;">Số điện thoại</th>

                                                                <td style="text-align: left;">${p.getPhoneNumber()}</td>
                                                            </tr>

                                                        </table>
                                                    </div>
                                                </div>
                                                <div style="height: 26px"></div>
                                                <div >

                                                    <div >

                                                        <div class="row" style="text-align: center; align-content: center">
                                                            <c:if test="${p.getStatus() == 'đang chờ xử lý'}">
                                                                <div class="col-lg-4"><a class="accept-button" onclick="submitForm('accept', '${p.getId()}')">Chấp nhận</a></div>
                                                                <div class="col-lg-4"><a class="decline-button" onclick="submitForm('decline', '${p.getId()}')">Từ chối</a> </div>
                                                            </c:if>
                                                            <c:if test="${p.getStatus() != 'đang chờ xử lý'}">
                                                                <div class="col-lg-4"></div>
                                                                <div class="col-lg-4"></div>
                                                            </c:if>
                                                            <div class="col-lg-4"><a class="return-button" onclick="redirect()">Danh sách nhân viên</a></div>


                                                        </div>
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

            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>








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
        window.location.href = "listpersonnel";
    }
</script>
<script>
    function submitForm(action, id) {
        // Tạo form
        var form = document.createElement("form");
        form.method = "post";
        form.action = "waitlistpersonnel";

        // Tạo input hidden cho action
        var actionInput = document.createElement("input");
        actionInput.type = "hidden";
        actionInput.name = "action";
        actionInput.value = action;
        form.appendChild(actionInput);

        // Tạo input hidden cho id
        var idInput = document.createElement("input");
        idInput.type = "hidden";
        idInput.name = "id";
        idInput.value = id;
        form.appendChild(idInput);

        // Thêm form vào body và submit
        document.body.appendChild(form);
        form.submit();
    }
</script>


</html>