package Beans;

public class Book {
	/**
	 *  ID
	 */
	public Integer b_id;
	/*
	 * 类型
	 */
	public String b_booktype;
	/*
	 * 出版社
	 */
	public String b_pubs;
	/*
	 * 名称
	 */
	public String b_bookname;
	/*
	 * 作者
	 */
	public String b_author;
	/*
	 * 价格
	 */
	public double b_price;
	/*
	 * 描述
	 */
	public String b_description;
	
	/**
	 * 缩略图片
	 */
	public String b_pic;
	
	/**
	 * 大图
	 */
	public String b_pic_large;


	/**
	 * 是否显示
	 */
	public Integer b_show;
	
	/**
	 * 库存
	 */
	
	
	public Book() {
		// TODO Auto-generated constructor stub
	}


	public Integer getB_id() {
		return b_id;
	}

	public void setB_id(Integer b_id) {
		this.b_id = b_id;
	}

	public String getB_booktype() {
		return b_booktype;
	}

	public void setB_booktype(String b_booktype) {
		this.b_booktype = b_booktype;
	}

	public String getB_pubs() {
		return b_pubs;
	}

	public void setB_pubs(String b_pubs) {
		this.b_pubs = b_pubs;
	}

	public String getB_bookname() {
		return b_bookname;
	}

	public void setB_bookname(String b_bookname) {
		this.b_bookname = b_bookname;
	}

	public String getB_author() {
		return b_author;
	}

	public void setB_author(String b_author) {
		this.b_author = b_author;
	}

	public double getB_price() {
		return b_price;
	}

	public void setB_price(double b_price) {
		this.b_price = b_price;
	}

	public String getB_description() {
		return b_description;
	}

	public void setB_description(String b_description) {
		this.b_description = b_description;
	}
	
	public String getB_pic() {
		return b_pic;
	}

	public void setB_pic(String b_pic) {
		this.b_pic = b_pic;
	}
	
	public String getB_pic_large() {
		return b_pic_large;
	}


	public void setB_pic_large(String b_pic_large) {
		this.b_pic_large = b_pic_large;
	}

	public Integer getB_show() {
		return b_show;
	}

	public void setB_show(Integer b_show) {
		this.b_show = b_show;
	}
	
}
