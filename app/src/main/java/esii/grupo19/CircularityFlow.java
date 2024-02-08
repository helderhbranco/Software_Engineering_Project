package esii.grupo19;

import java.security.InvalidAlgorithmParameterException;

import javax.management.InvalidAttributeValueException;

import exceptions.*;

public class CircularityFlow {
    private String flowName;
    private double V;       //introduced, (State.Virgin), total virgin material flow
    private double R;       //introduced, material that will go through a recycling process
    private double Rr;      //introduced, material recovered after recycling
    private double Ri;      //introduced, total recycled material input flow
    private double Ep;      //calculated, 1 - (Wc/R), energy needed to produce principal products/materials
    private double Es;      //calculated, 1 - (Wf/V), energy needed to produce secondary products/materials
    private double W;       //calculated, (V-Rr), total waste
    private double Wc;      //introduced, output flow in recycling process
    private double Wf;      //introduced, waste in the production of materials
    private double M;       //calculated, (V+Ri), total input
    private double x;       //calculated, (l/lavg) * (u/uavg)
    private double fx;      //calculated, (0.9/x)
    private double LFI;     //calculated, (2 * V - Rr)/(2 * M + ((Wf - Wc)/2))
    private double MCIp;    //calculated, 1 - (LFI * fx)

    public CircularityFlow(String flowName) {
        this.flowName = flowName;
        this.V = 0;
        this.R = 0;
        this.Rr = 0;
        this.Ri = 0;
        this.Ep = Double.NaN;
        this.Es = Double.NaN;
        this.W = Double.NaN;
        this.Wc = 0;
        this.Wf = 0;
        this.M = Double.NaN;
        this.x = Double.NaN;
        this.fx = Double.NaN;
        this.LFI = Double.NaN;
        this.MCIp = Double.NaN;
    }

    public CircularityFlow(String flowName, double V, double R, double Rr, double Ri, double Wc, double Wf) {
        this.flowName = flowName;
        this.V = V;
        this.R = R;
        this.Rr = Rr;
        this.Ri = Ri;
        this.Ep = Double.NaN;
        this.Es = Double.NaN;
        this.W = Double.NaN;
        this.Wc = Wc;
        this.Wf = Wf;
        this.M = Double.NaN;
        this.x = Double.NaN;
        this.fx = Double.NaN;
        this.LFI = Double.NaN;
        this.MCIp = Double.NaN;
    }

    //setters and getters
    public String getFlowName() {
        return flowName;
    }

    public void setFlowName(String flowName) {
        this.flowName = flowName;
    }

    public double getV() {
        return V;
    }

    public void setV(double v) {
        V = v;
    }

    public double getR() {
        return R;
    }

    public void setR(double r) {
        R = r;
    }

    public double getRr() {
        return Rr;
    }

    public void setRr(double rr) {
        Rr = rr;
    }

    public double getRi() {
        return Ri;
    }

    public void setRi(double ri) {
        Ri = ri;
    }

    public double getEp() {
        if (((Double) this.Ep).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("Ep has not been calculated yet");
        }
        return Ep;
    }

    public double getEs() {
        if (((Double) this.Es).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("Es has not been calculated yet");
        }
        return Es;
    }

    public double getW() {
        if (((Double) this.W).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("W has not been calculated yet");
        }
        return W;
    }

    public double getWc() {
        return Wc;
    }

    public void setWc(double wc) {
        Wc = wc;
    }

    public double getWf() {
        return Wf;
    }

    public void setWf(double wf) {
        Wf = wf;
    }

    public double getM() {
        if (((Double) this.M).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("M has not been calculated yet");
        }
        return M;
    }

    public double getx() {
        if (((Double) this.x).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("x has not been calculated yet");
        }
        return x;
    }

    public double getFx() {
        if (((Double) this.fx).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("Fx has not been calculated yet");
        }
        return fx;
    }

    public double getLFI() {
        if (((Double) this.LFI).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("LFI has not been calculated yet");
        }
        return LFI;
    }

    public double getMCIp() {
        if (((Double) this.MCIp).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("MCIp has not been calculated yet");
        }
        return MCIp;
    }

