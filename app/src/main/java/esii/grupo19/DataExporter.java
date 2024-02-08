package esii.grupo19;

import java.io.FileWriter;
import java.io.IOException;

/**
 * The DataExporter class provides methods for exporting circularity-related data to various file formats,
 * such as CSV (Comma-Separated Values) and JSON (JavaScript Object Notation).
 * It includes functionality to export Circularity System and Circularity Calculator data to files.
 *
 * <p>The exported data can be used for analysis, sharing, or storage, allowing users to capture and
 * preserve circularity metrics and calculations.
 *
 * <p>This class ensures that the exported data is consistent and conforms to the specified file formats.
 * It includes methods for writing Circularity System and Circularity Calculator data to CSV and JSON files.
 */
public class DataExporter {

    /**
     * Writes Circularity System data to a CSV (Comma-Separated Values) file.
     * The method takes a ProductSystem object and a file name as input, generates a CSV string
     * representation using the ProductSystem's toCsvString method, and writes it to the specified file.
     *
     * <p>Before performing the write operation, the method checks that the provided ProductSystem and file name
     * are not null. If either is null, it throws an IllegalArgumentException.
     *
     * <p>Uses a try-with-resources block to automatically close the FileWriter after writing.
     *
     * @param productSystem The Circularity System data to be written to the CSV file.
     * @param fileName      The name of the CSV file to be created or overwritten.
     * @throws IOException              If an error occurs while writing to the file.
     * @throws IllegalArgumentException If the provided ProductSystem or fileName is null.
     */
    public void toCsvProductSystem(ProductSystem productSystem, String fileName) throws IOException, IllegalArgumentException {
        if (productSystem == null || fileName == null) {
            throw new IllegalArgumentException("ProductSystem or fileName must not be null. must not be null.");
        }
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(productSystem.toCsvString());
            System.out.println("Arquivo CSV criado com sucesso!");

        } catch (IOException e) {
            throw new IOException("Error writing to file");
        }

    }

    /**
     * Writes Circularity System data to a JSON (JavaScript Object Notation) file.
     * The method takes a ProductSystem object and a file name as input, and uses the ProductSystem's
     * saveToJson method to serialize the data into JSON format and write it to the specified file.
     *
     * <p>Before performing the write operation, the method checks that the provided ProductSystem and file name
     * are not null. If either is null, it throws an IllegalArgumentException.
     *
     * @param productSystem The Circularity System data to be written to the JSON file.
     * @param fileName      The name of the JSON file to be created or overwritten.
     * @throws IOException              If an error occurs while writing to the file.
     * @throws IllegalArgumentException If the provided ProductSystem or fileName is null.
     */
    public void toJsonProductSystem(ProductSystem productSystem, String fileName) throws IOException, IllegalArgumentException {
        if (productSystem == null || fileName == null) {
            throw new IllegalArgumentException("ProductSystem or fileName must not be null.");
        }
        productSystem.saveToJson(fileName);

    }

    /**
     * Writes Circularity Calculator data to a CSV (Comma-Separated Values) file.
     * The method takes a CircularityCalculator object and a file name as input, generates a CSV string
     * representation using the CircularityCalculator's toCsvString method, and writes it to the specified file.
     *
     * <p>Before performing the write operation, the method checks that the provided CircularityCalculator
     * and file name are not null. If either is null, it throws an IllegalArgumentException.
     *
     * <p>Uses a try-with-resources block to automatically close the FileWriter after writing.
     *
     * @param circularityCalculator The Circularity Calculator data to be written to the CSV file.
     * @param fileName              The name of the CSV file to be created or overwritten.
     * @throws IOException              If an error occurs while writing to the file.
     * @throws IllegalArgumentException If the provided CircularityCalculator or fileName is null.
     */
    public void toCsvCircularityCalculator(CircularityCalculator circularityCalculator, String fileName) throws IOException, IllegalArgumentException {
        if (circularityCalculator == null || fileName == null) {
            throw new IllegalArgumentException("Circularity Calculator or fileName must not be null.");
        }
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(circularityCalculator.toCsvString());
            System.out.println("Arquivo CSV criado com sucesso!");

        } catch (IOException e) {
            throw new IOException("Error writing to file");
        }
    }

}
