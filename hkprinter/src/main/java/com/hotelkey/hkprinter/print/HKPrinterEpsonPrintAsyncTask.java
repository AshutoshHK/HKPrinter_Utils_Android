package com.hotelkey.hkprinter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.epson.epos2.printer.ReceiveListener;
import com.epson.epos2.printer.StatusChangeListener;
import com.hotelkey.hkprinter.printerStatus.PrinterStatusHandling;

public class HKPrinterEpsonPrintAsyncTask extends AsyncTask<Boolean, Printer, Boolean> {

    String TAG = this.getClass().getCanonicalName();


    public interface HKPrintCallBack
    {


        void statusUpdate(String codeText, boolean b);
    }

    HKPrintCallBack printCallBack;
    Context context;
    private final HKPrinterProfiles printerProfile;
    Bitmap bitMap;

    public HKPrinterEpsonPrintAsyncTask(HKPrinterProfiles printerProfile, Bitmap bitmap, Context context, HKPrintCallBack printCallBack) {
        this.printCallBack = printCallBack;
        this.context = context;
        this.printerProfile = printerProfile;
        this.bitMap = bitmap;

    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        Printer printer = initializeObject();


        if (!createReceiptData(printer, printerProfile.getNumberOfPrints(), bitMap)) {
            PrinterHelperUtil.getInstance().resetPrinter(printer);
            return false;
        }

        if (!printData(printer, printerProfile)) {
            PrinterHelperUtil.getInstance().resetPrinter(printer);
            return false;
        }
        return true;

    }

    @Override
    protected void onPostExecute(Boolean printer) {
        super.onPostExecute(printer);

    }

    private Printer initializeObject() {
        try {
            Printer printer = new Printer(Printer.TM_M30, Printer.MODEL_ANK, context);
            printer.setStatusChangeEventListener(new StatusChangeListener() {
                @Override
                public void onPtrStatusChange(Printer printer, int code) {
                    printCallBack.statusUpdate(PrinterStatusHandling.getCodeText(code), false);
                }
            });
            printer.setReceiveEventListener(new ReceiveListener() {
                @Override
                public void onPtrReceive(final Printer printer, int code, PrinterStatusInfo printerStatusInfo, String s) {

                    printCallBack.statusUpdate(PrinterStatusHandling.getCodeText(code), true);
                    new Thread(new Runnable() {
                        @Override
                        public synchronized void run() {

                            PrinterHelperUtil.getInstance().disconnectPrinter(printer);

                        }
                    }).start();
                }
            });
            return printer;

        } catch (Exception e) {
            printCallBack.statusUpdate(e.getMessage(), false);
            // ShowMsg.showException(e, "Error", mContext);
            return null;
        }
    }

    private boolean createReceiptData(Printer printer, int numberOfCopies, Bitmap bitmap) {
        if (printer == null) {
            return false;
        }
        try {
            printer.addTextAlign(Printer.ALIGN_CENTER);

            for (int i = 0; i < numberOfCopies; i++) {
                printer.addTextAlign(Printer.ALIGN_CENTER);
                printer.addTextSize(2, 2);
                printer.addFeedLine(1);
                printer.addPageArea(0, 0, bitmap.getWidth(), bitmap.getHeight());

                printer.addImage(bitmap, 0, 0,
                        bitmap.getWidth(),
                        bitmap.getHeight(),
                        Printer.COLOR_1,
                        Printer.MODE_MONO,
                        Printer.HALFTONE_DITHER,
                        Printer.PARAM_DEFAULT,
                        Printer.COMPRESS_AUTO);

                printer.addCut(Printer.CUT_FEED);

            }

        } catch (Exception e) {
            printCallBack.statusUpdate(e.getMessage(), false);
            // ShowMsg.showException(e, "Error", mContext);
            return false;
        }

        return true;
    }

    private boolean printData(Printer printer, HKPrinterProfiles activeDeviceProfile) {
        if (printer == null) {
            return false;
        }

        if (!PrinterHelperUtil.getInstance().connectPrinter(printer, activeDeviceProfile.getHostId())) {
            return false;
        }

        PrinterStatusInfo status = printer.getStatus();

        if (!PrinterStatusHandling.getInstance().isPrintable(status)) {
            PrinterHelperUtil.getInstance().disconnectPrinter(printer);
            return false;
        }

        Log.d(TAG, "Printer is ready");

        try {
            printer.sendData(Printer.PARAM_DEFAULT);
            Log.d(TAG, "Printer send onStepperValueChange");

        } catch (Epos2Exception e) {
            printCallBack.statusUpdate(PrinterStatusHandling.getInstance().getEposExceptionText(e.getErrorStatus()), false);
            // ShowMsg.showException(e, "Error", mContext);
            e.printStackTrace();
            Log.d(TAG, "Printer send onStepperValueChange failed  -> " + PrinterStatusHandling.getInstance().getEposExceptionText(e.getErrorStatus()));
            PrinterHelperUtil.getInstance().disconnectPrinter(printer);
            return false;
        }

        return true;
    }
}
