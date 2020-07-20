package com.luv2code.springdemo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.luv2code.springdemo.entity.Customer;

@Repository
public class CustomerDAOImpl implements CustomerDAO {

	//Need to inject the hibernate session factory so that DAO can use it to talk to the Database 
	@Autowired 
	SessionFactory sessionFactory;
	
	@Override
	//@Transactional Removed this after adding the service layer
	public List<Customer> getCustomers() {
		
		// Get the current hibernate session
		Session currentSession=sessionFactory.getCurrentSession();
		
		// Create a query ..sort by Last Name
		Query<Customer> theQuery=currentSession.createQuery("from Customer order by lastName"
				,Customer.class);
		
		// Execute Query and get result list
		List<Customer> customers=theQuery.getResultList();
		
		//return the results
		return customers;
	}

	@Override
	public void saveCustomer(Customer theCustomer) {
		
		//get the current hibernate session
		Session currentSession=sessionFactory.getCurrentSession();
		
		//save the customer
		currentSession.saveOrUpdate(theCustomer);;
		
	}

	@Override
	public Customer getCustomer(int theId) {
		//get the current hibernate session
		Session currentSession=sessionFactory.getCurrentSession();
		
		//Now retrieve/get/read frrom the database using the primary key
		Customer theCustomer=currentSession.get(Customer.class, theId);
		
		return theCustomer;
		
	}

	@Override
	public void deleteCustomer(int theId) {
		//get the current hibernate session
		Session currentSession=sessionFactory.getCurrentSession();
		
		//delete the object with the primary key that is being passed in
		Query theQuery=currentSession.createQuery("delete from Customer where id=:customerId");
		theQuery.setParameter("customerId", theId);
		
		theQuery.executeUpdate();
	}

}
