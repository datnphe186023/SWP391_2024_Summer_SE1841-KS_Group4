<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Title</title>
        <style>
            .app-sidebar__user-avatar {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                cursor: pointer;
                object-fit: cover;
            }
            .avatar-input {
                display: none;
            }
        </style>
    </head>

    <body>
    <jsp:include page="dashboard_staff.jsp"/>
        <!-- Head Teacher Information Section -->
        <main class="app-content">
            <div class="app-title">
                <div>
                    <h1><i class="fa fa-edit"></i> Thông tin giáo viên</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="app-sidebar__user">
                        <img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${sessionScope.personnel.avatar}" alt="User Image" onclick="document.getElementById('avatarInput').click()">
                        <input class="avatar-input" id="avatarInput" type="file" name="avatar" accept="image/*" onchange="previewAvatar(event)">
                    </div>
                    <div class="mt-3">
                        <button class="btn btn-primary btn-block" onclick="location.href = 'changePasswordPage'">Đổi mật khẩu</button>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tile">
                        <div class="tile-body">
                            <form method="post" action="/academicstaff/information" enctype="multipart/form-data">

                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="id">ID</label>
                                        <input class="form-control" id="id" type="text" name="id" value="${user.id}" readonly style="width: 250px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="username">Username</label>
                                        <input class="form-control" id="username" type="text" name="username" value="${user.username}" readonly style="width: 250px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="firstName">Họ</label>
                                        <input class="form-control" type="text" name="firstName" value="${personnel.firstName}" style="width: 250px" disabled>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="lastName">Tên</label>
                                        <input class="form-control" type="text" name="lastName" value="${personnel.lastName}" style="width: 250px" disabled>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="gender">Giới tính</label>
                                        <select class="form-control" id="gender" name="gender" style="width: 250px" disabled>
                                            <option value="Male" ${sessionScope.personnel.gender ? 'selected' : ''}>Nam</option>
                                            <option value="Female" ${!sessionScope.personnel.gender ? 'selected' : ''}>Nữ</option>
                                        </select>
                                    </div>

                                    <div class="form-group col-md-6">
                                        <label for="phone">Số điện thoại</label>
                                        <input class="form-control" id="phone" type="text" name="phone" value="${personnel.phoneNumber}" style="width: 250px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="email">Email</label>
                                        <input class="form-control" id="email" type="email" name="email" value="${personnel.email}" style="width: 350px">
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="address">Địa chỉ</label>
                                        <input class="form-control" id="address" type="textarea" name="address" value="${personnel.address}">
                                    </div>
                                </div>
                                <!-- Remove Avatar input from form since it's handled separately in sidebar -->
                                <div class="form-group">
                                    <button class="btn btn-primary" type="submit">Cập nhật thông tin</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <script>
                            function previewAvatar(event) {
                                const reader = new FileReader();
                                reader.onload = function () {
                                    const output = document.getElementById('avatarDisplay');
                                    output.src = reader.result;
                                }
                                reader.readAsDataURL(event.target.files[0]);
                            }

                            function redirectToInfoPage() {
                                window.location.href = '${pageContext.request.contextPath}/staff/information';
                            }
        </script>
    </body>
</html>
