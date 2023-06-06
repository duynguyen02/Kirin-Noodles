package bomoncntt.svk62.mssv2051067158.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
    public static Date stringToDateConverter(String dateString){
        SimpleDateFormat inputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'GMT'XXX yyyy", Locale.US);
        Date date = null;
        try {
            date = inputFormat.parse(dateString);
            SimpleDateFormat outputFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", new Locale("vi", "VN"));
            if(date != null){
                String formattedDate = outputFormat.format(date);
                return outputFormat.parse(formattedDate);

            }

        } catch (ParseException e) {
            return null;
        }

        return null;
    }

    public static String dataToStringConverter(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("d MMM yyyy HH:mm:ss",  new Locale("vi", "VN"));
        return formatter.format(date);
    }
}
