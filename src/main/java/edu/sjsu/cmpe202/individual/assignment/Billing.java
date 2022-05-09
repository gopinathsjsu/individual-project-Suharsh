package edu.sjsu.cmpe202.individual.assignment;

import edu.sjsu.cmpe202.individual.assignment.db.DB;
import edu.sjsu.cmpe202.individual.assignment.logic.*;
import edu.sjsu.cmpe202.individual.assignment.model.InputItems;

import java.util.List;

public class Billing {
    public static void main(String[] args) {
        if(args.length<1){
            System.out.println("Please provide the input file");
            return;
        }
        processOrder(args[0]);
    }

    private static void processOrder(String inputFile){

        ItemsFileReader fileReader = new ItemsFileReader();
        try {
            DB.getInstance().loadDB();
            List<InputItems> items = fileReader.getItems(inputFile);
            OrderHandler availabilityHandler = new ItemAvailabilityHandler();
            OrderHandler maxLimitHandler = new ItemMaxLimitHandler();
            OrderHandler quantityHandler = new ItemQuantityHandler();
            OrderHandler finaliseHandler = new FinaliseOrderHandler();
            availabilityHandler.setNextHandler(maxLimitHandler);
            maxLimitHandler.setNextHandler(quantityHandler);
            quantityHandler.setNextHandler(finaliseHandler);
            availabilityHandler.processOrder(items);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}