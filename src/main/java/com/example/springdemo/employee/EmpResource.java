package com.example.springdemo.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class EmpResource {

    private static Logger log = LoggerFactory.getLogger(EmpResource.class);

    @Autowired
    private EmpService empService;


    @RequestMapping("/employee")
    public List<Employee> getAll(){
        return empService.getAll();
    }

    @RequestMapping(method = RequestMethod.POST, value="/employee")
    public void insert(@RequestBody Employee emp){
        empService.insertEmployee(emp);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/employee/{id}")
    public void put(@RequestBody Employee emp,@PathVariable int id){
        empService.putEmployee(emp,id);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/employee/{id}")
    public void deleteEmployee(@PathVariable int id){
        empService.deleteEmployee(id);
    }





    @RequestMapping(method = RequestMethod.POST, value="/employee/transfervalue")
    public void putCondition(@RequestParam(name="id1")  int id1,@RequestParam(name="id2")  int id2, @RequestParam(name="amt") int transfer){
        empService.putCondition(id1,id2,transfer);
    }



    @RequestMapping(method=RequestMethod.POST,value ="/employee/insert")
    public void insertBatch(@RequestBody List<Employee> l){
        empService.insertBatch(l);
    }



    @RequestMapping(method = RequestMethod.POST,value="/employee/AddBonus/{id}")
    public void AddBonus(@PathVariable int id){
        empService.AddBonus(id);
    }


    @RequestMapping(method = RequestMethod.POST,value = "/employee/AddBonus1/{id}")
    public void AddBonusAsync(@PathVariable int id)throws InterruptedException, ExecutionException{
        log.info("AddBonusAsynch Start");

        CompletableFuture<String> getBonus = empService.getBonus(id);
        CompletableFuture<String> getSalary = empService.getSalary(id);

        CompletableFuture.allOf(getBonus, getSalary).join();

        log.info("Bonus + Salary--> " + getBonus.get());
        log.info("Total Salary--> " + getSalary.get());

        log.info("() Ends");
    }
}
