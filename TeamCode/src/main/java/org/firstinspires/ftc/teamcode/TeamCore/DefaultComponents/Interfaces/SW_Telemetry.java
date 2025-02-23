package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.GeneralInputMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.InputMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SW_UserInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SoftwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.InputSource;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;
import java.util.Date;
import java.util.function.Consumer;

public class SW_Telemetry extends SW_UserInterface {
    public Telemetry telemetry;
    private boolean busy = false;

    public SW_Telemetry(Boolean active, TeamCore TeamCore) {
        super("SW_Telemetry", active, TeamCore);
    }



    @Override
    public void print(String toPrint, boolean pl) {if(this.telemetry != null && !this.busy){this.telemetry.addLine(toPrint);}}
    @Override
    public void printLine(String toPrint, boolean pl) {if(this.telemetry != null && !this.busy) {this.telemetry.addLine(toPrint + "\n");}}
    private String menuTitle = "";
    private ArrayList<String> menuOptions = new ArrayList<>();
    private Consumer<Integer> menuCallback;
    private int selection = 0;

    @Override
    public void showMenu(String title, ArrayList<String> options, Consumer<Integer> callback) {
        if(!this.busy && this.telemetry != null){
            this.menuTitle = title;
            this.menuOptions = options;
            this.menuCallback = callback;
            this.busy = true;
            this.selection = 0;
        }
    }

    @Override
    public void updatePrint() {if(this.telemetry != null){this.telemetry.update();}}

    @Override
    public boolean isBusy() {
        return this.busy;
    }

    @Override
    public void step(TeamCore core) {
        if(this.busy && this.telemetry != null){
            this.telemetry.addLine("Browse menu(GP1) -> DPAP UP/DOWN, confirm -> A, cancel -> B");
            this.telemetry.addLine(this.menuTitle);
            int count = 0;
            for(String option: this.menuOptions){
                if(count == selection){
                    this.telemetry.addLine(option + " <----");
                }else{
                    this.telemetry.addLine(option);
                }
                count += 1;
            }
        }
    }

    @Override
    public void customUpdate(TeamCore core) {
        this.telemetry = this.core.getGlobalVariable("Telemetry", Telemetry.class);
    }
    private boolean pressed = false;
    @Override
    public void statesUpdated() {
        if(this.busy){
            int caca = 0;
            if(this.states.get(1).getButtonState(ButtonTypes.DPAD_DOWN)){
                if(!this.pressed){
                    if(this.selection < this.menuOptions.size() - 1){
                        this.selection += 1;
                    }
                    this.pressed = true;
                }
            }else{
                caca += 1;
            }
            if(this.states.get(1).getButtonState(ButtonTypes.DPAD_UP)){
                if(!this.pressed){
                    if(this.selection > 0){
                        this.selection -= 1;
                    }
                    this.pressed = true;

                }
                
            }else{
                caca += 1;
            }
            if(this.states.get(1).getButtonState(ButtonTypes.A)){
                if(!this.pressed){
                    this.busy = false;
                    this.menuCallback.accept(this.selection);
                    this.pressed = true;
                }
                
            }else{
                caca += 1;
            }
            if(this.states.get(1).getButtonState(ButtonTypes.B)){
                if(!this.pressed){
                    this.busy = false;
                    this.menuCallback.accept(-1);
                    this.pressed = true;
                }
                
            }else{
                caca += 1;
            }
            if(caca == 4){
                this.pressed = false;
            }
        }
    }


    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }
}