// Copyright (C) 2018  Vladimir Estivill
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

/**
 *
 * @author vlad
 */
public class DecisionTreeQuestion {
    
    private
             String nominalValue;
             int indexAttribute;
             
    public
             DecisionTreeQuestion(String ANominalValue, int AnIndexAttribute ){
                    nominalValue=ANominalValue;
                    indexAttribute=AnIndexAttribute;
             }
             
             String get_nominalValue()
             {
                 return nominalValue;
             }
             
              int get_indexAttribute()
             {
                 return indexAttribute;
             }
              
       String toPrintAsString()
     {
         String s="";

             s+= "<"+nominalValue+","+indexAttribute+">";
         
         return s;
     }

}
