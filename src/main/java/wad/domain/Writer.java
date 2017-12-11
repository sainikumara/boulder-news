package wad.domain;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
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
public class Writer extends AbstractPersistable<Long> {

    private String name;
    
    @Cascade(CascadeType.ALL)
    @ManyToMany(mappedBy="writers")
    private List<Article> articles;

}
