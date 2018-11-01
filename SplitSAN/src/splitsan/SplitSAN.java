/*
 * SplitSAN Copyright (C) 2017-2018 Vladimir Estivill-Castro 
 * This program comes with ABSOLUTELY NO WARRANTY 
 * GNU GENERAL PUBLIC LICENSE V 3 
 *
 */
package splitsan;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

import java.time.LocalDateTime;

/**
 *
 * @author vlad
 */
public class SplitSAN {
    
    //private static final String SAN_FILENAME="Davis_Souther_Cards";
    private static final String SAN_FILENAME = "SEP4";
    private static final String directory_data = "data";
    private static Random rn; 
    
    private int myRandomBounded (int n)
    {
        int number = rn.nextInt(n);
        return number;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        System.out.println("SplitSAN Copyright (C) 2017-2018 Vladimir Estivill-Castro ");
        System.out.println("This program comes with ABSOLUTELY NO WARRANTY ");
        System.out.println("GNU GENERAL PUBLIC LICENSE V 3 ");
        System.out.println(" ");
        LocalDateTime today = LocalDateTime.now();
        System.out.println("Current Date="+today);
        int seed=today.getNano();
        rn = new Random(seed);
        //rn = new Random();
       
        
        //boolean debug = false;
        boolean debug = false;
        boolean debugValues = false;

           
        // parse Arguments     
        int proportion_of_unidentified_users;
        int proportion_of_shared_attributes;
        
        proportion_of_unidentified_users = Integer.parseInt(args[0]);
            if ((proportion_of_unidentified_users <0) || (proportion_of_unidentified_users>100)) {
                System.err.println(args[0]+" Unacceptableas a proportion_of_unidentified_users in [0,100] ");
                System.exit(1);
            }
            
       proportion_of_shared_attributes = Integer.parseInt(args[1]);
            if ((proportion_of_shared_attributes <0) || (proportion_of_shared_attributes>100)) {
                System.err.println(args[1]+" Unacceptableas a proportion_of_shared_attributes in [0,100] ");
                System.exit(1);
            }
        
        SANreader theSANREADER = new SANreader(SAN_FILENAME,"US-ASCII");
        
        System.out.println("The number of social neighbours IDs is: " + theSANREADER.treeSocial_nodes.size());
        System.out.println("The number of attribute IDs is: " + theSANREADER.treeAttribute_nodes.size());

        
        Iterator<Integer> iterator = theSANREADER.treeSocial_nodes.iterator();
        int[] theSocialNodesIDsSorted = new int[theSANREADER.treeSocial_nodes.size()];
     
        if (debugValues) { System.err.println("Social neighbours IDs: ");}
        // Displaying the Tree set data
        int i =0;
        int j=0;
	while (iterator.hasNext() ) {
                theSocialNodesIDsSorted[j]=iterator.next();
                if (debugValues) { System.err.print(theSocialNodesIDsSorted[j] + " "); i++;
                             if (i>9 ) {System.err.println(); i=0; 
                               }
                }
                j++;
	}
	System.err.println();
        
        Iterator<Integer> iteratorA = theSANREADER.treeAttribute_nodes.iterator();
        int[] theAttributeNodesIDsSorted = new int[theSANREADER.treeAttribute_nodes.size()];
     
        if (debugValues) { System.err.println("Attributes neighbours IDs: ");}
        // Displaying the Tree set data
        i =0; j=0;
	while (iteratorA.hasNext() ) {
                theAttributeNodesIDsSorted[j]=iteratorA.next();
		 if (debugValues) {  System.err.print(theAttributeNodesIDsSorted[j] + " "); i++;
                         if (i>9) { System.err.println(); i=0;         
                             }
                 }
                 j++;
	}
	System.err.println();
        
        ////*********************************************
        // Create two sets of attributes for sub SAN one and sub-SAN two
        //// ***********************************************
        System.out.println("proportion_of_unidentified_users:"+proportion_of_unidentified_users);

        System.out.println("proportion_of_shared_attributes:"+proportion_of_shared_attributes);
        float fproportion_of_shared_attributes = (float) proportion_of_shared_attributes;
        float factorAttributeSize = ( fproportion_of_shared_attributes + 100)/(2*100);
        float newAttributeSize = theSANREADER.treeAttribute_nodes.size() * factorAttributeSize;
        
        System.out.println("factorAttributeSize:"+factorAttributeSize);

        int numberAttributesSubSANone = Math.round(newAttributeSize);
        int numberAttributesSubSANtwo = numberAttributesSubSANone;
        
        int[] attributesSubSANone = new int[numberAttributesSubSANone];
        int[] attributesSubSANtwo = new int[numberAttributesSubSANtwo];
        
        System.out.println("numberAttributesSubSANone:"+numberAttributesSubSANone);
        
        // draw randomly the shared attributes
        // we chose an attribute in the range [0,top_bound]
        // we place it in both subSANs and we swap it in the array
        // of attributes to the one in position "bound"
        // decrementing the bound
        
        int num_shared_attributes = 0;
        
        while (100*num_shared_attributes/theSANREADER.treeAttribute_nodes.size() < proportion_of_shared_attributes)
        { int chosenIndex = rn.nextInt(theSANREADER.treeAttribute_nodes.size()-num_shared_attributes);
          // attribute in both sub SANs
          //System.out.println("Chosen Index:"+chosenIndex);
          attributesSubSANone[num_shared_attributes]= theAttributeNodesIDsSorted[chosenIndex];
          attributesSubSANtwo[num_shared_attributes]= theAttributeNodesIDsSorted[chosenIndex];
          // swapped so is not chosen aggain
          int t = theAttributeNodesIDsSorted[chosenIndex];
          theAttributeNodesIDsSorted[chosenIndex]=theAttributeNodesIDsSorted[theSANREADER.treeAttribute_nodes.size()-num_shared_attributes-1];
          theAttributeNodesIDsSorted[theSANREADER.treeAttribute_nodes.size()-num_shared_attributes-1]=t;
          // increment 
          num_shared_attributes++;
        }
        
        System.out.println("num_shared_attributes:"+num_shared_attributes);
        int finalValueNum_shared_attributes = num_shared_attributes;
        // the remaining attributes are split randomly on the subnetworks
        
        int nextAttributeToSubSANOne = num_shared_attributes;
        int nextAttributeToSubSANTwo = num_shared_attributes;
        
        // select for Sub SAN one and the rest will go to Sub SAN two
        while (nextAttributeToSubSANOne < numberAttributesSubSANone )
        { int chosenIndex = rn.nextInt(theSANREADER.treeAttribute_nodes.size()-num_shared_attributes);
          //System.out.println("Chosen Index:"+chosenIndex);
          attributesSubSANone[nextAttributeToSubSANOne]= theAttributeNodesIDsSorted[chosenIndex];
          // swapped so is not chosen aggain
          int t = theAttributeNodesIDsSorted[chosenIndex];
          theAttributeNodesIDsSorted[chosenIndex]=theAttributeNodesIDsSorted[theSANREADER.treeAttribute_nodes.size()-num_shared_attributes-1];
          theAttributeNodesIDsSorted[theSANREADER.treeAttribute_nodes.size()-num_shared_attributes-1]=t;
            //increment
            nextAttributeToSubSANOne++;
            num_shared_attributes++; // kept as an idnex of the array f attributes  
        }
        while ((nextAttributeToSubSANTwo < numberAttributesSubSANtwo )
            && (theSANREADER.treeAttribute_nodes.size()-num_shared_attributes-1>=0) )
        {
            attributesSubSANtwo[nextAttributeToSubSANTwo]= theAttributeNodesIDsSorted[theSANREADER.treeAttribute_nodes.size()-num_shared_attributes-1];
            nextAttributeToSubSANTwo++;
            num_shared_attributes++; // kept as an idnex of the array f attributes 
        }
        
        
        //System.err.println("num_shared_attributes:"+num_shared_attributes);
        //System.err.println("nextAttributeToSubSANOne:"+nextAttributeToSubSANOne);
        //System.err.println("nextAttributeToSubSANTwo:"+nextAttributeToSubSANTwo);
           // build sets
        TreeSet<Integer>  setattributesSubSANone = new TreeSet<Integer>();
        for (int value : attributesSubSANone)
             {  setattributesSubSANone.add(value);
                if (!theSANREADER.treeAttribute_nodes.contains(value) ){
                    System.err.println("Invalid attribue in SAN one:"+value);
                    System.err.println("Attributes in SAN one are not subset"); 
                    System.exit(1);
                }
             }
            
        TreeSet<Integer>  setattributesSubSANtwo = new TreeSet<Integer>();
        for (int value : attributesSubSANtwo)
             {  setattributesSubSANtwo.add(value);
                if (!theSANREADER.treeAttribute_nodes.contains(value) ){
                    System.err.println("Invalid attribue in SAN two:"+value);
                    System.err.println("Attributes in SAN two are not subset"); 
                    System.exit(1);
                }
             }
            // check the original attributes are mostly in the union
        for (int value: theSANREADER.treeAttribute_nodes){
                if ( setattributesSubSANone.contains(value)
                        || setattributesSubSANtwo.contains(value)
                        ) {
                }// if
                else
                { System.err.println("Attribute not in the union:"+value);
                }
                
                }// for
            // count the intersection
            int intersecitonSize=0;
            for (int value: theSANREADER.treeAttribute_nodes){
                if ( setattributesSubSANone.contains(value)
                        && setattributesSubSANtwo.contains(value)
                        )
                {
                    intersecitonSize++;
                }
            }// for
            //System.err.println("Size of interseciton of attributes:"+intersecitonSize);
                 
            ////*****************************************************
            // Now we create double identity for a proportion of the Social nodes
            ///**********************************************************
        System.out.println("proportion_of_unidentified_users:" + proportion_of_unidentified_users);

        
        TreeSet<Integer>  userIDsInBothSubSANs = new TreeSet<Integer>();
        
        int num_non_shared_users = 0;
        
        while (100 * num_non_shared_users / theSANREADER.treeSocial_nodes.size() < proportion_of_unidentified_users) {
            int chosenIndex = rn.nextInt(theSANREADER.treeSocial_nodes.size() - num_non_shared_users);
            // users in both sub SANs
            int chosenToBeUnidnetified = theSocialNodesIDsSorted[chosenIndex];
            userIDsInBothSubSANs.add(chosenToBeUnidnetified);
            // swapped so is not chosen aggain
            int t = theSocialNodesIDsSorted[chosenIndex];
            theSocialNodesIDsSorted[chosenIndex] = theSocialNodesIDsSorted[theSANREADER.treeSocial_nodes.size() - num_non_shared_users - 1];
            theSocialNodesIDsSorted[theSANREADER.treeSocial_nodes.size() - num_non_shared_users - 1] = t;
            // increment 
            num_non_shared_users++;
        }

        System.out.println("num_non_shared_users:" + num_non_shared_users);
        // the remaining attributes are split randomly on the subnetworks
        int[][] theIdneityMap = new int[2][num_non_shared_users];
        int index=0;
        for (int value :userIDsInBothSubSANs )
        {   
            theIdneityMap[0][index]= value;
            theIdneityMap[1][index]= value;
            index++;
        }
        
        // these num_non_shared_users have to be randomply suffled
        while (index>=1)
        {  int position = rn.nextInt(index);
            // swap on the other side
            int t = theIdneityMap[1][position];
            theIdneityMap[1][position] = theIdneityMap[1][index-1] ;
            theIdneityMap[1][index-1] = t;
            index--;
        }
        
        Hashtable<Integer, Integer> hashIdentityMap = new Hashtable<Integer, Integer>();

        Path outputPath = FileSystems.getDefault().getPath(directory_data, SAN_FILENAME +"_"+finalValueNum_shared_attributes+ "_"+num_non_shared_users + ".identity");
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath,Charset.forName("US-ASCII"))) {
            for (index=0; index < num_non_shared_users; index++)
            { 
            hashIdentityMap.put(theIdneityMap[0][index], theIdneityMap[1][index]);
            String s = "" +  theIdneityMap[0][index] + ","+ theIdneityMap[1][index] + "\n";
            writer.write(s, 0, s.length());
            // test
            if ( (!userIDsInBothSubSANs.contains( theIdneityMap[0][index]))
                ||
                  ( !userIDsInBothSubSANs.contains( theIdneityMap[0][index])) ) {
                  System.err.println("Id of user not in hidden users");
                  System.exit(1);
               }  //if   
            
            }//for
           writer.flush();

        } catch (IOException x) {
                     System.err.format("IOException: %s%n", x);
        }

        // Rewrite 3 networks, 
        // 1.- the fusion of the subSAN
        // 2,3 .- the two subSANs
        Path outputPathOne = FileSystems.getDefault().getPath(directory_data, SAN_FILENAME +"_"+finalValueNum_shared_attributes+ "_"+num_non_shared_users + "SANone.txt");
        Path outputPathTwo = FileSystems.getDefault().getPath(directory_data, SAN_FILENAME +"_"+finalValueNum_shared_attributes+ "_"+num_non_shared_users + "SANtwo.txt");

        LineInfo[] toShuffleIdentities = new LineInfo[num_non_shared_users];
         
        try { theSANREADER.resetFile();
              BufferedWriter writerOne = Files.newBufferedWriter(outputPathOne, Charset.forName("US-ASCII"));
              BufferedWriter writerTwo = Files.newBufferedWriter(outputPathTwo, Charset.forName("US-ASCII"));
              int indexSharedUsers=0;
              while (theSANREADER.analizeNexTLine())
              {
                 LineInfo currentLine = theSANREADER.currentLineInformation();
                 
                 // build for sub-SAN 1
                 int idUser1 = currentLine.socialUsedID;
                 // idUser does not change for SAN one
                 // and thus not the IDs of neighbours
                 List<Integer>  hasSocialNehighourOne = new ArrayList<>();
                 for (int value : currentLine.socialLinks) {
                     hasSocialNehighourOne.add(value);                
                 }
                 // however the attributes are only ones in sub SAN1
                 List<Integer>  hasAttributeNeighbourOne = new ArrayList<>();
                 for (int value :currentLine.attributesLinks ){
                     if (setattributesSubSANone.contains(value)) {
                         hasAttributeNeighbourOne.add(value);                      
                     }
                 }
                 
                 LineInfo recordSubSANone = new LineInfo(idUser1,
                         hasSocialNehighourOne.size(),
                         hasAttributeNeighbourOne.size(),
                         hasSocialNehighourOne,hasAttributeNeighbourOne);
                 
                 // build for sub-SAN 2
                 // rename user by identity map, if necesary
                 int idUser2 = idUser1;
                 boolean userIdenityShouldBeHidded=false;
                 if (userIDsInBothSubSANs.contains(idUser1)) {
                     // id of social user that has another name
                     userIdenityShouldBeHidded=true;
                     idUser2=hashIdentityMap.get(idUser1); 
                 }
                 
                  // and thus not the IDs of neighbours
                  List<Integer> hasSocialNehighourTwo = new ArrayList<>();
                  for (int value : currentLine.socialLinks) {
                      int value2 = value;
                      if (userIDsInBothSubSANs.contains(value)) {
                     // id of social user that has another name
                            value2=hashIdentityMap.get(value); 
                      }
                      hasSocialNehighourTwo.add(value2);
                  }
                  // the attributes are only for sub SAN 2
                  // however the attributes are only ones in sub SAN1
                  List<Integer> hasAttributeNeighbourTwo = new ArrayList<>();
                  for (int value : currentLine.attributesLinks) {
                      if (setattributesSubSANtwo.contains(value)) {
                          hasAttributeNeighbourTwo.add(value);
                      }
                  }
                  
                LineInfo recordSubSANtwo = new LineInfo(idUser2,
                          hasSocialNehighourTwo.size(),
                          hasAttributeNeighbourTwo.size(),
                          hasSocialNehighourTwo, hasAttributeNeighbourTwo);
                 
                if (debug) {
                    System.err.println("TEST:"+currentLine.lineInfoAsString());
                    System.err.println("T-S1:"+recordSubSANone.lineInfoAsString());
                    System.err.println("T-S2:"+recordSubSANtwo.lineInfoAsString());
                 }
             
                 writerOne.write(recordSubSANone.lineInfoAsString()+"\n", 0, recordSubSANone.lineInfoAsString().length()+1);
                 if (userIdenityShouldBeHidded) {
                     toShuffleIdentities[indexSharedUsers]=recordSubSANtwo;
                     indexSharedUsers++;
                 }else
                 {     writerTwo.write(recordSubSANtwo.lineInfoAsString()+"\n", 0, recordSubSANtwo.lineInfoAsString().length()+1); 
                 }
              }// there are lines

            writerOne.flush();
            // we need to shuffle randomly those not printed
            int indexForShuffling = toShuffleIdentities.length;
            while (indexForShuffling>1)
            {
                int randomPosition = rn.nextInt(indexForShuffling);
                // permute
                LineInfo temporary = toShuffleIdentities[randomPosition];
                toShuffleIdentities[randomPosition]=toShuffleIdentities[indexForShuffling-1];
               toShuffleIdentities[indexForShuffling-1]=temporary;
                // decrement
                indexForShuffling --;
            }
            // output the suffled hidden identity users
            for (int indexS=0; indexS<toShuffleIdentities.length; indexS++){
                writerTwo.write(toShuffleIdentities[indexS].lineInfoAsString()+"\n", 0, toShuffleIdentities[indexS].lineInfoAsString().length()+1); 
            }
            writerTwo.flush();
              
        }catch (IOException x) {
                     System.err.format("IOException: %s%n", x);
        }
        
        

        
        
    }// main
    
}// class
