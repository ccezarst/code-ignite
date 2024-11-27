package org.firstinspires.ftc.teamcode.Core;

public class HardwareInterface extends CoreComponent{
    public final String name;
    public final InterfaceType type;
    public HardwareInterface(String cName, InterfaceType cType){
        if(cName!= null && !cName.isEmpty() && cType != null){
            this.name = cName;
            this.type = cType;
        }else{
            if(cType != null){
                throw new IllegalArgumentException("Invalid argumentspassed to a hardware interface constructor ( name: " + cName + " ; type: " + cType.name());
            }else{
                throw new IllegalArgumentException("Invalid argumentspassed to a hardware interface constructor ( name: " + cName + " ; type: null");
            }
        }
    }
}
