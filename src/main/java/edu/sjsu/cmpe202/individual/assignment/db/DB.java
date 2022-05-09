package edu.sjsu.cmpe202.individual.assignment.db;

import edu.sjsu.cmpe202.individual.assignment.model.Items;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DB {
    private static DB instance = new DB();
    private Map<String, Items> items;
    private Set<String> cards;

    private DB(){
      items = new HashMap<>();
      cards = new HashSet<>();
    }

    public void loadDB() throws IOException {
        List<String> itemsList = loadFilesFromResources("inventory.csv");
        List<String> cardsList = loadFilesFromResources("cardNumbers.csv");
        itemsList.stream()
                .map(item -> item.split(","))
                .forEach(data->items.put(data[0].toUpperCase(),
                        new Items(data[1].toUpperCase(),data[0].toUpperCase(),
                                Integer.parseInt(data[2]),
                                Double.parseDouble(data[3]))
                        ));
        cardsList.stream().map(card -> card.split(",")).forEach(
                data -> cards.add(data[0].trim())
        );
    }

    public List<String> loadFilesFromResources(String fileName) throws IOException {
        List<String> contents = new ArrayList<>();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        try(BufferedReader br= new BufferedReader(new InputStreamReader(inputStream))){
            String line=br.readLine() ;
            while(line!= null) {
                contents.add(line);
                line=br.readLine();
            }
        }
        contents.remove(0);
        return contents;
    }

    public static DB getInstance() {
        return instance; //initialized when class is loaded, provides singleton instance
    }

    public static void setInstance(DB instance) {
        DB.instance = instance;
    }

    public Map<String, Items> getItems() {
        return items;
    }

    public void setItems(Map<String, Items> items) {
        this.items = items;
    }

    public Set<String> getCards() {
        return cards;
    }

    public void setCards(Set<String> cards) {
        this.cards = cards;
    }
}
