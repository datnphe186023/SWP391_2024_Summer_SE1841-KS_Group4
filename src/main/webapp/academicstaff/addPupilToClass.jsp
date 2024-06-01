<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 5/24/2024
  Time: 6:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Duyệt học sinh vào lớp</title>

</head>
<body>
    <jsp:include page="dashboard_staff.jsp"/>

    <main class="app-content">
        <div class="row justify-content-center">
            <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách học sinh chưa có lớp</span>
        </div>
        <%--End : title    --%>
        <div class="row">
            <%--  Begin : Search item      --%>
            <div class="col-lg-6">
                <form action="addpupiltoclass">
                    <input type="hidden" name="classId" value="${requestScope.classId}" > <%--  This code to help know that we are add to which class in url --%>
                    <div class="search-field">
                        <div class="form-group has-search">
                            <span style="margin-top: 5px" class="fa fa-search form-control-feedback"></span>
                            <input style="border-radius: 30px" type="text" class="form-control" placeholder="Search" name="information">
                        </div>
                    </div>
                </form>
            </div>
            <%--End : Search item    --%>

        </div>

        <div class="row">
            <form action="addpupiltoclass" method="post">
            <input type="hidden" name="classId" value="${requestScope.classId}" >
                <table  class="table table-bordered">

                        <button style="float: right; margin-right: 220px;padding: 10px 20px; margin-bottom: 10px" class="btn btn-save" type="submit">Chọn vào lớp</button>
                        <a style="float: right; margin-right: 30px;padding: 10px 20px; margin-bottom: 10px" class="btn btn-cancel" href="pupilclass?classId=${requestScope.classId}">Quay Lại</a>

                    <tr class="table" >
                        <th>STT</th>
                        <th>Mã học sinh</th>
                        <th>Ảnh</th>
                        <th>Họ và tên</th>
                        <th>Ngày sinh</th>
                        <th>Địa chỉ</th>
                        <th>Chọn</th>
                    </tr>

                    <c:set var="index" value="1"/> <%--  This code to display number of this pupil in table --%>
                    <c:forEach items="${requestScope.listPupil}" var="pupil">
                        <tr >
                            <td>${index}</td>
                            <td>${pupil.id}</td>
                            <td style="width: 20%;"><img src="../images/${pupil.avatar}"
                                                         class="mx-auto d-block"
                                                         style="width:50%"></td>
                            <td>${pupil.lastName} ${pupil.firstName}</td>
                            <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                            <td>${pupil.address}</td>
                            <td style=" vertical-align: middle" >
                                <div class="form-check custom-checkbox">
                                    <input style="cursor: pointer;" class="form-check-input" type="checkbox" value="${pupil.id}" id="checkbox1" name="pupilSelected">
                                </div>
                            </td>
                        </tr>
                        <c:set var="index" value="${index+1}"/>
                    </c:forEach>
                </table>
            </form>

        </div>
    </main>
</body>
</html>
