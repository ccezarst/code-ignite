package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.GeneralInputMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.Core.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;
import org.firstinspires.ftc.teamcode.Gamepad;

import java.util.ArrayList;
import java.util.Date;

public class SW_Telemetry extends SW_UserInterface {
    public final Telemetry telemetry;
    private boolean busy = false;

    public SW_Telemetry(Boolean active, DefaultCore core, Telemetry telem) {
        super("SW_Telemetry", active, core);
        this.telemetry = telem;
    }

    @Override
    public void step(DefaultCore core) {

    }

    @Override
    public void update(DefaultCore core) {

    }

    private boolean checkButtonOnAllInputSources(ButtonTypes button){
        GeneralInputMapper mapper = (GeneralInputMapper) this.core.getComponentFromName("DefaultGeneralInputMapper");
        return mapper.checkButtonDownOnAll(button);
    }

    private int getAction(){
        if(checkButtonOnAllInputSources(ButtonTypes.DPAD_UP)){
            return 1;
        }else if(checkButtonOnAllInputSources(ButtonTypes.DPAD_DOWN)){
            return 2;
        }else if (checkButtonOnAllInputSources(ButtonTypes.A)){
            return 3;
        }else if(checkButtonOnAllInputSources(ButtonTypes.B)){
            return 4;
        }
        return 0;
    }

    int selection = 0;
    int privSelection = 0;
    long startTime = System.currentTimeMillis();
    long elapsedTime = 0L;

    @Override
    public int showMenu(String title, ArrayList<String> options) {
        if(privSelection == 0){
            selection = 0;
            privSelection = 1;
            startTime = System.currentTimeMillis();
            elapsedTime = 0;
        }
        elapsedTime = System.currentTimeMillis() - startTime;
        telemetry.addLine("Select an option using the DPAD, confirm pressing A, cancel pressing B");
        telemetry.addLine(title + "\n");
        for(int i = 0; i < options.size(); i++){
            if(i == privSelection - 1){
                this.telemetry.addLine(options.get(i) + "  <--");
            }else{
                this.telemetry.addLine(options.get(i));
            }
        }
        this.telemetry.update();
        if(elapsedTime > 300){
            int act = this.getAction();
            if(act != 0){
                startTime = System.currentTimeMillis();

            }
            switch(act){
                case 1:
                    if(privSelection > 1){
                        privSelection -= 1;
                    }
                    break;
                case 2:
                    if(privSelection < options.size()){
                        privSelection += 1;
                    }
                    break;
                case 3:
                    this.busy = false;
                    selection = privSelection;
                    break;
                case 4:
                    privSelection = 0;
                    selection = -1;
                    this.busy = false;
                    break;
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
