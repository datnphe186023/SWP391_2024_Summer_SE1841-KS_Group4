
<%--
  Created by IntelliJ IDEA.
  stoled from: Anh Quan 
  By: Thắng Đức 
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
            margin-bottom: 15px;
        }
        table.table-bordered, table.table-bordered th, table.table-bordered td {
            border: 2px solid black;
            text-align: center;
        }
        .accept-button , decltine-button{
            color: #001C41;
            background-color: #4CB5FB;
            cursor: pointer;
            border-radius: 20px;
            padding: 5px 0px;
            display: block;
        }
        .decline-button{
            color: #001C41;
            background-color: red;
            cursor: pointer;
            border-radius: 20px;
            padding: 5px 0px;
            display: block;
        }
        .accept-button:hover{
            background-color: white;
            border: 1px grey solid;
        }
        .decline-button:hover{
            background-color: white;
            border: 1px grey solid;
        }

        #myForm{
            display: flex;
            justify-content: space-evenly;
            font-weight: bold;
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
        <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách học sinh đang chờ phê duyệt</span>
    </div>
    <%--End : title    --%>
   

    <div class="row">
        <table  class="table table-bordered">
            <tr class="table" >
                <th>STT</th>
                <th>Mã Nhân viên</th>
                <th>Ảnh</th>
                <th>Họ và tên</th>
                <th>Ngày sinh</th>
                <th>Địa chỉ</th>
                <th>Chức vụ</th>
                <th>Hành Động</th>
            </tr>

            <c:set var="index" value="1"/> <%--  This code to display number of this pupil in table --%>
            <c:forEach items="${requestScope.waitlistpersonnel}" var="p">
                <tr >
                    <td>${index}</td>
                    <td>${p.getId()}</td>
                    <td style="width: 30%;"><img src="../images/${p.getAvatar()}"
                                                 class="mx-auto d-block"
                                                 style="width:50%;"></td>
                    <td>${p.getLastName()} ${p.getFirstName()}</td>
                    <td><fmt:formatDate value="${p.getBirthday()}" pattern="dd/MM/yyyy" /></td>
                    <td>${p.getAddress()}</td>
                     <td>
                                <c:if test="${p.getRoleId()== 0}">     
                                    Admin
                                </c:if>
                                <c:if test="${p.getRoleId()==1}">     
                                    Hiệu trưởng
                                </c:if>
                                <c:if test="${p.getRoleId()==2}">     
                                    Accademic Staff
                                </c:if>
                                <c:if test="${p.getRoleId()==3}">     
                                    Nhân viên kế toán
                                </c:if>
                                <c:if test="${p.getRoleId()==4}">     
                                    Giáo viên
                                </c:if>
                            </td>
                    <td style=" vertical-align: middle; padding-left: 10px"  >
                        <form action="viewpersonnel" method="get">
                                        <input type="hidden" value="${p.getId()}" name="id">  
                                        <button type="submit">Xem thông tin chi tiết</button>   
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








