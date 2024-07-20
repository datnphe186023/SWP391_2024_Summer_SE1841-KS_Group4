<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
                                                <option value="all" selected>Hiện toàn bộ chức vụ</option>
                                            </c:if>
                                            <c:if test="${sltedrole ne'all'}">
                                                <option value="all">Hiện toàn bộ chức vụ</option>
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
                                                <option value="all" selected>Hiện toàn bộ trạng thái</option>
                                            </c:if>
                                            <c:if test="${sltedstatus ne'all'}">
                                                <option value="all">Hiện toàn bộ trạng thái</option>
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
                                        <td><fmt:formatDate value="${p.getBirthday()}" pattern="dd/MM/yyyy"/> </td>
                                        <td>
                                            <c:if test="${p.getRoleId()== 0}">
                                                Nhân viên IT
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
                                                    <td >
                                                        <span class="badge badge-success">${p.getStatus()}</span>
                                                    </td>
                                                </c:when>
                                                <c:when test="${p.getStatus() == 'đang chờ xử lý'}">
                                                    <td>
                                                        <span class="badge badge-warning">${p.getStatus()}</span>
                                                    </td>
                                                </c:when>
                                                <c:otherwise>
                                                    <td >
                                                        <span class="badge badge-info">${p.getStatus()}</span>
                                                    </td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <td>
                                            <!--    <form action="viewpersonnel" method="get">
                                                        <input type="hidden" value="${p.getId()}" name="id">
                                                        <button class="btn btn-dark" type="submit">Xem thông tin chi tiết</button>
                                                    </form>
                                                -->
                                            <a href="viewpersonnel?id=${p.getId()}&page=list"
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
