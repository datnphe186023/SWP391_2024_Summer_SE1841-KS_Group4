<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <title>Health Report</title>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <!-- Custom CSS-->
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/main.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/information-style.css">

        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f2f5;
            }

            .health-report {
                max-width: 800px;
                margin: 20px auto;
                background: #ffffff;
                border-radius: 10px;
                padding: 20px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            }

            .health-report h3 {
                text-align: center;
                color: #007bff;
                margin-bottom: 20px;
            }

            .health-report table {
                width: 100%;
                border-collapse: collapse;
                margin-bottom: 20px;
            }

            .health-report table,
            .health-report th,
            .health-report td {
                border: 1px solid #ddd;
            }

            .health-report th,
            .health-report td {
                padding: 12px;
                text-align: left;
            }

            .health-report th {
                width: 50%;
                background-color: #f8f9fa;
            }

            .profile-actions {
                text-align: right;
                margin-top: 20px;
            }

            .profile-actions a {
                text-decoration: none;
                padding: 10px 20px;
                background: #007bff;
                color: white;
                border-radius: 5px;
                transition: background 0.3s ease;
            }

            .profile-actions a:hover {
                background: #0056b3;
            }

            .notes-section {
                padding: 10px;
                border: 1px solid #ddd;
                background-color: #f8f9fa;
                border-radius: 5px;
            }

            .notes-section h4 {
                color: #333;
                margin-bottom: 10px;
            }
        </style>
    </head>

    <body id="page-top">
        <div id="wrapper">
            <jsp:include page="navbar.jsp" />
            <div id="content-wrapper" class="d-flex flex-column">
                <div id="content">
                    <jsp:include page="../header.jsp" />
                    <div class="container-fluid">
                        <!-- Student Health Report Section -->
                        <main>
                            <div class="row justify-content-center">
                                <div class="col-md-12">
                                    <div class="health-report shadow mb-4">
                                        <h3>Chi tiết báo cáo sức khỏe</h3>
                                        <table>
                                            <thead>
                                                <tr>
                                                    <th>Chi tiết</th>
                                                    <th>Thông số</th>
                                                </tr>
                                            </thead>
                                            <c:set var="healthCheckUp" value="${requestScope.healthCheckUp}" />
                                            <tbody>
                                                <tr>
                                                    <td>Chiều cao (cm)</td>
                                                    <td>${healthCheckUp.height}</td>
                                                </tr>
                                                <tr>
                                                    <td>Cân nặng (kg)</td>
                                                    <td>${healthCheckUp.weight}</td>
                                                </tr>
                                                <tr>
                                                    <td>Chỉ số phát triển</td>
                                                    <td>${healthCheckUp.averageDevelopmentStage}</td>
                                                </tr>
                                                <tr>
                                                    <td>Huyết áp</td>
                                                    <td>${healthCheckUp.bloodPressure}</td>
                                                </tr>
                                                <tr>
                                                    <td>Răng</td>
                                                    <td>${healthCheckUp.teeth}</td>
                                                </tr>
                                                <tr>
                                                    <td>Mắt</td>
                                                    <td>${healthCheckUp.eyes}</td>
                                                </tr>
                                                <tr>
                                                    <td>Tai mũi họng</td>
                                                    <td>${healthCheckUp.earNoseThroat}</td>
                                                </tr>
                                            </tbody>

                                        </table>
                                        <div class="notes-section">
                                            <strong>Ghi chú:</strong>
                                            <p>${healthCheckUp.notes}</p>
                                        </div>

                                    </div>
                                        <div class="btn-group-right float-right">
                                            <button type="button" class="btn btn-primary" onclick="history.back()"  style="width: 100px">Quay lại</button>
                                        </div>
                                </div>

                                
                            </div>

                        </main>
                    </div>
                </div>
                <jsp:include page="../footer.jsp" />
            </div>
        </div>
    </body>

</html>
