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


public class SANPreprocData {
    
   
    public static Hashtable Users      = new Hashtable();
    public static Hashtable UsersT     = new Hashtable();
    public static Hashtable UsersTF    = new Hashtable();
    public static Hashtable Attributes = new Hashtable();
    public static Hashtable AttrDs     = new Hashtable();
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, Exception {

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
    
    //readData.writeDataDictionary(AttrDs);
       
    //Instances data = createAttributesSG1.createAttributesSG1(Users);
    //Instances data = createAttributesSG2.createAttributesSG2(Users);
    //Instances data = createAttributesGALL.createAttributesGALL(Users);
    
    }// main  
}
