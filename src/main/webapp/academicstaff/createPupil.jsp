<%--
  Created by IntelliJ IDEA.
  User: Anh Quan
  Date: 5/24/2024
  Time: 11:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <style>

    </style>
</head>
<body>
<jsp:include page="dashboard_staff.jsp"/>
<main class="app-content">
    <div class="app-title">
        <div>
            <h1><i class="bx bx-user"></i> Thêm học sinh</h1>
<%--            <p style="text-align: center; color: red">${requestScope.message}</p>--%>
        </div>
    </div>
    <form method="post" action="createpupil">
    <div class="row">

        <div class="col-md-3">
            <label for="imageUpload" class="form-label" style="cursor: pointer">Chọn hình ảnh</label>
            <input type="file" class="form-control" id="imageUpload" required accept="image/*" onchange="previewImage(event)" name="avatar">

            <div id="imagePreview" class="mt-3 text-center" style="display: none;">
                <img id="preview" class="img-fluid rounded" alt="Preview Image" style="max-width: 300px; max-height: 300px;">
            </div>
    </div>
        <script>
                function previewImage(event) {
                var reader = new FileReader();
                reader.onload = function() {
                var preview = document.getElementById('preview');
                preview.src = reader.result;
                document.getElementById('imagePreview').style.display = 'block';
            };
                reader.readAsDataURL(event.target.files[0]);
            }
        </script>

        <div class="col-md-9">
            <div class="tile">
                <div class="tile-body">

                    <div class="container">
                        <div class="row">
                            <div class="col-md-6">

                                <div class="form-group">
                                    <label for="id">Mã học sinh</label>
                                        <input type="text" class="form-control" id="id"  value="${requestScope.newPupilId}" style="width: 30%" readonly>
                                    </div>
                                    <div class="form-group">
                                        <label for="firstName">Tên học sinh</label>
                                        <input type="text" class="form-control" id="firstName" required name="firstName" style="width: 50%">
                                    </div>
                                    <div class="form-group">
                                        <label for="lastName">Họ học sinh</label>
                                        <input type="text" class="form-control" id="lastName" name="lastName" required style="width: 40%">
                                    </div>
                                    <div class="form-group">
                                        <label for="fatherName">Họ tên bố</label>
                                        <input type="text" class="form-control" id="fatherName" name="fatherName" required>
                                    </div>
                                    <div class="form-group">
                                        <label for="motherName">Họ tên mẹ</label>
                                        <input type="text" class="form-control" id="motherName" name="motherName" required>
                                    </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-md-6">
                                            <label for="birth" class="form-label">Ngày sinh</label>
                                            <input type="date" id="birth" class="form-control" name="birth" required>
                                        </div>
                                        <div class="col-md-6">
                                            <label for="gender" class="form-label" >Giới tính</label>
                                            <select name="gender" id="gender" required class="form-select" aria-label="Default select example" style="width: 100%;height: 60%;">
                                                <option value="1">Nam</option>
                                                <option value="0">Nữ</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>


                                <div class="form-group">
                                        <label for="email">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="parentNote">Ghi chú của phụ huynh</label>
                                    <textarea name="note" class="form-control" id="parentNote" rows="4"  required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="address">Address</label>
                                    <textarea class="form-control" id="address" rows="4" name="address" required></textarea>
                                </div>
                                <div class="form-group">
                                    <label for="fatherPhone">Số điện thoại bố</label>
                                    <input type="text" class="form-control" id="fatherPhone" name="fatherPhone" required style="width: 40%">
                                </div>
                                <div class="form-group">
                                    <label for="motherPhone">Số điện thoại mẹ</label>
                                    <input type="text" class="form-control" id="motherPhone" name="motherPhone" required style="width: 40%">
                                </div>
                                <div class="form-group">
                                    <label for="height">Chiều cao</label>
                                    <div class="input-group" style="width: 30%">
                                        <input type="number" class="form-control" id="height" name="height" required >
                                        <div class="input-group-append">
                                            <span class="input-group-text">CM</span>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="weight">Cân nặng</label>
                                    <div class="input-group" style="width: 30%">
                                        <input type="number" class="form-control" id="weight" name="weight"  required >
                                        <div class="input-group-append">
                                            <span class="input-group-text">KG</span>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>
                        <!-- Remove Avatar input from form since it's handled separately in sidebar -->
                    <div class="form-group text-center"> <!-- hoặc d-flex justify-content-center -->
                        <button class="btn btn-primary" type="submit">Thêm học sinh</button>
                    </div>

                </div>

        </div>
    </div>

    </div>
    </form>
</main>
</body>
<script>

</script>
</html>
