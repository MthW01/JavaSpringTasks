package SkillBoxTasks.Task4.src.main.repos;

import SkillBoxTasks.Task4.src.main.host.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<NewsEntity,Long>, JpaSpecificationExecutor<NewsEntity> {
}
