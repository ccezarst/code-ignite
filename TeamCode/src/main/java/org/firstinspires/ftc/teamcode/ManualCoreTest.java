package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Extra.PeripheralValuePrinter;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.ButtonOutput;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.PrintCoreStatusBind;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.SW_Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.State;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Core.ManualCore;
import org.firstinspires.ftc.teamcode.CustomComponents.IdleState;
import org.firstinspires.ftc.teamcode.CustomComponents.motorRunnerTwo;

import java.util.ArrayList;

@TeleOp
public class ManualCoreTest extends LinearOpMode {

    private ArrayList<HardwareInterface> tohwintf(ArrayList<CoreComponent> caca){
        ArrayList<HardwareInterface> toRet = new ArrayList<>();
        for(int i = 0; i < caca.size(); i++){
            toRet.add((HardwareInterface) caca.get(i));
        }
        return toRet;
    }

    private ArrayList<SoftwareInterface> toswintf(ArrayList<CoreComponent> caca){
        ArrayList<SoftwareInterface> toRet = new ArrayList<>();
        for(int i = 0; i < caca.size(); i++){
            toRet.add((SoftwareInterface) caca.get(i));
        }
        return toRet;
    }

    @Override
    public void runOpMode() throws InterruptedException {
        Gamepad gp1 = new Gamepad(gamepad1, 1);
        Gamepad gp2 = new Gamepad(gamepad2, 2);
        ManualCore core = new ManualCore(hardwareMap, 1, gp1, gp2);
        core.addComponent(new PrintCoreStatusBind(1, core, telemetry));
        core.addComponent(new ButtonOutput("ButtonOutGP1", true, core, telemetry));
        core.addComponent(new SW_Telemetry("TelemetryInterface", true, core, telemetry));
        core.addComponent(new PeripheralValuePrinter("PeripheralValuePrinter", true, core));
        core.addComponent(new motorRunnerTwo(core));
        ArrayList<State> intakeStates = new ArrayList<State>();
        ArrayList<String> in = new ArrayList<>();
        in.add("IdleState");
        ArrayList<String> out = new ArrayList<>();
        out.add("IdleState");
        intakeStates.add(new IdleState("IdleState", in, out));
        StateMachine intakeStateMach = new StateMachine(tohwintf(core.getComponentsOfType(ComponentType.HARDARE_INTERFACE)), toswintf(core.getComponentsOfType(ComponentType.SOFTWARE_INTERFACE)), intakeStates, true, "IntakeStateMachine", core);
        core.init();
        intakeStateMach.changeState("IdleState");
        telemetry.addLine("Waiting for start..");
        telemetry.update();
        this.waitForStart();
        int i = 0;
        while(opModeIsActive()) {
            core.step();
            telemetry.update();
        }
    }
}
