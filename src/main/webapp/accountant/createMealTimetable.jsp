<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
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
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
    <style>
        #selectWeek {
            width: 80%;
        }

        #selectYear {
            width: 70%;
        }

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

        .timetable-table {
            width: 100%;
            border-collapse: collapse;
        }

        .timetable-table thead th {
            background-color: #f8f9fc;
            color: #5a5c69;
            font-weight: 700;
            padding: 12px;
            text-align: center;
            border: 1px solid #e3e6f0;
        }

        .timetable-table tbody td {
            padding: 8px;
            text-align: center;
            border: 1px solid #e3e6f0;
        }

        .timetable-table tbody tr:nth-child(even) {
            background-color: #f8f9fc;
        }

        .timetable-table select {
            width:100%;
            padding: 6px;
            border-radius: 4px;
            border: 1px solid #d1d3e2;
            font-size: 0.875rem;
            color: #858796;
        }

             /* Hide the placeholder in the dropdown options */
         option[hidden] {
             display: none;
         }

    </style>

</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>

    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>

            <!-- Begin Page Content -->
            <div class="container-fluid" style="width: 90%">

                <div class="app-title">
                    <div>
                        <h1><i class="fa fa-cheese"></i> Tạo thức đơn mới</h1>
                    </div>
                </div>

                <!-- Timetable Form -->

                <form id="combinedForm" action="createmealtimetable" method="get">

                    <div class="form-row">

                        <div class="form-group col-md-3" style="padding-left: 0px; width: auto;">
                            <label for="selectWeek">Chọn tuần:</label>
                            <select class="form-control" id="selectWeek" name="weekId" onchange="submitForms()" required style="width: 90%">
                                <option value ="none">Chọn tuần</option>
                                <c:forEach var="listWeek" items="${requestScope.listWeek}" >
                                    <option value="${listWeek.id}" <c:if test="${param.weekId == listWeek.id}">selected</c:if>>
                                        <fmt:formatDate value="${listWeek.startDate}" pattern="dd/MM/yyyy" /> -
                                        <fmt:formatDate value="${listWeek.endDate}" pattern="dd/MM/yyyy" />
                                    </option>
                                </c:forEach>

                            </select>
                            <c:if test="${not empty requestScope.dateWeek}">
                                <div style="margin-top: 20px;">
                                    <p>Áp dụng từ ngày: <fmt:formatDate value="${requestScope.dateWeek.startDate}" pattern="dd/MM/yyyy" /></p>
                                    <p>Đến ngày: <fmt:formatDate value="${requestScope.dateWeek.endDate}" pattern="dd/MM/yyyy" /></p>
                                </div>
                            </c:if>

                        </div>

                        <div class="form-group col-md-2" >
                            <label for="selectGrade">Chọn khối:</label>
                            <select class="form-control" id="selectGrade" name="gradeId" onchange="submitForms()" style="width: auto" required>
                                <option value="none">Chọn khối</option>
                                <c:forEach var="listGrade" items="${requestScope.listGrade}">
                                    <option value="${listGrade.id}" <c:if test="${param.gradeId == listGrade.id}">selected</c:if>>${listGrade.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>


                <form action="createmealtimetable?action=create-foodmenu" method="POST">
                    <div class="form-row">

                        <div class="form-group col-md-2">
                            <label>Năm học :</label>

                            <input class="form-control" name="year" value="${requestScope.newYear.getName()}" disabled  style="width: 70%">
                        </div>
                        <c:if test="${requestScope.enable == 'false'}">
                            <div class="form-group col-md-2">
                                <label>Trạng thái :</label>
                                <c:if test="${requestScope.status == 'đang chờ xử lý'}">
                                <input class="form-control" name="status" value="${requestScope.status}" disabled  style="width:auto; color: #4c67ff  ">
                                 </c:if>
                                <c:if test="${requestScope.status == 'đã được duyệt'}">
                                    <input class="form-control" name="status" value="${requestScope.status}" disabled  style="width:auto; color: #5bff36">
                                </c:if>
                                <c:if test="${requestScope.status == 'năm học không tồn tại !'}">
                                    <input class="form-control" name="status" value="${requestScope.status}" disabled  style="width: auto; color: #ff1425">
                                </c:if>
                            </div>
                        </c:if>
                        <input class="form-control" hidden name="gradeid" value="${requestScope.selectedGradeId}">
                        <input class="form-control" hidden name="weekId" value="${requestScope.weekId}">

                    </div>
                    <c:if test="${requestScope.enable == 'true'}">
                        <table class="timetable-table table table-bordered text-center">
                            <thead>
                            <tr class="bg-light-gray">
                                <th class="text-uppercase">Thời gian</th>
                                <th class="text-uppercase">Thứ hai</th>
                                <th class="text-uppercase">Thứ ba</th>
                                <th class="text-uppercase">Thứ tư</th>
                                <th class="text-uppercase">Thứ năm</th>
                                <th class="text-uppercase">Thứ sáu</th>
                                <th class="text-uppercase">Thứ bảy</th>
                                <th class="text-uppercase">Chủ nhật</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${not empty requestScope.dayList}">
                                <c:forEach var="timeslot" items="${requestScope.listTimeslot}">
                                    <tr>
                                        <td class="align-middle">${timeslot.startTime} - ${timeslot.endTime}</td>
                                        <c:forEach var="day" items="${requestScope.dayList}">
                                            <td>
                                                <select class="form-control" name="timeslotId_${day.id}_${timeslot.id}" required >
                                                    <option value="" hidden>Chọn suất ăn</option>
                                                    <c:forEach var="foodMenu" items="${requestScope.foodMenuList}">

                                                        <option value="${foodMenu.id}" name="FoodmenuId_${day.id}_${timeslot.id}_${foodMenu.id}">${foodMenu.getFoodDetails()}</option>

                                                    </c:forEach>
                                                </select>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty requestScope.dayList }">
                                <c:forEach var="timeslot" items="${requestScope.listTimeslot}">
                                    <tr>
                                        <td class="align-middle">${timeslot.startTime} - ${timeslot.endTime}</td>
                                        <c:forEach var="day" items="${['mon', 'tue', 'wed', 'thu', 'fri' , 'sta', 'sun']}">
                                            <td>
                                                <select class="form-control" name="${day}">
                                                    <option value="">Chọn suất ăn</option>
                                                </select>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>
                    </c:if>
                    <c:if test="${requestScope.enable == 'false'}">
                        <table class="timetable-table table table-bordered text-center"  readonly>
                            <thead>
                            <tr class="bg-light-gray">
                                <th class="text-uppercase">Thời gian</th>
                                <th class="text-uppercase">Thứ hai</th>
                                <th class="text-uppercase">Thứ ba</th>
                                <th class="text-uppercase">Thứ tư</th>
                                <th class="text-uppercase">Thứ năm</th>
                                <th class="text-uppercase">Thứ sáu</th>
                                <th class="text-uppercase">Thứ bảy</th>
                                <th class="text-uppercase">Chủ nhật</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:if test="${not empty requestScope.dayList}">
                                <c:forEach var="timeslot" items="${requestScope.listTimeslot}">
                                    <tr>
                                        <td class="align-middle">${timeslot.startTime} - ${timeslot.endTime}</td>
                                        <c:forEach var="day" items="${requestScope.dayList}">
                                            <td style="text-align: left;">
                                              <!--  <select class="form-control" name="timeslotId_${day.id}_${timeslot.id}" disabled > -->

                                                    <c:forEach var="menuDetail" items="${requestScope.menuDetails}">
                                                        <c:if test="${menuDetail.getTimeslot().getId() == timeslot.id && menuDetail.getDay().getId()  == day.id}">
                                                            <!--    <option value="${menuDetail.getFoodMenu().getId()}" name="FoodmenuId_${day.id}_${timeslot.id}_${menuDetail.getFoodMenu().getId()}" selected> -->
                                                                    ${menuDetail.getFoodMenu().getFoodDetails()}
                                                            <!-- </option>-->
                                                        </c:if>
                                                    </c:forEach>
                                             <!--   </select>-->
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            <c:if test="${empty requestScope.dayList }">
                                <c:forEach var="timeslot" items="${requestScope.listTimeslot}">
                                    <tr>
                                        <td class="align-middle">${timeslot.startTime} - ${timeslot.endTime}</td>
                                        <c:forEach var="day" items="${['mon', 'tue', 'wed', 'thu', 'fri' , 'sta', 'sun']}">
                                            <td>
                                                <select class="form-control" name="${day}">
                                                    <option value="">Chọn suất ăn</option>
                                                </select>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </c:if>
                            </tbody>
                        </table>

                </c:if>
                    <div class="btn-container">
                        <div class="d-flex justify-content-end">

                        </div>
                        <div class="btn-group-right">
                    <c:if test="${requestScope.enable == 'true' && requestScope.weekId != 'none' && requestScope.selectedGradeId != 'none'  && requestScope.weekId != null && requestScope.selectedGradeId != null}">
                            <button type="submit" class="btn btn-success" style="width: 100px">Lưu</button>
                            <button type="reset" class="btn btn-danger" style="width: 100px">Hủy</button>
                    </c:if>
                            
                        </div>
                    </div>
                </form>


            </div>
            <!-- /.container-fluid -->
        </div>
        <!-- End of Main Content -->

        <jsp:include page="../footer.jsp"/>
    </div>
    <!-- End of Content Wrapper -->

</div>
<!-- End of Page Wrapper -->
<script>
    function submitForms() {
        document.getElementById("combinedForm").submit();
    }
</script>
<script>
    const input = document.querySelector('input[name="description"]');
    const charCount = document.getElementById('charCount');
    const warning = document.getElementById('warning');

    input.addEventListener('input', () => {
        const remaining = 8 - input.value.length;
        charCount.textContent = `${remaining} characters remaining`;

        if (remaining <= 0) {
            warning.style.display = 'block';
        } else {
            warning.style.display = 'none';
        }
    });
</script>
</body>
</html>
