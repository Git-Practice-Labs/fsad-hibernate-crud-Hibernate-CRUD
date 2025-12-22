package com.demo.main;



	import org.hibernate.Session;
	import org.hibernate.Transaction;

	import com.demo.entity.Student;
	import com.demo.util.HibernateUtil;

	public class StudentCrudDemo {

	    public static void main(String[] args) {

	        // CREATE
	        saveStudent(new Student(2, "Anu", "Anu@test.com"));
	        

	        // READ
	        Student s = getStudent(2);
	        System.out.println(s.getName());
	        

	        // UPDATE
	       // updateStudent(2, "Anu");

	        // DELETE
	       // deleteStudent(2);
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

	   /* static void deleteStudent(int id) {
	        Session session = HibernateUtil.getSessionFactory().openSession();
	        Transaction tx = session.beginTransaction();
	        Student s = session.get(Student.class, id);
	        if (s != null) {
	            session.delete(s);
	        }
	        tx.commit();
	        session.close();
	    }*/
	}


