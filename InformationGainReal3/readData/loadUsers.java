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

public class loadUsers{
	
public static void loadUsers(Hashtable UsersT, Hashtable Users) 
{
       	
       User nw, nw2, nwr;
   	   Attribute   aw, aw2; 
       Enumeration en1 = UsersT.keys();	
       Enumeration en0 = UsersT.keys();	
       
       int user1=0, user2=0, i=0, j=0, k=0, deg=0;
       double sumsameusers=0.0, sumsameattribs=0.0, sumsameusersAA=0.0, sumsameattribsAA=0.0;
       
       System.out.println("**********WELCOME TO LOAD USERS**********");
       
       // First find top 5 users by degree
       Vector topids     = new Vector ();
       Vector topdegrees = new Vector ();
       for (i=0; i < 5; i++)
  		{
  			topdegrees.add(0);
  			topids.add(0);	
  		}
       
       
       while ( en0.hasMoreElements() ){ // for all social nodes outer
       		nw = (User)UsersT.get((Integer)en0.nextElement());
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
       		
       		
       } // ewhile
       
       // see topids and degrees
  	   for (i=0; i < topids.size(); i++)
  	   {
  		 System.out.println("i="+i+" topid="+topids.get(i)+" topdegree="+topdegrees.get(i));
  	   }
       //if (1==1) return;
  	   
         
       while ( en1.hasMoreElements() ){ // for all social nodes outer
 
        	nw = (User)UsersT.get((Integer)en1.nextElement());
        	user1 = nw.getUser();
        	
    		if (topids.size() > 0)
    		{
    			for (i = 0; i < topids.size(); i++) // for each top user
    			{
			    	int topid = (Integer)topids.get(i);
			    	
			    	if (topid == user1) // if top user load it and all its neighbors into User
			    	{
						if ((Users.containsKey(user1)) == false){
							
							nwr = new User(user1);
														
	    	    			for (j = 0; j < nw.neighid.size(); j++) // load the top user and its neighbor list
	    	    			{
	    				    	int nid = nw.getneighid(j);
								nwr.loadneighid(nid);
	    	    			}
	    	    			for (j = 0; j < nw.attrid.size(); j++) // load the top user and its attribute list
	    	    			{
	    				    	int aid = nw.getattrid(j);
								nwr.loadattrid(aid);
	    	    			}
							Users.put(user1, nwr);
	    	    			
	    	    			
	    	    			//Now for each neighbor, load it and for each its neighbor list and attribute list
	    	    			
	    	    			for (j = 0; j < nw.neighid.size(); j++) 
	    	    			{
	    				    	user2 = nw.getneighid(j);
	    		    		    nw2 = (User)UsersT.get(user2);
	    				    	
	    						if ((Users.containsKey(user2)) == false){
	    							
	    							nwr = new User(user2);
	    							
	    	    	    			for (k = 0; k < nw2.neighid.size(); k++) // load the top user and its neighbor list
	    	    	    			{
	    	    				    	int nid = nw2.getneighid(k);
	    								nwr.loadneighid(nid);
	    	    	    			}
	    	    	    			for (k = 0; k < nw2.attrid.size(); k++) // load the top user and its attribute list
	    	    	    			{
	    	    				    	int aid = nw2.getattrid(k);
	    								nwr.loadattrid(aid);
	    	    	    			}
	    							Users.put(user2, nwr);
	    							
	    						} // eif not already there

	    	    			} // efor
	
						} // eif not already there
			    	} // if top user equal to current social node

    			} // efor each top user
    		} // eif topid not empty
         	
        	
       } // ewhile outer for all social nodes
           
        return;
    }  //fin de createFlags method


} //fin de createFlags class