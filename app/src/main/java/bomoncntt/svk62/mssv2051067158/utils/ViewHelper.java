package bomoncntt.svk62.mssv2051067158.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.FragmentActivity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import bomoncntt.svk62.mssv2051067158.R;

public class ViewHelper {
    public static Bitmap getBitmapFromView(View view){
        Bitmap bitmap = Bitmap.createBitmap(
                view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888
        );
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    public static Bitmap convertStringToBitmap(String imageString) {
        try {
            byte[] decodedBytes = Base64.decode(imageString, Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertImageToString(String imagePath) {
        try {

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Path path = Paths.get(imagePath);
                byte[] imageBytes;
                imageBytes = Files.readAllBytes(path);
                byte[] encodedBytes = Base64.encode(imageBytes, Base64.DEFAULT);
                return new String(encodedBytes);
            }
            return null;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setImageForImageViewFromFile(ImageView imageView, String path){
        File imageFile = new File(path);
        if (imageFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            imageView.setImageBitmap(bitmap);
        }
        else {
            imageView.setImageResource(R.drawable.food_example);
        }
    }

    public static void setVisibilityViewOnUiThread(FragmentActivity activity, View view, int visibility){
        System.out.println("run");
        activity.runOnUiThread(() -> view.setVisibility(visibility));
    }


}
