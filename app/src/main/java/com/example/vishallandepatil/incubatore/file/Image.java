package com.example.vishallandepatil.incubatore.file;

import android.graphics.Bitmap;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.example.vishallandepatil.incubatore.file.FileFormats.JPEG;
import static com.example.vishallandepatil.incubatore.file.FileFormats.PNG;


/**
 * Created by Administrator on 17.03.2017.
 */

public class Image {

    public static final int CONNOTCREATEFILE = 1, OK = 2, FILENOTFOUND = 3;

    public static int storeImage(String folder, String imageName, Bitmap image, String type) {
        FileOutputStream fos;
        File pictureFile = FileHelper.creatfile(folder, imageName, type);

        if (pictureFile == null) {

            return CONNOTCREATEFILE;
        }
        try {
            switch (type) {

                case PNG:
                    fos = new FileOutputStream(pictureFile);
                    image.compress(Bitmap.CompressFormat.PNG, 90, fos);
                    fos.close();
                    return OK;
                case JPEG:
                    fos = new FileOutputStream(pictureFile);
                    image.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                    fos.close();
                    return OK;
            }

        } catch (FileNotFoundException e) {

            return FILENOTFOUND;

        } catch (IOException e) {
            return FILENOTFOUND;
        }
        return FILENOTFOUND;
    }

    public static void encryptExtension(File file,String encryption)
    {
        try {
            int index = file.getName().lastIndexOf(".");
           String ext = file.getName().substring(index);
           String newname= file.getName().substring(0,index);
            String path=file.getParentFile().getPath()+"/"+newname + encryption;
           boolean v=file.renameTo(new File(path));
            Log.d("dddd","asdsa");

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
