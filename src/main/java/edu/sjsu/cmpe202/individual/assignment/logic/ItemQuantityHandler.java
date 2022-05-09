package edu.sjsu.cmpe202.individual.assignment.logic;

import edu.sjsu.cmpe202.individual.assignment.db.DB;
import edu.sjsu.cmpe202.individual.assignment.model.InputItems;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;

public class ItemQuantityHandler implements OrderHandler{
    private OrderHandler nextHandler;

    private StringBuilder errorBuffer = new StringBuilder();

    @Override
    public void setNextHandler(OrderHandler nextHandler) {
        this.nextHandler = nextHandler;
    }

    @Override
    public void processOrder(List<InputItems> items) {
        DB db = DB.getInstance();
        items.forEach(item->{
            if(db.getItems().get(item.getItem()).getQuantity()<item.getQuantity()){
                errorBuffer.append("Please correct quantities: "+ item.getItem() + " :("+item.getQuantity()+")\n");
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
