package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Pathing;

public class Point{

    public final double pi = 3.14159;

    public double cartesianX = 0; // in cm
    public double cartesianY = 0;
    public Point(double carthesianX, double carthesianY){
        this.cartesianX = carthesianX;
        this.cartesianY = carthesianY;


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
