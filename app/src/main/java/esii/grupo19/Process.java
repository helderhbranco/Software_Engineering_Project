package esii.grupo19;
import enums.ProcessType;
import exceptions.ElementNotFoundException;

import java.util.LinkedList;
import java.util.UUID;

public class Process implements ProcessADT {
    String name;
    String productName;
    UUID id;
    String operator;
    ProcessType processType;
    String location;
    String references;
    Contact contact;
    String comment;
    LinkedList<ProcessFlow> processFlows;


    public Process(String name,String productName, ProcessType processType, String operator, String location, String references, Contact contact, String comment) {
        this.name = name;
        this.productName = productName;
        this.processType = processType;
        this.operator = operator;
        this.location = location;
        this.references = references;
        this.contact = contact;
        this.comment = comment;
        this.processFlows = new LinkedList<ProcessFlow>();
        this.id = UUID.randomUUID();
    }



    @Override
    public void addProcessFlow(ProcessFlow processFlow) throws IllegalArgumentException {
        if(processFlow ==null){
            throw new IllegalArgumentException("Input is null");
        }
        if(processFlows.contains(processFlow)){
            throw new IllegalArgumentException("This Input already exists");
        }
        this.processFlows.add(processFlow);
    }

    @Override
    public ProcessFlow removeProcessFlow(UUID id)throws ElementNotFoundException {
        if(processFlows.isEmpty()){
            throw new IllegalArgumentException("Empty list");
        }
        //if name is null
        if(id ==null){
            throw new IllegalArgumentException("Id must not be null.");
        }

        ProcessFlow remove = null;
        for (ProcessFlow processFlow : this.processFlows) {
            if(processFlow.getId().equals(id)){
                remove = processFlow;
                this.processFlows.remove(processFlow);
            }
        }if(remove == null){
            throw new ElementNotFoundException("ProcessFlow not found");
        }
        return remove;
    }

    @Override
    public String toString() {
        return "Process{" +
                "name='" + name + '\'' +
                ", id=" + id +
                ", operator='" + operator + '\'' +
                ", processType=" + processType +
                ", location='" + location + '\'' +
                ", references='" + references + '\'' +
                ", contact=" + contact.toString() +
                ", comment='" + comment + '\'' +
                ", processFlows=" + processFlows.toString() +
                '}';
    }
/**
     * Retrieves the product name associated with this Process.
     *
     * This method returns the product name of the Process as a String.
     *
     * @return A String representing the product name of the Process.
     */
    public String getProductName() {
        return productName;
    }
/**
     * Sets the product name for this Process.
     *
     * This method updates the product name of the Process with the specified value.
     *
     * @param productName A String representing the new product name to be set for the Process.
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     * Retrieves the process type associated with this Process.
     *
     * This method returns the process type of the Process as a ProcessType.
     *
     * @return A ProcessType representing the process type of the Process.
     */
    public ProcessType getProcessType() {
        return processType;
    }
    /**
     * Sets the process type for this Process.
     *
     * This method updates the process type of the Process with the specified value.
     *
     * @param processType A ProcessType representing the new process type to be set for the Process.
     */
    public void setProcessType(ProcessType processType) {
        this.processType = processType;
    }

    /**
     * Retrieves the id associated with this Process.
     *
     * This method returns the id of the Process as a UUID.
     *
     * @return A UUID representing the id of the Process.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Retrieves the name associated with this Process.
     *
     * This method returns the name of the Process as a String.
     *
     * @return A String representing the name of the Process.
     */
    public String getName() {
        return name;
    }


    /**
     * Sets the name for this Process.
     *
     * This method updates the name of the Process with the specified value.
     *
     * @param name A String representing the new name to be set for the Process.
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * Retrieves the comment associated with this Process.
     *
     * This method returns the comment of the Process as a String.
     *
     * @return A String representing the comment of the Process.
     */
    public String getComment() {
        return comment;
    }


    /**
     * Sets the comment for this Process.
     *
     * This method updates the comment of the Process with the specified value.
     *
     * @param comment A String representing the new comment to be set for the Process.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }


    /**
     * Retrieves the contact associated with this Process.
     *
     * This method returns the contact of the Process.
     *
     * @return A Contact object representing the associated Process.
     */
    public Contact getContact() {
        return contact;
    }


    /**
     * Sets the contact for this Process.
     *
     * This method updates the contact of the Process with the specified Contact object.
     *
     * @param contact The Contact object to be set for the associated Process.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }



    /**
     * Sets the location for this Process.
     *
     * This method updates the location of the Process with the specified value.
     *
     * @param location A String representing the new location to be set for the Process.
     */
    public void setLocation(String location) {
        this.location = location;
    }
    /**
     * Retrieves the location associated with this Process.
     *
     * This method returns the location of the Process as a String.
     *
     * @return A String representing the location of the Process.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Retrieves the operator associated with this Process.
     *
     * This method returns the operator of the Process as a String.
     *
     * @return A String representing the operator of the Process.
     */


    public String getOperator() {
        return operator;
    }
    /**
     * Sets the operator for this Process.
     *
     * This method updates the operator of the Process with the specified value.
     *
     * @param operator A String representing the new operator to be set for the Process.
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * Retrieves the references associated with this Process.
     *
     * This method returns the references of the Process as a String.
     *
     * @return A String representing the references of the Process.
     */
    public String getReferences() {
        return references;
    }
    /**
     * Sets the references for this Process.
     *
     * This method updates the references of the Process with the specified value.
     *
     * @param references A String representing the new references to be set for the Process.
     */
    public void setReferences(String references) {
        this.references = references;
    }

    /**
     * Retrieves the process flows associated with this Process.
     *
     * This method returns the process flows of the Process as a LinkedList of ProcessFlow objects.
     *
     * @return A LinkedList of ProcessFlow objects representing the process flows of the Process.
     */
    public LinkedList<ProcessFlow> getProcessFlows() {
        return processFlows;
    }
    /**
     * Sets the process flows for this Process.
     *
     * This method updates the process flows of the Process with the specified value.
     *
     * @param processFlows A LinkedList of ProcessFlow objects representing the new process flows to be set for the Process.
     */
    public void setProcessFlows(LinkedList<ProcessFlow> processFlows) {
        this.processFlows = processFlows;
    }

    public String toCSVString() {
        StringBuilder csvString = new StringBuilder();
        csvString.append(name).append(",")
                .append(productName).append(",")
                .append(processType).append(",")
                .append(operator).append(",")
                .append(location).append(",")
                .append(references).append(",")
                .append(contact.toCSVString()).append(",")
                .append(comment).append(",\n");

        if (!processFlows.isEmpty()) {

            for (ProcessFlow processFlow : processFlows) {
                csvString.append(processFlow.toCSVString());
            }
        }

        return csvString.toString();
    }
}

