package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.User;
import lombok.val;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao extends AbstractHibernateDao<User>{
    public UserDao() {setClazz(User.class);}

    public User getUserById(Long id) {
        return this.findById(id);
    }

    public List<User> getAllUsers(){
        return this.getAll();
    }

    public void addUser(User user){
        this.add(user);
    }

    public Optional<User> loadUserByUsername(String username){
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("username"), username));
        List<User> users = session.createQuery(criteriaQuery).getResultList();
        // Hibernate.initialize(user.getPermissions()); to push initialization or specify it as eager
        return users.stream().filter(user -> user.getUsername().equals(username)).findAny();
    }

    public Optional<User> loadUserByEmail(String email){
        Session session = getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = builder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root);
        criteriaQuery.where(builder.equal(root.get("email"), email));
        List<User> users = session.createQuery(criteriaQuery).getResultList();
        return users.stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

    public void createUser(User user){
        Session session = sessionFactory.getCurrentSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
    }
}
