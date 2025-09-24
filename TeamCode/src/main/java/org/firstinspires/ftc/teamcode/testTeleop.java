package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.Implementations.CH_ImuLocalizer;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Configs.JSONConfigManager;
import org.firstinspires.ftc.teamcode.TeamCore.RobotTCore;
import org.firstinspires.ftc.teamcode.TeamCore.ManualCore;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.*;
@TeleOp
public class testTeleop extends OpMode {
    private RobotTCore core;

    @Override
    public void init() {
        OldCustomGamepad gp1 = new OldCustomGamepad(gamepad1, 1);
        OldCustomGamepad gp2 = new OldCustomGamepad(gamepad2, 2);
        ManualCore core = new ManualCore(telemetry, hardwareMap, gp1, gp2);
        core.useInstrumentation = false;
        //core.addComponent(new PeripheralValuePrinter(true, core));
        core.addComponent(new MecanumPedroDriveBase(true, core));
        core.addComponent(new JSONConfigManager(true, core));
        core.addComponent(new CH_ImuLocalizer(true ,core));
        //core.enableDebugging();
        core.init();
        telemetry.addLine("Components initialsed");
        telemetry.addLine("Waiting for start..");
        System.out.println("Waiting for start");
        telemetry.update();
        this.core = core;
        this.telemetry = telemetry;
    }

    @Override
    public void start(){
        this.core.start();
    }

    @Override
    public void stop(){
        core.exit();
    }

    @Override
    public void loop() {
        //this.core.step();
        //this.telemetry.update();
    }
}
