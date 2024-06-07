<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 07-Jun-24
  Time: 7:50 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Gửi Đơn</title>
</head>
<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Gửi Đơn</h1>
                <form action="application" method="post">
                    <select class="form-select dropdown mb-5" aria-label="Default select example" name="type">
                        <c:forEach items="${requestScope.applicationTypes}" var="type">
                            <option value="${type.id}">${type.name}</option>
                        </c:forEach>
                    </select>
                    <textarea class="form-control mb-5" type="text" placeholder="Không được vượt quá 255 kí tự"
                              name="details" rows="5" required maxlength="255"></textarea>
                    <button type="submit" class="btn btn-primary float-right">Gửi</button>
                </form>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>
</html>
