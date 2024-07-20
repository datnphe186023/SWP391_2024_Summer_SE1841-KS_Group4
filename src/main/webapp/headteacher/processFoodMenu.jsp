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
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
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
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh Sách Suất Ăn Chờ Phê Duyệt </h6>
                    </div>
                    <style>

                        .btn-container {
                            display: flex;
                            justify-content: space-evenly;
                            align-items: center;

                        }

                        .btn-group-right {
                            display: flex;
                            justify-content:space-evenly;
                        }

                        </style>

                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Chi tiết suất ăn</th>
                                    <th>Trạng thái </th>
                                    <th>Thao tác</th>
                                </tr>
                                </thead>
                                <tbody>


                                <c:forEach items="${foodMenuList}" var="fml" varStatus="status">
                                    <tr>
                                        <th scope="row" >${status.index + 1}</th>
                                        <td >${fml.getFoodDetails()}</td>

                                            <c:if test="${fml.getStatus() eq 'đã được duyệt'}">
                                        <td style="color: #0f6848">
                                            <span class="badge badge-success">${fml.getStatus()}</span>
                                        </td>
                                            </c:if>
                                        <c:if test="${fml.getStatus() eq 'đang chờ xử lý'}">
                                            <td style="color: #4c67ff">
                                                <span class="badge badge-warning">${fml.getStatus()}</span>
                                            </td>
                                        </c:if>
                                        <c:if test="${fml.getStatus() eq 'không được duyệt'}">
                                            <td style="color: #ff3a28">
                                                <span class="badge badge-danger">${fml.getStatus()}</span>
                                            </td>
                                        </c:if>
                                        <td>

                                            <c:if test="${fml.getStatus() eq 'đang chờ xử lý'}">
                                                <form action="processfoodmenu" method="post">
                                                <input hidden name="foodid" value="${fml.getId()}"/>

                                                    <div class="d-flex flex-column align-items-center">

                                                            <button type="submit" class="btn btn-sm btn-success shadow-sm btn-custom-width" style="width: 100%" name="action" value="accept">Chấp nhận</button>
                                                            <button type="submit"  class="btn btn-sm btn-danger shadow-sm btn-custom-width" style="width: 100%" name="action" value="deny">Từ chối</button>

                                                        </div>


                                                </form>
                                        </c:if>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
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