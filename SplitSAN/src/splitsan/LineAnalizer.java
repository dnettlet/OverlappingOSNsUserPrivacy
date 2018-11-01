/*
 * SplitSAN and LineAnalizer Copyright (C) 2017-2018 Vladimir Estivill-Castro 
 * This program comes with ABSOLUTELY NO WARRANTY 
 * GNU GENERAL PUBLIC LICENSE V 3 
 *
 */
package splitsan;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vlad
 */
class LineAnalizer {
    
    public static List<Integer> getAttributes(String line)
    {
        List<Integer> theNeighbours = new ArrayList<>();
        List<Integer> theAttributes = new ArrayList<>();
        
        int NumSoc_Neighbors=0;
        int nextBlank= line.indexOf(' ');
                    
       String rest=line.substring(nextBlank+1,line.length());
       nextBlank= rest.indexOf(' ');
       String stringNumSoc_Neighbors = rest.substring(0,nextBlank);
       NumSoc_Neighbors = Integer.parseInt(stringNumSoc_Neighbors);
       // skip num of attributes
       rest=rest.substring(nextBlank+1,rest.length());
        nextBlank= rest.indexOf(' ');
        String stringNumAattri_Neighbors = rest.substring(0,nextBlank);
        int NumAattri_Neighbors = Integer.parseInt(stringNumAattri_Neighbors);
        
        for (int i=0; i < NumSoc_Neighbors; i++)
                    {
                        rest=rest.substring(nextBlank+1,rest.length());
                        nextBlank= rest.indexOf(' ');
                        // if no attribute neighbours there is no blank ending
                        String stringA_SocialNeighbour;
                        if (nextBlank>0)
                        { stringA_SocialNeighbour = rest.substring(0,nextBlank);
                        } else
                        {
                         stringA_SocialNeighbour = rest;
                        }
                        int a_SocialNeighbour = Integer.parseInt(stringA_SocialNeighbour);
                        theNeighbours.add(a_SocialNeighbour);
                        //System.err.print(" "+a_SocialNeighbour);
                    }
        
       for (int i=0; i < NumAattri_Neighbors; i++)
                    {
                        rest=rest.substring(nextBlank+1,rest.length());
                        nextBlank= rest.indexOf(' ');
                        String stringA_AttributeNeighbour;
                        if (nextBlank>0)
                        {
                             stringA_AttributeNeighbour = rest.substring(0,nextBlank);
                        } else
                        { stringA_AttributeNeighbour = rest;
                        }
                            // attribute ID as shown as negative integers
                          int a_AttributeNeighbour = (-1)*Integer.parseInt(stringA_AttributeNeighbour);
                          theAttributes.add(a_AttributeNeighbour); 
                        //System.err.print(" "+a_AttributeNeighbour);
                    }
        
        
        return theAttributes;
    }
    
    public static int getNumAttributes(String line)
    {
        int theNumber=0;
        int nextBlank= line.indexOf(' ');
       String rest=line.substring(nextBlank+1,line.length());
       nextBlank= rest.indexOf(' ');
        rest=rest.substring(nextBlank+1,rest.length());
        nextBlank= rest.indexOf(' ');
        String stringNumAattri_Neighbors = rest.substring(0,nextBlank);
        theNumber = Integer.parseInt(stringNumAattri_Neighbors);
        return theNumber;
    }
    
    public static int getNodeId(String line) {
        int theNumber = 0;
        int nextBlank = line.indexOf(' ');
        String stringID = line.substring(0, nextBlank);
        theNumber = Integer.parseInt(stringID);
        return theNumber;
    }
    
    public static int getNumNeighbour(String line) {
        int theNumber = 0;
        int nextBlank = line.indexOf(' ');

        String rest = line.substring(nextBlank + 1, line.length());
        nextBlank = rest.indexOf(' ');
        String stringNumSoc_Neighbors = rest.substring(0, nextBlank);
        theNumber = Integer.parseInt(stringNumSoc_Neighbors);

        return theNumber;
    }

    public static List<Integer> getNeighbours(String line)
    {
        List<Integer> theNeighbours = new ArrayList<>();
        
        int NumSoc_Neighbors=0;
        int nextBlank= line.indexOf(' ');
                    
       String rest=line.substring(nextBlank+1,line.length());
       nextBlank= rest.indexOf(' ');
       String stringNumSoc_Neighbors = rest.substring(0,nextBlank);
       NumSoc_Neighbors = Integer.parseInt(stringNumSoc_Neighbors);
       // skip num of attributes
       rest=rest.substring(nextBlank+1,rest.length());
        nextBlank= rest.indexOf(' ');
        //String stringNumAattri_Neighbors = rest.substring(0,nextBlank);
        
        for (int i=0; i < NumSoc_Neighbors; i++)
                    {
                        rest=rest.substring(nextBlank+1,rest.length());
                        nextBlank= rest.indexOf(' ');
                        // if no attribute neighbours there is no blank ending
                        String stringA_SocialNeighbour;
                        if (nextBlank>0)
                        { stringA_SocialNeighbour = rest.substring(0,nextBlank);
                        } else
                        {
                         stringA_SocialNeighbour = rest;
                        }
                        int a_SocialNeighbour = Integer.parseInt(stringA_SocialNeighbour);
                        theNeighbours.add(a_SocialNeighbour);
                        //System.err.print(" "+a_SocialNeighbour);
                    }
        
        return theNeighbours;
    }
    
}
