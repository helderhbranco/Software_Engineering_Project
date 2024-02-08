package esii.grupo19;

import java.util.*;

import exceptions.ElementNotFoundException;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import java.io.FileWriter;
import java.io.IOException;


public class ProductSystem {

    // The default directory where the files will be saved
    private static final String DEFAULT_DIRECTORY = System.getProperty("user.home");
    private LinkedList<Flow> flows;
    private LinkedList<Process> processes;

    public ProductSystem() {
        this.flows = new LinkedList<Flow>();
        this.processes = new LinkedList<Process>();
    }


    public LinkedList<Flow> getFlows() {
        return this.flows;
    }

    public void setFlows(LinkedList<Flow> flows) {
        this.flows = flows;
    }


    public LinkedList<Process> getProcesses() {
        return this.processes;
    }

    public void setProcesses(LinkedList<Process> processes) {
        this.processes = processes;
    }

    /**
     * Retrieves a string representation of all flows in the collection.
     * <p>
     * This method iterates through the list of flows, concatenating the name
     * of each flow with a newline character. The resulting string is then returned.
     *
     * @return A string containing the names of all flows, each on a new line.
     */
    public String listAllFlows() {
        StringBuilder flowNames = new StringBuilder();

        for (Flow flow : this.flows) {
            flowNames.append(flow.getName()).append("\n");
        }

        return flowNames.toString();
    }

    /**
     * Retrieves a string representation of all processes in the collection.
     * <p>
     * This method iterates through the list of processes, concatenating the name
     * of each process with a newline character. The resulting string is then returned.
     *
     * @return A string containing the names of all processes, each on a new line.
     */
    public String listAllProcesses() {
        StringBuilder processNames = new StringBuilder();

        for (Process process : this.processes) {
            processNames.append(process.getName()).append("\n");
        }

        return processNames.toString();
    }

    /**
     * Adds a new flow to the collection of flows.
     *
     * @param flow The flow to be added. Must not be null.
     * @throws IllegalArgumentException If the flow parameter is null.
     */
    public void addFlow(Flow flow) throws IllegalArgumentException {

        // Check if the flow is null
        if (flow == null) {
            throw new IllegalArgumentException("Flow must not be null.");
        }

        // Check if the flow already exists in the linked list
        if (this.flows.contains(flow)) {
            throw new IllegalArgumentException("Flow already exists.");
        }

        this.flows.add(flow);

    }

    /**
     * Adds a new process to the collection of processes.
     *
     * @param process The process to be added. Must not be null.
     * @throws IllegalArgumentException If the process parameter is null.
     */
    public void addProcess(Process process) throws IllegalArgumentException {

        // Check if the process is null
        if (process == null) {
            throw new IllegalArgumentException("Process must not be null.");
        }

        // Check if the process already exists in the linked list
        if (this.processes.contains(process)) {
            throw new IllegalArgumentException("Process already exists.");
        }

        this.processes.add(process);

    }

    /**
     * Removes a flow with the specified name from the collection of flows.
     * <p>
     * This method iterates through the list of flows, searching for a flow
     * with a matching name. If found, the flow is removed from the collection,
     * and the removed flow is returned. If no matching flow is found, null
     * is returned.
     *
     * @param name The name of the flow to be removed. Must not be null.
     * @return The removed flow if found, or null if no flow with the specified name is found.
     * @throws IllegalArgumentException If the name parameter is null.
     * @throws IllegalArgumentException If the flow does not exist in the collection.
     */
    public Flow removeFlow(String name) throws IllegalArgumentException, ElementNotFoundException {
        if (this.flows.isEmpty()) {
            throw new IllegalArgumentException("Empty list");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null.");
        }
        Flow removed = null;
        for (Flow flow : this.flows) {
            if (flow.getName().equals(name)) {
                removed = flow;
                this.flows.remove(flow);
            }
        }
        if (removed == null) {
            throw new ElementNotFoundException("Flow not found");
        }

        return removed;
    }

