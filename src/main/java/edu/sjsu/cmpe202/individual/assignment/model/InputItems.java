package edu.sjsu.cmpe202.individual.assignment.model;

public class InputItems extends Items{

    private String cardNumber;

    public String getCardNumber() {
        return cardNumber;
    }

    public InputItems(String category, String item, int quantity, double price, String cardNumber) {
        super(category, item, quantity, price);
        this.cardNumber = cardNumber;
    }

    @Override
    public String toString() {
        return "InputItems{" +
                "cardNumber='" + cardNumber + '\'' +
                "} " + super.toString();
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
