
package kz.iitu.springlab.web;

import kz.iitu.springlab.catalog.Book;
import kz.iitu.springlab.catalog.BookService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.List;
import java.util.Comparator;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService service;

    public BookRestController(BookService service) {
        this.service = service;
    }

    @GetMapping
    public List<Book> list(
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "10") int limit) {

        return service.findAll(author)
                .stream()
                .limit(limit)
                .toList();
    }

    @GetMapping("/sorted")
    public List<Book> sorted(
            @RequestParam String sort) {

        Comparator<Book> comparator = switch (sort) {
            case "title" -> Comparator.comparing(Book::title);
            case "author" -> Comparator.comparing(Book::author);
            case "year" -> Comparator.comparingInt(Book::year);
            default -> throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Invalid sort parameter: " + sort
            );
        };

        return service.findAll(null)
                .stream()
                .sorted(comparator)
                .toList();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> find(@PathVariable long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Book> create(@RequestBody Book book) {
        Book saved = service.create(book);

        return ResponseEntity
                .created(URI.create("/api/books/" + saved.id()))
                .body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(
            @PathVariable long id,
            @RequestBody Book book) {

        return service.update(id, book)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {

        if (service.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }
}
