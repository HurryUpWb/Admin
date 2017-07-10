package Beans;


public class Comment {
	public Integer cid;
	public String b_bookname;
	public String u_account;
	public String time;
	public String content;
	public Integer isshow;
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getB_bookname() {
		return b_bookname;
	}
	public void setB_bookname(String b_bookname) {
		this.b_bookname = b_bookname;
	}
	public String getU_account() {
		return u_account;
	}
	public void setU_account(String u_account) {
		this.u_account = u_account;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIsshow() {
		return isshow;
	}
	public void setIsshow(Integer isshow) {
		this.isshow = isshow;
	}
}
