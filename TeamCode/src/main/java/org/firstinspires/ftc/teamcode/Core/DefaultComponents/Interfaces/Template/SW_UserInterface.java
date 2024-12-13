package org.firstinspires.ftc.teamcode.Core.DefaultComponents.Interfaces.Template;

import org.firstinspires.ftc.teamcode.Core.DefaultComponents.CoreComponent;
import org.firstinspires.ftc.teamcode.Core.DefaultCore;

import java.util.ArrayList;

// class to handle user-interactions
public abstract class SW_UserInterface extends SoftwareInterface {
    public SW_UserInterface(String cName, Boolean active, DefaultCore core) {
        super(cName, active, core, InterfaceType.USER_INTERFACE);
    }
    // show a menu of options and return selection(0 -> cancel)
    public abstract int showMenu(ArrayList<String> options);

    // print something, does not have newline at the end
    public abstract void print(String toPrint);

    // print something, adds newline at the end
    public abstract void printLine(String toPrint);

    // updates the ui with the new changes
    public abstract void updatePrint();

    // function that returns whether the interface can output new text
    public abstract boolean isBusy();

}
