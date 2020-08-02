package com.example.springdemo.employee;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class EmpService {

    @Autowired
    private EmpMapper empMapper;

    private static Logger log = LoggerFactory.getLogger(EmpService.class);


    public List<Employee> getAll(){
        return empMapper.getAll();
    }

    public void insertEmployee( Employee emp){
        empMapper.insertEmployee(emp);
    }

    public void putEmployee(Employee emp,int id){
        empMapper.putEmployee(emp,id);
    }

    public void deleteEmployee(int id){
        empMapper.deleteEmployee(id);
    }


    @Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT, rollbackFor=Exception.class)
   public void putCondition(int id1,int id2, int transfer){
        int salary = empMapper.getSalary(id1);
        if(salary > transfer) {
            try {
                empMapper.subValue(id1, salary, transfer);
                salary = empMapper.getSalary(id2);
            }
            catch (Exception e){
                throw e;
            }
            empMapper.addValue(id2, salary, transfer);
        }
    }



    public void insertBatch(@RequestBody List<Employee> l){
        empMapper.insertBatch(l);
    }


    public void AddBonus(int id){

        Lock l=new ReentrantLock();

        Condition cond = l.newCondition();

        ExecutorService e = Executors.newFixedThreadPool(2);

        e.submit(new GetValue(id,empMapper,l,cond));
        e.submit(new AddRandomBonus(id,empMapper,l,cond));


    }


    public CompletableFuture<String> getBonus(int id) throws InterruptedException
    {

        log.info("Get Bonus started");
        int salary = empMapper.getSalary(id);
        Random r = new Random();
        int x = r.nextInt(1000);
        log.info("Bonus is "+x);
        empMapper.addValue(id, salary, x);

        log.info("Bonus Added \n Ending getBonus()");

        return CompletableFuture.completedFuture(salary+x+"");
    }

    public CompletableFuture<String> getSalary(int id) throws InterruptedException
    {

        log.info("Get Salary started");
        int salary = empMapper.getSalary(id);

        log.info("New Salary = "+salary);
        log.info(" Ending getSalary()");

        return CompletableFuture.completedFuture(salary+"");
    }

}
