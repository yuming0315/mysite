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
		}else {
			action = new ListAction();
		}

		return action;
	}

}
