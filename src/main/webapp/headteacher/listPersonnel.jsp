<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>

<html lang="en">
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
    </head>
    <body>
        <jsp:include page="dashboard.jsp"/>

        <main class="app-content">
            <div class="row justify-content-center">
                <span class="bg-secondary font-weight-bold rounded-lg" id="style-span">Danh sách Nhân viên </span>
            </div>

            <div class="row">
                <%--  Begin : Select item      --%>
                <c:set var="sltedrole" value="${requestScope.selectedrole}"/>
                <c:set var="sltedstatus" value="${requestScope.selectedstatus}"/>

                <div class="col-lg-4" style="display: flex; justify-content: space-between">
<form action="listpersonnel" method="post" > 
                    <div class="class-form">
                           
                        <label>Chức vụ</label>
                            <select name="role" onchange="this.form.submit()" class="custom-select">
                                <option value="" hidden >Chức vụ</option>
                                <c:forEach items="${requestScope.roles}" var="r">
                                    <option ${sltedrole eq r.getId() ? "selected" : ""} value="${r.getId()}">${r.getVNeseDescription()}</option> 
                                </c:forEach>
                                <c:if test="${sltedrole eq 'all'}">
                                        <option value="all" selected>Hiện toàn bộ </option>
                                     </c:if>
                                      <c:if test="${sltedrole ne'all'}">
                                        <option value="all" >Hiện toàn bộ </option>
                                     </c:if>
                            </select>
                              
                    </div>

                    <div class="class-form">
                          
                        <label>Trạng thái</label>
                            <select name="status" onchange="this.form.submit()" class="custom-select">
                                <option value="" hidden >Trạng thái</option>
                                <c:forEach items="${requestScope.statuss}" var="r">
                                    <option ${sltedstatus eq r ? "selected" : ""} value="${r}">${r}</option> 
                                </c:forEach>
                                    <c:if test="${sltedstatus eq 'all'}">
                                        <option value="all" selected>Hiện toàn bộ </option>
                                     </c:if>
                                      <c:if test="${sltedstatus ne'all'}">
                                        <option value="all" >Hiện toàn bộ </option>
                                     </c:if>
                            </select>
                                
                    </div>
</form>
                    
                    <style>
                        .btn {
                            margin:  0px 0px 0px 10px;
                        }
                    </style>   

                </div>
                <div class="col-lg-4" >
                    <form action="listpersonnel" method="post" >   
                    <div class="search-field">
                        <div class="form-group has-search">
                           <span style="margin-top: 5px" class="fa fa-search form-control-feedback"></span>
                            <input  style="border-radius: 30px" type="text" class="form-control" placeholder="Nhập Tên hoặc ID " name="search" >    
                       </div>
                    </div>
                     </form>
                     </div> 
                <%--End : Select item    --%>
                <div class="col-lg-4" style="display: flex; justify-content: end">
                  

                    <div>
                        <button class="btn btn-add" >
                            <a  href="waitlistpersonnel">ĐANG CHỜ PHÊ DUYỆT (${requestScope.waitlist.size()})</a>   
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

            <div class="row">
                <table class="table table-bordered">
                    <thead>
                        <tr>
                            <th scope="col">STT</th>
                            <th scope="col">Ảnh</th>
                            <th scope="col">Mã Nhân Viên</th>
                            <th scope="col">Tên</th>
                            <th scope="col">Giới Tính</th>
                            <th scope="col">Ngày sinh</th>
                            <th scope="col">Chức vụ</th>
                            <th scope="col">Trạng thái</th>
                            <th scope="col">Chi tiết</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:set var="count" value="0" />



                        <c:forEach items="${persons}" var="p">
                            <c:set var="count" value="${count + 1}" />      
                            <tr>
                                <td>${count}</td>
                                <td><img class="profile_img" src="../images/${p.getAvatar()}" alt="ảnh nhân viên" width="191px" height="263px" object-fit: cover></td>
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


                                <td>${p.getStatus()} </td>
                                <td>
                                    <form action="viewpersonnel" method="get">
                                        <input type="hidden" value="${p.getId()}" name="id">  
                                        <button class="btn btn-info" type="submit">Xem thông tin chi tiết</button>   
                                    </form>

                                </td>
                            </tr>
                        </c:forEach>

                    </tbody>
                </table>
            </div>
            

        </main>






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
    </body>
</body>

</html>
