package com.claymus.commons.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public enum CommentState implements IsSerializable{

	PENDING_APPROVAL,
	DELETED,
	APPROVED,
	AUTO_APPROVED,

}
