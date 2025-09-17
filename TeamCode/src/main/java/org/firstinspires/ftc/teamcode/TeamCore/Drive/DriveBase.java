package org.firstinspires.ftc.teamcode.TeamCore.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameMap;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Robot;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.BasePath;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower.PathFollower;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower.PredictivePathFollower;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower.ReactivePathFollower;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.DefaultComponents.ComponentType;
import EngineCore.TestingEnviromentCore;

public abstract class DriveBase extends CoreComponent {

    protected PathFollower predictiveFollower;
    protected PathFollower reactiveFollower;
    protected double maxVelocity = 0; // cm/s
    protected double inertia = 0;     // arbitrary units

    public DriveBase(Boolean active, EngineCore core) {
        super("DriveBase", active, core, ComponentType.DRIVE_BASE);
        this.predictiveFollower = new PredictivePathFollower();
        this.reactiveFollower = new ReactivePathFollower();
    }

    public abstract void rotateRobotCentric(double angle);
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
    public final void rotateFieldCentric(double angle){
        if(angle > this.getSelf().center.orientation){
            this.rotateRobotCentric(angle-this.getSelf().center.orientation);
        }else{
            this.rotateRobotCentric(this.getSelf().center.orientation - angle);
        }
    }
    public final void moveFieldCentricPolar(double  radius, double angle){
        this.moveFieldCentricPoint(Point.fromPolar(radius, angle));
    };

    // --- Path following support ---
    public void setPathFollowers(PathFollower predictive, PathFollower reactive){
        this.predictiveFollower = predictive;
        this.reactiveFollower = reactive;
    }

    public double getMaxVelocity(){
        return maxVelocity;
    }

    public double getInertia(){
        return inertia;
    }

    /**
     * Follow a path using predictive and reactive followers.
     */
    public void followPath(BasePath path){
        if(path == null) return;
        if(predictiveFollower != null){
            predictiveFollower.follow(this, path);
        }
        if(reactiveFollower != null){
            reactiveFollower.follow(this, path);
        }
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        measureMaxVelocity();
        measureInertia();
        return 0;
    }

    /**
     * Measure maximum velocity using encoders.
     */
    protected abstract void measureMaxVelocity();
    /**
     * Measure inertia characteristic for predictive follower.
     */
    protected abstract void measureInertia();
}
