// Copyright (C) 2018  Vladimir Estivill
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

import weka.core.Instance;
import weka.core.Instances;

/**
 *
 * @author vlad
 */
public class SensitivityRule {
    
        private
            double confidence;
            double support;
            DecisionTreePath theRuleCannonicalForm;
            
            
            public double sensitivity()
            {
                return confidence+support;
            }
            
                 /* onstructor */
    SensitivityRule(DecisionTreePath d){
        theRuleCannonicalForm= new DecisionTreePath(d);

    }
    
    DecisionTreePath thePath()
    {
        return theRuleCannonicalForm;
    }
    
    void setConfidence(Instances theDataSet, Instance userI){
        
        double satisfyCount=0.0;
        double agreeCount=0.0;
        
        int index = theDataSet.classIndex();
        
        for (int i=0; i< theDataSet.numInstances(); i++)
        {  Instance currentInstacne = theDataSet.instance(i);
        
           if (theRuleCannonicalForm.instanceSatisfiesPath(currentInstacne))
                   {
                       satisfyCount+=1.0;
                       if (currentInstacne.stringValue(index).equals(userI.stringValue(index)))
                                                  agreeCount+=1.0;
                   }
        }   
        
        if (satisfyCount>0.0)
        confidence=agreeCount/satisfyCount;
        else confidence=0;
    }
    
    void setSupport(Instances theDataSet, Instance userI){
        
        double supportCount=0.0;
        
        int index = theDataSet.classIndex();
        
        for (int i=0; i< theDataSet.numInstances(); i++)
        {  Instance currentInstacne = theDataSet.instance(i);
        
           if (theRuleCannonicalForm.instanceSatisfiesPath(currentInstacne))
            supportCount+=1.0;
        }            
        support=supportCount/(theDataSet.numInstances()*1.0);
    }
    
}
