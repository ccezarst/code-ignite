package org.firstinspires.ftc.teamcode.TeamCore.Interfaces;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.TeamCore.Input.Template.ButtonTypes;
import org.firstinspires.ftc.teamcode.TeamCore.Interfaces.Template.SW_UserInterface;

import java.util.ArrayList;
import java.util.function.Consumer;

import EngineCore.Actions.ActionDataContainer;
import EngineCore.EngineCore;
import EngineCore.TestingEnviromentCore;

public class SW_Telemetry extends SW_UserInterface {
    public Telemetry telemetry;
    private boolean busy = false;

    // --- NEW: windowed menu state
    private static final int WINDOW_SIZE = 4;
    private int viewTop = 0;

    public SW_Telemetry(Boolean active, EngineCore TeamCore) {
        super("SW_Telemetry", active, TeamCore);
    }

    @Override
    public void print(String toPrint, boolean pl) {
        if (this.telemetry != null && !this.busy) { this.telemetry.addLine(toPrint); }
    }

    @Override
    public void printLine(String toPrint, boolean pl) {
        if (this.telemetry != null && !this.busy) { this.telemetry.addLine(toPrint + "\n"); }
    }

    private String menuTitle = "";
    private ArrayList<String> menuOptions = new ArrayList<>();
    private Consumer<Integer> menuCallback;
    private int selection = 0;

    @Override
    public void showMenu(String title, ArrayList<String> options, Consumer<Integer> callback) {
        if (!this.busy) {
            synchronized (this) {
                if (this.telemetry != null) {
                    this.menuTitle = title;
                    this.menuOptions = options != null ? options : new ArrayList<>();
                    this.menuCallback = callback;
                    this.busy = true;
                    this.selection = 0;
                    this.viewTop = 0; // NEW: reset window top
                }
            }
        }
    }

    @Override
    public void updatePrint() {
        if (this.telemetry != null) {
            synchronized (this.telemetry) { this.telemetry.update(); }
        }
    }

    @Override
    public boolean isBusy() { return this.busy; }

    @Override
    public void step(EngineCore core) {
        if (this.busy && this.telemetry != null) {
            synchronized (this.telemetry) {
                this.telemetry.addLine("Browse menu(GP1) – DPAD UP/DOWN, confirm: A, cancel: B");
                this.telemetry.addLine(menuTitle);

                // --- NEW: compute window bounds
                int total = menuOptions.size();
                int end = Math.min(viewTop + WINDOW_SIZE, total);

                // show arrow if there are items above
                if (viewTop > 0) {
                    this.telemetry.addLine("↑ more...");
                }

                for (int i = viewTop; i < end; i++) {
                    String option = menuOptions.get(i);
                    if (i == selection) {
                        this.telemetry.addLine("> " + option + "  <");
                    } else {
                        this.telemetry.addLine("  " + option);
                    }
                }

                // show arrow if there are items below
                if (end < total) {
                    this.telemetry.addLine("↓ more...");
                }
            }
        }
    }

    @Override
    protected void update(EngineCore core) {
        this.telemetry = this.core.getGlobalVariable("Telemetry", Telemetry.class);
        this.core.subscribeToAction("1" + ButtonTypes.DPAD_DOWN.name() + "_PRESSED", (ActionDataContainer data) -> { this.dpadDown_pressed(); });
        this.core.subscribeToAction("1" + ButtonTypes.DPAD_UP.name() + "_PRESSED", (ActionDataContainer data) -> { this.dpadUp_pressed(); });
        this.core.subscribeToAction("1" + ButtonTypes.A.name() + "_PRESSED", (ActionDataContainer data) -> { this.a_pressed(); });
        this.core.subscribeToAction("1" + ButtonTypes.B.name() + "_PRESSED", (ActionDataContainer data) -> { this.b_pressed(); });
        System.out.println(this.name + "-> Subscribed to input actions");
    }

    public void dpadDown_pressed() {
        if (!busy) return;
        int lastIndex = this.menuOptions.size() - 1;
        if (this.selection < lastIndex) {
            this.selection += 1;

            // --- NEW: scroll window down if selection moved past bottom
            if (this.selection >= this.viewTop + WINDOW_SIZE) {
                this.viewTop = this.selection - WINDOW_SIZE + 1;
            }
        }
    }

    private void dpadUp_pressed() {
        if (!busy) return;
        if (this.selection > 0) {
            this.selection -= 1;

            // --- NEW: scroll window up if selection moved above top
            if (this.selection < this.viewTop) {
                this.viewTop = this.selection;
            }
        }
    }

    private void a_pressed() {
        if (!busy) return;
        this.busy = false;
        if (this.menuCallback != null) this.menuCallback.accept(this.selection);
    }

    private void b_pressed() {
        if (!busy) return;
        this.busy = false;
        if (this.menuCallback != null) this.menuCallback.accept(-1);
    }

    @Override
    public int test(TestingEnviromentCore core) { return 0; }
}
