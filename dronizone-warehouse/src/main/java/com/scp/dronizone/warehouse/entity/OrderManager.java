package com.scp.dronizone.warehouse.entity;

import com.scp.dronizone.warehouse.BddConnection;
import com.scp.dronizone.warehouse.HibernateUtil;
import com.scp.dronizone.warehouse.entity.Order;
import com.scp.dronizone.warehouse.states.ProcessingState;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderManager  extends JdbcDaoSupport {
    static List<Order> orders = new ArrayList<>();

    public static void main(String[] args) {
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

        String query = "INSERT INTO `order`(`o_price`, `o_status`) VALUES ("+ 2.0 +","+0+")";
        session.createSQLQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();

//        Connection connection = BddConnection.getConnection();
//        String query = "INSERT INTO `Order`(`o_price`, `o_status`) VALUES ("+ 2.0 +","+0+")";
//        int  result = 0;
//        try {
//            result = connection.createStatement().executeUpdate(query);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }


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

    public static void setOrderPacked(int idOrder) {
        for (Order order : orders) {
            if(order.getId() == idOrder){
                order.setProcessingState(ProcessingState.PACKED);
                return;
            }
        }
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
}
