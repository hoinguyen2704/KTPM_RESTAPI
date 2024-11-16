package doctintuc.com.websitedoctintuc.application.service;

import doctintuc.com.websitedoctintuc.domain.dto.CategoryDTO;
import doctintuc.com.websitedoctintuc.domain.entity.Category;
import doctintuc.com.websitedoctintuc.domain.entity.User;
import doctintuc.com.websitedoctintuc.domain.pagine.PaginateDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ICategoryService {
    Category create(CategoryDTO categoryDTO , HttpServletRequest request);
    Category getCategory(Integer id);
    PaginateDTO<Category> searchPageCategory(Integer page, Integer size);
    Category update(Integer id, CategoryDTO categoryDTO , HttpServletRequest request);
    String delete(Integer id);
    List<Category> searchAllCategory();

}
