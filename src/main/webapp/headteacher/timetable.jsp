<%--
  Created by IntelliJ IDEA.
  User: datng
  Date: 24-May-24
  Time: 4:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
    <head>
        <title>Quản Lý Lớp Học</title>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <!-- Custom styles for this page -->
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body>
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Thời Khóa Biểu</h1>


                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary">Danh Sách Thời Khóa Biểu</h6>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>Mã</th>
                                                <th>Tên lớp</th>
                                                <th>Ca</th>
                                                <th>Ngày tạo</th>
                                                <th>Môn học</th>
                                                <th>Tạo Bởi</th>
                                                <th>Trạng Thái</th>
                                                <th>Ghi Chú</th>
                                                <th>Giáo Viên</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach var="listTimetable" items="${requestScope.listTimetable}">
                                                <tr>
                                                    <th>${listTimetable.id}</th>
                                                    <th>${listTimetable.aClass.name}</th>
                                                    <th>${listTimetable.timeslot.name}</th>
                                                    <th>${listTimetable.day.date}</th>
                                                    <th>${listTimetable.subject.name}</th>
                                                    <th>${listTimetable.createdBy.lastName} ${listTimetable.createdBy.firstName}</th>
                                                    <th>${listTimetable.status}</th>
                                                    <th>${listTimetable.note}</th>
                                                    <th>${listTimetable.teacher.lastName} ${listTimetable.teacher.firstName}</th>
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
        <!-- Page level plugins -->
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

        <!-- Page level custom scripts -->
        <script src="../js/demo/datatables-demo.js"></script>
    </body>
</html>
