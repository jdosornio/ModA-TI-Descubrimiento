/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package iing.uabc.edu.mx.persistencia.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import org.hibernate.cfg.Configuration;

/**
 *
 * @author emmanuelle
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static final ThreadLocal threadSession = new ThreadLocal();
    private static final ThreadLocal threadTransaccion = new ThreadLocal();   

    static {
        try {
            Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
            StandardServiceRegistryBuilder sb = new StandardServiceRegistryBuilder();
            sb.applySettings(cfg.getProperties());
            StandardServiceRegistry standardServiceRegistry = sb.build();
            sessionFactory = cfg.buildSessionFactory(standardServiceRegistry);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static Session getSession() {
        Session s = (Session) threadSession.get();
        try {
            if (s == null) {
                s = sessionFactory.openSession();
                threadSession.set(s);
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
        return s;
    }

    public static void closeSession() {
        try {
            Session s = (Session) threadSession.get();
            Transaction tx = (Transaction) threadTransaccion.get();
            threadSession.set(null);

            if (s != null && s.isOpen()) {
                if (tx != null) {
                    s.flush();
                }
                s.close();
            }

            threadTransaccion.set(null);

        } catch (HibernateException ex) {
            ex.printStackTrace();

        }
    }

    public static void beingTransaccion() {
        Transaction tx = (Transaction) threadTransaccion.get();
        try {
            if (tx == null) {
                tx = getSession().beginTransaction();
                System.out.println("BeginTransaction");
                threadTransaccion.set(tx);
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }
    }

    public static void commitTransaction() {
        Transaction tx = (Transaction) threadTransaccion.get();
        try {
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.commit();
                threadTransaccion.set(null);
            }
        } catch (HibernateException ex) {
            rollbackTransaction();
            ex.printStackTrace();
        }
    }

    public static void rollbackTransaction() {
        Transaction tx = (Transaction) threadTransaccion.get();
        try {
            threadTransaccion.set(null);
            if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
                tx.rollback();
            }
        } catch (HibernateException ex) {
            ex.printStackTrace();
        }

    }
}
