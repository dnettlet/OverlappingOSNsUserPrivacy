/*
 * IdentifyCrossSAN Copyright (C) 2017-2018 Vladimir Estivill-Castro 
 * This program comes with ABSOLUTELY NO WARRANTY 
 * GNU GENERAL PUBLIC LICENSE V 3 
 *
 */
package identifycrosssan;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.TreeSet;
import splitsan.LineInfo;
import splitsan.SANreader;

/**
 *
 * @author vlad
 */
public class IdentifyCrossSAN {
    private static final String SAN_FILENAME = "SEP4";
    private static final String SAN_ONE_FILENAME = "SEP4_674_520SANone";
    private static final String SAN_TWO_FILENAME = "SEP4_674_520SANtwo";
    private static final String directory_data = "data";


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("IdentifyCrossSAN Copyright (C) 2017-2018 Vladimir Estivill-Castro ");
        System.out.println("This program comes with ABSOLUTELY NO WARRANTY ");
        System.out.println("GNU GENERAL PUBLIC LICENSE V 3 ");
        System.out.println(" ");
        
        boolean debug = false;
        boolean debugValues = false;
        
        String the_SAN_ONE_FILENAME;
        String the_SAN_TWO_FILENAME;
        
        if (args.length<1 || null==args[1])
        {   the_SAN_ONE_FILENAME=SAN_ONE_FILENAME;
            the_SAN_TWO_FILENAME=SAN_TWO_FILENAME;
        }
        else {
         the_SAN_ONE_FILENAME=args[0];
         the_SAN_TWO_FILENAME=args[1];
        }
      
        int overlaps =0;
        
        SANreader theSANREADERone = new SANreader(the_SAN_ONE_FILENAME,"US-ASCII");
               
        System.out.println("The number of social neighbours IDs is: " + theSANREADERone.treeSocial_nodes.size());
        System.out.println("The number of attribute IDs is: " + theSANREADERone.treeAttribute_nodes.size());

        // Lets read SANtwo until the ID of the user is the number of NODES
       SANreader theSANREADERtwo = new SANreader(the_SAN_TWO_FILENAME,"US-ASCII");
               
        System.out.println("The number of social neighbours IDs is: " + theSANREADERtwo.treeSocial_nodes.size());
        System.out.println("The number of attribute IDs is: " + theSANREADERtwo.treeAttribute_nodes.size());

        // identify those nodes with unkown identity
       TreeSet<Integer> hiddenIdentitySocialNodes = new TreeSet<Integer>();

       try { theSANREADERtwo.resetFile();
              int indexSharedUsers=0;
              // scann the file until ID is last id
              // record those skipped as set of missing identity
              int pastID=0;
              LineInfo currentLine;
              do { theSANREADERtwo.analizeNexTLine() ; 
                  currentLine = theSANREADERtwo.currentLineInformation();
                  if(currentLine.socialUsedID-1 != pastID && pastID!= 0 ) {
                      for (int index=pastID+1; index<=currentLine.socialUsedID-1; index++){
                           hiddenIdentitySocialNodes.add(index);
                      }
                  }
                  pastID = currentLine.socialUsedID;
                  //System.out.println("The ID is :"+ currentLine.socialUsedID +"\n");       
              } while (currentLine.socialUsedID <theSANREADERtwo.treeSocial_nodes.size()-1 );
          }catch (IOException x) {
                     System.err.format("IOException: %s%n", x);
        }
       
        Path outputPath = FileSystems.getDefault().getPath(directory_data, SAN_FILENAME +"_"+"IDENTIFIED.txt");
        try (BufferedWriter writer = Files.newBufferedWriter(outputPath,Charset.forName("US-ASCII"))) {
             for (int value:hiddenIdentitySocialNodes ) {
                 String s = ""+value+"\n";
                 writer.write(s, 0, s.length());
                 
             }
              writer.flush();
        }catch (IOException x) {
                     System.err.format("IOException: %s%n", x);
        }      
       
