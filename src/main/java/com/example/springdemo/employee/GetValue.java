package com.example.springdemo.employee;

import org.springframework.beans.factory.annotation.Autowired;

import javax.sound.sampled.FloatControl;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class GetValue implements Runnable {

    private int id;

    private EmpMapper empMapper;
    private Lock l;
    private Condition cond;

    public GetValue(int id, EmpMapper empMapper, Lock l, Condition cond){
        this.id=id;
        this.empMapper=empMapper;
        this.l=l;
        this.cond=cond;
    }

    @Override
    public void run() {
        try {
            l.lock();
            cond.await();
            System.out.println("Hi");
            System.out.println(empMapper.getSalary(id));
        }
        catch (InterruptedException e){
            System.out.println("Interrupted Exception = "+e);
        }
        finally {
            l.unlock();
        }
    }


}
