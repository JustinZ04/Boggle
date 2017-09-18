/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Justin
 */

public class Board implements IBoard
{ 
    private ArrayList<String> boggleData;
    private ArrayList<String> dictData;
    private ArrayList<Die> diceData;
    private ArrayList<String> gameData;
    
    // Constructor for ArrayLists
    public Board(ArrayList<String> boggle, ArrayList<String> dictionary)
    {
        boggleData = boggle;
        dictData = dictionary;
        diceData = new ArrayList<Die>();
        gameData = new ArrayList<String>();
    }

    // Randomly selects a letter from each die and places
    // it in an ArrayList
    @Override
    public ArrayList shakeDice()
    {
        Random thisDie = new Random();
        int used[] = new int[NUMBER_OF_DICE], random, count = 0;
        
        // Reinstantiating ArrayList
        gameData = new ArrayList<>();
        
        // Using frequency array to keep track of which die was used
        for(int i = 0; i < NUMBER_OF_DICE; i++)
            used[i] = 0;
       
        // Picking a die and making sure it is only used once
        while(count < NUMBER_OF_DICE)
        {
            random = thisDie.nextInt(NUMBER_OF_DICE);
            
            if(used[random] != 1)
                count++;
            
            else
                continue;
            
            used[random] = 1;
            
            // Adding the letter to the ArrayList
            gameData.add(diceData.get(random).rollDie());
        }        
        return gameData;
    }

    // Displays the board being used for the current game
    public void displayGameData()
    {
        int i = 0;
        
        System.out.println("Boggle Board");
                
        // Takes a letter from each die and places it on the board
        for(String l : getGameData())
        {
            if((i+1) % 4 == 0)
                System.out.println(l);
            
            else
                System.out.print(l + " ");
            
            i++;
        }
    }
    
    // Puts a letter on each side of the die
    @Override
    public void populateDice()
    {
        Die die;
        int count = 0, i, j;
        
        // Creates each of the 16 die
        for(i = 0; i < NUMBER_OF_DICE; i++)
        {
            die = new Die();
            
            // Puts a letter on each of the six sides
            for(j = 0; j < Die.NUMBER_OF_SIDES; j++)
            {
                die.addLetter(getBoggleData().get(count));
                count++;
            }
            
            // Outputs the letters on each side of the die
            System.out.print("Die " + i + ": ");
            die.displayLetters();
            System.out.println();
            getDiceData().add(die);
        }
    }

    /**
     * @return the boggleData
     */
    public ArrayList<String> getBoggleData()
    {
        return boggleData;
    }

    /**
     * @param boggleData the boggleData to set
     */
    public void setBoggleData(ArrayList<String> boggleData)
    {
        this.boggleData = boggleData;
    }

    /**
     * @return the dictData
     */
    public ArrayList<String> getDictData()
    {
        return dictData;
    }

    /**
     * @param dictData the dictData to set
     */
    public void setDictData(ArrayList<String> dictData)
    {
        this.dictData = dictData;
    }

    /**
     * @return the diceData
     */
    public ArrayList<Die> getDiceData()
    {
        return diceData;
    }

    /**
     * @param diceData the diceData to set
     */
    public void setDiceData(ArrayList<Die> diceData)
    {
        this.diceData = diceData;
    }   

    /**
     * @return the gameData
     */
    public ArrayList<String> getGameData()
    {
        return gameData;
    }
}
