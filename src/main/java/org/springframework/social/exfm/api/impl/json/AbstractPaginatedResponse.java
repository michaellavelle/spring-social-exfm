package org.springframework.social.exfm.api.impl.json;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
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

	
	public PageImpl<T> createPage(Pageable pageable)
	{
		// Get the list of results, setting to be an empty list if its null
		List<T> resultsList = getNestedResponse() == null ? new ArrayList<T>() : getNestedResponse();
		
		// If our input pageable is null, but resultList is not empty, set up the most sensible return pageable based on the
		// "results" count or the size of the resultsList
		if (pageable == null && resultsList.size() > 0)
		{
			pageable =  new PageRequest(0,results == null ? getNestedResponse().size() : results.intValue());
		}
		
		// At this point the return pageable may or may not be null, if its null then we can only return a PageImpl
		// with page size = 0
		if (pageable == null)
		{
			return new PageImpl<T>(resultsList);
		}
		else
		{
			// Pageable isn't null, so return a page with non-zero page size, and most sensible value for results size
			long totalResults = (getTotal() == null) ? resultsList.size() : getTotal().longValue();
			return new PageImpl<T>(resultsList,pageable,totalResults);
		}
	}
	
	
	
	public Pageable getPageable(Pageable pageable,List<T> content)
	{
		if (pageable == null)
		{
			if (content.size() > 0)
			{
				return new PageRequest(0,results == null ? content.size() : results.intValue());
			}
			else
			{
				return null;
			}
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
