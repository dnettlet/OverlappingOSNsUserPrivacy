// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package readData;

import java.io.*;
import java.lang.*;
//import java.math.*;
import java.util.Collections;
import java.util.Vector;
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

import dataFile.dataFile;
import User.User;
import Attribute.Attribute;
import Attribute.AttributeDic;

public class readData{

final static int  NUMVARS = 4;



public static double readTopology(Hashtable UsersT, Hashtable Attributes, Hashtable AttrDs, Hashtable UsersTF) 
{
    int NUMVARS = 4;
    
    int user2=0;
    
	int i=0,j=0,k=0, yaesta=0;
	
	double ret=0;
	
	int vec[];
	double corr = 0;
	double[] v1 = {1,2,3,4};
	double[] v2 = {1,2,3,4};
	
	//***********************************TEST DATASETS************************************
 	
	String nomFixterIn1    = "SEP4.csv"; // structure, one record per link and weight
	String nomFixterIn2    = "flags_ALL.csv";  // data, one record per node

  	//  *** Obtener los datos del fichero de input ****/
    
  	ret = leer_datos_de_casos(UsersT, Attributes, AttrDs, nomFixterIn1, nomFixterIn2, user2, UsersTF);
     
    //corr = Correlation.Correlationp(v1, v2);
    //System.out.println("corr= "+corr);
  	
return ret;
} //fin de RVp

public static int writeUserGraph(Hashtable Users){
	
	String nomFixterOut1    = "user_sg2_stucture.csv"; // all links between users
	String ubicFixter = "";
	String nomUbicFixterOut1 = ubicFixter+nomFixterOut1;
	Enumeration en1 = Users.keys();
	User   nw;
	int    user1=0, i=0;

	try{
		FileWriter outputFile1 = new FileWriter(nomUbicFixterOut1);
		BufferedWriter output1= new BufferedWriter(outputFile1);
	
	while (en1.hasMoreElements() /*&& written < linesd*/){
		nw = (User)Users.get((Integer)en1.nextElement());
		int iduser = nw.getUser();
		
		//if(iduser != 2727 && iduser != 4292)		
		if(iduser != 2038 && iduser != 16 && iduser != 971)
		if (nw.neighid.size() > 0)
			for (i = 0; i < nw.neighid.size(); i++)
			{
				//if(nw.getneighid(i) != 2727 && nw.getneighid(i) != 4292)
				if(nw.getneighid(i) != 2038 && nw.getneighid(i) != 16 && nw.getneighid(i) != 971)
				{
					output1.write(iduser+";"+nw.getneighid(i));
					output1.newLine();
				}
			}
			output1.flush();
	} //ewhile
	} //etry
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	return 0;
}

public static int writeAttributeGraph(Hashtable Users){
	
	String nomFixterOut1    = "attribute_sg1_stucture.csv"; // all links between users and attributes
	String ubicFixter = "";
	String nomUbicFixterOut1 = ubicFixter+nomFixterOut1;
	Enumeration en1 = Users.keys();
	User   nw;
	int    user1=0, i=0;

	try{
		FileWriter outputFile1 = new FileWriter(nomUbicFixterOut1);
		BufferedWriter output1= new BufferedWriter(outputFile1);
	
	while (en1.hasMoreElements() /*&& written < linesd*/){
		nw = (User)Users.get((Integer)en1.nextElement());
		int iduser       = nw.getUser();
		
		if(iduser != 2727 && iduser != 4292)		
		//if(iduser != 2038 && iduser != 16 && iduser != 971)
		if (nw.attrid.size() > 0)
			for (i = 0; i < nw.attrid.size(); i++)
			{
				if(nw.getattrid(i) != -311 && nw.getattrid(i) != -564)
				//if(nw.getattrid(i) != -74 && nw.getattrid(i) != -61 && nw.getattrid(i) != -27 
				   //&& nw.getattrid(i) != -542 && nw.getattrid(i) != -322)
				{
					output1.write(iduser+";"+nw.getattrid(i));
					output1.newLine();
				}
			}
		output1.flush();
		
	} //ewhile
	} //etry
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	return 0;
}


/********************** Output Calculations in File ***********************/
public static int writeDataUsers(Hashtable Users){
	
	String nomFixterOut1    = "data_user_data_stucture.csv"; // structure, one record per link and weight
	String nomFixterOut2    = "stats_user_data_stucture.csv";  // data, one record per node
	String ubicFixter = "";
	String nomUbicFixterOut1 = ubicFixter+nomFixterOut1;
	String nomUbicFixterOut2 = ubicFixter+nomFixterOut2;

	Enumeration en1 = Users.keys();

	String str="";
	User   nw, nw2; 
	int    user1=0, user2=0, i=0, j=0, k=0;

	try{
		FileWriter outputFile1 = new FileWriter(nomUbicFixterOut1);
		BufferedWriter output1= new BufferedWriter(outputFile1);
		
		FileWriter outputFile2 = new FileWriter(nomUbicFixterOut2);
		BufferedWriter output2= new BufferedWriter(outputFile2);
	
	while (en1.hasMoreElements() /*&& written < linesd*/){
		nw = (User)Users.get((Integer)en1.nextElement());

		int iduser       = nw.getUser();
		
		int numneig = nw.neighid.size();
		int numattr = nw.attrid.size();
		
		double persameAttrib  = nw.getSameAttrib();
		double avgsameAttribs = nw.getAvgSameAttribs();

		output2.write(iduser+";"+numneig+";"+numattr+";"+persameAttrib+";"+avgsameAttribs);
		
		if (nw.neighid.size() > 0)
			for (i = 0; i < nw.neighid.size(); i++)
				output2.write(";"+nw.getneighid(i));
		
		
		if (nw.attrid.size() > 0)
			for (i = 0; i < nw.attrid.size(); i++)
				output2.write(";"+nw.getattrid(i));
		

	
		output2.newLine();output2.flush();
		
		
		// now for the second file
		output1.write(iduser+"");
		
		// attribute flags
		if (nw.attribhf.size() > 0)
			for (i = 0; i < nw.attribhf.size(); i++)
				output1.write(";"+nw.getattribhf(i));
		
		if (nw.nidsflags.size() > 0)
			for (i = 0; i < nw.nidsflags.size(); i++)
				output1.write(";"+nw.nidsflags.get(i));
		
		// five metrics
		if (nw.cnd.size() > 0)
			for (i = 0; i < nw.cnd.size(); i++)
				output1.write(";"+nw.getcnd(i));

		if (nw.cnsand.size() > 0)
			for (i = 0; i < nw.cnsand.size(); i++)
				output1.write(";"+nw.getcnsand(i));

		if (nw.aad.size() > 0)
			for (i = 0; i < nw.aad.size(); i++)
				output1.write(";"+nw.getaad(i));

		if (nw.aasand.size() > 0)
			for (i = 0; i < nw.aasand.size(); i++)
				output1.write(";"+nw.getaasand(i));

		if (nw.aasanstaticd.size() > 0)
			for (i = 0; i < nw.aasanstaticd.size(); i++)
				output1.write(";"+nw.getaasanstaticd(i));

		
		output1.newLine();output1.flush();
		
	} //ewhile
	} //etry
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	return 0;
}

public static int writeInstancesforWeka(Hashtable Users, Hashtable UsersTF, String nomFixterOut1){
	
	String ubicFixter = "";
	String nomUbicFixterOut1 = ubicFixter+nomFixterOut1;

	Enumeration en1 = Users.keys();

	String str="";
	User   nw, nw2; 
	int    user1=0, user2=0, i=0, j=0, k=0;

	try{
		FileWriter outputFile1 = new FileWriter(nomUbicFixterOut1);
		BufferedWriter output1= new BufferedWriter(outputFile1);
		
		output1.write("iduser,"+
				"cn0,"+"cn1,"+"cn2,"+"cn3,"+"cn4,"+
		        "cnsan0,"+"cnsan1,"+"cnsan2,"+"cnsan3,"+"cnsan4,"+
		        "aa0,"+"aa1,"+"aa2,"+"aa3,"+"aa4,"+
		        "topid0,"+"topid1,"+"topid2,"+"topid3,"+"topid4,"+
		        "topatt0,"+"topatt1");
		output1.newLine();output1.flush();
		
        String att0  = " "; String att1  = " "; String att2  = " "; String att3  = " "; String att4  = " "; 
        String att5  = " "; String att6  = " "; String att7  = " "; String att8  = " "; String att9  = " "; 
        String att10 = " "; String att11 = " ";String att12 = " "; String att13 = " "; String att14 = " "; 
        String att15 = " "; String att16 = " "; String att17 = " ";String att18 = " "; String att19 = " ";  
        String att20 = " "; String att21 = " ";
	
	while (en1.hasMoreElements() /*&& written < linesd*/){
		nw = (User)Users.get((Integer)en1.nextElement());

		int iduser       = nw.getUser();
		
		if (UsersTF.containsKey(iduser)==true)
		{
			nw2 = (User)UsersTF.get(iduser);
		
			// metric 1
			att0  = nw.getcnd(0);  att1  = nw.getcnd(1);  att2  = nw.getcnd(2);  
			att3  = nw.getcnd(3);  att4  = nw.getcnd(4);
		
			// metric 2
			att5 = nw.getcnsand(0); att6 = nw.getcnsand(1); att7 = nw.getcnsand(2); 
			att8 = nw.getcnsand(3); att9 = nw.getcnsand(4);
		
			// metric 3
			att10 = nw.getaad(0);  att11 = nw.getaad(1); att12 = nw.getaad(2);  
			att13 = nw.getaad(3);  att14 = nw.getaad(4);
		
			// flags for top users by degree
			att15 = nw2.getnidsflags(0); att16 = nw2.getnidsflags(1); att17 = nw2.getnidsflags(2); 
			att18 = nw2.getnidsflags(3); att19 = nw2.getnidsflags(4);
		
			// flags for top attribs
			att20 = nw2.getattribhf(0); att21 = nw2.getattribhf(1);

			output1.write(iduser+"");
			output1.write(","+att0+","+att1+","+att2+","+att3+","+att4);
			output1.write(","+att5+","+att6+","+att7+","+att8+","+att9);
			output1.write(","+att10+","+att11+","+att12+","+att13+","+att14);
			output1.write(","+att15+","+att16+","+att17+","+att18+","+att19);
			output1.write(","+att20+","+att21);
		
			output1.newLine();output1.flush();
			
		} // only write out if one of flag ids
		
	} //ewhile
	} //etry
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	return 0;
}


public static int writeDataAttributes(Hashtable Attributes){
	
	String nomFixterOut1    = "check_attributes.csv"; 
	String ubicFixter = "";
	String nomUbicFixterOut1 = ubicFixter+nomFixterOut1;

	Enumeration en1 = Attributes.keys();

	String str="";
	Attribute   aw, aw2; 
	int    attr1=0, attr2=0, i=0, j=0, k=0;

	try{
		FileWriter outputFile1 = new FileWriter(nomUbicFixterOut1);
		BufferedWriter output1= new BufferedWriter(outputFile1);
		
	
	while (en1.hasMoreElements()){
		aw = (Attribute)Attributes.get((Integer)en1.nextElement());

		int idattr   = aw.getAttrib();
		int numattrs = aw.attribids.size();
		int numusers = aw.userids.size();

		output1.write(idattr+";"+numattrs+";"+numusers);
		
		if (numattrs > 0)
			for (i = 0; i < aw.attribids.size(); i++)
				output1.write(";-"+aw.getattribids(i));
		
		
		if (numusers > 0)
			for (i = 0; i < aw.userids.size(); i++)
				output1.write(";"+aw.getuserids(i));
	
		output1.newLine();output1.flush();
		
	} //ewhile
	} //etry
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nsomething went wrong writing the output file");
		return 1;
	}
	return 0;
}

