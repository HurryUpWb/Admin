package service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import Beans.Book;
import Beans.NumOfType;
import Beans.Type;
import DAO.Dao;
import DAOImpl.BookDaoImpl;
import DAOImpl.TypeDaoImpl;
import Utils.ImgCompress;
import Utils.Page;
import Utils.TurnPage;
import Utils.utils;

public class BookService {
	private static Logger log=Logger.getLogger(BookService.class);
//	private static String ospath="D:\\bookshop_pic";	//window下存储路径
	private static String ospath="/home/bookshop_pic"; 	//linux图片存储路径
	
	
	/**
	 * 获得书籍分类
	 * @return
	 */
	public static List<Type> getAllType(){
		List<Type> list=null;
		TypeDaoImpl impl=new TypeDaoImpl();
		list=impl.getAllType();
		return list;
	} 
	
	/**
	 * 获取新增的书籍信息并上传图片
	 * @param request
	 * @param response
	 * @return
	 */
	public static Map<String,String> UploadPic(HttpServletRequest request){
		Map<String, String> valuemap=new HashMap<String,String>();
		//1.得到FileItem的集合
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		File TempDirectory=new File(ospath+"/TempDirectory");
		// setSeizeThreshold()设置内存中只能存在的文件内容的大小，超出则写入磁盘
		factory.setSizeThreshold(1024*500);
		factory.setRepository(TempDirectory);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
//		upload.setHeaderEncoding(request.getCharacterEncoding());

		// Set overall request size constraint
		upload.setSizeMax(1024*1024*5);
		String path="";
		
		
		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			//2.遍历集合
			
			for(FileItem item:items){
				if(item.isFormField()){
					valuemap.put(item.getFieldName(),item.getString("utf8"));
				}
			}
			
			for(FileItem item:items){
				//若是一个一般的表单域，则打印信息
				if(item.isFormField()){
					continue;
				}
				//若是文件域则把文件保存到磁盘中
				else{
					String filename=item.getName();
					if(filename!=null && !filename.equals("")){
						InputStream in=item.getInputStream();
						byte[]buffer=new byte[1024];
						int len=0;
						 
						 String rename=getfilepath(filename);
//						 path="//home//bookshop_pic//"+rename;
						 path=ospath+"/"+rename;
						OutputStream out=new FileOutputStream(path);
						while((len=in.read(buffer))!=-1){
							out.write(buffer, 0, len);
						}
						out.close();
						in.close();
//						String str=CompressPic(120,"//home//bookshop_pic//", rename); //缩略图
						String str=CompressPic(120,ospath+"/", rename); //缩略图
						valuemap.put("bpic","/bookpic/"+str);
//						String picc=CompressPic(180,"//home//bookshop_pic//",rename); //大图
						String picc=CompressPic(180,ospath+"/",rename); //大图
						valuemap.put("b_pic_large","/bookpic/"+picc);
					}else{
						valuemap.put("bpic",null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("",e);
		}finally{
			File f=new File(path);
			if(f.exists()){
				f.delete();
			}
		}	
		return valuemap;
	}
	
	private static String getfilepath(String filename) {
		String exName="";
		if(filename!=null && !filename.equals("")){
			exName=filename.substring(filename.lastIndexOf("."));
		}
		Random ran=new Random();
		int random=ran.nextInt(100000);
		String path=System.currentTimeMillis()+random+exName;
		return path;
	}
	
	/**
	 * 压缩图片
	 * @param path
	 */
	private static String CompressPic(int size,String path,String name){
		String str=UUID.randomUUID()+name;
		try {
			ImgCompress cmp=new ImgCompress(path+name);
			cmp.resizeFix(size,size,path+str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return str;
	}
	
	/***
	 * 保存添加的书籍信息
	 * @param map
	 * @return
	 */
	public static boolean SaveBook(Map<String,String> map){
		 BookDaoImpl impl=new BookDaoImpl();
		 Book book=new Book();
		 log.info("添加新书开始：");
		 
		 String bname=map.get("bname");
		 if(bname!=null && !bname.equals("")){
			 book.setB_bookname(bname);
		 }else{
			 book.setB_bookname("");
		 }
		 
		 String btype=map.get("btype");
		 if(btype!=null && !btype.equals("")){
			 book.setB_booktype(btype);
		 }else{
			 book.setB_booktype("");
		 }
		 
		 String bshow=map.get("bshow");
		 if(bshow!=null && !bshow.equals("")){
			 book.setB_show(Integer.parseInt(bshow));
		 }else{
			 book.setB_show(1);
		 }
		 
		 String bauthor=map.get("bauthor");
		 if(bauthor!=null && !bauthor.equals("")){
			 book.setB_author(bauthor);
		 }
		 
		 String bpubs=map.get("bpubs");
		 if(bpubs!=null && !bpubs.equals("")){
			 book.setB_pubs(bpubs);
		 }
		 
		 String bpic=map.get("bpic");
		 if(bpic!=null && !bpic.equals("")){
			 book.setB_pic(bpic);
		 }
		 
		 String b_pic_large=map.get("b_pic_large");
		 if(b_pic_large!=null && !b_pic_large.equals("")){
			 book.setB_pic_large(b_pic_large);
		 }
		 
		 String desc=map.get("desc");
		 if(desc!=null && !desc.equals("")){
			 book.setB_description(desc);
		 }
		 
		 String bprice=map.get("bprice");
		 if(bprice!=null && !bprice.equals("")){
			 double b=Double.parseDouble(bprice);
			 book.setB_price(b);
		 }
		 boolean b=impl.SaveBook(book);
		 if(!b){
			 log.info("添加新书  《"+book.getB_bookname()+"》成功！");
		 }else{
			 log.info("添加新书   《"+book.getB_bookname()+"》失败！");
			 return false;
		 }
		 return b;
	}
	
	/**
	 * 翻页操作
	 * @param page
	 * @param category
	 * @param val
	 * @return
	 */
	public static List<Book> TurnPages(Page page,String category,String val){
		StringBuffer sql=new StringBuffer();
		List<Book> list=null;
		TypeDaoImpl impl=new TypeDaoImpl();
		
		if(category!=null && !category.equals("") && val!=null && !val.equals("")){
			if(category.equals("b_booktype")){
				Type t=impl.getTypeByInfo(val);
				sql.append("select * from bookinfo where "+category+"='"+t.getTYPENO()+"' limit ?,?");
			}else if(category.equals("b_show")){
				if(val.equals("是")){
					sql.append("select * from bookinfo where "+category+"='1' limit ?,?");
				}else{
					sql.append("select * from bookinfo where "+category+"='0' limit ?,?");
				}
			}else{
				sql.append("select * from bookinfo where "+category+"='"+val+"' limit ?,?");
			}
		}else{
			sql.append("select * from bookinfo limit ?,?");
		}
		TurnPage<Book> turn=new TurnPage<>(page);
		Book b=new Book();
		list=turn.Turn(sql.toString(), b);
		for(Book book:list){
			Type type=impl.getTypeByNo(book.getB_booktype());
			book.setB_booktype(type.getTYPEINFO());
		}
		return list;
	}
	
	/**
	 * 获得bookinfo表的所有记录总数
	 * @return
	 */
	public static int getSumOfTab(){
		Integer i;
		String sql="select count(b_id) sum from bookinfo ";
		i=Dao.getNumOfTab(sql,null);
		return i.intValue();
	}
	
	/**
	 * 按条件获取bookinfo表的记录数
	 * @param category
	 * @param val
	 * @return
	 */
	public static int getSumOfTabByCategory(String category,String val){
		TypeDaoImpl impl=new TypeDaoImpl();
		Integer i;
		StringBuffer sql=new StringBuffer();
		sql.append("select count(b_id) sum from bookinfo ");
		if(category.equals("b_booktype")){
			Type t=impl.getTypeByInfo(val);
			val=t.getTYPENO();
			sql.append(" where "+category+"='"+val+"'");
		}else if(category.equals("b_show")){
			if(val.equals("是")){
				sql.append(" where "+category+"='1'");
			}else{
				sql.append(" where "+category+"='0'");
			}
		}
		i=Dao.getNumOfTab(sql.toString(),null);
		return i.intValue();
	}
	
	/**
	 * 根据ID查找书籍
	 * @param id
	 * @return
	 */
	public static Book getBookById(Integer id){
		Book b=new Book();
		BookDaoImpl impl=new BookDaoImpl();
		b=impl.getBookById(id);
		return b;
		
	}
	
	/**
	 * 更新修改
	 * @param request
	 * @return
	 */
	public static boolean SaveEdit(HttpServletRequest request){
		Map<String,String> map=new HashMap<>();
		map=UploadPic(request);
		BookDaoImpl impl=new BookDaoImpl();
		Book book=new Book();
		
		String bid=map.get("bid");
		if(bid!=null && !bid.equals("")){
			book=impl.getBookById(Integer.parseInt(bid));
			
			String bname=map.get("bname");
			 if(bname!=null && !bname.equals("")){
				 book.setB_bookname(bname);
			 }else{
				 book.setB_bookname("");
			 }
			 
			 String btype=map.get("btype");
			 if(btype!=null && !btype.equals("")){
				 book.setB_booktype(btype);
			 }else{
				 book.setB_booktype("");
			 }
			 
			 String bshow=map.get("bshow");
			 if(bshow!=null && !bshow.equals("")){
				 book.setB_show(Integer.parseInt(bshow));
			 }else{
				 book.setB_show(1);
			 }
			 
			 String bauthor=map.get("bauthor");
			 if(bauthor!=null && !bauthor.equals("")){
				 book.setB_author(bauthor);
			 }
			 
			 String bpubs=map.get("bpubs");
			 if(bpubs!=null && !bpubs.equals("")){
				 book.setB_pubs(bpubs);
			 }

			 String bpic=map.get("bpic");
			 if(bpic!=null && !bpic.equals("")){
				 book.setB_pic(bpic);
			 }
			 
			 String b_pic_large=map.get("b_pic_large");
			 if(bpic!=null && !bpic.equals("")){
				 book.setB_pic_large(b_pic_large);
			 }
			 
			 String desc=map.get("desc");
			 if(desc!=null && !desc.equals("")){
				 book.setB_description(desc);
			 }
			 
			 String bprice=map.get("bprice");
			 if(bprice!=null && !bprice.equals("")){
				 double b=Double.parseDouble(bprice);
				 book.setB_price(b);
			 }
		}
		 
		 
		 boolean b=impl.UpdadeBook(book);
		 if(!b){
			 log.info("修改  《"+book.getB_bookname()+"》成功！");
		 }else{
			 log.info("修改   《"+book.getB_bookname()+"》失败！");
			 return false;
		 }
		 return b;
	}
	
	
	/**
	 * 
	 * 根据ID删除书籍
	 * @param id
	 */
	public static boolean DeleteBook(Integer id){
		BookDaoImpl impl=new BookDaoImpl();
		Book b=impl.getBookById(id);
		String str=b.getB_pic();
		String bpic=b.getB_pic_large();
		boolean flag=impl.DeleteBookById(id.intValue());
		if(!flag){
			String path="";
			String bpath="";
			if(!utils.IsNull(bpic)){
				path=bpic.substring(9);
			}
			if(!utils.IsNull(str)){
				bpath=str.substring(9);
			}
//			File f=new File("//home//bookshop_pic//"+path);
			File f=new File(ospath+"/"+path);
//			File ff=new File("//home//bookshop_pic//"+bpath);
			File ff=new File(ospath+"/"+bpath);
			if(f.exists()){
				f.delete();
			}
			if(ff.exists()){
				ff.delete();
			}
		}else{
			log.info("删除  "+b.getB_bookname()+"失败！");
		}
		return flag;
	}
	
	/**
	 * 获取分类
	 * @return
	 */
	public static List<NumOfType> getNumOfType(){
		BookDaoImpl impl=new BookDaoImpl();
		return impl.getNumOfType();
	}
	
	public static Integer getSumOfBooks(){
		BookDaoImpl impl=new BookDaoImpl();
		Integer i=impl.getNumOfTab("select count('id') sum from bookinfo", null);
		return i;
	
	}
	
	/**
	 * 模板下载
	 * @param request
	 * @param response
	 */
	public static void DownLoadModel(HttpServletRequest request,HttpServletResponse response){
		String realpath=request.getServletContext().getRealPath("/files/model.xls");
		try {
			response.setHeader("content-disposition","attachment;filename="+URLEncoder.encode("模板.xls", "utf8"));
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
		FileInputStream in=null;
		try {
			in=new FileInputStream(new File(realpath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		OutputStream out=null;
		try {
			out=response.getOutputStream();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		byte[] b=new byte[1024];
		int len;
		try {
			while((len=in.read(b))!=-1){
				out.write(b, 0, len);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 获取上传的xlsx
	 * @param request
	 */
	public static String  UploadAllFile(HttpServletRequest request){
		DiskFileItemFactory factory = new DiskFileItemFactory();
//		File TempDirectory=new File("//home//bookshop_pic//TempDirectory");
		File TempDirectory=new File(ospath+"/TempDirectory");
		// setSeizeThreshold()设置内存中只能存在的文件内容的大小，超出则写入磁盘
		factory.setSizeThreshold(1024*500);
		factory.setRepository(TempDirectory);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Set overall request size constraint
		upload.setSizeMax(1024*1024*5);
		String path="";
		
		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			//2.遍历集合
			
			for(FileItem item:items){
				//若是一个一般的表单域，则打印信息
				if(item.isFormField()){
					continue;
				}
				//若是文件域则把文件保存到磁盘中
				else{
					String filename=item.getName();
					InputStream in=item.getInputStream();
					byte[]buffer=new byte[1024];
					int len=0;
					 String rename=getfilepath(filename);
//					 path="//home//bookshop_pic//temp//"+rename;
					 path=ospath+"/temp/"+rename;
					OutputStream out=new FileOutputStream(path);
					while((len=in.read(buffer))!=-1){
						out.write(buffer, 0, len);
					}
					out.close();
					in.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("",e);
		}
		if(path!=null && !path.equals("")){
			log.info("临时文件地址："+path);
		}else{
			log.info("未获得临时文件地址");
		}
		return path;
	}
	
	/**
	 * 读取xlsx中的信息，并上传
	 * @param path
	 */
	@SuppressWarnings({ "deprecation"})
	public static List<Book> ReadExcle(String path){
		List<Book> list=new ArrayList<>();
		InputStream in=null;
		BufferedInputStream stream=null;
		POIFSFileSystem fileSystem=null;
		HSSFWorkbook wb=null;
		HSSFCell cell=null;
		int rowsize=0;
		List<String[]> result = new ArrayList<String[]>();
		try {
			if(!path.equals("") && path!=null){
				in=new FileInputStream(new File(path));
			}else{
				Exception e=new Exception("未得到参数");
				log.info(e);
			}
			stream=new BufferedInputStream(in);
			fileSystem=new POIFSFileSystem(stream);
			wb=new HSSFWorkbook(fileSystem);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
			HSSFSheet sheet=wb.getSheetAt(0);
			for(int rowindex=2;rowindex<=sheet.getLastRowNum();rowindex++){
				HSSFRow row=sheet.getRow(rowindex);
				if(row==null){
					break;
				}
				int temprowsize=row.getLastCellNum()+1;
				if(temprowsize>rowsize){
					rowsize=temprowsize;
				}
				String[] values = new String[rowsize];
				Arrays.fill(values, "");
				boolean hasValue = false;

				for (short columnIndex = 0; columnIndex <= row.getLastCellNum(); columnIndex++) {
					String value = "";
					cell = row.getCell(columnIndex);
					if (cell != null) {
						switch (cell.getCellType()) {
						case HSSFCell.CELL_TYPE_STRING://读取的格式为字符串
							value = cell.getStringCellValue();
							break;
						case HSSFCell.CELL_TYPE_NUMERIC://读取的格式为数组
							//如果格式为日期格式，自定义格式输出
							if (HSSFDateUtil.isCellDateFormatted(cell)) {
								Date date = cell.getDateCellValue();
								if (date != null) {
									value = new SimpleDateFormat("yyyy-MM-dd")
											.format(date);
								} else {
									value = "";
								}
							} else {
								//如果格式为数值，自定义格式输出
								value = new DecimalFormat().format(cell.getNumericCellValue());
							}
							break;
						case HSSFCell.CELL_TYPE_FORMULA:
							// 导入时如果为公式生成的数据则无值
							value = "";
							break;
							// 导入时如果为空
						case HSSFCell.CELL_TYPE_BLANK:
							break;
						case HSSFCell.CELL_TYPE_ERROR:
							value = "";
							break;
							// 导入时如果为BOOLEAN型 自定义格式输出
						case HSSFCell.CELL_TYPE_BOOLEAN:
							value = (cell.getBooleanCellValue() == true ? "Y": "N");
							break;
						default:
							value = "";
						}
					}
					if(value==""){
						continue;
					}
					values[columnIndex] = rightTrim(value);
					hasValue = true;
					
				}
				if (hasValue) {
					result.add(values);
				}
			}
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			String[] str=null;
			for(int i=0;i<result.size();i++){
				str=result.get(i);
				Book b=new Book();
				b.setB_bookname(str[0]);
				b.setB_author(str[1]);
				b.setB_booktype(str[2]);
				b.setB_pubs(str[3]);
				b.setB_price(Double.parseDouble(str[4]));
				b.setB_description(str[5]);
				b.setB_pic("none");
				list.add(b);
		}
		File f=new File(path);
		if(f.exists()){
			log.info("删除临时文件"+f.getName());
			f.delete();
		}
		if(list!=null && list.size()>0){
			log.info("图书List");
			log.info(list.toArray());
		}
		return list;
	}
	
	/**
	 * 去掉字符串右边的空格
	 * 
	 * @param str 要处理的字符串
	 * @return 处理后的字符串
	 */
	public static String rightTrim(String str) {
		if (str == null) {
			return "";
		}
		int length = str.length();
		for (int i = length - 1; i >= 0; i--) {
			if (str.charAt(i) != 0x20) {
				break;
			}
			length--;
		}
		return str.substring(0, length);
	}
	
	/**
	 * 按list上传books
	 * @param list
	 */
	public static void UploadBooks(List<Book> list){
		log.info("批量上传图书开始：");
		 for(Book b:list){
			 Map<String,String> map=new HashMap<>();
			 String str=Double.toHexString(b.getB_price());
			 map.put("bname", b.getB_bookname());
			 map.put("btype", b.getB_booktype());
			 map.put("bauthor",b.getB_author());
			 map.put("bpubs",b.getB_pubs());
			 map.put("bpic"," "); //缩略图
			 map.put("b_pic_large"," ");//大图
			 map.put("bprice", str);
			 map.put("desc",b.getB_description());
			 log.info("图片的MAP："+map);
			 SaveBook(map);
		 }
		 log.info("批量上传结束");
	}
}
