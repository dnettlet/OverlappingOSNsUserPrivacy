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

public class createAttributesG{

public static Instances createAttributesG(Hashtable Users) 
{
	  /* define and create set of instances for input data */

    	
		//Attributes: user1, age, gender, residence, religion, maritalstatus, profession, politicalorientation, sexualorientation,
    	//              numFriendsS, like1, like2, like3, classvalue, auth, mod

   	  
          FastVector atts;
          FastVector attVals0, attVals1, attVals2, attVals3, attVals4, attVals5, attVals6, attVals7, attVals8, 
                     attVals9, attVals10, attVals11, attVals12, attVals13, attVals14, attVals15, attVals16,
                     attVals17, attVals18, attVals19, attVals20, attVals21, attVals22, attVals23, attVals24,
                     attVals25, attVals26, attVals27, attVals28, attVals29, attVals30, attVals31, attVals32,
                     attVals33, attVals34, attVals35, attVals36, attVals37, attVals38, attVals39, attVals40,
                     attVals41;
          Instances  datanew;
          double[]   vals;
          
          atts = new FastVector();
          
          // attributes 0 to 20
          String att0  = ""; String att1  = ""; String att2  = ""; String att3  = ""; String att4  = ""; String att5  = ""; 
          String att6  = ""; String att7  = ""; String att8  = ""; String att9  = ""; String att10 = ""; String att11 = ""; 
          String att12 = ""; String att13 = ""; String att14 = ""; String att15 = ""; String att16 = ""; String att17 = ""; 
          String att18 = ""; String att19 = ""; String att20 = ""; 
          
       // attributes 21 to 41 are discretized aasan for given user and attributes 0 to 20
          String att21 = ""; String att22 = ""; String att23 = ""; String att24 = ""; String att25 = ""; String att26 = ""; 
          String att27 = ""; String att28 = ""; String att29 = ""; String att30 = ""; String att31 = ""; String att32 = ""; 
          String att33 = ""; String att34 = ""; String att35 = ""; String att36 = ""; String att37 = ""; String att38 = "";  
          String att39 = ""; String att40 = ""; String att41 = "";
        		  
        	
          User nw;		Enumeration en1 = Users.keys();		int user1=0;
          
    	  attVals0  = new FastVector(); attVals1  = new FastVector(); attVals2  = new FastVector(); 
    	  attVals3  = new FastVector(); attVals4  = new FastVector(); attVals5  = new FastVector();
          attVals6  = new FastVector(); attVals7  = new FastVector(); attVals8  = new FastVector();
          attVals9  = new FastVector(); attVals10 = new FastVector(); attVals11 = new FastVector();
          attVals12 = new FastVector(); attVals13 = new FastVector(); attVals14 = new FastVector();
          attVals15 = new FastVector(); attVals16 = new FastVector(); attVals17 = new FastVector();
          attVals18 = new FastVector(); attVals19 = new FastVector(); attVals20 = new FastVector();
          
          attVals21 = new FastVector(); attVals22 = new FastVector(); attVals23 = new FastVector();  
          attVals24 = new FastVector(); attVals25 = new FastVector(); attVals26 = new FastVector(); 
          attVals27 = new FastVector(); attVals28 = new FastVector(); attVals29 = new FastVector(); 
          attVals30 = new FastVector(); attVals31 = new FastVector(); attVals32 = new FastVector(); 
          attVals33 = new FastVector(); attVals34 = new FastVector(); attVals35 = new FastVector(); 
          attVals36 = new FastVector(); attVals37 = new FastVector(); attVals38 = new FastVector(); 
          attVals39 = new FastVector(); attVals40 = new FastVector(); attVals41 = new FastVector(); 
          
          //atts.addElement(new Attribute("user"));
          
          while ( en1.hasMoreElements() ){
        		
        		//System.out.println("***********************************+HOLA");
        		nw = (User)Users.get((Integer)en1.nextElement());
        		user1 = nw.getUser();
        		
        		// attribute flags
        		att0  = nw.getattribhf(0);  att1  = nw.getattribhf(1);  att2  = nw.getattribhf(2);
        		att3  = nw.getattribhf(3);  att4  = nw.getattribhf(4);  att5  = nw.getattribhf(5);
        		att6  = nw.getattribhf(6);  
        		
        		// metric 1
        		att7  = nw.getcnd(0);  att8  = nw.getcnd(1);  att9  = nw.getcnd(2);  
        		att10 = nw.getcnd(3);  att11 = nw.getcnd(4);  att12 = nw.getcnd(5); 
        		att13 = nw.getcnd(6); 
        		
        		// metric 2
        		att14 = nw.getcnsand(0); att15 = nw.getcnsand(1); att16 = nw.getcnsand(2); 
        		att17 = nw.getcnsand(3); att18 = nw.getcnsand(4); att19 = nw.getcnsand(5); 
        		att20 = nw.getcnsand(6);
        		
        		// metric 3
        		att21 = nw.getaad(0);  att22 = nw.getaad(1);  att23 = nw.getaad(2);
        		att24 = nw.getaad(3);  att25 = nw.getaad(4);  att26 = nw.getaad(5);
        		att27 = nw.getaad(6);  
        		
        		// metric 4
        		att28 = nw.getaasand(0); att29 = nw.getaasand(1); att30 = nw.getaasand(2);  
        		att31 = nw.getaasand(3); att32 = nw.getaasand(4); att33 = nw.getaasand(5); 
        		att34 = nw.getaasand(6); 
        		
        		// metric 5
        		att35 = nw.getaasanstaticd(0); att36 = nw.getaasanstaticd(1); att37 = nw.getaasanstaticd(2); 
        		att38 = nw.getaasanstaticd(3); att39 = nw.getaasanstaticd(4); att40 = nw.getaasanstaticd(5); 
        		att41 = nw.getaasanstaticd(6);
           		
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
                    if (attVals20.indexOf(att20)==-1) attVals20.addElement(att20); 
                    
                    if (attVals21.indexOf(att21)==-1) attVals21.addElement(att21); 
                    if (attVals22.indexOf(att22)==-1) attVals22.addElement(att22);
                    if (attVals23.indexOf(att23)==-1) attVals23.addElement(att23);
                    if (attVals24.indexOf(att24)==-1) attVals24.addElement(att24);
                    if (attVals25.indexOf(att25)==-1) attVals25.addElement(att25);
                    if (attVals26.indexOf(att26)==-1) attVals26.addElement(att26);
                    if (attVals27.indexOf(att27)==-1) attVals27.addElement(att27);
                    if (attVals28.indexOf(att28)==-1) attVals28.addElement(att28);
                    if (attVals29.indexOf(att29)==-1) attVals29.addElement(att29);
                    if (attVals30.indexOf(att30)==-1) attVals30.addElement(att30);
                    if (attVals31.indexOf(att31)==-1) attVals31.addElement(att31);
                    if (attVals32.indexOf(att32)==-1) attVals32.addElement(att32);
                    if (attVals33.indexOf(att33)==-1) attVals33.addElement(att33);
                    if (attVals34.indexOf(att34)==-1) attVals34.addElement(att34);
                    if (attVals35.indexOf(att35)==-1) attVals35.addElement(att35);
                    if (attVals36.indexOf(att36)==-1) attVals36.addElement(att36);
                    if (attVals37.indexOf(att37)==-1) attVals37.addElement(att37);
                    if (attVals38.indexOf(att38)==-1) attVals38.addElement(att38);
                    if (attVals39.indexOf(att39)==-1) attVals39.addElement(att39);
                    if (attVals40.indexOf(att40)==-1) attVals40.addElement(att40);
                    if (attVals41.indexOf(att41)==-1) attVals41.addElement(att41);
        			
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
			atts.addElement(new Attribute("att20", attVals20));	
			
			atts.addElement(new Attribute("att21", attVals21));
            atts.addElement(new Attribute("att22", attVals22));	atts.addElement(new Attribute("att23", attVals23));
			atts.addElement(new Attribute("att24", attVals24));	atts.addElement(new Attribute("att25", attVals25));
			atts.addElement(new Attribute("att26", attVals26));	atts.addElement(new Attribute("att27", attVals27));
			atts.addElement(new Attribute("att28", attVals28));	atts.addElement(new Attribute("att29", attVals29));
			atts.addElement(new Attribute("att30", attVals30));	atts.addElement(new Attribute("att31", attVals31));
			atts.addElement(new Attribute("att32", attVals32));	atts.addElement(new Attribute("att33", attVals33));
			atts.addElement(new Attribute("att34", attVals34));	atts.addElement(new Attribute("att35", attVals35));
			atts.addElement(new Attribute("att36", attVals36));	atts.addElement(new Attribute("att37", attVals37));
			atts.addElement(new Attribute("att38", attVals38));	atts.addElement(new Attribute("att39", attVals39));
			atts.addElement(new Attribute("att40", attVals40));	atts.addElement(new Attribute("att41", attVals41));
			
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
    		
    		// attribute flags
    		att0  = nw.getattribhf(0);  att1  = nw.getattribhf(1);  att2  = nw.getattribhf(2);
    		att3  = nw.getattribhf(3);  att4  = nw.getattribhf(4);  att5  = nw.getattribhf(5);
    		att6  = nw.getattribhf(6);  
    		
    		// metric 1
    		att7  = nw.getcnd(0);  att8  = nw.getcnd(1);  att9  = nw.getcnd(2);  
    		att10 = nw.getcnd(3);  att11 = nw.getcnd(4);  att12 = nw.getcnd(5); 
    		att13 = nw.getcnd(6); 
    		
    		// metric 2
    		att14 = nw.getcnsand(0); att15 = nw.getcnsand(1); att16 = nw.getcnsand(2); 
    		att17 = nw.getcnsand(3); att18 = nw.getcnsand(4); att19 = nw.getcnsand(5); 
    		att20 = nw.getcnsand(6);
    		
    		// metric 3
    		att21 = nw.getaad(0);  att22 = nw.getaad(1);  att23 = nw.getaad(2);
    		att24 = nw.getaad(3);  att25 = nw.getaad(4);  att26 = nw.getaad(5);
    		att27 = nw.getaad(6);  
    		
    		// metric 4
    		att28 = nw.getaasand(0); att29 = nw.getaasand(1); att30 = nw.getaasand(2);  
    		att31 = nw.getaasand(3); att32 = nw.getaasand(4); att33 = nw.getaasand(5); 
    		att34 = nw.getaasand(6); 
    		
    		// metric 5
    		att35 = nw.getaasanstaticd(0); att36 = nw.getaasanstaticd(1); att37 = nw.getaasanstaticd(2); 
    		att38 = nw.getaasanstaticd(3); att39 = nw.getaasanstaticd(4); att40 = nw.getaasanstaticd(5); 
    		att41 = nw.getaasanstaticd(6);
   		
    		
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
    			vals[18] = attVals18.indexOf(att18); vals[19] = attVals19.indexOf(att19); vals[20] = attVals20.indexOf(att20);
    			
    			vals[21] = attVals21.indexOf(att21);  
    			vals[22] = attVals22.indexOf(att22); vals[23] = attVals23.indexOf(att23); vals[24] = attVals24.indexOf(att24);
    			vals[25] = attVals25.indexOf(att25); vals[26] = attVals26.indexOf(att26); vals[27] = attVals27.indexOf(att27);
    			vals[28] = attVals28.indexOf(att28); vals[29] = attVals29.indexOf(att29); vals[30] = attVals30.indexOf(att30);
    			vals[31] = attVals31.indexOf(att31); vals[32] = attVals32.indexOf(att32); vals[33] = attVals33.indexOf(att33);
    			vals[34] = attVals34.indexOf(att34); vals[35] = attVals35.indexOf(att35); vals[36] = attVals36.indexOf(att36);
    			vals[37] = attVals37.indexOf(att37); vals[38] = attVals38.indexOf(att38); vals[39] = attVals39.indexOf(att39);
    			vals[40] = attVals40.indexOf(att40); vals[41] = attVals41.indexOf(att41); 
     		
    		    //System.out.println("vals[12]: "+vals[12]);
    		
    			datanew.add(new Instance(1.0, vals));
  	          //System.out.println(datanew.instance(count).attribute(0).value(0));
    			++count;

    		}
            
    	} // ewhile enumeration on all users in hashtable
      
          return datanew;
    }  //fin de createAttributesG


} //fin de createAttributesG