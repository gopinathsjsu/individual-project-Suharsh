package edu.sjsu.cmpe202.individual.assignment.logic;

import java.io.FileNotFoundException;
import java.util.List;

public interface FileWriter {
	void writeToFile(List<String> lines) throws FileNotFoundException;
}
