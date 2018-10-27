// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package createAttributes;

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

import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;

import dataFile.dataFile;
import User.User;

public class createAttributesSG2{

public static Instances createAttributesSG2(Hashtable Users) 
{
	  /* define and create set of instances for input data */

    	
		//Attributes: user1, age, gender, residence, religion, maritalstatus, profession, politicalorientation, sexualorientation,
    	//              numFriendsS, like1, like2, like3, classvalue, auth, mod

   	  
          FastVector atts;
          FastVector attVals0, attVals1, attVals2, attVals3, attVals4, attVals5, attVals6, attVals7, attVals8, 
                     attVals9, attVals10, attVals11, attVals12, attVals13, attVals14, attVals15, attVals16,
                     attVals17, attVals18, attVals19;

          Instances  datanew;
          double[]   vals;
          
          atts = new FastVector();
          
          String att0  = ""; String att1  = ""; String att2  = ""; String att3  = ""; String att4  = ""; String att5  = ""; 
          String att6  = ""; String att7  = ""; String att8  = ""; String att9  = ""; String att10 = ""; String att11 = ""; 
          String att12 = ""; String att13 = ""; String att14 = ""; String att15 = ""; String att16 = ""; String att17 = ""; 
          String att18 = ""; String att19 = "";         		  
        	
          User nw;		Enumeration en1 = Users.keys();		int user1=0;
          
    	  attVals0  = new FastVector(); attVals1  = new FastVector(); attVals2  = new FastVector(); 
    	  attVals3  = new FastVector(); attVals4  = new FastVector(); attVals5  = new FastVector();
          attVals6  = new FastVector(); attVals7  = new FastVector(); attVals8  = new FastVector();
          attVals9  = new FastVector(); attVals10 = new FastVector(); attVals11 = new FastVector();
          attVals12 = new FastVector(); attVals13 = new FastVector(); attVals14 = new FastVector();
          attVals15 = new FastVector(); attVals16 = new FastVector(); attVals17 = new FastVector();
          attVals18 = new FastVector(); attVals19 = new FastVector(); 
          
          //atts.addElement(new Attribute("user"));
          
          while ( en1.hasMoreElements() ){
        		
        		//System.out.println("***********************************+HOLA");
        		nw = (User)Users.get((Integer)en1.nextElement());
        		user1 = nw.getUser();
        		
        		// flags for top users by degree
        		att0 = nw.getnidsflags(0); att1 = " "; att2 = " "; 
        		att3 = nw.getnidsflags(3); att4 = " ";
        		
        		// metric 1
        		att5  = nw.getcnd(0);  att6  = " ";  att7  = " ";  
        		att8  = nw.getcnd(3);  att9  = " ";
        		
        		// metric 2
        		att10 = nw.getcnsand(0); att11 = " "; att12 = " "; 
        		att13 = nw.getcnsand(3); att14 = " ";
        		
        		// metric 3
        		att15 = nw.getaad(0);  att16 = " "; att17 = " ";  
        		att18 = nw.getaad(3);  att19 = " ";
           		
        		if (att1 != null)
        		{
        			if (attVals0.indexOf(att0)==-1) attVals0.addElement(att0); 
           			if (attVals1.indexOf(att1)==-1) attVals1.addElement(att1); 
               		if (attVals2.indexOf(att2)==-1) attVals2.addElement(att2); 
                	if (attVals3.indexOf(att3)==-1) attVals3.addElement(att3); 
                    if (attVals4.indexOf(att4)==-1) attVals4.addElement(att4); 
                    if (attVals5.indexOf(att5)==-1) attVals5.addElement(att5); 
                    if (attVals6.indexOf(att6)==-1) attVals6.addElement(att6); 
                    if (attVals7.indexOf(att7)==-1) attVals7.addElement(att7); 
                    if (attVals8.indexOf(att8)==-1) attVals8.addElement(att8); 
                    if (attVals9.indexOf(att9)==-1) attVals9.addElement(att9); 
                    if (attVals10.indexOf(att10)==-1) attVals10.addElement(att10); 
                    if (attVals11.indexOf(att11)==-1) attVals11.addElement(att11); 
                    if (attVals12.indexOf(att12)==-1) attVals12.addElement(att12); 
                    if (attVals13.indexOf(att13)==-1) attVals13.addElement(att13); 
                    if (attVals14.indexOf(att14)==-1) attVals14.addElement(att14); 
                    if (attVals15.indexOf(att15)==-1) attVals15.addElement(att15); 
                    if (attVals16.indexOf(att16)==-1) attVals16.addElement(att16); 
                    if (attVals17.indexOf(att17)==-1) attVals17.addElement(att17); 
                    if (attVals18.indexOf(att18)==-1) attVals18.addElement(att18); 
                    if (attVals19.indexOf(att19)==-1) attVals19.addElement(att19); 
        			
        		}
        	} // ewhile
          
            atts.addElement(new Attribute("att0", attVals0));	atts.addElement(new Attribute("att1", attVals1));
			atts.addElement(new Attribute("att2", attVals2));	atts.addElement(new Attribute("att3", attVals3));
			atts.addElement(new Attribute("att4", attVals4));	atts.addElement(new Attribute("att5", attVals5));
			atts.addElement(new Attribute("att6", attVals6));	atts.addElement(new Attribute("att7", attVals7));
			atts.addElement(new Attribute("att8", attVals8));	atts.addElement(new Attribute("att9", attVals9));
			atts.addElement(new Attribute("att10", attVals10));	atts.addElement(new Attribute("att11", attVals11));
			atts.addElement(new Attribute("att12", attVals12));	atts.addElement(new Attribute("att13", attVals13));
			atts.addElement(new Attribute("att14", attVals14));	atts.addElement(new Attribute("att15", attVals15));
			atts.addElement(new Attribute("att16", attVals16));	atts.addElement(new Attribute("att17", attVals17));
			atts.addElement(new Attribute("att18", attVals18));	atts.addElement(new Attribute("att19", attVals19));
			
          for(int i=0;i<atts.size();++i)
        	  System.out.println("attribute: "+i+" "+atts.elementAt(i));
         
         
          datanew = new Instances("MyData", atts, 0);
          
       
          // Create instances object
          //datanew = new Instances("MyData", atts, 0);
          
          System.out.println("datanew.numAttributes(): "+datanew.numAttributes());
          
          //FastVector attVals = new FastVector();
          
          //attVals = (FastVector) atts.elementAt(1);
          
          // Now try add some data
        //vals = new double[datanew.numAttributes()];
          

          
          Enumeration en2 = Users.keys();	
          
          //now loop on all users in user data structure to load data from there
    	int count=0;
    	while ( en2.hasMoreElements() ){
    		
    		//System.out.println("***********************************+HOLA");
    		nw = (User)Users.get((Integer)en2.nextElement());
    		user1 = nw.getUser();
    		
       		// flags for top users by degree
    		att0 = nw.getnidsflags(0); att1 = " "; att2 = " ";
    		att3 = nw.getnidsflags(3); att4 = " ";
    		
    		// metric 1
    		att5  = nw.getcnd(0);  att6  = " ";  att7  = " ";  
    		att8  = nw.getcnd(3);  att9  = " ";
    		
    		// metric 2
    		att10 = nw.getcnsand(0); att11 = " "; att12 = " "; 
    		att13 = nw.getcnsand(3); att14 = " ";
    		
    		// metric 3
    		att15 = nw.getaad(0);  att16 = " "; att17 = " ";  
    		att18 = nw.getaad(3);  att19 = " ";
   		
    	    //System.out.println("user:"+user1+" "+att0+" "+att1+" "+att2+" "+att3+" "+att4+" "+att5+" "+att6+" "+att7+
    	    //		" "+att8+" "+att9+" "+att10+" "+att11+" "+att12+" "+att13+" "+att14+" "+att15+" "+att16+" "+att17+
    	    //		" "+att18+" "+att19+" "+att20+" "+att21+" "+att22+" "+att23+" "+att24);
   		
    		
    		if (att0 != null)
    		{
    			//vals[0] = user1; // user id
    			
    	        vals = new double[datanew.numAttributes()];
    	        
        		//System.out.println("datanew.numAttributes(): "+datanew.numAttributes());
    	        
    			vals[0]  = attVals0.indexOf(att0);   vals[1]  = attVals1.indexOf(att1);   vals[2]  = attVals2.indexOf(att2);
    			vals[3]  = attVals3.indexOf(att3);   vals[4]  = attVals4.indexOf(att4);   vals[5]  = attVals5.indexOf(att5);
    			vals[6]  = attVals6.indexOf(att6);   vals[7]  = attVals7.indexOf(att7);   vals[8]  = attVals8.indexOf(att8);
    			vals[9]  = attVals9.indexOf(att9);   vals[10] = attVals10.indexOf(att10); vals[11] = attVals11.indexOf(att11);
    			vals[12] = attVals12.indexOf(att12); vals[13] = attVals13.indexOf(att13); vals[14] = attVals14.indexOf(att14);
    			vals[15] = attVals15.indexOf(att15); vals[16] = attVals16.indexOf(att16); vals[17] = attVals17.indexOf(att17);
    			vals[18] = attVals18.indexOf(att18); vals[19] = attVals19.indexOf(att19); 
     		
    		    //System.out.println("vals[12]: "+vals[12]);
    		
    			datanew.add(new Instance(1.0, vals));
  	          //System.out.println(datanew.instance(count).attribute(0).value(0));
    			++count;

    		}
            
    	} // ewhile enumeration on all users in hashtable
      
          return datanew;
    }  //fin de createAttributesSG2


} //fin de createAttributesSG2