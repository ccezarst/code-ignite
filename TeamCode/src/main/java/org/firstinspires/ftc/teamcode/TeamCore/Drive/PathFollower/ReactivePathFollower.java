package org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.DriveBase;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.LocalizationManager;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.LocalizationPacket;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.BasePath;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;

/**
 * Reactive path follower that applies corrections based on current robot position.
 */
public class ReactivePathFollower implements PathFollower {

    private final double tolerance = 1.0; // cm
    private final double step = 0.1;

    @Override
    public void follow(DriveBase drive, BasePath path) {
        LocalizationManager loc = drive.core.getComponentFromName("Localization Manager", LocalizationManager.class);
        for(double t = 0.0; t <= 1.0 + 1e-6; t += step){
            Point expected = path.getPoint(Math.min(t,1.0));
            double currentX = loc.FeatureValues.get(LocalizationPacket.Features.X);
            double currentY = loc.FeatureValues.get(LocalizationPacket.Features.Y);
            double errorX = expected.cartesianX - currentX;
            double errorY = expected.cartesianY - currentY;
            if(Math.hypot(errorX, errorY) > tolerance){
                drive.moveFieldCentricCartesian(currentX + errorX,
                        currentY + errorY);
            }
            try{ Thread.sleep(10); }catch(InterruptedException e){ Thread.currentThread().interrupt(); break; }
        }
    }
}
