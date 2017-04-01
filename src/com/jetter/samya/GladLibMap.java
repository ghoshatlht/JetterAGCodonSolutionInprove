/**
 * 
 */
package com.jetter.samya;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import edu.duke.FileResource;


/**
 * @author Samya
 *
 */

// Creat the class call GladLibMap//

public class GladLibMap {
	
	
	private static String dataDirectory = "E://Ecllips/JetterAGCodonSolutionInprove/data";
	 
	private HashMap<String, ArrayList<String>> myMap;
	private ArrayList<String> previous;
    private ArrayList<String> previouscategory;
    private Random useRandom;
    
    /*Create the new HashMap in the constructors
     * inside Defult constructor of GladLibMap
     */
	
    public GladLibMap()
    {
    	myMap = new HashMap<String, ArrayList<String>>();
        initializeFromSource(dataDirectory);
        useRandom = new Random();
        
    }

    // Another constructor of GladLibMap which carries String source//
    
    public GladLibMap(String source)
    {
        initializeFromSource(source);
        useRandom = new Random();
    }
    
    /*Modify the method initializeFromSource
     * create an Array of categories and then iterate over this Array
     * For each category, read in the words from the associated file
     * create an ArrayList of the words (using the method readIt ),
     * and put the category and ArrayList into the HashMap.
     */
    
    
    private void initializeFromSource(String source) 
    {
        ArrayList<String> arraylist = new ArrayList<String>();
        String[] category = {"country", "color", "noun", "name", "adjective", "animal", "timeframe", "verb", "fruit"};
        
        for (int i = 0; i < category.length; i++) 
        {
            arraylist = readIt(source+"/"+category[i]+".txt");
            myMap.put(category[i], arraylist);
        }
        
        previouscategory = new ArrayList<String>();
        previous = new ArrayList<String>();
    }
    
    /*randomFrom that passes the appropriate ArrayList from myMap
     * It's also checks the next value inside the souece
     */
    
    private String randomFrom(ArrayList<String> source)
    {
        int id = useRandom.nextInt(source.size());
        return source.get(id);
    }
    
    /*  Modify the method getSubstitute to replace all the if statements that use category labels
     * with one call to randomFrom that passes the appropriate ArrayList from myMap .
     * 
     * 
     */
    
    
    private String getSubstitute(String label) {
        if (myMap.containsKey(label)) 
        {
            if (!previouscategory.contains(label)) previouscategory.add(label);
            return randomFrom(myMap.get(label));
        }
        else if (label.equals("number")) 
        	
        	return " "+useRandom.nextInt(50)+5;
        
        else return "This is not a valid category";
    }
    
    
    
    
    
    /* Write a new method named totalWordsInMap with no parameters
     * This method returns the total number of words in all the ArrayLists in the HashMap
     * After printing the GladLib, call this method and print out the total number of words that were possible to pick from.
     */
    
    
    private int totalWordsInMap()
    {
        int sum = 0;
        for (String word: myMap.keySet()) 
        {
            sum += myMap.get(word).size();
        }
        return sum;
    }
    
    
    
    
    
    /* Write a new method named totalWordsConsidered with no parameters
     * This method returns the total number of words in the ArrayLists of the categories that were used for a particular GladLib
     */
    
    
    
    
    private int totalWordsConsidered() {
        int sum = 0;
        for (int i = 0; i < previouscategory.size(); i++) 
        {
            sum += myMap.get(previouscategory.get(i)).size();
        }
        return sum;
    }
    
    
    
    // coping medthos from previous Glaslib class 
    
    
    private String processWord(String w){
        int first = w.indexOf("<");
        int last = w.indexOf(">",first);
        if (first == -1 || last == -1){
            return w;
        }
        String prefix = w.substring(0,first);
        String suffix = w.substring(last+1);
        String sub = getSubstitute(w.substring(first+1,last));
        int index = previous.indexOf(sub);
        int usedornot = 1;
        while (usedornot == 1) {
            if (index == -1) {
            	previous.add(sub);
                usedornot = 0;
                
            }
            else {
                sub = getSubstitute(w.substring(first+1,last));
                index = previous.indexOf(sub);
            }
        }
        return prefix+sub+suffix;
    }
    
    private void printOut(String s, int lineWidth){
        int charsWritten = 0;
        for(String w : s.split("\\s+")){
            if (charsWritten + w.length() > lineWidth){
                System.out.println();
                charsWritten = 0;
            }
            System.out.print(w+" ");
            charsWritten += w.length() + 1;
        }
    }
    
    private String fromTemplate(String source){
        String story = "";
        
           FileResource resource = new FileResource(source);
            for(String word : resource.words()){
                story = story + processWord(word) + " ";
            }
       
        return story;
    }
    
    // Using Method readIt
    
    private ArrayList<String> readIt(String source){
        ArrayList<String> list = new ArrayList<String>();
        
           FileResource resource = new FileResource(source);
            for(String line : resource.lines()){
                list.add(line);
            }
        
        return list;
    }
    
    // Testing the makeStory method from old Gladlib 
    
    public void makeStory(){
    	
    	System.out.println("    ****** Now The Real Story begins *****           ");
    	
        System.out.println("\n");
        String story = fromTemplate("data/madtemplate.txt");
        printOut(story, 100);
        int number = totalWordsInMap();
        System.out.println("\t");
        System.out.println("Here are "+number+" words to pick from."+"\t");
        number = totalWordsConsidered();
        System.out.println("Here are "+number+" words considered.");
    }
    
}
