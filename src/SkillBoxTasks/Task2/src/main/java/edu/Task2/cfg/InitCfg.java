package edu.Task2.cfg;

import edu.Task2.domain.UserStorage;
import edu.Task2.common.Parser;
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