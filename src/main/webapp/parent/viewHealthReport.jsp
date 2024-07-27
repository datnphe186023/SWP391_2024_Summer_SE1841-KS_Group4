<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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

</head>

<body id="page-top">
<c:set value="${requestScope.sltedsy}" var="sltedsy"/>
<c:set value="${requestScope.sltedtime}" var="sltedtime"/>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Báo cáo sức khỏe</h1>
                <div class="row">
                    <div class="col-md-4" >
                    <form action="viewhealthreport" method="post">
                        <div class="class-form" >
                            <label>Năm học
                                <select name="schoolyear" onchange="this.form.submit()" class="custom-select"  >
                                    <option value="" hidden>Năm học</option>
                                    <c:forEach items="${requestScope.schoolYearList}" var="sy">
                                        <option ${sltedsy eq sy.getId() ? "selected" : ""}
                                                value="${sy.getId()}">${sy.getName()}</option>
                                    </c:forEach>
                                </select>
                            </label>
                        </div>
                        <c:if test="${requestScope.selectedReport == 'detail'}">
                            <c:if test="${requestScope.healthCheckUpList.size() > 0 }">
                            <div class="class-form" >
                                <label> Thời gian kiểm tra
                                    <select name="time" onchange="this.form.submit()" class="custom-select" style="width:74%;" >
                                        <option value="" hidden> Ngày kiểm tra </option>
                                        <c:forEach items="${requestScope.healthCheckUpList}" var="check">
                                            <option ${sltedtime eq check.getCheckUpDate() ? "selected" : ""}
                                                    value="${check.getCheckUpDate()}">${check.getCheckUpDate()} </option>
                                        </c:forEach>
                                    </select>
                                </label>
                            </div>
                            </c:if>
                            <c:if test="${requestScope.healthCheckUpList.size() == 0 }">
                                <div class="class-form" >
                                    <label> Thời gian kiểm tra</label>
                                    <br>

                                       <input disabled name="time" value="Không có dữ liệu" class="form-control" style="width: 162px">

                                </div>
                            </c:if>
                        </c:if>
                        <style>
                            .fake-select {
                                display: inline-block;
                                width: 245px;
                                height: 70%;
                                padding: .375rem 1.75rem .375rem .75rem;
                                font-size: 1rem;
                                font-weight: 400;
                                line-height: 1.5;
                                color: #6e707e;
                                vertical-align: middle;
                                background-color: white;
                                border: 1px solid #d1d3e2;
                                border-radius: .35rem;
                                -webkit-appearance: none;
                                -moz-appearance: none;
                                appearance: none;
                            }
                        </style>

                    <div class="class-form" >
                        <label>Báo cáo</label><br>
                        <div class="fake-select" >
                            <label><input type="radio" name="report" value="overall" onclick="this.form.submit()" <%= request.getAttribute("selectedReport") != null && request.getAttribute("selectedReport").equals("overall") ? "checked" : "" %>/> Tổng Quan </label>
                            <label><input type="radio" name="report" value="detail" onclick="this.form.submit()" <%= request.getAttribute("selectedReport") != null && request.getAttribute("selectedReport").equals("detail") ? "checked" : "" %>/> Chi tiết </label>
                        </div>
                    </div>


                    </form>
                    </div>

                    <c:set var="c" value="${requestScope.healthCheckUpList}"/>
                    <c:if test="${requestScope.selectedReport == 'overall'}">
                    <div class="col-md-8">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        Biểu đồ cân nặng theo tháng
                                    </div>
                                    <div class="card-body">
                                        <canvas id="MonthlyWeightChart" style="width: 100%; height: 400px;"></canvas>
                                    </div>
                                </div>



                                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                <script type="text/javascript">
                                    var labels = [];
                                    var weightData = [];

                                    <c:forEach var="checkUp" items="${requestScope.healthCheckUpList}">
                                    var checkUpDate = new Date("${checkUp.getCheckUpDate()}");
                                    var formattedDate = ('0' + checkUpDate.getDate()).slice(-2) + '/' + ('0' + (checkUpDate.getMonth() + 1)).slice(-2)+ '/' + checkUpDate.getFullYear();
                                    labels.push(formattedDate);
                                    weightData.push(${checkUp.getWeight()});
                                    </c:forEach>

                                    var data = {
                                        labels: labels,
                                        datasets: [{
                                            label: "Cân nặng (kg)",
                                            backgroundColor: "rgba(54, 162, 235, 0.2)",
                                            borderColor: "rgba(54, 162, 235, 1)",
                                            borderWidth: 1,
                                            data: weightData
                                        }]
                                    };

                                    var ctx = document.getElementById("MonthlyWeightChart").getContext("2d");
                                    var myChart = new Chart(ctx, {
                                        type: 'line',
                                        data: data,
                                        options: {
                                            scales: {
                                                yAxes: [{
                                                    ticks: {
                                                        beginAtZero: true
                                                    }
                                                }]
                                            }
                                        }
                                    });
                                </script>
                            </div>
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        Biểu đồ chiều cao theo tháng
                                    </div>
                                    <div class="card-body">
                                        <canvas id="MonthlyHeightChart" style="width: 100%; height: 400px;"></canvas>
                                    </div>
                                </div>



                                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                <script type="text/javascript">
                                    var labels = [];
                                    var heightData = [];

                                    <c:forEach var="checkUp" items="${healthCheckUpList}">
                                    var checkUpDate = new Date("${checkUp.checkUpDate}");
                                    var formattedDate = ('0' + checkUpDate.getDate()).slice(-2) + '/' + ('0' + (checkUpDate.getMonth() + 1)).slice(-2)+ '/' + checkUpDate.getFullYear();
                                    labels.push(formattedDate);
                                    heightData.push(${checkUp.height});
                                    </c:forEach>

                                    var data = {
                                        labels: labels,
                                        datasets: [{
                                            label: "Chiều cao (cm)",
                                            backgroundColor: "rgba(75, 192, 192, 0.2)",
                                            borderColor: "rgba(75, 192, 192, 1)",
                                            borderWidth: 1,
                                            data: heightData
                                        }]
                                    };

                                    var ctx = document.getElementById("MonthlyHeightChart").getContext("2d");
                                    var myChart = new Chart(ctx, {
                                        type: 'line',
                                        data: data,
                                        options: {
                                            scales: {
                                                yAxes: [{
                                                    ticks: {
                                                        beginAtZero: true
                                                    }
                                                }]
                                            }
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        Biểu đồ thị lực theo tháng
                                    </div>
                                    <div class="card-body">
                                        <canvas id="MonthlyEyesChart" style="width: 100%; height: 400px;"></canvas>
                                    </div>
                                </div>
                                <label></label>


                                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                                <script type="text/javascript">
                                    var labels = [];
                                    var eyesData = [];

                                    <c:forEach var="checkUp" items="${healthCheckUpList}">
                                    var checkUpDate = new Date("${checkUp.checkUpDate}");
                                    var formattedDate = ('0' + checkUpDate.getDate()).slice(-2) + '/' + ('0' + (checkUpDate.getMonth() + 1)).slice(-2)+ '/' + checkUpDate.getFullYear();
                                    labels.push(formattedDate);
                                    eyesData.push(${checkUp.getEyetoGraphData()});
                                    </c:forEach>

                                    var data = {
                                        labels: labels,
                                        datasets: [{
                                            label: "Thị lực (Score)",
                                            backgroundColor: "rgba(255, 159, 64, 0.2)",
                                            borderColor: "rgba(255, 159, 64, 1)",
                                            borderWidth: 1,
                                            data: eyesData
                                        }]
                                    };

                                    var ctx = document.getElementById("MonthlyEyesChart").getContext("2d");
                                    var myChart = new Chart(ctx, {
                                        type: 'line',
                                        data: data,
                                        options: {
                                            scales: {
                                                yAxes: [{
                                                    ticks: {
                                                        beginAtZero: true
                                                    }
                                                }]
                                            }
                                        }
                                    });
                                </script>
                            </div>
                            <div class="col-md-6">
                                <div class="card">
                                    <div class="card-header">
                                        Biểu đồ huyết áp theo tháng
                                    </div>
                                    <div class="card-body">
                                        <canvas id="MonthlyBloodPressureChart" style="width: 100%; height: 400px;"></canvas>
                                    </div>
                                </div>



                                <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

                                <script type="text/javascript">
                                    var labels = [];
                                    var systolicData = [];
                                    var diastolicData = [];

                                    <c:forEach var="checkUp" items="${healthCheckUpList}">
                                    var checkUpDate = new Date("${checkUp.checkUpDate}");
                                    var formattedDate = ('0' + checkUpDate.getDate()).slice(-2) + '/' + ('0' + (checkUpDate.getMonth() + 1)).slice(-2)+ '/' + checkUpDate.getFullYear();
                                    labels.push(formattedDate);

                                    var bloodPressure = "${checkUp.bloodPressure}";
                                    var parts = bloodPressure.split('/');
                                    systolicData.push(parseInt(parts[0]));
                                    diastolicData.push(parseInt(parts[1]));
                                    </c:forEach>

                                    var data = {
                                        labels: labels,
                                        datasets: [
                                            {
                                                label: "Huyết áp tối đa (mmHg)",
                                                backgroundColor: "rgba(255, 99, 132, 0.2)",
                                                borderColor: "rgba(255, 99, 132, 1)",
                                                borderWidth: 1,
                                                data: systolicData
                                            },
                                            {
                                                label: "Huyết áp tối thiểu (mmHg)",
                                                backgroundColor: "rgba(54, 162, 235, 0.2)",
                                                borderColor: "rgba(54, 162, 235, 1)",
                                                borderWidth: 1,
                                                data: diastolicData
                                            }
                                        ]
                                    };

                                    var ctx = document.getElementById("MonthlyBloodPressureChart").getContext("2d");
                                    var myChart = new Chart(ctx, {
                                        type: 'line',
                                        data: data,
                                        options: {
                                            scales: {
                                                yAxes: [{
                                                    ticks: {
                                                        beginAtZero: true
                                                    }
                                                }]
                                            }
                                        }
                                    });
                                </script>
                            </div>
                        </div>
                    </div>
                    </c:if>

                    <c:if test="${requestScope.selectedReport == 'detail'}">

                        <div class="col-md-8">
                        <div class="row">

                            <div class="col-md-3">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Chiều cao</h5>
                                        <p class="card-text">${requestScope.healthCheckUp.getHeight()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Cân Nặng</h5>
                                        <p class="card-text">${requestScope.healthCheckUp.getWeight()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                            </div>
                            </div>
                        <div class="row">

                            <div class="col-md-3">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Đánh giá thể trạng</h5>
                                        <p class="card-text">${requestScope.healthCheckUp.getAverageDevelopmentStage()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Huyết áp</h5>
                                        <p class="card-text">${requestScope.healthCheckUp.getBloodPressure()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                            </div>
                        </div>
                        <div class="row">

                            <div class="col-md-3">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Tình trạnh răng </h5>
                                        <p class="card-text">${requestScope.healthCheckUp.getTeeth()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Thị lực</h5>
                                        <p class="card-text">${requestScope.healthCheckUp.getEyes()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                            </div>
                        </div>
                        <div class="row">

                            <div class="col-md-6">
                                <div class="card shadow mb-4">
                                    <div class="card-body">
                                        <h5 class="card-title">Ghi chú của bác sĩ</h5>
                                        <p class="card-text">${requestScope.healthCheckUp.getNotes()}</p>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
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
</body>

</html>