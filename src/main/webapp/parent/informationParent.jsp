<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="pupilBean" class="models.pupil.PupilDAO"/>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Cập nhật thông tin</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="../css/main.css">
        <link rel="stylesheet" type="text/css" href="../css/information-style.css">
        <link rel="stylesheet" type="text/css" href="../css/information-css.css">

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

        <script>
            function submitForm() {
                var newPassword = document.getElementById("newPassword").value;
                var confirmPassword = document.getElementById("confirmPassword").value;

                if (newPassword !== confirmPassword) {
                    toastr.error('Mật khẩu không trùng khớp.');
                } else {
                    document.getElementById("changePasswordForm").submit();
                }
            }
        </script>
        <script>
            function validateForm() {
                // Lấy các trường input và textarea cần validate
                var firstGuardianName = document.getElementsByName("first_guardian_name")[0].value.trim();
                var firstGuardianPhone = document.getElementsByName("firstGuardianPhoneNumber")[0].value.trim();
                var secondGuardianName = document.getElementsByName("second_guardian_name")[0].value.trim();
                var secondGuardianPhone = document.getElementsByName("secondGuardianPhoneNumber")[0].value.trim();
                var address = document.getElementsByName("address")[0].value.trim();

                // Kiểm tra nếu các trường bắt buộc không được điền
                if (address === "") {
                    toastr.error("Địa chỉ không được bỏ trống");
                    return false; // Ngăn form submit
                }

                // Kiểm tra hợp lệ cho họ tên người giám hộ thứ nhất
                if (!isValidName(firstGuardianName)) {
                    toastr.error("Họ tên người giám hộ thứ nhất chỉ được nhập chữ cái và không được chứa ký tự đặc biệt");
                    return false; // Ngăn form submit
                }

                // Kiểm tra hợp lệ cho họ tên người giám hộ thứ hai nếu có
                if (secondGuardianName !== "") {
                    if (!isValidName(secondGuardianName)) {
                        toastr.error("Họ tên người giám hộ thứ hai chỉ được nhập chữ cái và không được chứa ký tự đặc biệt");
                        return false; // Ngăn form submit
                    }
                    // Kiểm tra nếu có tên người giám hộ thứ hai thì phải nhập số điện thoại
                    if (secondGuardianPhone === "") {
                        toastr.error("Vui lòng nhập số điện thoại người giám hộ thứ hai");
                        return false; // Ngăn form submit
                    }
                } else if (secondGuardianPhone !== "") {
                    if (secondGuardianName === "") {
                        if (!isValidName(secondGuardianName)) {
                            toastr.error("Họ tên người giám hộ thứ hai chỉ được nhập chữ cái và không được chứa ký tự đặc biệt");
                            return false; // Ngăn form submit
                        }
                    }
                }

                return true;
            }

            function isValidName(name) {
                return /^[A-Za-z\s'.\-àáảãạâầấẩẫậăằắẳẵặèéẻẽẹêềếểễệìíỉĩịòóỏõọôồốổỗộơờớởỡợùúủũụưừứửữựỳỹỷỹỵđĐ]+$/.test(name);
            }

        </script>
        <script>
            function resetForm() {
                document.getElementById('informationUpdate').reset();
            }
        </script>
        <style>


            .button-container {
                display: flex;
                width: 100%;
                justify-content: space-between;
            }

            .button-container div {
                flex: 1;
            }

            .button-container div:first-child {
                text-align: left;
            }

            .button-container div:last-child {
                text-align: right;
            }

        </style>
    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="header-parent.jsp"/>

                    <div class="container">
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h3 class="m-0 font-weight-bold"><i class="fa fa-edit"></i>Chỉnh sửa thông tin</h3>
                            </div>
                        </div>
                        <div class="row gutters">
                            <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <div class="account-settings">
                                            <div class="user-profile">
                                                <div class="user-avatar">
                                                    <c:set var="pupil" value="${pupilBean.getPupilByUserId(sessionScope.pupil.userId)}"/>
                                                    <img src="${pageContext.request.contextPath}/images/${pupil.avatar}" alt="User Avatar">
                                                </div>
                                                <h5 class="user-name">${pupil.lastName} ${pupil.firstName}</h5>
                                                <button type="button" id="submit" name="submit" class="btn btn-primary" data-toggle="modal" data-target="#changePasswordModal">Đổi mật khẩu</button>
                                            </div>

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+"/>
                                        <form action="update" method="post" id="informationUpdate" onsubmit="return validateForm()">
                                            <div class="row gutters">
                                                <p>Chú ý : Những Tiêu Đề Có Dấu (*) Là Những Tiêu Đề Được Chỉnh Sửa</p>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="id">ID Người dùng</label>
                                                        <input class="form-control" placeholder="Mã người dùng" type="text" name="userId" value="${pupil.userId}" disabled style="width: 32%;"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="id">Mã Học Sinh</label>
                                                        <input class="form-control" placeholder="Mã học sinh" type="text" name="id" value="${pupil.id}" disabled style="width: 32%;"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="firstName">Họ tên người giám hộ thứ nhất <a style="color: red">(*)</a></label>
                                                        <input style="width: 70%;" class="form-control" type="text" name="first_guardian_name" value="${pupil.firstGuardianName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,80}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 80 kí tự)" required=""/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="phoneNumber">Số điện thoại người giám hộ thứ nhất <a style="color: red">(*)</a></label>
                                                        <input style="width: 50%;" type="text" class="form-control" placeholder="Số điện thoại" name="firstGuardianPhoneNumber" value="${pupil.firstGuardianPhoneNumber}" pattern="^(0[23578]|09)\d{8}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại" required=""/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="firstName">Họ tên người giám hộ thứ hai <a style="color: red">(*)</a></label>
                                                        <input style="width: 70%;" class="form-control" type="text" name="second_guardian_name" value="${pupil.secondGuardianName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,80}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 80 kí tự)"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="phoneNumber">Số điện thoại người giám hộ thứ hai <a style="color: red">(*)</a></label>
                                                        <input style="width: 50%;" type="text" class="form-control" placeholder="Số điện thoại" name="secondGuardianPhoneNumber" value="${pupil.secondGuardianPhoneNumber}" pattern="^(0[23578]|09)\d{8}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="lastName">Họ tên bé</label>
                                                        <input style="width: 50%;" type="text" class="form-control" placeholder="Họ tên bé" name="name_pupil" value="${pupil.lastName} ${pupil.firstName}" disabled/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="email">Email <a style="color: red">(*)</a></label>
                                                        <input style="width: 60%;" class="form-control" placeholder="email" type="email" name="email" value="${pupil.email}" required=""/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gutters">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="birthDay">Ngày sinh của bé</label>
                                                        <input style="width: 50%;" class="form-control" type="date" name="birthday" value="${pupil.birthday}" disabled/>
                                                    </div>
                                                </div>



                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="address">Địa chỉ <a style="color: red">(*)</a></label>
                                                        <textarea class="form-control" placeholder="Địa chỉ" name="address" rows="2" pattern="^[A-Za-z1-9,${vietnamesePattern}\s]{1,100}$" title="Địa chỉ không được quá 100 kí tự">${pupil.address}</textarea>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="lastName">Tình trạng</label>
                                                        <input style="width: 50%;" class="form-control" type="text" name="status" value="${pupil.status}" disabled/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="address">Ghi chú <a style="color: red">(*)</a></label>
                                                        <textarea class="form-control" placeholder="Ghi chú" name="note" rows="2">${pupil.parentSpecialNote}</textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gutters">
                                                <div class="button-container">
                                                    <div>
                                                        <button type="button" class="btn btn-primary" onclick="history.back()">Quay lại</button>
                                                    </div>
                                                    <div>
                                                        <button type="button" class="btn btn-danger" onclick="resetForm()">Hủy</button>
                                                        <button type="submit" id="submit" name="submit" class="btn btn-primary">Lưu</button>
                                                    </div>

                                                </div>
                                            </div>


                                        </form>
                                        <!-- Password Change Modal -->
                                        <div class="modal fade" id="changePasswordModal" tabindex="-1" role="dialog" aria-labelledby="changePasswordModalLabel" aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-body">
                                                        <form id="changePasswordForm" action="${pageContext.request.contextPath}/new-password" method="post">
                                                            <div class="form-group">
                                                                <label for="oldPassword">Mật khẩu cũ:</label>
                                                                <input type="password" id="oldPassword" name="oldPassword" class="form-control" required>
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="newPassword">Mật khẩu mới:</label>
                                                                <input type="password" id="newPassword" name="newPassword" class="form-control" pattern=".{8,12}" required title="Mật khẩu phải từ 8 đến 12 ký tự">
                                                            </div>
                                                            <div class="form-group">
                                                                <label for="confirmPassword">Xác nhận mật khẩu:</label>
                                                                <input type="password" id="confirmPassword" name="confirmPassword" class="form-control" required>
                                                            </div>
                                                            <button type="button" onclick="submitForm()" class="btn btn-primary">Đổi mật khẩu</button>
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
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>


    </body>

</html>
