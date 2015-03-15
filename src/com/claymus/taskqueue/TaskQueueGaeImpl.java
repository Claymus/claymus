package com.claymus.taskqueue;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskQueueGaeImpl implements TaskQueue {

	private final Queue queue;
	
	
	public TaskQueueGaeImpl( String taskQueueName ) {
		this.queue = QueueFactory.getQueue( taskQueueName );
	}


	@Override
	public void add( Task task ) {
		queue.add( ( (TaskGaeImpl) task ).getTaskOptions() );
	}
	
	@Override
	public void add( List<Task> taskList ) {
		List<TaskOptions> taskOptionsList = new ArrayList<TaskOptions>( taskList.size() );
		for( Task task : taskList )
			taskOptionsList.add( ( (TaskGaeImpl) task ).getTaskOptions() );
		queue.add( taskOptionsList );
	}
	
}
