package com.rookies3.myspringbootlab.repository;

import com.rookies3.myspringbootlab.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    void testCreateBook() {
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        book.setPublishDate(LocalDate.of(2025, 5, 7));

        Book saved = bookRepository.save(book);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    void testFindByIsbn() {
        Book book = new Book();
        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");
        book.setPrice(35000);
        book.setPublishDate(LocalDate.of(2025, 4, 30));
        bookRepository.save(book);

        Optional<Book> found = bookRepository.findByIsbn("9788956746432");
        assertThat(found).isPresent();
    }

    @Test
    void testFindByAuthor() {
        Book book = new Book();
        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");
        book.setPrice(35000);
        book.setPublishDate(LocalDate.of(2025, 4, 30));
        bookRepository.save(book);

        List<Book> books = bookRepository.findByAuthor("박둘리");
        assertThat(books).hasSizeGreaterThan(0);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book();
        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");
        book.setPrice(35000);
        book.setPublishDate(LocalDate.of(2025, 4, 30));
        Book saved = bookRepository.save(book);

        saved.setPrice(40000);
        Book updated = bookRepository.save(saved);
        assertThat(updated.getPrice()).isEqualTo(40000);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book();
        book.setTitle("JPA 프로그래밍");
        book.setAuthor("박둘리");
        book.setIsbn("9788956746432");
        book.setPrice(35000);
        book.setPublishDate(LocalDate.of(2025, 4, 30));
        Book saved = bookRepository.save(book);

        bookRepository.delete(saved);
        Optional<Book> deleted = bookRepository.findById(saved.getId());
        assertThat(deleted).isEmpty();
    }
}
