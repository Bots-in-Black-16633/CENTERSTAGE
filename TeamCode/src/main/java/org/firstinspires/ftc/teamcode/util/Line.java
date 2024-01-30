package org.firstinspires.ftc.teamcode.util;

public class Line {

    double slope;
    double xPos;
    double yPos;
    public Line(double x1, double y1, double x2, double y2){
     this.slope = (y2-y1)/(x2-x1);
     this.xPos = x2;
     this.yPos = y2;
    }

    public double calculate(double xPos){
        //y = m(x-x1)+y1
        return slope*(xPos-this.xPos)+this.yPos;
    }
}
