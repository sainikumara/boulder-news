package wad.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Picture extends AbstractPersistable<Long> {
    
    @OneToOne
    private Article article;
    
    @Lob
    private byte[] content;
}