    /**
     * Calculates the parameter W for a given scenario.
     * The parameter W is computed as the difference between the values of V (Volume) and Rr (Reference Volume).
     *
     * @return The calculated value of W.
     */
    public double calculateW() {
        W = V - Rr;

        return W;
    }

    /**
     * Calculates the parameter M for a given scenario.
     * The parameter M is computed as the sum of the values of V (Volume) and Ri (Incremental Volume).
     *
     * @return The calculated value of M.
     */
    public double calculateM() {
        M = V + Ri;

        return M;
    }

    /**
     * Calculates the Parameter Ep for a given scenario, representing the Efficiency Parameter.
     * The Efficiency Parameter (Ep) is computed based on the values of Wc (Circularity Weight) and R (Reference Circularity).
     * It is used to assess the efficiency of the circularity of a product or system.
     *
     * <p>If the reference circularity (R) is zero, a DivideByZeroException is thrown to prevent division by zero.
     * The Ep calculation involves taking the absolute value of R and then applying the formula: Ep = 1 - (Wc / |R|).
     * The resulting Ep value is rounded to two decimal places.
     *
     * @return The calculated Efficiency Parameter (Ep) for the given scenario.
     * @throws DivideByZeroException If the reference circularity (R) is zero, resulting in division by zero.
     */
    public double calculateEp() throws DivideByZeroException {
        if (R == 0) {
            throw new DivideByZeroException("Ep Division by zero");
        }

        double tmpR = Math.abs(R);

        Ep = 1 - (Wc / tmpR);
        Ep = Math.round(Ep * 100.0) / 100.0;

        return Ep;
    }

    /**
     * Calculates the Parameter Es for a given scenario, representing the Efficiency Parameter.
     * The Efficiency Parameter (Es) is computed based on the values of Wf (Functional Weight) and V (Volume).
     * It assesses the efficiency of the functionality provided by the product or system.
     *
     * <p>If the volume (V) is zero, a DivideByZeroException is thrown to prevent division by zero.
     * The Es calculation involves applying the formula: Es = 1 - (Wf / V).
     * The resulting Es value is rounded to two decimal places.
     *
     * @return The calculated Efficiency Parameter (Es) for the given scenario.
     * @throws DivideByZeroException If the volume (V) is zero, resulting in division by zero.
     */
    public double calculateEs() throws DivideByZeroException {
        if (V == 0) {
            throw new DivideByZeroException("Es Division by zero");
        }

        Es = 1 - (Wf / V);
        Es = Math.round(Es * 100.0) / 100.0;

        return Es;
    }

    /**
     * Calculates the parameter X for a given scenario, representing a metric derived from input parameters.
     * The parameter X assesses the relationship between lower (l) and upper (u) bounds with their respective averages (lavg and uavg).
     *
     * <p>If either lavg or uavg is zero, a DivideByZeroException is thrown to prevent division by zero.
     * If lavg or uavg is negative, an InvalidAlgorithmParameterException is thrown to indicate an invalid input value.
     *
     * <p>The X calculation involves applying the formula: X = (l / lavg) * (u / uavg).
     * The resulting X value is rounded to two decimal places.
     *
     * @param l    The lower bound value.
     * @param lavg The average value for lower bounds.
     * @param u    The upper bound value.
     * @param uavg The average value for upper bounds.
     * @return The calculated value of X for the given scenario.
     * @throws DivideByZeroException              If either lavg or uavg is zero, resulting in division by zero.
     * @throws InvalidAlgorithmParameterException If lavg or uavg is negative, indicating an invalid input value.
     */
    public double calculateX(int l, double lavg, int u, double uavg) throws DivideByZeroException, InvalidAlgorithmParameterException {
        if (lavg == 0 || uavg == 0) {
            throw new DivideByZeroException("X Division by zero");
        }
        if (lavg < 0 || uavg < 0) {
            throw new InvalidAlgorithmParameterException("Invalid input value");
        }
        x = (l / lavg) * (u / uavg);
        x = Math.round(x * 100.0) / 100.0;

        return x;
    }

