// Copyright (C) 2018  Vladimir Estivill
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

import java.util.LinkedList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;

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

/**
 *
 * @author vlad
 */
public class Heuristic {
    
   	  static double[][]  rankByInformationGain(Instances theSet) throws Exception
	  {
	       AttributeSelection attributeSelection = new  AttributeSelection();
	       InfoGainAttributeEval infoGainAttributeEval = new InfoGainAttributeEval();
	 
	       attributeSelection.setEvaluator(infoGainAttributeEval);
	 
	       Ranker ranker = new Ranker(); 
	       ranker.setNumToSelect(theSet.numAttributes() - 1); 

	       attributeSelection.setSearch(ranker);
	       
	       try {
	 
	       attributeSelection.SelectAttributes(theSet);
	       
	       double[][] rankingResults= attributeSelection.rankedAttributes();
	       
	       return rankingResults;
	       
	       } catch (Exception ex) {
	           Logger.getLogger(InformationGainReal3.class.getName()).log(Level.SEVERE, null, ex);
	       }
		return null;
	      
	 
	      //System.out.println("attributeSelection:"+attributeSelection.toResultsString());
	 
	      //double[][] rankingResults= attributeSelection.rankedAttributes();
	      
	      //return rankingResults;
	      
	  }
   	  
   	 static Instances filterOutPositive(Instances theSet,  DecisionTreePath aDecisionTreePath )
     {
           Instances yesInstances = new Instances(theSet);
           
           aDecisionTreePath.reset();
           
           while (aDecisionTreePath.hasNext())
           {   DecisionTreeQuestion theQuesiton=aDecisionTreePath.nextQuestion();
               aDecisionTreePath.moveToNext();
               for (int i=0; i< yesInstances.numInstances(); )
               {
                 Instance oneInstace =yesInstances.instance(i);
                  if (theQuesiton.get_nominalValue().equals (oneInstace.stringValue(theQuesiton.get_indexAttribute())))
                   { i++; 
                    //System.out.println("Instace :"+oneInstace);
                   }
                  else
                   yesInstances.delete(i);
                }
           }// while
           return yesInstances;
     }
    
   	 
     
   	static Instances filterOutNegative(Instances theSet,  DecisionTreePath aDecisionTreePath )
   	{
   	 
   	          Instances negativeInstances = newSetOfInstacnes(theSet);
   	          
   	          aDecisionTreePath.reset();
   	          
   	     
   	         for (int i=0; i< theSet.numInstances();i++ )
   	          {
   	                Instance oneInstace =theSet.instance(i);
   	                
   	                 aDecisionTreePath.reset();
   	                 boolean placed=false;
   	                 while (!placed && aDecisionTreePath.hasNext())
   	                    {   DecisionTreeQuestion theQuesiton=aDecisionTreePath.nextQuestion();
   	                           aDecisionTreePath.moveToNext();
   	                           if (!(theQuesiton.get_nominalValue().equals (oneInstace.stringValue(theQuesiton.get_indexAttribute()))))
   	                           {    placed=true;
   	                                negativeInstances.add(oneInstace);
   	                           }
   	                   }// while
   	          }// for
   	          return negativeInstances;
   	    }
   	
   	/* return an emptys et of instacnes with the same attributes as the input instace*/
    static Instances newSetOfInstacnes(Instances theSet)
    {
          FastVector attInfo = new FastVector(theSet.numAttributes());
          
          for (int i=0; i<theSet.numAttributes()  ; i++)
          {
             Attribute anAttribute = theSet.attribute(i);
             attInfo.addElement(anAttribute);
          }
          
          //attInfo.addElement(theSet.classAttribute());
          Instances newInstances = new Instances("theNeWSet",attInfo,theSet.numInstances());
          newInstances.setClassIndex(newInstances.numAttributes() - 1);
          
          return newInstances;
    }
    
