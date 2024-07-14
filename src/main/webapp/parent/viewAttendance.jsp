<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 30-Jun-24
  Time: 8:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="pupilAttendanceBean" class="models.pupil.PupilAttendanceDAO"/>
<jsp:useBean id="timetableBean" class="models.timetable.TimetableDAO"/>
<jsp:useBean id="personneBean" class="models.personnel.PersonnelDAO"/>
<html>
<head>
    <title>Tình Hình Điểm Danh</title>
    <script>
        function submitForm(formId) {
            document.getElementById(formId).submit();
        }
    </script>
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="header.jsp"/>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12 mb-4">
                        <form action="attendance" id="mergedForm">
                            <div class="mb-4">
                                <label>Chọn năm học</label>
                                <select class="custom-select" style="width: 10%" aria-label="Default select example" onchange="submitForm('mergedForm')" name="schoolYearId">
                                    <c:forEach items="${requestScope.schoolYears}" var="year">
                                        <option ${requestScope.schoolYearId eq year.id ? "selected" : ""} value="${year.id}">${year.name}</option>
                                    </c:forEach>
                                </select>
                                <label class="ml-1">Chọn tuần</label>
                                <select class="custom-select" style="width: 15%" aria-label="Default select example" onchange="submitForm('mergedForm')" name="weekId">
                                    <c:forEach items="${requestScope.weeks}" var="week">
                                        <option ${requestScope.weekId eq week.id ? "selected" : ""} value="${week.id}">${week.getStartDatetoEndDate()}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </form>
                    </div>
                </div>
                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Tình hình điểm danh của bé ${requestScope.pupilFullName}</h6>
                    </div>
                    <div class="card-body">
                        <div class="table-responsive">
                            <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                <thead>
                                <tr>
                                    <th>Ngày</th>
                                    <th>Giáo viên</th>
                                    <th>Trạng thái điểm danh</th>
                                    <th>Ghi chú</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="day" items="${requestScope.days}" varStatus="status">
                                    <tr>
                                        <fmt:setLocale value="vi_VN" />
                                        <td>
                                            <fmt:formatDate value="${day.date}" pattern="EEEE" /> - <fmt:formatDate value="${day.date}" pattern="dd/MM/yyyy" />
                                        </td>
                                        <td>
                                            ${personneBean.getPersonnel(timetableBean.getTeacherByDayId(day.id)).lastName} ${personneBean.getPersonnel(timetableBean.getTeacherByDayId(day.id)).firstName}
                                        </td>
                                        <c:set var="attendance" value="${pupilAttendanceBean.getAttendanceByPupilAndDay(requestScope.pupilId, day.id)}"/>
                                        <c:set value="${attendance.status}" var="s"/>
                                        <c:if test="${s eq 'present'}">
                                            <td><span class="badge badge-success">có mặt</span></td>
                                        </c:if>
                                        <c:if test="${s eq 'absent'}">
                                            <td><span class="badge badge-danger">vắng</span>  </td>
                                        </c:if>
                                        <c:if test="${s == null}">
                                            <td><span class="badge badge-warning">chưa cập nhật</span>  </td>
                                        </c:if>
                                        <td>${attendance.note}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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
