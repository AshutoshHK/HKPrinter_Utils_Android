package com.hotelkey.hkprinter.print;

import android.graphics.Bitmap;

import com.starmicronics.starioextension.ICommandBuilder;
import com.starmicronics.starioextension.StarIoExt;

public class StarReceiptsFunctions {
    public static byte[] createRasterData(StarIoExt.Emulation emulation, Bitmap bitmap, boolean bothScale) {

        ICommandBuilder builder = StarIoExt.createCommandBuilder(emulation);

        builder.beginDocument();

        builder.appendBitmap(bitmap, false, bitmap.getWidth(), bothScale);

        builder.appendCutPaper(ICommandBuilder.CutPaperAction.PartialCutWithFeed);

        builder.endDocument();

        return builder.getCommands();
    }
}
