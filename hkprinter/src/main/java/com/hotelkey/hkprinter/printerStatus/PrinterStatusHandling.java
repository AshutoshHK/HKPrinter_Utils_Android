package com.hotelkey.hkprinter.printerStatus;

import com.epson.epos2.Epos2CallbackCode;
import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.Printer;
import com.epson.epos2.printer.PrinterStatusInfo;

public class PrinterStatusHandling {

    private static PrinterStatusHandling printerStatusHandling;

    private Printer printer;

    private PrinterStatusHandling() {
    }

    public static PrinterStatusHandling getInstance() {
        if (printerStatusHandling == null) {
            printerStatusHandling = new PrinterStatusHandling();
        }
        return printerStatusHandling;
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

    public static String getCodeText(int state) {
        String return_text = "";
        switch (state) {
            case Epos2CallbackCode.CODE_SUCCESS:
                return_text = "PRINT_SUCCESS";
                break;
            case Epos2CallbackCode.CODE_PRINTING:
                return_text = "PRINTING";
                break;
            case Epos2CallbackCode.CODE_ERR_AUTORECOVER:
                return_text = "ERR_AUTORECOVER";
                break;
            case Epos2CallbackCode.CODE_ERR_COVER_OPEN:
                return_text = "ERR_COVER_OPEN";
                break;
            case Epos2CallbackCode.CODE_ERR_CUTTER:
                return_text = "ERR_CUTTER";
                break;
            case Epos2CallbackCode.CODE_ERR_MECHANICAL:
                return_text = "ERR_MECHANICAL";
                break;
            case Epos2CallbackCode.CODE_ERR_EMPTY:
                return_text = "ERR_EMPTY";
                break;
            case Epos2CallbackCode.CODE_ERR_UNRECOVERABLE:
                return_text = "ERR_UNRECOVERABLE";
                break;
            case Epos2CallbackCode.CODE_ERR_FAILURE:
                return_text = "ERR_FAILURE";
                break;
            case Epos2CallbackCode.CODE_ERR_NOT_FOUND:
                return_text = "ERR_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SYSTEM:
                return_text = "ERR_SYSTEM";
                break;
            case Epos2CallbackCode.CODE_ERR_PORT:
                return_text = "ERR_PORT";
                break;
            case Epos2CallbackCode.CODE_ERR_TIMEOUT:
                return_text = "ERR_TIMEOUT";
                break;
            case Epos2CallbackCode.CODE_ERR_JOB_NOT_FOUND:
                return_text = "ERR_JOB_NOT_FOUND";
                break;
            case Epos2CallbackCode.CODE_ERR_SPOOLER:
                return_text = "ERR_SPOOLER";
                break;
            case Epos2CallbackCode.CODE_ERR_BATTERY_LOW:
                return_text = "ERR_BATTERY_LOW";
                break;
            case Epos2CallbackCode.CODE_ERR_TOO_MANY_REQUESTS:
                return_text = "ERR_TOO_MANY_REQUESTS";
                break;
            case Epos2CallbackCode.CODE_ERR_REQUEST_ENTITY_TOO_LARGE:
                return_text = "ERR_REQUEST_ENTITY_TOO_LARGE";
                break;
            case Epos2CallbackCode.CODE_CANCELED:
                return_text = "CODE_CANCELED";
                break;
            case Epos2CallbackCode.CODE_ERR_NO_MICR_DATA:
                return_text = "ERR_NO_MICR_DATA";
                break;
            case Epos2CallbackCode.CODE_ERR_ILLEGAL_LENGTH:
                return_text = "ERR_ILLEGAL_LENGTH";
                break;
            case Epos2CallbackCode.CODE_ERR_NO_MAGNETIC_DATA:
                return_text = "ERR_NO_MAGNETIC_DATA";
                break;
            case Epos2CallbackCode.CODE_ERR_RECOGNITION:
                return_text = "ERR_RECOGNITION";
                break;
            case Epos2CallbackCode.CODE_ERR_READ:
                return_text = "ERR_READ";
                break;
            case Epos2CallbackCode.CODE_ERR_NOISE_DETECTED:
                return_text = "ERR_NOISE_DETECTED";
                break;
            case Epos2CallbackCode.CODE_ERR_PAPER_JAM:
                return_text = "ERR_PAPER_JAM";
                break;
            case Epos2CallbackCode.CODE_ERR_PAPER_PULLED_OUT:
                return_text = "ERR_PAPER_PULLED_OUT";
                break;
            case Epos2CallbackCode.CODE_ERR_CANCEL_FAILED:
                return_text = "ERR_CANCEL_FAILED";
                break;
            case Epos2CallbackCode.CODE_ERR_PAPER_TYPE:
                return_text = "ERR_PAPER_TYPE";
                break;
            case Epos2CallbackCode.CODE_ERR_WAIT_INSERTION:
                return_text = "ERR_WAIT_INSERTION";
                break;
            case Epos2CallbackCode.CODE_ERR_ILLEGAL:
                return_text = "ERR_ILLEGAL";
                break;
            case Epos2CallbackCode.CODE_ERR_INSERTED:
                return_text = "ERR_INSERTED";
                break;
            case Epos2CallbackCode.CODE_ERR_WAIT_REMOVAL:
                return_text = "ERR_WAIT_REMOVAL";
                break;
            case Epos2CallbackCode.CODE_ERR_DEVICE_BUSY:
                return_text = "ERR_DEVICE_BUSY";
                break;
            case Epos2CallbackCode.CODE_ERR_IN_USE:
                return_text = "ERR_IN_USE";
                break;
            case Epos2CallbackCode.CODE_ERR_CONNECT:
                return_text = "ERR_CONNECT";
                break;
            case Epos2CallbackCode.CODE_ERR_DISCONNECT:
                return_text = "ERR_DISCONNECT";
                break;
            case Epos2CallbackCode.CODE_ERR_MEMORY:
                return_text = "ERR_MEMORY";
                break;
            case Epos2CallbackCode.CODE_ERR_PROCESSING:
                return_text = "ERR_PROCESSING";
                break;
            case Epos2CallbackCode.CODE_ERR_PARAM:
                return_text = "ERR_PARAM";
                break;
            case Epos2CallbackCode.CODE_RETRY:
                return_text = "RETRY";
                break;
            case Epos2CallbackCode.CODE_ERR_DIFFERENT_MODEL:
                return_text = "ERR_DIFFERENT_MODEL";
                break;
            case Epos2CallbackCode.CODE_ERR_DIFFERENT_VERSION:
                return_text = "ERR_DIFFERENT_VERSION";
                break;
            case Epos2CallbackCode.CODE_ERR_DATA_CORRUPTED:
                return_text = "ERR_DATA_CORRUPTED";
                break;
            default:
                return_text = String.format("%d", state);
                break;
        }
        return return_text;
    }



}
