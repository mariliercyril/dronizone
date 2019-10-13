package com.scp.dronizone.warehouse.entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderDAO  extends JdbcDaoSupport {

    @Autowired
    DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public List<Order> getAllEmployees() {
        String sql = "SELECT * FROM order";
        List<Map<String, Object>> rows = getJdbcTemplate().queryForList(sql);

        List<Order> result = new ArrayList<Order>();
        for (Map<String, Object> row : rows) {
            Order emp = new Order();
            result.add(emp);
        }

        return result;
    }
    public void insertEmployee(Order employee) {
        String sql = "INSERT INTO employee " + "(o_price, o_status) VALUES (?, ?)";
        getJdbcTemplate().update(sql, new Object[] { employee.getPrice(), employee.getProcessingState() });
    }
}
