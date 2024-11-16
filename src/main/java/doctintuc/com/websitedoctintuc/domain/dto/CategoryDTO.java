package doctintuc.com.websitedoctintuc.domain.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class CategoryDTO {
    private String categoryName;

    private String description;

    private String parentId;

}
