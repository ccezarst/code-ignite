package org.firstinspires.ftc.teamcode.TeamCore.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.DefaultComponents.ComponentType;
import EngineCore.TestingEnviromentCore;

public class DrivingManager extends CoreComponent {
    public DrivingManager(Boolean active, EngineCore core) {
        super("DrivingManager", active, core, ComponentType.DRIVING_MANAGER);
    }

    @Override
    protected void step(EngineCore core) {

    }

    public void moveRobotCentricPolarNoPathing(double radius, double angle){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveFieldCentricPolar(radius, angle);
            }
        }
    }; // move the robot in respect to it's current position radius cm in angle radians direction counteclockwise to the X axis
    public final void moveRobotCentricCartesianNoPathing(double x, double y){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveRobotCentricCartesian(x, y);
            }
        }
    }; // cm, cm

    public final void moveFieldCentricPointNoPathing(Point point){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveFieldCentricPoint(point);
            }
        }
    }
    public final void moveFieldCentricCartesianNoPathing(double x, double y){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveFieldCentricCartesian(x, y);
            }
        }
    };
    public final void moveFieldCentricPolarNoPathing(double  radius, double angle){
        if(this.db != null){
            synchronized (this.db) {
                this.db.moveRobotCentricPolar(radius, angle);
            }
        }
    };

    DriveBase db;
    @Override
    protected void update(EngineCore core) {
        db = this.core.getComponentFromName("DriveBase", DriveBase.class);
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
