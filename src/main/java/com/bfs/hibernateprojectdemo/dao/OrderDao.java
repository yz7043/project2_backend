package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.Order;
import com.bfs.hibernateprojectdemo.domain.User;
import com.bfs.hibernateprojectdemo.dto.order.orderdetail.OrderDetailResponse;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class OrderDao extends AbstractHibernateDao<Order>{
    public OrderDao(){
        setClazz(Order.class);
    }

    @Override
    public List<Order> getAll() {
        return super.getAll();
    }

    @Transactional
    public List<Order> getAllByUsername(String username){
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteria = builder.createQuery(clazz);
        Root<Order> root = criteria.from(Order.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("user").get("username"), username));
        List<Order> resultList = session.createQuery(criteria).getResultList();
        // make sure user are fetched
        for(Order order : resultList){
            Hibernate.initialize(order.getUser());
        }
        return resultList;
    }

    @Transactional
    public void addOrder(Order order){
        Session session = getCurrentSession();
        session.save(order);
    }

    public Order getById(Long orderId) {
        return this.findById(orderId);
    }

    @Transactional
    public void updateOrder(Order order){
        Session session = sessionFactory.getCurrentSession();
        session.update(order);
    }
}
