package com.hadoop.mapreduce.basketMess.domain;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MatchData implements Writable {

    private String ptsType;
    private String shotResult;

    public MatchData() {
        super();
    }

    public MatchData(MatchData data) {
        this.ptsType = data.ptsType;
        this.shotResult = data.shotResult;
    }

    public MatchData(String ptsType, String shotResult) {
        this.ptsType = ptsType;
        this.shotResult = shotResult;
    }

    public String getPtsType() {
        return ptsType;
    }

    public void setPtsType(String ptsType) {
        this.ptsType = ptsType;
    }

    public String getShotResult() {
        return shotResult;
    }

    public void setShotResult(String shotResult) {
        this.shotResult = shotResult;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeUTF(ptsType);
        dataOutput.writeUTF(shotResult);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.ptsType = dataInput.readUTF();
        this.shotResult = dataInput.readUTF();
    }
}
