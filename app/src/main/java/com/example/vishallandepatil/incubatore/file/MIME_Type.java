package com.example.vishallandepatil.incubatore.file;

/**
 * Created by Administrator on 12.02.2018.
 */

public class MIME_Type {

    public static final String IMAGE_MIME_TYPE = "image/*";
    public static final String PNG_MIME_TYPE = "image/png";
    public static final String VIDEO_MIME_TYPE = "video/*";
    public static final String AUDIO_MIME_TYPE = "audio/*";
    public static final String TEXT_PLAIN_MIME_TYPE = "text/plain";
    public static final String PPT_MIME_TYPE = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
    public static final String EXCEL_MIME_TYPE = "application/vnd.ms-excel";
    public static final String PDF_MIME_TYPE = "application/pdf";
    public static final String WORD_DOC_MIME_TYPE = "application/msword";
    public static final String ALL_MIME_TYPE = "*/*";

    public static String  getType(String urldisplay)
    {



        String filenamewithextension = urldisplay.substring(urldisplay.lastIndexOf("/") + 1);
        String format = filenamewithextension.substring(filenamewithextension.lastIndexOf("."));





        if (format.equalsIgnoreCase(FileFormats.JPEG  )||format.equalsIgnoreCase(FileFormats.JPG ))
        {
            return IMAGE_MIME_TYPE;
        }
        else   if (format.equalsIgnoreCase(FileFormats.PNG))
        {
            return PNG_MIME_TYPE;
        } else if (format.equalsIgnoreCase(FileFormats.MP4)) {

            return VIDEO_MIME_TYPE;
        } else if (format.equalsIgnoreCase(FileFormats.MP3)) {
            return AUDIO_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.PDF)) {
            return PDF_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.XLS)) {
            return ALL_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.XLSX)) {
            return ALL_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.TXT)) {
            return TEXT_PLAIN_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.DOC)) {
            return WORD_DOC_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.DOCX)) {
            return WORD_DOC_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.PPT)) {
            return PPT_MIME_TYPE;


        } else if (format.equalsIgnoreCase(FileFormats.PPTX)) {
            return PPT_MIME_TYPE;


        }
        else
        {
            return ALL_MIME_TYPE;

        }

    }

}
