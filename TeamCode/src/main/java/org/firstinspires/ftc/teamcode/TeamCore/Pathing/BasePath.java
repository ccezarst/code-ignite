package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

/**
 * BasePath provides a modular abstraction for any path segment the robot can follow.
 * Implementations return intermediate points for a given interpolation value t in [0,1].
 */
public abstract class BasePath {
    protected final Point start;
    protected final Point end;

    public BasePath(Point start, Point end){
        this.start = start;
        this.end = end;
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
