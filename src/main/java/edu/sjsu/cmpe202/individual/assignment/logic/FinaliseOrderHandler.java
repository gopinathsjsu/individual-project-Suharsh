package edu.sjsu.cmpe202.individual.assignment.logic;

import edu.sjsu.cmpe202.individual.assignment.db.DB;
import edu.sjsu.cmpe202.individual.assignment.model.InputItems;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FinaliseOrderHandler implements OrderHandler{
    private OrderHandler nextHandler;

    private StringBuilder errorBuffer = new StringBuilder();

    List<String> content = new ArrayList<>();

    @Override
    public void setNextHandler(OrderHandler orderHandler) {
        this.nextHandler = orderHandler;
    }


    @Override
    public void processOrder(List<InputItems> items) {
        DB db = DB.getInstance();
        double total = 0;
        for(InputItems item:items) {

            total += db.getItems().get(item.getItem()).getPrice() * item.getQuantity();
            int currentQuantity = db.getItems().get(item.getItem()).getQuantity();
            db.getItems().get(item.getItem()).setQuantity(currentQuantity - item.getQuantity());

            String cardValue= item.getCardNumber();
            if(cardValue!=null)
                db.getCards().add(cardValue);
            StringBuilder line=new StringBuilder();
            line.append(item.getItem()).append(",")
                    .append(item.getQuantity()).append(",").append(item.getPrice())
                    .append("\n");
            content.add(line.toString());
        }
        content.add("Total Amount \n");
        content.add(String.valueOf(total));
        System.out.println("Current cards in db"+ db.getCards());
        FileWriterFactory fileFactory = new FileWriterFactory();
        try {
            fileFactory.getWriterInstance("OUTPUT").writeToFile(content);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
