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
    <style >
        #style-span{
            padding: 11px 150px;
            margin-top: 10px;
            border-radius: 20px;
            margin-bottom: 40px;
        }
        table.table-bordered, table.table-bordered th, table.table-bordered td {
            border: 2px solid black;
            text-align: center;
        }
        .detail-button {
            color: #001C41;
            background-color: #FFD43A;
            cursor: pointer;
            border-radius: 20px;
            padding: 5px -2px;
            display: block;
        }

        .detail-button:hover {
            background-color: white;
            color: #001C41;
            border: 1px grey solid;
        }

        .form-select{
            padding: 5px 53px;
        }
        #myForm{
            display: flex;
            justify-content: space-evenly;
            font-weight: bold;
        }
        .add-button{
            margin-right: 30px;
            color: white;
            background-color: #169D53;
            padding: 10px 100px;
            border-radius: 30px;
            float: right;
            font-weight: bold;
            cursor: pointer;
        }
        .add-button:hover{
            background-color: white;
            color: #169D53;
            border: 1px grey solid;
        }

    </style>
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
        <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách học sinh</span>
    </div>
    <%--End : title    --%>
    <div class="row">
        <%--  Begin : Select item      --%>
        <c:set var="schoolYearSelect" value="${requestScope.schoolYearSelect}"/>
          <c:set var="classesSelect" value="${requestScope.classSelect}"/>
        <div class="col-lg-6">
            <form action="listpupil"  id="myForm">
                <div class="class-form">
                    <label >Chọn lớp</label>
                    <select class="form-select"  aria-label="Default select example" onchange="submitForm()" name="classes">
                        <option>Chọn lớp</option>
                        <c:forEach items="${requestScope.listClass}" var="c">
                            <option ${classesSelect eq c.id ? "selected" : ""} value="${c.id}">${c.name}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="year-form">
                    <label >Chọn năm học</label>
                    <select class="form-select"  aria-label="Default select example" onchange="submitForm()" name="schoolYear">
                        <c:forEach items="${requestScope.listSchoolYear}" var="year">
                            <option ${schoolYearSelect eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                        </c:forEach>
                    </select>
                </div>


            </form>
        </div>
        <%--End : Select item    --%>
            <div class="col-lg-6">
                <a class="add-button" href="reviewpupil">ĐANG CHỜ PHÊ DUYỆT (${requestScope.numOfPendingPupils})</a>
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
                    <td style="width: 20%; margin: 50px"><img src="../images/${pupil.avatar}"
                                                 class="mx-auto d-block"
                                                 style="width:50%;"></td>
                    <td>${pupil.lastName} ${pupil.firstName}</td>
                    <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                    <td>${pupil.address}</td>
                    <td style=" vertical-align: middle; padding-left: 10px"  ><a class="detail-button" href="pupilprofile?id=${pupil.id}">Chi tiết</a><br>
                        <a class="detail-button" href="#">Báo cáo</a>
                    </td>
                </tr>
                <c:set var="index" value="${index+1}"/>
            </c:forEach>
        </table>
    </div>
</main>

</body>
</html>



