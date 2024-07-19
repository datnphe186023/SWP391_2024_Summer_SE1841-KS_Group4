package controller.headteacher;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import models.pupil.IPupilDAO;
import models.pupil.Pupil;
import models.pupil.PupilDAO;

@WebServlet(name = "ExportExcelServlet", urlPatterns = {"/headteacher/exportExcel"})
public class ExportExcelServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String schoolyearId = request.getParameter("schoolyearid");
        String schoolyear = request.getParameter("schoolyear");
        String numberpupil = request.getParameter("numberpupil");
        String numbergoodpupil = request.getParameter("numbergoodpupil");
        String numbernotgoodpupil = request.getParameter("numbernotgoodpupil");
        IPupilDAO pupilDAO = new PupilDAO();
        List<Pupil> pupils = pupilDAO.getPupilBySchoolYear(schoolyearId);
        // Tạo một workbook mới
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Evaluation Pupil");

        // Tạo kiểu cho tiêu đề lớn
        CellStyle titleStyle = workbook.createCellStyle();
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 16);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);
        titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        // Tạo hàng tiêu đề lớn
        Row titleRow = sheet.createRow(0);
        Cell titleCell = titleRow.createCell(0);
        titleCell.setCellValue("Tổng kết khen thưởng học sinh " + schoolyear);
        titleCell.setCellStyle(titleStyle);
        sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 6));

        // Tạo các dòng thông tin tổng quát
        Row infoRow1 = sheet.createRow(2);
        Cell infoCell1 = infoRow1.createCell(0);
        infoCell1.setCellValue("Số lượng học sinh: " + numberpupil);

        Row infoRow2 = sheet.createRow(3);
        Cell infoCell2 = infoRow2.createCell(0);
        infoCell2.setCellValue("Số lượng học sinh đạt danh hiệu Cháu Ngoan Bác Hồ: " + numbergoodpupil);

        Row infoRow3 = sheet.createRow(4);
        Cell infoCell3 = infoRow3.createCell(0);
        infoCell3.setCellValue("Số lượng học sinh không đạt danh hiệu Cháu Ngoan Bác Hồ: " + numbernotgoodpupil);

        // Tạo hàng tiêu đề bảng
        Row headerRow = sheet.createRow(6);
        String[] columnHeaders = {"STT", "Mã học sinh", "Họ và tên", "Giới Tính", "Ngày sinh", "Số phiếu Bé Ngoan", "Cháu Ngoan Bác Hồ"};
        for (int i = 0; i < columnHeaders.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columnHeaders[i]);
        }

        // Điền dữ liệu vào các hàng
        int rowNum = 7;
        for (int i = 0; i < pupils.size(); i++) {
            Row row = sheet.createRow(rowNum++);
            Pupil pupil = pupils.get(i);

            row.createCell(0).setCellValue(i + 1);
            row.createCell(1).setCellValue(pupil.getId());
            row.createCell(2).setCellValue(pupil.getLastName() + " " + pupil.getFirstName());
            row.createCell(3).setCellValue(pupil.getGender() ? "Nam" : "Nữ");
            row.createCell(4).setCellValue(pupil.getBirthday().toString());
            row.createCell(5).setCellValue(pupil.getYearEvaluation(schoolyearId));
            row.createCell(6).setCellValue(pupil.evaluate(schoolyearId));
        }

        // Thiết lập các tiêu đề HTTP để thông báo trình duyệt tải về file
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=SummarySchoolYear.xlsx");

        // Ghi workbook vào OutputStream
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();

        outputStream.flush();
        outputStream.close();
    }

}
