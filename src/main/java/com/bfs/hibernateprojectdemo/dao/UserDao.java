package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.User;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDao extends AbstractHibernateDao<User>{
    public UserDao() {setClazz(User.class);}

    public User getUserById(int id) {
        return this.findById(id);
    }

    public List<User> getAllUsers(){
        return this.getAll();
    }

    public void addUser(User user){
        this.add(user);
    }

//    @Transactional
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
}
