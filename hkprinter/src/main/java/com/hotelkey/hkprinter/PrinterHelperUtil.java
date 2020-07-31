package com.hotelkey.hkprinter;

import android.util.Log;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;

public class PrinterHelperUtil {

    private static PrinterHelperUtil printerHelperUtil = null;

    public static PrinterHelperUtil getInstance() {
        if (printerHelperUtil == null) {
            printerHelperUtil = new PrinterHelperUtil();
        }
        return printerHelperUtil;
    }



     public boolean connectPrinter(Printer mPrinter, String hostIpAddress) {
        boolean isBeginTransaction = false;

        try {
            String target = String.format("TCP:%s", hostIpAddress);
            Log.d("TAG", target);
            mPrinter.connect(target, com.epson.epos2.printer.Printer.PARAM_DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        try {
            mPrinter.beginTransaction();
            isBeginTransaction = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isBeginTransaction) {
            try {
                mPrinter.disconnect();
            } catch (Epos2Exception e) {
                return false;
            }
        }

        return true;
    }


    public void disconnectPrinter(Printer mPrinter) {
        if (mPrinter == null) {
            return;
        }
        try {
            mPrinter.endTransaction();
            mPrinter.disconnect();
        } catch (final Exception e) {
            e.printStackTrace();
        }
        resetPrinter(mPrinter);
    }

    public void resetPrinter(com.epson.epos2.printer.Printer printer) {
        if (printer == null) {
            return;
        }
        printer.clearCommandBuffer();
        printer.setReceiveEventListener(null);
        printer = null;
    }

}
