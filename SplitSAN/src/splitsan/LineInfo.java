/*
 * SplitSAN and LineInfo Copyright (C) 2017-2018 Vladimir Estivill-Castro 
 * This program comes with ABSOLUTELY NO WARRANTY 
 * GNU GENERAL PUBLIC LICENSE V 3 
 *
 */
package splitsan;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vlad
 */

// IMPORTANT: Stores attributes as POSTIVE integers

public class LineInfo {
    
    public int socialUsedID;
    public int numberOfSocialLinks;
    public int numberOfAttributes;
    public List<Integer> socialLinks;
    public List<Integer> attributesLinks;
    
    public LineInfo (int sUsedID, int nOfSocialLinks, int nOfAttributes,
            List<Integer> negihboursList,
            List<Integer> attributeList
                    ){
        socialUsedID= sUsedID;
        numberOfSocialLinks = nOfSocialLinks;
        numberOfAttributes = nOfAttributes;
        socialLinks = new ArrayList<>();
        attributesLinks = new ArrayList<>();
        for (int value :negihboursList ) {
            socialLinks. add(value);
        }
        for (int value :attributeList ) {
            attributesLinks. add(value);
        }
    }
    
    public String lineInfoAsString()
    {
        String s = ""+socialUsedID+" "+numberOfSocialLinks+" "+numberOfAttributes;
        for (int value: socialLinks)
        { s+= " "+value;
        }
        
        for (int value: attributesLinks)
        { s+= " -"+value;
        }
        
        return s;
    }
    
}
