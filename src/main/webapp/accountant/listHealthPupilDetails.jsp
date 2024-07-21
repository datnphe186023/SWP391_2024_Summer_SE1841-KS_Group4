<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 5/23/2024
  Time: 3:17 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh Sách Học Sinh</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Báo cáo sức khỏe</h1>

                        <div class="card shadow mb-4">

                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã kiểm tra sức khỏe</th>
                                                <th>Ngày kiểm tra</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:forEach var="pupilHealth" items="${requestScope.listHealthCheckUp}" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td>${pupilHealth.id}</td>
                                                <td>${pupilHealth.checkUpDate}</td>
                                                <td><a href="viewHealthCheckUp?healthId=${pupilHealth.id}" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Chi tiết</a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                        <div class="btn-group-right float-right">
                            <button type="button" class="btn btn-primary" onclick="history.back()"  style="width: 100px">Quay lại</button>
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



