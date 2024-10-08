<%--
  Created by IntelliJ IDEA.
  User: asus
  Date: 6/10/2024
  Time: 2:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Trường Mầm Non BoNo</title>

    <!-- Custom fonts for this template-->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function () {
            var toastMessage = '<%= request.getAttribute("toastMessage") %>';
            var toastType = '<%= request.getAttribute("toastType") %>';
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'fail') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>

    <div id="content-wrapper" class="d-flex flex-column">

        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">

                <h1 class="h3 mb-4 text-gray-800 text-center"> Thực Đơn </h1>
                <div class="row">
                    <style>

                        .btn-container {
                            display: flex;
                            justify-content: space-between;
                            align-items: center;
                            margin-top: 20px;
                        }

                        .btn-group-right {
                            display: flex;
                            gap: 10px;
                        }
                        .class-form {
                            margin: 0 10px; /* Adjust the margin as needed */
                        }

                    </style>
                    <c:set var="sltedg" value="${requestScope.sltedg}"/>
                    <c:set var="sltedw" value="${requestScope.sltedw}"/>
                    <c:set var="sltedsy" value="${requestScope.sltedsy}"/>


                </div>
                <div class=" my-3">
                    <button class="btn btn-primary mx-2" style="width: 200px">
                        <a style="color: white; text-decoration: none;" href="waitlistmealtimetable">Quay lại danh sách</a>
                    </button>
                </div>
                <form action="processmealtimetable" method="post" id="process">
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary"><label  >Khối Lớp :</label> ${requestScope.grade.getName()}</h6>
                        <h6 class="m-0 font-weight-bold text-primary"><label >Năm học :</label> ${requestScope.schoolyear.getName()}</h6>
                        <h6 class="m-0 font-weight-bold text-primary"><label >Tuần học :</label> ${requestScope.week.getStartDatetoEndDate()}</h6>

                    </div>

                    <c:set var="timesOfDay" value="${['Bữa trưa', 'Bữa chiều', 'Bữa chiều phụ']}" />
                    <c:set var="daysOfWeek" value="${['Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy', 'Chủ Nhật']}" />
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th></th>
                                    <th>Thứ Hai</th>
                                    <th>Thứ Ba</th>
                                    <th>Thứ Tư</th>
                                    <th>Thứ Năm</th>
                                    <th>Thứ Sáu</th>
                                    <th>Thứ Bảy</th>
                                    <th>Chủ Nhật</th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:set value="${requestScope.menuDetailList}" var="menuDetailList"/>
                                <c:forEach var="timeOfDay" items="${timesOfDay}">
                                    <tr>
                                        <c:if test="${timeOfDay == 'Bữa trưa'}" >
                                            <td>Trưa</td>
                                        </c:if>
                                        <c:if test="${timeOfDay == 'Bữa chiều'}" >
                                            <td>Chiều</td>
                                        </c:if>
                                        <c:if test="${timeOfDay == 'Bữa chiều phụ'}" >
                                            <td>Chiều Phụ</td>
                                        </c:if>
                                        <c:forEach var="dayOfWeek" items="${daysOfWeek}">
                                            <td>
                                                <c:set var="menucheck" value="false"/>
                                                <c:forEach var="menu" items="${requestScope.menuDetailList}">
                                                    <c:if test="${menu.getTimeslot().getName() == timeOfDay && menu.getDay().convertToWeekDay() == dayOfWeek}">
                                                        <c:if test="${menu.getFoodMenu().getFoodDetails() != null }">
                                                            <div style="display:flex ; justify-content: left">
                                                                    ${menu.getFoodMenu().getFoodDetails()}
                                                            </div>
                                                            <c:set var="menucheck" value="true"/>
                                                        </c:if>

                                                         <input hidden value="${menu.getId()}" name="menuid" />
                                                        <input hidden value="${requestScope.schoolyear.getId()}" name="sltedsy" />
                                                        <input hidden value="${requestScope.grade.getId()}" name="sltedg" />
                                                        <input hidden value="${requestScope.week.getId()}" name="sltedw" />
                                                    </c:if>
                                                </c:forEach>
                                                <c:if test="${menucheck eq 'false'}">
                                                    <div style="display:flex ; justify-content: center">
                                                        <a style="color: red"> (Bữa trống) </a>
                                                    </div>

                                                </c:if>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                        </div>
                    </div>
                </div>
            </form>
                <div class="btn-container">
                <div class="d-flex justify-content-end">

                </div>
                    <div class=" my-3">
                        <c:if test="${requestScope.enable eq true}">
                            <button type="submit" form="process" class="btn btn-success mx-2" style="width: 150px" name="action" value="accept">Chấp nhận</button>
                            <button type="submit" form="process" class="btn btn-danger mx-2" style="width: 150px" name="action" value="deny">Từ chối</button>
                        </c:if>

                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>

</html>