    static boolean isDifferentRule(LinkedList foundRules, DecisionTreePath aRule ) {
        
        for (int i=0; i<foundRules.size(); i++)
        {   DecisionTreePath aDecisionTreePath= (DecisionTreePath) foundRules.get(i);
            if (aRule.hasEqualCannonicalForm (aDecisionTreePath)) return false;
        }
        
        return true;
        
    }
		
	/*
     * Given an user, and a size, this return in the arrays the top ranking attributes to conceeal by cummualtive and by total
     * heuristics wuth their values
    */
	
	
    static boolean Heuristic(Instance theUser, Instances theData, 
            AttributeInformationPair [] theCummulativeArray, AttributeInformationPair [] theTotalArray, double thresholdBeta, int ruleLength)
    {
         /* RANKING OF ATTRIBUTES BY INFORMATION GAIN */
        double [][]   rankingResults;
        try {
          rankingResults = rankByInformationGain(theData);

          if (rankingResults == null)
             return false;
          
          System.out.println("RESULTS INDEX="+rankingResults[0][0]+" MERIT="+rankingResults[0][1]);
          System.out.println("RESULTS INDEX="+rankingResults[1][0]+" MERIT="+rankingResults[1][1]);
          System.out.println("RESULTS INDEX="+rankingResults[2][0]+" MERIT="+rankingResults[2][1]);
       

          Queue processingQueue = new LinkedList();
          LinkedList foundRules = new LinkedList();
          LinkedList rulesWithSensitivity = new LinkedList();
           // Initialy, levle1 (roots of decision trees, whose IG ranking is greater than Beta */
          int index=0;
          while ( (index <rankingResults.length)&& (rankingResults[index][1]>thresholdBeta))
          {   int attributeIndex=(int)Math.round( rankingResults[index][0]);
              DecisionTreePath aDecisionTreePath= new DecisionTreePath();
              String valueForUser=theUser.stringValue(theData.attribute(attributeIndex));
              DecisionTreeQuestion theQuestion = new DecisionTreeQuestion(valueForUser,attributeIndex);
              aDecisionTreePath.add(theQuestion);
              processingQueue.add(aDecisionTreePath);
              index++;
          }
          /* Expand by going a levle depper, but cut it as depth MAXIMUM_RULES_LENGTH, as IG always icnreases with tree depth*/
          while  (!processingQueue.isEmpty())
          {   
              DecisionTreePath aDecisionTreePath= (DecisionTreePath) processingQueue.remove();
              System.out.println("A N A L I Z I N G ****>  "+aDecisionTreePath.toPrintAsString());
           
              if (aDecisionTreePath.pathLength()<ruleLength)
              {
                  Instances yesInstances  = filterOutPositive(theData ,aDecisionTreePath);
                  Instances noInstances  = filterOutNegative(theData ,aDecisionTreePath);
              /* These do not match the user */
                  //System.out.println(aDecisionTreePath.toPrintAsString() +"\nNO Dataset has size:"+noInstances.numInstances()+"\n");
                  //System.out.println(noInstances);
              /*These match the user */
                  //System.out.println(aDecisionTreePath.toPrintAsString() +"\n"+ATTRIBUTE_VALUES[0]+" Dataset has size:"+yesInstances.numInstances()+"\n");
                  //System.out.println("The user="+theUser.toString() );
                  //System.out.println(yesInstances);
           
                  double [][]   rankingYesResults=rankByInformationGain(yesInstances);
                  int theIndex=0;
                  while ( (theIndex <rankingYesResults.length)&& (rankingYesResults[theIndex][1]>thresholdBeta))
                  {   int attributeIndex=(int)Math.round( rankingYesResults[theIndex][0]);
                  //FIXME to construct from a Given path
                      DecisionTreePath anExtendedDecisionTreePath= new DecisionTreePath(aDecisionTreePath);
                      String valueForUser=theUser.stringValue(theData.attribute(attributeIndex));
                      DecisionTreeQuestion theQuestion = new DecisionTreeQuestion(valueForUser,attributeIndex);
                      anExtendedDecisionTreePath.add(theQuestion);
                      processingQueue.add(anExtendedDecisionTreePath);
                      theIndex++;
                  }
                  if (0==theIndex) // the path didn't get extended
                      {   DecisionTreePath inCannonicalFrom = aDecisionTreePath.cannocicalForm();
                          if (isDifferentRule(foundRules,inCannonicalFrom))
                              foundRules.add(inCannonicalFrom);
                      }
                  }
                  else
                  {   DecisionTreePath inCannonicalFrom = aDecisionTreePath.cannocicalForm();
                      if (isDifferentRule(foundRules,inCannonicalFrom))
                          foundRules.add(inCannonicalFrom);
                  }
          } // while
          
          while  (!foundRules.isEmpty())
          {
              DecisionTreePath aDecisionTreePath= (DecisionTreePath) foundRules.remove();
              SensitivityRule  aRule = new SensitivityRule(aDecisionTreePath);
            
              aRule.setSupport(theData,theUser);
              aRule.setConfidence(theData,theUser);
 
              rulesWithSensitivity.add(aRule);
              System.out.println("F O U N D ****>  "+aDecisionTreePath.toPrintAsString()+" Sensitivity: "+aRule.sensitivity());
          }
          
          AttributeInformationPair [] theCummulativeRanking = new  AttributeInformationPair [theData.numAttributes()];
          AttributeInformationPair [] theTotalRanking = new  AttributeInformationPair [theData.numAttributes()];
      ///////////////// Compute the CUMMULATIVE SENSITIVITY and TOTAL_  of ALL ATTRIBUTES
          for (int att=0; att < theData.numAttributes(); att++)
          {   double cummulative=0.0;
              int totalCount=0;
              for (int ruleCoutner=0; ruleCoutner< rulesWithSensitivity.size(); ruleCoutner++ )
              {   SensitivityRule theRule=( SensitivityRule)rulesWithSensitivity.get(ruleCoutner);
                  if (theRule.thePath().isAttributeInPath(att))
                  {   totalCount++;
                      cummulative+=theRule.sensitivity();
                  }
             }
             System.out.println("ATTRIBUTE****>  "+att+" Cummulative Sensitivity: "+cummulative+" Total Count"+totalCount);
             
             AttributeInformationPair newCummulativeInfoPair = new  AttributeInformationPair(att,cummulative);
             AttributeInformationPair newTotalInfoPair = new  AttributeInformationPair(att,totalCount);
             theCummulativeRanking [att]=newCummulativeInfoPair;
             theTotalRanking [att]=newTotalInfoPair;              
          }
        
        // NEED to SORT attributes by CUMULLATIVE and by TOTAL
      System.out.println("ATTRIBUTES SORTED : ");
      AttributeInformationPair.insertionSortList(theCummulativeRanking);
      AttributeInformationPair.insertionSortList(theTotalRanking);
      for (int attIndex=0; attIndex < theCummulativeRanking.length; attIndex++ )
             System.out.println("ATTRIBUTE****>  "+theCummulativeRanking[attIndex].get_attributeID()+" Cummulative Sensitivity: "+theCummulativeRanking[attIndex].get_information() );
      for (int attIndex=0; attIndex < theTotalRanking.length; attIndex++ )
             System.out.println("ATTRIBUTE****>  "+theTotalRanking[attIndex].get_attributeID()+" Total Count: "+theTotalRanking[attIndex].get_information() );
        
      for (int attIndex=0; attIndex < theCummulativeArray.length; attIndex++ )
      {
          theCummulativeArray[attIndex]=theCummulativeRanking[attIndex];
      }
      
      for (int attIndex=0; attIndex < theTotalArray.length; attIndex++ )
      {
          theTotalArray[attIndex]=theTotalRanking[attIndex];
      }
        
        
        
          
       } catch (Exception ex) {
        Logger.getLogger(InformationGainReal3.class.getName()).log(Level.SEVERE, null, ex);
        return false;
    }
       return true;   
    }
    

}
