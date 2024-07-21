<%@ page import="models.event.Event" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %><%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 22-Jun-24
  Time: 1:58 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Chi tiết sự kiện</title>
</head>
<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Chi Tiết Sự kiện</h1>
                <div class="card mb-4">
                    <div class="card-header">
                        <h4>${requestScope.event.heading}</h4>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <%
                                Event event = (Event) request.getAttribute("event");
                                String details = event.getDetails();
                                if (details != null) {
                                    details = details.replace("\r\n", "<br/>").replace("\n", "<br/>");
                                }
                                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                             String eventDate = dateFormat.format(event.getDate());

                            %>
                            <div class="col-sm-9" id="details">
                                <%= details %>
                            </div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-9" id="event-date">
                                <strong>Ngày:</strong> <%= eventDate %>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="btn-group-right float-right">
                    <button type="button" class="btn btn-primary" onclick="history.back()"  style="width: 100px">Quay lại</button>
                </div>

            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>
</html>
