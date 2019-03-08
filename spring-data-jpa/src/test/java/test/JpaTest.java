package test;

import com.linhui.domain.Customer;
import com.linhui.utils.JpaUtil;
import org.hibernate.Transaction;
import org.junit.Test;

import javax.persistence.*;
import java.util.List;

public class JpaTest {
    @Test
    public void save() {
        // 第一步：创建实体管理工厂（EntityManagerFactory） -- 数据源
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("myjpa");
        // 第二步：创建实体管理对象（EntityManager）,操作数据库的核心对象（CRUD的API都在EntityManager中） -- 连接
        EntityManager entityManager = factory.createEntityManager();
        // 第三步：获取事务（开启事务、提交、回滚）
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 第四步：操作对象==操作数据库
        Customer customer = new Customer();
        customer.setCustName("小甜心");
        customer.setCustSource("朋友圈");
        customer.setCustAddress("楠木");
        customer.setCustIndustry("管家");
        customer.setCustLevel("99");
        customer.setCustPhone("15271877863");
        System.out.println("瞬时态 ：" + customer);
        entityManager.persist(customer); //保存操作
        System.out.println("持久态 ：" + customer);
        // 第五步：事务提交
        transaction.commit();
        // 第六步：资源关闭
        entityManager.close();
        factory.close();
        System.out.println("游离态 ：" + customer);
    }
    @Test
    public void testSave() {
        Customer customer = new Customer();
        customer.setCustName("小甜心");
        customer.setCustSource("朋友圈");
        customer.setCustAddress("楠木村");
        customer.setCustIndustry("管家");
        customer.setCustLevel("99");
        customer.setCustPhone("15271877865");
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction tr = entityManager.getTransaction();
        try {
            tr.begin();
            entityManager.persist(customer);
            tr.commit();
        }catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }finally {
            entityManager.close();
        }
    }
    @Test
    public void testUpdate() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction tr = entityManager.getTransaction();
        try {
            tr.begin();
            Customer customer = entityManager.find(Customer.class, 4L);
            customer.setCustAddress("金山村");
            tr.commit();
        }catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }finally {
            entityManager.close();
        }
    }
    @Test
    public void testDelete() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction tr = entityManager.getTransaction();
        try {
            tr.begin();
            Customer customer = entityManager.find(Customer.class, 5L);
            entityManager.remove(customer);
            tr.commit();
        }catch (Exception e) {
            e.printStackTrace();
            tr.rollback();
        }finally {
            entityManager.close();
        }
    }
    @Test
    public void testFindById() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            Customer customer = entityManager.find(Customer.class, 4L);
            System.out.println(customer);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 延时加载
     */
    @Test
    public void testFindId() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
           Customer customer = entityManager.getReference(Customer.class, 4L);
            System.out.println(customer);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    /**
     * 查询所有
     */
    @Test
    public void findAll() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        EntityTransaction tr = entityManager.getTransaction();
        try {
            String jpql = "from Customer";
            Query query = entityManager.createQuery(jpql);
            List<Customer> customers = query.getResultList();
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }

    }
    @Test
    public void testPaged() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            Query query = entityManager.createQuery("from Customer");
            query.setFirstResult(1);
            query.setMaxResults(2);
            List<Customer> resultList = query.getResultList();
            for (Customer customer : resultList) {
                System.out.println(customer);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }
    @Test
    public void condition() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            /*Query query = entityManager.createQuery("from Customer where custAddress like :name");
            query.setParameter("name","%山%");*/
           /* Query query = entityManager.createQuery("from Customer where custAddress like ?");
            query.setParameter(1,"%山%");*/
            Query query = entityManager.createQuery("from Customer where custAddress like '%山%'");
            List<Customer> resultList = query.getResultList();
            for (Customer customer : resultList) {
                System.out.println(customer);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    @Test
    public void testSort() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            Query query = entityManager.createQuery("from Customer order by custAddress desc ");
            List<Customer> resultList = query.getResultList();
            for (Customer customer : resultList) {
                System.out.println(customer);
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }

    @Test
    public void testCount() {
        EntityManager entityManager = JpaUtil.getEntityManager();
        try {
            Query query = entityManager.createQuery("select count(custId) from Customer");
            Long count = (Long) query.getSingleResult();
            System.out.println(count);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            entityManager.close();
        }
    }



}
