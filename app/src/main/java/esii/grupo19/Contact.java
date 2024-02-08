package esii.grupo19;

public class Contact {

    String name;
    String email;
    String phone;
    String address;


    public Contact(String name, String email, String phone, String address) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Retrieves the address associated with this Contact.
     *
     * @return A String representing the address of the Contact.
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address for this Contact.
     * <p>
     * This method updates the address of the Contact with the specified value.
     *
     * @param address A String representing the new address to be set for the Contact.
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Retrieves the email address associated with this Contact.
     *
     * @return A String representing the email address of the Contact.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address for this Contact.
     * <p>
     * This method updates the email address of the Contact with the specified value.
     *
     * @param email A String representing the new email address to be set for the Contact.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Retrieves the name associated with this Contact.
     *
     * @return A String representing the name of the Contact.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for this Contact.
     * <p>
     * This method updates the name of the Contact with the specified value.
     *
     * @param name A String representing the new name to be set for the Contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the phone number associated with this Contact.
     *
     * @return A String representing the phone number of the Contact.
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets the phone number for this Contact.
     * <p>
     * This method updates the phone number of the Contact with the specified value.
     *
     * @param phone A String representing the new phone number to be set for the Contact.
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * Retrieves a string representation of the Contact.
     */
    public String toString() {
        return "Name: " + this.name + "\n" + "Email: " + this.email + "\n" + "Phone: " + this.phone + "\n" + "Address: " + this.address + "\n";
    }

    /**
     * Retrieves a string representation of the Contact in CSV format.
     */
    public String toCSVString() {
        return this.name + "," + this.email + "," + this.phone + "," + this.address;
    }
}
