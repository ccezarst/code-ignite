package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ButtonOutput;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.PrintCoreStatusBind;
import org.firstinspires.ftc.teamcode.Core.ManualCore;

import java.util.Timer;

@TeleOp
public class ManualCoreTest extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad gp1 = new Gamepad(gamepad1, 1);
        Gamepad gp2 = new Gamepad(gamepad2, 2);
        ManualCore core = new ManualCore(hardwareMap, gp1, gp2);
        core.addComponent(new PrintCoreStatusBind(1, core, telemetry));
        core.addComponent(new ButtonOutput("ButtonOutGP1", true, core, telemetry));

        telemetry.addLine("Waiting for start..");
        telemetry.update();
        core.init();
        this.waitForStart();
        int i = 0;
        while(opModeIsActive()) {
            core.step();
            telemetry.update();
        }
    }
}
