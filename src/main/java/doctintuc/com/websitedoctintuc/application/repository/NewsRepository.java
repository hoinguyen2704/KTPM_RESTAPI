package doctintuc.com.websitedoctintuc.application.repository;

import doctintuc.com.websitedoctintuc.domain.entity.News;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
    Boolean existsByTitle(String title);

    @Query(value = "SELECT  u.id , u.title , u.description , u.thumbnail FROM news u ORDER BY u.view DESC LIMIT 5", nativeQuery = true)
    List<News> favoriteNews();

    @Query(value = "SELECT u.id , u.title , u.description , u.thumbnail FROM news u ORDER BY u.create_date DESC LIMIT 7", nativeQuery = true)
    List<News> leastNews();

    @Query(value = "SELECT * FROM news u WHERE u.category_id = ?1 or u.title = ?2 or u.author LIKE ?3", nativeQuery = true)
    List<News> filterNewsByCategory(Integer categoryId, String title, String author, Pageable pageable);

    @Query(value = "SELECT * FROM news u  WHERE u.title LIKE :key OR u.description LIKE :key", nativeQuery = true)
    List<News> searchNewsByKey(String key, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM news", nativeQuery = true)
    Integer countRecordNews();

    Boolean existsNewsByTitle(String title);

}
