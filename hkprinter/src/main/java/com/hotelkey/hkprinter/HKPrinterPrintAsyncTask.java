package com.hotelkey.hkprinter;

import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.epson.epos2.printer.Printer;

import java.util.HashMap;

public class HKPrinterPrint extends AsyncTask<Boolean, Printer, HashMap<HKPrinterProfiles, Bitmap>> {


    @Override
    protected HashMap<HKPrinterProfiles, Bitmap> doInBackground(Boolean... booleans) {
        return null;
    }
}
