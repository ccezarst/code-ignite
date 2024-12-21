package org.firstinspires.ftc.teamcode.CustomComponents;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.HW_HwMap;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

public class OuttakeInterface extends HardwareInterface {
    public OuttakeInterface(Boolean active, DefaultCore core) {
        super("OuttakeInterface", active, core, InterfaceType.OUTTAKE);
    }

    private DcMotor rightMotor;
    private DcMotor leftMotor;

    private Servo rightRotate;
    private Servo leftRotate;

    private Servo outtakeServo;

    private Servo clawServo;

    private int lowLimmit = 5;
    private int highLimit = 2800;

    // 0 to 100
    public void extend(double amount){
        int finalAm = (int) Math.round((((double) amount / 100) * this.highLimit) + this.lowLimmit);
        this.rightMotor.setTargetPosition(finalAm);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.rightMotor.setPower(1);
        this.leftMotor.setTargetPosition(finalAm);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        this.leftMotor.setPower(1);
        this.last = amount;
    }
    private double last;
    public void subtract(double am){
        this.extend(last - am);
    }

    public void add(double am){
        this.extend(last + am);
    }
    @Override
    public void step(DefaultCore core) {

    }

    private HW_HwMap getHwMap(){
        return ((HW_HwMap) this.core.getInterfacesOfType(InterfaceType.HARDWARE_MAP).get(0));
    }

    // 0 -> 100
    public void rotateAss(double pos){
        this.leftRotate.setPosition((double) pos / 100);
        this.rightRotate.setPosition((double) pos / 100);
    }
    // 0 -> 100
    public void secondRotateBasket(double pos){
        this.outtakeServo.setPosition((double) pos / 100);
    }

    public void openClaw(){
        this.clawServo.setPosition(1);
    }

    public void closeClaw(){
        this.clawServo.setPosition(0);
    }

    @Override
    public void update(DefaultCore core) {
        this.rightMotor = (DcMotor) this.getHwMap().hwMap.get("OUTmotorR");
        this.rightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.rightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightMotor.setPower(0);
        this.leftMotor = (DcMotor) this.getHwMap().hwMap.get("OUTmotorL");
        this.leftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.leftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        this.leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftMotor.setPower(0);

        this.leftRotate = (Servo) this.getHwMap().hwMap.get("outtakeServoL");
        this.leftRotate.setDirection(Servo.Direction.REVERSE);
        this.rightRotate = (Servo) this.getHwMap().hwMap.get("outtakeServoR");
        this.rightRotate.setDirection(Servo.Direction.FORWARD);
        this.outtakeServo = (Servo) this.getHwMap().hwMap.get("outtakeRotServo");
        this.outtakeServo.setDirection(Servo.Direction.FORWARD);
        this.outtakeServo.setPosition(0.5);
        this.clawServo = (Servo) this.getHwMap().hwMap.get("clawServo");
        this.clawServo.setDirection(Servo.Direction.REVERSE);
    }
}
