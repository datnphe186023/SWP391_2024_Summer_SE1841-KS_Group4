<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 6/27/2024
  Time: 7:39 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="vi">

<head>
    <title>Danh Sách Học Sinh</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Custom fonts for this template-->
    <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
    <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

    <!-- Custom styles for this template-->
    <link href="../css/sb-admin-2.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
    <script>
        $(document).ready(function () {
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
        function confirmAccept(formId, msg) {
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
</head>

<body id="page-top">
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Đánh giá học sinh hàng ngày</h1>
                <div class="row">


                </div>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <c:choose>
                            <c:when test="${requestScope.schoolYear == null}">
                                <h6 class="m-0 font-weight-bold text-primary">Năm học: <a
                                        style="color: red">Chưa bắt đầu năm học mới</a>
                                </h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="m-0 font-weight-bold text-primary">Năm học: <a
                                >${requestScope.schoolYear.name}</a>
                                </h6>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${requestScope.teacherGrade == null}">
                                <h6 class="m-0 font-weight-bold text-primary">Khối: <a
                                        style="color: red">Chưa có lớp</a>
                                </h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="m-0 font-weight-bold text-primary">Khối: <a
                                >${requestScope.teacherGrade}</a>
                                </h6>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${requestScope.teacherClass == null}">
                                <h6 class="m-0 font-weight-bold text-primary">Lớp: <a
                                        style="color: red">Chưa có lớp</a>
                                </h6>
                            </c:when>
                            <c:otherwise>
                                <h6 class="m-0 font-weight-bold text-primary">Lớp: <a
                                >${requestScope.teacherClass}</a>
                                </h6>
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <jsp:useBean id="evaluation" class="models.evaluation.EvaluationDAO"/>
                    <c:set var="dateId" value="${requestScope.dateId}"/>
                    <div class="card-body">
                    <form method="post" id="evaluateForm" action="evaluate?action=evaluatePupil">
                        <div class="table-responsive">
                            <table class="table table-bordered"  width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>STT</th>
                                    <th>Mã học sinh</th>
                                    <th>Ảnh</th>
                                    <th>Họ và tên</th>
                                    <th>Đánh giá</th>
                                    <th>Ghi chú</th>
                                </tr>
                                </thead>
                                <tbody>
                                <div style="color: red">${requestScope.error}</div>
                                <c:forEach var="pupil" items="${requestScope.listPupil}" varStatus="status">
                                    <tr>
                                        <th scope="row">${status.index + 1}</th>
                                        <td>${pupil.id}</td>
                                        <td style="width: 20%;">
                                            <img src="../images/${pupil.avatar}"
                                                 class="mx-auto d-block"
                                                 style="width:100px; height:100px; object-fit: cover;">
                                        </td>
                                        <td>${pupil.lastName} ${pupil.firstName}</td>
                                        <td class="text-center">
                                            <select class="form-control mt-4" aria-label="Default select example" name="evaluation-${pupil.id}">
                                                <option value="nghỉ học" ${evaluation.getEvaluationByPupilIdAndDay(pupil.id,dateId).evaluation eq "Nghỉ học"?"selected":""}>-</option>
                                                <option value="Ngoan"  ${evaluation.getEvaluationByPupilIdAndDay(pupil.id,dateId).evaluation eq 'Ngoan'?"selected":""} name="evaluationId-good" >Ngoan</option>
                                                <option value="Chưa ngoan" ${evaluation.getEvaluationByPupilIdAndDay(pupil.id,dateId).evaluation eq 'Chưa ngoan'?"selected":""} name="evaluationId-bad">Chưa ngoan</option>
                                            </select>
                                        </td>
                                        <td class="text-center">
                                            <textarea class="mt-2" rows="3" name="notes-${pupil.id}">${evaluation.getEvaluationByPupilIdAndDay(pupil.id,dateId).notes}</textarea>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>

                            </table>
                            <c:if test="${requestScope.schoolYear != null || requestScope.teacherGrade != null }">
                                <div class="btn-group-right float-right">
                                    <button type="button" class="btn btn-success" onclick="confirmAccept('evaluateForm','Bạn có chắc chắn muốn lưu thay đổi ?')"  style="width: 100px">Lưu</button>
                                </div>
                            </c:if>
                        </div>
                    </form>
                    </div>
                    <%-- Begin confirmMessage modal--%>
                    <div class="modal fade" id="confirmMessage" tabindex="-1" role="dialog" aria-labelledby="confirmationModalLabel" aria-hidden="true">
                        <div class="modal-dialog" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" >Xác nhận thao tác</h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <div class="modal-body" id="confirmMessageTitle">
                                    <!-- Dynamic message will be inserted here -->
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Hủy</button>
                                    <button type="button" class="btn btn-primary" id="confirmMessageButton">Xác Nhận</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <%-- End confirmMessage modal--%>
                </div>

            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>
</html>
