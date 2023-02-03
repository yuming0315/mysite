package com.douzone.mysite.vo;

public class PageVo {
	private Long page=1L;
	private Long offset=5L;
	private Long pages;
//	private Long begin;
//	private Long end;
	
	public Long getPages() {
		return pages;
	}
	public void setMaxPages(Long count) {
		this.pages = Long.valueOf( (long) Math.ceil(count/(double)offset));
		if(pages<page) {
			page = pages;
		}
	}
	public boolean isLong(String str) {
		return str!=null && !"".equals(str);
	}
	
	public void addPage(String m) {
		if(isLong(m)) {
			Long move = Long.parseLong(m);
			setPage((page += ( page + move > 0 && pages>=page+move ? move : 0 ))+"");
		}
	}
	public Long getPage() {
		return page;
	}
//	public void setPage(Long L) {
//		this.page = pages>L ? L : pages;
//	}
	public void setPage(String p) {
		if(isLong(p)) {
			Long L = Long.parseLong(p);
			this.page = L;
		}
	}
	public Long getOffset() {
		return offset;
	}
	public void setOffset(Long o) {
//		this.offset = ( isLong(o) ? offset : Long.parseLong(o));
		this.offset = o;
	}
	
	public Long getBegin() {
		return page<3L ? 1L : (page+2L > pages ? pages-4L : page-2L);
	}
	public Long getEnd() {
		return page<3L ? (pages > 5L ? 5L :pages) : (page + 2L < pages ? page + 2L : pages );
	}
	public String getLimit() {
		return (page-1L)*offset+","+offset;
	}
}
