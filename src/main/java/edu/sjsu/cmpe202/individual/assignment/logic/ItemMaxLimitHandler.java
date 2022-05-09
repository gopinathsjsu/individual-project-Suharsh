package edu.sjsu.cmpe202.individual.assignment.logic;

import edu.sjsu.cmpe202.individual.assignment.model.InputItems;

import java.io.FileNotFoundException;
import java.util.*;

public class ItemMaxLimitHandler implements OrderHandler{

    private OrderHandler nextHandler;

    private StringBuilder errorBuffer = new StringBuilder();

    private final int MAX_LUXURY = 10;
    private final int MAX_ESSENTIAL = 10;
    private final int MAX_MISC = 10;


    @Override
    public void setNextHandler(OrderHandler orderHandler) {
        this.nextHandler = orderHandler;
    }

    @Override
    public void processOrder(List<InputItems> items) {

        Map<String, Integer> countMap = new HashMap<>();
        items.forEach(item->{
            Integer quantity = countMap.getOrDefault(item.getCategory(), 0);
            countMap.put(item.getCategory(), quantity + item.getQuantity());
        });

        if(countMap.getOrDefault("LUXURY",0)>MAX_LUXURY){
            errorBuffer.append("No of items exceeds max limit for luxury items, please correct the quantity below\n");
            items.stream().filter(item->item.getCategory().equals("LUXURY")).forEach(
                    data -> {errorBuffer.append(data.getItem() + " :(" + data.getQuantity() + ")\n");}
            );
        }

        if(countMap.getOrDefault("ESSENTIALS",0)>MAX_ESSENTIAL){
            errorBuffer.append("No of items exceeds max limit for essential items, please correct the quantity below\n");
            items.stream().filter(item->item.getCategory().equals("ESSENTIALS")).forEach(
                    data -> {errorBuffer.append(data.getItem() + " :(" + data.getQuantity() + ")\n");}
            );
        }

        if(countMap.getOrDefault("MISC",0)>MAX_MISC){
            errorBuffer.append("No of items exceeds max limit for misc items, please correct the quantity below\n");
            items.stream().filter(item->item.getCategory().equals("MISC")).forEach(
                    data -> {errorBuffer.append(data.getItem() + " :(" + data.getQuantity() + ")\n");}
            );
        }

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
