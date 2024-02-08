import enums.*;
import esii.grupo19.Flow;
import esii.grupo19.Process;
import esii.grupo19.Contact;
import esii.grupo19.ProcessFlow;
import esii.grupo19.ProductSystem;


import static org.junit.jupiter.api.Assertions.*;

import exceptions.ElementNotFoundException;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class ProductSystemTest {

    ProductSystem productSystem;
    Flow flow;
    Process process;
    ProcessFlow processFlow;
    Contact contact;

    @BeforeEach
    void setUp() {
        productSystem = new ProductSystem();
        contact = new Contact("name", "email", "organization", "address");
        flow = new Flow("flowName", FlowType.energy, "comment");
        process = new Process("processName", "productName", ProcessType.primary, "operator", "location", "references", contact, "comment");
        processFlow = new ProcessFlow("flowName", "processName", 1.0, Unit.g, IOFlow.Input, State.virgin);
    }


    @Test
    void addProcess() {
        //Test valid process
        productSystem.addProcess(process);
        assertTrue(productSystem.getProcesses().contains(process));

        //Test null process
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productSystem.addProcess(null));
        assertEquals("Process must not be null.", exception.getMessage());
        //Test existing process
        exception = assertThrows(IllegalArgumentException.class, () -> productSystem.addProcess(process));
        assertEquals("Process already exists.", exception.getMessage());
    }

    @Test
    void removeProcess() throws ElementNotFoundException {
        process = new Process("processName2", "productName", ProcessType.primary, "operator", "location", "references", contact, "comment");

        //empty list
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productSystem.removeProcess(process.getName()));
        assertEquals("Empty list", exception.getMessage());
        //null name
        productSystem.addProcess(process);
        exception = assertThrows(IllegalArgumentException.class, () -> productSystem.removeProcess(null));
        assertEquals("Name must not be null.", exception.getMessage());

        //non-existing name
        exception = assertThrows(ElementNotFoundException.class, () -> productSystem.removeProcess("nonExistingName"));
        assertEquals("Process not found", exception.getMessage());

        //valid process
        assertEquals(process, productSystem.removeProcess(process.getName()));
        assertFalse(productSystem.getProcesses().contains(process));
    }

    @Test
    void addFlow() {
        //Test valid flow
        productSystem.addFlow(flow);
        assertTrue(productSystem.getFlows().contains(flow));

        //Test null flow
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productSystem.addFlow(null));
        assertEquals("Flow must not be null.", exception.getMessage());
        //Test existing flow
        exception = assertThrows(IllegalArgumentException.class, () -> productSystem.addFlow(flow));
        assertEquals("Flow already exists.", exception.getMessage());
    }

    @Test
    void removeFlow() throws ElementNotFoundException {
        Flow flow2 = new Flow("flowName2", FlowType.energy, "comment");

        //empty list
        Exception exception = assertThrows(IllegalArgumentException.class, () -> productSystem.removeFlow(flow.getName()));
        assertEquals("Empty list", exception.getMessage());
        //null name
        productSystem.addFlow(flow);
        exception = assertThrows(IllegalArgumentException.class, () -> productSystem.removeFlow(null));
        assertEquals("Name must not be null.", exception.getMessage());

        //non existing name
        exception = assertThrows(ElementNotFoundException.class, () -> productSystem.removeFlow("nonExistingName"));
        assertEquals("Flow not found", exception.getMessage());

        //valid flow
        assertEquals(flow, productSystem.removeFlow(flow.getName()));
        assertFalse(productSystem.getFlows().contains(flow));
    }

    @Test
    public void testsaveToJson() throws IOException {
        // Configurar o nome do arquivo
        String fileName = "testProductSystem.json";

        try {
            // Adicionar flow e process ao productSystem
            productSystem.addFlow(flow);
            productSystem.addProcess(process);

            // Executar o método de salvamento
            productSystem.saveToJson(fileName);

            // Verificar se o arquivo foi criado
            assertTrue(Files.exists(Path.of(fileName)), "O arquivo não foi criado.");

            // Verificar se o arquivo não está vazio
            assertTrue(new File(fileName).length() > 0, "O arquivo está vazio.");

            String fileContent = Files.readString(Path.of(fileName));
            System.out.println("Conteudo do arquivo JSON: " + fileContent);
            assertTrue(fileContent.contains("\"flowName\""), "O conteudo do arquivo JSON não está correto.");
        } finally {
            // Excluir o arquivo após o teste
            Files.deleteIfExists(Path.of(fileName));
        }

    }

}
