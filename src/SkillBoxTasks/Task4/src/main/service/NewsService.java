package SkillBoxTasks.Task4.src.main.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import SkillBoxTasks.Task4.src.main.repos.NewsCategoryRepository;
import SkillBoxTasks.Task4.src.main.repos.NewsRepository;
import SkillBoxTasks.Task4.src.main.repos.UserRepository;
import SkillBoxTasks.Task4.src.main.dto.rq.NewsRequest;
import SkillBoxTasks.Task4.src.main.dto.rs.NewsFullResponse;
import SkillBoxTasks.Task4.src.main.dto.rs.NewsSmallResponse;
import SkillBoxTasks.Task4.src.main.host.NewsCategoryEntity;
import SkillBoxTasks.Task4.src.main.host.NewsEntity;
import SkillBoxTasks.Task4.src.main.host.UserEntity;
import SkillBoxTasks.Task4.src.main.mapper.NewsMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NewsService {
    private final UserRepository userRepository;
    private final NewsCategoryRepository categoryRepository;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    public NewsFullResponse add(NewsRequest newsRequest) {
        Optional<UserEntity> user = userRepository.findById(newsRequest.userID());
        Optional<NewsCategoryEntity> category = categoryRepository.findById(newsRequest.categoryID());
        if (user.isEmpty()) {
            throw new RuntimeException("not found user by id");
        }
        if (category.isEmpty()) {
            throw new RuntimeException("not found category by id");
        }
        NewsEntity news = new NewsEntity(newsRequest.content(), user.get(), category.get());
        newsRepository.save(news);
        return newsMapper.newsToFullResponse(news);
    }

    public List<NewsSmallResponse> getAll(Pageable pageable) {
        return newsRepository.findAll(pageable).map(newsMapper::newsToSmallResponse).getContent();
    }

    public NewsFullResponse getById(Long id) {
        Optional<NewsEntity> news = newsRepository.findById(id);
        if (news.isEmpty()) {
            throw new RuntimeException("not found news by id");
        }
        return newsMapper.newsToFullResponse(news.get());
    }

    public void delete(Long id, Long userId) {
        newsRepository.deleteById(id);
    }

    public NewsFullResponse update(Long id, Long userId, String newContent) {
        Optional<NewsEntity> news = newsRepository.findById(id);
        if (news.isEmpty()) {
            throw new RuntimeException("not found news by id");
        }
        NewsEntity updatedNews = news.get();
        updatedNews.setContent(newContent);
        newsRepository.save(updatedNews);
        return newsMapper.newsToFullResponse(updatedNews);
    }
}
