package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

public class ManualDriving extends CoreComponent {
    public ManualDriving(Boolean active, TeamCore core) {
        super("ManualDriving", active, core, ComponentType.OTHER);
    }

    @Override
    protected void step(TeamCore core) {
        man.moveRobotCentricCartesianNoPathing(-this.core.getGlobalVariable("1RIGHT_STICK_X", Double.class), -this.core.getGlobalVariable("1RIGHT_STICK_Y", Double.class));
    }
    DrivingManager man;
    @Override
    protected void update(TeamCore core) {
        synchronized (this.man){
            man = this.core.getComponentFromName("DrivingManager", DrivingManager.class);
        }
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
