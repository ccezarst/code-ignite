package org.firstinspires.ftc.teamcode.Core;

public class SoftwareInterface extends CoreComponent{
    public final String name;
    public final InterfaceType type;
    public SoftwareInterface(String cName, InterfaceType cType){
        if(cName!= null && !cName.isEmpty() && cType != null){
            this.name = cName;
            this.type = cType;
        }else{
            if(cType != null){
                throw new IllegalArgumentException("Invalid arguments passed to a software interface constructor ( name: " + cName + " ; type: " + cType.name());
            }else{
                throw new IllegalArgumentException("Invalid arguments passed to a software interface constructor ( name: " + cName + " ; type: null");
            }
        }
    }
}
