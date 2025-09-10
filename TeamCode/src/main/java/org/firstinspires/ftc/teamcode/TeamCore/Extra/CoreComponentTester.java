package org.firstinspires.ftc.teamcode.TeamCore.Extra;


import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.TeamCore.Managers.UI_Manager;

import EngineCore.TestingEnviromentCore;
import EngineCore.EngineCore;

import java.util.ArrayList;

public class CoreComponentTester extends OneButtonMapper {
    public CoreComponentTester(Boolean active, EngineCore core) {
        super("CoreComponentTester", active, core, ButtonTypes.BACK, 2);
    }

    @Override
    public void step(EngineCore core) {

    }

    @Override
    public void customUpdate(EngineCore core) {
    }
    private boolean busy = false;
    private ArrayList<String> responses;
    private int current = 0;


    private void acceptCallback(Integer select){
        synchronized (this.responses){
            ArrayList<String> opts = new ArrayList<>();
            opts.add("Confirm");
            opts.add("Back");
            switch(select){
                case -1:
                case 1:
                    if(this.current > 0){
                        this.current -= 1;
                    }else{
                        this.busy = false;
                    }
                    break;
                case 0:
                    if(this.current < this.responses.size() - 1){
                        this.current += 1;
                    }else{
                        this.busy = false;
                    }
                    break;
            }
            if(this.busy){
                this.core.getComponentFromName("UI_Manager", UI_Manager.class).showMenu(this.responses.get(current), opts, this::acceptCallback);
            }
        }
    }
    @Override
    public void buttonPressed() {
        if(!busy){
            synchronized (this.responses){
                this.busy = true;
                this.current = 0;
                this.responses = this.core.testComponents();
                ArrayList<String> opts = new ArrayList<>();
                opts.add("Confirm");
                opts.add("Back");
                this.core.getComponentFromName("UI_Manager", UI_Manager.class).showMenu(responses.get(0), opts, this::acceptCallback);
            }
        }
    }

    @Override
    public void buttonDown() {

    }

    @Override
    public void buttonUp() {

    }

    @Override
    public void buttonToggle() {

    }

    @Override
    public int test(TestingEnviromentCore core) {
        return 0;
    }
}