    /**
     * Removes a process with the specified name from the collection of processes.
     * <p>
     * This method iterates through the list of processes, searching for a process
     * with a matching name. If found, the process is removed from the collection,
     * and the removed process is returned. If no matching process is found, null
     * is returned.
     *
     * @param name The name of the process to be removed. Must not be null.
     * @return The removed process if found, or null if no process with the specified name is found.
     * @throws IllegalArgumentException If the name parameter is null.
     * @throws IllegalArgumentException If the process does not exist in the collection.
     */
    public Process removeProcess(String name) throws IllegalArgumentException, ElementNotFoundException {
        if (this.processes.isEmpty()) {
            throw new IllegalArgumentException("Empty list");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null.");
        }
        Process removed = null;
        for (Process process : this.processes) {
            if (process.getName().equals(name)) {
                removed = process;
                this.processes.remove(process);

            }
        }
        if (removed == null) {
            throw new ElementNotFoundException("Process not found");
        }

        return removed;
    }

    /**
     * Returns a string representation of the ProductSystem.
     *
     * @return A string representation containing relevant information about the ProductSystem.
     */
    @Override
    public String toString() {
        String s = "";

        s += "Processes: {\n" + listAllProcesses() + "\n}\n";
        s += "Flows: {\n" + listAllFlows() + "\n}";

        return s;
    }

    /**
     * Saves the data of the ProductSystem to a JSON file.
     *
     * @param filePath The path to the JSON file where the data will be saved.
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public void saveToJson(String filePath) throws IOException {
        // Create the main JSON object to hold the data
        JSONObject jsonObject = new JSONObject();

        // Create and populate the JSON array for flows
        JSONArray flowArray = new JSONArray();
        for (Flow flow : flows) {
            JSONObject flowObject = new JSONObject();
            flowObject.put("id", flow.getId());
            flowObject.put("name", flow.getName());
            flowObject.put("comment", flow.getComment());
            flowObject.put("type", flow.getType().toString());
            flowArray.add(flowObject);
        }
        jsonObject.put("flows", flowArray);

        // Create and populate the JSON array for processes
        JSONArray processArray = new JSONArray();
        for (Process process : processes) {
            JSONObject processObject = new JSONObject();
            processObject.put("id", process.getId());
            processObject.put("name", process.getName());
            processObject.put("productName", process.getProductName());
            processObject.put("operator", process.getOperator());
            processObject.put("processType", process.getProcessType().toString());
            processObject.put("location", process.getLocation());
            processObject.put("references", process.getReferences());
            processObject.put("comment", process.getComment());

            // Create and populate the JSON object for contact
            JSONObject contactObject = new JSONObject();
            contactObject.put("name", process.getContact().getName());
            contactObject.put("email", process.getContact().getEmail());
            contactObject.put("phone", process.getContact().getPhone());
            contactObject.put("adress", process.getContact().getAddress());
            processObject.put("contact", contactObject);

            // Create and populate the JSON array for process flows
            JSONArray processFlowsArray = new JSONArray();
            for (ProcessFlow processFlow : process.getProcessFlows()) {
                JSONObject processFlowObject = new JSONObject();
                processFlowObject.put("id", processFlow.getId());
                processFlowObject.put("nameFlow", processFlow.getNameFlow());
                processFlowObject.put("nameProcess", processFlow.getNameProcess());
                processFlowObject.put("flowQuantity", processFlow.getFlowQuantity());
                processFlowObject.put("unit", processFlow.getUnit().toString());
                processFlowObject.put("ioflow", processFlow.getIOFlow().toString());
                processFlowObject.put("state", processFlow.getState().toString());
                processFlowsArray.add(processFlowObject);
            }
            processObject.put("processFlows", processFlowsArray);

            processArray.add(processObject);
        }
        jsonObject.put("processes", processArray);

        // Write the JSON data to the specified file
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toJSONString());
        }
    }

    /**
     * Generates a CSV (Comma-Separated Values) string representation of the ProductSystem.
     * The CSV string includes details such as flows and processes contained within the ProductSystem.
     *
     * <p>This method iterates over the list of flows and processes, invoking their respective toCSVString methods,
     * and appending the generated CSV strings to a StringBuilder. The resulting CSV string represents the entire
     * ProductSystem's information in a structured format.
     *
     * @return The generated CSV string representing the ProductSystem.
     */
    protected String toCsvString() {
        StringBuilder csvString = new StringBuilder();

        // Escrever objetos da classe Flow
        for (Flow flow : flows) {
            csvString.append(flow.toCSVString());
        }

        // Escrever objetos da classe Process
        for (Process process : processes) {
            csvString.append(process.toCSVString());
        }

        return csvString.toString();
    }
}


