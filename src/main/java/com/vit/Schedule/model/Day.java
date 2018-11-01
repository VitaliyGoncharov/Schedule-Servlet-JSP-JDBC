package com.vit.Schedule.model;

public class Day {
	private int id;
	private String name;
	private int orderNum;
	
	public Day() {
		super();
	}

	public Day(int id, String name, int orderNum) {
		super();
		this.id = id;
		this.name = name;
		this.orderNum = orderNum;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + orderNum;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Day other = (Day) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orderNum != other.orderNum)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Day [id=" + id + ", name=" + name + ", orderNum=" + orderNum + "]";
	}
}