public static int processAttributes(Hashtable Users, Hashtable Attributes){
	

	Enumeration en1 = Users.keys();

	String str="";
	User   nw, nw2; 
	int    user1=0, user2=0, i=0, j=0, k=0;

	while (en1.hasMoreElements() /*&& written < linesd*/){
		nw = (User)Users.get((Integer)en1.nextElement());

		int iduser       = nw.getUser();
		
		int numneig = nw.neighid.size();
		int numattr = nw.attrid.size();
		
		if (numattr > 0)
			for (i = 0; i < numattr; i++)
			{
				int idattr = nw.getattrid(i);
				if ((Attributes.containsKey(idattr)) == false){ // its a new attribute
					Attribute aw = new Attribute(idattr);
					
					for (j = 0; j < numattr; j++) // write attribute i's neighbour attributes into adjacency list
					{
						int idattr2 = nw.getattrid(j);
						if (idattr != idattr2)
							aw.loadattribids(idattr2);
					}
					aw.loaduserids(iduser);
					Attributes.put(idattr, aw);
				} //eif
				else // attribute already exists so its an update to add adjacent attribs
				{
					Attribute aw = (Attribute)Attributes.get(idattr);
					
					for (j = 0; j < numattr; j++) // write attribute i's neighbour attributes into adjacency list
					{
						int idattr2 = nw.getattrid(j);
						if (idattr != idattr2)
							aw.loadattribids(idattr2); // method checks its not already in vector
					}
					aw.loaduserids(iduser);

					Attributes.remove(aw.getAttrib());
					Attributes.put(aw.getAttrib(),aw);
	
				}
	
			} //efor
		
	} //ewhile

	return 0;
}

