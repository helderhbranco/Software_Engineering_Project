import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.security.InvalidAlgorithmParameterException;
import javax.management.InvalidAttributeValueException;

import esii.grupo19.CircularityFlow;
import exceptions.DivideByZeroException;

public class CircularityFlowTest {
    CircularityFlow cf0, cf1, cf2, cf3, cf4, cf5, cf6, cf7, cf8, cf9, cf10, cf11, cf12, cf13;
    DivideByZeroException exceptionDivideByZero;
    InvalidAlgorithmParameterException exceptionInvalidAlgorithmParameter;
    InvalidAttributeValueException exceptionInvalidAttributeValue;
    IllegalStateException exceptionIllegalState;


    @BeforeEach
    void setUp() {
        //Default CircularityFlows
        cf0 = new CircularityFlow("All Zeros", 0, 0, 0, 0, 0, 0);
        cf1 = new CircularityFlow("polyethylene terephthalate (PET) granulate", 247, -143.26, 103.15, 0, 40.11, 103.74);
        cf2 = new CircularityFlow("Low-density polyethylene (LD-PE)", 730, -423.38, 270.98, 0, 152.4, 306);
        cf3 = new CircularityFlow("adhesive PUR", 23, -13.34, 0, 0, 9.66, 9.66);

        //R = 0
        cf4 = new CircularityFlow("CF4", 20, 0, 0, 10, 0, 0);

        //V = 0
        cf5 = new CircularityFlow("CF5", 0, -13, 5, 20, 8, 13);

        //M < V (impossible)
        cf12 = new CircularityFlow("CF12", 10, -5, 5, -5, 0, 5);

        //Wc > Wf (impossible)
        cf6 = new CircularityFlow("CF6", 25, -10, 5, 2, 6, 3);

        //LFI > 1 (impossible)
        cf7 = new CircularityFlow("CF7", 100, -70, 0, 0, 70, 50);

        //Ri and Rr negatives (W > M) (impossible)
        cf8 = new CircularityFlow("CF8", 50, -50, -5, -10, 55, 25);

        //R > W (impossible)
        cf9 = new CircularityFlow("CF9", 30, -20, 100, 0, 5, 20);

        //R = W (impossible)
        cf10 = new CircularityFlow("CF10", 30, -20, 50, 0, 5, 20);

        //R = 0 so Rr != 0 (impossible)
        cf11 = new CircularityFlow("CF11", 20, 0, 10, 0, 0, 0);

        cf13 = new CircularityFlow("only name");
    }

    @Test
    void calculateWTest() {
        //Test valid CircularityFlow
        assertEquals(0, cf0.calculateW());
        assertEquals(143.85, cf1.calculateW());
        assertEquals(459.02, cf2.calculateW());
        assertEquals(23, cf3.calculateW());
        assertEquals(20, cf4.calculateW());
        assertEquals(-5, cf5.calculateW());
    }

    @Test
    void calculateMTest() {
        //Test valid CircularityFlow
        assertEquals(0, cf0.calculateM());
        assertEquals(247, cf1.calculateM());
        assertEquals(730, cf2.calculateM());
        assertEquals(23, cf3.calculateM());
        assertEquals(30, cf4.calculateM());
        assertEquals(20, cf5.calculateM());
    }

