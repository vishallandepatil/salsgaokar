package com.example.vishallandepatil.incubatore.file;

import android.content.ContentValues;
import android.content.Context;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;

/**
 * Created by Administrator on 17.03.2017.
 */

public class FileHelper {


    public static File creatfile(String folder, String name, String type) {
            File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Folders.APPROOTFOLDER), folder);

        //Create the storage directory if it does not exist
       if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        File file;
        file = new File(mediaStorageDir.getPath() + File.separator + "" + name +type);

        return file;


    }

    public static File creatFileUnknownFromat(String folder, String name, String type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Folders.APPROOTFOLDER), folder);
        File mediaFile;
        //Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        String format = mediaStorageDir.getPath().substring(mediaStorageDir.getPath().lastIndexOf("/") + 1).split("\\.")[1];

        //Create circular_progress_bar media file name

        mediaFile= new File(mediaStorageDir.getPath() + File.separator + "" + name + format);


        return mediaFile;
    }


    public static void addImageToGallery(final String filePath, final Context context) {

        ContentValues values = new ContentValues();

        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.MediaColumns.DATA, filePath);

        context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
    }





}
