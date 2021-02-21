package com.br.qualitibanck.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.br.qualitibanck.model.Customer;

@Stateless
public class CustomerDao {

	protected EntityManager entityManager;
	private static CustomerDao instance;

	private CustomerDao() {
		entityManager = getEntityManager();
	}
	

	public static CustomerDao getInstance() {
		if (instance == null) {
			instance = new CustomerDao();
		}
		return instance;
	}

	private EntityManager getEntityManager() {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mysvb-jpa");
		if (entityManager == null) {
			entityManager = entityManagerFactory.createEntityManager();
		}
		return entityManager;
	}

	public void save(Customer customer) {
		try {
			entityManager.getTransaction().begin();
			entityManager.persist(customer);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
			System.out.println("PERSISTENCIA EFETUADA!!!!!!!!!!!!!");
		}

	}

	public void update(Customer customer) {
		try {
			entityManager.getTransaction().begin();
			entityManager.merge(customer);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public Customer getById(final int id) {
		return entityManager.find(Customer.class, id);
	}

	@SuppressWarnings("unchecked")
	public List<Customer> findAll() {
		return entityManager.createQuery("FROM " + Customer.class.getName()).getResultList();
	}

	public void remove(Customer customer) {
		try {
			entityManager.getTransaction().begin();
			customer = entityManager.find(Customer.class, customer.getId());
			entityManager.remove(customer);
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			entityManager.getTransaction().rollback();
		} finally {
			entityManager.close();
		}
	}

	public void removeById(final int id) {
		try {
			Customer customer = getById(id);
			remove(customer);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
