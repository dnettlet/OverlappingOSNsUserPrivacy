// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package readData;

//import java.io.*;
//import java.lang.*;
//import java.math.*;
import java.util.Collections;
import java.util.Vector;
//import java.lang.Math;
//import java.lang.Object;
//import java.io.Serializable;
//import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.io.InputStreamReader;
//import java.io.PrintStream;
//import java.nio.*;

import User.User;
import Attribute.Attribute;

public class createFlags{
	
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

public static void createFlags(Hashtable Users, Hashtable Attributes) 
{
       	
       User nw, nwn;
   	   Attribute   aw, aw2; 
       Enumeration en1 = Users.keys();	
       
       int user1=0, i=0, j=0, k=0;
       double sumsameusers=0.0;
       
   	   List lu;
   	   List<Integer> lu2; 
   	   List<Integer> la2;
       
	   int idattr   = 0, numattrs = 0, numusers = 0;
	   
	   double numberofsocialneighboursuser = 0.0;
	   double numberofattributeneighboursuser = 0.0;
		
	   String dsumsameusers ="";
       
       System.out.println("**********WELCOME TO CREATE FLAGS**********");
          int usercount = 0;
       while ( en1.hasMoreElements()){
        	++usercount;	

        	nw = (User)Users.get((Integer)en1.nextElement());
        	user1 = nw.getUser();
 
        	//nw.setNumAttribs();
 
			numberofsocialneighboursuser = nw.neighid.size();

			numberofattributeneighboursuser = nw.attribhfid.size();
        	
        	lu = Arrays.asList(nw.neighid);
        	
        	//List<Integer> lu2 = new ArrayList<Integer>(Arrays.asList(lu));
        	lu2 = new ArrayList<Integer>(nw.neighid);
        	
        	Enumeration en2 = Attributes.keys();
        	
        	while (en2.hasMoreElements()){
        		aw = (Attribute)Attributes.get((Integer)en2.nextElement());

        		idattr   = aw.getAttrib();
        		//numattrs = aw.attribids.size();
        		//numusers = aw.userids.size();
        		
            	//System.out.println("USER:"+user1+" ATTRIBUTE:"+idattr);
        		
            	//List la = Arrays.asList(aw.userids);
            	
            	la2 = new ArrayList<Integer>(aw.userids);
            	
            	//List lua = intersection(lu,la);
            	
            	//List lua = lu;
            	
            	//List<Integer> list = new ArrayList<Integer>(Arrays.asList(16, 17, 18, 19, 20));
            	//List<Integer> list2 = new ArrayList<Integer>(list.subList(2, 5));
            	//System.out.println("lu2:"+lu2);
            	//System.out.println("la2:"+la2);
            	la2.retainAll(lu2);
            	//System.out.println(la2);
            	
            	//lu.retainAll(la);
            	
            	sumsameusers = la2.size();
        		
            	if (sumsameusers > 0)
            	{

    					
        			//now increment the total by 1 divided by the sum of social and attribute nodes for i
        			sumsameusers = sumsameusers / (numberofsocialneighboursuser + 
		    							           numberofattributeneighboursuser);
        			
                	//System.out.println("USER:"+user1+" ATTRIBUTE:"+idattr);
        			//System.out.println("lu2:"+lu2);
        			//System.out.println("la2:"+la2);
        			//System.out.println("hola sumsameusers: "+sumsameusers);
        			//System.out.println("sumsameusers "+sumsameusers);
		    					
		    	} // eif sumsameusers

				
    		
    		// now write this statistic (AA_SAN(u,a) to the user instance into a vector element for this attribute-value
	    	//System.out.println("sumsameusers "+sumsameusers);
	    	dsumsameusers = discretizeFlags(sumsameusers);
	    	//System.out.println("dsumsameusers "+dsumsameusers);
        	nw.loadaasan(sumsameusers);         	nw.loadaasand(dsumsameusers);
        	
			Users.remove(nw.getUser());
			Users.put(nw.getUser(),nw);
        	
        	//int sizevec = nw.attribv_aa_san.size();
        	//double seesum = nw.getaasan(sizevec-1); //this forces the update in the hashtable
        	
        	//int sizevecd = nw.attribv_aa_sand.size();
        	//String seesumd = nw.getaasand(sizevecd-1); //this forces the update in the hashtable
        	
	    	//System.out.println("sumsameusers from vector "+seesum+" "+seesumd);
  
        } // ewhile for all attribute nodes
        	
       } // ewhile for all social nodes
           
        return;
    }  //fin de createFlags method


} //fin de createFlags class