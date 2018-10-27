// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Properties;
import weka.attributeSelection.AttributeSelection;
import weka.attributeSelection.InfoGainAttributeEval;
import weka.attributeSelection.Ranker;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ProtectedProperties;
import weka.filters.Filter;
import weka.filters.unsupervised.instance.RemoveWithValues;

import dataFile.dataFile;
import User.User;
import readData.readData;
import readData.createFlags;
import readData.createFlagsV3filter;
import readData.loadUsers;
import createAttributes.createAttributesGALL;
import createAttributes.createAttributesSG1;
import createAttributes.createAttributesSG2;


//import createAttributes.createAttributesNRed;

/**
 *
 * @author vlad
 */


public class InformationGainReal3 {
    
    /* Rank the attributes of the given data set by InformaitonGain */
    //final static String [] ATTRIBUTE_VALUES ={"YES","NO","FEMALE","MALE","INNER_CITY","TOWN","RURAL","SUBURBAN"};
    //final static String DIFFERENT_VALUES ="DIFFERENT";
    
    public static Hashtable Users      = new Hashtable();
    public static Hashtable UsersT     = new Hashtable();
    public static Hashtable UsersTF    = new Hashtable();
    public static Hashtable Attributes = new Hashtable();
    public static Hashtable AttrDs     = new Hashtable();
    
    
    static boolean isAttributeInArray(int value ,AttributeInformationPair [] theArray )
    {
        for (int i=0; i<theArray.length; i++ )
        {
            if (value==theArray[i].get_attributeID())
                return true;
        }
        return false;
    }
    // is the instance already in the ser oF Instacnes
    static boolean isInInstacnes(Instance oneInstace, Instances setOfInstances)
    {  boolean temp=false;
    
       for (int j=0; j<setOfInstances.numInstances(); j++)
       {   boolean allEqual=true;
            //System.out.println("Searching :"+oneInstace);
            //System.out.println("Against  :"+ setOfInstances.instance(j));
           //for (int att=0; att< setOfInstances.numAttributes(); att++)
           for (int att=0; (att< oneInstace.numAttributes()) && (att< setOfInstances.numAttributes()); att++) //added this 9/1/2015
           {
               try {
            	   
            	   //System.out.println("oneInstace.stringValue(att) :"+oneInstace.stringValue(att));
            	   //System.out.println("setOfInstances.instance(j).stringValue(att) :"+setOfInstances.instance(j).stringValue(att));
               if ((oneInstace.stringValue(att) != null) && (setOfInstances.instance(j).stringValue(att) != null) )     	   
               if (! oneInstace.stringValue(att).equals( setOfInstances.instance(j).stringValue(att)   )     )
                   allEqual=false;
               } catch (Exception ex) {
                   Logger.getLogger(InformationGainReal3.class.getName()).log(Level.SEVERE, null, ex);
                   return false;
               }
           }
               
           if (allEqual) {
                System.out.println("Found :"+oneInstace);
               return true;
           }
       }
        
        return temp;
    }
    


  
    /* return an emptys et of instacnes with the same attributes as the input instace*/
    static Instances newSetOfInstacnes(Instances theSet)
    {
          FastVector attInfo = new FastVector(theSet.numAttributes());
          
          for (int i=0; i<theSet.numAttributes()  ; i++)
          {
             Attribute anAttribute = theSet.attribute(i);
             attInfo.addElement(anAttribute);
          }
          
          //attInfo.addElement(theSet.classAttribute());
          Instances newInstances = new Instances("theNeWSet",attInfo,theSet.numInstances());
          newInstances.setClassIndex(newInstances.numAttributes() - 1);
          
          return newInstances;
    }
  
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

    /* THE PARAMETER OF THE ALGORIYM*/
    final double Beta=0.01; // so we get at least 3 attributes (was 0.09 / 0.001)
    final int MAXIMUM_RULES_LENGHT=3;
    final int SET_SIZE_OF_PRIVATE_ATTRIBUTES=3;
    
    readData.readTopology(UsersT, Attributes, AttrDs, UsersTF);
  
    loadUsers.loadUsers(UsersT, Users);

    readData.processAttributes(Users, Attributes);
    
    boolean topidf = true;			// can be true or false
    int topid = 0;							// can be 0,1,2,3 or 4
    boolean topattf = false; 		// can be true or false
    int topatt = -99; 							// can be 0 or 1
    
	String nomFixterOut1    = " ";
    for (topid=0; topid<5;topid++)  // do the topids for ALL
    {
    	nomFixterOut1    = "data_all_weka_ALL_topid"+topid+".csv"; 

    	createFlagsV3filter.createFlagsV3filter(Users, Attributes, topid, topatt);
    
    	int ret     = readData.writeInstancesforWeka(Users, UsersTF, nomFixterOut1);
    }
    
    topid = -99;
    
