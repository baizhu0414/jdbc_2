package jdbc_2.test2.bean;

public class Major {

	int id;
	String majorname;
	String majornum;
	public Major() {
		super();
	}
	public Major(int id, String majorname, String majornum) {
		super();
		this.id = id;
		this.majorname = majorname;
		this.majornum = majornum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMajorname() {
		return majorname;
	}
	public void setMajorname(String majorname) {
		this.majorname = majorname;
	}
	public String getMajornum() {
		return majornum;
	}
	public void setMajornum(String majornum) {
		this.majornum = majornum;
	}
	@Override
	public String toString() {
		return "major [id=" + id + ", majorname=" + majorname + ", majornum=" + majornum + "]";
	}
	
}
