package doctintuc.com.websitedoctintuc;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import doctintuc.com.websitedoctintuc.application.constants.DevMessageConstant;
import doctintuc.com.websitedoctintuc.application.jwt.JwtUtils;
import doctintuc.com.websitedoctintuc.application.repository.CategoryRepository;
import doctintuc.com.websitedoctintuc.application.repository.UserRepository;
import doctintuc.com.websitedoctintuc.application.service.impl.CategoryServiceImpl;
import doctintuc.com.websitedoctintuc.domain.dto.CategoryDTO;
import doctintuc.com.websitedoctintuc.domain.entity.Category;
import doctintuc.com.websitedoctintuc.domain.entity.User;
import doctintuc.com.websitedoctintuc.domain.pagine.PaginateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class HoiTest {

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    private CategoryDTO categoryDTO;
    private User mockUser;
    private Category mockCategory;

    @BeforeEach
    public void setUp() {
        // Mock dữ liệu
        categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName("Category A");

        mockUser = new User();
        mockUser.setUsername("user1");
        mockUser.setFullName("John Doe");

        mockCategory = new Category();
        mockCategory.setId(1);
        mockCategory.setCategoryName("Category A");
        mockCategory.setCreateBy("John Doe");
        mockCategory.setLastModifiedBy("John Doe");
    }

    @Test
    public void testCreateCategory() {

        // Mock repository và JWT utils
        when(categoryRepository.existsByCategoryName(categoryDTO.getCategoryName())).thenReturn(false);
        when(jwtUtils.getUserByToken(anyString())).thenReturn(mockUser.getUsername());
        when(modelMapper.map(categoryDTO, Category.class)).thenReturn(mockCategory);
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(mockUser);
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);

        // Mock request header
        when(request.getHeader("Authorization")).thenReturn("Bearer token123");

        // Thực thi phương thức create
        Category createdCategory = categoryServiceImpl.create(categoryDTO, request);

        // Kiểm tra kết quả
        assertNotNull(createdCategory);
        assertEquals("Category A", createdCategory.getCategoryName());
        assertEquals("John Doe", createdCategory.getCreateBy());
    }

    @Test
    public void testUpdateCategory() {
        CategoryDTO updateCategoryDTO = new CategoryDTO();
        updateCategoryDTO.setCategoryName("Category B");

        Category updateCategory = new Category();
        updateCategory.setCategoryName(updateCategoryDTO.getCategoryName());

        // Mock dữ liệu
        when(categoryRepository.findById(1)).thenReturn(Optional.of(mockCategory));
        when(jwtUtils.getUserByToken(anyString())).thenReturn(mockUser.getUsername());
        when(modelMapper.map(updateCategoryDTO, Category.class)).thenReturn(mockCategory);
        when(userRepository.findByUsername(mockUser.getUsername())).thenReturn(mockUser);
        when(categoryRepository.save(any(Category.class))).thenReturn(mockCategory);

        // Mock request header
        when(request.getHeader("Authorization")).thenReturn("Bearer token123");

        // Thực thi phương thức update
        Category updatedCategory = categoryServiceImpl.update(1, updateCategoryDTO, request);

        // Kiểm tra kết quả
        assertNotNull(updatedCategory);
        assertEquals("Category B", updatedCategory.getCategoryName());
        assertEquals("John Doe", updatedCategory.getLastModifiedBy());
    }

    @Test
    public void testDeleteCategory() {
        // Mock dữ liệu
        when(categoryRepository.existsById(1)).thenReturn(true);
        doNothing().when(categoryRepository).deleteById(1);

        // Thực thi phương thức delete
        String result = categoryServiceImpl.delete(1);

        // Kiểm tra kết quả
        assertEquals(DevMessageConstant.Common.NOTIFICATION_DELETE_SUCCESS, result);
        verify(categoryRepository, times(1)).deleteById(1);
    }

    @Test
    public void testSearchPageCategory() {
        // Mock dữ liệu
        // Tạo các đối tượng Category giả lập
        Category category1 = new Category(1, "Category 1", "Description 1");
        Category category2 = new Category(2, "Category 2", "Description 2");
        Category category3 = new Category(3, "Category 3", "Description 3");

        // Đưa các đối tượng vào trong một danh sách
        List<Category> categoryList = Arrays.asList(category1, category2, category3);

        Page<Category> categoryPage = new PageImpl<>(categoryList, PageRequest.of(0, 10), categoryList.size());  // Giả lập một trang dữ liệu với tổng số phần tử là 1

        // Mock repository để trả về dữ liệu phân trang
        when(categoryRepository.findAll(PageRequest.of(0, 10, Sort.by(Sort.Order.desc("createDate"))))).thenReturn(categoryPage);

        // Thực thi phương thức searchPageCategory
        PaginateDTO<Category> paginatedCategories = categoryServiceImpl.searchPageCategory(0, 10);

        // Kiểm tra kết quả
        assertNotNull(paginatedCategories);  // Kiểm tra xem PaginateDTO có phải là null không
        assertEquals(3, paginatedCategories.getPageData().size());  // Kiểm tra số lượng dữ liệu trên trang (nên là 1 vì chỉ có mockCategory)
        assertEquals(0, paginatedCategories.getTotalPage());  // Kiểm tra tổng số trang (nên là 1 vì chỉ có 1 phần tử)
        assertEquals(0, paginatedCategories.getCurrentPage());  // Kiểm tra số trang hiện tại (nên là 0 vì chúng ta gọi với trang đầu tiên)
    }

}
