package wad.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wad.domain.Article;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    @Query("SELECT a FROM Article a JOIN PageView p "
            + "ON a.id = p.article "
            + "WHERE p.viewTime > :dateOneWeekAgo "
            + "GROUP BY p.article "
            + "ORDER BY COUNT(*) DESC")
    List<Article> mostReadSince(@Param("dateOneWeekAgo") LocalDate dateAWeekAgo);
}
