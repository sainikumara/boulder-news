package wad.repository;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wad.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a JOIN PageView p "
            + "ON a.id = p.article "
            + "WHERE p.viewTime > :oneWeekAgo "
            + "GROUP BY p.article "
            + "ORDER BY COUNT(*) DESC")
    List<Article> mostReadSince(@Param("oneWeekAgo") LocalDateTime oneWeekAgo);
}
