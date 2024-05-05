package com.elastich.elastichspringboot.DAO;

import com.elastich.elastichspringboot.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class NativeQueryRepository {

    private final ElasticsearchOperations elasticsearchOperations;

    /**
     * This one provides the maximum flexibility for constructing queries.
     * Native queries with the same convention of writing queries in elasticsearch
     * Generated Query :
     * "query":{"multi_match":{"fields":["title","author"],"query":"Author 1"}}
     *
     */
    public List<SearchHit<Book>> searchByFields(String searchTerm, List<String> fields) {
        Query query =
                NativeQuery.builder()
                        .withQuery(
                                q -> q.multiMatch(
                                        m -> m.fields(fields).query(searchTerm))).build();

        return elasticsearchOperations.search(query, Book.class)
                .stream().toList();
    }
}
