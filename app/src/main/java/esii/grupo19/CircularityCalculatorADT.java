package esii.grupo19;

import java.security.InvalidAlgorithmParameterException;

import javax.management.InvalidAttributeValueException;

import enums.*;
import exceptions.DivideByZeroException;

/**
 * Interface representing functionality related to Circularity Flows, Process Types, and Flow searches.
 */
public interface CircularityCalculatorADT {

    /**
     * Searches for Circularity Flows associated with the specified product name in the product system's processes.
     */
    public void searchCircularityFlow();

    /**
     * Calculates and updates Circularity Flows based on the provided process flow.
     *
     * @param processFlow The process flow for which flows are to be calculated and updated.
     */
    public void updateCircularityFlows(ProcessFlow processFlow);

    /**
     * Searches for the Process Type associated with the given Process Flow by matching the
     * process name with the processes in the product system.
     *
     * @param processFlow The process flow for which the process type is to be determined.
     * @return The Process Type associated with the provided Process Flow.
     * @throws IllegalArgumentException If no matching process is found in the product system.
     */
    public ProcessType searchProcessType(ProcessFlow processFlow);

    /**
     * Searches for a Flow in the product system based on the specified name.
     *
     * @param name The name of the Flow to be searched.
     * @return The Flow object associated with the provided name.
     * @throws IllegalArgumentException If no matching flow is found in the product system.
     */
    public Flow searchFlow(String name);

    /**
     * Calculates the Circularity Flow for a given product, based on the provided product name.
     * The Circularity Flow is determined by computing the Mass Circularity Index (MCI) for each
     * component in the product system and aggregating them to obtain the overall Circularity Flow.
     *
     * @return The Circularity Flow value for the specified product.
     * @throws IllegalArgumentException           If the provided product name is null or if the product system
     *                                            associated with this calculation is null.
     * @throws DivideByZeroException
     * @throws InvalidAttributeValueException
     * @throws InvalidAlgorithmParameterException
     */
    public double calculateCircularity() throws DivideByZeroException, InvalidAttributeValueException, InvalidAlgorithmParameterException;
}

