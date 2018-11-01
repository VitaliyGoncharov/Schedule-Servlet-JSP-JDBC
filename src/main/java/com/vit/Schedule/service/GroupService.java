package com.vit.Schedule.service;

import java.util.List;

import com.vit.Schedule.model.Group;
import com.vit.Schedule.model.Major;

public interface GroupService {
	List<Group> findAllByMajor(Major major);
	Group findById(int id);
}