        // build the match
        Hashtable<Integer, MatchScorePair> hashIdentityMap = new Hashtable<Integer, MatchScorePair>();

        
        try { theSANREADERtwo.resetFile();
// examine unmached individual
            while (theSANREADERtwo.analizeNexTLine() )  {
              LineInfo currentLine = theSANREADERtwo.currentLineInformation();
              // check if it is one that needs to be identified
              if (hiddenIdentitySocialNodes.contains(
                        currentLine.socialUsedID))
              {   // build the sets of this un- identified node
                  TreeSet<Integer> fromSocial_nodes = new TreeSet<Integer>();
                  TreeSet<Integer> fromAttribute_nodes = new TreeSet<Integer>();;
                  for (int value : currentLine.socialLinks) {
                      fromSocial_nodes.add(value);
                  }
                  for (int value : currentLine.attributesLinks) {
                      fromAttribute_nodes.add(value);
                  }
                 // scann the other network to find most similar
                 // make a set of friends and attributes
                  int debugFrom = currentLine.socialUsedID;
                  if (( 4670==debugFrom)  && debug) {
                      System.err.println(currentLine.lineInfoAsString() + "\n");
                      for (int value : fromSocial_nodes)
                         System.err.print(value + " ");
                      System.err.println("\n");
                      for (int value : fromAttribute_nodes)
                         System.err.print(value + " ");
                      System.err.println("\n");
                  }
                  theSANREADERone.resetFile();
                  int intersectionScore = 0;
                  int myMatch = 0;
                  LineInfo theOtherCurrentLine;
                  while (theSANREADERone.analizeNexTLine()) {
                       // only those with unkown identity
                       theOtherCurrentLine = theSANREADERone.currentLineInformation();
                      if (hiddenIdentitySocialNodes.contains(
                              theOtherCurrentLine.socialUsedID)) {
                          // build sets for the other signature
                          TreeSet<Integer> intoSocial_nodes = new TreeSet<Integer>();
                          TreeSet<Integer> intoAttribute_nodes = new TreeSet<Integer>();;
                          for (int value : theOtherCurrentLine.socialLinks) {
                              intoSocial_nodes.add(value);
                          }
                          for (int value : theOtherCurrentLine.attributesLinks) {
                              intoAttribute_nodes.add(value);
                          }
                          int otherSocialID = theOtherCurrentLine.socialUsedID;
                      if (( 4670==debugFrom  )  && debug && (1315==otherSocialID ||3298==otherSocialID )) {
                      System.err.println(currentLine.lineInfoAsString() + "\n");
                      System.err.println(theOtherCurrentLine.lineInfoAsString() + "\n");

                      for (int value : intoSocial_nodes)
                         System.err.print(value + " ");
                      System.err.println("\n");
                      for (int value : intoAttribute_nodes)
                         System.err.print(value + " ");
                      System.err.println("\n");
                  }
                          // compute the intersection of both sets
                          int intersectionSize = 0;
                          for (int value : intoSocial_nodes) {
                              if (fromSocial_nodes.contains(value)) {
                                  intersectionSize++;
                              }
                          }
                          for (int value : intoAttribute_nodes) {
                              if (fromAttribute_nodes.contains(value)) {
                                  intersectionSize++;
                              }
                          }
                          

                          
                          if (intersectionSize > intersectionScore) {
                              // update new maximum found
                              intersectionScore = intersectionSize;
                              myMatch = theOtherCurrentLine.socialUsedID;
                          } // updated because of better match   
                      }// the ID in second network is also unidentified
                  } // while scanning the second network
                  // output the best match
                  //System.out.println(currentLine.lineInfoAsString() + "\n");
                  //System.out.println("IDENTITY:" + currentLine.socialUsedID + "," + myMatch + "\n");
                  // I should insert an idenity only if it is not
                  // already in OR if the score surpsses what is in.
                  MatchScorePair theMatchPair = new MatchScorePair(currentLine.socialUsedID,intersectionScore);
                  if (hashIdentityMap.containsKey(myMatch))
                  {
                      overlaps++;
                      MatchScorePair alreadyIn = hashIdentityMap.get(myMatch);
                      if (alreadyIn.getTheScore() < theMatchPair.getTheScore()) {
                          hashIdentityMap.put(myMatch,theMatchPair);
                      }
                  } else {
                       hashIdentityMap.put(myMatch,theMatchPair);
                  }
                  if (623==myMatch && debug) {
                      System.err.println(currentLine.lineInfoAsString() + "\n");

                  }
              } // if the currentLine.socialUsedID is an unidentified
            } // while scanning the first network 
          }catch (IOException x) {
                     System.err.format("IOException: %s%n", x);
        }
        // OUTPUT to file found identity
        Path outputIdentityPath = FileSystems.getDefault().getPath(directory_data, SAN_FILENAME +"_"+"FOUND.identity");
        try (BufferedWriter writer = Files.newBufferedWriter(outputIdentityPath,Charset.forName("US-ASCII"))) {
             for (int value : hiddenIdentitySocialNodes) {
                 MatchScorePair partnerFound = hashIdentityMap.get(value);
                 if (623==value && debug) {
                      System.err.println(value+","+partnerFound.getTheID() + "\n");

                 }
                 String s = ""+value+",";
                 if (null!=partnerFound ) {
                        s += partnerFound.getTheID() +"\n";
                 } else {
                     s+="null\n";
                 }
                     
                 writer.write(s, 0, s.length());
                 
             }
             writer.flush();
        }catch (IOException x) {
                     System.err.format("IOException: %s%n", x);
        }

        
    }//main
    
}//class
