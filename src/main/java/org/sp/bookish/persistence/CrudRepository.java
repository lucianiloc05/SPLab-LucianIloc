package org.sp.bookish.persistence;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    List<T> findAll();

    void deleteById(ID id);

    boolean existsById(ID id);

    default void delete(T entity) {
    }

    default long count() {
        return 0;
    }
}
