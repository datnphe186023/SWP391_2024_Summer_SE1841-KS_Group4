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

            var formIdToAccept;

            function setAction(action) {
                document.getElementById('actionField').value = action;
            }

            function confirmAccept(formId, action, msg) {
                formIdToAccept = formId;
                setAction(action);
                document.getElementById('confirmMessageTitle').innerText = msg;
                $('#confirmMessage').modal('show');
            }

            $(document).ready(function () {
                $('#confirmMessageButton').click(function () {
                    document.getElementById(formIdToAccept).submit();
                });
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

            .btn-group-left {
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
            <jsp:include page="navbar.jsp" />

            <!-- Content Wrapper -->
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp" />

                    <!-- Begin Page Content -->
                    <div class="container-fluid" style="width: 90%">

                        <div class="app-title">
                            <div>
                                <h3><i class="fa fa-calendar"></i> Chi tiết thời khóa biểu của lớp ${requestScope.aClass.name}</h3></br>
                                <h2 class="row justify-content-center">
                                    Thời khóa biểu
                                </h2>
                                </br>
                                <h5 class="row justify-content-center">
                                    Thời khóa biểu áp dụng từ ngày
                                    <fmt:formatDate value="${requestScope.week.startDate}" pattern="dd/MM/yyyy" />
                                    đến ngày
                                    <fmt:formatDate value="${requestScope.week.endDate}" pattern="dd/MM/yyyy" />
                                </h5>
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
                                <c:forEach var="timeslot" items="${requestScope.timeslotList}">
                                    <tr>
                                        <td>${timeslot.startTime} - ${timeslot.endTime}</td>
                                        <c:forEach var="day" items="${requestScope.dayList}">
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty requestScope.timetable}">
                                                        <c:forEach var="timetable" items="${requestScope.timetable}">
                                                            <c:if test="${timetable.timeslot.id eq timeslot.id && timetable.day.id eq day.id}">
                                                                ${timetable.subject.name}
                                                            </c:if>
                                                        </c:forEach>
                                                    </c:when>
                                                    <c:otherwise>
                                                        -
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>
                            </tbody>

                        </table>
                        
                        <form action="review-detail-timetable" method="post" id="reviewForm">
                            <div class="btn-container" style="display: flex; justify-content: center;">
                                <div style="width: 33%">
                                    <textarea class="form-control" rows="4" name="note" placeholder="Ghi chú" style="width: 100%;"></textarea><br>
                                    <input type="hidden" name="action" id="actionField">
                                    <div class="btn-group-left" style="display: flex; justify-content: space-between; margin-top: 10px;">
                                        <button type="button" class="btn btn-success" style="width: 100px;" onclick="confirmAccept('reviewForm', 'approve', 'Bạn có chắc chắn muốn duyệt thời khóa biểu này?')">Duyệt</button>
                                        <button type="button" class="btn btn-danger" style="width: 100px;" onclick="confirmAccept('reviewForm', 'reject', 'Bạn có chắc chắn muốn từ chối thời khóa biểu này?')">Từ chối</button>
                                    </div>
                                </div>
                                <div style="width: 33%">
                                    
                                </div>
                                <div style="width: 33%">
                                    <div class="btn-group-right float-right">
                                        <button type="button" class="btn btn-primary" onclick="history.back()"  style="width: 100px;margin-top: 140px;">Quay lại</button>
                                    </div>
                                </div>
                            </div>

                        </form>

                        <%-- Begin confirmMessage modal --%>
                        <div class="modal fade" id="confirmMessage" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title">Xác nhận thao tác</h5>
                                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                            <span aria-hidden="true">&times;</span>
                                        </button>
                                    </div>
                                    <div class="modal-body" id="confirmMessageTitle">
                                        <!-- Dynamic message will be inserted here -->
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                        <button type="button" class="btn btn-primary" id="confirmMessageButton">Xác Nhận</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <%-- End confirmMessage modal --%>

                    </div>
                    <!-- /.container-fluid -->

                    <jsp:include page="../footer.jsp" />
                </div>
                <!-- End of Main Content -->

            </div>
            <!-- End of Content Wrapper -->

        </div>
        <!-- End of Page Wrapper -->
    </body>

</html>
