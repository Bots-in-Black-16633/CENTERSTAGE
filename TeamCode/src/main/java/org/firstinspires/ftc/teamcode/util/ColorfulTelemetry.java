package org.firstinspires.ftc.teamcode.util;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.stream.CameraStreamSource;

public class ColorfulTelemetry  {
   private MultipleTelemetry telemetry;

   //Constants
   public static final String Red  = "Red";
   public static final String Orange  = "Orange";
   public static final String Yellow  = "Yellow";
   public static final String Green  = "Green";
   public static final String Blue  = "Blue";
   public static final String Purple  = "Purple";
   public static final String Black = "Black";


   public String color = "white";
   public boolean isBold = false;
   public boolean isItalic = false;
   public boolean isUnderline = false;

   FtcDashboard dash;



    public ColorfulTelemetry(Telemetry telemetry, FtcDashboard dash){
        this.telemetry=new MultipleTelemetry(telemetry, dash.getTelemetry());
        telemetry.setDisplayFormat(Telemetry.DisplayFormat.HTML);
        telemetry.setItemSeparator("");

    }

    /**
     *Adds a line to telemetry in the given color
     * @param message
     * @param color
     */
    public  ColorfulTelemetry addLine(String message, String color){
        telemetry.addData("<font color = \""+color+"\">" + message, "");
        return this;
    }
    public ColorfulTelemetry addLine(String message){
        telemetry.addLine(format(message));
        return this;
    }
    public ColorfulTelemetry addData(String title, Object data){
        telemetry.addData(format(title),data);
        return this;
    }
    public ColorfulTelemetry addData(String title, int data){
        telemetry.addData(format(title),data);
        return this;
    }
    public ColorfulTelemetry addLine(){
        telemetry.addLine();
        return this;
    }

    private String format(String message){
        String boldFront = isBold?"<b>":"";
        String italicFront = isItalic?"<i>":"";
        String underLineFront = isUnderline?"<u>":"";
        String boldEnd = isBold?"</b>":"";
        String italicEnd= isItalic?"</i>":"";
        String underLineEnd = isUnderline?"</u>":"";

        String frontTags = "<font color=\""+color+"\">" + boldFront + italicFront + underLineFront;
        String endTags = underLineEnd + italicEnd + boldEnd + "</font>" ;
        return frontTags + message + endTags;
    }

    public ColorfulTelemetry reset(){
        isUnderline=false;
        isBold=false;
        isItalic = false;
        color="White";

        return this;
    }

    //Setter Methods- these methods will kind of work like a pen
    public ColorfulTelemetry setUnderLine(boolean in){
        isUnderline = in;
        return this;
    }
    public ColorfulTelemetry underLine(){
        isUnderline = true;
        return this;
    }
    public ColorfulTelemetry setBold(boolean in){
        isBold = in;
        return this;
    }
    public ColorfulTelemetry bold(){
        isBold = true;
        return this;
    }
    public ColorfulTelemetry setItalic(boolean in){
        isItalic=in;
        return this;
    }
    public ColorfulTelemetry italic(){
        isItalic = true;
        return this;
    }
    public ColorfulTelemetry setColor(String in){
        color=in;
        return this;
    }


    public ColorfulTelemetry update(){
        telemetry.update();
        return this;
    }

    public void setCamera(CameraStreamSource camera){
        dash.startCameraStream(camera, 0);
    }
}
