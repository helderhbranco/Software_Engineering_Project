package esii.grupo19;

import enums.*;

import java.util.UUID;

public class Flow {
    private UUID id;
    private String name;
    private FlowType flowType;
    private String comment;

    /**
     * Constructs a new Flow object with the specified attributes.
     * <p>
     * This constructor is used to create a representation of a material or resource flow within a system.
     * The parameters allow customization of essential properties, such as the name,
     * flowType (material,product,energy) and additional comments.
     *
     * @param name     The distinctive name of the flow.
     * @param flowType The nature of the flow, whether it's an input or an output.
     * @param comment  Additional comments or information about the flow.
     */
    public Flow(String name, FlowType flowType, String comment) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.flowType = flowType;
        this.comment = comment;
    }


    /**
     * Gets the id of the Flow.
     *
     * @return The id of the Flow.
     */
    public UUID getId() {
        return id;
    }

    /**
     * Gets the name of the Flow.
     *
     * @return The distinctive name of the Flow.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the Flow.
     * <p>
     * This method updates the distinctive name of the Flow with the provided value.
     *
     * @param name The new name to be assigned to the Flow.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the type of the Flow.
     *
     * @return The type of the Flow.
     */
    public FlowType getType() {
        return flowType;
    }

    /**
     * Sets the type of the Flow.
     * <p>
     * This method updates the type of the Flow with the provided value.
     *
     * @param flowType The new type to be assigned to the Flow.
     */

    public void setType(FlowType flowType) {
        this.flowType = flowType;
    }

    /**
     * Gets the comment of the Flow.
     *
     * @return The comment of the Flow.
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets the comment of the Flow.
     * <p>
     * This method updates the comment of the Flow with the provided value.
     *
     * @param comment The new comment to be assigned to the Flow.
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Returns a string representation of the Flow.
     * <p>
     * This method returns a string representation of the Flow, containing its name, type and comment.
     *
     * @return A string representation of the Flow.
     */
    public String toString() {
        String s = "";
        s += "Name: " + this.name + "\n";
        s += "Type: " + this.flowType + "\n";
        s += "Comment: " + this.comment + "\n";
        return s;
    }

    /**
     * Generates a CSV (Comma-Separated Values) string representation of the Circularity Flow information.
     * The CSV string includes details such as the name, flow type, and comment associated with the Circularity Flow.
     *
     * @return The generated CSV string representing Circularity Flow data.
     */
    public String toCSVString() {
        return this.name + "," + this.flowType + "," + this.comment + ", \n";
    }

}
