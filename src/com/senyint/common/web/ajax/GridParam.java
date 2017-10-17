package com.senyint.common.web.ajax;

public class GridParam {
	
	public static final Integer DEFAULT_PAGE = 1;
	public static final Integer DEFAULT_ROWS = 10;
	
	public GridParam() {
		
	}
	
	public GridParam(Integer page, Integer rows) {
		this.page = page;
		this.rows = rows;
	}
	
	/**
	 * pageNo 必须
	 */
	private Integer page;
	
	/**
	 * pageSize 必须
	 */
	private Integer rows;
	
	/**
	 * 开始行数
	 */
	private Integer startRow;
	
	/**
	 * 结束行数
	 */
	private Integer endRow;
	
	/**
	 * pageNo 必须
	 * 
	 * @return
	 */
	public Integer getPage() {
		if (page == null) {
			page = DEFAULT_PAGE;
		}
		return page;
	}
	
	/**
	 * pageNo 必须
	 * 
	 * @param page
	 */
	public void setPage(Integer page) {
		this.page = page;
	}

	/**
	 * pageSize 必须
	 * 
	 * @return
	 */
	public Integer getRows() {
		if (rows == null) {
			rows = DEFAULT_ROWS;
		}
		return rows;
	}

	/**
	 * pageSize 必须
	 * 
	 * @param rows
	 */
	public void setRows(Integer rows) {
		this.rows = rows;
	}

	/**
	 * 开始行数
	 * 
	 * @return
	 */
	public Integer getStartRow() {
		startRow = (page -1) * rows;
		return startRow;
	}

	/**
	 * 开始行数
	 * 
	 * @param startRow
	 */
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	/**
	 * 结束行数
	 * 
	 * @return
	 */
	public Integer getEndRow() {
		endRow = page * rows;
		return endRow;
	}

	/**
	 * 结束行数
	 * 
	 * @param endRow
	 */
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
}
