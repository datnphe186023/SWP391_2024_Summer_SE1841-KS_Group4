<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 5/24/2024
  Time: 10:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Quản lý học sinh</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function() {
            var toastMessage = '<%= session.getAttribute("toastMessage") %>';
            var toastType = '<%= session.getAttribute("toastType") %>';
            <%
                session.removeAttribute("toastMessage");
                session.removeAttribute("toastType");
            %>
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>
    <script>
        function submitForm() {
            document.getElementById("myForm").submit();
        }
    </script>
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
                <form action="pupilclass">
                    <input type="hidden" value="${requestScope.classId}" name="classId">
                    <div class="search-field">
                        <div class="form-group has-search">
                            <span style="margin-top: 5px" class="fa fa-search form-control-feedback"></span>
                            <input style="border-radius: 30px" type="text" class="form-control" placeholder="Search" name="information">
                        </div>
                    </div>
                </form>
            </div>
        <div class="col-lg-6">
            <a class="add-button" href="addpupiltoclass?classId=${requestScope.classId}">Thêm học sinh vào lớp(${requestScope.numberOfPupilsPending})</a>
        </div>
        <%--End : Search item    --%>
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
                    <td style="width: 20%;"><img src="../images/${pupil.avatar}"
                                                 class="mx-auto d-block"
                                                 style="width:50%"></td>
                    <td>${pupil.lastName} ${pupil.firstName}</td>
                    <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                    <td>${pupil.address}</td>
                    <td style=" vertical-align: middle" ><a class="detail-button" href="pupilprofile?id=${pupil.id}">Chi tiết</a></td>
                </tr>
                <c:set var="index" value="${index+1}"/>
            </c:forEach>
        </table>
    </div>
</main>

</body>
</html>
