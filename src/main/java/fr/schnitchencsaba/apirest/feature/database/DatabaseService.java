package fr.schnitchencsaba.apirest.feature.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatabaseService {

    @Autowired
    private EntityManager entityManager;

    public List<Tuple> getProductNameList() {
        Query query = entityManager.createNativeQuery("SELECT name from products;", Tuple.class);
        return ((List<Tuple>) query.getResultList());
    }

    public List<Tuple> getProductList() {
        Query query = entityManager.createNativeQuery("SELECT id, name, description from products;", Tuple.class);
        return ((List<Tuple>) query.getResultList());
    }
}
