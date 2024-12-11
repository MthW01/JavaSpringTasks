package service;

import dto.NewsDTO;
import mapper.NewsMapper;
import repository.NewsRepository;
import repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static repository.NewsSpecification.*;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class NewsService {

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final UserRepository userRepository;

    public NewsService(NewsRepository newsRepository, NewsMapper newsMapper, UserRepository userRepository) {
        this.newsRepository = newsRepository;
        this.newsMapper = newsMapper;
        this.userRepository = userRepository;
    }

    public Page<NewsDTO> findAll(Pageable pageable) {
        return newsRepository.findAll(pageable).map(newsMapper::toDto);
    }

    public NewsDTO findById(Long id, Long categoryId, Long author) {
        var news = newsRepository.findAll(where(hasAuthor(author))
                .and(hasCategory(categoryId))
                .and(hasId(id))).stream().findFirst().orElseThrow(RuntimeException::new);
        return newsMapper.toDto(news);
    }

    public NewsDTO createNews(NewsDTO dto) {
        var news = newsMapper.toEntity(dto);
        var author = userRepository.findById(dto.getAuthorId())
                .orElse(null);
        news.setAuthor(author);
        return newsMapper.toDto(newsRepository.save(news));
    }

    public void deleteNews(Long id) {
        var news = newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not found"));
        newsRepository.delete(news);
    }

    public void updateById(Long newsId, NewsDTO updateRequest) {
        var existing = newsRepository.findById(newsId).orElseThrow(RuntimeException::new);
        existing.setTitle(updateRequest.getTitle());
        existing.setContent(updateRequest.getContent());
        newsRepository.save(existing);
    }
}
