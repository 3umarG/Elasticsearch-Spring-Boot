package com.elastich.elastichspringboot.DAO;

import com.elastich.elastichspringboot.model.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends ElasticsearchRepository<Book,Integer> {

}
