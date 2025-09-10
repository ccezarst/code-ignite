package org.firstinspires.ftc.teamcode.TeamCore;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.DrivingManager;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.LocalizationManager;
import org.firstinspires.ftc.teamcode.TeamCore.Extra.CoreComponentTester;
import org.firstinspires.ftc.teamcode.TeamCore.Extra.CoreOptionsMenu;
import org.firstinspires.ftc.teamcode.TeamCore.GameMap.GameMap;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.SW_Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.Interface;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.Drive.Implementation.LocalPathFetcher;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import EngineCore.DefaultComponents.CoreComponent;
import EngineCore.EngineCore;
import EngineCore.DefaultComponents.ComponentType;
public class RobotTCore extends EngineCore {
    public RobotTCore(Telemetry telem, HardwareMap hwMap) {
        this.addComponent(new UI_Manager(true, this));
        this.addComponent(new SW_Telemetry(true, this));
        this.addComponent(new GameMap(true, this));
        LocalPathFetcher localFetcher = new LocalPathFetcher(true, this);
        this.addComponent(localFetcher);
        this.addComponent(new DrivingManager(true, this, localFetcher));
        this.addComponent(new LocalizationManager(true, this, new LocalizationManager.AveregeSensorFusion()));
        this.addComponent(new CoreOptionsMenu(true, this));
        this.addComponent(new CoreComponentTester(true, this));
        // shared executor for background tasks like path following
        ExecutorService exec = Executors.newSingleThreadExecutor();
        this.setGlobalVariable("PathExecutor", exec);
        if(telem != null){
            //this.addComponent(new SW_Telemetry(true, this, telem));
            this.setGlobalVariable("Telemetry", telem);
        }
        if(hwMap != null){
            //this.addComponent(new HW_HwMap(true, this, hwMap));
            this.setGlobalVariable("HardwareMap", hwMap);
        }
    }

    public HardwareMap getHwMap(){
        return this.getGlobalVariable("HardwareMap", HardwareMap.class);
    }

    public final ArrayList<Interface> getInterfacesOfType(InterfaceType type){
        ArrayList<CoreComponent> interfs = this.getComponentsOfType(ComponentType.INTERFACE);
        ArrayList<Interface> toReturn = new ArrayList<>();
        for(int i = 0; i < interfs.size(); i++){
            if(((Interface)(interfs.get(i))).interfaceType == type){
                toReturn.add((Interface) interfs.get(i));
            }
        }
        return toReturn;
    }

    public final <T> ArrayList<T> getInterfacesOfType(InterfaceType type, Class<? extends T> caster){
        ArrayList<CoreComponent> interfs = this.getComponentsOfType(ComponentType.INTERFACE);
        ArrayList<T> toReturn = new ArrayList<>();
        for(int i = 0; i < interfs.size(); i++){
            if(((Interface)(interfs.get(i))).interfaceType == type){
                toReturn.add(caster.cast(interfs.get(i)));
            }
        }
        return toReturn;
    }
}
