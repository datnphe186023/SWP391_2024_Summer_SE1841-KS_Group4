<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 10-Jun-24
  Time: 7:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Chi Tiết Đơn Từ</title>
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Chi Tiết Đơn Từ</h1>

                <div class="card">
                    <div class="card-header bg-primary text-white">
                        <h3>Application Details</h3>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">ID:</div>
                            <div class="col-sm-9" id="id"></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Processed At:</div>
                            <div class="col-sm-9" id="processedAt"></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Type:</div>
                            <div class="col-sm-9" id="type"></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Details:</div>
                            <div class="col-sm-9" id="details"></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Status:</div>
                            <div class="col-sm-9" id="status"></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Created By:</div>
                            <div class="col-sm-9" id="createdBy"></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Created At:</div>
                            <div class="col-sm-9" id="createdAt"></div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Processed By:</div>
                            <div class="col-sm-9" id="processedBy"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
</body>
</html>
