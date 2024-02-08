package esii.grupo19;

import exceptions.ElementNotFoundException;

import java.util.UUID;


public interface ProcessADT {

    /**
     * Adds a process flow to the process.
     *
     * @param processFlow The process flow to add to the process. Must not be null.
     * @throws IllegalArgumentException If the process flow parameter is null.
     */
    void addProcessFlow(ProcessFlow processFlow) throws IllegalArgumentException;

    /**
     * Removes a process flow from the process.
     *
     * @param id The id of the process flow to remove from the process. Must not be null.
     * @return The removed processFlow.
     * @throws IllegalArgumentException If the id parameter is null.
     * @throws ElementNotFoundException If the process flow does not exist in the process.
     */
    ProcessFlow removeProcessFlow(UUID id) throws ElementNotFoundException, IllegalArgumentException;


}

