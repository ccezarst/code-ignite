package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;
import java.util.Date;

public class SW_Telemetry extends SW_UserInterface {
    public final Telemetry telemetry;
    private boolean busy = false;

    public SW_Telemetry(String cName, Boolean active, DefaultCore core, Telemetry telem) {
        super(cName, active, core);
        this.telemetry = telem;
    }

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {

    }

    private boolean checkButtonOnAllGamepads(InputSource sor, Gamepad.Button button){
        for(int i = 0; i < sor.gps.length; i++){
            if(sor.checkHold(sor.gps[i].getNumber(), button)){
                return true;
            }
        }
        return false;
    }

    private int getAction(){
        ArrayList<CoreComponent> inSources = this.core.getComponentsOfType(ComponentType.INPUT_SOURCE);
        for(int i = 0; i < inSources.size(); i++){
            InputSource cur = (InputSource) inSources.get(i);
            if(checkButtonOnAllGamepads(cur, Gamepad.Button.DPAD_UP)){
                return 1;
            }else if(checkButtonOnAllGamepads(cur, Gamepad.Button.DPAD_DOWN)){
                return 2;
            }else if(checkButtonOnAllGamepads(cur, Gamepad.Button.A)){
                return 3;
            }else if(checkButtonOnAllGamepads(cur, Gamepad.Button.B)){
                return 4;
            }
        }
        return 0;
    }

    @Override
    public int showMenu(ArrayList<String> options) {
        this.busy = true;
        int selection = 1;
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0L;
        while(this.busy){
            elapsedTime = System.currentTimeMillis() - startTime;
            if(elapsedTime > 300){
                telemetry.addLine("Select an option using the DPAD, confirm pressing A, cancel pressing B");
                for(int i = 0; i < options.size(); i++){
                    if(i == selection - 1){
                        this.telemetry.addLine(options.get(i) + "<--");
                    }else{
                        this.telemetry.addLine(options.get(i));
                    }
                }
                this.telemetry.update();
                int act = this.getAction();
                switch(act){
                    case 1:
                        if(selection > 0){
                            selection -= 1;
                        }
                        break;
                    case 2:
                        if(selection < options.size()){
                            selection += 1;
                        }
                        break;
                    case 3:
                        this.busy = false;
                        break;
                    case 4:
                        selection = 0;
                        this.busy = false;
                        break;
                }
                startTime = System.currentTimeMillis();
            }
        }
        // get input sources, wait for user to press on any gamepad A to confirm
        return selection;
    }

    @Override
    public void print(String toPrint) {
        this.telemetry.addLine(toPrint);
    }

    @Override
    public void printLine(String toPrint) {
        this.telemetry.addLine(toPrint + "\n");
    }

    @Override
    public void updatePrint() {
        this.telemetry.update();
    }

    @Override
    public boolean isBusy() {
        return this.busy;
    }
}
