/*
 * SplitSAN and SANreader Copyright (C) 2017-2018 Vladimir Estivill-Castro 
 * This program comes with ABSOLUTELY NO WARRANTY 
 * GNU GENERAL PUBLIC LICENSE V 3 
 *
 */
package splitsan;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.TreeSet;

/**
 *
 * @author vlad
 */
public class SANreader {
    //public int Total_NumSoc_Neighbors;
    public TreeSet<Integer> treeSocial_nodes; 
    public TreeSet<Integer> treeAttribute_nodes; 
    
    private String theFileName;
    private String theCharSet;
    private LineInfo myLineInfo;
    
    private BufferedReader myReader;
    
    private LineAnalizer theLineAnalizer;
    
    
     private static final Path path_to_file(String san_filename) {
     Path path = FileSystems.getDefault().getPath("data", san_filename+".txt");
        //System.err.println("Path:"+path);
        return path;
    }
    
    private static final Charset file_charset(String nameCharset) {
        Charset charset = Charset.forName(nameCharset);
        return charset;
    }
    
    private final void openTheOriginalSAN(String san_filename, String nameCharset) throws IOException
    {
      myReader = Files.newBufferedReader(path_to_file(san_filename), file_charset(nameCharset)); 
    
    }
    
    public void resetFile() throws IOException
    {
       myReader = Files.newBufferedReader(path_to_file(theFileName), file_charset(theCharSet)); 
    }
    
    public boolean analizeNexTLine() throws IOException
    {  String line = null;
        if ((line = myReader.readLine()) != null)
       {   int NodeID = LineAnalizer.getNodeId(line);    
           int NumSoc_Neighbors = LineAnalizer.getNumNeighbour(line);
           int NumAattri_Neighbors=LineAnalizer.getNumAttributes(line);
           List<Integer> list = LineAnalizer.getNeighbours(line);
                for (int i = 0; i < list.size(); i++) {  
                        treeSocial_nodes.add(list.get(i));
		       }//for
          List<Integer> listAt = LineAnalizer.getAttributes(line);
                for (int i = 0; i < listAt.size(); i++) {  
                        treeAttribute_nodes.add(listAt.get(i));
		       }// for
          myLineInfo = new LineInfo(NodeID,NumSoc_Neighbors,NumAattri_Neighbors,list,listAt);
          return true; 
        }
        return false; 
    }
    
    public LineInfo currentLineInformation()
    {
        return myLineInfo;
    }
    
    

    public SANreader(String san_filename, String nameCharset) {
        
        treeSocial_nodes = new TreeSet<Integer>();
        treeAttribute_nodes = new TreeSet<Integer>();
        theFileName = san_filename;
        theCharSet=nameCharset;
        try 
            {   //System.err.println("Before reading");
            openTheOriginalSAN(theFileName, theCharSet);
            
            while (analizeNexTLine()) {
                /* Lets parse the line for negative numbers and add a count */
                //System.err.println("READING "+line);                

            }//while
        }  catch (IOException x) {
                  System.err.format("IOException: %s%n", x);
         }// end of try block//
    }
        


    
}
