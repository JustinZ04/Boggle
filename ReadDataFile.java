/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inputOutput;

import java.io.IOException;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Justin
 */
public class ReadDataFile implements IReadDataFile
{
    private Scanner inputFile;
    private String dataFile;
    private ArrayList<String> data;
    
    public ReadDataFile(String fileName)
    {
        dataFile = fileName;
        data = new ArrayList<String>();
    }
    
    @Override
    public void populateData()
    {
        try
        {
            // Getting address of the input file
            URL url = getClass().getResource(dataFile);
            File file = new File(url.toURI());
            
            inputFile = new Scanner(file);
            
            // Reading each string from the file
            while(inputFile.hasNext())
                getData().add(inputFile.next());            
        }
        
        catch(IOException | URISyntaxException ex)
        {
            System.out.println(ex.toString());
            ex.printStackTrace();
        }
        
        // Closes the input file
        finally
        {
            if(inputFile != null)
                inputFile.close();
        }
    }

    /**
     * @return the data
     */
    public ArrayList<String> getData()
    {
        return data;
    }
    
}
