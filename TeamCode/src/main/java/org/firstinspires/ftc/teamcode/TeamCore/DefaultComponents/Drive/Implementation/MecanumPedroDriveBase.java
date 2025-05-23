package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.Implementation;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.DriveBase;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing.Point;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class MecanumPedroDriveBase extends DriveBase {
    public MecanumPedroDriveBase(Boolean active, TeamCore core) {
        super(active, core);
    }

    @Override
    public void rotateRobotCentric(double angle) {

    }

    // !!! ADD CALIBRATION FOR RADIUS MODIFIER SO IT REPRESENTS
    public static final double magnitudeModifier = 5.0;
    public final HardwareMap hw = this.core.getGlobalVariable("HardwareMap", HardwareMap.class);
    public final DcMotor FR = hw.dcMotor.get("front_right");
    public final DcMotor FL = hw.dcMotor.get("front_left");
    public final DcMotor BL = hw.dcMotor.get("back_left");
    public final DcMotor BR = hw.dcMotor.get("back_right");
    @Override
    public void moveRobotCentricPolar(double radius, double angle) {
        double FRBL = Math.sin(angle - 1/4* Point.pi) * radius*magnitudeModifier;
        double FLBR = Math.sin(angle + 1/4* Point.pi) * radius*magnitudeModifier;
        synchronized (this.FR){
            synchronized (this.BL){
                synchronized (this.FL){
                    synchronized (this.BR){
                        FR.setTargetPosition((int) (FR.getCurrentPosition() + FRBL));
                        BL.setTargetPosition((int) (BL.getCurrentPosition() + FRBL));
                        FL.setTargetPosition((int) (FL.getCurrentPosition() + FLBR));
                        BR.setTargetPosition((int) (BR.getCurrentPosition() + FLBR));

                        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    }
                }
            }
        }
    }

    @Override
    protected void step(TeamCore core) {

    }

    @Override
    protected void update(TeamCore core) {
        synchronized (this.FR){
            synchronized (this.FL){
                synchronized (this.BR){
                    synchronized (this.BL){
                        FR.setDirection(DcMotorSimple.Direction.FORWARD);
                        FR.setPower(1);
                        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                        FL.setDirection(DcMotorSimple.Direction.REVERSE);
                        FL.setPower(1);
                        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                        BR.setDirection(DcMotorSimple.Direction.FORWARD);
                        BR.setPower(1);
                        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                        BL.setDirection(DcMotorSimple.Direction.REVERSE);
                        BL.setPower(1);
                        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
                        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
                        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                    }
                }
            }
        }
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
