package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Extra.PeripheralValuePrinter;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.PrintCoreStatusBind;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.State;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.StateMachine.StateMachine;
import org.firstinspires.ftc.teamcode.Core.ManualCore;
import org.firstinspires.ftc.teamcode.CustomComponents.ButtonA;
import org.firstinspires.ftc.teamcode.CustomComponents.ButtonDPAD_DOWN;
import org.firstinspires.ftc.teamcode.CustomComponents.ButtonTRIGGER_LEFT;
import org.firstinspires.ftc.teamcode.CustomComponents.IntakeInterface;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Idle;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Intake_HIGH;

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
        ManualCore core = new ManualCore(telemetry, hardwareMap, 1, gp1, gp2);
        core.addComponent(new PrintCoreStatusBind(1, core, telemetry));
        core.addComponent(new IntakeInterface("intake", true, core));
        core.addComponent(new PeripheralValuePrinter("perif", true, core));

        //core.addComponent(new ButtonOutput("ButtonOutGP1", true, core, telemetry));
        //core.addComponent(new PeripheralValuePrinter("PeripheralValuePrinter", true, core));
        ArrayList<State> states =  new ArrayList<State>();
        Idle idleState = new Idle();
        idleState.inputs.add("IntakeHIGH");
        idleState.outputs.add("IntakeHIGH");
        states.add(idleState);
        Intake_HIGH intakeHIGH = new Intake_HIGH();
        intakeHIGH.inputs.add("Idle");
        intakeHIGH.outputs.add("Idle");
        states.add(intakeHIGH);
        StateMachine stateMachine = new StateMachine(states, true, "IntakeStateMachine", core);
        core.addComponent(stateMachine);
        //core.addComponent(new ButtonA("stbtnMap", true, core));
        //core.addComponent(new ButtonDPAD_DOWN("stbtnMap2", true, core));
        core.addComponent(new ButtonTRIGGER_LEFT("stbtnMap3", true, core));

        core.removeComponent("ManualDrivingManager");

        core.init();
        stateMachine.changeState("Idle");
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
