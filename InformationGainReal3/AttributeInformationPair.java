// Copyright (C) 2018  Vladimir Estivill
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

/**
 *
 * @author vlad
 */
public class AttributeInformationPair {
    
    private

    int attributeID;
    double information;
    
    public
             AttributeInformationPair(int AnattributeID, double theInformation ){
                    attributeID=AnattributeID;
                    information=theInformation;
             }
             
             int get_attributeID()
             {
                 return attributeID;
             }
             
              double get_information()
             {
                 return information;
             }
              
                           
             void set_attributeID(int att)
             {
                  attributeID=att;
             }
             
              void set_information(double info)
             {
                  information=info;
             }
              
              
            static void insertionSortList(AttributeInformationPair [] theArray)
              {
                  if (theArray.length<=1) return;
                  
                  for (int index=1; index<theArray.length; index++) // we place item in position index into the sorted order
                  { int previousIndex=index-1;
                    int currentIndex=index;
                    
                  while ((previousIndex>=0) && (theArray[previousIndex].get_information() < theArray[currentIndex].get_information() ))
                  { // swap the data in previousIndex and currentIndex
                      int tempAttribue= theArray[previousIndex].get_attributeID();
                      double tempInfo=theArray[previousIndex].get_information();
                      theArray[previousIndex].set_attributeID(theArray[currentIndex].get_attributeID());
                      theArray[previousIndex].set_information(theArray[currentIndex].get_information());
                      theArray[currentIndex].set_attributeID(tempAttribue);
                      theArray[currentIndex].set_information(tempInfo);
                      currentIndex--; previousIndex--;
                      
                      
                  }// while
                      
                  }// for of the index going up
              }
    
    
    
}
