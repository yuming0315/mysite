package com.douzone.mysite.web.mvc.gb;

import com.douzone.mysite.web.mvc.main.MainAction;
import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class GuestBookActionFactory extends ActionFactory{

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("deleteform".equals(actionName)) {
			action = new GuestBookDeleteFormAction();
		}else if("delete".equals(actionName)) {
			action = new GuestBookDeleteAction();
		}else if("insert".equals(actionName)) {
			action = new GuestBookInsertAction();
		}else {
			action = new GuestBookListAction();
		}
		return action;
	}

}
