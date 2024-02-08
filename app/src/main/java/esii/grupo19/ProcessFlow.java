package esii.grupo19;

import java.util.UUID;

import enums.*;

public class ProcessFlow {
    private UUID id;
    private String nameFlow;
    private String nameProcess;
    private double flowQuantity;
    private Unit unit;
    private IOFlow ioflow;
    private State state;

    /**
     * @param nameFlow     Name of the flow to associate with the process
     * @param nameProcess  Name of the process to associate with the flow
     * @param flowQuantity Quantity of the flow, specifying the amount or volume.
     * @param unit         Unit representative of the quantity of "flows"
     * @param ioflow       IOFlow (input/output)
     * @param state        State of the flow (virgin, recycled, waste).
     */
    public ProcessFlow(String nameFlow, String nameProcess, double flowQuantity, Unit unit, IOFlow ioflow, State state) {
        this.id = UUID.randomUUID();
        this.nameFlow = nameFlow;
        this.nameProcess = nameProcess;
        this.flowQuantity = flowQuantity;
        this.unit = unit;
        this.ioflow = ioflow;
        this.state = state;
    }

    public UUID getId() {
        return id;
    }

    public String getNameFlow() {
        return nameFlow;
    }

    public void setNameFlow(String nameFlow) {
        this.nameFlow = nameFlow;
    }

    public String getNameProcess() {
        return nameProcess;
    }

    public void setNameProcess(String nameProcess) {
        this.nameProcess = nameProcess;
    }

    public double getFlowQuantity() {
        return flowQuantity;
    }

    public void setFlowQuantity(double flowQuantity) {
        this.flowQuantity = flowQuantity;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public IOFlow getIOFlow() {
        return ioflow;
    }

    public void setIOFlow(IOFlow ioflow) {
        this.ioflow = ioflow;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        String s = "";

        s += "Flow name: " + this.nameFlow + "\n";
        s += "Process name: " + this.nameProcess + "\n";
        s += "Quantity: " + this.flowQuantity + "\n";
        s += "Unit: " + this.unit + "\n";
        s += "Input/Output: " + this.ioflow + "\n";
        s += "State: " + this.state + "\n";

        return s;
    }

    /**
     * Generates a CSV (Comma-Separated Values) string representation of the ProcessFlow information.
     * The CSV string includes details such as the flow name, process name, flow quantity, unit, input/output flow (IOFlow),
     * and state associated with the ProcessFlow.
     *
     * @return The generated CSV string representing ProcessFlow data.
     */
    public String toCSVString() {
        return this.nameFlow + "," + this.nameProcess + "," + this.flowQuantity + "," + this.unit + "," + this.ioflow + "," + this.state;
    }

}