    @Test
    void calculateEpTest() throws DivideByZeroException {
        //Test valid CircularityFlow
        assertEquals(0.72, cf1.calculateEp());
        assertEquals(0.64, cf2.calculateEp());
        assertEquals(0.28, cf3.calculateEp());
        assertEquals(0.38, cf5.calculateEp());

        //Test R = 0
        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf0.calculateEp());
        assertEquals("Ep Division by zero", exceptionDivideByZero.getMessage());

        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf4.calculateEp());
        assertEquals("Ep Division by zero", exceptionDivideByZero.getMessage());
    }

    @Test
    void calculateEsTest() throws DivideByZeroException {
        //Test valid CircularityFlow
        assertEquals(0.58, cf1.calculateEs());
        assertEquals(0.58, cf2.calculateEs());
        assertEquals(0.58, cf3.calculateEs());
        assertEquals(1, cf4.calculateEs());

        //Test V = 0
        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf0.calculateEs());
        assertEquals("Es Division by zero", exceptionDivideByZero.getMessage());

        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf5.calculateEs());
        assertEquals("Es Division by zero", exceptionDivideByZero.getMessage());
    }

    @Test
    void calculateXTest() throws DivideByZeroException, InvalidAlgorithmParameterException {
        //Test valid CircularityFlow
        assertEquals(1, cf1.calculateX(10, 10, 10, 10));
        assertEquals(6, cf1.calculateX(20, 10, 30, 10));
        assertEquals(0, cf1.calculateX(0, 20, 0, 20));

        //Test Lavg = 0
        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf1.calculateX(10, 0, 10, 10));
        assertEquals("X Division by zero", exceptionDivideByZero.getMessage());

        //Test Uavg = 0
        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf1.calculateX(10, 10, 10, 0));
        assertEquals("X Division by zero", exceptionDivideByZero.getMessage());

        //Test Lavg = 0 and Uavg = 0
        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf1.calculateX(10, 0, 10, 0));
        assertEquals("X Division by zero", exceptionDivideByZero.getMessage());

        //Test Lavg < 0
        exceptionInvalidAlgorithmParameter = assertThrows(InvalidAlgorithmParameterException.class, () -> cf1.calculateX(10, -1, 10, 10));
        assertEquals("Invalid input value", exceptionInvalidAlgorithmParameter.getMessage());

        //Test Uavg < 0
        exceptionInvalidAlgorithmParameter = assertThrows(InvalidAlgorithmParameterException.class, () -> cf1.calculateX(10, 10, 10, -7));
        assertEquals("Invalid input value", exceptionInvalidAlgorithmParameter.getMessage());

        //Test Lavg < 0 and Uvag < 0
        exceptionInvalidAlgorithmParameter = assertThrows(InvalidAlgorithmParameterException.class, () -> cf1.calculateX(10, -9, 10, -8));
        assertEquals("Invalid input value", exceptionInvalidAlgorithmParameter.getMessage());
    }

    @Test
    void calculateFxTest() throws DivideByZeroException, InvalidAlgorithmParameterException {
        //Test valid CircularityFlow
        assertEquals(0.9, cf1.calculateFx(10, 10, 10, 10));
        assertEquals(0.15, cf1.calculateFx(20, 10, 30, 10));

        //Test x = 0
        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf1.calculateFx(0, 20, 0, 20));
        assertEquals("Fx Division by zero", exceptionDivideByZero.getMessage());
    }

    @Test
    void calculateLFITest() throws DivideByZeroException, InvalidAlgorithmParameterException, InvalidAttributeValueException {
        //Test valid CircularityFlow
        assertEquals(0.74, cf1.calculateLFI());
        assertEquals(0.77, cf2.calculateLFI());
        assertEquals(1, cf3.calculateLFI());
        assertEquals(0.67, cf4.calculateLFI());

        //Test negative LFI
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf5.calculateLFI());
        assertEquals("LFI should be beetwen 0 and 1", exceptionInvalidAttributeValue.getMessage());

        //Test LFI bigger than 1
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf7.calculateLFI());
        assertEquals("LFI should be beetwen 0 and 1", exceptionInvalidAttributeValue.getMessage());

        //Test denominator = 0
        exceptionDivideByZero = assertThrows(DivideByZeroException.class, () -> cf0.calculateLFI());
        assertEquals("LFI Division by zero", exceptionDivideByZero.getMessage());

        //Test Wc bigger than Wf
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf6.calculateLFI());
        assertEquals("Wc can't be bigger than Wf", exceptionInvalidAttributeValue.getMessage());
    }

    @Test
    void calculateMCIpTest() throws DivideByZeroException, InvalidAttributeValueException, InvalidAlgorithmParameterException {
        //Test valid CircularityFlow
        assertEquals(0.33, cf1.calculateMCIp(1, 1, 1, 1));
        assertEquals(0.31, cf2.calculateMCIp(1, 1, 1, 1));

        // Test if Rr + Wc is different than R
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf3.calculateMCIp(1, 1, 1, 1));
        assertEquals("Rr + Wc should equal R", exceptionInvalidAttributeValue.getMessage());

        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf6.calculateMCIp(1, 1, 1, 1));
        assertEquals("Rr + Wc should equal R", exceptionInvalidAttributeValue.getMessage());

        //Test if M is smaller than V
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf12.calculateMCIp(1, 1, 1, 1));
        assertEquals("M can't be smaller than V", exceptionInvalidAttributeValue.getMessage());

        //Test if W is bigger than M
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf8.calculateMCIp(1, 1, 1, 1));
        assertEquals("W can't be bigger than M", exceptionInvalidAttributeValue.getMessage());

        //Test if R is bigger than W
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf9.calculateMCIp(1, 1, 1, 1));
        assertEquals("R can't be bigger or equal to W", exceptionInvalidAttributeValue.getMessage());

        //Test if R is equal to W
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf10.calculateMCIp(1, 1, 1, 1));
        assertEquals("R can't be bigger or equal to W", exceptionInvalidAttributeValue.getMessage());

        //Test if R = 0 so Rr is different than 0
        exceptionInvalidAttributeValue = assertThrows(InvalidAttributeValueException.class, () -> cf11.calculateMCIp(1, 1, 1, 1));
        assertEquals("R is equal to 0, Rr can't be different than 0!", exceptionInvalidAttributeValue.getMessage());
    }

    @Test
    void toStringTest() throws DivideByZeroException, InvalidAttributeValueException, InvalidAlgorithmParameterException {
        //MCIp is not calculated
        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.toString());
        assertEquals("MCIp has not been calculated yet", exceptionIllegalState.getMessage());

        cf1.calculateMCIp(1, 1, 1, 1);

        //MCIp is calculated, Ep is not calculated
        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.toString());
        assertEquals("Ep has not been calculated yet", exceptionIllegalState.getMessage());

        cf1.calculateEp();

        //MCIp is calculated, Ep is calculated, Es is not calculated
        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.toString());
        assertEquals("Es has not been calculated yet", exceptionIllegalState.getMessage());

        cf1.calculateEs();

        //It's all calculated
        assertEquals("CircularityFlow:\nflowName='polyethylene terephthalate (PET) granulate'\n V=247.0\n R=-143.26\n Rr=103.15\n Ri=0.0\n Ep=0.72\n Es=0.58\n W=143.85\n Wc=40.11\n Wf=103.74\n M=247.0\n x=1.0\n fx=0.9\n LFI=0.74\n MCIp=0.33", cf1.toString());
    }

    @Test
    void getssetsTest() throws DivideByZeroException, InvalidAlgorithmParameterException, InvalidAttributeValueException {
        //Test invalid gets (before making the calculations)
        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getEp());
        assertEquals("Ep has not been calculated yet", exceptionIllegalState.getMessage());

        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getEs());
        assertEquals("Es has not been calculated yet", exceptionIllegalState.getMessage());

        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getW());
        assertEquals("W has not been calculated yet", exceptionIllegalState.getMessage());

        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getM());
        assertEquals("M has not been calculated yet", exceptionIllegalState.getMessage());

        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getx());
        assertEquals("x has not been calculated yet", exceptionIllegalState.getMessage());

        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getFx());
        assertEquals("Fx has not been calculated yet", exceptionIllegalState.getMessage());

        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getLFI());
        assertEquals("LFI has not been calculated yet", exceptionIllegalState.getMessage());

        exceptionIllegalState = assertThrows(IllegalStateException.class, () -> cf1.getMCIp());
        assertEquals("MCIp has not been calculated yet", exceptionIllegalState.getMessage());

        //Test valid CircularityFlow
        cf1.calculateMCIp(1, 1, 1, 1);
        cf1.calculateEp();
        cf1.calculateEs();

        cf1.setFlowName("teste");
        assertTrue(cf1.getFlowName() == "teste");
        cf1.setFlowName("polyethylene terephthalate (PET) granulate");

        assertEquals("polyethylene terephthalate (PET) granulate", cf1.getFlowName());
        assertEquals(247, cf1.getV());
        assertEquals(-143.26, cf1.getR());
        assertEquals(0, cf1.getRi());
        assertEquals(0.72, cf1.getEp());
        assertEquals(0.58, cf1.getEs());
        assertEquals(143.85, cf1.getW());
        assertEquals(40.11, cf1.getWc());
        assertEquals(103.74, cf1.getWf());
        assertEquals(247, cf1.getM());
        assertEquals(1, cf1.getx());
        assertEquals(0.9, cf1.getFx());
        assertEquals(0.74, cf1.getLFI());
        assertEquals(0.33, cf1.getMCIp());

    }
}