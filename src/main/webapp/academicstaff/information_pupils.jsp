<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Title</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Main CSS-->
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">


    </head>

    <body>
    <jsp:include page="dashboard_staff.jsp"/>
    <main class="app-content">
        <div class="student-info">
            <div class="header">
                <div class="avatar" >
                    <img src="../images/${pupil.avatar}" alt="Avatar"/>
                </div>
                <div class="id">ID: <span>${pupil.id}</span></div>
            </div>
            <form action="updatePulpil" method="POST">
                <div class="form-group">
                    <label for="fullname">Họ và tên:</label>
                    <input type="text" id="fullname" value="${pupil.lastName} ${pupil.firstName}">
                </div>
                <div class="form-group">
                    <label for="birthday">Ngày sinh:</label>
                    <input type="date" id="birthday" name="date" placeholder="DD/MM/YYYY" value="${pupil.birthday}">
                </div>
                <div class="form-group">
                    <label for="mother-name">Họ và tên mẹ:</label>
                    <input type="text" id="mother-name" value="${pupil.motherName}">
                </div>
                <div class="form-group">
                    <label for="mother-phone">Số điện thoại mẹ:</label>
                    <input type="text" id="mother-phone" value="(+84)${motherPhoneNumber}">
                </div>
                <div class="form-group">
                    <label for="father-name">Họ và tên bố:</label>
                    <input type="text" id="father-name" value="${pupil.fatherName}">
                </div>
                <div class="form-group">
                    <label for="father-phone">Số điện thoại bố:</label>
                    <input type="text" id="father-phone" value="(+84)${pupil.fatherPhoneNumber}">
                </div>
                <div class="form-group">
                    <label for="address">Địa chỉ:</label>
                    <input type="text" id="address" value="${pupil.address}">
                </div>
                <div class="form-group special-notes">
                    <label for="notes">Ghi chú đặc biệt của phụ huynh:</label>
                    <input id="notes" value="${pupil.parentSpecialNote}"></input>
                </div>
                <div class="form-buttons">
                    <button class="cancel" type="button" onclick="cancelAction()">HUỶ</button>
                    <button type="submit" class="save">LƯU</button>
                </div>
            </form>
        </div>
    </main>
    </body>
    <script>
        function cancelAction() {
            window.location.href = '${pageContext.request.contextPath}/academicstaff/listpupil';
        }
        function previewAvatar(event) {
            const reader = new FileReader();
            reader.onload = function () {
                const output = document.getElementById('avatarDisplay');
                output.src = reader.result;
            };
            reader.readAsDataURL(event.target.files[0]);
        }

        function redirectToInfoPage() {
            window.location.href = '${pageContext.request.contextPath}/academicstaff/information';
        }
    </script>
</html>
