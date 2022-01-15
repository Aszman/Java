import java.io.*;
import java.nio.charset.Charset;

import static org.junit.Assert.*;

public class CSVReaderTest {

    @org.junit.Test
    public void ReadFileWithHeader(){
        Reader actualReader = null;
        BufferedReader exceptedReader = null;
        try {
            actualReader  = new InputStreamReader(new FileInputStream("with-header.csv"), Charset.forName("Cp1250"));
            exceptedReader  = new BufferedReader(new InputStreamReader(new FileInputStream("with-header.csv"), Charset.forName("Cp1250")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        CSVReader reader = new CSVReader(actualReader,";",true);

        String[] exceptedLabels = null;
        try {
            exceptedLabels = exceptedReader.readLine().split(";");
        } catch (IOException e) {
            assertFalse(true);
        }

        assertArrayEquals(exceptedLabels,reader.columnLabels.toArray());

        while(reader.next()){
            String[] exceptedValues = null;

            try {
                exceptedValues = exceptedReader.readLine().split(";");
            } catch (IOException e) {
                assertFalse(true);
            }

            assertArrayEquals(exceptedValues, reader.current);
        }
    }

    @org.junit.Test
    public void ReadFileWithoutHeader(){
        Reader actualReader = null;
        BufferedReader exceptedReader = null;
        try {
            actualReader  = new InputStreamReader(new FileInputStream("no-header.csv"), Charset.forName("Cp1250"));
            exceptedReader  = new BufferedReader(new InputStreamReader(new FileInputStream("no-header.csv"), Charset.forName("Cp1250")));
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }
        CSVReader reader = new CSVReader(actualReader,";",false);

        while(reader.next()){
            String[] exceptedValues = null;

            try {
                exceptedValues = exceptedReader.readLine().split(";");
            } catch (IOException e) {
                assertFalse(true);
            }

            assertArrayEquals(exceptedValues, reader.current);
        }
    }

    @org.junit.Test
    public void getInt(){
        InputStreamReader isr = null;
        try {
            isr  = new InputStreamReader(new FileInputStream("elec.csv"), Charset.forName("Cp1250"));
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }
        CSVReader reader = new CSVReader(isr,",",true);

        reader.next();
        assertEquals(2,reader.getInt("day"));
    }

    @org.junit.Test
    public void getLong(){
        InputStreamReader isr = null;
        try {
            isr  = new InputStreamReader(new FileInputStream("accelerator.csv"), Charset.forName("Cp1250"));
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }
        CSVReader reader = new CSVReader(isr,";",true);

        reader.next();
        assertEquals(518845052664L,reader.getLong("time"));
    }

    @org.junit.Test
    public void getDouble(){
        InputStreamReader isr = null;
        try {
            isr  = new InputStreamReader(new FileInputStream("elec.csv"), Charset.forName("Cp1250"));
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }
        CSVReader reader = new CSVReader(isr,",",true);

        reader.next();
        assertEquals(0.056443,reader.getDouble("nswprice"),0.00001);
    }

    @org.junit.Test
    public void MissingValues(){
        InputStreamReader isr = null;
        try {
            isr  = new InputStreamReader(new FileInputStream("missing-values.csv"), Charset.forName("Cp1250"));
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }
        CSVReader reader = new CSVReader(isr,";",true);

        reader.next();
        reader.next();
        assertTrue(reader.isMissing(4));
        reader.next();
        assertTrue(reader.isMissing("density"));
    }

    @org.junit.Test
    public void GetStringFromNonExistentIndex(){
        InputStreamReader isr = null;
        try {
            isr  = new InputStreamReader(new FileInputStream("missing-values.csv"), Charset.forName("Cp1250"));
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }
        CSVReader reader = new CSVReader(isr,";",true);

        reader.next();

        assertEquals("",reader.get(20));
    }

    @org.junit.Test
    public void GetStringFromNonExistentLabel(){
        InputStreamReader isr = null;
        try {
            isr  = new InputStreamReader(new FileInputStream("missing-values.csv"), Charset.forName("Cp1250"));
        } catch (FileNotFoundException e) {
            assertFalse(true);
        }
        CSVReader reader = new CSVReader(isr,";",true);

        reader.next();

        assertEquals("",reader.get("ItIsALabel"));
    }

    @org.junit.Test
    public void CSVFromString(){
        String text = "produkt,ilosc,koszt(zł)\nmleko,3,4.40\njajka,2,6.99\nchleb,1,5.49";
        CSVReader reader = new CSVReader(new StringReader(text),",",true);

        String[] expectedLabels = new String[]{"produkt","ilosc","koszt(zł)"};
        assertArrayEquals(expectedLabels,reader.columnLabels.toArray());

        reader.next();
        String[] expectedRecord1 = new String[]{"mleko", "3", "4.40"};
        assertArrayEquals(expectedRecord1, reader.current);

        reader.next();

        String[] expectedRecord2 = new String[]{"jajka", "2", "6.99"};
        assertArrayEquals(expectedRecord2, reader.current);

        reader.next();
        String[] expectedRecord3 = new String[]{"chleb", "1", "5.49"};
        assertArrayEquals(expectedRecord3, reader.current);
    }
}