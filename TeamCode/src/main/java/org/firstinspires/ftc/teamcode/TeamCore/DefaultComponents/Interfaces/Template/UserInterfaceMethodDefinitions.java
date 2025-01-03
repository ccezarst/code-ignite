package org.firstinspires.ftc.teamcode.TeamCore.DefaultComponents.Interfaces.Template;

import java.util.ArrayList;
import java.util.function.Consumer;

public interface UserInterfaceMethodDefinitions {
    public abstract void showMenu(String title, ArrayList<String> options, Consumer<Integer> callback);

    // print something, does not have newline at the end
    public abstract void print(String print, boolean speak);

    // print something, adds newline at the end
    public abstract void printLine(String toPrint, boolean speak);

    // updates the ui with the new changes
    public abstract void updatePrint();

    // function that returns whether the interface can output new text
    public abstract boolean isBusy();
}
