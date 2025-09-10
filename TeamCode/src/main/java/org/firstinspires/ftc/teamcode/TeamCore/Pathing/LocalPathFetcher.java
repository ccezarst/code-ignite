package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

import java.util.HashMap;
import java.util.Map;

import EngineCore.EngineCore;

/**
 * Default PathFetcher implementation that returns locally stored paths
 * mapped by their start and end coordinates.
 */
public class LocalPathFetcher extends PathFetcher {

    private final Map<String, BasePath> paths = new HashMap<>();

    public LocalPathFetcher(boolean active, EngineCore core){
        super("LocalPathFetcher", active, core);
        // Example predefined path from (0,0) to (10,0)
        Point start = new Point(0,0);
        Point end = new Point(10,0);
        paths.put(key(start,end), new LinearPath(start,end));
    }

    public LocalPathFetcher(EngineCore core){
        this(true, core);
    }

    @Override
    public BasePath fetch(Point start, Point end) {
        return paths.get(key(start, end));
    }

    private String key(Point a, Point b){
        return a.cartesianX + "," + a.cartesianY + "_" + b.cartesianX + "," + b.cartesianY;
    }
}
