package esii.grupo19;

import java.security.InvalidAlgorithmParameterException;
import java.util.LinkedList;

import javax.management.InvalidAttributeValueException;

import enums.*;
import exceptions.DivideByZeroException;


public class CircularityCalculator implements CircularityCalculatorADT {
    private static final String DEFAULT_DIRECTORY = System.getProperty("user.home");
    private String productName;
    private int U;
    private int L;
    private double Lavg;
    private double Uavg;
    private double result;
    ProductSystem productSystem;
    LinkedList<CircularityFlow> circularityFlows;

    public CircularityCalculator(String productName, int u, int l, double lavg, double uavg, ProductSystem productSystem) {
        this.productSystem = productSystem;
        this.productName = productName;
        this.U = u;
        this.L = l;
        this.Lavg = lavg;
        this.Uavg = uavg;
        this.circularityFlows = new LinkedList<>();
        this.result = 0;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getU() {
        return U;
    }

    public void setU(int u) {
        U = u;
    }

    public int getL() {
        return L;
    }

    public void setL(int l) {
        L = l;
    }

    public double getLavg() {
        return Lavg;
    }

    public void setLavg(double lavg) {
        Lavg = lavg;
    }

    public double getUavg() {
        return Uavg;
    }

    public void setUavg(double uavg) {
        Uavg = uavg;
    }

    public double getResult() {
        return result;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public ProductSystem getProductSystem() {
        return productSystem;
    }

    public void setProductSystem(ProductSystem productSystem) {
        this.productSystem = productSystem;
    }

    public LinkedList<CircularityFlow> getCircularityFlows() {
        return circularityFlows;
    }

    public void setCircularityFlows(LinkedList<CircularityFlow> circularityFlows) {
        this.circularityFlows = circularityFlows;
    }

    /**
     * Checks if there is a CircularityFlow with the specified name in the list.
     *
     * @param name The name of the CircularityFlow to be searched for.
     * @return {@code true} if a CircularityFlow with the specified name is found, otherwise {@code false}.
     * @throws IllegalArgumentException If the provided name is null or if the list of CircularityFlows is empty.
     */
    public boolean containsByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name is null");
        }
        for (CircularityFlow circularityFlow : circularityFlows) {
            if (circularityFlow.getFlowName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void searchCircularityFlow() {
        if (productName == null) {
            throw new IllegalArgumentException("Product name is null");
        }
        if (productSystem == null) {
            throw new IllegalArgumentException("ProductSystem is null");
        }
        if (productSystem.getProcesses().isEmpty()) {
            throw new IllegalArgumentException("Empty Process list");
        }
        circularityFlows.clear();
        for (Process process : productSystem.getProcesses()) {
            if (process.getProductName().equals(productName)) {
                if (process.getProcessFlows().isEmpty()) {
                    throw new IllegalArgumentException("Empty ProcessFlow list");
                }
                for (ProcessFlow processFlow : process.getProcessFlows()) {
                    Flow flow = searchFlow(processFlow.getNameFlow());
                    // Update Circularity Flows for material or service flows
                    if (flow.getType() == FlowType.material || flow.getType() == FlowType.service) {
                        if (!containsByName(flow.getName())) {
                            circularityFlows.add(new CircularityFlow(flow.getName()));
                        }
                        updateCircularityFlows(processFlow);
                    } else if (flow.getType() != FlowType.energy) {
                        throw new IllegalArgumentException("Invalid flow type");
                    }
                }
            }
        }
        if (circularityFlows.isEmpty()) {
            throw new IllegalArgumentException("There are no processes with this name: " + productName);
        }
    }

    @Override
    public void updateCircularityFlows(ProcessFlow processFlow) {
        if (processFlow == null) {
            throw new IllegalArgumentException("Process flow is null");
        }
        if (circularityFlows.isEmpty()) {
            throw new IllegalArgumentException("Empty Circularity Flow list");
        }
        for (CircularityFlow circularityFlow : circularityFlows) {
            if (circularityFlow.getFlowName().equals(processFlow.getNameFlow())) {
                ProcessType type = searchProcessType(processFlow);
                State state = processFlow.getState();
                if (state == null) {
                    throw new IllegalArgumentException("Invalid processFlow state");
                }
                switch (state) {
                    case virgin:
                        circularityFlow.setV(circularityFlow.getV() + processFlow.getFlowQuantity());
                        break;
                    case recycled:
                        //Ri
                        if (processFlow.getIOFlow() == IOFlow.Input && type == ProcessType.primary) {
                            circularityFlow.setRi(circularityFlow.getRi() + processFlow.getFlowQuantity());
                            //R
                        } else if (processFlow.getIOFlow() == IOFlow.Input && type == ProcessType.recycling) {
                            circularityFlow.setR(circularityFlow.getR() + processFlow.getFlowQuantity());
                            //Rr
                        } else if (processFlow.getIOFlow() == IOFlow.Output && type == ProcessType.recycling) {
                            circularityFlow.setRr(circularityFlow.getRr() + processFlow.getFlowQuantity());
                        }
                        break;
                    case waste:
                        //Wf
                        if (type == ProcessType.primary) {
                            circularityFlow.setWf(circularityFlow.getWf() + processFlow.getFlowQuantity());
                            //Wc
                        } else if (type == ProcessType.recycling) {
                            circularityFlow.setWc(circularityFlow.getWc() + processFlow.getFlowQuantity());
                        }
                        break;
                }
            }
        }

    }

    @Override
    public ProcessType searchProcessType(ProcessFlow processFlow) {
        if (processFlow == null) {
            throw new IllegalArgumentException("Process flow is null");
        }
        if (productSystem == null) {
            throw new IllegalArgumentException("ProductSystem is null");
        }
        if (productSystem.getProcesses().isEmpty()) {
            throw new IllegalArgumentException("Empty Process list");
        }
        for (Process process : productSystem.getProcesses()) {
            if (process.getName().equals(processFlow.getNameProcess())) {
                if (process.getProcessType() == null) {
                    throw new IllegalArgumentException("Process type is null");
                }
                return process.getProcessType();
            }
        }
        throw new IllegalArgumentException("Process does not exist");
    }

    @Override
    public Flow searchFlow(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Flow name is null");
        }
        if (productSystem == null) {
            throw new IllegalArgumentException("ProductSystem is null");
        }
        if (productSystem.getFlows().isEmpty()) {
            throw new IllegalArgumentException("Empty Flow list");
        }
        for (Flow flow : productSystem.getFlows()) {
            if (flow.getName().equals(name)) {
                return flow;
            }
        }
        throw new IllegalArgumentException("Flow does not exist");
    }


    @Override
    public double calculateCircularity() throws DivideByZeroException, InvalidAttributeValueException, InvalidAlgorithmParameterException {
        // Auxiliary variables for MCI calculation
        double x = 0;
        double y = 0;
        // Validate input parameters
        if (productName == null) {
            throw new IllegalArgumentException("Product name is null");
        }
        if (productSystem == null) {
            throw new IllegalArgumentException("ProductSystem is null");
        }
        if (productSystem.getProcesses().isEmpty()) {
            throw new IllegalArgumentException("Empty Process list");
        }
        if (productSystem.getFlows().isEmpty()) {
            throw new IllegalArgumentException("Empty Flow list");
        }
        // Initialize variables and retrieve Circularity Flow information
        searchCircularityFlow();
        // Calculate total MCI
        for (CircularityFlow circularityFlow : circularityFlows) {
            x += (circularityFlow.calculateMCIp(L, Lavg, U, Uavg) * circularityFlow.calculateM());
            y += circularityFlow.calculateM();
        }
        if (y == 0) {
            throw new DivideByZeroException("MCI Division by zero");
        }
        this.result = x / y;
        // Validate Circularity Flow value
        if (this.result < 0 || this.result > 1) {
            throw new IllegalArgumentException("Invalid Circularity Flow value");
        }
        // Return the calculated Circularity Flow value
        return this.result;
    }

    /**
     * Generates a CSV (Comma-Separated Values) string representation of the Circularity data for a product.
     * The CSV string includes information such as product name, product system details, lower and upper bounds,
     * average lower and upper bounds, result, and Circularity Flow details for each component.
     *
     * <p>The method constructs the CSV string by concatenating the relevant attributes with commas and newline characters.
     * It also ensures the product name and product system are non-null, and throws an IllegalArgumentException if either
     * is null. Additionally, it checks for an empty Circularity Flow list and raises an exception if applicable.
     *
     * <p>Usage:
     * {@code
     * CircularityEngine circularityEngine = new CircularityEngine();
     * String csvString = circularityEngine.toCsvString();
     * }
     *
     * @return A CSV string representing Circularity data for the product.
     * @throws IllegalArgumentException           If the product name or product system is null, or if the Circularity Flow list is empty.
     * @throws DivideByZeroException              If a divide by zero scenario is encountered during the Circularity calculation.
     * @throws InvalidAttributeValueException     If there is an invalid attribute value during the Circularity calculation.
     * @throws InvalidAlgorithmParameterException If there is an invalid algorithm parameter during the Circularity calculation.
     * @throws DivideByZeroException              If a divide by zero scenario is encountered during the Circularity calculation.
     */
    public String toCsvString() throws IllegalArgumentException {

        String s = "";
        s += this.productName + ",";
        s += this.productSystem.toCsvString() + ",";
        s += this.U + ",";
        s += this.L + ",";
        s += this.Lavg + ",";
        s += this.Uavg + ",";
        s += this.result + "\n";

        if (circularityFlows.isEmpty()) {
            throw new IllegalArgumentException("Empty Circularity Flow list");

        } else {
            for (CircularityFlow circularityFlow : circularityFlows) {
                s += circularityFlow.toCSVString() + "\n";
            }
        }
        return s;

    }

}