import esii.grupo19.*;
import enums.*;
import esii.grupo19.Process;
import exceptions.DivideByZeroException;
import exceptions.ElementNotFoundException;
import org.junit.jupiter.api.*;
import javax.management.InvalidAttributeValueException;
import static org.junit.jupiter.api.Assertions.*;

public class CircularityCalculatorTest {

    ProductSystem productSystem;
    Flow flow;
    Process process;
    ProcessFlow processFlow;
    Contact contact;
    CircularityCalculator circularityCalculator;
    CircularityFlow circularityFlow;


    @Test
    void containsByName() {
        //true
        circularityCalculator = new CircularityCalculator("productName", 1, 1, 1, 1, productSystem);
        circularityFlow = new CircularityFlow("flowName");
        circularityCalculator.getCircularityFlows().add(circularityFlow);
        assertTrue(circularityCalculator.containsByName("flowName"));
        //false
        assertFalse(circularityCalculator.containsByName("flowName2"));
        //null
        Exception exception = new IllegalArgumentException("Null name");
        assertEquals("Null name", exception.getMessage());
    }

    @Test
    void searchCircularityFlow() throws ElementNotFoundException {
        circularityCalculator = new CircularityCalculator(null, 1, 1, 1, 1, null);

        //null productName
        Exception exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchCircularityFlow(), "Product name is null");
        assertEquals("Product name is null", exception.getMessage());

