
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
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Quản lý học sinh</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <style>
        #scrollToTopBtn {
            position: fixed;
            bottom: 20px;
            right: 20px;
            z-index: 99;
            font-size: 18px;
            border: none;
            outline: none;
            background-color: green;
            color: white;
            cursor: pointer;
            padding: 15px;
            border-radius: 4px;
            opacity: 0.1;
            transition: opacity 0.3s;
        }

        #scrollToTopBtn:hover {
            opacity: 1;
        }
    </style>

    <%
        String toastMessage = (String) session.getAttribute("toastMessage");
        String toastType = (String) session.getAttribute("toastType");
        session.removeAttribute("toastMessage");
        session.removeAttribute("toastType");
    %>
    <script>
        $(document).ready(function() {
            var toastMessage = '<%= toastMessage %>';
            var toastType = '<%= toastType %>';
            if (toastMessage) {
                if (toastType === 'success') {
                    toastr.success(toastMessage);
                } else if (toastType === 'error') {
                    toastr.error(toastMessage);
                }
            }
        });
    </script>

</head>
<body>
    <jsp:include page="dashboard.jsp"/>
<%--Begin : title    --%>
    <main class="app-content">

        <div class="row justify-content-center">
            <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách học sinh</span>
        </div>
<%--End : title    --%>
        <div class="row">
            <%--  Begin : Search item      --%>
            <div class="col-lg-6">
                <form action="listpupil" method="get">
                    <div class="search-field">
                        <div class="form-group has-search">
                            <span style="margin-top: 5px" class="fa fa-search form-control-feedback"></span>
                            <input style="border-radius: 30px" type="text" class="form-control" placeholder="" name="information">
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
        <button id="scrollToTopBtn"><i class="fa fa-angle-double-up"></i></button>
    </main>
        <script>
            const btnScrollToTop = document.getElementById('scrollToTopBtn')
            const docEl = document.documentElement

            document.addEventListener('scroll', () => {
                const scrollToTal = docEl.scrollHeight - docEl.clientHeight

                if ((docEl.scrollTop / scrollToTal) >= 0.4) {
                    btnScrollToTop.hidden = false
                } else {
                    btnScrollToTop.hidden = true
                }
            })

            btnScrollToTop.addEventListener('click', () => {
                docEl.scrollTo({
                    top: 0,
                    behavior: 'smooth'
                })
            })
        </script>
</body>
</html>


