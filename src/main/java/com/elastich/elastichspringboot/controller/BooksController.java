package com.elastich.elastichspringboot.controller;

import com.elastich.elastichspringboot.DAO.BooksRepository;
import com.elastich.elastichspringboot.DAO.CriteriaQueryRepository;
import com.elastich.elastichspringboot.DAO.NativeQueryRepository;
import com.elastich.elastichspringboot.model.Book;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Slf4j
public class BooksController {

    private final BooksRepository booksRepository;
    private final CriteriaQueryRepository criteriaRepo;
    private final NativeQueryRepository nativeQueryRepo;
    private static final AtomicInteger counter = new AtomicInteger(0);

    @GetMapping("/log")
    public ResponseEntity<Integer> testLogs() {
        log.info(counter.getAndIncrement() + "- Testing Logs .............");
        return ResponseEntity.ok(counter.get());
    }

    @GetMapping
    public ResponseEntity<Iterable<Book>> getAllBooks() {
        return ResponseEntity.ok(booksRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {
        var book = booksRepository.findById(id);
        return book.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        var savedBook = booksRepository.save(book);
        return ResponseEntity.ok(savedBook);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Integer id, @RequestBody Book book) {
        return booksRepository.findById(id).map(b -> {

            b.setAuthor(book.getAuthor());
            b.setTitle(book.getTitle());
            b.setPrice(book.getPrice());
            b.setContent(book.getContent());

            b = booksRepository.save(b);

            return ResponseEntity.ok(b);
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Integer id) {
        return booksRepository
                .findById(id).map(b -> {
                    booksRepository.delete(b);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/native-query")
    public ResponseEntity<List<SearchHit<Book>>> searchByNativeQuery(@RequestParam String q,
                                                                     @RequestParam Integer page,
                                                                     @RequestParam Integer size) {
        return ResponseEntity.ok(nativeQueryRepo.searchByFields(q, List.of("title", "author"), page, size));
    }

    @GetMapping("/search/criteria-query")
    public ResponseEntity<List<SearchHit<Book>>> searchByCriteriaQuery(@RequestParam String q,
                                                                       @RequestParam Integer page,
                                                                       @RequestParam Integer size
    ) {
        return ResponseEntity.ok(criteriaRepo.searchByTitle(q, page, size));
    }

}
