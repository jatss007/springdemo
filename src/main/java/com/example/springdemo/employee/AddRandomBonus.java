package com.example.springdemo.employee;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AddRandomBonus implements Runnable {

    private int id;
    private EmpMapper empMapper;
    private Lock l;
    private Condition cond;

    public AddRandomBonus(int id, EmpMapper empMapper, Lock l, Condition cond){
        this.id=id;
        this.empMapper=empMapper;
        this.l=l;
        this.cond=cond;
    }

    @Override
    public void run() {


        l.lock();
        try {
            int salary = empMapper.getSalary(id);
            Random r = new Random();
            int x = r.nextInt(1000);
            System.out.println(x);
            empMapper.addValue(id, salary, x);
        }
        finally {
            cond.signal();
            l.unlock();
        }
    }

}
