package com.hotelkey.hkprinter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Printer;

import com.epson.epos2.Epos2Exception;
import com.epson.epos2.printer.PrinterStatusInfo;
import com.hotelkey.hkprinter.printerStatus.PrinterStatusHandling;

class PrinterTestConnectTask extends AsyncTask<Void, Printer, String> {

    public interface TestConnectionCallbacks {
        void connectionCallSuccess();

        void connectionError(String message);
    }

    private final String hostIpAddress;
    private Context mContext;

    private com.epson.epos2.printer.Printer mPrinter;

    private TestConnectionCallbacks callbacks;


    public PrinterTestConnectTask(Context mContext, String hostIpAddress, TestConnectionCallbacks testConnectionCallbacks) {
        this.mContext = mContext;
        this.hostIpAddress = hostIpAddress;
        this.callbacks = testConnectionCallbacks;
    }


    @Override
    protected String doInBackground(Void... voids) {
        return initializeObject();
    }


    private String initializeObject() {
        String message = null;
        try {

            mPrinter = PrinterConfigModel.getInstance().getPrinter();
            if (mPrinter == null) {
                mPrinter = new com.epson.epos2.printer.Printer(com.epson.epos2.printer.Printer.TM_T82, com.epson.epos2.printer.Printer.MODEL_ANK, mContext);
                PrinterConfigModel.getInstance().setPrinter(mPrinter);
            }
            mPrinter.setReceiveEventListener(new com.epson.epos2.printer.ReceiveListener() {
                @Override
                public void onPtrReceive(com.epson.epos2.printer.Printer printer, int i, PrinterStatusInfo printerStatusInfo, String s) {
                    new Thread(new Runnable() {
                        @Override
                        public synchronized void run() {
                            PrinterHelperUtil.getInstance().disconnectPrinter(mPrinter);
                        }
                    }).start();
                }
            });

            boolean isPrinterConnected = PrinterHelperUtil.getInstance().connectPrinter(mPrinter,hostIpAddress);
            if (isPrinterConnected) {
                PrinterStatusInfo status = mPrinter.getStatus();

                if (!PrinterStatusHandling.getInstance().isPrintable(status)) {
                    mPrinter.disconnect();
                }
            } else {
                throw new Exception("Printer not connected");
            }

        } catch (Exception e) {
            if (e instanceof Epos2Exception) {
                message = PrinterStatusHandling.getInstance().getEposExceptionText(((Epos2Exception) e).getErrorStatus());
                System.out.println("Epos2Exception : " + message);
            }
            message = "Unable to connect to printer";
            e.printStackTrace();
        }
        PrinterHelperUtil.getInstance().disconnectPrinter(mPrinter);
        return message;
    }






    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result == null) {
            callbacks.connectionCallSuccess();
        } else {
            callbacks.connectionError(result);
        }
    }
}
