package DAO;

import java.util.List;
import Beans.Order;

public interface OrderDao {
	
	/**
	 * 根据Id查找订单
	 * @param oid
	 * @return
	 */
	public Order getOrderById(Integer oid);
	
	/**
	 * 删除某一订单
	 * @param oid
	 * @return
	 */
	public boolean deleteOrderById(Integer oid);
	
	/**
	 * 更新订单
	 * @param o
	 * @return
	 */
	public boolean updateOrder(Order o);
	
	/**
	 * 获取订单List 
	 * @return
	 */
	public List<Order> getAllOrder();
}
