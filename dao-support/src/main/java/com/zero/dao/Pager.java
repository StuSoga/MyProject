package com.zero.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zero on 16/3/1.
 */
public class Pager<T> implements Serializable {

	private int pageNo = 1;

	private int pageSize = -1;

	private List<T> results = null;

	private long totalCount = -1;

	private boolean autoCount = true;

	public Pager() {
		this.results = new ArrayList<T>();
	}

	public Pager(int pageSize) {
		this.results = new ArrayList<T>();
		this.setPageSize(pageSize);
	}

	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;
		if (this.pageNo < 1) {
			this.pageNo = 1;
		}
	}

	public int getPageNo() {
		return this.pageNo;
	}

	public void setPageSize(final int pageSize) {

		this.pageSize = pageSize;

		if (this.pageSize < 1) {
			this.pageSize = 20;
		}
	}

	public int getPageSize() {

		return this.pageSize;

	}

	public int getFirst() {
		return (this.pageNo - 1) * this.pageSize + 1;
	}

	public void setResults(List<T> results) {
		this.results = results;
	}

	public List<T> getResults() {
		return this.results;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
		if (this.getTotalPages() < this.pageNo) {
			this.pageNo = Long.valueOf(this.getTotalPages()).intValue();
		}
	}

	public long getTotalCount() {
		return this.totalCount;
	}

	public boolean isAutoCount() {
		return this.autoCount;
	}

	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}

	public long getTotalPages() {
		if (this.totalCount < 0) {
			return -1;
		}

		long count = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0) {
			count++;
		}
		return count;
	}

	public boolean isHasNext() {
		return (this.pageNo + 1 <= this.getTotalPages());
	}

	public int getNextPage() {
		if (this.isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}

	public boolean isHasPre() {
		return (this.pageNo - 1 >= 1);
	}

	public int getPrePage() {
		if (this.isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}

	public void copyExcludeResultsFrom(Pager<?> sourcePager) {
		this.setPageNo(sourcePager.getPageNo());
		this.setPageSize(sourcePager.getPageSize());
		this.setTotalCount(sourcePager.getTotalCount());
		this.setAutoCount(sourcePager.isAutoCount());
	}
}
