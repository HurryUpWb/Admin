package Utils;
import java.lang.reflect.Field;
import java.util.regex.Pattern;

public class show {
	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
		 
//	      Class cache = Integer.class.getDeclaredClasses()[0]; //1
//	      Field myCache = cache.getDeclaredField("cache"); //2
//	      myCache.setAccessible(true);//3
//	 
//	      Integer[] newCache = (Integer[]) myCache.get(cache); //4
//	      newCache[132] = newCache[133]; //5
//	 
//	      int a = 2;
//	      int b = a + a;
//	      System.out.printf("%d + %d = %d", a, a, b); //
//	      System.out.println();
//	      int c=0;
//	      c=a+a;
//	      System.out.printf("%d",c);
//	      System.out.println(c);
		
//			String sql="SELECT o.*,p.p_state,p.p_code FROM orders o LEFT JOIN payment p ON o.o_id=p.o_id WHERE o.u_id=?";
//			System.out.println(DAO.Dao.getInfo(sql, 1));
		
			String a = "aaaa\"[aa\"[a";
//			System.out.println(a);
			String str=Pattern.quote("\"[");
			System.out.println(str);
			String b =a.replaceAll(str, "[");
			System.out.println(b);
//			System.out.println(Pattern.quote("\\\"["));
	    }		
}
