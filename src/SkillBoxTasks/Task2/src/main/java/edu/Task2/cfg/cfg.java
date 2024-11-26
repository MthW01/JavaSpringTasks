package SkillBoxTasks.Task2.src.main.java.edu.Task2.cfg;

import SkillBoxTasks.Task2.src.main.java.edu.Task2.domain.UserStorage;
import SkillBoxTasks.Task2.src.main.java.edu.Task2.common.Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("init")
public class InitCfg {
    private final Parser myParser;

    public InitCfg(Parser myParser) {
        this.myParser = myParser;
    }

    @Bean
    public UserStorage userStorage() {
        return new UserStorage(myParser.parseInitFile());
    }

}