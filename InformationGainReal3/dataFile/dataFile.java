// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

//**************process whole line with  regex to extract variables*****************
package dataFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.*;
import java.io.*;
import java.lang.CharSequence;

public class dataFile {
	private static final String DELIMITADORS = ";";
	private Pattern patro = Pattern.compile( "(\\d+(,\\d+)*(\\.\\d+)?)|([A-Z]\\w*)" );
   
	public void processarLinea(String text, String delim, Object[] data, int j){
		String oneToken;
		int i=0;
		StringTokenizer tokens = new StringTokenizer(text);
		while(tokens.hasMoreTokens() && i < j){
			oneToken = tokens.nextToken(delim);
			data[i] = oneToken;
			i++;
		} // fin de while
	} // fin de processarLinea
	
	public void processarLineaV(String text, String delim, Object[] data){
		String oneToken;
		int i=0;
		int iduser=0, numneighbors=0, numattributes=0;
		
		StringTokenizer tokens = new StringTokenizer(text);
		
		oneToken = tokens.nextToken(delim);
		iduser = Integer.parseInt(oneToken);
		data[0] = oneToken;
		
		oneToken = tokens.nextToken(delim);
		numneighbors = Integer.parseInt(oneToken);
		data[1] = oneToken;
		
		oneToken = tokens.nextToken(delim);
		numattributes = Integer.parseInt(oneToken);
		data[2] = oneToken;
		
		i=3;
		while(tokens.hasMoreTokens() && i < numneighbors+3){
			oneToken = tokens.nextToken(delim);
			data[i] = oneToken;
			i++;
		} // fin de while
		
		while(tokens.hasMoreTokens() && i < numneighbors+numattributes+3){
			oneToken = tokens.nextToken(delim);
			data[i] = oneToken;
			i++;
		} // fin de while

	} // fin de processarLineaV
				
} // end of public class processarText