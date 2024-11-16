package doctintuc.com.websitedoctintuc.application.service;

import doctintuc.com.websitedoctintuc.domain.dto.CustomNewDTO;
import doctintuc.com.websitedoctintuc.domain.dto.NewsDTO;
import doctintuc.com.websitedoctintuc.domain.entity.News;
import doctintuc.com.websitedoctintuc.domain.pagine.PaginateDTO;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface INewsService {
    News create(NewsDTO newsDTO, HttpServletRequest request);

    News get(Integer id);

    News update(int newsId , NewsDTO newsDTO, HttpServletRequest request);

    PaginateDTO<News> searchAll(Integer page, Integer size);

    List<News> searAllNotPaginate();

    String delete(Integer id);

    List<News> getFavoriteNews();

    List<News> getLeastNews();

    PaginateDTO<News> paginateHomePage(Integer page, Integer size);

    News setView(Integer id);

    CustomNewDTO filterNewsByCategory(Integer page, Integer size, String author, String title, Integer categoryId, String filter);

    PaginateDTO<News> searchNews(Integer page, Integer size, String key);

    Integer countRecordNews();

    News saveNewsWatched(int newsId, HttpServletRequest request);

    List<News> getAllNewWatched(HttpServletRequest request);
}
