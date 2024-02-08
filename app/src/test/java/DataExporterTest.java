import enums.*;
import esii.grupo19.*;
import esii.grupo19.Process;
import org.junit.jupiter.api.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;


public class DataExporterTest {

    DataExporter dataExporter;
    ProductSystem productSystem;
    Flow flow;
    Process process;
    ProcessFlow processFlow;
    Contact contact;
    CircularityCalculator circularityCalculator;
    CircularityFlow circularityFlow;

    @Test
    public void testSaveToCsv() throws IOException {
        DataExporter dataExporter = new DataExporter();
        ProductSystem productSystem = new ProductSystem();
        Contact contact = new Contact("name", "email", "organization", "address");
        Flow flow = new Flow("flowName", FlowType.energy, "comment");
        Process process = new Process("processName", "productName", ProcessType.primary, "operator", "location", "references", contact, "comment");
        ProcessFlow processFlow = new ProcessFlow("flowName", "processName", 1.0, Unit.g, IOFlow.Input, State.virgin);

        productSystem.addFlow(flow);
        productSystem.addProcess(process);
        process.addProcessFlow(processFlow);

        String fileName = "testFlow.csv";
        try {

            // Tentar salvar os dados em um arquivo CSV
            dataExporter.toCsvProductSystem(productSystem, fileName);

            // Verificar se o arquivo foi criado
            assertTrue(new File(fileName).exists(), "Arquivo CSV não foi criado");

            // Verificar se o conteúdo do arquivo é o esperado
            assertCsvContent(fileName, "flowName,energy,comment, \n" + "processName,productName,primary,operator,location,references,name,email,organization,address,comment,\n" + "flowName,processName,1.0,g,Input,virgin\n");

        } finally {
            // Excluir o arquivo após o teste
            Files.deleteIfExists(Path.of(fileName));
        }
    }

    private void assertCsvContent(String fileName, String expectedContent) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            StringBuilder actualContent = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                actualContent.append(line).append("\n");
            }
            assertEquals(expectedContent, actualContent.toString());
        }
    }

}
