package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

/**
 * Simple straight line path between two points.
 */
public class LinearPath extends BasePath {

    public LinearPath(Point start, Point end){
        super(start, end);
    }

    @Override
    public Point getPoint(double t){
        double x = start.cartesianX + (end.cartesianX - start.cartesianX) * t;
        double y = start.cartesianY + (end.cartesianY - start.cartesianY) * t;
        return new Point(x, y);
    }
}
