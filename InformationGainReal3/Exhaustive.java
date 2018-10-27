// Copyright (C) 2018  Vladimir Estivill-Castro
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

//import createAttributes.createAttributes;
import createAttributes.createAttributesGEX;

import weka.attributeSelection.InfoGainAttributeEval;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author vlad
 */
public class Exhaustive {
	
    final static String [] ATTRIBUTE_VALUES ={"No religious affiliation", "Christian", "Muslim", "Buddhist", "Hindu", "Sikh", "Other Religins",
    	                     "Center", "Left", "Far Right", "Center Left", "Right", "Center Right", "Far Left", "NO", "YES"};
    
    final static String DIFFERENT_VALUES ="DIFFERENT";
    
    /*
     * THE EXHAUSTIVE METHODS SEARCH
    */
    
    public static boolean Exhaustive(Hashtable Users, Instance theUser, Instances theData, AttributeInformationPair [] theExhaustiveSet) throws Exception
    {
       double largestInfoGainFound=0.0;
      int indexOne=3; 
      int indexTwo=6;
      int indexThree=7;
      
     //for (int indexOne=0; indexOne <  theData.numAttributes() - 1; indexOne++)
     //{  for (int indexTwo=indexOne+1; indexTwo <  theData.numAttributes() - 1; indexTwo++)
         //{   for (int indexThree=indexTwo+1; indexThree <  theData.numAttributes() - 1; indexThree++)

      
              { // Need to create a new data set with the three attributes collapsed.
                  Set<Integer> theOtherAttributes = new TreeSet<Integer>();
                  for (int theOtherIndex=0; theOtherIndex <  theData.numAttributes() - 1; theOtherIndex++)
                   {  if ((indexOne!=theOtherIndex) && (indexTwo!=theOtherIndex )&& (indexThree!=theOtherIndex))
                          theOtherAttributes.add(theOtherIndex);
                  }
                  String combinedATrributes="att1p"+indexOne+"att2p"+indexTwo+"att3p"+indexThree+"";
                  //String combinedATrributes="att1p["+indexOne+"]att2p["+indexTwo+"]att3p["+indexThree+"]";
                  //if ((6==indexOne)&&(9==indexTwo)&&(15==indexThree))
                  {   // CONSTRUCT A COLAPSED DATA FOR THIS SET OF ATTRIBUTES
   
                      String userValuesAsString=theUser.stringValue(indexOne)+theUser.stringValue(indexTwo)+theUser.stringValue(indexThree);
                      boolean determinedDifferent=false;
                      
                      Instances collapsedData = createAttributesGEX.createAttributesGEX(Users, theUser, theData, 
                              indexOne, indexTwo, indexThree, userValuesAsString, combinedATrributes);
                      
                      //headerForTempARFF(combinedATrributes, theOtherAttributes, ""+(theData.numAttributes() - 1), writer, theUser, userValuesAsString);
                      
                      double[] vals;
                      
                      // for all data instances
                      String valss="";
                      
                      for (int i=0; i < theData.numInstances(); i++)
                          {   String combinedValue="";
                              String outputRow="";
                              Instance oneInstace =theData.instance(i);
                              determinedDifferent=false;
                              
                              System.out.println("Instance: "+i);
                              
                              vals = new double[collapsedData.numAttributes()]; // to create the attribute values
                              
                              for (int indexOnAllTrributes=0, indexOnCollapsedAtts=0 ; indexOnAllTrributes< theData.numAttributes()-1; indexOnAllTrributes++ )
                              {
                                  System.out.println("indexOnAllTrributes: "+indexOnAllTrributes);
                                  if ((indexOne==indexOnAllTrributes) || (indexTwo==indexOnAllTrributes )|| (indexThree==indexOnAllTrributes))
                                  {
                                      if(indexOnAllTrributes == indexOne)
                                      {
                                          if (!oneInstace.stringValue(indexOne).equals( theUser.stringValue(indexOne) ))
                                              determinedDifferent=true;
                                      } else  if(indexOnAllTrributes == indexTwo)
                                      {
                                          if (!oneInstace.stringValue(indexTwo).equals( theUser.stringValue(indexTwo) ))
                                              determinedDifferent=true; 
                                      } else  if(indexOnAllTrributes == indexThree)
                                      {
                                          if (!oneInstace.stringValue(indexThree).equals( theUser.stringValue(indexThree) ))
                                              determinedDifferent=true; 
                                      } 
                                  }
                                  else
                                  {  
                                	  // this concatenates the first n-2 attribute values
                                	  
                                      //outputRow+=oneInstace.stringValue(indexOnAllTrributes)+",";
                                	  
                                	  //System.out.println("indexOnAllTrributes: "+indexOnAllTrributes);
                                	  
                                	  System.out.println("INDEX collapsed data: "+indexOnCollapsedAtts);
                                	  
                                	  
                                	  //if ( indexOnAllTrributes <= theData.numAttributes())
                                	  //if (oneInstace.stringValue(indexOnAllTrributes) != null)
                                	  valss = oneInstace.stringValue(indexOnAllTrributes);
                                      //valss = "YES";
                                      //Attribute anAttribute = theData.attribute(i);
                                      Attribute anAttribute = theData.attribute(indexOnAllTrributes);
                                	  
                                	  vals[indexOnCollapsedAtts] = anAttribute.indexOfValue(valss);
                                	  ++indexOnCollapsedAtts;
                                  } // its not collapsed or the class value
                                  
                                  
                              } // efor indexOnAllTrributes
                              
                              
                              
                              if (determinedDifferent) combinedValue=DIFFERENT_VALUES;
                                      else combinedValue=userValuesAsString;
                              
                              
                              // this concatenates the collapsed attribute and the class value
                              
                              //outputRow+=combinedValue+","+oneInstace.stringValue(theData.classIndex())+"\n";
                              
                        	  // collapsed attribute
                              
                              Attribute anAttribute = collapsedData.attribute(collapsedData.classIndex()-1);
                        	  
                        	  vals[collapsedData.classIndex()-2] = anAttribute.indexOfValue(combinedValue);
                        	  
                        	  //class value
                        	  valss = oneInstace.stringValue(theData.classIndex()-1); //BUT WHERE DOES THIS INSTANCE COME FROM ?
                              
                              Attribute anAttributec = theData.attribute(theData.classIndex());
                        	  
                        	  vals[collapsedData.classIndex()-1] = anAttributec.indexOfValue(valss);
                        	  
                        	  collapsedData.add(new Instance(1.0, vals)); // THIS CREATES A NEW COLLAPSED INSTANCE
                              
                              // and this writes the complete row into the file
                              
                              //writer.write(outputRow, 0, outputRow.length());

                          } // efor all data instances
                      
                      
                      // Read the collapsed instances
                      //BufferedReader collapsedReader = new BufferedReader(
                            //new FileReader("data/"+collapsedFilename));
                      
                      // ******Here we need to create and use an internal structure instead of reading the file******
                      
                      //Instances collapsedData = new Instances(collapsedReader);

                      //collapsedReader.close();
                      //System.out.println("\nCollapsed Dataset:\n"); System.out.println(collapsedData);
                      
                      collapsedData.setClassIndex(collapsedData.numAttributes() - 1);
                      InfoGainAttributeEval infoGainOfAttribute = new InfoGainAttributeEval();
                      
                      infoGainOfAttribute.buildEvaluator(collapsedData);
                      double infoGainOfCollapsedAttribute= infoGainOfAttribute.evaluateAttribute(collapsedData.numAttributes() - 2);
                      //System.out.println("Information Gain of "+combinedATrributes+" = "+infoGainOfCollapsedAttribute+"\n");
                      if (infoGainOfCollapsedAttribute> largestInfoGainFound)
                      {
                          largestInfoGainFound=infoGainOfCollapsedAttribute;
                          AttributeInformationPair forindexOne = new  AttributeInformationPair(indexOne,0.0);
                          AttributeInformationPair forindexTwo = new  AttributeInformationPair(indexTwo,0.0);
                          AttributeInformationPair forindexThree = new  AttributeInformationPair(indexThree,0.0);
                          theExhaustiveSet[0]=forindexOne;
                          theExhaustiveSet[1]=forindexTwo;
                          theExhaustiveSet[2]=forindexThree;
                      }

              }

      
          //}// indexThree
      //}// indexwo
  }// indexOne
  
  System.out.println("Exhaustive 3-set has info gain:"+largestInfoGainFound+"\n");
  return true;
    }

}
