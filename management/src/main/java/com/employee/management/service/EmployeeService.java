package com.employee.management.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.management.entity.Employee;
import com.employee.management.repo.EmployeeRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	
	public void save(Employee employee) {
		employeeRepository.save(employee);
	}
	
	public Employee getDetails(Integer id) {
		// TODO Auto-generated method stub
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			 Employee e = emp.get();
			 double tax = getTaxCalculated(e);
			 e.setTax(tax);
			 return e;
		}
		return null;
	}
	
	private double getTaxCalculated(Employee e) {
		 LocalDate joining = e.getDoj();
		 LocalDate till = null;
		 if(joining.getMonthValue() <= 3) {
		    	 till = LocalDate.of(joining.getYear(), 3, 31);
		 }else {
			     till = LocalDate.of(joining.getYear()+1, 3, 31);
		 }
		 long totalDays = ChronoUnit.DAYS.between(joining, till) + 1;
		 long perDaySalary = e.getSalary()/30;
		 long salaryForFinYr = totalDays * perDaySalary;
		 double cess  = 0.0D;
		 if(salaryForFinYr > 2500000) {
			 cess = (salaryForFinYr - 2500000)*0.02;
		 }
		 long tax = 0;
		 while(salaryForFinYr > 250000) {
			  if(salaryForFinYr > 250000 && salaryForFinYr <= 500000) {
				 tax += 0.05*(salaryForFinYr-250000);
				 salaryForFinYr =  salaryForFinYr - (salaryForFinYr - 250000);
			 }
			 else if(salaryForFinYr > 500000 && salaryForFinYr <= 1000000) {
				 tax += 0.1 *(salaryForFinYr-500000);
				 salaryForFinYr = salaryForFinYr - (salaryForFinYr - 500000);
			 }
			 else if(salaryForFinYr >1000000) {
				 tax += 0.2 * (salaryForFinYr-1000000);
				 salaryForFinYr = salaryForFinYr - (salaryForFinYr - 1000000);
			 }
		 }
		 return tax+cess;
	}
	
	
}
