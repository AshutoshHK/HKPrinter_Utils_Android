package com.hotelkey.hkprinter.print;

import com.hotelkey.hkprinter.HKPrinterProfiles;

public interface PrinterPrintCallBacks {
    void statusUpdate(String codeText, HKPrinterProfiles profiles, boolean b);


}
