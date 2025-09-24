package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

/**
 * BasePath provides a modular abstraction for any path segment the robot can follow.
 * Implementations return intermediate points for a given interpolation value t in [0,1].
 */

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.MotorConfiguration;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower.PathFollower;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameMap;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Robot;

import java.nio.file.Path;

import EngineCore.EngineCore;

public abstract class BasePath {
    protected final Point start;
    protected final Point end;
    protected final PathFollower pf;

    protected final EngineCore core;

    protected final Robot self;

    public BasePath(Point start, Point end, EngineCore core, PathFollower pf){
        this.start = start;
        this.end = end;
        this.core = core;
        this.pf = pf;
        this.self = this.core.getComponentFromName("GameMap", GameMap.class).getRobotSelf();
    }

    public boolean step(){
        if(!this.pf.reachedTarget()){
            Double[] motorPowers = this.pf.getMotorPowers(this.self.center.cartesianX, this.self.center.cartesianY, this.conf);
            int i = 0;
            for(DcMotor mot: this.motors){
                mot.setPower(motorPowers[i]);
                i++;
            }
        }else{
            return true;
        }
        return false;
    }
    protected MotorConfiguration conf;
    protected DcMotor[] motors;
    public void configurate(MotorConfiguration conf, DcMotor... motors){
        this.conf = conf;
        this.motors = motors;
    }

    /**
     * @param t interpolation parameter between 0 (start) and 1 (end)
     * @return point on path at position t
     */
    public abstract Point getPoint(double t);

    public Point getStart(){
        return start;
    }

    public Point getEnd(){
        return end;
    }
}
