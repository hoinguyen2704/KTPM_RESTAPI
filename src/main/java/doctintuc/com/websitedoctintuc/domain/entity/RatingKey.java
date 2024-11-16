package doctintuc.com.websitedoctintuc.domain.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class RatingKey implements Serializable {
    @Column(name = "user_id")
    private int userId;

    @Column(name = "news_id")
    private int newsId;
}
