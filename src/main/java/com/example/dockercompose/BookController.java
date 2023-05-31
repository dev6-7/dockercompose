package com.example.dockercompose;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
public record BookController(BookService bookService) {

    @GetMapping("/{id}")
    public Mono<Book> findById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping
    public Flux<Book> findAll() {
        return bookService.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Book> save(@RequestBody Book payload) {
        Book book = new Book(null, payload.title(), payload.isbn());
        return bookService.save(book);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable Long id) {
        return bookService.deleteById(id).then();
    }
}