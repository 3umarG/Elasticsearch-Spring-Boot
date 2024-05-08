package com.elastich.elastichspringboot.DAO;

import co.elastic.clients.elasticsearch._types.query_dsl.Query.Builder;
import co.elastic.clients.util.ObjectBuilder;
import com.elastich.elastichspringboot.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Function;

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
     */
    public List<SearchHit<Book>> searchByFields(String searchTerm, List<String> fields, int page, int size) {
        Query query =
                NativeQuery.builder()
                        .withQuery(buildQueryString(searchTerm, fields))
                        .withPageable(PageRequest.of(page, size)).build();

        return elasticsearchOperations.search(query, Book.class)
                .stream().toList();
    }

    private static Function<Builder, ObjectBuilder<co.elastic.clients.elasticsearch._types.query_dsl.Query>> buildQueryString(String searchTerm, List<String> fields) {
        return q -> q.multiMatch(
                m -> m.fields(fields)
                        .query(searchTerm)
        );
    }
}
