package org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization;

import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameMap;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameObjects.Robot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;
import EngineCore.DefaultComponents.ComponentType;
public class LocalizationManager extends CoreComponent implements Runnable {

    public ArrayList<LocalizationPacket> packets = new ArrayList<>();

    public static class AveregeSensorFusion implements LocalizationManagerSensorFusion {

        @Override
        public double fuseValues(ArrayList<Double> values) {
            double total = 0;
            for(Double val: values){
                total += val;
            }
            return total / values.size();
        }
    }
    public LocalizationManagerSensorFusion fuser = new AveregeSensorFusion();
    public GameMap map;
    public LocalizationManager(Boolean active, EngineCore core, LocalizationManagerSensorFusion fuser) {
        super("Localization Manager", active, core, ComponentType.LOCALIZATION_MANAGER);
        synchronized (this.fuser){
            this.fuser = fuser;
        }
    }
    public Map<LocalizationPacket.Features, Double> FeatureValues = new HashMap<>();
    @Override
    public void run() {
        Robot rob = this.map.getRobotSelf();
        while(true){
            synchronized (rob){
                for(LocalizationPacket.Features feat: LocalizationPacket.Features.values()){
                    ArrayList<Double> values = new ArrayList<>();
                    for(LocalizationPacket pac: this.packets){
                        if(pac.activeFeatures.contains(feat)){
                            values.add(pac.getFeatureValue(feat));
                        }
                        double val = this.fuser.fuseValues(values);
                        FeatureValues.put(feat, val);
                        this.core.setGlobalVariable(feat.name(), val);
                    }
                }
                rob.center.orientation = this.FeatureValues.get(LocalizationPacket.Features.FIELD_ORIENTATION);
                rob.center.cartesianX = this.FeatureValues.get(LocalizationPacket.Features.X);
                rob.center.cartesianY = this.FeatureValues.get(LocalizationPacket.Features.Y);
                rob.speed = this.FeatureValues.get(LocalizationPacket.Features.SPEED);

            }
        }
    }

    @Override
    protected void step(EngineCore core) {

    }


    @Override
    protected void update(EngineCore core) {
        this.map = this.core.getComponentFromName("GameMap", GameMap.class);
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
