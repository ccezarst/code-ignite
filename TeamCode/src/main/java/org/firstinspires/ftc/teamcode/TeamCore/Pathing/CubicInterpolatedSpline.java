package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

public class CubicInterpolatedSpline {

    private Point p0; // start point
    private Point p1; // end point

    private double a, b, c, d; // Coefficients of the cubic polynomial

    public CubicInterpolatedSpline(Point p0, Point p1, double a, double b, double c, double d) {
        this.p0 = p0;
        this.p1 = p1;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    // Evaluate spline at given x (returns y)
    public double evaluate(double x) {
        double dx = x - p0.cartesianX;
        return a + b * dx + c * dx * dx + d * dx * dx * dx;
    }

    public Point getStartPoint() {
        return p0;
    }

    public Point getEndPoint() {
        return p1;
    }

    public String getEquation() {
        return "S(x) = " + a + " + " + b + "(x - " + p0.cartesianX + ") + "
                + c + "(x - " + p0.cartesianX + ")^2 + "
                + d + "(x - " + p0.cartesianX + ")^3";
    }
}
