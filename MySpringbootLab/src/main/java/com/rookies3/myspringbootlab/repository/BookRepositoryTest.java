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

    private Book createSampleBook() {
        Book book = new Book();
        book.setTitle("스프링 부트 입문");
        book.setAuthor("홍길동");
        book.setIsbn("9788956746425");
        book.setPrice(30000);
        book.setPublishDate(LocalDate.of(2025, 5, 7));
        return book;
    }

    @Test
    void testCreateBook() {
        Book book = createSampleBook();
        Book saved = bookRepository.save(book);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getTitle()).isEqualTo("스프링 부트 입문");
    }

    @Test
    void testFindByIsbn() {
        Book book = createSampleBook();
        bookRepository.save(book);

        Optional<Book> result = bookRepository.findByIsbn("9788956746425");
        assertThat(result).isPresent();
        assertThat(result.get().getAuthor()).isEqualTo("홍길동");
    }

    @Test
    void testFindByAuthor() {
        Book book = createSampleBook();
        bookRepository.save(book);

        List<Book> result = bookRepository.findByAuthor("홍길동");
        assertThat(result).isNotEmpty();
    }

    @Test
    void testUpdateBook() {
        Book book = createSampleBook();
        Book saved = bookRepository.save(book);

        saved.setPrice(25000);
        bookRepository.save(saved);

        Optional<Book> updated = bookRepository.findById(saved.getId());
        assertThat(updated).isPresent();
        assertThat(updated.get().getPrice()).isEqualTo(25000);
    }

    @Test
    void testDeleteBook() {
        Book book = createSampleBook();
        Book saved = bookRepository.save(book);

        bookRepository.delete(saved);

        Optional<Book> result = bookRepository.findById(saved.getId());
        assertThat(result).isEmpty();
    }
}
