// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package Attribute;
import java.util.*;

public class AttributeDic {
	private Integer AttributeDic;
	
	public String attribdescrip    = "";
	
	public AttributeDic(Integer attribd){
		setAttribDic(attribd);
	}
	public Integer getAttribDic() {
		return AttributeDic;
	}
	public void setAttribDic(Integer attribd) {
		AttributeDic = attribd;
	}

	public String getAttribDesc() {
		return this.attribdescrip;
	}
	public void setAttribDesc(String attribdesc) {
		 this.attribdescrip = attribdesc;
	}

}
