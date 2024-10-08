<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="pupilBean" class="models.pupil.PupilDAO"/>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Profile</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Custom CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">

        <style>
            .profile-card {
                display: flex;
                flex-direction: column;
                align-items: center;
                background: #f8f9fa;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }
            .profile-card img {
                width: 150px;
                height: 150px;
                border-radius: 50%;
                object-fit: cover;
                margin-bottom: 15px;
            }
            .profile-info {
                text-align: left;
                display: flex;
                flex-wrap: wrap;
                justify-content: space-between;
                width: 100%;
            }
            .profile-info div {
                flex: 1 1 45%; /* Adjust the width to be around 45% to fit two columns */
                margin-bottom: 10px;
            }
            .profile-info h3 {
                text-align: center;
                margin-bottom: 10px;
                color: #333;
                width: 100%;
            }
            .profile-info p {
                margin: 5px 0;
                color: #666;
            }
            .profile-actions {
                margin-top: 20px;
            }
            .profile-actions a {
                text-decoration: none;
                padding: 10px 20px;
                background: #007bff;
                color: white;
                border-radius: 5px;
                transition: background 0.3s ease;
            }
            .profile-actions a:hover {
                background: #0056b3;
            }
        </style>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header.jsp"/>
                    <div class="container-fluid">
                        <!-- Head Teacher Information Section -->
                        <main>

                            <div class="row justify-content-center">

                                <div class="col-md-6">
                                    <div class="card shadow mb-4">
                                        <div class="card-header py-3">
                                            <h3 class="m-0 font-weight-bold row justify-content-center">Thông tin tài khoản</h3>
                                        </div>
                                    </div>
                                    <div class="profile-card">
                                        <c:set var="pupil" value="${pupilBean.getPupilByUserId(sessionScope.pupil.userId)}"/>
                                        <img src="${pageContext.request.contextPath}/images/${pupil.avatar}" alt="User Avatar">
                                        <div class="profile-info">
                                            <h3>${pupil.lastName} ${pupil.firstName}</h3>

                                            <div class="col-md-6">
                                                <div>
                                                    <p><strong>ID người dùng:</strong> ${pupil.userId}</p>
                                                </div>
                                                <div>
                                                    <p><strong>ID học sinh:</strong> ${pupil.id}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Họ tên người giám hộ thứ nhất:</strong> ${pupil.firstGuardianName}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Số điện thoại người giám hộ thứ nhất:</strong> ${pupil.firstGuardianPhoneNumber}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Họ tên người giám hộ thứ hai:</strong> ${pupil.secondGuardianName}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Số điện thoại người giám hộ thứ hai:</strong> ${pupil.secondGuardianPhoneNumber}</p>
                                                </div>
                                            </div>
                                            <div class="col-md-6">
                                                <div>
                                                    <p><strong>Họ tên bé:</strong> ${pupil.lastName} ${pupil.firstName}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Ngày sinh của bé:</strong> <fmt:formatDate value="${pupil.birthday}" pattern="yyyy/MM/dd"/> </p>
                                                </div>
                                                <div>
                                                    <p><strong>Email:</strong> ${pupil.email}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Địa chỉ:</strong> ${pupil.address}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Tình trạng:</strong> ${pupil.status}</p>
                                                </div>
                                                <div>
                                                    <p><strong>Ghi chú:</strong> ${pupil.parentSpecialNote}</p>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="profile-actions">
                                            <a href="information">Cập nhật thông tin</a>
                                        </div>
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

</html>
