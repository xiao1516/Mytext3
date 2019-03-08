package com.linhui.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JpaUtil {
    private static EntityManagerFactory entityManagerFactory;
    static {
        entityManagerFactory = Persistence.createEntityManagerFactory("myjpa");
    }
    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

    public static void main(String[] args) {
        List list = Collections.synchronizedList(new ArrayList<>());
    }
}
