package org.firstinspires.ftc.teamcode.TeamCore.Input;

import com.qualcomm.robotcore.hardware.Gamepad;


import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.AnalogTypes;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.InputSource;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.OldCustomGamepad;

import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;
public class CustomGamepad extends InputSource {
    private OldCustomGamepad gamepad;
    public CustomGamepad(String name, Boolean active, EngineCore core, int inputSourceID, Gamepad gamepad) {
        super(name, active, core, inputSourceID);
        this.gamepad = new OldCustomGamepad(gamepad, inputSourceID);
    }

    @Override
    protected void step(EngineCore core) {
        synchronized(this.buttonStates){
            synchronized (this.analogStates){
                for(ButtonTypes btn: ButtonTypes.values()){
                    this.buttonStates.put(btn, gamepad.checkHold(btn));
                }
                for(AnalogTypes an: AnalogTypes.values()){
                    this.analogStates.put(an, (double) gamepad.getAnalog(an));
                }
            }
        }
        this.sendInputs();
    }

    @Override
    protected void update(EngineCore core) {
        System.out.println(this.name + "-> Initialising actions for buttons");
        this.registerActionsForButtons(ButtonTypes.values()); // because the gamepad has them all for now
        System.out.println(this.name + "-> Inited actions for buttons");
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
