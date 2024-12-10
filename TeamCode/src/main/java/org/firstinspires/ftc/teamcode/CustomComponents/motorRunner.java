package org.firstinspires.ftc.teamcode.CustomComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.OneButtonMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SW_Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;
import java.util.Objects;

public class motorRunner extends OneButtonMapper {

    private DcMotor rightMotor;
    private DcMotor leftMotor;

    private int lowLimmit = 5;
    private int highLimit = 500;

    public motorRunner(DefaultCore core) {
        super(Gamepad.Button.Y, true, 1, "caca", true, core);
    }

    public void extend(double amount){
        int finalAm = (int) Math.round((amount * this.highLimit) + this.lowLimmit);
        this.rightMotor.setTargetPosition(finalAm);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setPower(0.5);
        this.leftMotor.setTargetPosition(finalAm);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.leftMotor.setPower(0.5);
        Objects.requireNonNull(this.getTelemetry()).telemetry.addLine(String.valueOf(finalAm));


    }
    private double currentPos = 0;
    @Override
    public void callback() {
        this.extend((double) this.currentPos / 100);
        this.currentPos += 0.2;
        if(this.currentPos >= 100){
            this.currentPos = 10;
        }
        Objects.requireNonNull(this.getTelemetry()).telemetry.addLine(String.valueOf(this.currentPos));
    }

    private HW_HwMap getHwMap(){
        ArrayList<CoreComponent> interfs = this.core.getComponentsOfType(ComponentType.HARDARE_INTERFACE);
        for(int i = 0; i < interfs.size(); i++){
            if(((HardwareInterface)(interfs.get(i))).interfaceType == InterfaceType.HARDWARE_MAP){
                return (HW_HwMap) interfs.get(i);
            }
        }
        return null;
    }

    private SW_Telemetry getTelemetry(){
        ArrayList<CoreComponent> interfs = this.core.getComponentsOfType(ComponentType.SOFTWARE_INTERFACE);
        for(int i = 0; i < interfs.size(); i++){
            if(((SoftwareInterface)(interfs.get(i))).interfaceType == InterfaceType.TELEMETRY){
                return (SW_Telemetry) interfs.get(i);
            }
        }
        return null;
    }

    @Override
    public void update(DefaultCore core) {
        this.rightMotor = (DcMotor) this.getHwMap().hwMap.get("motorR");
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightMotor.setPower(0);
        this.leftMotor = (DcMotor) this.getHwMap().hwMap.get("motorL");
        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.leftMotor.setPower(0);
    }
}
