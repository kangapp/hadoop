package com.hadoop.domain;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MatchCount implements Writable {

    private int shotNum;
    private int score;

    public MatchCount(int shotNum, int score) {
        this.shotNum = shotNum;
        this.score = score;
    }

    public int getShotNum() {
        return shotNum;
    }

    public void setShotNum(int shotNum) {
        this.shotNum = shotNum;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "投篮数："+shotNum +"\t"+"得分："+score;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(shotNum);
        dataOutput.writeInt(score);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.shotNum = dataInput.readInt();
        this.score = dataInput.readInt();
    }
}
