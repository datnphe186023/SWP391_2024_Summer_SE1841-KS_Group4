<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <link rel="shortcut icon" type="image/x-icon" href="../image/logo.png" />
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">

        <title>Trường Mầm Non BoNo</title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

        <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
              rel="stylesheet">
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <c:set value="${requestScope.sltedsy}" var="sltedsy"/>
                        <h1 class="h3 mb-4 text-gray-800 text-center">Tổng kết khen thưởng học sinh ${sltedsy.getName()}</h1>
                        <div class="row">
                            <div class="col-md-4">
                                <form action="schoolyearsummerize" method="post">
                                    <c:set value="${requestScope.numberOfGoodPupil}" var="numberOfGoodPupil"/>
                                    <c:set value="${requestScope.numberOfPupilInSchoolYear}" var="numberOfPupilInSchoolYear"/>
                                    <div class="class-form">
                                        <label> Năm học
                                            <select name="year" onchange="this.form.submit()" class="custom-select">
                                                <option value="" hidden>Năm học</option>
                                                <c:forEach items="${requestScope.schoolYearList}" var="sy">
                                                    <option ${sltedsy.getId() eq sy.getId() ? "selected" : ""}
                                                        value="${sy.getId()}">${sy.getName()}</option>
                                                </c:forEach>
                                            </select>
                                        </label>
                                    </div>
                                </form>
                            </div>
                        </div>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Số lượng học sinh: ${numberOfPupilInSchoolYear} </h6>
                                <h6 class="m-0 font-weight-bold text-primary">Số lượng học sinh đạt danh hiệu Cháu Ngoan Bác Hồ : ${numberOfGoodPupil}</h6>
                                <h6 class="m-0 font-weight-bold text-primary">Số lượng học sinh không đạt danh hiệu Cháu Ngoan Bác Hồ:${numberOfPupilInSchoolYear - numberOfGoodPupil} </h6>
                            </div>

                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã Học Sinh</th>
                                                <th>Tên</th>
                                                <th>Giới Tính</th>
                                                <th>Ngày sinh</th>
                                                <th>Số phiếu Bé Ngoan</th>
                                                <th>Cháu ngoan Bác Hồ</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:forEach items="${requestScope.pupils}" var="p" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
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
                                                    ${p.getYearEvaluation(sltedsy.getId())}
                                                </td>
                                                <td>
                                                    ${p.Evaluate(sltedsy.getId())}
                                                </td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                                <form action="exportExcel" method="post">
                                    <button type="submit" class="btn btn-success">Export to Excel</button>
                                </form>
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
