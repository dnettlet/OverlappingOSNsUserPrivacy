// Copyright (C) 2018  Vladimir Estivill
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.


import java.util.LinkedList;
import weka.core.Instance;

/**
 *
 * @author vlad
 */
public class DecisionTreePath {
    
    private LinkedList<DecisionTreeQuestion> theList;
            int position;
            
            
    private static DecisionTreePath insertionSortList(DecisionTreePath head) {
            DecisionTreePath result = new DecisionTreePath();
	    if (head.theList.isEmpty()) return result;
            
            head.reset();

            while (head.hasNext())
            {
                DecisionTreeQuestion toInsert = head.nextQuestion();
                // merge the quesiton into the sorted list
                result.reset();
                DecisionTreePath merge = new DecisionTreePath();
                
                boolean foundPosition=false;
                while (result.hasNext()&& ! foundPosition)
                {   DecisionTreeQuestion toCompare= result.nextQuestion();
                    if (toInsert.get_indexAttribute() <  toCompare.get_indexAttribute() )
                    { merge.add(toInsert);
                     foundPosition=true;
                    }  
                    
                    merge.add(toCompare);
                    result.moveToNext();
                 }
    
                if (foundPosition) /* copy the rest of result in merge */
                    {   
                        while (result.hasNext() )
                                {  DecisionTreeQuestion aTail = result.nextQuestion();
                                    merge.add(aTail);
                                    result.moveToNext();
                                 }
                    }
                else // append to the merge
                  merge.add(toInsert);  
                
                // copy the merge into the result
                result = new DecisionTreePath(merge);
                head.moveToNext();
            }
        return result;
	}
    
    public
    DecisionTreePath(){
        theList = new LinkedList();
        position=0;
    }
    
     /* copy constructor */
    DecisionTreePath(DecisionTreePath d){
        theList = new LinkedList();
        position=0;
        d.reset();
        while (d.hasNext())
        {
            DecisionTreeQuestion aQuestion= d.nextQuestion();
            theList.add(aQuestion);
            d.moveToNext();
        }
    }
    
     
    DecisionTreePath cannocicalForm(){
       return insertionSortList(this);
    }
    
    // assumes both inputs ar ein cannoncial form
    boolean hasEqualCannonicalForm( DecisionTreePath secondPath)
    {   boolean temp=true;
    
        this.reset();
        secondPath.reset();
        
        while (this.hasNext() && secondPath.hasNext() )
        {  DecisionTreeQuestion fQuestion= this.nextQuestion();
           DecisionTreeQuestion sQuestion= secondPath.nextQuestion();
           
           if (fQuestion.get_indexAttribute() != sQuestion.get_indexAttribute()) return false;
           if (! fQuestion.get_nominalValue().equals( sQuestion.get_nominalValue())) return false;
           
           this.moveToNext();
           secondPath.moveToNext();
            
        }
        
        if (this.hasNext()) return false;
        if (secondPath.hasNext()) return false;
        
        return temp;
    }
    
    void add (DecisionTreeQuestion t) {
        theList.add(t);
    }
    
    void reset() { position=0;}
    
    DecisionTreeQuestion nextQuestion(){
        return theList.get(position);
    }
    
    boolean hasNext(){
        return position<theList.size();
    }
    
     void moveToNext(){
        if( position<theList.size()) position++;
    }
     
      String toPrintAsString()
     {
         String s="";
         
         for (int i=0; i< theList.size(); i++)
             s+= theList.get(i).toPrintAsString();
         
         return s;
     }
      
      int pathLength()
      {
          return theList.size();
      }
      
      boolean instanceSatisfiesPath(Instance i)
      {
          this.reset();
          
          while (this.hasNext())
          {  DecisionTreeQuestion theQuestion= this.nextQuestion();
             if (! theQuestion.get_nominalValue().equals(  i.stringValue(  theQuestion.get_indexAttribute()   )   )     )
                return false;              
             this.moveToNext(); 
          }
        return true;
      }
      
      boolean isAttributeInPath(int i) {
          this.reset();
          
          while (this.hasNext())
          {  DecisionTreeQuestion theQuestion= this.nextQuestion();
             if ( theQuestion.get_indexAttribute() ==i    )
                return true;              
             this.moveToNext(); 
          }
        return false;
          
      
      }
    
}
