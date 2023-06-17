package com.employee.management.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.employee.management.entity.Employee;
import com.employee.management.service.EmployeeService;

@RestController
public class EmployeeController {

	  @Autowired
	  private EmployeeService service;
	
	  @PostMapping("/save")
	  public ResponseEntity<Object> createEmployee(@RequestBody Employee employee) {
		 try { 
		   boolean valid = isValid(employee);
		   if(!valid) {
			  return ResponseEntity.badRequest().build();
		   }
		   service.save(employee);
		 }catch(Exception e) {
			 return ResponseEntity.internalServerError().build();
		 }
		 return ResponseEntity.ok().build();
	  }
	  
	  @GetMapping("/get/{id}")
	  public ResponseEntity<Object> getDetails(@PathVariable int id) {
		 Employee e = service.getDetails(id);
		 if(e != null)
			 return ResponseEntity.ok(e);
		 return ResponseEntity.notFound().build();
	  }
	  
	  
	  private boolean isValid(Employee employee) {
		  String s= employee.getPhoneNumber();
		  String p[] = s.split(";");
		  for(String phone : p) {
			if(phone.length() != 10 || employee.getEmail().indexOf("@") == -1 || employee.getEmail().indexOf(".") == -1) {
			  return false;
			} 
			try {
				Long.parseLong(phone);
			}catch(NumberFormatException e) {
				return false;
			}
		  }
		  return true;
	  }
	
	
	

}
