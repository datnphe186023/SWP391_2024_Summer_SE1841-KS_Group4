<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <link rel="shortcut icon" type="image/x-icon" href="../image/logo.png" />
        <title>Title</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <!-- or -->
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <!-- Font-icon css-->
        <link rel="stylesheet" type="text/css"
              href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
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
            .change-password-btn {
                background-color: #007bff;
                color: white;
                border: none;
                padding: 10px 20px;
                text-align: center;
                text-decoration: none;
                display: inline-block;
                font-size: 16px;
                margin-top: 10px;
                border-radius: 5px;
                cursor: pointer;
                transition: background-color 0.3s ease;
            }

            .change-password-btn:hover {
                background-color: #0056b3;
            }
        </style>

    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>
                    <div class="container-fluid">
                        <main>
                            <div class="app-title">
                                <div>
                                    <h1><i class="fa fa-edit"></i> THÔNG TIN HỌC SINH</h1>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-3">
                                    <div class="app-sidebar__user">
                                        <img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${pupil.avatar}" >
                                        <input class="avatar-input" id="avatarInput" type="file" name="avatar">
                                        <div>
                                            <p class="app-sidebar__user-name"><b style="color: #000">${pupil.lastName} ${pupil.firstName}</b></p>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-9">
                                    <div class="tile">
                                        <div class="tile-body">
                                            <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+"/>
                                            <form action="${pageContext.request.contextPath}/headteacher/listpupil" method="get">
                                                <table>
                                                    <tbody>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>User ID :</h5><input placeholder="User Id" type="text" name="userId" value="${pupil.userId}" disabled=""/>
                                                                </div></td>
                                                            <td><div class="form-group col-md-6">
                                                                    <h5>ID : </h5> <input placeholder="First Name" type="text" name="id" value="${pupil.id}" disabled=""/><br />
                                                                </div></td>
                                                        </tr>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Người giám hộ 1 : </h5> <input type="text" name="mother_name" value="${pupil.firstGuardianName}" disabled=""/><br />
                                                                </div></td>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Số điện thoại :</h5> <input type="text" name="mother_phone" value="${pupil.firstGuardianPhoneNumber}" disabled=""/><br />
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">    
                                                                    <h5>Người giám hộ 2 : </h5> <input type="text" name="father_name" value="${pupil.secondGuardianName}" disabled=""/><br />                                                                </div></td>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Số điện thoại :</h5> <input type="text" name="father_phone" value="${pupil.secondGuardianPhoneNumber}" disabled=""/><br />
                                                                </div></td>
                                                        </tr>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Họ tên bé :</h5> <input type="text" name="name_pupil" value="${pupil.lastName} ${pupil.firstName}" disabled=""/><br />
                                                                </div></td>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Ngày sinh của bé : </h5> <input type="date" name="birthday" value="${pupil.birthday}" disabled=""/><br />
                                                                </div></td>
                                                        </tr>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Địa chỉ : </h5> <input type="text" name="address" value="${pupil.address}" style="width: 200%" disabled=""/><br />
                                                                </div></td>
                                                        </tr>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Ghi chú :</h5> <input type="text" name="note" value="${pupil.parentSpecialNote}" style="width: 200%" disabled=""/><br />
                                                                </div></td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                                </br>
                                                <input type="submit" value="Quay Lại" style="width: 20%"/>
                                            </form>
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


        <script>
            function previewAvatar(event) {
                const reader = new FileReader();
                reader.onload = function () {
                    const output = document.getElementById('avatarDisplay');
                    output.src = reader.result;
                };
                reader.readAsDataURL(event.target.files[0]);
            }
        </script>
        <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/main.js"></script>
        <script src="${pageContext.request.contextPath}/js/plugins/pace.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.js"></script>

    </body>

</html>