    for (topatt=0; topatt<2;topatt++)  // do the topatts for ALL
    {
    	nomFixterOut1    = "data_all_weka_ALL_topatt"+topatt+".csv"; 

    	createFlagsV3filter.createFlagsV3filter(Users, Attributes,topid, topatt);
    
    	int ret     = readData.writeInstancesforWeka(Users, UsersTF, nomFixterOut1);
    }
    
    
    
    //readData.writeDataUsers(Users);
    
    //readData.writeDataAttributes(Attributes);
    
    //int ret = readData.writeUserGraph(Users);
    //ret     = readData.writeAttributeGraph(Users);
    

    
    if (1==1) return;
    
 
    //readData.writeDataDictionary(AttrDs);
       
    //Instances data = createAttributesSG1.createAttributesSG1(Users);
    Instances data = createAttributesSG2.createAttributesSG2(Users);
    //Instances data = createAttributesGALL.createAttributesGALL(Users);
    
    //if (1==1) return;
    
    int auserID=990;
    
    //int CLASSTOPREDICT = data.numAttributes() - 20;  // GALL -20 TO -16
    //int CLASSTOPREDICT = data.numAttributes() - 16;  // SG1 -19(1), -18(2), -16(4)
    int CLASSTOPREDICT = data.numAttributes() - 17;  // SG2 -20(0), -17(3)
 
    data.setClassIndex(CLASSTOPREDICT); // this is the predictor class label to use
    // -20 gets the first user flag, -16 gets the last user flag, 
 
    System.out.println("ORIGINAL DATA SIZE:"+data.numInstances());
 
    /* GET THE USERS */
 
    Instances theYes92Users = newSetOfInstacnes(data);
 
    for (int i=0; i < data.numInstances(); i++)
    {   String theClassValue="YES";
        Instance oneInstace = data.instance(i);
        if (theClassValue.equals(oneInstace.stringValue(data.classIndex() )       ))
        {
            theYes92Users.add(oneInstace);
        }
    }
 

    // select differetn users
    Instances uniqueUsers = newSetOfInstacnes(data);
    
    for (int i=0; i< theYes92Users.numInstances(); i++ )
    {
        Instance oneInstace =theYes92Users.instance(i);
        //System.out.println("Lookign for :"+oneInstace );
        //System.out.println("Among:"+uniqueUsers );
        if (! isInInstacnes(oneInstace,uniqueUsers))
                    {
            uniqueUsers.add(oneInstace);
          System.out.println("HOLA2***************");
        }
    }

      //System.out.println("SET OF USERS:"+uniqueUsers); this gives error 9 01 2015
    
      System.out.println("SET OF UNIQUE USERS:"); 
     
      
      String filename="ResultsDataSize"+data.numInstances()+"UserSetSize"+theYes92Users.numInstances()+"UniqueSize"+uniqueUsers.numInstances();
      File logFile=new File(filename  );

       BufferedWriter myOutputWriter = new BufferedWriter(new FileWriter(logFile));

    System.out.println("Number of users with YES-attribute:"+theYes92Users.numInstances() + " Number of DIFFERENT users with YES-attribute:"+uniqueUsers.numInstances()  );

    //if (1==1) return;
   // here is where we iterate over the unique users
    
