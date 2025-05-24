package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.Localization;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.UserInterfaceMethodDefinitions;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

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
    public Localizer(String cName, Boolean active, TeamCore core, ComponentType... types) {
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

    public abstract void customUpdate(TeamCore core);
    public abstract void customStep(TeamCore core);
    boolean awaitUpdate = true;
    @Override
    public final void step(TeamCore core){}
    @Override
    public final void update(TeamCore core){
        this.awaitUpdate = true;
    }
}