    /**
     * Calculates the parameter Fx for a given scenario, representing a metric derived from input parameters.
     * The parameter Fx assesses the inverse relationship of the metric X, which is calculated using lower (l) and upper (u) bounds
     * with their respective averages (lavg and uavg).
     *
     * <p>The method internally calculates X using the provided input parameters and checks if X is zero.
     * If X is zero, a DivideByZeroException is thrown to prevent division by zero during the subsequent Fx calculation.
     *
     * <p>The Fx calculation involves applying the formula: Fx = 0.9 / X.
     * The resulting Fx value is rounded to two decimal places.
     *
     * @param l    The lower bound value.
     * @param lavg The average value for lower bounds.
     * @param u    The upper bound value.
     * @param uavg The average value for upper bounds.
     * @return The calculated value of Fx for the given scenario.
     * @throws DivideByZeroException              If the internally calculated X is zero, resulting in division by zero during Fx calculation.
     * @throws InvalidAlgorithmParameterException If invalid input values are provided for calculating X.
     */
    public double calculateFx(int l, double lavg, int u, double uavg) throws DivideByZeroException, InvalidAlgorithmParameterException {
        x = calculateX(l, lavg, u, uavg);
        if (x == 0) {
            throw new DivideByZeroException("Fx Division by zero");
        }
        fx = 0.9 / x;
        fx = Math.round(fx * 100.0) / 100.0;

        return fx;
    }

    /**
     * Calculates the Linear Functionality Index (LFI) for a given scenario.
     * The LFI is computed based on various parameters including volume (V), reference circularity (Rr),
     * incremental volume (Ri), functional weight (Wf), and circularity weight (Wc).
     *
     * <p>The method internally calculates two components, x and y, and then computes LFI using the formula: LFI = x / y.
     * The resulting LFI value is rounded to two decimal places.
     *
     * <p>It checks for a divide by zero scenario where y is zero, and throws a DivideByZeroException in such cases.
     * Additionally, it validates that the calculated LFI falls within the range [0, 1].
     * If LFI is outside this range or if Wc is greater than Wf, it throws an InvalidAttributeValueException.
     *
     * @return The calculated Linear Functionality Index (LFI) for the given scenario.
     * @throws DivideByZeroException              If the denominator (y) is zero, resulting in division by zero.
     * @throws InvalidAttributeValueException     If the calculated LFI is outside the range [0, 1],
     *                                            or if Wc is greater than Wf.
     * @throws DivideByZeroException              If a divide by zero scenario is encountered during the calculation.
     * @throws InvalidAlgorithmParameterException If there are invalid algorithm parameters during the calculation.
     */
    public double calculateLFI() throws DivideByZeroException, InvalidAlgorithmParameterException, InvalidAttributeValueException {
        double x = 2 * getV() - getRr();
        double y = 2 * calculateM() + ((getWf() - getWc()) / 2);
        if (y == 0) {
            throw new DivideByZeroException("LFI Division by zero");
        }
        LFI = x / y;
        LFI = Math.round(LFI * 100.0) / 100.0;

        if (LFI < 0 || LFI > 1) {
            throw new InvalidAttributeValueException("LFI should be beetwen 0 and 1");
        }
        if (getWf() - getWc() < 0) {
            throw new InvalidAttributeValueException("Wc can't be bigger than Wf");
        }

        return LFI;
    }

