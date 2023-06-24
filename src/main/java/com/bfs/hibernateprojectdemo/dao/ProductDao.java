package com.bfs.hibernateprojectdemo.dao;

import com.bfs.hibernateprojectdemo.domain.Product;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;

@Repository
public class ProductDao extends AbstractHibernateDao<Product>{
    public ProductDao(){
        this.setClazz(Product.class);
    }

    public List<Product> getAllProduct(){
        return this.getAll();
    }

    public Product getProductById(Long id){
        return this.findById(id);
    }

}
