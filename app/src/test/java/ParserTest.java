import java.io.File;
import java.io.IOException;

import esii.grupo19.Parser;
import esii.grupo19.ProductSystem;
import org.junit.jupiter.api.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;


import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {
    Parser parser = new Parser();

    @Test
    public void testLoadFromCsv() throws IOException {

        String testFileName = "testFlow.csv";
        createTestCsvFile(testFileName);
        String absolutePath = new File(testFileName).getAbsolutePath();
        ProductSystem productSystem = parser.loadFromCsv(absolutePath);
        assertNotNull(productSystem);
        assertEquals(1, productSystem.getFlows().size());
        assertEquals(1, productSystem.getProcesses().size());
        Files.deleteIfExists(Path.of(testFileName));

    }

    @Test
    public void testLoadFromNullCsv() throws IOException {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.loadFromCsv(null));
        assertEquals("File name must not be null.", exception.getMessage());
    }

    @Test
    public void testLoadFromEmptyCsv() throws IOException {
        String emptyCsvFileName = "emptyFlow.csv";
        createEmptyCsvFile(emptyCsvFileName);
        ProductSystem productSystem = parser.loadFromCsv(emptyCsvFileName);
        assertNotNull(productSystem);
        assertEquals(0, productSystem.getFlows().size());
        assertEquals(0, productSystem.getProcesses().size());
        Files.deleteIfExists(Path.of(emptyCsvFileName));
    }

    @Test
    public void testLoadFromMalformattedCsv() throws IOException {
        String malformattedCsvFileName = "malformattedFlow.csv";
        createMalformattedCsvFile(malformattedCsvFileName);
        Exception exception = assertThrows(IOException.class, () -> parser.loadFromCsv(malformattedCsvFileName));
        assertEquals("Invalid Number of lines", exception.getMessage());
        Files.deleteIfExists(Path.of(malformattedCsvFileName));

    }

    @Test
    public void testLoadFromCsvWithInvalidData() throws IOException {
        String invalidDataCsvFileName = "invalidDataFlow.csv";
        createInvalidDataCsvFile(invalidDataCsvFileName);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> parser.loadFromCsv(invalidDataCsvFileName));
        assertEquals("Invalid arguments", exception.getMessage());
        Files.deleteIfExists(Path.of(invalidDataCsvFileName));

    }


    private void createInvalidDataCsvFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            //Wtite invalid data to file
            writer.write("flowName,energy,comment\n");
            writer.write("processName,productName,primary,operator,location,references,name,email,organization,address,comment\n");
            writer.write("flowName,processName,1.0,g,Input,virgin\n");
            writer.write("invalidFlow,processName,notANumber,g,Input,virgin\n");
        }
    }


    private void createTestCsvFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write the data correctly
            writer.write("flowName,energy,comment,\n");
            writer.write("processName,productName,primary,operator,location,references,name,email,organization,address,comment,\n");
            writer.write("flowName,processName,1.0,g,Input,virgin\n");

        }
    }

    private void createEmptyCsvFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {

        }
    }

    private void createMalformattedCsvFile(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Write poorly formated data
            writer.write("flowName,energy,comment\n");
            writer.write("processName,productName,primary,operator,location,references,name,email,organization,address,comment\n");
            writer.write("flowName,processName,1.0,g,Input,virgin\n");
            writer.write("linha,extra\n");
        }
    }


}