    /**
     * Calculates the Mass Circularity Index (MCIp) for a given scenario.
     * The MCIp is computed based on various parameters including lower (l) and upper (u) bounds with their averages (lavg and uavg),
     * reference circularity (R), reference circularity (Rr), circularity weight (Wc), and functional weight (Wf).
     *
     * <p>The method validates several conditions related to the input parameters before proceeding with the MCIp calculation.
     * It checks for invalid attribute values, ensuring that R is not equal to 0 when Rr is not 0, R is not greater or equal to W,
     * Rr + Wc equals the absolute value of R, W is not greater than M, and M is not smaller than V.
     *
     * <p>The MCIp calculation involves applying the formula: MCIp = 1 - (calculateLFI() * calculateFx(l, lavg, u, uavg)).
     * The resulting MCIp value is rounded to two decimal places.
     *
     * <p>It further validates that the calculated MCIp falls within the range [0, 1].
     * If MCIp is outside this range, it throws an InvalidAttributeValueException.
     *
     * @param l    The lower bound value.
     * @param lavg The average value for lower bounds.
     * @param u    The upper bound value.
     * @param uavg The average value for upper bounds.
     * @return The calculated Mass Circularity Index (MCIp) for the given scenario.
     * @throws DivideByZeroException              If a divide by zero scenario is encountered during the calculation.
     * @throws InvalidAttributeValueException     If any of the input parameter conditions are not met,
     *                                            or if the calculated MCIp is outside the range [0, 1].
     * @throws InvalidAlgorithmParameterException If there are invalid algorithm parameters during the calculation.
     */
    public double calculateMCIp(int l, double lavg, int u, double uavg) throws DivideByZeroException, InvalidAttributeValueException, InvalidAlgorithmParameterException {
        if (R == 0 && Rr != 0) {
            throw new InvalidAttributeValueException("R is equal to 0, Rr can't be different than 0!");
        }
        if (R >= calculateW()) {
            throw new InvalidAttributeValueException("R can't be bigger or equal to W");
        }
        if (Rr + Wc != Math.abs(R)) {
            throw new InvalidAttributeValueException("Rr + Wc should equal R");
        }
        if (calculateW() > calculateM()) {
            throw new InvalidAttributeValueException("W can't be bigger than M");
        }
        if (calculateM() < V) {
            throw new InvalidAttributeValueException("M can't be smaller than V");
        }

        MCIp = 1 - (calculateLFI() * calculateFx(l, lavg, u, uavg));

        if (MCIp < 0 || MCIp > 1) {
            throw new InvalidAttributeValueException("MCIp should be beetwen 0 and 1");
        }

        MCIp = Math.round(MCIp * 100.0) / 100.0;

        return MCIp;
    }

    @Override
    public String toString() {
        if (((Double) this.MCIp).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("MCIp has not been calculated yet");
        }

        if (((Double) this.Ep).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("Ep has not been calculated yet");
        }

        if (((Double) this.Es).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("Es has not been calculated yet");
        }

        return "CircularityFlow:" +
                "\nflowName='" + this.flowName +
                "'\n V=" + this.V +
                "\n R=" + this.R +
                "\n Rr=" + this.Rr +
                "\n Ri=" + this.Ri +
                "\n Ep=" + this.Ep +
                "\n Es=" + this.Es +
                "\n W=" + this.W +
                "\n Wc=" + this.Wc +
                "\n Wf=" + this.Wf +
                "\n M=" + this.M +
                "\n x=" + this.x +
                "\n fx=" + this.fx +
                "\n LFI=" + this.LFI +
                "\n MCIp=" + this.MCIp;
    }

    /**
     * Generates a CSV (Comma-Separated Values) string representation of the Circularity Flow data.
     * The CSV string includes information such as flow name, volumes (V, R, Rr, Ri), efficiency parameters (Ep, Es),
     * weights (W, Wc, Wf), metrics (M, X, Fx, LFI, MCIp).
     *
     * <p>Before generating the CSV string, the method checks if key metrics (MCIp, Ep, Es) have been calculated.
     * If any of these metrics is not available (NaN), it throws an IllegalStateException, indicating that the
     * corresponding metric has not been calculated yet.
     *
     * @return The generated CSV string representing Circularity Flow data.
     * @throws IllegalStateException If MCIp, Ep, or Es has not been calculated yet.
     */
    protected String toCSVString() {
        if (((Double) this.MCIp).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("MCIp has not been calculated yet");
        }

        if (((Double) this.Ep).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("Ep has not been calculated yet");
        }

        if (((Double) this.Es).isNaN()) { //if it has not yet been calculated
            throw new IllegalStateException("Es has not been calculated yet");
        }

        return this.flowName + "," + this.V + "," + this.R + "," + this.Rr + "," + this.Ri + "," + this.Ep + "," + this.Es + "," + this.W + "," + this.Wc + "," + this.Wf + "," + this.M + "," + this.x + "," + this.fx + "," + this.LFI + "," + this.MCIp;
    }
}
