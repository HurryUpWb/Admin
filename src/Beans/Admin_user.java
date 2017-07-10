package Beans;

public class Admin_user {

	public Integer id;
	public String name;
	public String password;
	public Integer authority;
	
	public Admin_user() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "Admin_user [id=" + id + ", name=" + name + ", password=" + password + ", authority=" + authority + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getAuthority() {
		return authority;
	}

	public void setAuthority(Integer authority) {
		this.authority = authority;
	}
}
