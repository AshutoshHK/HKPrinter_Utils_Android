package com.hotelkey.hkprinter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.ParcelFileDescriptor;

import com.epson.epos2.printer.Printer;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConvertPdftoImageTask extends AsyncTask<Boolean, Printer, Bitmap> {


    interface ConvertImageCallBack
    {
        void getCallBackImage(Bitmap bitmap);
    }
    String pdfFilePath;
    Context mContext;
    ConvertImageCallBack convertImageCallBack;

    public ConvertPdftoImageTask(Context mContext,String pdfFilePath,ConvertImageCallBack convertImageCallBack) {
        this.pdfFilePath = pdfFilePath;
        this.mContext = mContext;
        this.convertImageCallBack = convertImageCallBack;
    }



    @Override
    protected Bitmap doInBackground(Boolean... booleans) {
        return makeKOTPrint();
    }

    private Bitmap makeKOTPrint() {

        Bitmap logoData = null;

            try {
                File fileCopy = new File(pdfFilePath);
                ParcelFileDescriptor fd = ParcelFileDescriptor.open(fileCopy, ParcelFileDescriptor.MODE_READ_ONLY);
                int pageNum = 0;
                PdfiumCore pdfiumCore = new PdfiumCore(mContext);
                PdfDocument pdfDocument = pdfiumCore.newDocument(fd);
                pdfiumCore.openPage(pdfDocument, pageNum);
                int dpHeight = HelperClass.pxToDp(pdfiumCore.getPageSize(pdfDocument, 0).getHeight());
                int dpWidth = HelperClass.pxToDp(pdfiumCore.getPageSize(pdfDocument, 0).getWidth());

                logoData  = Bitmap.createBitmap(dpWidth + 65, dpHeight + 170,
                        Bitmap.Config.ARGB_8888);
                pdfiumCore.renderPageBitmap(pdfDocument, logoData, pageNum, 0, 0, dpWidth + 65, dpHeight + 170);
                pdfiumCore.closeDocument(pdfDocument); // important!

            } catch (IOException e) {
                e.printStackTrace();
            }
        return logoData;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        convertImageCallBack.getCallBackImage(bitmap);
    }
}
