package com.hadoop.mapreduce.basketMess.domain;

import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class MatchCount implements Writable {

    private int shot_two;
    private int shot_three;
    private int shotNum;
    private int score;

    public MatchCount() {
        super();
    }

    public MatchCount(int shot_two, int shot_three, int shotNum, int score) {
        this.shot_two = shot_two;
        this.shot_three = shot_three;
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

    public int getShot_two() {
        return shot_two;
    }

    public void setShot_two(int shot_two) {
        this.shot_two = shot_two;
    }

    public int getShot_three() {
        return shot_three;
    }

    public void setShot_three(int shot_three) {
        this.shot_three = shot_three;
    }

    @Override
    public String toString() {
        return "两分命中："+shot_two +"\t"+"三分命中："+shot_three +"\t"+"投篮数："+shotNum +"\t"+"得分："+score;
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(shot_two);
        dataOutput.writeInt(shot_three);
        dataOutput.writeInt(shotNum);
        dataOutput.writeInt(score);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.shot_two = dataInput.readInt();
        this.shot_three = dataInput.readInt();
        this.shotNum = dataInput.readInt();
        this.score = dataInput.readInt();
    }
}
