package com.douzone.mysite.web.mvc.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("new-book".equals(actionName)) {
			action = new NewBookAction();
		}else if("write".equals(actionName)) {
			action = new WriteAction();
		}else if("view".equals(actionName)) {
			action = new ViewAction();
		}else if("modify".equals(actionName)) {
			action = new ModifyAction();
		}else if("update".equals(actionName)) {
			action = new UpdateAction();
		}else if("reply".equals(actionName)) {
			action = new ReplyAction();
		}else if("replyform".equals(actionName)) {
			action = new ReplyFormAction();
		}else if("delete".equals(actionName)) {
			action = new DeleteAction();
		}else {
			action = new ListAction();
		}

		return action;
	}

}
