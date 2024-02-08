import enums.*;
import esii.grupo19.Flow;
import esii.grupo19.Process;
import esii.grupo19.Contact;
import esii.grupo19.ProcessFlow;
import exceptions.ElementNotFoundException;
import org.junit.jupiter.api.*;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProcessTest {

    Process process;
    Flow flow;
    Contact contact;
    ProcessFlow processFlow;


    @BeforeEach
    void setUp() {
        contact = new Contact("name", "email", "organization", "address");
        process = new Process("processName", "productName", ProcessType.primary, "operator", "location", "references", contact, "comment");
        flow = new Flow("flowName", FlowType.energy, "comment");
        processFlow = new ProcessFlow("flowName", "processName", 1.0, Unit.g, IOFlow.Input, State.virgin);
    }

    @Test
    void addProcessFlow() {
        //Test valid processFlow
        process.addProcessFlow(processFlow);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> process.addProcessFlow(processFlow));
        assertEquals("This Input already exists", exception.getMessage());

        //Test null processFlow
        exception = assertThrows(IllegalArgumentException.class, () -> process.addProcessFlow(null));
        assertEquals("Input is null", exception.getMessage());

        //Test existing processFlow
        exception = assertThrows(IllegalArgumentException.class, () -> process.addProcessFlow(processFlow));
        assertEquals("This Input already exists", exception.getMessage());
    }

    @Test
    void removeProcessFlow() throws ElementNotFoundException {
        processFlow = new ProcessFlow("flowName2", "processName", 1.0, Unit.g, IOFlow.Input, State.virgin);

        //empty list
        Exception exception = assertThrows(IllegalArgumentException.class, () -> process.removeProcessFlow(processFlow.getId()));
        assertEquals("Empty list", exception.getMessage());

        //null id
        process.addProcessFlow(processFlow);
        exception = assertThrows(IllegalArgumentException.class, () -> process.removeProcessFlow(null));
        assertEquals("Id must not be null.", exception.getMessage());

        //non-existing id
        exception = assertThrows(ElementNotFoundException.class, () -> process.removeProcessFlow(UUID.randomUUID()));
        assertEquals("ProcessFlow not found", exception.getMessage());

        //valid id
        assertEquals(processFlow, process.removeProcessFlow(processFlow.getId()));

    }


}

