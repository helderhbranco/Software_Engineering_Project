package esii.grupo19;

/**
 * The Reports class provides methods to generate and print reports based on a CircularityCalculator object.
 * It includes functionality to print details of the CircularityCalculator, CircularityCalculator with CircularFlow details,
 * Circularity Flow waste, Circularity Flow virgin, Circularity Flow recycled, and to check Circularity result against a specified value.
 *
 * <p>Usage:
 * {@code
 * Reports reports = new Reports(circularityCalculator);
 * reports.printCircularityCalculator();
 * reports.printCircularityCalculatorWithCircularityFlow();
 * reports.printCircularityFlowWaste();
 * reports.printCircularityFlowVirgin();
 * reports.printCircularityFlowRecycled();
 * reports.printCircularityResultValue(0.5);
 * }
 */
public class Reports {

    private CircularityCalculator circularityCalculator;

    /**
     * Constructor for Reports class.
     *
     * @param circularityCalculator The CircularityCalculator object for which the report is generated.
     */
    public Reports(CircularityCalculator circularityCalculator) {
        this.circularityCalculator = circularityCalculator;
    }

    /**
     * Prints the details of the CircularityCalculator without CircularFlow details.
     * Outputs information such as product name, U, L, Lavg, Uavg, and the result of the CircularityCalculator.
     */
    public void printCircularityCalculator() {
        System.out.println("Circularity Calculator");
        System.out.println("Product Name: " + circularityCalculator.getProductName());
        System.out.println("U: " + circularityCalculator.getU());
        System.out.println("L: " + circularityCalculator.getL());
        System.out.println("Lavg: " + circularityCalculator.getLavg());
        System.out.println("Uavg: " + circularityCalculator.getUavg());
        System.out.println("Result: " + circularityCalculator.getResult());
    }

    /**
     * Prints the details of the CircularityCalculator along with CircularFlow details.
     * Outputs information such as product name, U, L, Lavg, Uavg, result of the CircularityCalculator,
     * and CircularFlow details including flow name, V, R, Rr, Ri, Ep, Es, W, and Wc.
     */
    public void printCircularityCalculatorWithCircularityFlow() {
        System.out.println("Circularity Calculator");
        System.out.println("Product Name: " + circularityCalculator.getProductName());
        System.out.println("U: " + circularityCalculator.getU());
        System.out.println("L: " + circularityCalculator.getL());
        System.out.println("Lavg: " + circularityCalculator.getLavg());
        System.out.println("Uavg: " + circularityCalculator.getUavg());
        System.out.println("Result: " + circularityCalculator.getResult());

        System.out.println("Circularity Flow");
        for (CircularityFlow circularityFlow : circularityCalculator.getCircularityFlows()) {
            System.out.println("Flow Name: " + circularityFlow.getFlowName());
            System.out.println("V: " + circularityFlow.getV());
            System.out.println("R: " + circularityFlow.getR());
            System.out.println("Rr: " + circularityFlow.getRr());
            System.out.println("Ri: " + circularityFlow.getRi());
            System.out.println("Ep: " + circularityFlow.getEp());
            System.out.println("Es: " + circularityFlow.getEs());
            System.out.println("W: " + circularityFlow.getW());
            System.out.println("Wc: " + circularityFlow.getWc() + "\n");
        }
    }

    /**
     * Prints details of the CircularityCalculator and checks if the Circularity result is above, below, or equal to a specified value.
     * Outputs information such as product name, U, L, Lavg, Uavg, and the result of the CircularityCalculator.
     * Additionally, checks and prints whether the Circularity is above, below, or equal to the specified value.
     *
     * @param value The specified value for comparison with the Circularity result.
     */
    public void printCircularityResultValue(double value) {
        if (circularityCalculator.getResult() > value) {
            System.out.println("Circularity is above " + value);

            System.out.println("Product Name: " + circularityCalculator.getProductName());
            System.out.println("U: " + circularityCalculator.getU());
            System.out.println("L: " + circularityCalculator.getL());
            System.out.println("Lavg: " + circularityCalculator.getLavg());
            System.out.println("Uavg: " + circularityCalculator.getUavg());
            System.out.println("Result: " + circularityCalculator.getResult());

        } else if (circularityCalculator.getResult() < value) {
            System.out.println("Circularity is below " + value);

            System.out.println("Product Name: " + circularityCalculator.getProductName());
            System.out.println("U: " + circularityCalculator.getU());
            System.out.println("L: " + circularityCalculator.getL());
            System.out.println("Lavg: " + circularityCalculator.getLavg());
            System.out.println("Uavg: " + circularityCalculator.getUavg());
            System.out.println("Result: " + circularityCalculator.getResult());
        } else {
            System.out.println("Circularity is equal to " + value);

            System.out.println("Product Name: " + circularityCalculator.getProductName());
            System.out.println("U: " + circularityCalculator.getU());
            System.out.println("L: " + circularityCalculator.getL());
            System.out.println("Lavg: " + circularityCalculator.getLavg());
            System.out.println("Uavg: " + circularityCalculator.getUavg());
            System.out.println("Result: " + circularityCalculator.getResult());
        }

    }

