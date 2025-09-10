package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

public class Point{

    public static final double pi = 3.14159;

    public double cartesianX = 0; // in cm
    public double cartesianY = 0;
    public Point(double carthesianX, double carthesianY){
        this.cartesianX = carthesianX;
        this.cartesianY = carthesianY;
    }

    public final static Point fromPolar(double radius, double angle){
        Point res = new Point(0, 0);
        res.setCoordinatesFromPolar(radius, angle);
        return res;
    }
    public final static Point getPointWithReference(double x, double y, double rX, double rY){
        // basically shift the point by it's refrence position( so the refrence position becomes 0,0)
        double newX = rX - x;
        double newY = rY - y;
        Point npoint = new Point(newX, newY);
        return npoint;
    }

    public final static Point removeRefrenceFromPoint(double x, double y, double rX, double rY){
        return new Point(rX + x, rY + y);
    }
    public void setCoordinatesFromPolar(double radius, double angle){
        this.cartesianY = Math.sin(angle) * radius;
        this.cartesianX = Math.cos(angle) * radius;
    }

    public double getPolarRadius(){ // radians
        return Math.sqrt(this.cartesianX * this.cartesianX + this.cartesianY * this.cartesianY);
    }

    public double getPolarAngle(){ // radians
        if(this.cartesianX > 0 && this.cartesianY > 0){
            return Math.atan(this.cartesianY / this.cartesianX);
        }else if(this.cartesianX < 0 && this.cartesianY > 0){
            return Math.atan(this.cartesianY / -this.cartesianX) + pi/2;
        }else if(this.cartesianX < 0 && this.cartesianY < 0){
            return Math.atan(-this.cartesianY / -this.cartesianX) + pi;
        }else if(this.cartesianX > 0 && this.cartesianY < 0){
            return Math.atan(-this.cartesianY / this.cartesianX) + 3*pi/2;
        }
        return 0;
    }
}
