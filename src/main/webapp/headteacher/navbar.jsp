<%@ page import="models.pupil.PupilDAO" %>
<%@ page import="models.classes.ClassDAO" %>
<%@ page import="models.schoolYear.SchoolYearDAO" %>
<%@ page import="models.timetable.TimetableDAO" %>
<%@ page import="models.timetable.ITimetableDAO" %>
<%@ page import="models.subject.SubjectDAO" %>
<%@ page import="models.classes.IClassDAO" %>
<%@ page import="models.schoolYear.ISchoolYearDAO" %>
<%@ page import="models.subject.ISubjectDAO" %>
<%@ page import="models.pupil.IPupilDAO" %>
<%@ page import="models.schoolYear.SchoolYear" %>
<%@ page import="java.util.List" %>
<%@ page import="models.classes.Class" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>

        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <meta name="description" content="">
        <meta name="author" content="">
        <link rel="shortcut icon" type="image/x-icon" href="../image/logo.png" />
        <title></title>

        <!-- Custom fonts for this template-->
        <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
        <link
            href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i"
            rel="stylesheet">

        <!-- Custom styles for this template-->
        <link href="../css/sb-admin-2.min.css" rel="stylesheet">

    </head>

    <body id="page-top">

        <!-- Sidebar -->
        <ul class="navbar-nav bg-gradient-primary sidebar sidebar-dark accordion" id="accordionSidebar">

            <!-- Sidebar - Brand -->
            <a class="sidebar-brand d-flex align-items-center justify-content-center" href="${pageContext.request.contextPath}/headteacher/dashboard">
                <div class="sidebar-brand-icon rotate-n-15">
                    <i class="fas fa-laugh-wink"></i>
                </div>
                <div class="sidebar-brand-text mx-3">Bono Kindergarten</div>
            </a>

            <!-- Divider -->
            <hr class="sidebar-divider my-0">

            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTwo"
                   aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-cog"></i>
                    <span>Quản lý lớp học</span>
                </a>
                <div id="collapseTwo" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="class">Danh Sách Lớp</a>
                        <% IClassDAO classDAO = new ClassDAO(); %>
                        <% ISchoolYearDAO schoolYearDAO = new SchoolYearDAO(); %>
                        <a class="collapse-item" href="reviewclass">
                            <%
                                List<SchoolYear> futureSchoolYears = schoolYearDAO.getFutureSchoolYears();
                                int totalClasses = 0;
                                for (SchoolYear schoolYear : futureSchoolYears) {
                                    String schoolYearId = schoolYear.getId();
                                    totalClasses += classDAO.getByStatus("đang chờ xử lý", schoolYearId).size();
                                }
                            %>
                            Lớp Chờ Phê Duyệt (<%= totalClasses %>)
                        </a>
                    </div>
                </div>
            </li>

            <!-- Nav Item -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseTimetable"
                   aria-expanded="true" aria-controls="collapseTimetable">
                    <i class="fas fa-fw fa-calendar-week"></i>
                    <span>Quản lý thời khóa biểu</span>
                </a>
                <div id="collapseTimetable" class="collapse" aria-labelledby="headingTimetable" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="timetable">Danh Sách Thời Khóa Biểu</a>
                        <% ITimetableDAO timetableDAO = new TimetableDAO(); %>
                        <a class="collapse-item" href="reviewtimetable">Đang Chờ Phê Duyệt(<%=timetableDAO.getListTimetableByStatus("đang chờ xử lý").size()%>)</a>
                        <a class="collapse-item" href="viewtimetableclass">Xem Thời Khóa Biểu</a>
                    </div>
                </div>
            </li>
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseSubject"
                   aria-expanded="true" aria-controls="collapseTimetable">
                    <i class="fas fa-fw fa-book"></i>
                    <span>Quản lý môn học</span>
                </a>
                <div id="collapseSubject" class="collapse" aria-labelledby="headingSubject" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="listsubject">Danh Sách Môn Học</a>
                        <%ISubjectDAO subjectDAO = new SubjectDAO();%>
                        <a class="collapse-item" href="reviewsubject">Đang Chờ Phê Duyệt(<%=subjectDAO.getSubjectsByStatus("đang chờ xử lý").size()%>)</a>
                    </div>
                </div>
            </li>

            <!-- Nav Item -->
            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapsePupil"
                   aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-user"></i>
                    <span>Quản lý học sinh</span>
                </a>
                <div id="collapsePupil" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="listpupil">Danh Sách Học Sinh</a>
                        <% IPupilDAO pupilDAO = new PupilDAO(); %>
                        <a class="collapse-item" href="reviewpupil">Học Sinh Cần Phê Duyệt(<%=pupilDAO.getPupilByStatus("đang chờ xử lý").size()%>)</a>
                    </div>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="listpersonnel">
                    <i class="fas fa-fw fa-user-friends"></i>
                    <span>Quản lý nhân sự</span></a>
            </li>

            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseMealTimetable"
                   aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-cheese"></i>
                    <span>Quản lý thực đơn</span></a>
                <div id="collapseMealTimetable" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="viewmealtimetable">Xem thực đơn</a>
                        <a class="collapse-item" href="waitlistmealtimetable">Phê duyệt thực đơn</a>
                        <a class="collapse-item" href="processfoodmenu">Phê duyệt suất ăn</a>
                    </div>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseEvent"
                   aria-expanded="true" aria-controls="collapseTwo">
                    <i class="fas fa-fw fa-calendar-check" aria-hidden="true"></i>
                    <span>Quản lý Sự Kiện</span>
                </a>
                <div id="collapseEvent" class="collapse" aria-labelledby="headingTwo" data-parent="#accordionSidebar">
                    <div class="bg-white py-2 collapse-inner rounded">
                        <a class="collapse-item" href="listevent">Danh Sách Sự Kiện</a>
                        <a class="collapse-item" href="sendevent">Tạo Sự Kiện</a>
                    </div>
                </div>
            </li>

            <li class="nav-item">
                <a class="nav-link" href="#">
                    <i class="fas fa-fw fa-bars"></i>
                    <span>Tổng kết khen thưởng học sinh</span></a>
            </li>

            <!-- Divider -->
            <hr class="sidebar-divider d-none d-md-block">

            <!-- Sidebar Toggler (Sidebar) -->
            <div class="text-center d-none d-md-inline">
                <button class="rounded-circle border-0" id="sidebarToggle"></button>
            </div>

        </ul>
        <!-- End of Sidebar -->


        <!-- Bootstrap core JavaScript-->
        <script src="../vendor/jquery/jquery.min.js"></script>
        <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

        <!-- Core plugin JavaScript-->
        <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

        <!-- Custom scripts for all pages-->
        <script src="../js/sb-admin-2.min.js"></script>

        <!-- Page level plugins -->
        <script src="../vendor/chart.js/Chart.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/chart-area-demo.js"></script>
        <script src="../js/demo/chart-pie-demo.js"></script>

    </body>

</html>