package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.Implementation.MecanumPedroDriveBase;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.Localization.Implementations.CH_ImuLocalizar;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Extra.CoreComponentTester;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Extra.PeripheralValuePrinter;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Extra.PrintCoreStatusBind;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Configs.JSONConfigManager;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.OldCustomGamepad;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.State;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.TeamCore.ManualCore;
import org.firstinspires.ftc.teamcode.CustomComponents.IntakeInterface;
import org.firstinspires.ftc.teamcode.CustomComponents.OuttakeInterface;
import org.firstinspires.ftc.teamcode.CustomComponents.States.DropBlockFromIntake;
import org.firstinspires.ftc.teamcode.CustomComponents.States.DropBlockFromOuttake;
import org.firstinspires.ftc.teamcode.CustomComponents.States.DropSpecimen;
import org.firstinspires.ftc.teamcode.CustomComponents.States.HoldingBlock;
import org.firstinspires.ftc.teamcode.CustomComponents.States.HoldingBlockInOuttake;
import org.firstinspires.ftc.teamcode.CustomComponents.States.HoldingSpecimenOne;
import org.firstinspires.ftc.teamcode.CustomComponents.States.HoldingSpecimenTwo;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Idle;
import org.firstinspires.ftc.teamcode.CustomComponents.States.IntakeHIGH;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Outtake_FirstBasket;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Outtake_SecondBasket;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Transfer;
import org.firstinspires.ftc.teamcode.CustomComponents.States.WaitingForBlock;
import org.firstinspires.ftc.teamcode.CustomComponents.States.WaitingForSpecimen;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

import java.util.ArrayList;

@TeleOp
public class testTeleop extends OpMode {
    private TeamCore core;
    private Telemetry telem;

    @Override
    public void init() {
        OldCustomGamepad gp1 = new OldCustomGamepad(gamepad1, 1);
        OldCustomGamepad gp2 = new OldCustomGamepad(gamepad2, 2);
        ManualCore core = new ManualCore(telemetry, hardwareMap, gp1, gp2);
        core.threads = 10;
        //TeamCore core = new TeamCore(telem, hardwareMap);
        //core.addComponent(new PeripheralValuePrinter(true, core));
        //core.addComponent(new MecanumPedroDriveBase(true, core));
        //core.addComponent(new JSONConfigManager(true, core));
        core.addComponent(new CH_ImuLocalizar(true ,core));
        core.init();
        telemetry.addLine("Waiting for start..");
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
        this.core.step();
        this.telemetry.update();
    }
}
