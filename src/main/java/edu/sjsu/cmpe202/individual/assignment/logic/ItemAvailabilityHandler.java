package edu.sjsu.cmpe202.individual.assignment.logic;

import edu.sjsu.cmpe202.individual.assignment.db.DB;
import edu.sjsu.cmpe202.individual.assignment.model.InputItems;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class ItemAvailabilityHandler implements OrderHandler{

    private OrderHandler nextHandler;

    private StringBuilder errorBuffer = new StringBuilder();

    @Override
    public void setNextHandler(OrderHandler orderHandler) {
        this.nextHandler = orderHandler;
    }

    @Override
    public void processOrder(List<InputItems> items) {
        DB db = DB.getInstance();
        items.forEach(item->{
            if(!db.getItems().containsKey(item.getItem())){
                errorBuffer.append("Item is not present in the inventory\n");
                errorBuffer.append(item.getItem());
            }
        });
        if(errorBuffer.length()==0){
            System.out.println("Passing request next handler in chain");
            nextHandler.processOrder(items);
        }else{
            FileWriterFactory fileFactory=new FileWriterFactory();
            FileWriter errorWriter = fileFactory.getWriterInstance("ERROR");
            try {
                errorWriter.writeToFile(Arrays.asList(errorBuffer.toString()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
