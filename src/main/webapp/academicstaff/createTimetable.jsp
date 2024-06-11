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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">
        <style>
            #selectWeek {
                width: 30%;
            }

            #selectYear {
                width: 25%;
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
                width: 100%;
                padding: 6px;
                border-radius: 4px;
                border: 1px solid #d1d3e2;
                font-size: 0.875rem;
                color: #858796;
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
                                <h1><i class="fa fa-calendar"></i> Tạo thời khóa biểu</h1>
                            </div>
                        </div>

                        <!-- Timetable Form -->
                        <form id="combinedForm" action="timetable" method="get">
                            <div class="form-row">
                                <div class="form-group col-md-4" style="padding-left: 0px; width: 50%;">
                                    <label for="selectWeek">Chọn tuần:</label>
                                    <select class="form-control" id="selectWeek" name="weekId" onchange="submitForms()">
                                        <option>Chọn tuần</option>
                                        <c:forEach var="listWeek" items="${requestScope.listWeek}">
                                            <option value="${listWeek.id}" <c:if test="${param.weekId == listWeek.id}">selected</c:if>>${listWeek.id}</option>
                                        </c:forEach>
                                    </select>
                                </div>

                                <div class="form-group col-md-4" >
                                    <label for="selectGrade">Chọn khối:</label>
                                    <select class="form-control" id="selectGrade" name="gradeId" onchange="submitForms()" style="width: 40%">
                                        <option>Chọn khối</option>
                                        <c:forEach var="listGrade" items="${requestScope.listGrade}">
                                            <option value="${listGrade.id}" <c:if test="${param.gradeId == listGrade.id}">selected</c:if>>${listGrade.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                        </form>

                        <form action="timetable" method="post">
                            <div class="form-row">
                                <div class="form-group col-md-4">
                                    <label for="selectClass">Chọn lớp:</label>
                                    <select class="form-control" id="selectClass" name="classId" style="width: 30%;">
                                        <option>Chọn lớp</option>
                                        <c:forEach var="classList" items="${requestScope.classList}">
                                            <option value="${classList.id}">${classList.name}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="form-group col-md-4">
                                    <label for="selectYear">Năm học :</label>
                                    <input class="form-control" name="year" value="${requestScope.newYear.name}" disabled style="width: 40%">
                                </div>
                            </div>

                            <table class="timetable-table table table-bordered text-center">
                                <thead>
                                    <tr class="bg-light-gray">
                                        <th class="text-uppercase">Thời gian</th>
                                        <th class="text-uppercase">Thứ hai</th>
                                        <th class="text-uppercase">Thứ ba</th>
                                        <th class="text-uppercase">Thứ tư</th>
                                        <th class="text-uppercase">Thứ năm</th>
                                        <th class="text-uppercase">Thứ sáu</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="timeslot" items="${requestScope.listTimeslot}">
                                        <tr>
                                            <td class="align-middle">${timeslot.startTime} - ${timeslot.endTime}</td>
                                            <c:forEach var="dayList" items="${requestScope.dayList}">
                                                <td>
                                                    <select class="form-control" name="${timeslot.startTime}_${dayList}">
                                                        <option value="">Chọn môn học</option>
                                                        <c:forEach var="subList" items="${requestScope.subList}">
                                                            <option value="${subList.id}">${subList.name}</option>
                                                        </c:forEach>
                                                    </select>
                                                </td>
                                            </c:forEach>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                            

                            <div class="btn-container">
                                <button type="button" class="btn btn-primary" onclick="addSubjectRow()">Thêm Môn Học Mới</button>
                                <div class="btn-group-right">
                                    <button type="submit" class="btn btn-success" style="width: 100px">Lưu</button>
                                    <button type="reset" class="btn btn-danger" style="width: 100px">Hủy</button>
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

    </body>
</html>
