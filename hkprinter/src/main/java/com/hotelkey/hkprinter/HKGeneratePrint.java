package com.hotelkey.hkprinter;

import android.content.Context;
import android.graphics.Bitmap;

import com.epson.epos2.printer.Printer;
import com.hotelkey.hkprinter.print.HKPrinterEpsonPrintAsyncTask;
import com.hotelkey.hkprinter.print.HKPrinterStarPrintAsyncTask;
import com.hotelkey.hkprinter.print.PrinterPrintCallBacks;
import com.starmicronics.starioextension.StarIoExtManager;

public class HKGeneratePrint implements ConvertPdftoImageTask.ConvertImageCallBack, PrinterPrintCallBacks {


    private StarIoExtManager mStarIoExtManager;


    public interface HKPrintGenerateCallBack {

    }

    public enum DocumentType {
        /*To check type of Printer*/
        PRINT_IMAGE, PRINT_DOC;
    }

    HKPrintGenerateCallBack hkPrintGenerateCallBack;
    DocumentType documentType;
    Context context;
    String path;
    Printer printer;
    Bitmap parsedBitmap;

    public HKGeneratePrint(HKPrintGenerateCallBack hkPrintGenerateCallBack, DocumentType documentType, Printer printer, String path, Context context) {
        this.hkPrintGenerateCallBack = hkPrintGenerateCallBack;
        this.documentType = documentType;
        this.printer = printer;
        this.path = path;
        this.context = context;

        parseDocument();
    }

    private void parseDocument() {
        if (documentType == DocumentType.PRINT_DOC) {
            new ConvertPdftoImageTask(context, path, this);
        }
    }

    @Override
    public void getCallBackImage(Bitmap bitmap) {
        parsedBitmap = bitmap;
    }

    public void performPrinting(HKPrinterProfiles profiles, Context context, PrinterType printerType) {
        if (printerType == PrinterType.PRINTER_EPSON) {
            new HKPrinterEpsonPrintAsyncTask(profiles, parsedBitmap, context, this).execute();
        } else if (printerType == PrinterType.PRINTER_STAR) {
            if (mStarIoExtManager == null) {
                String target = String.format("TCP:%s", profiles.getHostId());
                mStarIoExtManager = new StarIoExtManager(StarIoExtManager.Type.Standard, target, "", 10000, context);
            }
            new HKPrinterStarPrintAsyncTask(profiles, parsedBitmap, mStarIoExtManager, context, this).execute();
        }
    }

    public StarIoExtManager getmStarIoExtManager() {
        return mStarIoExtManager;
    }

    public Printer getPrinter() {
        return printer;
    }

    public Bitmap getParsedBitmap() {
        return parsedBitmap;
    }


    @Override
    public void statusUpdate(String codeText, HKPrinterProfiles profiles, boolean b) {

    }

}
