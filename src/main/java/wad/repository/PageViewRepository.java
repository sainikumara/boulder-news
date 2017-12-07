package wad.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.PageView;

public interface PageViewRepository extends JpaRepository<PageView, Long> {
}
