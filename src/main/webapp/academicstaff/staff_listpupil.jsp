
<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 5/22/2024
  Time: 9:23 PM
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
    <jsp:include page="dashboard_staff.jsp"/>
<%--Begin : title    --%>
    <main class="app-content">
        <div class="row justify-content-center">
            <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách học sinh</span>
        </div>
<%--End : title    --%>
        <div class="row">
            <%--  Begin : Search item      --%>
            <div class="col-lg-6">
                <form action="addpupiltoclass" method="post">
                    <div class="search-field">
                        <div class="form-group has-search">
                            <span style="margin-top: 5px" class="fa fa-search form-control-feedback"></span>
                            <input style="border-radius: 30px" type="text" class="form-control" placeholder="Search" name="information">
                        </div>
                    </div>
                </form>
            </div>
            <%--End : Search item    --%>
                <div class="col-lg-6">
                    <a class="add-button" href="createpupil">Thêm học sinh mới</a>
                </div>
        </div>

            <div class="row">
                <table  class="table table-bordered">
                    <tr class="table" >
                        <th>STT</th>
                        <th>Mã học sinh</th>
                        <th>Họ và tên</th>
                        <th>Ngày sinh</th>
                        <th>Hành động</th>
                    </tr>

                    <c:set var="index" value="1"/> <%--  This code to display number of this pupil in table --%>
                    <c:forEach items="${requestScope.listPupil}" var="i">
                        <tr >
                            <td>${index}</td>
                            <td>${i.id}</td>
                            <td>${i.lastName} ${i.firstName}</td>
                            <td><fmt:formatDate value="${i.birthday}" pattern="dd/MM/yyyy" /></td>
                            <td style="padding: 0px;"><a style="margin-top: 15px;display: inline-block;padding: 0px 20px;" class="detail-button" href="pupilprofile?id=${i.id}">Chi tiết</a></td>
                        </tr>
                        <c:set var="index" value="${index+1}"/>
                    </c:forEach>
                </table>
            </div>
    </main>

</body>
</html>


