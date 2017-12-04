package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Article;

public interface CategoryRepository extends JpaRepository<Article, Long> {
}
