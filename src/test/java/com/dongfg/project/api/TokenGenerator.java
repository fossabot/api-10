package com.dongfg.project.api;

import com.dongfg.project.api.graphql.type.Token;
import com.dongfg.project.api.repository.TokenRepository;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.apache.commons.text.CharacterPredicates.DIGITS;
import static org.apache.commons.text.CharacterPredicates.LETTERS;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("dev")
public class TokenGenerator {

    @Autowired
    private TokenRepository tokenRepository;

    @Test
    public void generate() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder()
                .withinRange('0', 'z')
                .filteredBy(LETTERS, DIGITS)
                .build();
        Token token = Token.builder().token(generator.generate(16)).requestTimes(0).enabled(true).build();
        Token r = tokenRepository.save(token);
        System.out.println(r);
    }

}
