package com.senyint.common.web.ajax;

public class GridResult {
	public GridResult() {
		
	}
	
	public GridResult(Integer page, Integer pageSize, Integer records, Object rows) {
		this.page = page;
		this.pageSize = pageSize;
		this.records = records;
		this.rows = rows;
	}
	
	private Integer page;
	private Integer total;
	private Integer records;
	private Object rows;
	private Integer pageSize;
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getTotal() {
		this.total = (records - 1 ) / this.pageSize + 1;
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getRecords() {
		return records;
	}
	public void setRecords(Integer records) {
		this.records = records;
	}
	public Object getRows() {
		return rows;
	}
	public void setRows(Object rows) {
		this.rows = rows;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
