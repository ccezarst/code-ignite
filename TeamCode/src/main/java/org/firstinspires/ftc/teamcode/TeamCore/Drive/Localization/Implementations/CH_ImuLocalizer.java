package org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.Implementations;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;


import org.firstinspires.ftc.teamcode.TeamCore.Drive.Localization.Localizer;

import EngineCore.DefaultComponents.CoreComponentSettings;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;
import java.util.ArrayList;

public class CH_ImuLocalizer extends Localizer {

    public static RevHubOrientationOnRobot.LogoFacingDirection logoDir;
    public static RevHubOrientationOnRobot.UsbFacingDirection usbDir;

    public CH_ImuLocalizer(Boolean active, EngineCore core) {
        super("ControlHubIMULocalizer", active, core);
        this.settings.add(new CoreComponentSettings() {
            @Override
            public ArrayList<String> getSettings() {
                ArrayList<String> opts = new ArrayList<>();
                opts.add("Logo direction");
                opts.add("Usb direction");
                return opts;
            }

            @Override
            public ArrayList<String> getSettingOptions(String settingName) {
                ArrayList<String> toReturn = new ArrayList<>();
                toReturn.add("Up");
                toReturn.add("Down");
                toReturn.add("Left");
                toReturn.add("Right");
                toReturn.add("Forward");
                toReturn.add("Backward");
                return toReturn;
            }

            @Override
            public void changeSetting(String settingName, String option) {
                if(settingName == "Logo direction"){
                    if(option == "Up"){
                        logoDir = RevHubOrientationOnRobot.LogoFacingDirection.UP;
                    }
                    if(option == "Down"){
                        logoDir = RevHubOrientationOnRobot.LogoFacingDirection.DOWN;
                    }
                    if(option == "Left"){
                        logoDir = RevHubOrientationOnRobot.LogoFacingDirection.LEFT;
                    }
                    if(option == "Right"){
                        logoDir = RevHubOrientationOnRobot.LogoFacingDirection.RIGHT;
                    }
                    if(option == "Forward"){
                        logoDir = RevHubOrientationOnRobot.LogoFacingDirection.FORWARD;
                    }
                    if(option == "Backward"){
                        logoDir = RevHubOrientationOnRobot.LogoFacingDirection.BACKWARD;
                    }
                }else if(settingName == "Usb direction"){
                    if(option == "Up"){
                        usbDir = RevHubOrientationOnRobot.UsbFacingDirection.UP;
                    }
                    if(option == "Down"){
                        usbDir = RevHubOrientationOnRobot.UsbFacingDirection.DOWN;
                    }
                    if(option == "Left"){
                        usbDir = RevHubOrientationOnRobot.UsbFacingDirection.LEFT;
                    }
                    if(option == "Right"){
                        usbDir = RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;
                    }
                    if(option == "Forward"){
                        usbDir = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
                    }
                    if(option == "Backward"){
                        usbDir = RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD;
                    }
                }
            }
        });
    }

    @Override
    public void customUpdate(EngineCore core) {

    }

    @Override
    public void customStep(EngineCore core) {

    }

    @Override
    protected int test(TestingEnviromentCore core) {
        return 0;
    }
}
