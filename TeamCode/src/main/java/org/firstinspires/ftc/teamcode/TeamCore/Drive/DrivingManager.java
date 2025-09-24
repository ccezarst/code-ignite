package org.firstinspires.ftc.teamcode.TeamCore.Drive;

import org.firstinspires.ftc.teamcode.TeamCore.Drive.PathFollower.PathFollower;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.Point;
import org.firstinspires.ftc.teamcode.TeamCore.Pathing.BasePath;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.DefaultComponents.ComponentType;
import EngineCore.TestingEnviromentCore;

public class DrivingManager extends CoreComponent {
    private final PathFetcher preferredFetcher;
    private final List<PathFetcher> fetchers = new ArrayList<>();
    private ExecutorService pathExecutor;


    public DrivingManager(Boolean active, EngineCore core, PathFetcher preferredFetcher) {
        super("DrivingManager", active, core, ComponentType.DRIVING_MANAGER);
        this.preferredFetcher = preferredFetcher;
    }

    @Override
    protected void step(EngineCore core) {

    }

    public void moveRobotCentricPolarNoPathing(double radius, double angle){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveRobotCentricPolar(radius, angle);
            }
        }
    }; // move the robot in respect to it's current position radius cm in angle radians direction counteclockwise to the X axis
    public final void moveRobotCentricCartesianNoPathing(double x, double y){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveRobotCentricCartesian(x, y);
            }
        }
    }; // cm, cm

    public final void moveFieldCentricPointNoPathing(Point point){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveFieldCentricPoint(point);
            }
        }
    }
    public final void moveFieldCentricCartesianNoPathing(double x, double y){
        if(this.db!=null) {
            synchronized (this.db) {
                this.db.moveFieldCentricCartesian(x, y);
            }
        }
    };
    public final void moveFieldCentricPolarNoPathing(double  radius, double angle){
        if(this.db != null){
            synchronized (this.db) {
                this.db.moveRobotCentricPolar(radius, angle);
            }
        }
    };

    // --- Path based movement ---

    public void followPath(BasePath path, PathFollower pf){
        if(this.db!=null && path!=null && pathExecutor!=null){
            pathExecutor.submit(() -> {
                synchronized (this.db){
                    path.pf = pf;
                    this.db.followPath(path);
                }
            });
        }
    }

    public void followPath(Point start, Point end, PathFollower pf){
        if(fetchers.isEmpty()) return;

        ExecutorService exec = Executors.newFixedThreadPool(fetchers.size());
        List<Callable<BasePath>> calls = new ArrayList<>();
        for(PathFetcher fetcher : fetchers){
            calls.add(() -> fetcher.fetch(start, end));
        }

        try {
            List<Future<BasePath>> futures = exec.invokeAll(calls);
            int preferredIndex = fetchers.indexOf(preferredFetcher);
            if(preferredIndex < 0) preferredIndex = 0;
            BasePath path = futures.get(preferredIndex).get();
            if(path == null){
                for(int i = 0; i < futures.size(); i++){
                    if(i == preferredIndex) continue;
                    path = futures.get(i).get();
                    if(path != null) break;
                }
            }
            followPath(path, pf);
        } catch (InterruptedException | ExecutionException e) {
            // ignore and do not follow any path
        } finally {
            exec.shutdown();
        }
    }

    DriveBase db;
    @Override
    protected void update(EngineCore core) {
        db = this.core.getComponentFromName("DriveBase", DriveBase.class);
        pathExecutor = core.getGlobalVariable("PathExecutor", ExecutorService.class);

        // refresh fetcher list from available components
        fetchers.clear();
        ArrayList<CoreComponent> comps = core.getComponentsOfType(ComponentType.OTHER);
        for(CoreComponent comp : comps){
            if(comp instanceof PathFetcher){
                fetchers.add((PathFetcher) comp);
            }
        }

        // ensure preferred fetcher is first in order
        if(preferredFetcher != null){
            fetchers.remove(preferredFetcher);
            fetchers.add(0, preferredFetcher);
        }
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
