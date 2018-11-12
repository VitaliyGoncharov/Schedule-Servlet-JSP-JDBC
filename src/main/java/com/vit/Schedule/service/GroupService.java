package com.vit.Schedule.service;

import java.sql.SQLException;
import java.util.List;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Major;

public interface GroupService {
	List<Group> findAllByMajor(Major major);
	Group findById(int id);
	
	void add(Group group) throws SQLException;
}
