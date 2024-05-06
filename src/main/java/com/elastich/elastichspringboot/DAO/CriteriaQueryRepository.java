package com.elastich.elastichspringboot.DAO;

import com.elastich.elastichspringboot.model.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.query.Criteria;
import org.springframework.data.elasticsearch.core.query.CriteriaQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CriteriaQueryRepository {

    private final ElasticsearchOperations elasticsearchOperations;


    /**
     * Generated Query :
     * {
     *    "query":{
     *       "bool":{
     *          "should":[
     *             {
     *                "query_string":{
     *                   "analyze_wildcard":true,
     *                   "fields":[
     *                      "title"
     *                   ],
     *                   "query":"*Book*"
     *                }
     *             },
     *             {
     *                "query_string":{
     *                   "analyze_wildcard":true,
     *                   "fields":[
     *                      "author"
     *                   ],
     *                   "query":"*Book*"
     *                }
     *             }
     *          ]
     *       }
     *    },
     *    "size":3,
     *    "sort":[
     *       {
     *          "price":{
     *             "mode":"min",
     *             "order":"desc"
     *          }
     *       }
     *    ]
     * }
     */
    public List<SearchHit<Book>> searchByTitle(String q, int page, int size) {
        Criteria criteria =
                new Criteria("title").contains(q)
                        .or("author").contains(q);

        Query searchQuery = new CriteriaQuery(criteria, PageRequest.of(page, size))
                .addSort(Sort.by(Sort.Direction.DESC, "price"));

        return elasticsearchOperations
                .search(searchQuery, Book.class)
                .stream().toList();
    }
}
