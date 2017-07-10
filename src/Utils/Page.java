package Utils;

public class Page {
	
	/**
	 * 当前页
	 */
	public int pageIndex;
	
	/**
	 * 总记录个数
	 */
	public Integer TotalNum;
	
	/**
	 * 每页的个数
	 */
	public Integer PerPageNo;
	
	public Integer TotalPage;

	public Page() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "Page [pageIndex=" + pageIndex + ", TotalNum=" + TotalNum + ", PerPageNo=" + PerPageNo + ", TotalPage="
				+ getTotalPage() + "]";
	}

	public int getpageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		if(pageIndex<=0){
			this.pageIndex=1;
		} else 	if(pageIndex>getTotalPage()){
			this.pageIndex=getTotalPage();
		}else{
			this.pageIndex = pageIndex;
		}
	}

	public Integer getTotalNum() {
		return TotalNum;
	}
	
	public void setTotalNum(Integer TotalNum) {
		this.TotalNum=TotalNum;
	}


	public Integer getPerPageNo() {
		return PerPageNo;
	}

	public void setPerPageNo(Integer perPageNo) {
		PerPageNo = perPageNo;
	}

	/**
	 * 查看是否还有下一页
	 * @return
	 */
	public boolean gethasNext(){
		if(getpageIndex()<getTotalPage()){
			return true;
		}
		return false;
	}
	
	/**
	 * 是否还有前页
	 * @return
	 */
	public boolean gethasPrve(){
		if(pageIndex==1){
			return false;
		}
		return true;
	}
	
	/**
	 * 总页数
	 * @return
	 */
	public int getTotalPage(){
		int totalpage;
		if((TotalNum%PerPageNo)>0){
			totalpage=(TotalNum/PerPageNo)+1;
		}else{
			totalpage=TotalNum/PerPageNo;
		}
		return totalpage;
	}
	
}
