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

public class createAttributesGEX{

public static Instances createAttributesGEX(Hashtable Users, Instance theUser, Instances theData, 
		                                  int indexOne, int indexTwo, int indexThree, String userValuesAsString, String collapsedAtts) 
{

	      FastVector attValsCollapsed;

          attValsCollapsed = new FastVector();
           
          FastVector attInfo = new FastVector(theData.numAttributes()-2);
          
          for (int i=0; i<theData.numAttributes()-1  ; i++)
          {
        	  if ( (i != indexOne) && (i != indexTwo) &&(i != indexThree) ) // not one of attribs to be collapsed
        	  {
                 Attribute anAttribute = theData.attribute(i);
                 attInfo.addElement(anAttribute);
        	  }
          }
          
          attValsCollapsed.addElement("DIFFERENT");
          attValsCollapsed.addElement(userValuesAsString);
          
          attInfo.addElement(new Attribute(collapsedAtts, attValsCollapsed));
          
          Attribute cAttribute = theData.attribute(theData.numAttributes()-1);
          
          attInfo.addElement(cAttribute); // this is the class value
          
          Instances newInstances = new Instances("theNeWSet",attInfo,0);
          newInstances.setClassIndex(newInstances.numAttributes() - 1);
          
          System.out.println("***************HOLA1***************");
          
          for(int i=0;i<attInfo.size();++i)
        	  System.out.println("attribute: "+i+" "+attInfo.elementAt(i));
          
          System.out.println("***************HOLA2***************");
       
          return newInstances;
    }  //fin de createAttributesGEX


} //fin de createAttributesGEX