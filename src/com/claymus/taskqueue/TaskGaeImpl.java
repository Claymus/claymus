package com.claymus.taskqueue;

import com.google.appengine.api.taskqueue.TaskOptions;

public class TaskGaeImpl implements Task {

	private TaskOptions taskOptions = TaskOptions.Builder.withDefaults();

	
	@Override
	public Task addParam( String paramName, String value ) {
		taskOptions.param( paramName, value );
		return this;
	}

	TaskOptions getTaskOptions() {
		return this.taskOptions;
	}
	
}
