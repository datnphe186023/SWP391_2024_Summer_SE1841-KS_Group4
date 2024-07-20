
<%--
  Created by IntelliJ IDEA.
  stoled from: Anh Quan 
  By: Thắng Đức 
  Date: 5/23/2024
  Time: 8:47 PM
  To change this template use File | Settings | File Templates.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>

        <title>Quản lý học sinh</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script>
            $(document).ready(function () {
                var toastMessage = '<%= request.getAttribute("message") %>';
                var toastType = '<%= request.getAttribute("type") %>';
                if (toastMessage) {
                    if (toastType === 'success') {
                        toastr.success(toastMessage);
                    } else if (toastType === 'fail') {
                        toastr.error(toastMessage);
                    }
                }
            });
        </script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
        <script>
        function redirect() {
          window.location.href = "listpersonnel";
        }
    </script>
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
    </head>
    <body id="page-top">
    <div id="wrapper">
        <jsp:include page="navbar.jsp"/>
        <div id="content-wrapper" class="d-flex flex-column">
            <div id="content">
                <jsp:include page="../header.jsp"/>
                <div class="container-fluid">
        <main class="app-content">
            <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Nhân Viên Chờ Phê Duyệt</h1>
            <%--End : title    --%>

                
            <div class="row" style="text-align: center; align-content: center">
                                                        <div class="col-lg-3"></div>
                                                    
                                                        <div class="col-lg-3"></div>
                                                        
                                                        <div class="col-lg-3"></div>                                                 
                                                    
                                                        <div class="col-lg-3" style="display: flex; justify-content: end">
                                                            <button class="btn btn-primary">
                                                            <a onclick="redirect()">Danh sách nhân viên</a>
                                                            </button>
                                                        </div>
                                                </div>

            <div class="card shadow mb-4">
                <div class="card-header py-3">
                    <h6 class="m-0 font-weight-bold text-primary">Danh Sách Nhân Viên Chờ Phê Duyệt</h6>
                </div>

                <div class="card-body">
                    <div class="table-responsive">
                        <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                            <thead>
                            <tr>
                                <th>STT</th>
                                <th>Ảnh</th>
                                <th>Mã Nhân Viên</th>
                                <th>Tên</th>
                                <th>Giới Tính</th>
                                <th>Ngày sinh</th>
                                <th>Chức vụ</th>
                                <th>Trạng thái</th>
                                <th>Chi tiết</th>
                            </tr>
                            </thead>
                            <tbody>
                            <div style="color: red">${requestScope.error}</div>
                            <c:forEach items="${waitlistpersonnel}" var="p" varStatus="status">
                                <tr>
                                    <th scope="row">${status.index + 1}</th>
                                    <td><img class="profile_img" src="../images/${p.getAvatar()}" alt="ảnh nhân viên" width="191px"
                                             height="263px" object-fit: cover></td>
                                    <td>${p.getId()}</td>
                                    <td>${p.getLastName()} ${p.getFirstName()}</td>
                                    <td>
                                        <c:if test="${p.getGender()==true}">
                                            Nam
                                        </c:if>
                                        <c:if test="${p.getGender()==false}">
                                            Nữ
                                        </c:if>
                                    </td>
                                    <td>${p.getBirthday()} </td>
                                    <td>
                                        <c:if test="${p.getRoleId()== 0}">
                                            Nhân viên IT
                                        </c:if>
                                        <c:if test="${p.getRoleId()==1}">
                                            Hiệu trưởng
                                        </c:if>
                                        <c:if test="${p.getRoleId()==2}">
                                            Giáo vụ
                                        </c:if>
                                        <c:if test="${p.getRoleId()==3}">
                                            Nhân viên kế toán
                                        </c:if>
                                        <c:if test="${p.getRoleId()==4}">
                                            Giáo viên
                                        </c:if>
                                    </td>
                                    <c:if test="${p.getStatus() != null}">
                                        <c:choose>
                                            <c:when test="${p.getStatus() == 'đang làm việc'}">
                                                <td >
                                                    <span class="badge badge-success">${p.getStatus()}</span>
                                                </td>
                                            </c:when>
                                            <c:when test="${p.getStatus() == 'đang chờ xử lý'}">
                                                <td>
                                                    <span class="badge badge-warning">${p.getStatus()}</span>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td >
                                                    <span class="badge badge-info">${p.getStatus()}</span>
                                                </td>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                    <td>

                                        <a href="viewpersonnel?id=${p.getId()}&page=waitlist"
                                           class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Thông
                                            tin chi tiết</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </main>
                </div>
            </div>
            <jsp:include page="../footer.jsp"/>
        </div>
    </div>

    </body>
    <!-- Page level plugins -->
    <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
    <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

    <!-- Page level custom scripts -->
    <script src="../js/demo/datatables-demo.js"></script>
</html>








