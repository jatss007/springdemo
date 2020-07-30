package com.example.springdemo.employee;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
