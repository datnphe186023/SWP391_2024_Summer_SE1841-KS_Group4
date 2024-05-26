
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
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
</head>
<body>
<jsp:include page="dashboard_headteacher.jsp"/>
<%--Begin : title    --%>
<main class="app-content">
    <div class="row justify-content-center">
        <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách học sinh đang chờ phê duyệt</span>
    </div>
    <%--End : title    --%>
    <c:set value="${requestScope.message}" var="message"/>
    <c:if test="${message eq 'Đã duyệt thành công' }">
        <div class="row justify-content-center" style="margin: 30px;">
            <span class="bg-success font-weight-bold rounded-lg" style="padding: 8px;border-radius: 30px;">${message}</span>
        </div>
    </c:if>
    <c:if test="${message eq 'Đã từ chối' }">
        <div class="row justify-content-center" style="margin: 30px;">
            <span class="bg-warning font-weight-bold rounded-lg"style="padding: 8px;border-radius: 30px;">${message}</span>
        </div>
    </c:if>

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
                                                 style="width:50%;"></td>
                    <td>${pupil.lastName} ${pupil.firstName}</td>
                    <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                    <td>${pupil.address}</td>
                    <td style=" vertical-align: middle; padding-left: 10px"  ><a class="accept-button" href="reviewpupil?action=accept&id=${pupil.id}">Chấp nhận</a><br>
                        <a class="decline-button" href="reviewpupil?action=decline&id=${pupil.id}">Từ chối</a>
                    </td>
                </tr>
                <c:set var="index" value="${index+1}"/>
            </c:forEach>
        </table>
    </div>
</main>

</body>
</html>








