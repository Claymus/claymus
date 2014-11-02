package com.claymus.taskqueue;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;

public class TaskQueueGaeImpl implements TaskQueue {

	private final Queue queue;
	
	
	public TaskQueueGaeImpl( String taskQueueName ) {
		this.queue = QueueFactory.getQueue( taskQueueName );
	}

	
	public void add( Task task ) {
		queue.add( ((TaskGaeImpl) task).getTaskOptions() );
	}
	
}
