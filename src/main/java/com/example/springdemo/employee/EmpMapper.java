package com.example.springdemo.employee;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Mapper
public interface EmpMapper {




    @Select("Select * from employee")
    List<Employee> getAll();


    @Insert("insert into employee(name,salary,designation) values(#{name},#{salary},#{designation})")
    void insertEmployee(Employee emp);

    @Update("Update employee set name=#{emp.name}, salary=#{emp.salary},designation = #{emp.designation} where id=#{id}")
    void putEmployee(Employee emp,int id);




    @Insert({
            "<script>",
            "insert into employee (name,salary,designation)",
            "values ",
            "<foreach  collection='EmpList' item='emp' separator=','>",
            "( #{emp.name,jdbcType=VARCHAR}, #{emp.salary,jdbcType=INTEGER},#{emp.designation,jdbcType=VARCHAR})",
            "</foreach>",
            "</script>"
    })
    int insertBatch(@Param("EmpList") List<Employee> EmpList);


    @Delete("Delete from employee where id=#{id}")
    void deleteEmployee(int id);



    @Update({"<script>",
            "update employee",
            "set",
            "salary=#{salary1}-#{amt}",
            "where id=#{id1};",
            "</script>"
    })
    void subValue(int id1,int salary1 ,int amt);


    @Update({"<script>",
            "update employee",
            "set",
            "salary = #{salary1} + #{amt}",
            "where id=#{id2};",
            "</script>"
    })
    void addValue(int id2,int salary1, int amt);

    @Select("Select salary from employee where id=#{id1}")
    int getSalary(int id1);

}
