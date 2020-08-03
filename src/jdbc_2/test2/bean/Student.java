package jdbc_2.test2.bean;

public class Student {
	
	int id;
	String stuname;
	String stuphone;
	public Student() {
		super();
	}
	public Student(int id, String stuname, String stuphone) {
		super();
		this.id = id;
		this.stuname = stuname;
		this.stuphone = stuphone;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStuname() {
		return stuname;
	}
	public void setStuname(String stuname) {
		this.stuname = stuname;
	}
	public String getStuphone() {
		return stuphone;
	}
	public void setStuphone(String stuphone) {
		this.stuphone = stuphone;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", stuname=" + stuname + ", stuphone=" + stuphone + "]";
	}

}
