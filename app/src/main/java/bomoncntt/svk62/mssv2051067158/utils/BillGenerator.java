package bomoncntt.svk62.mssv2051067158.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

import bomoncntt.svk62.mssv2051067158.R;
import bomoncntt.svk62.mssv2051067158.domain.models.Dish;
import bomoncntt.svk62.mssv2051067158.domain.models.Order;
import bomoncntt.svk62.mssv2051067158.domain.models.OrderedDish;

public class BillGenerator {
    private static final int IMAGE_WIDTH = 90;
    private static final int IMAGE_HEIGHT = 90;
    private static final int FONT_SIZE_TITLE = 24;
    private static final int FONT_SIZE_HEADER = 18;
    private static final int FONT_SIZE_CONTENT = 12;
    private static final int FONT_SIZE_TOTAL = 14;
    private static final int FONT_SIZE_TABLE = 12;

    public static File createBill(Order order, Context context){

        context = context.getApplicationContext();
        String tempBillFileName = "BILL_" + System.currentTimeMillis() + ".pdf";
        Document document = new Document();
        try {
            File tempFile = new File(context.getCacheDir(), tempBillFileName);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                PdfWriter.getInstance(document, Files.newOutputStream(tempFile.toPath()));
            }
            document.open();
            BaseFont baseFont = BaseFont.createFont("assets/font/roboto.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, FONT_SIZE_TITLE, Font.BOLD);
            Font headerFont = new Font(baseFont, FONT_SIZE_HEADER, Font.BOLD);
            Font contentFont = new Font(baseFont, FONT_SIZE_CONTENT);
            Font totalFont = new Font(baseFont, FONT_SIZE_TOTAL, Font.BOLD);
            Font tableHeaderFont = new Font(baseFont, FONT_SIZE_TABLE, Font.BOLD);
            Font tableRowFont = new Font(baseFont, FONT_SIZE_TABLE);


            Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.cat_logo);
            Image image = Image.getInstance(getImageUri(imageBitmap, context).toString());
            image.scaleToFit(IMAGE_WIDTH, IMAGE_HEIGHT);
            image.setAlignment(Element.ALIGN_CENTER);
            document.add(image);

            Paragraph address = new Paragraph(context.getString(R.string.kirin_spicy_noodles), headerFont);
            address.setAlignment(Element.ALIGN_CENTER);
            document.add(address);

            Paragraph title = new Paragraph("PHIẾU THANH TOÁN", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            Paragraph invoiceNumber = new Paragraph("Số hóa đơn: "+ order.getInvoice().getInvoiceID(), contentFont);
            document.add(invoiceNumber);

            Paragraph tableNumber = new Paragraph(
                    (order.getTableLocation() != null) ? "Bàn: "+ order.getTableLocation().getTableName() : "Mang Đi",
                    contentFont);
            document.add(tableNumber);

            Paragraph date = new Paragraph("Ngày tạo đơn: " + DateHelper.dataToStringConverter(order.getInvoice().getOrderTime()), contentFont);
            document.add(date);

            Paragraph datePrint = new Paragraph("Ngày in đơn: " + DateHelper.dataToStringConverter(new Date()), contentFont);
            document.add(datePrint);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);
            table.setSpacingBefore(20f);
            table.setSpacingAfter(20f);

            addTableHeader(table, tableHeaderFont,"Tên Món", "SL", "Giá/Món", "Ghi Chú");

            for(Dish dish : order.getOrderMap().keySet()){
                OrderedDish orderedDish = order.getOrderMap().get(dish);
                assert orderedDish != null;
                addTableRow(
                        table,
                        tableRowFont,
                        dish.getDishName(),
                        String.valueOf(orderedDish.getQuantity()),
                        CurrencyHelper.currencyConverter(dish.getPrice()),
                        orderedDish.getNote()
                        );
            }

            document.add(table);

            Paragraph total = new Paragraph("Tổng đơn giá: "+ CurrencyHelper.currencyConverter(order.getInvoice().getTotal()), totalFont);
            total.setAlignment(Element.ALIGN_RIGHT);
            document.add(total);

            Paragraph closingMessage = new Paragraph("Cám ơn quý khách và hẹn gặp lại", contentFont);
            closingMessage.setAlignment(Element.ALIGN_CENTER);
            document.add(closingMessage);

            document.close();

            return tempFile;


        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static File getImageUri(Bitmap bitmap, Context context) throws IOException {
        String imageTempFile = "TEMP_IMG_" + System.currentTimeMillis() + ".png";
        File imageFile = new File(context.getCacheDir(), imageTempFile);
        FileOutputStream fos = new FileOutputStream(imageFile);
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.close();
        return imageFile;
    }

    private static void addTableHeader(PdfPTable table, Font font,String... headers) {

        for (String header : headers) {
            PdfPCell cell = new PdfPCell(new Phrase(header, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

    private static void addTableRow(PdfPTable table, Font font, String... values) {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value, font));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);
        }
    }

}
