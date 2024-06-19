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
            input[readonly] {
                background-color: wheat; /* Màu nền giống với disabled */
                color: black; /* Màu chữ giống với disabled */
                cursor: not-allowed; /* Con trỏ chuột giống với disabled */
                border: 1px solid #ddd; /* Đường viền nhẹ */
            }

            /* Bỏ bóng và đường viền khi focus cho giống với disabled */
            input[readonly]:focus {
                outline: none;
                box-shadow: none;
            }

            /* Ngăn không cho tương tác */
            input[readonly] {
                pointer-events: none; /* Ngăn không cho tương tác */
            }
            textarea[readonly] {
                background-color: wheat; /* Màu nền giống với disabled */
                color: black; /* Màu chữ giống với disabled */
                cursor: not-allowed; /* Con trỏ chuột giống với disabled */
                border: 1px solid #ddd; /* Đường viền nhẹ */
            }

            /* Bỏ bóng và đường viền khi focus cho giống với disabled */
            textarea[readonly]:focus {
                outline: none;
                box-shadow: none;
            }

            /* Ngăn không cho tương tác */
            textarea[readonly] {
                pointer-events: none; /* Ngăn không cho tương tác */
            }
        </style>

    </head>
    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp"/>
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp"/>

                    <div class="container">
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h3 class="m-0 font-weight-bold"><i class="fa fa-edit"></i>CHỈNH SỬA HỌC SINH</h3>
                            </div>
                        </div>
                        <div class="row gutters">
                            <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <div class="account-settings">
                                            <div class="user-profile">
                                                <div class="user-avatar">
                                                    <img class="app-sidebar__user-avatar" id="avatarDisplay" src="../images/${pupil.avatar}" >
                                                </div>
                                                <h5 class="user-name">${pupil.lastName} ${pupil.firstName}</h5>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+"/>
                                        <form action="updatepupils" method="get" onsubmit="return validateForm()">
                                            <input type="hidden" value="${personnel.userId}"/>
                                            <table>
                                                <tbody>
                                                    <tr>
                                                        <td><div class="form-group col-md-12">
                                                                <h5>ID Người Dùng :</h5><input value="${pupil.userId!=null?pupil.userId:"Chưa có tài khoản"}" type="text" name="userId" readonly=""/>
                                                            </div></td>
                                                        <td><div class="form-group col-md-6">
                                                                <h5>ID : </h5> <input value="${pupil.id}" type="text" name="id" readonly=""/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Họ tên người giám hộ thứ nhất<a style="color: red">(*)</a>  : </h5> <input type="text" name="first_guardian_name" placeholder="${pupil.firstGuardianName}" pattern="^[A-Za-z,${vietnamesePattern}\s]{1,20}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)" required=""/><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">

                                                                <h5>Số điện thoại người giám hộ thứ nhất<a style="color: red">(*)</a>  :</h5> <input type="text" name="firstGuardianPhoneNumber" placeholder="${pupil.firstGuardianPhoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại." required=""/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Họ tên người giám hộ thứ hai<a style="color: red">(*)</a>  : </h5> <input type="text" name="second_guardian_name" placeholder="${pupil.secondGuardianName}" pattern="^[A-Za-z,${vietnamesePattern}\s]{30}$" title="Họ và tên không được chứa số hoặc kí tự đặc biệt (Tối đa 20 kí tự)" /><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-8">
                                                                <h5>Số điện thoại người giám hộ thứ hai<a style="color: red">(*)</a>  :</h5> <input type="text" name="secondGuardianPhoneNumber" placeholder="${pupil.secondGuardianPhoneNumber}" pattern="^0\d{9}$" title="Số điện thoại không hợp lệ vui lòng kiểm tra lại." /><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-6">    
                                                                <h5>Họ tên bé :</h5> <input type="text" name="name_pupil" value="${pupil.lastName} ${pupil.firstName}" readonly="" /><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-12">
                                                                <h5>Ngày sinh của bé : </h5> <input type="date" name="birthday" value="${pupil.birthday}" readonly=""/><br />
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                        <td><div class="form-group col-md-6">
                                                                <h5>Địa chỉ<a style="color: red">(*)</a>  : </h5> <textarea type="text" name="address" placeholder="${pupil.address}" style="width: 200%"  title="Địa chỉ không được quá 100 kí tự" required=""></textarea><br />
                                                            </div></td>
                                                        <td><div class="form-group col-md-6">
                                                                <h5>Ghi chú<a style="color: red">(*)</a>  :</h5> <textarea type="text" name="note" style="width: 200%" placeholder="${pupil.parentSpecialNote}"></textarea><br/>
                                                            </div></td>
                                                    </tr>
                                                    <tr>
                                                <p>Chú ý : Những Tiêu Đề Có Dấu (*) Là Những Tiêu Đề Được Chỉnh Sửa.</p>
                                                </tr>
                                                </tbody>
                                            </table>
                                            <div class="form-buttons">
                                                <button type="button" class="btn btn-danger" onclick="cancelAction()">Quay Lại</button>
                                                <button type="submit" class="btn btn-success">Lưu</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <script>
            function cancelAction() {
                window.history.back();
            }
            function validateForm() {
                const firstGuardianName = document.getElementsByName('first_guardian_name')[0];
                const secondGuardianName = document.getElementsByName('second_guardian_name')[0];
                const address = document.getElementsByName('address')[0];
                const note = document.getElementsByName('note')[0];

                // Trim leading and trailing whitespace
                firstGuardianName.value = firstGuardianName.value.trim();
                secondGuardianName.value = secondGuardianName.value.trim();
                address.value = address.value.trim();
                note.value = note.value.trim();

                // Biểu thức chính quy để kiểm tra tên
                const namePattern = /^[A-Za-zÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯăạảấầẩẫậắằẳẵặẹẻẽếềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹ\s]+$/;
                const addressPattern = /^[A-Za-z1-9,ÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚĂĐĨŨƠàáâãèéêìíòóôõùúăđĩũơƯăạảấầẩẫậắằẳẵặẹẻẽếềểễệỉịọỏốồổỗộớờởỡợụủứừửữựỳỵỷỹ\s]{1,100}$/;
                // Kiểm tra họ tên người giám hộ thứ nhất
                if (firstGuardianName.value === "" || !namePattern.test(firstGuardianName.value) || firstGuardianName.value.length > 30) {
                    alert("Họ tên người giám hộ thứ nhất không được bỏ trống và không chứa số hoặc ký tự đặc biệt và không quá 30 kí tự. Vui lòng nhập lại!");
                    firstGuardianName.focus();
                    return false;
                }
                if (!namePattern.test(secondGuardianName.value) || secondGuardianName.value.length > 30) {
                    alert("Họ tên người giám hộ thứ hai không chứa số hoặc ký tự đặc biệt và không quá 30 kí tự. Vui lòng nhập lại!");
                    secondGuardianName.focus();
                    return false;
                }

                // Validate address
                if (address.value === "" || !addressPattern.test(address.value) || address.value.length > 100) {
                    alert("Địa chỉ không được bỏ trống, không chứa ký tự đặc biệt ngoài dấu phẩy và không quá 100 ký tự. Vui lòng nhập lại!");
                    address.focus();
                    return false;
                }

                return true;
            }
        </script>
    </body>
</html>