        //null productSystem
        circularityCalculator.setProductName("productName");
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchCircularityFlow(), "Product system is null");
        assertEquals("ProductSystem is null", exception.getMessage());

        //empty processes list
        productSystem = new ProductSystem();
        circularityCalculator.setProductSystem(productSystem);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchCircularityFlow(), "Empty Process list");
        assertEquals("Empty Process list", exception.getMessage());

        //empty ProcessFlows list
        process = new Process("processName", "productName", ProcessType.primary, "operator", "location", "references", contact, "comment");
        productSystem.addProcess(process);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchCircularityFlow(), "Empty ProcessFlow list");
        assertEquals("Empty ProcessFlow list", exception.getMessage());

        //empty flows list
        processFlow = new ProcessFlow("flowName", "processName", 1.0, Unit.g, IOFlow.Input, State.virgin);
        process.addProcessFlow(processFlow);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchCircularityFlow(), "Empty Flow list");
        assertEquals("Empty Flow list", exception.getMessage());

        //flow not found
        flow = new Flow("diferentFlowName", FlowType.energy, "comment");
        productSystem.addFlow(flow);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchCircularityFlow(), "Flow does not exist");
        assertEquals("Flow does not exist", exception.getMessage());

        //flow found type null
        flow.setType(null);
        flow.setName("flowName");
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchCircularityFlow(), "Invalid flow type");
        assertEquals("Invalid flow type", exception.getMessage());

        //new material flow
        flow.setType(FlowType.material);
        circularityCalculator.searchCircularityFlow();
        assertTrue(circularityCalculator.containsByName("flowName"));
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getV());

        //another material flow
        ProcessFlow processFlow2 = new ProcessFlow("flowName", "processName", 1.0, Unit.g, IOFlow.Input, State.virgin);
        process.addProcessFlow(processFlow2);
        circularityCalculator.searchCircularityFlow();
        assertTrue(circularityCalculator.containsByName("flowName"));
        assertEquals(2.0, circularityCalculator.getCircularityFlows().get(0).getV());

        //new service flow
        flow.setType(FlowType.service);
        process.removeProcessFlow(processFlow2.getId());
        circularityCalculator.searchCircularityFlow();
        assertTrue(circularityCalculator.containsByName("flowName"));
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getV());

        //another service flow
        process.addProcessFlow(processFlow2);
        circularityCalculator.searchCircularityFlow();
        assertTrue(circularityCalculator.containsByName("flowName"));
        assertEquals(2.0, circularityCalculator.getCircularityFlows().get(0).getV());
    }

    @Test
    void updateCircularityFlows() {
        //null processFlow
        productSystem = new ProductSystem();
        circularityCalculator = new CircularityCalculator("productName", 1, 1, 1, 1, productSystem);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.updateCircularityFlows(null), "Process flow is null");
        assertEquals("Process flow is null", exception.getMessage());

        //empty circularityFlows list
        processFlow = new ProcessFlow("flowName", "processName", 1.0, Unit.g, IOFlow.Input, null);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.updateCircularityFlows(processFlow), "Empty Circularity Flow list");

        //process type null
        circularityFlow = new CircularityFlow("flowName");
        circularityCalculator.getCircularityFlows().add(circularityFlow);
        process = new Process("processName", "productName", null, "operator", "location", "references", contact, "comment");
        productSystem.addProcess(process);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.updateCircularityFlows(processFlow), "Process type is null");
        assertEquals("Process type is null", exception.getMessage());

        //processFlow state invalid
        process.setProcessType(ProcessType.primary);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.updateCircularityFlows(processFlow), "Invalid processFlow state");
        assertEquals("Invalid processFlow state", exception.getMessage());

        //processFlow V
        processFlow.setState(State.virgin);
        circularityCalculator.updateCircularityFlows(processFlow);
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getV());

        //processFlow Ri
        processFlow.setState(State.recycled);
        processFlow.setIOFlow(IOFlow.Input);
        process.setProcessType(ProcessType.primary);
        circularityCalculator.updateCircularityFlows(processFlow);
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getRi());

        //processFlow R
        process.setProcessType(ProcessType.recycling);
        circularityCalculator.updateCircularityFlows(processFlow);
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getR());

        //processFlow Rr
        processFlow.setIOFlow(IOFlow.Output);
        circularityCalculator.updateCircularityFlows(processFlow);
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getRr());

        //processFlow Wc
        processFlow.setState(State.waste);
        circularityCalculator.updateCircularityFlows(processFlow);
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getWc());

        //processFlow Wf
        process.setProcessType(ProcessType.primary);
        circularityCalculator.updateCircularityFlows(processFlow);
        assertEquals(1.0, circularityCalculator.getCircularityFlows().get(0).getWf());
    }

    @Test
    void searchProcessType() {
        //null processFlow
        circularityCalculator = new CircularityCalculator("productName", 1, 1, 1, 1, productSystem);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchProcessType(null), "Process flow is null");
        assertEquals("Process flow is null", exception.getMessage());

        //null productSystem
        processFlow = new ProcessFlow("flowName", "processName", 1.0, Unit.g, IOFlow.Input, null);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchProcessType(processFlow), "ProductSystem is null");
        assertEquals("ProductSystem is null", exception.getMessage());

        //empty processes list
        productSystem = new ProductSystem();
        circularityCalculator.setProductSystem(productSystem);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchProcessType(processFlow), "Empty Process list");
        assertEquals("Empty Process list", exception.getMessage());

        //null process type
        process = new Process("processName", "productName", null, "operator", "location", "references", contact, "comment");
        productSystem.addProcess(process);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchProcessType(processFlow), "Process type is null");
        assertEquals("Process type is null", exception.getMessage());

        //invalid process name
        process.setProcessType(ProcessType.primary);
        process.setName("diferentProcessName");
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchProcessType(processFlow), "Process does not exist");
        assertEquals("Process does not exist", exception.getMessage());

        //primary process
        process.setName("processName");
        assertEquals(ProcessType.primary, circularityCalculator.searchProcessType(processFlow));

        //recycling process
        process.setProcessType(ProcessType.recycling);
        assertEquals(ProcessType.recycling, circularityCalculator.searchProcessType(processFlow));
    }

    @Test
    void searchFlow() {
        //null name
        circularityCalculator = new CircularityCalculator("productName", 1, 1, 1, 1, productSystem);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchFlow(null), "Flow name is null");
        assertEquals("Flow name is null", exception.getMessage());

        //null productSystem
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchFlow("flowName"), "ProductSystem is null");
        assertEquals("ProductSystem is null", exception.getMessage());

        //empty flows list
        productSystem = new ProductSystem();
        circularityCalculator.setProductSystem(productSystem);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchFlow("flowName"), "Empty Flow list");
        assertEquals("Empty Flow list", exception.getMessage());

        //flow not found
        flow = new Flow("diferentFlowName", FlowType.energy, "comment");
        productSystem.addFlow(flow);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.searchFlow("flowName"), "Flow does not exist");
        assertEquals("Flow does not exist", exception.getMessage());

        //flow found
        flow.setName("flowName");
        assertEquals(flow, circularityCalculator.searchFlow("flowName"));

    }

    @Test
    void calculateCircularity() throws DivideByZeroException, InvalidAttributeValueException {
        //null productName
        circularityCalculator = new CircularityCalculator(null, 1, 1, 1, 1, null);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.calculateCircularity(), "Product name is null");
        assertEquals("Product name is null", exception.getMessage());

        //null productSystem
        circularityCalculator.setProductName("productName");
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.calculateCircularity(), "ProductSystem is null");
        assertEquals("ProductSystem is null", exception.getMessage());

        //empty processes list
        productSystem = new ProductSystem();
        circularityCalculator.setProductSystem(productSystem);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.calculateCircularity(), "Empty Process list");
        assertEquals("Empty Process list", exception.getMessage());

        //empty flows list
        process = new Process("processName", "productName", ProcessType.primary, "operator", "location", "references", contact, "comment");
        productSystem.addProcess(process);
        exception = assertThrows(IllegalArgumentException.class, () -> circularityCalculator.calculateCircularity(), "Empty Flow list");
    }
}
