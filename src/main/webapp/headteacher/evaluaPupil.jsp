<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    function confirmAccept(formId, msg, action) {
        document.getElementById('actionField').value = action;
        formIdToAccept = formId;
        document.getElementById('confirmMessageTitle').innerText = msg;
        $('#confirmMessage').modal('show');
    }
    $(document).ready(function () {
        $('#confirmMessageButton').click(function () {
            document.getElementById(formIdToAccept).submit();

        });
    });
</script>
<html>
    <head>
        <title>Tổng Kết Khen Thưởng</title>
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <form action="evaluapupil" method="post" id="yearForm">
                            <h1 class="h3 mb-4 text-gray-800 text-center">Tổng Kết Khen Thưởng Học Sinh</h1>
                            <div class="card mb-4">
                                <div class="card-body">
                                    <div class="row mb-3">
                                        <div class="col-sm-6 font-weight-bold">Năm Học:</div>
                                        <select style="border-radius: 8px" name="schoolyear" id="schoolyear" style="border-radius: 8px" onchange="document.getElementById('yearForm').submit();">
                                            <option hidden="">Chọn Năm Học</option>
                                            <c:forEach items="${listSchoolYear}" var="schoolYear">
                                                <option ${requestScope.schoolYearEQ eq schoolYear.id ? "selected":""} value="${schoolYear.id}">${schoolYear.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-sm-6 font-weight-bold">Tổng Số Học Sinh:</div>
                                        <div class="col-sm-6" id="type">${requestScope.sumPupil}</div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-sm-6 font-weight-bold">Số Học Sinh Đạt Danh Hiệu Cháu Ngoan Bác Hồ:</div>
                                        <div class="col-sm-6" id="createdBy">

                                        </div>
                                    </div>
                                    <div class="row mb-3">
                                        <div class="col-sm-6 font-weight-bold">Số Học Sinh Không Đạt Danh Hiệu Cháu Ngoan Bác Hồ:</div>
                                        <div class="col-sm-6" id="createdAt">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="card mb-4">
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                            <thead>
                                                <tr>
                                                    <th>STT</th>
                                                    <th>Mã học sinh</th>
                                                    <th>Ảnh</th>
                                                    <th>Họ và tên</th>
                                                    <th>Ngày sinh</th>
                                                    <th>Lớp</th>
                                                    <th>Số phiếu bé ngoan của trẻ</th>
                                                    <th>Cháu ngoan bác Hồ</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${listPupil}" var="pupil" varStatus="index">
                                                    <tr>
                                                        <td class="col-sm-1">${index.index +1}</td>
                                                        <td class="col-sm-2">${pupil.id}</td>
                                                        <td style="width: 20%;">
                                                            <img alt="" src="../images/${pupil.avatar}"
                                                                 class="mx-auto d-block"
                                                                 style="width:100px; height:100px; object-fit: cover;">
                                                        </td>
                                                        <td>${pupil.lastName} ${pupil.firstName}</td>
                                                        <td><fmt:formatDate value="${pupil.birthday}" pattern="dd/MM/yyyy" /></td>
                                                        <td class="col-sm-2">
                                                            <c:forEach items="${listClass}" var="aclass" varStatus="classIndex">
                                                                <c:if test="${classIndex.index == index.index}">
                                                                    ${aclass}
                                                                </c:if>
                                                            </c:forEach>
                                                        </td>
                                                        <td class="text-center align-middle"></td>
                                                        <td></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <jsp:include page="../footer.jsp"/>
                </div>
            </div>
    </body>
</html>
