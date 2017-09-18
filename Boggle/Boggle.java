/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package boggle;

import core.Board;
import inputOutput.ReadDataFile;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import userInterface.BoggleUI;

/**
 *
 * @author Justin
 */
public class Boggle
{ 
    /**
     * @param args the command line arguments
     */
    
    // Used to store the data from the input files
    private static ArrayList<String> diceData = new ArrayList();
    private static ArrayList<String> dictionaryData = new ArrayList();
    
    // From input file BoggleData.txt
    private static String boggleData = new String("../data/BoggleData.txt");
    
    // From input file Dictionary.txt
    private static String dictData = new String("../data/Dictionary.txt");
    
    public static void main(String[] args)
    {
        // Prints to the IDE console.
        System.out.println("Welcome to Boggle!");
        
        // Generates a popup on the screen.
        JOptionPane.showMessageDialog(null, "Let's Play Boggle!");
        
        // Reads from input file and saves to ArrayList
        ReadDataFile data = new ReadDataFile(boggleData);
        data.populateData();
    
        // Reads from input file and saves to ArrayList
        ReadDataFile dictionary = new ReadDataFile(dictData);
        dictionary.populateData();
        
        // Makes board from Boggle.txt and Dictionary.txt
        Board board = new Board(data.getData(), dictionary.getData());
        board.populateDice();
        
        // Prints how many entries are in the dictionary
        System.out.println("There are " + dictionary.getData().size() + " entries in the dictionary.");
        
        diceData = board.shakeDice();
        
        board.displayGameData();
        
        // Calls the method to create the GUI
        BoggleUI thisUI = new BoggleUI(board);
    }  
}
