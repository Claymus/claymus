package com.claymus.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum NotificationType implements IsSerializable {

	REVIEW_ADD,
	REVIEW_UPDATE,
	REVIEW_UPVOTE,
	REVIEW_DOWNVOTE,
	COMMENT_ADD,
	COMMENT_UPDATE,
	COMMENT_UPVOTE,
	COMMENT_DOWNVOTE
	
}
