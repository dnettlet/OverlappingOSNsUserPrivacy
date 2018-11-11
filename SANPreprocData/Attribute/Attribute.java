// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package Attribute;
import java.util.*;

public class Attribute {
	private Integer Attribute;
	
	public Vector attribids    = new Vector (); // attribute node neighbors
	public Vector userids      = new Vector (); // social node neighbors
	
	public Attribute(Integer attrib){
		setAttrib(attrib);
	}
	public Integer getAttrib() {
		return Attribute;
	}
	public void setAttrib(Integer attrib) {
		Attribute = attrib;
	}
	
 	
	public void loadattribids(int attribid){
		if (attribids.contains(attribid)== false)
			this.attribids.add(attribid);
	}
	
	public void loaduserids(int userid){
		if (userids.contains(userid)== false)
			this.userids.add(userid);
	}
	
	public Integer getuserids(Integer ixneighid){
		int val=0;
		if (ixneighid < this.userids.size())
			val = (Integer)this.userids.get(ixneighid);
		return val;
	}
	
	public Integer getattribids(Integer ixattribid){
		int val=0;
		if (ixattribid < this.attribids.size())
			val = (Integer)this.attribids.get(ixattribid);
		return val;
	}
	
}