    /**
     * Prints the Circularity Calculator details and CircularFlow details for flows with positive waste (W) values.
     * Outputs information such as product name, U, L, Lavg, Uavg, result of the CircularityCalculator,
     * and CircularFlow details including flow name, Ep, Es, W, and Wc for flows with positive waste.
     */
    public void printCircularityFlowWaste() {
        System.out.println("Circularity Calculator");
        System.out.println("Product Name: " + circularityCalculator.getProductName());
        System.out.println("U: " + circularityCalculator.getU());
        System.out.println("L: " + circularityCalculator.getL());
        System.out.println("Lavg: " + circularityCalculator.getLavg());
        System.out.println("Uavg: " + circularityCalculator.getUavg());
        System.out.println("Result: " + circularityCalculator.getResult());

        System.out.println("Circularity Flow");
        for (CircularityFlow circularityFlow : circularityCalculator.getCircularityFlows()) {
            if (circularityFlow.getW() > 0) {
                System.out.println("Flow Name: " + circularityFlow.getFlowName());
                System.out.println("Ep: " + circularityFlow.getEp());
                System.out.println("Es: " + circularityFlow.getEs());
                System.out.println("W: " + circularityFlow.getW());
                System.out.println("Wc: " + circularityFlow.getWc() + "\n");
            }
        }
    }

    /**
     * Prints the Circularity Calculator details and CircularFlow details for flows with positive virgin (V) values.
     * Outputs information such as product name, U, L, Lavg, Uavg, result of the CircularityCalculator,
     * and CircularFlow details including flow name, V, Ep, Es for flows with positive virgin values.
     */
    public void printCircularityFlowVirgin() {
        System.out.println("Circularity Calculator");
        System.out.println("Product Name: " + circularityCalculator.getProductName());
        System.out.println("U: " + circularityCalculator.getU());
        System.out.println("L: " + circularityCalculator.getL());
        System.out.println("Lavg: " + circularityCalculator.getLavg());
        System.out.println("Uavg: " + circularityCalculator.getUavg());
        System.out.println("Result: " + circularityCalculator.getResult());

        System.out.println("Circularity Flow");
        for (CircularityFlow circularityFlow : circularityCalculator.getCircularityFlows()) {
            if (circularityFlow.getV() > 0) {
                System.out.println("Flow Name: " + circularityFlow.getFlowName());
                System.out.println("V: " + circularityFlow.getV());
                System.out.println("Ep: " + circularityFlow.getEp());
                System.out.println("Es: " + circularityFlow.getEs());

            }
        }
    }

    /**
     * Prints the Circularity Calculator details and CircularFlow details for flows with positive recycled (R) values.
     * Outputs information such as product name, U, L, Lavg, Uavg, result of the CircularityCalculator,
     * and CircularFlow details including flow name, R, Rr, Ri, Ep, Es for flows with positive recycled values.
     */
    public void printCircularityFlowRcycled() {
        System.out.println("Circularity Calculator");
        System.out.println("Product Name: " + circularityCalculator.getProductName());
        System.out.println("U: " + circularityCalculator.getU());
        System.out.println("L: " + circularityCalculator.getL());
        System.out.println("Lavg: " + circularityCalculator.getLavg());
        System.out.println("Uavg: " + circularityCalculator.getUavg());
        System.out.println("Result: " + circularityCalculator.getResult());

        System.out.println("Circularity Flow");
        for (CircularityFlow circularityFlow : circularityCalculator.getCircularityFlows()) {
            if (circularityFlow.getR() > 0) {
                System.out.println("Flow Name: " + circularityFlow.getFlowName());

                System.out.println("R: " + circularityFlow.getR());
                System.out.println("Rr: " + circularityFlow.getRr());
                System.out.println("Ri: " + circularityFlow.getRi());
                System.out.println("Ep: " + circularityFlow.getEp());
                System.out.println("Es: " + circularityFlow.getEs());
            }
        }
    }

}


