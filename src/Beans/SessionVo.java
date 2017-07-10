package Beans;

public class SessionVo {
	private String userName;// 当前登陆用户名
	private Integer authority;//权限flag
	
	public SessionVo() {
		// TODO Auto-generated constructor stub
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
	
}
