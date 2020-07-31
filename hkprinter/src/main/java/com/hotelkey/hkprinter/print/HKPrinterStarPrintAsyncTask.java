package com.hotelkey.hkprinter.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import com.epson.epos2.printer.Printer;
import com.hotelkey.hkprinter.HKPrinterProfiles;
import com.starmicronics.stario.StarResultCode;
import com.starmicronics.starioextension.ConnectionCallback;
import com.starmicronics.starioextension.StarIoExt;
import com.starmicronics.starioextension.StarIoExtManager;
import com.starmicronics.starioextension.StarIoExtManagerListener;

public class HKPrinterStarPrintAsyncTask  extends AsyncTask<Boolean, Printer, Boolean> {


    HKPrinterProfiles profile;
    Bitmap bitmap;
    private StarIoExtManager mStarIoExtManager;
    Context mContext;
    PrinterPrintCallBacks callBacks;

    public HKPrinterStarPrintAsyncTask(HKPrinterProfiles profile, Bitmap bitmap, StarIoExtManager mStarIoExtManager, Context mContext, PrinterPrintCallBacks callBacks) {
        this.profile = profile;
        this.bitmap = bitmap;
        this.mStarIoExtManager = mStarIoExtManager;
        this.mContext = mContext;
        this.callBacks = callBacks;
    }

    @Override
    protected Boolean doInBackground(Boolean... booleans) {
        printOnStarPrinter();
        return true;
    }

    private void printOnStarPrinter() {
        byte[] commands;
        String target = String.format("TCP:%s", profile.getHostId());
        mStarIoExtManager.setListener(mStarIoExtManagerListener);
        mStarIoExtManager.connect(mConnectionCallback);

        StarIoExt.Emulation emulation = ModelCapability.getEmulation(ModelCapability.TSP100);

        if (bitmap != null) {
            commands = StarReceiptsFunctions.createRasterData(emulation, bitmap, false);
        } else {
            commands = new byte[0];
        }
        for (int i = 0; i < profile.getNumberOfPrints(); i++) {
            Communication.sendCommands(mStarIoExtManager, commands, target, "", 10000, 30000, mContext, mCallback);     // 10000mS!!!
        }
    }


    private final Communication.SendCallback mCallback = new Communication.SendCallback() {
        @Override
        public void onStatus(Communication.CommunicationResult communicationResult) {

            if (communicationResult.getCode() == 0) {
                String message = Communication.getCommunicationResultMessage(new Communication.CommunicationResult(Communication.Result.ErrorOpenPort, communicationResult.getCode()));
                callBacks.statusUpdate(message,profile, true);
            }

        }
    };
    private final ConnectionCallback mConnectionCallback = new ConnectionCallback() {
        @Override
        public void onConnected(boolean result, int resultCode) {

            if (!result) {
                String message;

                if (resultCode == StarResultCode.FAILURE_IN_USE) {
                    message = "Check the device. (In use)\nThen touch up the Refresh button.";
                    callBacks.statusUpdate(message,profile, false);
                } else {
                    message = "Check the device. (Power and Bluetooth pairing)\nThen touch up the Refresh button.";
                    callBacks.statusUpdate(message,profile, false);
                }


            }
        }

        @Override
        public void onDisconnected() {
            // do nothing
        }
    };
    private final StarIoExtManagerListener mStarIoExtManagerListener = new StarIoExtManagerListener() {
        @Override
        public void onPrinterImpossible() {
            callBacks.statusUpdate("Printer Impossible.",profile, false);
        }

        @Override
        public void onPrinterOnline() {
            //callbacks.statusUpdate("Printer Online.", false);
        }

        @Override
        public void onPrinterOffline() {
            callBacks.statusUpdate("Printer Offline.",profile, false);
        }

        @Override
        public void onPrinterPaperReady() {
            //callbacks.statusUpdate("Printer  .", false);
        }

        @Override
        public void onPrinterPaperNearEmpty() {
            callBacks.statusUpdate("Printer Paper Near Empty.",profile, false);
        }

        @Override
        public void onPrinterPaperEmpty() {

            callBacks.statusUpdate("Printer Paper Empty.",profile, false);
        }

        @Override
        public void onPrinterCoverOpen() {
            callBacks.statusUpdate("Printer Cover Open.",profile, false);

        }

        @Override
        public void onPrinterCoverClose() {
//          mComment.setText("Printer Cover Close.");
//
//          mComment.setTextColor(Color.BLUE);
        }


        @Override
        public void onStatusUpdate(String status) {
            System.out.println("status" + status);
        }
    };
}
