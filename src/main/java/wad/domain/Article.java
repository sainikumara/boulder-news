package wad.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Article extends AbstractPersistable<Long> {

    private String title;
    private String lead;
    private String content;
    private LocalDateTime publishingTime;
    
    @OneToOne
    private Picture picture;
    
    @Cascade(CascadeType.ALL)
    @ManyToMany
    private List<Writer> writers;
    
    @Cascade(CascadeType.ALL)
    @ManyToMany
    private List<Category> categories;
    
    @Cascade(CascadeType.ALL)
    @OneToMany
    private List<PageView> pageViews;

}
