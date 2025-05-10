package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Drive.Localization;

import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.HardwareInterface;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.InterfaceType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template.UserInterfaceMethodDefinitions;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;

public abstract class Localizer  extends HardwareInterface implements Runnable{
    public LocalizationPacket data = new LocalizationPacket();

    public Localizer(String cName, Boolean active, TeamCore core, ComponentType... types) {
        super(cName, active, core, InterfaceType.LOCALIZER, types);
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
