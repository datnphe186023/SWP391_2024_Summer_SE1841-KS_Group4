<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                                    <img src="../images/${sessionScope.pupil.avatar}" alt="Maxwell Admin">
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
                                        <form action="update" method="post">
                                            <div class="row gutters">

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
                                                        <label for="firstName">Họ tên người giám hộ thứ nhất *</label>
                                                        <input style="width: 70%;" class="form-control" type="text" name="first_guardian_name" value="${pupil.firstGuardianName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,80}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 80 kí tự)" required=""/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="phoneNumber">Số điện thoại người giám hộ thứ nhất *</label>
                                                        <input style="width: 50%;" type="text" class="form-control" placeholder="Số điện thoại" name="firstGuardianPhoneNumber" value="${pupil.firstGuardianPhoneNumber}" pattern="^(0[23578]|09)\d{8}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại" required=""/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="firstName">Họ tên người giám hộ thứ hai </label>
                                                        <input style="width: 70%;" class="form-control" type="text" name="second_guardian_name" value="${pupil.secondGuardianName}" pattern="^[A-Za-z${vietnamesePattern}\s]{1,80}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 80 kí tự)"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="phoneNumber">Số điện thoại người giám hộ thứ hai</label>
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
                                                        <label for="email">Email *</label>
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
                                                        <label for="address">Địa chỉ *</label>
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
                                                        <label for="address">Ghi chú *</label>
                                                        <textarea class="form-control" placeholder="Ghi chú" name="note" rows="2">${pupil.parentSpecialNote}</textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gutters">
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="text-right">
                                                        <button type="submit" id="submit" name="submit" class="btn btn-primary">Update</button>
                                                    </div>
                                                </div>
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
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>


    </body>

</html>
