import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReader {
    BufferedReader reader;
    String delimiter;
    boolean hasHeader;

    List<String> columnLabels = new ArrayList<>();
    Map<String,Integer> columnLabelsToInt = new HashMap<>();

    String[] current;

    public CSVReader(Reader reader, String delimiter, boolean hasHeader){
        this.reader = new BufferedReader(reader);
        this.delimiter = delimiter;
        this.hasHeader = hasHeader;

        if(this.hasHeader){
            this.parseHeader();
        }
    }

    public CSVReader(String filename, String delimiter, boolean hasHeader) throws FileNotFoundException {
        this(new BufferedReader(new FileReader(filename)), delimiter, hasHeader);
    }

    public CSVReader(String filename, String delimiter) throws FileNotFoundException {
        this(filename, delimiter, true);
    }

    public CSVReader(String filename) throws FileNotFoundException {
        this(filename, ";", true);
    }

    void parseHeader(){
        String line = null;
        try {
            line = this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line == null){
            return;
        }

        String[] header = line.split(String.format("%s(?=([^\"]*\"[^\"]*\")*[^\"]*$)",this.delimiter));

        for (int i=0; i < header.length; ++i){
            this.columnLabels.add(header[i]);
            this.columnLabelsToInt.put(header[i], i);
        }
    }

    boolean next(){
        String line = null;

        try {
            line = this.reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(line == null){
            return false;
        }

        this.current = line.split(String.format("%s(?=([^\"]*\"[^\"]*\")*[^\"]*$)",this.delimiter));
        return true;
    }

    List<String> getColumnLabels(){
        return this.columnLabels;
    }

    int getRecordLength(){
        if(this.current == null){
            return 0;
        }
        return this.current.length;
    }

    boolean isMissing(int columnIndex){
        if(this.current == null){
            return true;
        }
        if (columnIndex >= this.current.length) {
            return true;
        }
        if (this.current[columnIndex].equals("")){
            return true;
        }

        return false;
    }

    boolean isMissing(String columnLabel){
        if(this.columnLabels.contains(columnLabel)){
            return isMissing(this.columnLabelsToInt.get(columnLabel));
        }

        return true;
    }

    String get(int columnIndex){
        if (this.current == null){
            return "";
        }

        if (columnIndex >= this.current.length) {
            return "";
        }

        return this.current[columnIndex];
    }

    String get(String columnLabel){
        if(this.columnLabels.contains(columnLabel)){
            return get(this.columnLabelsToInt.get(columnLabel));
        }

        return "";
    }

    int getInt(int columnIndex){
        return Integer.parseInt(get(columnIndex));
    }

    int getInt(String columnLabel){
        return Integer.parseInt(get(columnLabel));
    }

    long getLong(int columnIndex){
        return Long.parseLong(get(columnIndex));
    }

    long getLong(String columnLabel){
        return Long.parseLong(get(columnLabel));
    }

    double getDouble(int columnIndex){
        return Double.parseDouble(get(columnIndex));
    }

    double getDouble(String columnLabel){
        return Double.parseDouble(get(columnLabel));
    }

    LocalTime getTime(int columnIndex, String format){
        return LocalTime.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    LocalTime getTime(String columnLabel, String format){
        return LocalTime.parse(this.get(columnLabel), DateTimeFormatter.ofPattern(format));
    }

    LocalDate getDate(int columnIndex, String format){
        return LocalDate.parse(this.get(columnIndex), DateTimeFormatter.ofPattern(format));
    }

    LocalDate getDate(String columnLabel, String format){
        return LocalDate.parse(this.get(columnLabel), DateTimeFormatter.ofPattern(format));
    }

    public static void main(String args[]){

        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(new FileInputStream("titanic-part.csv"), Charset.forName("Cp1250"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        CSVReader reader = new CSVReader(isr,",",true);

        List<String> labels = reader.getColumnLabels();
        for (String s: labels){
            System.out.printf("%s ", s);
        }
        System.out.printf("\n");


        while(reader.next()){
            for (String s: reader.current){
                System.out.printf("%s ",s);
            }
            System.out.printf("\n");
        }
    }
}
