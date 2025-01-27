package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Actions.ActionDataContainer;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.SW_UserInterface;
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
    protected void update(TeamCore core) {
        this.telemetry = this.core.getGlobalVariable("Telemetry", Telemetry.class);
        this.core.subscribeToAction("1" + ButtonTypes.DPAD_DOWN.name() + "_PRESSED", (ActionDataContainer data) ->{this.dpadDown_pressed();});
        this.core.subscribeToAction("1" + ButtonTypes.DPAD_UP.name() + "_PRESSED", (ActionDataContainer data) ->{this.dpadUp_pressed();});
        this.core.subscribeToAction("1" + ButtonTypes.A.name() + "_PRESSED", (ActionDataContainer data) ->{this.a_pressed();});
        this.core.subscribeToAction("1" + ButtonTypes.B.name() + "_PRESSED", (ActionDataContainer data) ->{this.b_pressed();});
    }
    public void dpadDown_pressed(){
        if(this.selection < this.menuOptions.size() - 1){
            this.selection += 1;
        }
    }
    private void dpadUp_pressed(){
        if(this.selection > 0){
            this.selection -= 1;
        }
    }
    private void a_pressed(){
        this.busy = false;
        this.menuCallback.accept(this.selection);
    }
    private void b_pressed(){
        this.busy = false;
        this.menuCallback.accept(-1);
    }

    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }
}