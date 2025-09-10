package org.firstinspires.ftc.teamcode.TeamCore.Pathing;

import EngineCore.DefaultComponents.ComponentType;
import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;

/**
 * Component capable of providing a path between two points.
 */
public abstract class PathFetcher extends CoreComponent {

    public PathFetcher(String name, boolean active, EngineCore core) {
        super(name, active, core, ComponentType.OTHER);
    }

    /**
     * Attempt to fetch a path between the provided points.
     *
     * @return a path or {@code null} if none is available
     */
    public abstract BasePath fetch(Point start, Point end);

    @Override
    protected void step(EngineCore core) {
        // no periodic action required
    }

    @Override
    protected void update(EngineCore core) {
        // nothing to update
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
