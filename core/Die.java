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
public class Die implements IDie
{
    private ArrayList<String> letters = new ArrayList<String>();
    
    @Override
    public String rollDie()
    {
        Random letter = new Random();
        
        return letters.get(letter.nextInt(6));
    }

    // Adds a letter to the ArrayList
    @Override
    public void addLetter(String letter)
    {
        letters.add(letter);
    }

    // Prints the letter
    @Override
    public void displayLetters()
    {
        for(String letter : letters)
            System.out.print(letter + " ");
    }
    
}
