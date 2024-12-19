package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
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
import org.firstinspires.ftc.teamcode.CustomComponents.OuttakeInterface;
import org.firstinspires.ftc.teamcode.CustomComponents.States.DropBlockFromIntake;
import org.firstinspires.ftc.teamcode.CustomComponents.States.DropBlockFromOuttake;
import org.firstinspires.ftc.teamcode.CustomComponents.States.HoldingBlock;
import org.firstinspires.ftc.teamcode.CustomComponents.States.HoldingBlockInOuttake;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Idle;
import org.firstinspires.ftc.teamcode.CustomComponents.States.IntakeHIGH;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Outtake_SecondBasket;
import org.firstinspires.ftc.teamcode.CustomComponents.States.Transfer;
import org.firstinspires.ftc.teamcode.CustomComponents.States.WaitingForBlock;

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
        core.addComponent(new IntakeInterface(true, core));
        core.addComponent(new OuttakeInterface(true, core));
        //core.addComponent(new PeripheralValuePrinter("perif", true, core));

        //core.addComponent(new ButtonOutput("ButtonOutGP1", true, core, telemetry));
        //core.addComponent(new PeripheralValuePrinter("PeripheralValuePrinter", true, core));
        ArrayList<State> states =  new ArrayList<State>();
        Idle idleState = new Idle();
        idleState.inputs.add("DropBlockFromIntake");
        idleState.inputs.add("IntakeHIGH");
        idleState.inputs.add("DropBlockFromOuttake");
        idleState.outputs.add("IntakeHIGH");
        states.add(idleState);
        IntakeHIGH intakeHIGH = new IntakeHIGH();
        intakeHIGH.inputs.add("Idle");
        intakeHIGH.inputs.add("WaitingForBlock");
        intakeHIGH.outputs.add("Idle");
        intakeHIGH.outputs.add(0, "WaitingForBlock");
        states.add(intakeHIGH);
        WaitingForBlock WaitingForBlock = new WaitingForBlock();
        WaitingForBlock.inputs.add("IntakeHIGH");
        WaitingForBlock.outputs.add("HoldingBlock");
        WaitingForBlock.outputs.add("IntakeHIGH");
        states.add(WaitingForBlock);
        HoldingBlock HoldingBlock = new HoldingBlock();
        HoldingBlock.inputs.add("WaitingForBlock");
        HoldingBlock.outputs.add("Transfer");
        HoldingBlock.outputs.add("DropBlockFromIntake");
        states.add(HoldingBlock);
        DropBlockFromIntake DropBlockFromIntake = new DropBlockFromIntake();
        DropBlockFromIntake.inputs.add("HoldingBlock");
        DropBlockFromIntake.outputs.add("Idle");
        states.add(DropBlockFromIntake);
        Transfer Transfer = new Transfer();
        Transfer.inputs.add("HoldingBlock");
        Transfer.outputs.add("HoldingBlockInOuttake");
        states.add(Transfer);
        HoldingBlockInOuttake HoldingBlockInOuttake = new HoldingBlockInOuttake();
        HoldingBlockInOuttake.inputs.add("Transfer");
        HoldingBlockInOuttake.outputs.add("Outtake_SecondBasket");
        states.add(HoldingBlockInOuttake);
        Outtake_SecondBasket Outtake_SecondBasket = new Outtake_SecondBasket();
        Outtake_SecondBasket.inputs.add("HoldingBlockInOuttake");
        Outtake_SecondBasket.outputs.add("DropBlockFromOuttake");
        states.add(Outtake_SecondBasket);
        DropBlockFromOuttake DropBlockFromOuttake = new DropBlockFromOuttake();
        DropBlockFromOuttake.inputs.add("Outtake_SecondBasket");
        DropBlockFromOuttake.outputs.add("Idle");
        states.add(DropBlockFromOuttake);
        StateMachine stateMachine = new StateMachine(states, true, "IntakeStateMachine", core);
        core.addComponent(stateMachine);
        core.addComponent(new ButtonA("stbtnMap", true, core));
        core.addComponent(new ButtonDPAD_DOWN("stbtnMap2", true, core));
        core.addComponent(new ButtonTRIGGER_LEFT("stbtnMap3", true, core));

        //core.removeComponent("ManualDrivingManager");

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
        core.exit();
    }
}
