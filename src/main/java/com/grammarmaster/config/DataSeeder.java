package com.grammarmaster.config;

import com.grammarmaster.model.*;
import com.grammarmaster.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSeeder {
    @Bean
    CommandLineRunner seedDatabase(QuizRepository quizzes, QuestionRepository questions) {
        return args -> {
            if (quizzes.count() > 0) return;

            Quiz presentSimple = quizzes.save(new Quiz(
                    "Present Simple",
                    "Practice habits, routines and general facts."
            ));

            questions.save(new Question("She _____ to school every day.",
                    "go", "goes", "going", "gone", "B",
                    "With he, she and it, we usually add -s or -es to the verb.", presentSimple));

            questions.save(new Question("They _____ football on Sundays.",
                    "plays", "playing", "play", "played", "C",
                    "With I, you, we and they, use the base form of the verb.", presentSimple));

            questions.save(new Question("_____ he like coffee?",
                    "Do", "Does", "Is", "Are", "B",
                    "Use does to make Present Simple questions with he, she or it.", presentSimple));

            questions.save(new Question("My brother _____ TV in the evening.",
                    "watch", "watching", "watchs", "watches", "D",
                    "Verbs ending in -ch take -es with he, she or it.", presentSimple));

            questions.save(new Question("I _____ understand this question.",
                    "doesn't", "am not", "don't", "not", "C",
                    "Use don't with I, you, we and they in negative Present Simple sentences.", presentSimple));

            Quiz pastSimple = quizzes.save(new Quiz(
                    "Past Simple",
                    "Practice completed actions in the past."
            ));

            questions.save(new Question("We _____ a movie yesterday.",
                    "watch", "watched", "watches", "watching", "B",
                    "For most regular verbs, add -ed to form the Past Simple.", pastSimple));

            questions.save(new Question("She _____ to London last year.",
                    "go", "goes", "went", "going", "C",
                    "Went is the Past Simple form of the irregular verb go.", pastSimple));

            questions.save(new Question("_____ you visit your grandmother yesterday?",
                    "Did", "Do", "Were", "Have", "A",
                    "Use did to form Past Simple questions.", pastSimple));

            questions.save(new Question("They didn't _____ dinner at home.",
                    "ate", "eats", "eating", "eat", "D",
                    "After didn't, use the base form of the verb.", pastSimple));

            questions.save(new Question("I _____ tired after the lesson.",
                    "were", "was", "am", "be", "B",
                    "Use was with I, he, she and it in the past tense.", pastSimple));
        };
    }
}
