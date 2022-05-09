package edu.sjsu.cmpe202.individual.assignment.logic;

public class FileWriterFactory {
    public FileWriter getWriterInstance(String type){
        FileWriter fileWriter = null;
        if(type.equals("OUTPUT")) {
            fileWriter = new OutputWriter();
        }else if(type.equals("ERROR")) {
            fileWriter = new ErrorWriter();
        }
        return fileWriter;
    }
}
