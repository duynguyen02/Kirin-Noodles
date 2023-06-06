package bomoncntt.svk62.mssv2051067158.utils;

import android.content.Context;
import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileIOHelper {

    private static FileIOHelper instance = null;

    private final String appDir;

    public static synchronized FileIOHelper getInstance(Context context){
        if(instance == null){
            instance = new FileIOHelper(context);
        }
        return instance;
    }

    private FileIOHelper(Context context){
        appDir = context.getApplicationContext().getFilesDir().toString();
    }

    public synchronized File copyImageToApplication(Bitmap bitmap, String folder){
        String imageFilename = appDir + "/" + folder + "/IMG_" + System.currentTimeMillis() + ".jpeg";

        File file = new File(imageFilename);
        File dir = new File(Objects.requireNonNull(file.getParent()));

        if(!dir.exists()){
            if (!dir.mkdirs()){
                return null;
            }
        }

        try {
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return file;
        } catch (IOException e){
            return null;
        }

    }


    public synchronized boolean deleteFile(String filePath){
        File file = new File(filePath);

        if (file.exists()){
            return file.delete();
        }
        else {
            return false;
        }

    }



}