    auserID=0;
    Vector topattsc = new Vector ();
    Vector topfreqc = new Vector ();
    Vector topattsf = new Vector ();
    Vector topfreqf = new Vector ();
    System.out.println("uniqueUsers.numInstances():"+uniqueUsers.numInstances());
    for (auserID=0; auserID<uniqueUsers.numInstances() && auserID < 5; auserID++)
    {
        Instance aUser = uniqueUsers.instance(auserID); // *******this is the user being tested******
        
        
        AttributeInformationPair [] topCummulativeArray = new AttributeInformationPair[5];
        AttributeInformationPair [] theTotalArray = new AttributeInformationPair[5];
        AttributeInformationPair [] theExhaustiveArrayArray = new AttributeInformationPair[SET_SIZE_OF_PRIVATE_ATTRIBUTES];

        Heuristic.Heuristic(aUser, data,  topCummulativeArray, theTotalArray, Beta,  MAXIMUM_RULES_LENGHT);

        if (1==1)
        {
            for (int attIndex=0; attIndex < topCummulativeArray.length; attIndex++ )
                {
                try {

        			int attid = topCummulativeArray[attIndex].get_attributeID();
        			if (topattsc.contains(attid) == false){
        				topattsc.add(attid);
        				topfreqc.add((Integer)(5-attIndex));
        			}
        			else
        			{
        				int ix = topattsc.indexOf(attid);
        				int freq = (Integer)topfreqc.get(ix);
        				freq = freq + (5-attIndex);
        				topfreqc.setElementAt(freq, ix);
        			}
    		
                    System.out.println("TOP CUMULATIVE FOUND ****>  "+topCummulativeArray[attIndex].get_information()+" Attribute"+topCummulativeArray[attIndex].get_attributeID() );
                } catch (Exception ex) {
                    Logger.getLogger(InformationGainReal3.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                
                }
            for (int attIndex=0; attIndex < theTotalArray.length; attIndex++ )
                {
                try {
                	
        			int attid = theTotalArray[attIndex].get_attributeID();
        			if (topattsf.contains(attid) == false){
        				topattsf.add(attid);
        				topfreqf.add((Integer)(5-attIndex));
        			}
        			else
        			{
        				int ix = topattsf.indexOf(attid);
        				int freq = (Integer)topfreqf.get(ix);
        				freq = freq + (5-attIndex);
        				topfreqf.setElementAt(freq, ix);
        			}

                System.out.println("TOP TOTAL FOUND ****>  "+theTotalArray[attIndex].get_information()+" Attribute"+theTotalArray[attIndex].get_attributeID() );
                } catch (Exception ex) {
                    Logger.getLogger(InformationGainReal3.class.getName()).log(Level.SEVERE, null, ex);
                }
                }   
        }
    if (1==2) 
    {
    
    
        Exhaustive.Exhaustive(Users, aUser, data, theExhaustiveArrayArray); //COMMENTED OUT
        // Finding the set of size 3 which has the highest ranking of Information Gain
        
        System.out.println("length of exhaustive array is: "+theExhaustiveArrayArray.length);
    
        for (int infoIndex=0; infoIndex< theExhaustiveArrayArray.length; infoIndex++)
            System.out.println("Attribute"+theExhaustiveArrayArray[infoIndex].get_attributeID());
            System.out.println("\n");
    
        // computing  an intersection
        Set<Integer> theApproxiamtionByCummulative = new TreeSet<Integer>();
        Set<Integer> theApproxiamtionByTotal = new TreeSet<Integer>();
    
        for (int i=0; i<topCummulativeArray.length; i++)
        {
       if (isAttributeInArray(topCummulativeArray[i].get_attributeID(),theExhaustiveArrayArray ))
            theApproxiamtionByCummulative.add(topCummulativeArray[i].get_attributeID());
       if (isAttributeInArray(theTotalArray[i].get_attributeID(),theExhaustiveArrayArray ))
            theApproxiamtionByTotal.add(theTotalArray[i].get_attributeID());
        }
    
        myOutputWriter.write ("Cummulative Approxiamtion:"+theApproxiamtionByCummulative.size()+" ");
        myOutputWriter.write ("Total Approxiamtion:"+theApproxiamtionByCummulative.size()+"\n");
       
    }
    }
    
    
    Vector orderedtopattsc = new Vector ();
    Vector orderedtopfreqc = new Vector ();
    // now see average ranking over N users
    for(int j=0; j < topattsc.size(); ++j)
    {
    	int maxfreq=0; int maxid = 0;
    	for(int i=0; i < topattsc.size(); ++i)
    	{
    		if (((Integer)topfreqc.get(i) > maxfreq) && ((Integer)topfreqc.get(i) > 0))
    		{
    			maxfreq = (Integer)topfreqc.get(i);
    			maxid = i;
    		}
    	}
    	// now got maxfreq and id of one pass.
    	topfreqc.setElementAt(0, maxid);
    	orderedtopattsc.add(topattsc.get(maxid));
    	orderedtopfreqc.add(maxfreq);
    }
    
    System.out.println("TOP RANKED BY SENSITIVITY OVER "+uniqueUsers.numInstances()+" USERS FOR ATTRIBUTE:"+CLASSTOPREDICT);
    for(int i=0; i< orderedtopattsc.size(); ++i)
    {
    	System.out.println("Att:"+orderedtopattsc.get(i)+" Freq:"+orderedtopfreqc.get(i));
    }
    
    Vector orderedtopattsf = new Vector ();
    Vector orderedtopfreqf = new Vector ();
    // now see average ranking over N users
    for(int j=0; j < topattsf.size(); ++j)
    {
    	int maxfreq=0; int maxid = 0;
    	for(int i=0; i < topattsf.size(); ++i)
    	{
    		if (((Integer)topfreqf.get(i) > maxfreq) && ((Integer)topfreqf.get(i) > 0))
    		{
    			maxfreq = (Integer)topfreqf.get(i);
    			maxid = i;
    		}
    	}
    	// now got maxfreq and id of one pass.
    	topfreqf.setElementAt(0, maxid);
    	orderedtopattsf.add(topattsf.get(maxid));
    	orderedtopfreqf.add(maxfreq);
    }
    
    System.out.println("TOP RANKED BY FREQUENCY OVER "+uniqueUsers.numInstances()+" USERS FOR ATTRIBUTE:"+CLASSTOPREDICT);
    for(int i=0; i< orderedtopattsf.size(); ++i)
    {
    	System.out.println("Att:"+orderedtopattsf.get(i)+" Freq:"+orderedtopfreqf.get(i));
    }


        //Close writer
        myOutputWriter.close();

            
    
    }// main  
}
