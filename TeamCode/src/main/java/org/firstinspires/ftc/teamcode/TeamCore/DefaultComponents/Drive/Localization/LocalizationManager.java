package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.Localization;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class LocalizationManager extends CoreComponent implements Runnable {
    public LocalizationManager(String name, Boolean active, TeamCore core) {
        super(name, active, core, ComponentType.LOCALIZATION_MANAGER);
    }

    @Override
    public void run() {
        while(true){

        }
    }

    @Override
    protected void step(TeamCore core) {

    }

    @Override
    protected void update(TeamCore core) {

    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
