package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameMap;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects.Robot;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing.Point;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;

public class DrivingManager extends CoreComponent {
    public DrivingManager(Boolean active, TeamCore core) {
        super("DrivingManager", active, core, ComponentType.DRIVING_MANAGER);
    }

    @Override
    protected void step(TeamCore core) {

    }

    public void moveRobotCentricPolarNoPathing(double radius, double angle){
        this.db.moveFieldCentricPolar(radius, angle);
    }; // move the robot in respect to it's current position radius cm in angle radians direction counteclockwise to the X axis
    public final void moveRobotCentricCartesianNoPathing(double x, double y){
        this.db.moveRobotCentricCartesian(x, y);
    }; // cm, cm

    public final void moveFieldCentricPointNoPathing(Point point){
        this.db.moveFieldCentricPoint(point);
    }
    public final void moveFieldCentricCartesianNoPathing(double x, double y){
        this.db.moveFieldCentricCartesian(x, y);
    };
    public final void moveFieldCentricPolarNoPathing(double  radius, double angle){
        this.db.moveRobotCentricPolar(radius, angle);
    };

    DriveBase db;
    @Override
    protected void update(TeamCore core) {
        db = this.core.getComponentFromName("DriveBase", DriveBase.class);
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
