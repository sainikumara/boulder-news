package wad.repository;

import wad.domain.Picture;
import org.springframework.data.jpa.repository.JpaRepository;
import wad.domain.Article;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
