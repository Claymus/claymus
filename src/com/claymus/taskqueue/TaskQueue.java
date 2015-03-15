package com.claymus.taskqueue;

import java.util.List;

public interface TaskQueue {

	void add( Task task );

	void add( List<Task> taskList );
	
}
