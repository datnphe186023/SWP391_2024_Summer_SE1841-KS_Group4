<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 28-Jun-24
  Time: 7:44 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Điểm Danh</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Điểm Danh Hôm Nay</h1>

                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh sách học sinh</h6>
                        <h6 class="m-0 font-weight-bold text-primary">Lớp : <a style="margin-right: 60px" >${requestScope.className == null ?"Ngày hôm nay không có lớp":requestScope.className}</a></h6>
                    </div>
                    <div class="card-body">
                        <form action="takeattendance" method="post">
                            <div class="table-responsive">
                                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                    <thead>
                                    <tr>
                                        <th>STT</th>
                                        <th>Mã học sinh</th>
                                        <th>Ảnh</th>
                                        <th>Họ và tên</th>
                                        <th>Có mặt</th>
                                        <th>Vắng</th>
                                        <th>Ghi chú</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="pupil" items="${requestScope.pupils}" varStatus="status">
                                        <tr>
                                            <th scope="row">${status.index + 1}</th>
                                            <td>${pupil.id}</td>
                                            <td style="width: 20%;">
                                                <img src="../images/${pupil.avatar}"
                                                     class="mx-auto d-block"
                                                     style="width:100px; height:100px; object-fit: cover;">
                                            </td>
                                            <td>${pupil.lastName} ${pupil.firstName}</td>
                                            <td><input type="radio" name="attendance${status.index}" value="present"></td>
                                            <td><input type="radio" name="attendance${status.index}" value="absent" checked></td>
                                            <td><textarea name="note${status.index}" class="form-control" rows="1"></textarea></td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                            <div class="form-group float-right">
                                <button type="submit" class="btn btn-success" style="width: 100px">Lưu</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>
</html>
