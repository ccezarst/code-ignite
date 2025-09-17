package org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.DriveBase;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.LocalizationManager;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.LocalizationPacket;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.BasePath;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;

/**
 * Predictively follows a path while respecting the drive base measured
 * inertia and max velocity to prevent slipping.
 */
public class PredictivePathFollower implements PathFollower {

    private final double step = 0.1; // interpolation step

    @Override
    public void follow(DriveBase drive, BasePath path) {
        LocalizationManager loc = drive.core.getComponentFromName("Localization Manager", LocalizationManager.class);
        Point previous = new Point(
                loc.FeatureValues.get(LocalizationPacket.Features.X),
                loc.FeatureValues.get(LocalizationPacket.Features.Y));
        for(double t = step; t <= 1.0 + 1e-6; t += step){
            Point target = path.getPoint(Math.min(t,1.0));
            double distance = Math.hypot(target.cartesianX - previous.cartesianX,
                    target.cartesianY - previous.cartesianY);
            double maxStep = drive.getMaxVelocity() * step;
            if(distance > maxStep){
                double scale = maxStep / distance;
                double newX = previous.cartesianX + (target.cartesianX - previous.cartesianX) * scale;
                double newY = previous.cartesianY + (target.cartesianY - previous.cartesianY) * scale;
                target = new Point(newX, newY);
            }
            drive.moveFieldCentricPoint(target);
            previous = new Point(
                    loc.FeatureValues.get(LocalizationPacket.Features.X),
                    loc.FeatureValues.get(LocalizationPacket.Features.Y));
            try{ Thread.sleep(10); }catch(InterruptedException e){ Thread.currentThread().interrupt(); break; }
        }
    }
}
