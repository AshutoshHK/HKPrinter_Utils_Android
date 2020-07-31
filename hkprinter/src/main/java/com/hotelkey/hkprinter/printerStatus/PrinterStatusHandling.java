package com.hotelkey.hkprinter.exceptionHandling;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.hotelkey.hkprinter.PrinterConfigModel;

public class PrinterException {

    private static PrinterException printerException;

    private Printer printer;

    private PrinterException() {
    }

    public static PrinterException getInstance() {
        if (printerException == null) {
            printerException = new PrinterException();
        }
        return printerException;
    }

    public boolean isPrintable(PrinterStatusInfo status) {
        if (status == null) {
            return false;
        }
        if (status.getConnection() == Printer.FALSE) {
            return false;
        } else {
            return status.getOnline() != Printer.FALSE;
        }
    }


    public String getEposExceptionText(int state) {
        String returnText = "";
        switch (state) {
            case Epos2Exception.ERR_PARAM:
                returnText = "ERR_PARAM";
                break;
            case Epos2Exception.ERR_CONNECT:
                returnText = "ERR_CONNECT";
                break;
            case Epos2Exception.ERR_TIMEOUT:
                returnText = "ERR_TIMEOUT";
                break;
            case Epos2Exception.ERR_MEMORY:
                returnText = "ERR_MEMORY";
                break;
            case Epos2Exception.ERR_ILLEGAL:
                returnText = "ERR_ILLEGAL";
                break;
            case Epos2Exception.ERR_PROCESSING:
                returnText = "ERR_PROCESSING";
                break;
            case Epos2Exception.ERR_NOT_FOUND:
                returnText = "ERR_NOT_FOUND";
                break;
            case Epos2Exception.ERR_IN_USE:
                returnText = "ERR_IN_USE";
                break;
            case Epos2Exception.ERR_TYPE_INVALID:
                returnText = "ERR_TYPE_INVALID";
                break;
            case Epos2Exception.ERR_DISCONNECT:
                returnText = "ERR_DISCONNECT";
                break;
            case Epos2Exception.ERR_ALREADY_OPENED:
                returnText = "ERR_ALREADY_OPENED";
                break;
            case Epos2Exception.ERR_ALREADY_USED:
                returnText = "ERR_ALREADY_USED";
                break;
            case Epos2Exception.ERR_BOX_COUNT_OVER:
                returnText = "ERR_BOX_COUNT_OVER";
                break;
            case Epos2Exception.ERR_BOX_CLIENT_OVER:
                returnText = "ERR_BOX_CLIENT_OVER";
                break;
            case Epos2Exception.ERR_UNSUPPORTED:
                returnText = "ERR_UNSUPPORTED";
                break;
            case Epos2Exception.ERR_FAILURE:
                returnText = "ERR_FAILURE";
                break;
            default:
                returnText = String.format("%d", state);
                break;
        }
        return returnText;
    }



}
