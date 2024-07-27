<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Danh Sách Học Sinh</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Test CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" href="https://unpkg.com/boxicons@latest/css/boxicons.min.css">
        <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/sweetalert/2.1.2/sweetalert.min.js"></script>
        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.8.2/css/all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.2/jquery-confirm.min.css">
        <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
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
            .center-text {
                text-align: center;
            }
            .modal-header {
                text-align: center;
            }
            .modal-title {
                margin: 0 auto;
                display: inline-block;
            }
            .modal-lg {
                max-width: 80%;
            }
            .form-group input, .form-group textarea {
                width: 100%;
            }
        </style>
        <script>
            function submitForm() {
                document.getElementById("myForm").submit();
            }
        </script>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp" />
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp" />
                    <div class="container-fluid">
                        <h1 class="h3 mb-4 text-gray-800 text-center">Báo cáo sức khỏe</h1>
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <div class="row">
                                    <div class="col-md-4">
                                        <button class="btn btn-success" data-toggle="modal" data-target="#healthReportModal">
                                            <i class="fas fa-fw fa-plus"></i> Thêm báo cáo sức khỏe
                                        </button>
                                    </div>
                                </div>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                                        <thead>
                                            <tr>
                                                <th>STT</th>
                                                <th>Mã kiểm tra sức khỏe</th>
                                                <th>Ngày kiểm tra</th>
                                                <th>Hành động</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        <div style="color: red">${requestScope.error}</div>
                                        <c:forEach var="pupilHealth" items="${requestScope.listHealthCheckUp}" varStatus="status">
                                            <tr>
                                                <th scope="row">${status.index + 1}</th>
                                                <td>${pupilHealth.id}</td>
                                                <td><fmt:formatDate value="${pupilHealth.checkUpDate}" pattern="yyyy/MM/dd"/> </td>
                                                <td><a href="viewHealthCheckUp?healthId=${pupilHealth.id}" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">Chi tiết</a></td>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                                        
                        <div class="btn-group-right float-right">
                            <a href="list-pupil" class="btn btn-primary">Quay lại</a>
                        </div>
                    </div>
                    <!-- Health Report Modal -->
                    <div class="modal fade" id="healthReportModal" tabindex="-1" role="dialog" aria-labelledby="healthReportModalLabel" aria-hidden="true">
                        <div class="modal-dialog modal-lg" role="document">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="healthReportModalLabel"><strong>Thêm báo cáo sức khỏe của bé ${requestScope.pupil.lastName} ${requestScope.pupil.firstName}</strong></h5>
                                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                        <span aria-hidden="true">&times;</span>
                                    </button>
                                </div>
                                <c:set var="vietnamesePattern" value="aáàảãạâấầẩẫậăắằẳẵặeéèẻẽẹêếềểễệiíìỉĩịoóòỏõọôốồổỗộơớờởỡợuúùủũụưứừửữựyýỳỷỹỵAÁÀẢÃẠÂẤẦẨẪẬĂẮẰẲẴẶEÉÈẺẼẸÊẾỀỂỄỆIÍÌỈĨỊOÓÒỎÕỌÔỐỒỔỖỘƠỚỜỞỠỢUÚÙỦŨỤƯỨỪỬỮỰYÝỲỶỸ\s]+" />
                                <form action="addHealthReport" method="post" id="healthReportForm" onsubmit="return validateDate();">
                                    <div class="modal-body">
                                        <div class="row">

                                            <div class="col-md-6">
                                                <div class="form-group">
                                                    <label for="pupil_id"><strong>ID Học sinh</strong></label>
                                                    <input class="form-control" type="text" name="pupil_id_display" value="${requestScope.pupil.id}" disabled style="width: 36%" />
                                                    <input type="hidden" name="pupil_id" value="${requestScope.pupil.id}" />
                                                    <input type="hidden" name="schoolYear" value="${requestScope.schoolYear.id}" />
                                                </div>
                                                <div class="form-group">
                                                    <label for="height"><strong>Chiều cao (cm) <a style="color: red">(*)</a></strong></label>
                                                    <input type="text" class="form-control" placeholder="Chiều cao" name="height" pattern="^\s*([3-9]\d|1\d{2}|200)(\.\d)?\s*$" title="Chiều cao không hợp lệ vui lòng kiểm tra lại!" style="width: 50%" required >
                                                </div>
                                                <div class="form-group">
                                                    <label for="weight"><strong>Cân nặng (kg) <a style="color: red">(*)</a></strong></label>
                                                    <input type="text" class="form-control" placeholder="Cân nặng" name="weight" title="Cân nặng không hợp lệ vui lòng kiểm tra lại!" style="width: 50%" required pattern="^\s*(0?[1-9]|[1-9][0-9]|100)(\.\d)?\s*$">
                                                </div>
                                                <div class="form-group">
                                                    <label for="average_development_stage"><strong>Giai đoạn phát triển <a style="color: red">(*)</a></strong></label>
                                                    <input class="form-control" placeholder="Giai đoạn phát triển (trung bình,...)" type="text" name="average_development_stage" pattern="^[A-Za-z1-9 ${vietnamesePattern},\\s]{1,100}$" title="Giai đoạn phát triển không được chứa kí tự đặc biệt hoặc vượt quá 100 kí tự!" style="width: 70%" required />
                                                </div>
                                                <div class="form-group">
                                                    <label for="blood_pressure"><strong>Huyết áp <a style="color: red">(*)</a></strong></label>
                                                    <input type="text" placeholder="Huyết áp (.. / ..)" class="form-control" name="blood_pressure" title="Chỉ số huyết áp không hợp lệ vui lòng kiểm tra lại!" style="width: 60%" required pattern="^\s*(1?[0-9]{2})/(1?[0-9]{2})\s*$">
                                                </div>
                                            </div>
                                            <div class="col-md-6">

                                                <div class="form-group">
                                                    <label for="teeth"><strong>Răng <a style="color: red">(*)</a></strong></label>
                                                    <input type="text" placeholder="Răng (tốt,...)" class="form-control" name="teeth" title="Kết quả đánh giá không được chứa kí tự đặc biệt hoặc vượt quá 30 kí tự!
                                                           " pattern="^[A-Za-z1-9 ${vietnamesePattern},\\s]{1,30}$" style="width: 60%" required />
                                                </div>
                                                <div class="form-group">
                                                    <label for="eyes"><strong>Mắt <a style="color: red">(*)</a></strong></label>
                                                    <input type="text" placeholder="Mắt (.. / ..)" class="form-control" name="eyes" title="Kết quả đánh giá không được chứa kí tự đặc biệt hoặc vượt quá kí tự cho phép!" required style="width: 60%" pattern="^\s*(1?[0-9])/(1?[0-9])\s*$">
                                                </div>
                                                <div class="form-group">
                                                    <label for="ear_nose_throat"><strong>Tai mũi họng <a style="color: red">(*)</a></strong></label>
                                                    <input type="text" class="form-control" placeholder="Tai mũi họng (tốt,...)" pattern="^[A-Za-z1-9 ${vietnamesePattern},\\s]{1,30}$" title="Thông số không được để trống , chứa kí tự đặc biệt hoặc vượt quá kí tự cho phép!
                                                           " name="ear_nose_throat" style="width:60%" required />
                                                </div>
                                                <div class="form-group">
                                                    <label for="notes"><strong>Ghi chú <a style="color: red">(*)</a></strong></label>
                                                    <textarea class="form-control" placeholder="Ghi chú" name="note" pattern="^[A-Za-z1-9 ${vietnamesePattern},\\s]{1,1000}$" title="Ghi chú không hợp lệ vui lòng kiểm tra lại!" name="notes" rows="5" required></textarea>
                                                </div>
                                            </div>
                                            <p class="form-group"> <Strong>Chú ý</strong> : Những Tiêu Đề Có Dấu <a style="color: red">(*)</a> Là Những Tiêu Đề Được Thêm</p>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-danger" data-dismiss="modal">Đóng</button>
                                        <button type="submit" class="btn btn-primary">Lưu</button>
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
                <jsp:include page="../footer.jsp" />
            </div>
        </div>
        <script src="../vendor/datatables/jquery.dataTables.min.js"></script>
        <script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>
        <script src="../js/demo/datatables-demo.js"></script>
    </body>

</html>
