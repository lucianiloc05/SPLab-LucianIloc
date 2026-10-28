package org.sp.bookish.persistence;

import org.sp.bookish.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Primary
public class BooksRepositoryAdapter implements CrudRepository<Book, Integer> {

    private final BooksRepository booksRepository;

    @Autowired
    public BooksRepositoryAdapter(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Override
    public Book save(Book entity) {
        return booksRepository.save(entity);
    }

    @Override
    public Optional<Book> findById(Integer id) {
        return booksRepository.findById(id);
    }

    @Override
    public List<Book> findAll() {
        return booksRepository.findAll();
    }

    @Override
    public void deleteById(Integer id) {
        booksRepository.deleteById(id);
    }

    @Override
    public boolean existsById(Integer id) {
        return booksRepository.existsById(id);
    }

    @Override
    public void delete(Book entity) {
        booksRepository.delete(entity);
    }

    @Override
    public long count() {
        return booksRepository.count();
    }

    public BooksRepository getBooksRepository() {
        return booksRepository;
    }
}
