package wad.domain;

import java.time.LocalDate;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Article extends AbstractPersistable<Long> {

    private String title;
    private String lead;
    private String picture;
    private String content;
    private LocalDate publishingTime;
    
    @ManyToMany
    private List<Writer> writers;
    
    @ManyToMany
    private List<Category> categories;
    
}
