package doctintuc.com.websitedoctintuc.adapter.web.rest.impl;

import doctintuc.com.websitedoctintuc.adapter.web.base.RestApiV1;
import doctintuc.com.websitedoctintuc.adapter.web.base.VsResponseUtil;
import doctintuc.com.websitedoctintuc.adapter.web.rest.NewsResource;
import doctintuc.com.websitedoctintuc.application.service.INewsService;
import doctintuc.com.websitedoctintuc.domain.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;

@RestApiV1
@RequiredArgsConstructor
public class NewsResourceImpl implements NewsResource {

    private final INewsService newsService;

    @Override
    public ResponseEntity<?> create(NewsDTO newsDTO, HttpServletRequest request) {
        return VsResponseUtil.ok(newsService.create(newsDTO, request));
    }

    @Override
    public ResponseEntity<?> get(Integer id) {
        return VsResponseUtil.ok(newsService.get(id));
    }

    @Override
    public ResponseEntity<?> update(int newsId , NewsDTO newsDTO, HttpServletRequest request) {
        return VsResponseUtil.ok(newsService.update(newsId , newsDTO, request));
    }

    @Override
    public ResponseEntity<?> searchAll(Integer page, Integer size) {
        return VsResponseUtil.ok(newsService.searchAll(page, size));
    }

    @Override
    public ResponseEntity<?> searchAllNewsByAdmin(Integer page, Integer size) {
        return VsResponseUtil.ok(newsService.searchAll(page, size));
    }

    @Override
    public ResponseEntity<?> searchAllNotPaginate() {
        return VsResponseUtil.ok(newsService.searAllNotPaginate());
    }

    @Override
    public ResponseEntity<?> delete(Integer id) {
        return VsResponseUtil.ok(newsService.delete(id));
    }

    @Override
    public ResponseEntity<?> getFavoriteNews() {
        return VsResponseUtil.ok(newsService.getFavoriteNews());
    }

    @Override
    public ResponseEntity<?> getLeastNews() {
        return VsResponseUtil.ok(newsService.getLeastNews());
    }

    @Override
    public ResponseEntity<?> paginateHomePage(Integer page, Integer size) {
        return VsResponseUtil.ok(newsService.paginateHomePage(page, size));
    }

    @Override
    public ResponseEntity<?> setView(Integer id) {
        return VsResponseUtil.ok(newsService.setView(id));
    }

    @Override
    public ResponseEntity<?> filterNewByCategory(Integer categoryId, Integer page, Integer size, String author, String title, String filter) {
        return VsResponseUtil.ok(newsService.filterNewsByCategory(page, size, author, title, categoryId, filter));
    }

    @Override
    public ResponseEntity<?> filterNewByCategory(Integer page, Integer size, String key) {
        return VsResponseUtil.ok(newsService.searchNews(page, size, key));
    }

    @Override
    public ResponseEntity<?> countRecordNews() {
        return VsResponseUtil.ok(newsService.countRecordNews());
    }

    @Override
    public ResponseEntity<?> saveNewsWatched(int newsId, HttpServletRequest request) {
        return VsResponseUtil.ok(newsService.saveNewsWatched(newsId, request));
    }

    @Override
    public ResponseEntity<?> getAllNewsWatched(HttpServletRequest request) {
        return VsResponseUtil.ok(newsService.getAllNewWatched(request));
    }
}
