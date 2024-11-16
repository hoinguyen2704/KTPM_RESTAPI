package doctintuc.com.websitedoctintuc.adapter.web.rest;

import doctintuc.com.websitedoctintuc.domain.dto.CategoryDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@Api(tags = "Category Resource")
public interface CategoryResource {

    @ApiOperation(value = "Get all category")
    @GetMapping("/admin/search-category")
    ResponseEntity<?> searchPageCategory(@RequestParam(name = "page", defaultValue = "0") Integer page,
                                     @RequestParam(name = "size", defaultValue = "10") Integer size);

    @ApiOperation(value = "Create new category")
    @PostMapping("/admin/create-category")
    ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO , HttpServletRequest request);


    @ApiOperation(value = "Get category by id")
    @GetMapping("/admin/get-category/{id}")
    ResponseEntity<?> getCategory(@PathVariable("id") Integer id);

    @ApiOperation(value = "Edit category by id")
    @PostMapping("/admin/update-category/{id}")
    ResponseEntity<?> updateCategory(@PathVariable("id") Integer id,
                                     @RequestBody(required = false) CategoryDTO categoryDTO,
                                     HttpServletRequest request);

    @ApiOperation(value = "Delete category by id")
    @PostMapping("/admin/delete-category/{id}")
    ResponseEntity<?> deleteCategory(@PathVariable("id") Integer id);

    @ApiOperation(value = "Get all category")
    @GetMapping("/no-auth/search-category")
    ResponseEntity<?> getAllCategory();
}
