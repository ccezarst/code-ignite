package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameMap;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.GameMap.GameObjects.Robot;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing.Point;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

public abstract class DriveBase extends CoreComponent {
    public DriveBase(Boolean active, TeamCore core) {
        super("DriveBase", active, core, ComponentType.DRIVE_BASE);
    }

    public abstract void moveRobotCentricPolar(double radius, double angle); // move the robot in respect to it's current position radius cm in angle radians direction counteclockwise to the X axis
    public final void moveRobotCentricCartesian(double x, double y){
        this.moveFieldCentricPoint(Point.removeRefrenceFromPoint(x, y, this.getSelf().center.cartesianX, this.getSelf().center.cartesianY));
    }; // cm, cm

    public final void moveFieldCentricPoint(Point point){
        this.moveFieldCentricCartesian(point.cartesianX, point.cartesianY);
    };

    public final Robot getSelf(){
        return this.core.getComponentFromName("GameMap", GameMap.class).getRobotSelf();
    }
    public final void moveFieldCentricCartesian(double x, double y){
        Point withRef = Point.getPointWithReference(x, y, this.getSelf().center.cartesianX, this.getSelf().center.cartesianY);
        double radius = withRef.getPolarRadius();
        double angleToXs = withRef.getPolarAngle();
        double angleBetweenAxis = this.getSelf().center.orientation - Point.pi/2;
        double angle = 0;
        if(angleToXs > angleBetweenAxis){
            angle = angleBetweenAxis - angleToXs;
        }else{
            angle = angleToXs - angleBetweenAxis;
        }
        this.moveRobotCentricPolar(radius, angle);
    };
    public final void moveFieldCentricPolar(double  radius, double angle){
        this.moveFieldCentricPoint(Point.fromPolar(radius, angle));
    };

    // add pathing commands
}