/********************** Get Max Degree ***********************/
public static int getMaxDegree(Hashtable Users){
	Enumeration en1 = Users.keys();
	
	String str=""; int degree_user2=0;
	int maxDegree=0, degree=0;
	User nw,nw2;
	int user1=0,user2=0,i=0;
	
	while (en1.hasMoreElements()){
		nw = (User)Users.get((Integer)en1.nextElement());
		user1 = nw.getUser();
		degree = nw.friends.size();
		if (degree > maxDegree)
			maxDegree = degree;
	} //ewhile
	return maxDegree;
}

//******************************************************************** */
//Función que lee los datos de casos del fichero "nomFixter" y los     */
//mete en la estructura 'datosD'									   							*/
//******************************************************************** */
public static double leer_datos_de_casos(Hashtable UsersT, Hashtable Attributes, Hashtable AttrDs, String nomFixterIn1, 
										 String nomFixterIn2, int user2, Hashtable UsersTF)
{
String ubicFixter = "";
String nomUbicFixterIn1 = ubicFixter+nomFixterIn1;
String nomUbicFixterIn2 = ubicFixter+nomFixterIn2;
String delim = ";";
String isNull = "";
String temp;
Object[] data1=null;
Object[] data2=null;
int i, j, k, kk;

data1  = new Object[99+1];
data2  = new Object[22+1];
String valVar;

String[] s1; String s2;

int len=0, kount=0;
char oldChar='.';
char newChar=',';

long ii=0;
int u1; int u2; double t1 ; double t2;
String delimiter=";";

Integer oneUser=0;
Integer userActual=0;

int maxid=0;
System.out.println("WELCOME TO READ TOPOLOGY");

System.out.println("READ TOPOLOGY STEP1");

	try{
		FileReader inputFile1 = new FileReader(nomUbicFixterIn1);
		BufferedReader input1= new BufferedReader(inputFile1);
		// In1    = "SEP4.csv"; // structure, one record per link and weight
		// In2    = "ATTRIBDATA.csv";  // data, one record per node
		
		FileReader inputFile2 = new FileReader(nomUbicFixterIn2);
		BufferedReader input2= new BufferedReader(inputFile2);

		dataFile df1= new dataFile();
		dataFile df2= new dataFile();
		
		if (inputFile1 == null || inputFile1 == null)
		{
			System.out.println("\nCant open the input files\n");
			return 1;
		}

		i=0;
		int iduser=0, numneighbors=0, numattributes=0;
		
		//System.out.println("\n****GET ALL neighbors FOR ALL USERS*****\n");
	
			int numnodes = UsersT.size();
	
			int[] atIds;
			atIds = new int[2];
			
			String stemp = "";
			String sy = "YES";
	
			
			/*********** STEP 1, read the users, their social neighbors and attribute neighbors into the user data structure **********/
			
			
			temp= input1.readLine();
			if (temp==null) // end of the file....
				;
			else
			{
				// Process linea sencer amb RegEx per a extreure els variables 
				// d'un cas.  
				df1.processarLineaV(temp, delim, data1); 
					
				stemp = (String)data1[0];
				iduser = Integer.parseInt(stemp);
				
				kk=0; int lines=0;

				while ( kk>=0) // for each user			
				{
					stemp = (String)data1[1];
					numneighbors = Integer.parseInt(stemp);

					stemp = (String)data1[2];
					numattributes = Integer.parseInt(stemp);
				


					//if ((UsersT.containsKey(iduser)) == false && (iduser != 2727) && (iduser != 4292)){   // SG1
					//if ((UsersT.containsKey(iduser)) == false && (iduser != 2038) && (iduser != 16) && (iduser != 971)){   //SG2
				    if ((UsersT.containsKey(iduser)) == false){   // ALL
							
						User nw = new User(iduser);
					
						for (j = 3; (j < numneighbors + 3) && (stemp != null); j++)
						{
							stemp = (String)data1[j];
							int idneighbor = Integer.parseInt(stemp);

							//if ((idneighbor != 2727) && (idneighbor != 4292)) //SG1
							//if ((idneighbor != 2038) && (idneighbor != 16)&& (idneighbor != 971)) // SG2
							if (1==1) //ALL
							{
								nw.loadneighid(idneighbor);
								nw.afegirFriend(idneighbor);
								}
						}
						
						for (j = numneighbors + 3; (j < numneighbors + numattributes + 3) && (stemp != null); j++)
						{
							stemp = (String)data1[j];
							int idattribute = Integer.parseInt(stemp);

							//if (idattribute != -27)  //SG1
							//if (idattribute != -74) //SG2
							if (1==1) //ALL
								nw.loadattrid(idattribute);
						}
			
						UsersT.put(iduser, nw);
						++lines;

					} 

					temp= input1.readLine();
					if (temp==null) // end of the file....
						kk=-10;
					else
					{
						df1.processarLineaV(temp, delim, data1); 
						stemp = (String)data1[0];
						iduser = Integer.parseInt(stemp);
					}
				} // ewhile. ok, got all friends for all users...
				
				System.out.println("\n****Number of users inserted into hashtable :"+lines);

		
			System.out.println("\n****END OF PROCESSING*****\n");
			inputFile1.close();
			input1.close();
			
			System.out.println("FAREWELL READ TOPOLOGY");

			
		} //end of if for first line of input file read
			
			/*********** STEP 2, read the flags for the top users and attributes **********/
			
			
			temp= input2.readLine();
			if (temp==null) // end of the file....
				;
			else
			{
				// Process linea sencer amb RegEx per a extreure els variables 
				// d'un cas.  
				df2.processarLinea(temp, delim, data2,8); 
					
				stemp = (String)data2[0];
				iduser = Integer.parseInt(stemp);
				
				kk=0; int lines=0;

				while ( kk>=0) // for each user			
				{
				
					stemp = (String)data2[1];
					String topidflag0 =stemp;
				
					stemp = (String)data2[2];
					String topidflag1 =stemp;
				
					stemp = (String)data2[3];
					String topidflag2 =stemp;
				
					stemp = (String)data2[4];
					String topidflag3 =stemp;
				
					stemp = (String)data2[5];
					String topidflag4 =stemp;
				
					stemp = (String)data2[6];
					String topattflag0 =stemp;
				
					stemp = (String)data2[7];
					String topattflag1 =stemp;

					if ((UsersTF.containsKey(iduser)) == false ){
							
						User nw = new User(iduser);
				    	int topid = 0;			nw.nids.add(topid);		nw.nidsflags.add(topidflag0);
	        			topid = 1;	 				nw.nids.add(topid);		nw.nidsflags.add(topidflag1);
	        			topid = 2;	 				nw.nids.add(topid);		nw.nidsflags.add(topidflag2);
	        			topid = 3;	 				nw.nids.add(topid);		nw.nidsflags.add(topidflag3);
	        			topid = 4;	 				nw.nids.add(topid);		nw.nidsflags.add(topidflag4);
				    	int topatt = 0;			nw.attribhfid.add(topatt);	nw.attribhf.add(topattflag0);
				    	topatt = 1;				nw.attribhfid.add(topatt);	nw.attribhf.add(topattflag1);
						
						UsersTF.put(iduser, nw);
						++lines;
					} 

					temp= input2.readLine();
					if (temp==null) // end of the file....
						kk=-10;
					else
					{
						df2.processarLinea(temp, delim, data2,8); 
						stemp = (String)data2[0];
						iduser = Integer.parseInt(stemp);
					}
				} // ewhile. ok, got all flags for all users...
				
				System.out.println("\n****Number of users inserted into hashtable :"+lines);

		
			System.out.println("\n****END OF PROCESSING FLAGS*****\n");
			inputFile1.close();
			input1.close();
			
			System.out.println("FAREWELL READ TOPOLOGY");

			
		} //end of if for first line of input file read		
			
		
	}
	catch(IOException ioe){
		ioe.printStackTrace();
		System.out.println("\nHa ocurrido un problema al llegir el fixter");
		return 1;
	}
	return 0;
} //fin de funcion 'leer_datos_de_casos'

} // end of public class RVp