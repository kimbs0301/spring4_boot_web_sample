package com.example.spring.logic.cassandra.model;

import java.util.Date;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

/**
 * @author gimbyeongsu
 * 
 */
@Table(value = "person")
public class Person {
	@PrimaryKey(value = "id")
	private String id;
	@Column(value = "event_time")
	private Date event_time;
	@Column(value = "name")
	private String name;
	@Column(value = "age")
	private int age;

	public Person(String id, Date event_time, String name, int age) {
		this.id = id;
		this.event_time = event_time;
		this.name = name;
		this.age = age;
	}

	public String getId() {
		return id;
	}

	public Date getEvent_time() {
		return event_time;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", event_time=" + event_time + ", name=" + name + ", age=" + age + "]";
	}
}