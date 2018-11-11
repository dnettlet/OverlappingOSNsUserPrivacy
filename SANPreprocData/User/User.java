// Copyright (C) 2018  David F. Nettleton (dnettlet@gmail.com)
// License: GNU GENERAL PUBLIC LICENSE v3.0   See LICENSE for the full license.

package User;
import java.util.*;

public class User {
	private Integer User;
	public List<Integer> friends = new LinkedList <Integer> ();
	public int    numFriends     = 0;
	public int    numAttribs     = 0;
	public double sameAttrib     = 0;
	public double avgSameAttribs = 0;
	
	public Vector neighid        = new Vector (); // social node neighbour list
	public Vector attrid         = new Vector (); // attribute node neighbour list
	
	
	public Vector attribhfid     = new Vector (); // attribute values flag ids (7)
	public Vector attribhf       = new Vector (); // attribute values flags YES NO (7)
	
	public Vector nids           = new Vector (); // user ids chosen for metrics 1 to 4
	public Vector nidsflags      = new Vector (); // flags of user ids chosen for metrics 1 to 4 indicating if
	                                              // each userid is a neighbor (YES) or not (NO)
	
	public Vector cn           = new Vector (); // cn metric for each attribute value (7)
	public Vector cnd          = new Vector (); // discretized version of cn for each attribute value (7)
	
	public Vector cnsan        = new Vector (); // cnsan metric for each attribute value (7)
	public Vector cnsand       = new Vector (); // discretized version of cnsan for each attribute value (7)
	
	public Vector aa           = new Vector (); // aa metric for each attribute value (7)
	public Vector aad          = new Vector (); // discretized version of aa for each attribute value (7)
	
	public Vector aasan        = new Vector (); // aasan metric for each attribute value (7)
	public Vector aasand       = new Vector (); // discretized version of aasan for each attribute value (7)
	
	public Vector aasanstatic  = new Vector (); // aasanstatic metric for each attribute value (7)
	public Vector aasanstaticd = new Vector (); // discretized version of aasanstatic for each attribute value (7)
		
	
	public void setSameAttrib(double sameAtt){
		this.sameAttrib = sameAtt;
	}
	public Double getSameAttrib() {
		return this.sameAttrib;
	}
	
	public void setAvgSameAttribs(double avgsameAtts){
		this.avgSameAttribs = avgsameAtts;
	}
	public Double getAvgSameAttribs() {
		return this.avgSameAttribs;
	}
	
	
	public void loadneighid(int neighidd){
		this.neighid.add(neighidd);
	}
	public void loadattrid(int attridd){
		this.attrid.add(attridd);
	}
	
	// load methods for five metrics
	public void loadcn(double cn){ this.cn.add(cn); }
	public void loadcnd(String cnd){ this.cnd.add(cnd); }
	
	public void loadcnsan(double cnsan){ this.cnsan.add(cnsan); }
	public void loadcnsand(String cnsand){ this.cnsand.add(cnsand); }
	
	public void loadaa(double aa){ this.aa.add(aa); }
	public void loadaad(String aad){ this.aad.add(aad); }
	
	public void loadaasan(double aasan){ this.aasan.add(aasan); }
	public void loadaasand(String aasand){ this.aasand.add(aasand); }
	
	public void loadaasanstatic(double aasanstatic){ this.aasanstatic.add(aasanstatic); }
	public void loadaasanstaticd(String aasanstaticd){ this.aasanstaticd.add(aasanstaticd); }
	
	
	// get methods for five metrics
	
	public Double getcn(Integer ixcn){ double val=0;
	if (ixcn < this.cn.size()) val = (Double)this.cn.get(ixcn); return val; }
    public String getcnd(Integer ixcnd){ String val="";
	if (ixcnd < this.cnd.size()) val = (String)this.cnd.get(ixcnd); return val;}
	
	public Double getcnsan(Integer ixcnsan){ double val=0;
	if (ixcnsan < this.cnsan.size()) val = (Double)this.cnsan.get(ixcnsan); return val; }
    public String getcnsand(Integer ixcnsand){ String val="";
	if (ixcnsand < this.cnsand.size()) val = (String)this.cnsand.get(ixcnsand); return val;}
    
	public Double getaa(Integer ixaa){ double val=0;
	if (ixaa < this.aa.size()) val = (Double)this.aa.get(ixaa); return val; }
    public String getaad(Integer ixaad){ String val="";
	if (ixaad < this.aad.size()) val = (String)this.aad.get(ixaad); return val;}
		
	public Double getaasan(Integer ixaasan){ double val=0;
	if (ixaasan < this.aasan.size()) val = (Double)this.aasan.get(ixaasan); return val; }
	public String getaasand(Integer ixaasand){ String val="";
    if (ixaasand < this.aasand.size()) val = (String)this.aasand.get(ixaasand); return val;}
	
	public Double getaasanstatic(Integer ixaasanstatic){ double val=0;
	if (ixaasanstatic < this.aasanstatic.size()) val = (Double)this.aasanstatic.get(ixaasanstatic); return val; }
    public String getaasanstaticd(Integer ixaasanstaticd){ String val="";
	if (ixaasanstaticd < this.aasanstaticd.size()) val = (String)this.aasanstaticd.get(ixaasanstaticd); return val;}
	
    public String getnidsflags(Integer ixnidsflags){ String val="";
	if (ixnidsflags < this.nidsflags.size()) val = (String)this.nidsflags.get(ixnidsflags); return val;}
  	
	public void loadattribhf(String attribhff){
		this.attribhf.add(attribhff);
	}
	public void loadattribhfid(int attribhfid){
		this.attribhfid.add(attribhfid);
	}
	
	public void setNumFriends(){
		this.numFriends = this.friends.size();
	}
		
	public Integer getneighid(Integer ixneighid){
		int val=0;
		if (ixneighid < this.neighid.size())
			val = (Integer)this.neighid.get(ixneighid);
		return val;
	}
	public Integer getattrid(Integer ixattrid){
		int val=0;
		if (ixattrid < this.attrid.size())
			val = (Integer)this.attrid.get(ixattrid);
		return val;
	}
	

	public String getattribhf(Integer ixattribhf){
		String val="";
		if (ixattribhf < this.attribhf.size())
			val = (String)this.attribhf.get(ixattribhf);
		return val;
	}
	
	public Integer getattribhfid(Integer ixattribhfid){
		int val=0;
		if (ixattribhfid < this.attribhfid.size())
			val = (Integer)this.attribhfid.get(ixattribhfid);
		return val;
	}
	
	public Integer getUser() {
		return User;
	}
	
	public void setUser(Integer user) {
		User = user;
	}

	public float getNumFriends() {
		return this.friends.size();
	}
	
	public User(Integer user){
		setUser(user);
	}

	public void afegirFriend(Integer friend){
		if (this.friends.contains(friend) == false){
			this.friends.add(friend);
			setNumFriends();
		}
	}
	public void esborrarFriend(Integer friend){
		if (this.friends.contains(friend) == true){
			this.friends.remove(friend);
			setNumFriends();
		}
	}
	
}
