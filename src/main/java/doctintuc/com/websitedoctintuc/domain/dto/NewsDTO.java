package doctintuc.com.websitedoctintuc.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {
    private String title;
    private String content;
    private String author;
    private String description;
    private String thumbnail;
    private int categoryId;
}
