package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.HibernateUtil;
import com.scp.dronizone.warehouse.states.ProcessingState;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.ArrayList;
import java.util.List;

public class OrderManager {
    static List<Order> orders = new ArrayList<>();

    public OrderManager() {
    }

    public static List<Order> getUnpackedOrders() {
        List<Order> unpackedOrders = new ArrayList<>();
        for(Order order : orders){
            if(order.getProcessingState().equals(ProcessingState.PENDING))
                unpackedOrders.add(order);
        }
        return unpackedOrders; // TODO check unpackedOrders and return them
    }

    public static Order setOrderPacked(int idOrder) {
        for (Order order : orders) {
            if(order.getId() == idOrder){
                order.setProcessingState(ProcessingState.PACKED);
                return order;
            }
        }
        return null;
    }

    public static void addOrder(Order newOrder) {
        orders.add(newOrder);
    }

    public static List<Order> getOrders() {
        return orders;
    }

    public static void resetOrders() {
        orders.clear();
    }

    public static void setOrders(List<Order> myOrders) {
        orders = myOrders;
    }

    public static int getNbOrder(){
        return orders.size();
    }

    public void insertOrder(Order order){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.beginTransaction();

//        Order order = new Order(2.0);
//
//        session.save(order);
//        session.getTransaction().commit();

//        List orders = session.createSQLQuery("SELECT * FROM `order`")
//                .addEntity(Order.class).list();
//        Order order = (Order) orders.get(0);
//        order.toString();

        String query = "INSERT INTO `order`(`o_price`, `o_status`) VALUES ("+ order.getPrice() +","+order.getProcessingState()+")";
        session.createSQLQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
