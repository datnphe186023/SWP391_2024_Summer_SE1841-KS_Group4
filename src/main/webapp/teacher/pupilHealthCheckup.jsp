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
                    <jsp:include page="../header.jsp"/>

                    <div class="container">
                        <div class="card shadow mb-4">
                            <div class="card-header py-3">
                                <h3 class="m-0 font-weight-bold"><i class="fa fa-edit"></i>Cập nhật sức khỏe theo tháng của học sinh</h3>
                            </div>
                        </div>
                        <div class="row gutters">
                            
                            <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                <div class="card h-100">
                                    <div class="card-body">
                                        <form action="${pageContext.request.contextPath}/health-check-up" method="post">
                                            <div class="row gutters">

                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="id">ID Học sinh *</label>
                                                        <input class="form-control" placeholder="ID Học sinh" type="text" name="pupil_id" value="${healthCheckUp.pupilId}" disabled style="width: 32%;"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="checkUpDate">Ngày khám *</label>
                                                        <input style="width: 50%;" class="form-control" type="date" name="check_up_date" value="${healthCheckUp.checkUpDate}" required/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="height">Chiều cao (cm) *</label>
                                                        <input style="width: 40%;" type="number" step="0.1" class="form-control" placeholder="Chiều cao" name="height" value="${healthCheckUp.height}" required/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="weight">Cân nặng (kg) *</label>
                                                        <input style="width: 40%;" type="number" step="0.1" class="form-control" placeholder="Cân nặng" name="weight" value="${healthCheckUp.weight}" required/>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gutters">
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="averageDevelopmentStage">Giai đoạn phát triển trung bình *</label>
                                                        <input class="form-control" type="text" name="average_development_stage" value="${healthCheckUp.averageDevelopmentStage}" required style="width: 40%;"/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="bloodPressure">Huyết áp *</label>
                                                        <input style="width: 60%;" class="form-control" type="text" name="blood_pressure" value="${healthCheckUp.bloodPressure}" required/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="teeth">Răng *</label>
                                                        <input style="width: 40%;" type="text" class="form-control" name="teeth" value="${healthCheckUp.teeth}" required/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="eyes">Mắt *</label>
                                                        <input style="width: 40%;" type="text" class="form-control" name="eyes" value="${healthCheckUp.eyes}" required/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="earNoseThroat">Tai mũi họng *</label>
                                                        <input style="width: 40%;" type="text" class="form-control" name="ear_nose_throat" value="${healthCheckUp.earNoseThroat}" required/>
                                                    </div>
                                                </div>
                                                <div class="col-xl-6 col-lg-6 col-md-6 col-sm-6 col-12">
                                                    <div class="form-group">
                                                        <label for="notes">Ghi chú</label>
                                                        <textarea class="form-control" name="notes" rows="2" style="width: 100%;">${healthCheckUp.notes}</textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="row gutters">
                                                <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                                    <div class="text-right">
                                                        <button type="submit" id="submit" name="submit" class="btn btn-primary">Lưu</button>
                                                    </div>
                                                </div>
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


    </body>

</html>
