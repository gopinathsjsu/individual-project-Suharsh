package edu.sjsu.cmpe202.individual.assignment.logic;

import edu.sjsu.cmpe202.individual.assignment.model.InputItems;
import edu.sjsu.cmpe202.individual.assignment.model.Items;

import java.util.List;

public interface OrderHandler {

    void setNextHandler(OrderHandler orderHandler);
    void processOrder(List<InputItems> items);
}
