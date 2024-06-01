
<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 5/23/2024
  Time: 8:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Quản lý học sinh</title>

</head>
<body>
<jsp:include page="dashboard_headteacher.jsp"/>
<%--Begin : title    --%>
<main class="app-content">
    <div class="row justify-content-center">
        <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách học sinh đang chờ phê duyệt</span>
    </div>
    <%--End : title    --%>
    <div class="row">
        <div class="col-lg-6">
            <a style="float: right; margin-right: 200px;padding: 10px 20px; margin-bottom: 10px" class="btn btn-cancel" href="listpupil">Quay Lại</a>

        </div>
    </div>
        <div class="row">
        <table  class="table table-bordered">
            <tr class="table" >
                <th>STT</th>
                <th>Mã học sinh</th>
                <th>Ảnh</th>
                <th>Họ và tên</th>
                <th>Ngày sinh</th>
                <th>Địa chỉ</th>
                <th>Hành động</th>
            </tr>

            <c:set var="index" value="1"/> <%--  This code to display number of this pupil in table --%>
            <c:forEach items="${requestScope.listPupil}" var="pupil">
                <tr >
                    <td>${index}</td>
                    <td>${pupil.id}</td>
                    <td style="width: 30%;"><img src="../images/${pupil.avatar}"
                                                 class="mx-auto d-block"
                                                 style="width:50%;" alt=""></td>
                    <td>${pupil.lastName} ${pupil.firstName}</td>
                    <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                    <td>${pupil.address}</td>
                    <td style="vertical-align: middle; padding-left: 10px">
                        <form method="post" action="reviewpupil" style="display: inline;">
                            <input type="hidden" name="action" value="accept">
                            <input type="hidden" name="id" value="${pupil.id}">
                            <button type="submit" class="accept-button" style="margin: auto; margin-bottom: 20px" >Chấp nhận</button>
                        </form>
                        <form method="post" action="reviewpupil" style="display: inline;">
                            <input type="hidden" name="action" value="decline">
                            <input type="hidden" name="id" value="${pupil.id}">
                            <button type="submit" class="decline-button" style="margin: auto"  >Từ chối</button>
                        </form>
                    </td>


                </tr>
                <c:set var="index" value="${index+1}"/>
            </c:forEach>
        </table>
    </div>
</main>
</body>
</html>








