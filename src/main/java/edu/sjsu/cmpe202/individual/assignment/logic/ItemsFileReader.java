package edu.sjsu.cmpe202.individual.assignment.logic;

import edu.sjsu.cmpe202.individual.assignment.db.DB;
import edu.sjsu.cmpe202.individual.assignment.model.InputItems;
import edu.sjsu.cmpe202.individual.assignment.model.Items;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemsFileReader {

    public List<InputItems> getItems(String filePath) throws IOException{
        List<InputItems> result=new ArrayList<>();
        List<String>content = readFile(filePath);
        DB db = DB.getInstance();
        for(int i=1;i<content.size();i++) {
            String[] items=content.get(i).split(",");
            if(db.getItems().containsKey(items[0].toUpperCase()) ) {
                Items item = db.getItems().get(items[0].toUpperCase());
                String category = item.getCategory();
                String itemName = item.getItem();
                Double price = item.getPrice();
                Double card = 0.0;
                if(items.length==3){
                    card = Double.parseDouble(items[2].trim());
                }
                InputItems Item=new InputItems(category, itemName, Integer.parseInt(items[1]), price,items.length<3?null:String.format("%.0f",card));
                result.add(Item);
            }else {
                InputItems item=new InputItems(null, items[0].toUpperCase(), Integer.parseInt(items[1]), 0, null);
                result.add(item);
            }
        }
        System.out.println(result);
        return result;
    }


    private List<String> readFile(String filePath) throws IOException {
        List<String> content = new ArrayList<>();
        String line;
        try(BufferedReader br= new BufferedReader(new FileReader(filePath))){
            line=br.readLine() ;
            while(line!= null) {
                content.add(line);
                line=br.readLine();
            }
        }
        return content;
    }
}
