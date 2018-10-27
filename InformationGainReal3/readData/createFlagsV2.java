// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package readData;

import java.io.*;
import java.lang.*;
//import java.math.*;
import java.util.Collections;
import java.util.Vector;
import java.lang.Math;
import java.lang.Object;
import java.io.Serializable;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.*;

//import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import dataFile.dataFile;
import User.User;
import Attribute.Attribute;

public class createFlagsV2{
	
public static String discretizeFlags(Double nflag) 
	{
	String dflag = "";
	if (nflag == 0.0)
		dflag = "0.00";		
	else if (nflag < 0.11)
		dflag = ">0.00-0.10";
	else if (nflag < 0.21)
		dflag = "0.11-0.20";
	else if (nflag < 0.31)
		dflag = "0.21-0.30";
	else if (nflag < 0.41)
		dflag = "0.31-0.40";
	else if (nflag < 0.51)
		dflag = "0.41-0.50";
	else if (nflag < 0.61)
		dflag = "0.51-0.60";
	else if (nflag < 0.71)
		dflag = "0.61-0.70";
	else if (nflag < 0.81)
		dflag = "0.71-0.80";
	else if (nflag < 0.91)
		dflag = "0.81-0.90";
	else
		dflag = "0.91-1.00";
	
	return dflag;
	
	}   

public static void createFlagsV2(Hashtable Users, Hashtable Attributes) 
{
       	
       User nw, nw2;
   	   Attribute   aw, aw2; 
       Enumeration en1 = Users.keys();	
       Enumeration en0 = Users.keys();	
       
       int user1=0, user2=0, i=0, j=0, k=0, deg=0;
       double sumsameusers=0.0, sumsameattribs=0.0, sumsameusersAA=0.0, sumsameattribsAA=0.0;
       
       System.out.println("**********WELCOME TO CREATE FLAGS**********");
       
       // First find top 5 users by degree
       Vector topids     = new Vector ();
       Vector topdegrees = new Vector ();
       for (i=0; i < 5; i++)
  		{
  			topdegrees.setElementAt(0, i);
  			topids.setElementAt(0, i);	
  		}
       
       
       while ( en0.hasMoreElements() ){ // for all social nodes outer
       		nw = (User)Users.get((Integer)en0.nextElement());
       		user1 = nw.getUser();
       		deg  = nw.neighid.size();
       		int smallestdegree = 99999, smallestix=-1, smallestid=-1;
       		for (i=0; i < topids.size(); i++)
       			if ((Integer)topdegrees.get(i) < smallestdegree)
       			{
       				smallestdegree = (Integer)topdegrees.get(i);
       				smallestid     = (Integer)topids.get(i);      				
       				smallestix     = i;
       			}
       		if (deg > smallestdegree)
       		{ // substitute
       			topdegrees.setElementAt(deg, smallestix);
       			topids.setElementAt(smallestid, smallestix);
       		}
       		
       		
       }
          
       while ( en1.hasMoreElements() ){ // for all social nodes outer
        		

        	nw = (User)Users.get((Integer)en1.nextElement());
        	user1 = nw.getUser();
 
        	//nw.setNumAttribs();
        	
        	// FIRSTLY, LOAD A VECTOR WITH FIRST FIVE NEIGHBORS AND FIRST FIVE NON-NEIGHBORS OF "U"
        	Vector nids = new Vector ();
        	
        	
            // FIRST THE NEIGHBORS
            int rcount = 1;
    		if (nw.neighid.size() > 0)
    		{
    			for (i = 0; (i < nw.neighid.size()) && (rcount < 6); i++) // for each social neighbour of user1
    			{
                	++rcount;
			    	int socialneighbouruser1 = nw.getneighid(i);
        			nids.add(socialneighbouruser1);
        			nw.nids.add(socialneighbouruser1);
        			nw.nidsflags.add("YES");
        			System.out.println("add a neighbor:"+socialneighbouruser1);
    			}
    		}

    		
			int fill = 5 - nw.neighid.size();
			
        	// NEXT THE NON NEIGHBORS

            Enumeration en3 = Users.keys();	
            rcount = 1;
            boolean isneighbor = false;
            while ( en3.hasMoreElements() && rcount <= fill){   // for all social nodes inner
            	
            	++rcount;

            	nw2 = (User)Users.get((Integer)en3.nextElement());
            	user2 = nw2.getUser();
            	
            	// CHECK THAT THIS USER IS NOT A NEIGHBOR
        		isneighbor = false;
        		if (nw.neighid.size() > 0)
        			for (i = 0; (i < nw.neighid.size()) && (isneighbor == false); i++) // for each social neighbour of user1
        			{
    			    	int socialneighbouruser1 = nw.getneighid(i);
    			    	if (socialneighbouruser1 == user2)
    			    		isneighbor = true;
        			}
    			 // END CHECK THAT THIS USER IS NOT A NEIGHBOR   	
            	
            	
        		if (isneighbor == false) // load into vector
        		{
        			nids.add(user2);
        			nw.nids.add(user2);
        			nw.nidsflags.add("NO");
        			System.out.println("add a non neighbor:"+user2);
        		}
	
            } // ewhile
            System.out.println("HOLA p1. use1="+user1+"nw.neighid.size()="+nw.neighid.size());
            
         	
        	
        // This loop deals with first four metrics
        for (k = 0; k < nids.size(); k++) // for 5 non neighbors and 5 neighbors
        {	
        	
    		user2 = (Integer)nids.get(k);
		
    		if (Users.containsKey(user2) == true) // user2 must be in User hashtable
    		{
    		    nw2 = (User)Users.get(user2);

        	    user2 = nw2.getUser();

        	
        	
   		
    		//METRICS 1 and 3 same social nodes
        	sumsameusers = 0.0;         	sumsameusersAA = 0.0;
    		if (nw.neighid.size() > 0)
    			for (i = 0; i < nw.neighid.size(); i++) // for each social neighbour of user1
    			{
			    	int socialneighbouruser1 = nw.getneighid(i);
			    	
		    		double numberofsocialneighboursuser = nw.neighid.size();
			    	
    				
    	    		if (nw2.neighid.size() > 0)
    	    			for (j = 0; j < nw2.neighid.size(); j++) // for each social neighbour of user2
    	    			{
    				    	int socialneighbouruser2 = nw2.getneighid(j);
    	    				
    				    	if (socialneighbouruser1 == socialneighbouruser2) //the same
    				    	{
    				    		++sumsameusers;
    				    		sumsameusersAA = sumsameusersAA + ( 1.0 / Math.log(numberofsocialneighboursuser));
    				    	}
    	    				
    	    			}
    			}
	    	String dsumsameusers = discretizeFlags(sumsameusers);
	    	String metric1 = dsumsameusers;
	    	String dsumsameusersAA = discretizeFlags(sumsameusersAA);
	    	String metric3 = dsumsameusersAA;

	    	nw.loadcn(sumsameusers);     // load metric1 into vector in user 'u' data structure
	    	nw.loadcnd(dsumsameusers);   // load metric1 into vector in user 'u' data structure (discretized version)
	    	nw.loadaa(sumsameusersAA);   // load metric3 into vector in user 'u' data structure
	    	nw.loadaad(dsumsameusersAA); // load metric3 into vector in user 'u' data structure (discretized version)
	    	
	  		//METRICS 2 and 4 same social nodes and same attributes
        	sumsameattribs = 0.0;        	sumsameattribsAA = 0.0;
    		if (nw.attribhf.size() > 0)
    			for (i = 0; i < nw.attribhf.size(); i++) // for each social neighbour of user1
    			{
			    	String attributeneighbouruser1 = nw.getattribhf(i);
   				
    	    		if (nw2.attribhf.size() > 0)
    	    			for (j = 0; j < nw2.attribhf.size(); j++) // for each social neighbour of user2
    	    			{
    	    				String attributeneighbouruser2 = nw2.getattribhf(j);
    	    				
    				    	if (attributeneighbouruser1.equals("YES") && attributeneighbouruser2.equals("YES")) //the same
    				    	{
    				    		++sumsameattribs;
  
    				    		int attribid = nw2.getattribhfid(j);
    				    		
    				    		if (Attributes.containsKey(attribid) == true)
    				    		{
    				    		    aw = (Attribute)Attributes.get(attribid);
    				    		
    				    		    double numberofsocialneighboursattribute = 0;
    				    		    numberofsocialneighboursattribute = aw.userids.size();
    				    		
    				    		    sumsameattribsAA = sumsameattribsAA + ( 1.0 / Math.log(numberofsocialneighboursattribute));
    				    		}
    				    	}
    	    				
    	    			}
    			}
	    	String dsumsameattribs = discretizeFlags(sumsameattribs);
	    	
	    	double m2 =  sumsameusers + sumsameattribs;
	    	String dm2 = discretizeFlags(m2);
	    	double m4 =  sumsameusersAA + sumsameattribsAA;
	    	String dm4 = discretizeFlags(m4);
	    	
	    	nw.loadcnsan(m2);    // load metric2 into vector in user 'u' data structure
	    	nw.loadcnsand(dm2);  // load metric2 into vector in user 'u' data structure (discretized version)
	    	nw.loadaasan(m4);    // load metric4 into vector in user 'u' data structure
	    	nw.loadaasand(dm4);  // load metric4 into vector in user 'u' data structure  (discretized version)
	    	
       	
    		} // user2 has to be in User hashtable
    		
        } // ewhile for all social nodes inner (nw2) METRICS 1 TO 4
        
        
     	//AASANSTATIC static (METRIC 5)
    	Enumeration en2 = Attributes.keys();
    	while (en2.hasMoreElements()){
    		aw = (Attribute)Attributes.get((Integer)en2.nextElement());

    		int idattr   = aw.getAttrib();
    		int numattrs = aw.attribids.size();
    		int numusers = aw.userids.size();
    		
        	//System.out.println("USER:"+user1+" ATTRIBUTE:"+idattr);
   	
    		sumsameusers = 0.0;
         
    		if (nw.neighid.size() > 0)
    			for (i = 0; i < nw.neighid.size(); i++) // for each social neighbour of user
    			{
                	//System.out.println("point 1");
			    	int socialneighbouruser = nw.getneighid(i);
    				
            		if (aw.userids.size() > 0)
    				    for (j = 0; j < aw.userids.size(); j++)  // for each social neighbour of attribute
    				    {
                        	//System.out.println("point 2");
    				    	
    				    	int socialneighbourattribute = aw.getuserids(j);
    				    	
    		            	//System.out.println("socialneighbouruser:"+socialneighbouruser+" socialneighbourattribute:"+socialneighbourattribute);
	    				
    				    	if (socialneighbouruser == socialneighbourattribute) //the same
    				    	{
                            	//System.out.println("hola");
           		            	//System.out.println("socialneighbouruser:"+socialneighbouruser+" socialneighbourattribute:"+socialneighbourattribute);
    		    				
    				    		// get number of social neighbours of socialneighbouruser(i)
    				    		double numberofsocialneighboursuser = nw.neighid.size();
    				    		// get number of attribute neighbours of socialneighbouruser(i)
    				    		double numberofsocialneighboursattribute = aw.userids.size();
	    					
    				    		//now increment the total by 1 divided by the sum of social and attribute nodes for i
    				    		sumsameusers = sumsameusers + ( 1.0 / Math.log(numberofsocialneighboursuser + 
    				    				                              numberofsocialneighboursattribute) );
    				    		//System.out.println("sumsameusers "+sumsameusers);
	    					
	    				     } // eif same
	    				} // efor social nodes of attribute
    			} // efor social nodes of user
			
		
		// now write this statistic (AA_SAN(u,a) to the user instance into a vector element for this attribute-value
    	double metric5 = sumsameusers;
    		
    		
    	//System.out.println("sumsameusers "+sumsameusers);
    	String dsumsameusers = discretizeFlags(sumsameusers);
    	//System.out.println("dsumsameusers "+dsumsameusers);
    	
    	nw.loadaasanstatic(sumsameusers);         	
    	nw.loadaasanstaticd(dsumsameusers); // load discretized version
    	
    	int sizevec = nw.aasanstatic.size();
    	double seesum = nw.getaasanstatic(sizevec-1); //this forces the update in the hashtable
    	
    	int sizevecd = nw.aasanstaticd.size();
    	String seesumd = nw.getaasanstaticd(sizevecd-1); //this forces the update in the hashtable
    	
    	//System.out.println("sumsameusers from vector "+seesum+" "+seesumd);

    } // ewhile for all attribute nodes (METRIC 5)
        
        
         	
        	
       } // ewhile for all social nodes outer (nw)
           
        return;
    }  //fin de createFlags method


} //fin de createFlags class