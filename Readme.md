
What is Hibernate? 

Hibernate is a Java framework that:
Converts Java objects into database rows
Eliminates manual SQL coding
Works on top of JDBC

You write Java code → Hibernate writes SQL → Database stores data

A simple Student CRUD application using:

Hibernate (Session API)
MySQL database

Maven project

Eclipse IDE

3. Tools Required

Prerequisites:

JDK 21

Eclipse IDE for Java Developers

MySQL Server 8.x (running)

4. Database Setup
Open MySQL Workbench :

CREATE DATABASE studentdb;

USE studentdb;

CREATE TABLE student (
    id INT PRIMARY KEY,
    name VARCHAR(100),
    email VARCHAR(100)
);


5. Project Creation (Maven)

In Eclipse:

File → New → Maven Project

Next → Check Create a simple project

Next

Group Id: com.demo

Artifact Id: hibernate-mysql-demo

Finish

6. Maven Dependencies (pom.xml)

Maven automatically downloads required libraries.

Add the following dependencies:

<dependencies>

    <dependency>
        <groupId>org.hibernate.orm</groupId>
        <artifactId>hibernate-core</artifactId>
        <version>6.4.4.Final</version>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <version>8.4.0</version>
    </dependency>

    <dependency>
        <groupId>jakarta.persistence</groupId>
        <artifactId>jakarta.persistence-api</artifactId>
        <version>3.1.0</version>
    </dependency>

</dependencies>


7. What is hibernate.cfg.xml?

hibernate.cfg.xml is the main configuration file where we define:

Database connection:

Username & password


Hibernate behavior

Entity mapping

Hibernate reads this file at startup.

8. hibernate.cfg.xml Configuration

Create file at:


src/main/resources/hibernate.cfg.xml



<?xml version="1.0" encoding="UTF-8"?>
<hibernate-configuration>
    <session-factory>

        <property name="hibernate.connection.driver_class">
            com.mysql.cj.jdbc.Driver
        </property>

        <property name="hibernate.connection.url">
            jdbc:mysql://localhost:3306/studentdb
        </property>

        <property name="hibernate.connection.username">root</property>
        <property name="hibernate.connection.password">password</property>

        <property name="hibernate.dialect">
            org.hibernate.dialect.MySQLDialect
        </property>

        <property name="hibernate.hbm2ddl.auto">validate</property>

        <property name="hibernate.show_sql">true</property>

        <mapping class="com.demo.entity.Student"/>

    </session-factory>
</hibernate-configuration>

9. What is HibernateUtil?

HibernateUtil:

Creates SessionFactory only once

Provides Hibernate sessions to the application

Acts as a helper class

SessionFactory is heavy → create once → reuse everywhere.

10. HibernateUtil.java


package com.demo.util;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class HibernateUtil {
    private static final SessionFactory factory =
            new Configuration().configure().buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        return factory;
    }
}
11. Student Entity

Hibernate maps this class to the student table.

package com.demo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student")
public class Student {

    @Id
    private int id;

    private String name;
    private String email;

    public Student() {}

    public Student(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

}


// getters and setters
12. CRUD Operations Workflow

Each operation follows the same pattern:

Open Hibernate session

Begin transaction

Perform operation

Commit transaction

Close session

13. CRUD Demo (main Class)

package com.demo.main;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.demo.entity.Student;
import com.demo.util.HibernateUtil;

public class StudentCrudDemo {

    public static void main(String[] args) {

        saveStudent(new Student(1, "Ravi", "ravi@test.com"));

        Student s = getStudent(1);
        System.out.println(s.getName());

        updateStudent(1, "Ravi Kumar");

        deleteStudent(1);
    }

    static void saveStudent(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(student);
        tx.commit();
        session.close();
    }

    static Student getStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Student s = session.get(Student.class, id);
        session.close();
        return s;
    }

    static void updateStudent(int id, String name) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student s = session.get(Student.class, id);
        if (s != null) {
            s.setName(name);
        }
        tx.commit();
        session.close();
    }

    static void deleteStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student s = session.get(Student.class, id);
        if (s != null) {
            session.delete(s);
        }
        tx.commit();
        session.close();
    }
}


14. How to Run
Right-click StudentCrudDemo
Run As → Java Application

Verify data changes in MySQL

15. End-to-End Flow 

Java Class
   ↓
Hibernate Session
   ↓
JDBC Driver
   ↓
MySQL Database


16.Checklist

Maven project created
Hibernate configured
CRUD executed successfully
SQL visible in console
Data verified in MySQL
