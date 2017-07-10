package DAOImpl;

import java.util.List;

import Beans.Order;
import DAO.Dao;
import DAO.OrderDao;

public class OrderDaoImpl extends Dao implements OrderDao{

	@Override
	public Order getOrderById(Integer oid) {
		Order order=null;
		StringBuffer sql=new StringBuffer();
		sql.append("select * from orders where o_id=?");
		order=get(Order.class,sql.toString(),oid);
		return order;
	}

	@Override
	public boolean deleteOrderById(Integer oid) {
		StringBuffer sb=new StringBuffer();
		sb.append("delete from orders where o_id=?");
		boolean b=update(sb.toString(),oid);
		return b;
	}

	@Override
	public boolean updateOrder(Order o) {
		StringBuffer sb=new StringBuffer();
		sb.append("update orders set u_id=?,o_booknum=?,o_booksname=?,o_orderdate=?,o_totalprice=?,o_isdeliver=? ");
		sb.append(" where o_id=?");
		boolean b=update(sb.toString(),o.getU_id(),o.getO_booknum(),o.getO_booksname(),o.getO_orderdate(),o.getO_totalprice(),o.getO_isdeliver(),o.getO_id());
		return b;
	}

	@Override
	public List<Order> getAllOrder() {
		List<Order> list=null;
		String sql="select * from orders";
		list=getAll(Order.class, sql);
		return list;
	}

}
