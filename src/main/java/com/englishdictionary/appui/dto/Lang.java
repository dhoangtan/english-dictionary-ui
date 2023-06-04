package com.englishdictionary.appui.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Lang {
    @Value("${nav.title}")
    private String title;
    @Value("${nav.home}")
    private String navHome;
    @Value("${nav.about}")
    private String navAbout;
    @Value("${nav.contact}")
    private String navContact;
    @Value("${nav.login}")
    private String navLogin;
    @Value("${nav.register}")
    private String navRegister;
    @Value("${nav.explore}")
    private String navExplore;
    @Value("${nav.wordlist}")
    private String navWordlist;
    @Value("${nav.exercise}")
    private String navExercise;
    @Value("${nav.course}")
    private String navCourse;
    @Value("${nav.exams}")
    private String navExams;
    @Value("${nav.getstarted}")
    private String navGetStarted;
}
