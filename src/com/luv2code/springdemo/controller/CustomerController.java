package com.luv2code.springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.luv2code.springdemo.entity.Customer;
import com.luv2code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	/*
	 * Commented after adding the service layer
	 * //Need to inject the customer DAO
	 * 
	 * @Autowired private CustomerDAO customerDAO;
	 */
	
	
	//Need to inject the customer service
	@Autowired
	private CustomerService customerService;
	
	
	@GetMapping("/list")
	public String listCustomers(Model theModel)
	{
		/*
		 * Commented after addign the service layer
		 * //get the customers from the DAO List<Customer>
		 * theCustomers=customerDAO.getCustomers();
		 */
		
		//get the customers from the service
		List<Customer> theCustomers=customerService.getCustomers();
		
		
		//Add the customers to the model
		theModel.addAttribute("customers",theCustomers);
		
		return "list-customers";
	}
	
	@GetMapping("/showFormAdd")
	public String showFormForAdd(Model theModel)
	{
		//create model attribute to bind form data
		Customer theCustomer=new Customer();
		
		theModel.addAttribute("customer", theCustomer);
		
		return "customer-form";
	}
	
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer") Customer theCustomer)
	{
		//save the customer using our service
		customerService.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId,
									Model theModel)
	{
		//get the customer from our Service
		Customer theCustomer=customerService.getCustomer(theId);
		
		//set customer as a model attribute to pre-populate the form
		theModel.addAttribute("customer", theCustomer);
		
		//send over to the form
		return "customer-form";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("customerId") int theId)
	{
		//delete the customer
		customerService.deleteCustomer(theId);
		return "redirect:/customer/list";
	}
	
	
	
}
	
