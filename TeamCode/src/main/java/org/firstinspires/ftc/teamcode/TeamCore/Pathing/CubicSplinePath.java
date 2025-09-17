package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

/**
 * Cubic spline path defined by polynomial coefficients between two points.
 */
public class CubicSplinePath extends BasePath {

    private double a, b, c, d; // Coefficients of cubic polynomial

    public CubicSplinePath(Point p0, Point p1, double a, double b, double c, double d) {
        super(p0, p1);
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Evaluate path at interpolation parameter t (0-1).
     */
    @Override
    public Point getPoint(double t) {
        double x = start.cartesianX + (end.cartesianX - start.cartesianX) * t;
        double dx = x - start.cartesianX;
        double y = a + b * dx + c * dx * dx + d * dx * dx * dx;
        return new Point(x, y);
    }

    public String getEquation() {
        return "S(x) = " + a + " + " + b + "(x - " + start.cartesianX + ") + "
                + c + "(x - " + start.cartesianX + ")^2 + "
                + d + "(x - " + start.cartesianX + ")^3";
    }
}
