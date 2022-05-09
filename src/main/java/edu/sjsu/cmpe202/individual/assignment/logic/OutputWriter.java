package edu.sjsu.cmpe202.individual.assignment.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class OutputWriter implements FileWriter{
    @Override
    public void writeToFile(List<String> lines) throws FileNotFoundException {
        File dir=new File("output");
        if(!dir.exists()) {
            dir.mkdir();
        }
        File file= new File("output/output.csv");
        try(PrintWriter writer=new PrintWriter(file)){
            for(String line :lines) {
                writer.write(line);
            }
        }
        System.out.println("Output result is available in output/output.txt");
    }
}
