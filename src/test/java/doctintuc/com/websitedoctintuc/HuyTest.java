package doctintuc.com.websitedoctintuc;

import doctintuc.com.websitedoctintuc.application.constants.CommonConstant;
import doctintuc.com.websitedoctintuc.application.repository.CategoryRepository;
import doctintuc.com.websitedoctintuc.application.repository.NewsRepository;
import doctintuc.com.websitedoctintuc.application.service.impl.NewsServiceImpl;
import doctintuc.com.websitedoctintuc.domain.dto.CustomNewDTO;
import doctintuc.com.websitedoctintuc.domain.entity.Category;
import doctintuc.com.websitedoctintuc.domain.entity.News;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HuyTest {

    @Mock
    private NewsRepository newsRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private NewsServiceImpl newsService;

    private Integer page = 0;
    private Integer size = 10;
    private String author = "John Doe";
    private String title = "Sample Title";
    private Integer categoryId = 1;
    private String filter = "ASC"; // có thể thay thế bằng "DESC" hoặc null cho các trường hợp khác

    private Category mockCategory;
    private News mockNews;

    @BeforeEach
    public void setUp() {
        // Khởi tạo dữ liệu mẫu cho Category
        mockCategory = new Category();
        mockCategory.setId(1);
        mockCategory.setCategoryName("Technology");

        // Khởi tạo dữ liệu mẫu cho News
        mockNews = new News();
        mockNews.setId(1);
        mockNews.setTitle("Sample News");
        mockNews.setContent("This is a sample news content.");
        mockNews.setAuthor("John Doe");
        mockNews.setDescription("Sample Description");
        mockNews.setThumbnail("thumbnail.png");
        mockNews.setView(100);

        // Mock behavior cho categoryRepository và newsRepository
        when(categoryRepository.existsById(categoryId)).thenReturn(true);
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(mockCategory));
    }

    @Test
    public void testFilterNewsByCategory_Success_SortAsc() {
        // Mô phỏng dữ liệu trả về khi filter theo category, title, author với sort ASC
        List<News> newsList = new ArrayList<>();
        newsList.add(mockNews);
        when(newsRepository.filterNewsByCategory(categoryId, title, author, PageRequest.of(page, size,
                Sort.by(CommonConstant.SORT_BY_TIME).ascending()))).thenReturn(newsList);

        // Thực thi phương thức filterNewsByCategory
        CustomNewDTO result = newsService.filterNewsByCategory(page, size, author, title, categoryId, CommonConstant.SORT_ASC);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.getNewsList().size());
        assertEquals(mockCategory, result.getCategory());
        assertEquals(1, result.getTotalPage());
    }

    @Test
    public void testFilterNewsByCategory_Success_SortDesc() {
        // Mô phỏng dữ liệu trả về khi filter theo category, title, author với sort DESC
        List<News> newsList = new ArrayList<>();
        newsList.add(mockNews);
        when(newsRepository.filterNewsByCategory(categoryId, title, author, PageRequest.of(page, size,
                Sort.by(CommonConstant.SORT_BY_TIME).descending()))).thenReturn(newsList);

        // Thực thi phương thức filterNewsByCategory
        CustomNewDTO result = newsService.filterNewsByCategory(page, size, author, title, categoryId, CommonConstant.SORT_DESC);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.getNewsList().size());
        assertEquals(mockCategory, result.getCategory());
        assertEquals(1, result.getTotalPage());
    }

    @Test
    public void testFilterNewsByCategory_NoFilter() {
        // Mô phỏng dữ liệu khi không sử dụng filter (null hoặc empty)
        List<News> newsList = new ArrayList<>();
        newsList.add(mockNews);
        when(newsRepository.filterNewsByCategory(categoryId, title, author, PageRequest.of(page, size,
                Sort.by(CommonConstant.SORT_BY_TIME).ascending()))).thenReturn(newsList);

        // Thực thi phương thức filterNewsByCategory
        CustomNewDTO result = newsService.filterNewsByCategory(page, size, author, title, categoryId, "");

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(1, result.getNewsList().size());
        assertEquals(mockCategory, result.getCategory());
        assertEquals(1, result.getTotalPage());
    }

    @Test
    public void testFilterNewsByCategory_EmptyResults() {
        // Mô phỏng dữ liệu trả về rỗng khi không có bài viết
        List<News> newsList = new ArrayList<>();
        when(newsRepository.filterNewsByCategory(categoryId, title, author, PageRequest.of(page, size,
                Sort.by(CommonConstant.SORT_BY_TIME).ascending()))).thenReturn(newsList);

        // Thực thi phương thức filterNewsByCategory
        CustomNewDTO result = newsService.filterNewsByCategory(page, size, author, title, categoryId, CommonConstant.SORT_ASC);

        // Kiểm tra kết quả
        assertNotNull(result);
        assertEquals(0, result.getNewsList().size());
        assertEquals(mockCategory, result.getCategory());
        assertEquals(0, result.getTotalPage());
    }
}
