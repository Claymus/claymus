package com.claymus.data.access;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.claymus.commons.shared.exception.UnexpectedServerException;
import com.google.appengine.api.search.Cursor;
import com.google.appengine.api.search.Document;
import com.google.appengine.api.search.Index;
import com.google.appengine.api.search.IndexSpec;
import com.google.appengine.api.search.MatchScorer;
import com.google.appengine.api.search.PutException;
import com.google.appengine.api.search.Query;
import com.google.appengine.api.search.QueryOptions;
import com.google.appengine.api.search.Results;
import com.google.appengine.api.search.ScoredDocument;
import com.google.appengine.api.search.SearchService;
import com.google.appengine.api.search.SearchServiceFactory;
import com.google.appengine.api.search.SortOptions;
import com.google.appengine.api.search.StatusCode;

public class SearchAccessorGaeImpl implements SearchAccessor {

	private static final Logger logger =
			Logger.getLogger( SearchAccessorGaeImpl.class.getName() );

	private static final SearchService searchService =
			SearchServiceFactory.getSearchService();

	private final Index searchIndex;

	
	public SearchAccessorGaeImpl( String indexName ) {
		searchIndex = searchService.getIndex( IndexSpec.newBuilder().setName( indexName ) );
	}

	
	public Results<ScoredDocument> search(
			String searchQuery, SortOptions sortOptions, String cursorStr,
			Integer resultCount, String... fieldsToReturn ) {
		
		if( sortOptions == null )
			sortOptions = SortOptions.newBuilder()
					.setMatchScorer( MatchScorer.newBuilder() )
					.setLimit( 1000 )
					.build();
		
		QueryOptions queryOptions = QueryOptions.newBuilder()
				.setSortOptions( sortOptions )
				.setCursor( cursorStr == null ? Cursor.newBuilder().build() : Cursor.newBuilder().build( cursorStr ) )
				.setLimit( resultCount == null ? 1000 : resultCount )
				.setFieldsToReturn( fieldsToReturn )
				.build();
		
		Query query = Query.newBuilder()
				.setOptions( queryOptions )
				.build( searchQuery );

		logger.log( Level.INFO, "Search Query: " + query.toString() );

	    return searchIndex.search( query );
	}

	protected void indexDocument( Document document ) throws UnexpectedServerException {
		List<Document> documentList = new ArrayList<>( 1 );
		documentList.add( document );
		indexDocumentList( documentList );
	}
	
	protected void indexDocumentList( List<Document> documentList ) throws UnexpectedServerException {
		for( int i = 0; i < documentList.size(); i = i + 200 ) {
			try {
				searchIndex.put( documentList.subList( i, i + 200 > documentList.size() ? documentList.size() : i + 200 ) );
			} catch( PutException e ) {
				if( StatusCode.TRANSIENT_ERROR.equals( e.getOperationResult().getCode() ) ) {
					logger.log( Level.WARNING, "Failed to update search index. Retrying ...", e );
					try {
						Thread.sleep( 100 );
						i = i - 200;
						continue;
					} catch( InterruptedException ex ) {
						// Do nothing
					}
				} else {
					logger.log( Level.SEVERE, "Failed to update search index.", e );
					throw new UnexpectedServerException();
				}
			}
		}
	}

	protected void deleteIndex( String docId ) {
		searchIndex.delete( docId );
	}
	
}
