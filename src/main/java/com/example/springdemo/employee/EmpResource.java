package com.example.springdemo.employee;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EmpResource {

    @Autowired
    private EmpMapper empMapper;


    @RequestMapping("/employee")
    public List<Employee> getAll(){
        return empMapper.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, value="/employee")
    public void insert(@RequestBody Employee emp){
        empMapper.insertEmployee(emp);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/employee/{id}")
    public void put(@RequestBody Employee emp,@PathVariable int id){
        empMapper.putEmployee(emp,id);
    }
/*
    @RequestMapping(method = RequestMethod.PUT, value="/employee/condition/{id}")
    public void putcondition(@RequestBody Employee emp,@PathVariable int id){
        empMapper.putCondition(emp,id);
    }*/

    @RequestMapping(method = RequestMethod.DELETE, value="/employee/{id}")
    public void deleteEmployee(@PathVariable int id){
        empMapper.deleteEmployee(id);
    }

    @RequestMapping(method=RequestMethod.POST,value ="/employee/insert")
    public void addinsert(@RequestBody List<Employee> l){
        empMapper.insertBatch(l);
    }

}
