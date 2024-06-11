<%@ page import="models.application.Application" %><%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 10-Jun-24
  Time: 7:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="bean" class="models.pupil.PupilDAO"/>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.css"/>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/2.1.4/toastr.min.js"></script>
<script>
    $(document).ready(function () {
        var toastMessage = '<%= request.getAttribute("toastMessage") %>';
        var toastType = '<%= request.getAttribute("toastType") %>';
        if (toastMessage) {
            if (toastType === 'success') {
                toastr.success(toastMessage);
            } else if (toastType === 'error') {
                toastr.error(toastMessage);
            }
        }
    });
</script>
<%--confirm message of processing application--%>
<script>
    function confirmAction(action) {
        var actionName = (action === 'approve') ? 'duyệt' : 'từ chối';
        return confirm('Bạn có chắc chắn muốn ' + actionName + ' đơn này không?');
    }
</script>
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
                <div class="card mb-4">
                    <div class="card-body">
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Loại đơn:</div>
                            <div class="col-sm-9" id="type">${requestScope.application.type.name}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Người gửi:</div>
                            <div class="col-sm-9" id="createdBy">${bean.getPupilByUserId(requestScope.application.createdBy).id}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Thời gian gửi:</div>
                            <div class="col-sm-9" id="createdAt">${requestScope.application.createdAt}</div>
                        </div>
                        <div class="row mb-3">
                            <div class="col-sm-3 font-weight-bold">Trạng thái đơn:</div>
                            <c:set value="${requestScope.application.status}" var="s"/>
                            <c:if test="${s eq 'đã duyệt'}">
                                <div class="col-sm-9 text-success" id="createdAt">${s}</div>
                            </c:if>
                            <c:if test="${s eq 'đang xử lý'}">
                                <div class="col-sm-9 text-warning" id="createdAt">${s}</div>
                            </c:if>
                            <c:if test="${s eq 'đã từ chối'}">
                                <div class="col-sm-9 text-danger" id="createdAt">${s}</div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <div class="card mb-4">
                    <div class="card-header bg-primary text-white">
                        <h4>Lý do:</h4>
                    </div>
                    <div class="card-body">
                        <div class="row mb-3">
                            <%
                                Application app = (Application) request.getAttribute("application");
                                String details = app.getDetails();
                                if (details != null) {
                                    details = details.replace("\r\n", "<br/>").replace("\n", "<br/>");
                                }
                            %>
                            <div class="col-sm-9" id="details">
                                <%= details %>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-md-6">
                    <form action="applicationdetails" method="post" id="applicationForm">
                        <label for="note">Ghi chú</label>
                         <textarea class="form-control mb-5" type="text" placeholder="${requestScope.application.processNote}"
                                   name="note" id="note" rows="5" required></textarea>
                        <input name="id" value="${requestScope.applicationId}" hidden/>
                        <button type="submit" name="action" value="approve" class="btn btn-success" onclick="return confirmAction('approve')">
                            Duyệt
                        </button>
                        <button type="submit" name="action" value="reject" class="btn btn-danger" onclick="return confirmAction('reject')">
                            Từ chối
                        </button>
                    </form>
                </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>

<%--        this checks for application status and let staff process or not--%>
        <script>
            document.addEventListener("DOMContentLoaded", function() {
                // Get the application status from the JSP
                var applicationStatus = '${requestScope.application.status}';

                // Get the form elements
                var noteTextarea = document.getElementById('note');
                var approveButton = document.querySelector('button[name="action"][value="approve"]');
                var rejectButton = document.querySelector('button[name="action"][value="reject"]');

                // Check the status and modify the form accordingly
                if (applicationStatus !== 'đang xử lý') {
                    // Make the textarea read-only
                    noteTextarea.readOnly = true;
                    // Hide the submit buttons
                    approveButton.style.display = 'none';
                    rejectButton.style.display = 'none';
                }
            });
        </script>
</body>
</html>
