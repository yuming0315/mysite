package com.douzone.mysite.vo;

public class PageVo {
	private Long page=1L;
	private Long offset=5L;
	private Long pages;
//	private Long begin;
	private Long end;
	
	public Long getPages() {
		return pages;
	}
	public void setPages(Long count) {
		this.pages = Long.valueOf( (long) Math.ceil(count/(double)offset));
	}
	
	public void addPage(String m) {
		if(m!=null) {
			Long move = Long.parseLong(m);
			setPage(page += ( (page + move > 0)&&( page + move <= pages ) ? move : 0));
		}
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long L) {
		this.page = L;
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
	
	public Long getBegin() {
		return page<3L ? 1L : (page+2L < pages ? pages-4L : page-2L);
	}
	public Long getEnd() {
		return page<3L ? (pages > 5L ? 5L :pages) : (page + 2L < pages ? page + 2L : pages );
	}
}
