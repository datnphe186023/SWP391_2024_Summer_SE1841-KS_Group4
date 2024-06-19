<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <title>TẠO THÔNG BÁO HỌC PHÍ KÌ TIẾP THEO</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/toastr.min.js"></script>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
            function calculateTotal() {
                // Lấy giá trị học phí kỳ tiếp theo
                const nextTermFee = parseFloat(document.getElementById('nextTermFee').value) || 0;

                // Tính tổng các loại phí khác
                const extraFees = document.getElementsByClassName('extraFee');
                let extraTotal = 0;
                for (let i = 0; i < extraFees.length; i++) {
                    if (extraFees[i].checked) {
                        extraTotal += parseFloat(extraFees[i].value);
                    }
                }

                // Tính tổng học phí
                const totalFee = nextTermFee + extraTotal;

                // Cập nhật giá trị tổng học phí
                document.getElementById('totalFee').value = totalFee;
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
                        <h1 class="h3 mb-4 text-gray-800 text-center">TẠO THÔNG BÁO HỌC PHÍ KÌ TIẾP THEO</h1>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h6 class="m-0 font-weight-bold text-primary"></h6>
                            </div>

                            <div class="card-body">
                                <div class="table-responsive">
                                    <form id="myForm" action="sendfee" method="post">
                                        <input type="hidden" name="userid" value="${sessionScope.personnel.id}">
                                        <input type="hidden" id="submitDate" name="submitDate">
                                        <div style="display: flex;">
                                            <div style="flex: 1; padding: 20px;">
                                                <h2 style="background-color: #3b97e0; padding: 10px; color: white;">HỌC PHÍ KỲ TIẾP THEO</h2>
                                                <input name="hocphi" type="number" placeholder="0" id="nextTermFee" oninput="calculateTotal()" required=""> VNĐ</br>
                                                <label style="margin-top: 10px" for="insuranceFee">CÁC LOẠI PHÍ KHÁC :</label></br>
                                                <input name="baohiem" type="checkbox" class="extraFee" value="500000" onclick="calculateTotal()">PHÍ BẢO HIỂM</br>
                                                <input name="csvatchat" type="checkbox" class="extraFee" value="100000" onclick="calculateTotal()">PHÍ CƠ SỞ VẬT CHẤT</br>
                                                <input name="dongphuc" type="checkbox" class="extraFee" value="400000" onclick="calculateTotal()">PHÍ ĐỒNG PHỤC</br
                                                <!-- Add more fields as necessary -->
                                            </div>
                                            <div style="flex: 1; padding: 20px;">
                                                <h2 style="background-color: #3b97e0; padding: 10px; color: white;">TỔNG</h2>
                                                <div style="background-color: #d9f1ff; padding: 20px;">
                                                    <input type="number" id="totalFee" value="0" readonly> VNĐ
                                                </div>
                                                <div style="margin-top: 10px">
                                                    <button class="btn btn-success" type="submit" onclick="submitForm()">XÁC NHẬN</button>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp"/>
            </div>
        </div>
        <script>
            function submitForm() {
                var now = new Date();

                // Chuyển múi giờ sang múi giờ Việt Nam (GMT+7)
                now.setHours(now.getHours());

                // Lấy ngày tháng năm
                var day = now.getDate();
                var month = now.getMonth() + 1; // Tháng trong JavaScript bắt đầu từ 0
                var year = now.getFullYear();

                // Định dạng thành chuỗi YYYY-MM-DD
                var formattedDate = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);

                // Gán giá trị cho input có id là submitDate
                document.getElementById('submitDate').value = formattedDate;

                // Submit form
                document.getElementById("myForm").submit();
            }
        </script>

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
