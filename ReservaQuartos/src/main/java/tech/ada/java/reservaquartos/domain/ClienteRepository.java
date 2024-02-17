package tech.ada.java.reservaquartos.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//@Repository
//public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {
//}
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
