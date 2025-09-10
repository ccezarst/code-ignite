package org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization;

import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.InterfaceType;

import EngineCore.DefaultComponents.ComponentType;
import EngineCore.EngineCore;

public abstract class Localizer  extends HardwareInterface implements Runnable{
    public LocalizationPacket data = new LocalizationPacket();
    private static ComponentType[] addX(ComponentType arr[], ComponentType x) {

        ComponentType newarr[] = new ComponentType[arr.length + 1];

        // insert the elements from
        // the old array into the new array
        // insert all elements till n
        // then insert x at n+1
        for (int i = 0; i < arr.length; i++)
            newarr[i] = arr[i];

        newarr[newarr.length - 1] = x;

        return newarr;
    }
    public Localizer(String cName, Boolean active, EngineCore core, ComponentType... types) {
        super(cName, active, core, InterfaceType.LOCALIZER, addX(types, ComponentType.LOCALIZER));
    }

    @Override
    public final void run(){
        while(true){
            if(awaitUpdate){
                this.customUpdate(this.core);
                this.awaitUpdate = true;
            }else{
                this.customStep(this.core);
            }
        }
    }

    public abstract void customUpdate(EngineCore core);
    public abstract void customStep(EngineCore core);
    boolean awaitUpdate = true;
    @Override
    public final void step(EngineCore core){this.customStep(core);}
    @Override
    public final void update(EngineCore core){
        this.awaitUpdate = true;this.customUpdate(core);
    }
}
