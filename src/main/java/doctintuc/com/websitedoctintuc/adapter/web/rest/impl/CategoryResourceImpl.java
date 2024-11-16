package doctintuc.com.websitedoctintuc.adapter.web.rest.impl;

import doctintuc.com.websitedoctintuc.adapter.web.base.RestApiV1;
import doctintuc.com.websitedoctintuc.adapter.web.base.VsResponseUtil;
import doctintuc.com.websitedoctintuc.adapter.web.rest.CategoryResource;
import doctintuc.com.websitedoctintuc.application.service.ICategoryService;
import doctintuc.com.websitedoctintuc.domain.dto.CategoryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@RestApiV1
@RequiredArgsConstructor
public class CategoryResourceImpl implements CategoryResource {

    private final ICategoryService categoryService;

    @Override
    public ResponseEntity<?> searchPageCategory(Integer page, Integer size) {
        return VsResponseUtil.ok(categoryService.searchPageCategory(page, size));
    }

    @Override
    public ResponseEntity<?> createCategory(CategoryDTO categoryDTO, HttpServletRequest request) {
        return VsResponseUtil.ok(categoryService.create(categoryDTO, request));
    }

    @Override
    public ResponseEntity<?> getCategory(Integer id) {
        return VsResponseUtil.ok(categoryService.getCategory(id));
    }

    @Override
    public ResponseEntity<?> updateCategory(Integer id, CategoryDTO categoryDTO, HttpServletRequest request) {
        return VsResponseUtil.ok(categoryService.update(id, categoryDTO, request));
    }

    @Override
    public ResponseEntity<?> deleteCategory(Integer id) {
        return VsResponseUtil.ok(categoryService.delete(id));
    }

    @Override
    public ResponseEntity<?> getAllCategory() {
        return VsResponseUtil.ok(categoryService.searchAllCategory());
    }
}
