package com.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * TODO https://www.youtube.com/watch?v=8xa0RWMwAOE&t=2140s - c 35:45 Женя Борисов начинает рассказывать про то, как
 *  все это работает(@SpringBootApplication - очень сложная аннотация). Писать документацию ко всему этому делу - с ума
 *  сойти можно. Могу только прикрепить ссылку на это видео, но и то такое себе: оно на русском, без субтитров вообщею
 *  Как это оценят айбишники. Может, все же нужно весь ад про эту аннотацию расписывать?
 */
@SpringBootApplication
public class ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}