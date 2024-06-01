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
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
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

            .form-buttons {
                display: flex;
                justify-content: space-between;
                align-items: center;
                margin-top: 20px;
            }

            .update-btn, .cancel-btn {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                font-size: 16px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .update-btn:hover, .cancel-btn:hover {
                background-color: #0056b3;
            }

            .cancel-btn {
                background-color: #dc3545;
            }

            .cancel-btn:hover {
                background-color: #c82333;
            }
        </style>

    </head>

    <body>
        <jsp:include page="dashboard_parent.jsp"/>
        <main class="app-content">
            <div class="app-title">
                <div>
                    <h1><i class="fa fa-edit"></i> Thông tin phụ huynh</h1>
                </div>
            </div>
            <div class="row">
                <div class="col-md-3">
                    <div class="app-sidebar__user">
                        <img class="app-sidebar__user-avatar" id="avatarDisplay" src="${pageContext.request.contextPath}/images/${pupil.avatar}" >
                        <input class="avatar-input" id="avatarInput" type="file" name="avatar">
                        <div>
                            <p class="app-sidebar__user-name"><b style="color: #000">${pupil.lastName} ${pupil.firstName}</b></p>

                        </div>
                    </div>
                </div>
                <div class="col-md-9">
                    <div class="tile">
                        <div class="tile-body">

                            <form action="updatepupils" method="post">
                                <input type="hidden" value="${personnel.userId}"/>
                                <table>
                                    <tbody>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>User ID :</h5><input placeholder="User Id" type="text" name="userId" value="${pupil.userId}" readonly=""/>
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>ID : </h5> <input placeholder="First Name" type="text" name="id" value="${pupil.id}" readonly=""/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Họ tên mẹ : </h5> <input type="text" name="mother_name" value="${pupil.motherName}"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">

                                                    <h5>Số điện thoại mẹ :</h5> <input type="text" name="mother_phone" value="${pupil.motherPhoneNumber}"/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Họ tên bố : </h5> <input type="text" name="father_name" value="${pupil.fatherName}"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Số điện thoại bố :</h5> <input type="text" name="father_phone" value="${pupil.fatherPhoneNumber}" /><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">    
                                                    <h5>Họ tên bé :</h5> <input type="text" name="name_pupil" value="${pupil.lastName} ${pupil.firstName}" readonly=""/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Ngày sinh của bé : </h5> <input type="date" name="birthday" value="${pupil.birthday}" readonly=""/><br />
                                                </div></td>
                                        </tr>
                                        <tr>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Địa chỉ : </h5> <input type="text" name="address" value="${pupil.address}" style="width: 200%"/><br />
                                                </div></td>
                                            <td><div class="form-group col-md-6">
                                                    <h5>Ghi chú :</h5> <input type="text" name="note" value="${pupil.parentSpecialNote}" style="width: 200%"/><br />
                                                </div></td>

                                        </tr>



                                    </tbody>
                                </table>
                                <div class="form-buttons">
                                    <button type="submit" class="update-btn">Chỉnh Sửa</button>
                                </div>
                            </form>



                        </div>
                    </div>
                </div>
            </div>
        </main>
    </body>
   
</html>