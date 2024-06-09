<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Quản lý Nhân sự</title>
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
    <!-- Custom styles for this page -->
    <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body>
<div id="wrapper">
    <jsp:include page="navbar.jsp"/>
    <div id="content-wrapper" class="d-flex flex-column">
        <div id="content">
            <jsp:include page="../header.jsp"/>
            <div class="container-fluid">
                <h1 class="h3 mb-4 text-gray-800 text-center">Danh Sách Nhân Viên </h1>

                <div class="row">
                    <%--  Begin : Select item      --%>
                    <c:set var="sltedrole" value="${requestScope.selectedrole}"/>
                    <c:set var="sltedstatus" value="${requestScope.selectedstatus}"/>
                    <c:set var="vietnamesePattern"
                           value="ĐđaáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸỴ"/>
                    <div class="col-lg-4">
                        <form action="listpersonnel" method="post">
                            <div style="display: flex; justify-content: space-between">
                                <div class="class-form">

                                    <label> Chức vụ
                                        <select name="role" onchange="this.form.submit()" class="custom-select">
                                            <option value="" hidden>Chức vụ</option>
                                            <c:forEach items="${requestScope.roles}" var="r">
                                                <option ${sltedrole eq r.getId() ? "selected" : ""}
                                                        value="${r.getId()}">${r.getVNeseDescription()}</option>
                                            </c:forEach>
                                            <c:if test="${sltedrole eq 'all'}">
                                                <option value="all" selected>Hiện toàn bộ</option>
                                            </c:if>
                                            <c:if test="${sltedrole ne'all'}">
                                                <option value="all">Hiện toàn bộ</option>
                                            </c:if>
                                        </select>
                                    </label>
                                </div>

                                <div class="class-form">

                                    <label>Trạng thái
                                        <select name="status" onchange="this.form.submit()" class="custom-select">
                                            <option value="" hidden>Trạng thái</option>
                                            <c:forEach items="${requestScope.statuss}" var="r">
                                                <option ${sltedstatus eq r ? "selected" : ""} value="${r}">${r}</option>
                                            </c:forEach>
                                            <c:if test="${sltedstatus eq 'all'}">
                                                <option value="all" selected>Hiện toàn bộ</option>
                                            </c:if>
                                            <c:if test="${sltedstatus ne'all'}">
                                                <option value="all">Hiện toàn bộ</option>
                                            </c:if>
                                        </select>
                                    </label>
                                </div>
                            </div>
                        </form>

                        <style>
                            .btn {
                                margin: 0px 0px 0px 10px;
                            }
                        </style>

                    </div>
                    <div class="col-lg-4">

                    </div>
                    <%--End : Select item    --%>
                    <div class="col-lg-4" style="display: flex; justify-content: end">
                        <div>
                            <button class="btn btn-primary" >
                                <a style="color:white "  href="waitlistpersonnel">ĐANG CHỜ PHÊ DUYỆT (${requestScope.waitlist.size()})</a>
                            </button>
                        </div>


                    </div>
                </div>


                <style>
                    /* Hide the placeholder in the dropdown options */
                    option[hidden] {
                        display: none;
                    }
                </style>


                <div class="card shadow mb-4">
                    <div class="card-header py-3">
                        <h6 class="m-0 font-weight-bold text-primary">Danh Sách Nhân Viên</h6>
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
                                <c:forEach items="${persons}" var="p" varStatus="status">
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
                                                Admin
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
                                                    <td style=" color: #4fff33;">${p.getStatus()}</td>
                                                </c:when>
                                                <c:when test="${p.getStatus() == 'đang chờ xử lý'}">
                                                    <td style=" color: #ff2848;">${p.getStatus()}</td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td style="color: #4c67ff;">${p.getStatus()}</td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <td>
                                            <!--    <form action="viewpersonnel" method="get">
                                                        <input type="hidden" value="${p.getId()}" name="id">
                                                        <button class="btn btn-dark" type="submit">Xem thông tin chi tiết</button>
                                                    </form>
                                                -->
                                            <a href="viewpersonnel?id=${p.getId()}"
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

                <!--modal for new personnel-->
                <div class="modal fade" id="newPersonnelModal" tabindex="-1" role="dialog" aria-hidden="true" data-backdrop="static"
                     data-keyboard="false">
                    <form action="createpersonnel?action=create" method="POST">
                        <div class="modal-dialog modal-dialog-centered" role="document">
                            <div class="modal-content">
                                <div class="modal-body">
                                    <div class="row">
                                        <div class="form-group col-md-12">
                                        <span class="thong-tin-thanh-toan">
                                            <h5>Tạo Nhân Viên Mới</h5>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Chức vụ </label>
                                            <select class="form-control" name="role" id="role" required>
                                                <option value="" hidden selected>Chọn chức vụ</option>
                                                <option value="0">Admin</option>
                                                <option value="1">Hiệu trưởng</option>
                                                <option value="2">Giáo Vụ</option>
                                                <option value="3">Nhân viên kế toán</option>
                                                <option value="4">Giáo viên</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Tên </label>
                                            <input class="form-control" type="text" name="firstname"
                                                   pattern="^[a-zA-Z${vietnamesePattern}\s]{1,50}$" required>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Họ</label>
                                            <input class="form-control" type="text" name="lastname"
                                                   pattern="^[A-Za-z${vietnamesePattern}\s]{1,20}$" required>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Giới tính </label>
                                            <select class="form-control" name="gender" id="gender" required>
                                                <option value="" hidden selected>Chọn giới tính</option>
                                                <option value="0">Nữ</option>
                                                <option value="1">Nam</option>
                                            </select>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Ngày sinh</label>
                                            <input class="form-control" type="date" name="birthday" required>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Địa chỉ</label>
                                            <input class="form-control" type="text" name="address"
                                                   pattern="^[A-Za-z1-9,${vietnamesePattern}\s]{1,100}$" required>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Email</label>
                                            <input class="form-control" type="email" name="email" required>
                                        </div>
                                        <div class="form-group col-md-6">
                                            <label class="control-label">Số điện thoại</label>
                                            <input class="form-control" type="text" pattern="^(0[23578]|09)\d{8}$" name="phone"
                                                   required>


                                        </div>
                                        <div class="form-group col-md-12">
                                            <label class="control-label">Ảnh :</label>
                                            <div class="form-group col-md-12">

                                                <div id="myfileupload">
                                                    <input type="file" id="uploadfile" name="avatar" onchange="readURL(this);"
                                                           required/>
                                                </div>
                                                <div id="thumbbox">
                                                    <img height="200" width="200" alt="Thumb image" id="thumbimage"
                                                         style="display: none"/>
                                                    <a class="removeimg" href="javascript:"></a>
                                                </div>


                                            </div>
                                        </div>
                                    </div>
                                    <br>
                                    <button class="btn btn-success" type="submit">Lưu lại</button>
                                    <a class="btn btn-danger" data-dismiss="modal" href="#">Hủy bỏ</a>
                                    <br>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <jsp:include page="../footer.jsp"/>
    </div>
