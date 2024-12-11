package mapper;

import dto.NewsDTO;
import model.News;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NewsMapper {
    NewsDTO toDto(News news);

    News toEntity(NewsDTO dto);
}

