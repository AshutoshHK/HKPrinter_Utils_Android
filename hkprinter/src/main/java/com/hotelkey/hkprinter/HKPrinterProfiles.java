package com.hotelkey.hkprinter;

public class HKPrinterProfiles {

    private String hostId;
    PrinterType printerType;
    int numberOfPrints;
    String printMode;
    String pinConfig;
    boolean isEnabled;
    String otherDetails;
    String id;

    private static HKPrinterProfiles printer = null;

    public HKPrinterProfiles()
    {
        /*Default Constructor*/
    }

    public static HKPrinterProfiles getInstance()
    {
        if (printer == null) {
            printer = new HKPrinterProfiles();
        }
        return printer;
    }

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public PrinterType getPrinterType() {
        return printerType;
    }

    public void setPrinterType(PrinterType printerType) {
        this.printerType = printerType;
    }

    public int getNumberOfPrints() {
        return numberOfPrints;
    }

    public void setNumberOfPrints(int numberOfPrints) {
        this.numberOfPrints = numberOfPrints;
    }

    public String getPrintMode() {
        return printMode;
    }

    public void setPrintMode(String printMode) {
        this.printMode = printMode;
    }

    public String getPinConfig() {
        return pinConfig;
    }

    public void setPinConfig(String pinConfig) {
        this.pinConfig = pinConfig;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getOtherDetails() {
        return otherDetails;
    }

    public void setOtherDetails(String otherDetails) {
        this.otherDetails = otherDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static HKPrinterProfiles getPrinter() {
        return printer;
    }

    public static void setPrinter(HKPrinterProfiles printer) {
        HKPrinterProfiles.printer = printer;
    }
}
