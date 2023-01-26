package com.douzone.mysite.vo;

public class PageVo {
	private Long page=1L;
	private Long offset=5L;
	private Long pages;
	
	public Long getPages() {
		return pages;
	}
	public void setPages(Long count) {
		this.pages = Long.valueOf( (long) Math.ceil(count/(double)offset));
	}
	
	public void addPage(String m) {
		if(m!=null) {
			Long move = Long.parseLong(m);
			page += ( (page + move > 0)&&( page + move <= pages ) ? move : 0);
		}
	}
	public Long getPage() {
		return page;
	}
	public void setPage(String p) {
		this.page = (p == null ? page : Long.parseLong(p));
	}
	public Long getOffset() {
		return offset;
	}
	public void setOffset(String o) {
		this.offset = (o == null ? offset : Long.parseLong(o));
	}
}
