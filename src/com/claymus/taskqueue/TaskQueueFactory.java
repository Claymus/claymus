package com.claymus.taskqueue;

import java.util.HashMap;
import java.util.Map;

public class TaskQueueFactory {

	private static final Map<String, TaskQueue> taskQueueMap = new HashMap<>();
	
	private static final String QUEUE_INVITE_USER = "invite-user";
	private static final String QUEUE_WELCOME_USER = "welcome-user";
	private static final String QUEUE_RESET_PASSWORD = "reset-password";
	private static final String QUEUE_USER = "user";
	
	
	public static TaskQueue getInviteUserTaskQueue() {
		return getTaskQueue( QUEUE_INVITE_USER );
	}

	public static TaskQueue getWelcomeUserTaskQueue() {
		return getTaskQueue( QUEUE_WELCOME_USER );
	}

	public static TaskQueue getResetPasswordTaskQueue() {
		return getTaskQueue( QUEUE_RESET_PASSWORD );
	}
	
	public static TaskQueue getUserTaskQueue() {
		return getTaskQueue( QUEUE_USER );
	}

	protected static TaskQueue getTaskQueue( String taskQueueName ) {
		TaskQueue taskQueue = taskQueueMap.get( taskQueueName );
		if( taskQueue == null ) {
			taskQueue = new TaskQueueGaeImpl( taskQueueName );
			taskQueueMap.put( taskQueueName, taskQueue );
		}
		return taskQueue;
	}
	
	public static Task newTask() {
		return new TaskGaeImpl();
	}
	
}
