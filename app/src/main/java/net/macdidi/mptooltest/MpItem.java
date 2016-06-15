package net.macdidi.mptooltest;

public class MpItem {
    public int mIndex;
    public String mDesc;
    public String mResult;

    public MpItem() {
        mIndex = 0;
        mDesc = "Test Description";
        mResult = "-";
    }

    public MpItem(int mIndex, String mDesc, String mResult) {
        this.mDesc = mDesc;
        this.mIndex = mIndex;
        this.mResult = mResult;
    }
}
