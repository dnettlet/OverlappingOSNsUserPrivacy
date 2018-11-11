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

public class createFlagsV3filter{
	
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

public static void createFlagsV3filter(Hashtable Users, Hashtable Attributes, int ptopid, int ptopatt) 
{
       	
       User nw, nw2;
   	   Attribute   aw, aw2; 
       Enumeration en1 = Users.keys();	
       Enumeration en0 = Users.keys();	
       
       int user1=0, user2=0, i=0, j=0, k=0, deg=0;
       double sumsameusers=0.0, sumsameattribs=0.0, sumsameusersAA=0.0, sumsameattribsAA=0.0;
       
       System.out.println("**********CREATE FLAGS**********");
		 System.out.println(" ptopid="+ptopid+" ptopatt="+ptopatt);
       
       // First find top 5 users by degree
       Vector topids     = new Vector ();
       Vector topdegrees = new Vector ();
       Vector topids_atts = new Vector ();

       for (i=0; i < 5; i++)
  		{
  			topdegrees.add(0);
  			topids.add(0);	
  		}
       
       for (i=0; i < 2; i++)
  		{
  			topids_atts.add(0);	
  		}
        
       /*
		topids_atts.setElementAt(-74, 0); // bangalore institute of technology
		topids_atts.setElementAt(-61, 1); // aspiag service srl
		topids_atts.setElementAt(-27, 2); // fh aachen
		topids_atts.setElementAt(-542, 3); // \u6578\u5b78\u7cfb (hk school)
		topids_atts.setElementAt(-322, 4); // 1180
		topids_atts.setElementAt(-311, 5); // general studies
		topids_atts.setElementAt(-564, 6); // post graduate in marketing
       */
       
       /*
		topids_atts.setElementAt(-2544, 0); // model public sr. sec. school
		topids_atts.setElementAt(-362, 1); // bury, england
		topids_atts.setElementAt(-462, 2); // nasa
		topids_atts.setElementAt(-74, 3); // bangalore institute of technology
		topids_atts.setElementAt(-743, 4); // thailand
		topids_atts.setElementAt(-27, 5); // fh aachen
	*/
      
       /*
		topids_atts.setElementAt(-74, 0); // 
		topids_atts.setElementAt(-542, 1); // 
		topids_atts.setElementAt(-27, 2); // 
		topids_atts.setElementAt(-795, 3); // 
		topids_atts.setElementAt(-175, 4); // 
		topids_atts.setElementAt(-564, 5); //
       */
 
		topids_atts.setElementAt(-74, 0); // 
		topids_atts.setElementAt(-27, 1); // 
       
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
       			topids.setElementAt(user1, smallestix);
       		}
       		
       		
       }
       
       //now set "top" user ids manually
		topids.setElementAt(2727, 0); // 
		topids.setElementAt(4292, 1); // 
		topids.setElementAt(2038, 2); // 
		topids.setElementAt(16, 3); // 
		topids.setElementAt(971, 4); // 
       
       
       // see topids and degrees
  	  /* for (i=0; i < topids.size(); i++)
  	  {
  		 System.out.println("i="+i+" topid="+topids.get(i)+" topdegree="+topdegrees.get(i));
  	   }
	  */
         
       while ( en1.hasMoreElements() ){ // for all social nodes outer
        		

        	nw = (User)Users.get((Integer)en1.nextElement());
        	user1 = nw.getUser();
        	
            // FIRST THE USER NEIGHBORS
            int rcount = 1;
    		if (topids.size() > 0)
    		{
    			for (i = 0; i < topids.size(); i++) // for each social neighbour of user1
    			{
			    	int topid = (Integer)topids.get(i);
			    	String neighbor = "NO";
			    	if (nw.neighid.contains((Integer)topid))
			    		neighbor = "YES";
			    		
        			nw.nids.add(topid);
        			nw.nidsflags.add(neighbor);
        			//System.out.println("add a topid:"+topid+" flag="+neighbor);
    			}
    		}
    		
            // SECOND THE ATTRIBUTE NEIGHBORS
       		if (topids_atts.size() > 0)
    		{
    			for (i = 0; i < topids_atts.size(); i++) // for each attribute neighbour of user1
    			{
			    	int topatt = (Integer)topids_atts.get(i);
			    	String neighbor = "NO";
			    	if (nw.attrid.contains((Integer)topatt))
			    		neighbor = "YES";
			    		
        			nw.attribhfid.add(topatt);
        			nw.attribhf.add(neighbor);
        			//System.out.println("add a topatt:"+topatt+" flag="+neighbor);
    			}
    		}
 
        	//nw.setNumAttribs();
        	
            //System.out.println("HOLA p1. use1="+user1+"nw.neighid.size()="+nw.neighid.size());
            
            int top0 = (Integer)topids.get(0); 
            int top1 = (Integer)topids.get(1); 
            int top2 = (Integer)topids.get(2); 
            int top3 = (Integer)topids.get(3); 
            int top4 = (Integer)topids.get(4); 
            

            
            System.out.println("topu0 "+top0+"topu1 "+top1+"topu2 "+top2+"topu3 "+top3+"topu4 "+top4);
            
            int topa0 = (Integer)topids_atts.get(0); 
            int topa1 = (Integer)topids_atts.get(1); 
            
            int topidx =0; int topattx=0;
            
            if (ptopid != -99)
            	topidx = (Integer)topids.get(ptopid);    			// THIS IS THE NEW LINE    
            else
            	topidx = -99;
            
            if(ptopatt != -99)
            	topattx = (Integer)topids_atts.get(ptopatt);    // THIS IS THE NEW LINE
            else
            	topattx = 99;

            
            //System.out.println("topa0 "+topa0+"topa1 "+topa1+"topa2 "+topa2+"topa3 "+topa3+"topa4 "+topa4);
            System.out.println("topa0 "+topa0+"topa1 "+topa1);

        	
        // This loop deals with first four metrics
        for (k = 0; k < topids.size(); k++) // for 5 non neighbors and 5 neighbors
        {	
        	
    		user2 = (Integer)topids.get(k);
		
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
    				    	  if (socialneighbouruser1 != topidx) 	// THIS IS THE NEW LINE
    				    	  {
    				    		++sumsameusers;
    				    		sumsameusersAA = sumsameusersAA + ( 1.0 / Math.log(numberofsocialneighboursuser));
    				    	  }
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
	    	
	  		//METRICS 2 and 4 same attributes
        	sumsameattribs = 0.0;        	sumsameattribsAA = 0.0;
    		if (nw.attrid.size() > 0)
    			for (i = 0; i < nw.attrid.size(); i++) // for each attrib neighbour of user1
    			{
			    	int attributeneighbouruser1 = nw.getattrid(i);
   				
    	    		if (nw2.attrid.size() > 0)
    	    			for (j = 0; j < nw2.attrid.size(); j++) // for each attrib neighbour of user2
    	    			{
    	    				int attributeneighbouruser2 = nw2.getattrid(j);
 	    				
    				    	if (attributeneighbouruser1 == attributeneighbouruser2) //the same
    				    	{
      				    	  if (attributeneighbouruser1 != topattx)    // THIS IS THE NEW LINE
      				    	  {
    				    		++sumsameattribs;
  
    				    		int attribid = nw2.getattrid(j);
    				    		
    				    		if (Attributes.containsKey(attribid) == true)
    				    		{
    				    		    aw = (Attribute)Attributes.get(attribid);
    				    		
    				    		    double numberofsocialneighboursattribute = 0;
    				    		    numberofsocialneighboursattribute = aw.userids.size();
    				    		
    				    		    sumsameattribsAA = sumsameattribsAA + ( 1.0 / Math.log(numberofsocialneighboursattribute));
    				    		}
      				    	  } // filter top
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
       	
        	
       } // ewhile for all social nodes outer (nw)
           
        return;
    }  //fin de createFlags method


} //fin de createFlags class