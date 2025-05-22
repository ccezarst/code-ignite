package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Extra;

import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.TeamCore.Actions.ActionDataContainer;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.ComponentType;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Managers.UI_Manager;
import org.firstinspires.ftc.teamcode.TeamCore.TeamCore;
import org.firstinspires.ftc.teamcode.TeamCore.TestingEnviromentCore;

import java.util.ArrayList;

public class CoreOptionsMenu extends CoreComponent {
    public CoreOptionsMenu(String name, Boolean active, TeamCore core, ComponentType... type) {
        super(name, active, core, type);
    }

    public void showMenu(){
        ArrayList<String> opts = new ArrayList<>();
        opts.add("Re-init");
        opts.add("Run tests");
        opts.add("Components list");
        opts.add("Hardware list");
        opts.add("Settings");
        this.core.getComponentFromName("UI_Manager", UI_Manager.class).showMenu("Pick an option", opts, (Integer o)->{this.menuCallback(o);});
    }

    public void menuCallback(int opts){
        if(opts == 0){
            this.core.update();
        }else if(opts == 1){
            this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(this.core.testComponents().toString());
        }else if(opts == 2){
            this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(this.core.getStatus());
        }else if(opts == 3){
            this.getHwMap().forEach(a -> {
                if (a instanceof DcMotor) {
                    this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(((DcMotor) a).getPortNumber() + " " +this.getHwMap().getNamesOf(a) + "-" + ((DcMotor) a).getMode() + "-" + ((DcMotor) a).getZeroPowerBehavior() + "-" + ((DcMotor) a).getDirection() + ": " + ((DcMotor) a).getCurrentPosition() + " . " + ((DcMotor)a).getTargetPosition());
                }
                if (a instanceof Servo) {
                    this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(((Servo) a).getPortNumber() + " " + this.getHwMap().getNamesOf(a) + "-" + ((Servo) a).getDirection() + ": " + ((Servo) a).getPosition());
                }
                if (a instanceof AccelerationSensor) {
                    this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(this.getHwMap().getNamesOf(a) + ": " + ((AccelerationSensor) a).getAcceleration());
                }
                if (a instanceof AnalogInput) {
                    this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(this.getHwMap().getNamesOf(a) + ": " + ((AnalogInput) a).getVoltage() + " . " + ((AnalogInput) a).getMaxVoltage());
                }
                if (a instanceof ColorSensor) {
                    this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(this.getHwMap().getNamesOf(a) + "-" + ((ColorSensor) a).getI2cAddress() + ": " + ((ColorSensor) a).red() + " . " + ((ColorSensor) a).green() + " . " + ((ColorSensor) a).blue() + " . " + ((ColorSensor) a).alpha());
                }
                if (a instanceof CompassSensor) {
                    this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(this.getHwMap().getNamesOf(a) + "-" + ((CompassSensor) a).calibrationFailed() +": " + ((CompassSensor) a).getDirection());
                }
                if (a instanceof CRServo) {
                    this.core.getComponentFromName("UI_Manager", UI_Manager.class).print(((CRServo) a).getPortNumber() + " " +this.getHwMap().getNamesOf(a) + "-" + ((CRServo) a).getDirection() +": " + ((CRServo) a).getPower());
                }
            });
        }else if(opts == 4){
            ArrayList<String> comps = new ArrayList<>();
            for(CoreComponent comp : this.core.getAllComponents()){
                comps.add(comp.name);
            }
            this.core.getComponentFromName("UI_Manager", UI_Manager.class).showMenu("Pick a component",comps , (Integer o)->{this.settingsMenuCallbackA(o);});
        }
    }
    String selectedComponent = "";
    public void settingsMenuCallbackA(int opts){
        if(opts != -1){
            int i = 0;
            for(CoreComponent comp : this.core.getAllComponents()){
                if(i == opts){
                    selectedComponent = comp.name;
                    break;
                }
                i += 1;
            }
        }
        this.core.getComponentFromName("UI_Manager", UI_Manager.class).showMenu("Pick a setting",this.core.getComponentFromName(selectedComponent).getAllSettings() , (Integer o)->{this.settingsMenuCallbackB(o);});
    }
    String selectedSetting = "";
    public void settingsMenuCallbackB(int opts){
        if(opts != -1){
            int i = 0;
            for(String set : this.core.getComponentFromName(selectedComponent).getAllSettings()){
                if(i == opts){
                    selectedSetting = set;
                    break;
                }
                i += 1;
            }
        }
        this.core.getComponentFromName("UI_Manager", UI_Manager.class).showMenu(selectedSetting,this.core.getComponentFromName(selectedComponent).getSettingOptions(selectedSetting) , (Integer o)->{this.settingsMenuCallbackC(o);});
    }
    public void settingsMenuCallbackC(int opts){
        if(opts != -1){
            int i = 0;
            for(String opt : this.core.getComponentFromName(selectedComponent).getSettingOptions(selectedSetting)){
                if(i == opts){
                    this.core.getComponentFromName(selectedComponent).changeSetting(selectedSetting, opt);
                    break;
                }
                i += 1;
            }
        }
    }

    @Override
    protected void step(TeamCore core) {

    }

    @Override
    protected void update(TeamCore core) {
        this.core.subscribeToAction("1START_PRESSED", (ActionDataContainer caca) -> {this.showMenu();});
    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
