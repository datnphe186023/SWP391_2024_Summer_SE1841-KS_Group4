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
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
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
                } else if (toastType === 'fail') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
<script>
    function enableWeek() {
    var schoolYearSelect = document.querySelector('select[name="schoolyear"]');
    var weekSelect = document.querySelector('select[name="week"]');

    if (schoolYearSelect.value !== "") {
        weekSelect.disabled = false;
    } else {
        weekSelect.disabled = true;
    }
}

window.onload = function() {
    enableSchoolYear();
    enableWeek();
}
</script>
</head>


<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center"> Báo cáo đánh giá trên lớp </h1>
                <div class="row">
                    <style>
                        option[hidden] {
                            display: none;
                        }
                        .class-form {
                            margin: 0 10px; /* Adjust the margin as needed */
                        }
                        .custom-select {
                            padding: 5px; /* Add padding for better appearance */
                            border: 1px solid #ccc; /* Optional: Add border to match design */
                            border-radius: 4px; /* Optional: Add border radius for rounded corners */
                            min-width: 200px; /* Increase minimum width for readability */
                            max-width: 100%; /* Allow the width to adjust based on content */
                            overflow: hidden; /* Ensure no overflow */
                            text-overflow: ellipsis; /* Handle overflowed text with ellipsis */
                        }
                    </style>

                    <c:set var="sltedw" value="${requestScope.sltedw}"/>
                    <c:set var="sltedsy" value="${requestScope.sltedsy}"/>
                    <form action="viewdailyevaluationreport" method="post">
                        <div style="display: flex; justify-content: space-evenly;">


                            <div class="class-form">
                                <label>Năm học
                                    <select name="schoolyear" onchange="enableWeek(); this.form.submit();" class="custom-select" >
                                        <option value="" hidden>Năm học</option>
                                        <c:forEach items="${requestScope.schoolYearList}" var="sy">
                                            <option ${sltedsy eq sy.getId() ? "selected" : ""}
                                                    value="${sy.getId()}">${sy.getName()}</option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>

                            <div class="class-form">
                                <label>Tuần học
                                    <select name="week" onchange="this.form.submit()" class="custom-select" ${ not empty sltedsy ? '' : 'disabled'}>
                                        <option value="" hidden>Tuần học</option>
                                        <c:forEach items="${requestScope.weekList}" var="w">
                                            <option ${sltedw eq w.getId() ? "selected" : ""}
                                                    value="${w.getId()}">${w.getStartDatetoEndDate()} </option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                        </div>
                    </form>
                </div>

                <c:set value="${requestScope.display}" var="display"/>
                <div class="row">
                    <c:if test="${display =='year'}">
                        <div class="card shadow mb-4">

                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Báo cáo năm học</h6>
                            </div>

                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="" width="100%" cellspacing="0">
                                        <thead>
                                        <tr>
                                            <th>Năm học</th>
                                            <th>Đánh giá trẻ ngoan</th>
                                            <th>Danh hiệu cháu ngoan Bác Hồ</th>

                                        </tr>
                                        </thead>

                                        <tbody>
                                        <c:set value="${requestScope.menuDetailList}" var="menuDetaillist"/>
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

                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>

            </div>

        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>

</html>