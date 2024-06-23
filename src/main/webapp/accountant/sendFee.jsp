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
            function validateInput(event) {
                const input = event.target;
                // Loại bỏ tất cả ký tự không phải là số
                input.value = input.value.replace(/[^0-9]/g, '');
                // Giới hạn tối đa 9 chữ số
                if (input.value.length > 9) {
                    input.value = input.value.slice(0, 9);
                    toastr.error('Học phí không được vượt quá 9 chữ số.');
                }
                input.value = input.value.replace(/\B(?=(\d{3})+(?!\d))/g, '.');
            }

            function validateForm(event) {
                const input = document.getElementById('nextTermFee');
                if (!input.value > 1000000000) {
                    toastr.error('Vui lòng nhập lại.');
                    input.focus();
                    event.preventDefault(); // Ngăn chặn việc gửi form
                } else {
                    // Nếu đã nhập học phí, cập nhật ngày gửi
                    var now = new Date();
                    var day = now.getDate();
                    var month = now.getMonth() + 1;
                    var year = now.getFullYear();
                    var formattedDate = year + '-' + (month < 10 ? '0' + month : month) + '-' + (day < 10 ? '0' + day : day);
                    document.getElementById('submitDate').value = formattedDate;
                    // Loại bỏ dấu chấm trong học phí trước khi gửi form
                    input.value = input.value.replace(/\./g, '');
                }
            }

            function calculateTotal() {
                // Lấy giá trị học phí kỳ tiếp theo và loại bỏ dấu chấm
                const nextTermFee = parseFloat(document.getElementById('nextTermFee').value.replace(/\./g, '')) || 0;
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

                // Cập nhật giá trị tổng học phí với định dạng dấu chấm
                document.getElementById('totalFee').value = totalFee.toString().replace(/\B(?=(\d{3})+(?!\d))/g, '.');
            }

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
                                    <form id="myForm" action="sendfee" method="post" onsubmit="validateForm(event);">
                                        <input type="hidden" name="userid" value="${sessionScope.personnel.id}">
                                        <input type="hidden" id="submitDate" name="submitDate">
                                        <div style="display: flex;">
                                            <div style="flex: 1; padding: 20px;">
                                                <h2 style="background-color: #3b97e0; padding: 10px; color: white;">HỌC PHÍ KỲ TIẾP THEO</h2>
                                                <input name="hocphi" type="text" placeholder="Nhập học phí" id="nextTermFee" oninput="calculateTotal(); validateInput(event);" min="0" required> VNĐ</br>
                                                <label style="margin-top: 10px" for="insuranceFee">CÁC LOẠI PHÍ KHÁC :</label></br>
                                                <input name="baohiem" type="checkbox" class="extraFee" value="500000" onclick="calculateTotal()">PHÍ BẢO HIỂM</br>
                                                <input name="csvatchat" type="checkbox" class="extraFee" value="100000" onclick="calculateTotal()">PHÍ CƠ SỞ VẬT CHẤT</br>
                                                <input name="dongphuc" type="checkbox" class="extraFee" value="400000" onclick="calculateTotal()">PHÍ ĐỒNG PHỤC</br>
                                                <!-- Add more fields as necessary -->
                                            </div>
                                            <div style="flex: 1; padding: 20px;">
                                                <h2 style="background-color: #3b97e0; padding: 10px; color: white;">TỔNG</h2>
                                                <div style="background-color: #d9f1ff; padding: 20px;">
                                                    <input type="text" id="totalFee" value="0" readonly> VNĐ
                                                </div>
                                                <div style="margin-top: 10px">
                                                    <button class="btn btn-success" type="submit">XÁC NHẬN</button>
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
        <!-- Script for handling other form elements and functionalities -->
        <script src="js/jquery-3.2.1.min.js"></script>
        <script src="js/popper.min.js"></script>
        <script src="https://unpkg.com/boxicons@latest/dist/boxicons.js"></script>
        <script src="js/bootstrap.min.js"></script>
        <script src="js/main.js"></script>
        <script src="js/plugins/pace.min.js"></script>
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="../js/demo/datatables-demo.js"></script>
    </body>
</html>
