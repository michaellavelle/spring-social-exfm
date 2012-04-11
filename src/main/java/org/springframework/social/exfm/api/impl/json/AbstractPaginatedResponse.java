package org.springframework.social.exfm.api.impl.json;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public abstract class AbstractPaginatedResponse<T,L extends List<T>> extends
		AbstractExFmNamedNestedResponse<L> {

	private Long start;
	private Long results;
	private Long total;
	
	public Long getStart() {
		return start;
	}
	
	public Pageable getPageable(Pageable pageable,List<T> content)
	{
		if (pageable == null)
		{
			return new PageRequest(0,results == null ? content.size() : results.intValue());
		}
		else
		{
			return pageable;
		}
		
	}
	
	
	public void setStart(Long start) {
		this.start = start;
	}
	public Long getResults() {
		return results;
	}
	public void setResults(Long results) {
		this.results = results;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	
	
}
