<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
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
            input[readonly] {
                background-color: #e9ecef; /* Màu nền của trường readonly */
                cursor: not-allowed;      /* Con trỏ chỉ vào trường readonly */
                border: 1px solid #ced4da; /* Viền của trường readonly */
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
                        <!-- Head Teacher Information Section -->
                        <main>
                            <div class="app-title">
                                <div>
                                    <h1><i class="fa fa-edit"></i>CHỈNH SỬA TÀI KHOẢN</h1>
                                </div>
                            </div>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="tile">
                                        <div class="tile-body">
                                            <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+"/>
                                            <form action="userprofile" method="post">
                                                <table>
                                                    <tbody>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>USER ID :</h5><input placeholder="USER ID" type="text" name="userId" value="${user.getId()}" readonly=""/>
                                                                </div></td>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>USER NAME : </h5> <input placeholder="USER NAME" type="text" name="userName" value="${user.getUsername()}" readonly=""/><br />
                                                                </div></td>
                                                        </tr>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>VAI TRÒ : ${roleMap[user.getRoleId()]}</h5> 
                                                                    <select name="role">
                                                                        <option value="0">Admin</option>
                                                                        <option value="1">Head Teacher</option>
                                                                        <option value="2">Academic Staff</option>
                                                                        <option value="3">Accountant</option>
                                                                        <option value="4">Teacher</option>
                                                                        <option value="5">Parent</option>
                                                                    </select><br />
                                                                </div></td>
                                                            <td>
                                                                <div class="form-group col-md-12">
                                                                    <h5>Email :</h5> <input style="width: 200%" type="email" name="email" value="${user.getEmail()}" /><br />
                                                                </div>
                                                            </td>
                                                        </tr>
                                                        <tr>
                                                            <td><div class="form-group col-md-12">
                                                                    <h5>Trạng Thái : ${roleDis[user.getIsDisabled()]} </h5> 
                                                                    <select name="active">
                                                                        <option value="0">Active</option>
                                                                        <option value="1">Disable</option>
                                                                    </select><br />
                                                                </div></td>

                                                        </tr>                        
                                                    </tbody>
                                                </table>
                                                <div class="form-buttons">
                                                    <button type="button" class="cancel-btn" onclick="cancelAction()">Quay Lại</button>
                                                    <button type="submit" class="update-btn">Chỉnh Sửa</button></a>
                                                </div>
                                            </form>
                                            <!-- Password Change Modal -->
                                            <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
                                                <div class="modal-dialog" role="document">
                                                    <div class="modal-content">

                                                        <div class="modal-body">
                                                            <form action="${pageContext.request.contextPath}/new-password" method="post">
                                                                <div class="form-group">
                                                                    <label for="oldPassword">Mật khẩu cũ:</label>
                                                                    <input type="password" id="oldPassword" name="oldPassword" class="form-control" required>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="newPassword">Mật khẩu mới:</label>
                                                                    <input type="password" id="newPassword" name="newPassword" class="form-control" required>
                                                                </div>
                                                                <div class="form-group">
                                                                    <label for="confirmPassword">Xác nhận mật khẩu:</label>
                                                                    <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                                                                </div>
                                                                <input type="submit" value="Đổi mật khẩu" class="btn btn-primary">
                                                            </form>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- End Password Change Modal -->

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
