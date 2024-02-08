package esii.grupo19;
import enums.*;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The Parser class provides methods for reading CSV files and creating a ProductSystem object
 * based on the data stored in the files. It supports parsing information related to processes, process flows,
 * and flows, creating corresponding objects, and assembling them into a cohesive ProductSystem representation.
 *
 * <p>The class includes a method {@code loadFromCsv} which reads a CSV file and converts its content into a
 * ProductSystem object. The CSV file is expected to contain information about processes, process flows, and flows,
 * and the class validates the format of each line during parsing.
 *
 * <p>Usage:
 * {@code
 * Parser parser = new Parser();
 * ProductSystem productSystem = parser.loadFromCsv("example.csv");
 * }
 *
 * <p>Supported CSV formats:
 * <ul>
 *     <li>For Processes: processName,productName,type,operator,location,references,name,email,organization,address,comment</li>
 *     <li>For ProcessFlows: flowName,processName,quantity,unit,ioFlow,state</li>
 *     <li>For Flows: flowName,flowType,comment</li>
 * </ul>
 *
 * <p>Throws IllegalArgumentException if the file name is null, and IOException if an invalid number of columns
 * or invalid arguments are encountered during parsing.
 */
public class Parser {

    /**
     * Reads a CSV file and returns its content as a String
     *
     * @param fileName name of the CSV file
     * @return Content of the CSV file
     */
    public ProductSystem loadFromCsv(String fileName) throws IOException {
        if (fileName == null) {
            throw new IllegalArgumentException("File name must not be null.");
        }
            ProductSystem productSystem = new ProductSystem();

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length == 11) {
                        // Process
                        try {
                            String processName = values[0].trim();
                            String productName = values[1].trim();
                            ProcessType type = ProcessType.valueOf(values[2].trim());
                            String operator = values[3].trim();
                            String location = values[4].trim();
                            String references = values[5].trim();
                            String name = values[6].trim();
                            String email = values[7].trim();
                            String organization = values[8].trim();
                            String address = values[9].trim();
                            String comment = values[10].trim();
                            Contact contact = new Contact(name, email, organization, address);
                            Process process = new Process(processName, productName, type, operator, location, references, contact, comment);
                            productSystem.addProcess(process);
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Invalid arguments");
                        }


                    } else if (values.length == 6) {
                        // ProcessFlow
                        try {


                            String flowName = values[0].trim();
                            String processName = values[1].trim();
                            double quantity = Double.parseDouble(values[2].trim());
                            Unit unit = Unit.valueOf(values[3].trim());
                            IOFlow ioFlow = IOFlow.valueOf(values[4].trim());
                            State state = State.valueOf(values[5].trim());
                            ProcessFlow processFlow = new ProcessFlow(flowName, processName, quantity, unit, ioFlow, state);
                            for (Process process : productSystem.getProcesses()) {
                                if (process.getName().equals(processName)) {
                                    process.addProcessFlow(processFlow);
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Invalid arguments");
                        }

                    } else if (values.length == 3) {
                        // Flow
                        try {


                            String flowName = values[0].trim();
                            FlowType flowType = FlowType.valueOf(values[1].trim());
                            String comment = values[2].trim();
                            Flow flow = new Flow(flowName, flowType, comment);
                            productSystem.addFlow(flow);
                        } catch (IllegalArgumentException e) {
                            throw new IllegalArgumentException("Invalid arguments");
                        }
                    } else {
                        throw new IOException("Invalid Number of lines");
                    }
                }
            }

            return productSystem;
        }


    }

