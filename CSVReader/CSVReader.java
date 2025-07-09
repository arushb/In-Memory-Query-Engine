// CSVReader/CSVReader.java
package CSVReader;

import table.Table;
import table.Vector;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class CSVReader{
    public static Table readCSV(String filePath) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String headerLine = reader.readLine();
        if(headerLine == null) throw new IOException("CSV file is empty.");
        String[] headers = headerLine.split(",");
        List<List<String>> rawColumns = new ArrayList<>();
        for(int i = 0; i < headers.length; i++){
            rawColumns.add(new ArrayList<>());
        }
        String line;

        while((line = reader.readLine()) != null){
            String[] values = line.split(",");
            for(int i = 0; i < headers.length; i++){
                rawColumns.get(i).add(values[i].trim());
            }
        }

        Map<String, Vector<?>> columns = new HashMap<>();
        for(int i = 0; i < headers.length; i++){
            String header = headers[i].trim();
            List<String> raw = rawColumns.get(i);
            Vector<?> vector = Converter(raw);
            columns.put(header, vector);
        }

        return new Table(columns);

    }

    private static Vector<?> Converter(List<String> raw){
        List<Object> passed = new ArrayList<>();
        boolean allInts = true;
        boolean allBools = true;

        for(String value : raw){
            if(!value.matches("-?\\d+")) allInts = false;
            if(!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) allBools = false;
        }
        if(allInts){
            for(String v : raw) passed.add(Integer.parseInt(v));
            return new Vector<>(passed);
        }
        if(allBools){
            for(String v : raw) passed.add(Boolean.parseBoolean(v));
            return new Vector<>(passed);
        }
        passed.addAll(raw);
        return new Vector<>(passed);
    }
}