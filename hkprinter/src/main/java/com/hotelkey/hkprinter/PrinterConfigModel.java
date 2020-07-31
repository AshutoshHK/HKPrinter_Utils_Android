package com.hotelkey.hkprinter;

import com.epson.epos2.printer.Printer;

public class PrinterConfigModel {

    private static PrinterConfigModel printerConfigModel;

    private Printer printer;

    private PrinterConfigModel() {
    }

    public static PrinterConfigModel getInstance() {
        if (printerConfigModel == null) {
            printerConfigModel = new PrinterConfigModel();
        }
        return printerConfigModel;
    }

    public Printer getPrinter() {
        return printer;
    }

    public void setPrinter(Printer printer) {
        this.printer = printer;
    }
}
