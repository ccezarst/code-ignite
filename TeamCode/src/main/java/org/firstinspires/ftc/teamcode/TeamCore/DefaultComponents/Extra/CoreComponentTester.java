package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Extra;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.InputMappers.Template.OneButtonMapper;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;

public class CoreComponentTester extends OneButtonMapper {
    public CoreComponentTester(Boolean active, TeamCore core) {
        super("CoreComponentTester", active, core, ButtonTypes.START, 2);
    }

    @Override
    public void step(TeamCore core) {

    }

    @Override
    public void customUpdate(TeamCore core) {
    }

    @Override
    public void buttonPressed() {
        ArrayList<String> res = this.core.testComponents();
        String finalRes = "";
        for(String caca: res){
            finalRes += caca + "\n";
        }
        this.core.getComponentFromName("UI_Manager", UI_Manager.class).showWarning(finalRes);
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
