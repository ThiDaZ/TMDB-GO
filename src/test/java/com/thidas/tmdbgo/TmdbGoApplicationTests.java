package com.thidas.tmdbgo;

import com.thidas.tmdbgo.client.TmdbProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
)
class TmdbGoApplicationTests {

    @Autowired
    private TmdbProperties tmdbProperties;

    @Test
    void shouldLoadConfigProperties() {

        assertThat(tmdbProperties.getBaseUrl()).isEqualTo("https://api.themoviedb.org/3");
        assertThat(tmdbProperties.getApiKey()).isNotBlank();
    }

}