</div>


<script src="js/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
<script src="js/popper.min.js"></script>
<script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
<!--===============================================================================================-->
<script src="js/bootstrap.min.js"></script>
<!--===============================================================================================-->
<script src="js/main.js"></script>
<!--===============================================================================================-->
<script src="js/plugins/pace.min.js"></script>
<!--===============================================================================================-->
<!--===============================================================================================-->
<script>
    document.getElementById('role').addEventListener('change', function () {
        this.querySelector('option[hidden]').disabled = true;
    });
</script>
<script>

    function redirect() {
        window.location.href = "listpersonnel";
    }
</script>
<script>
    document.getElementById('role').addEventListener('change', function () {
        this.querySelector('option[hidden]').disabled = true;
    });
    document.getElementById('id').addEventListener('change', function () {
        this.querySelector('option[hidden]').disabled = true;
    });
</script>
<script>

    function readURL(input, thumbimage) {
        if (input.files && input.files[0]) { //Sử dụng  cho Firefox - chrome
            var reader = new FileReader();
            reader.onload = function (e) {
                $("#thumbimage").attr('src', e.target.result);
            }
            reader.readAsDataURL(input.files[0]);
        } else { // Sử dụng cho IE
            $("#thumbimage").attr('src', input.value);

        }
        $("#thumbimage").show();
        $('.filename').text($("#uploadfile").val());
        $('.Choicefile').css('background', '#14142B');
        $('.Choicefile').css('cursor', 'default');
        $(".removeimg").show();
        $(".Choicefile").unbind('click');

    }

    $(document).ready(function () {
        $(".Choicefile").bind('click', function () {
            $("#uploadfile").click();

        });
        $(".removeimg").click(function () {
            $("#thumbimage").attr('src', '').hide();
            $("#myfileupload").html('<input type="file" id="uploadfile"  onchange="readURL(this);" />');
            $(".removeimg").hide();
            $(".Choicefile").bind('click', function () {
                $("#uploadfile").click();
            });
            $('.Choicefile').css('background', '#14142B');
            $('.Choicefile').css('cursor', 'pointer');
            $(".filename").text("");
        });
    });
</script>
<script>
    const inpFile = document.getElementById("inpFile");
    const loadFile = document.getElementById("loadFile");
    const previewContainer = document.getElementById("imagePreview");
    const previewContainer = document.getElementById("imagePreview");
    const previewImage = previewContainer.querySelector(".image-preview__image");
    const previewDefaultText = previewContainer.querySelector(".image-preview__default-text");
    const object = new ActiveXObject("Scripting.FileSystemObject");
    inpFile.addEventListener("change", function () {
        const file = this.files[0];
        if (file) {
            const reader = new FileReader();
            previewDefaultText.style.display = "none";
            previewImage.style.display = "block";
            reader.addEventListener("load", function () {
                previewImage.setAttribute("src", this.result);
            });
            reader.readAsDataURL(file);
        }
    });


</script>
<!-- Page level plugins -->
<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

<!-- Page level custom scripts -->
<script src="../js/demo/datatables-demo.js"></script>
</body>
</html>
