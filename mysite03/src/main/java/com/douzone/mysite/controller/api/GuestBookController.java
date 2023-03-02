package com.douzone.mysite.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.douzone.mysite.dto.JsonResult;
import com.douzone.mysite.service.GuestbookService;
import com.douzone.mysite.vo.GuestBookVo;

@RestController("guestbookApiController")
@RequestMapping("/guestbook/api")
public class GuestBookController {

	@Autowired
	private GuestbookService guestbookService;
	
	//추가하기
	@PostMapping("")
	public JsonResult insert(@RequestBody GuestBookVo vo) {
		guestbookService.addMessage(vo);
		// selectkey를 이용해 vo에 no값 받아왔음
		vo.setPassword("");
		
		return JsonResult.success(vo);
	}
	
	//리스트 읽어오기
	@GetMapping("")
	public JsonResult read(@RequestParam(value="sno",required=true,defaultValue="0")Long startNo) {
		List<GuestBookVo> list = new ArrayList<>();
		list = guestbookService.findSno(startNo);
		
		return JsonResult.success(list);
	}
	
	@DeleteMapping("")
	public JsonResult delete(@RequestBody GuestBookVo vo) {
		System.out.println(vo);
		
		if(guestbookService.deleteMessage(vo)) {
			return JsonResult.success(vo);
		}
		
		return JsonResult.fail("비밀번호 불일치");
	}
}
