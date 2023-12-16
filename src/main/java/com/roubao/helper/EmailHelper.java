package com.roubao.helper;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 邮件发送工具类
 *
 * @author SongYanBin
 * @copyright 2023-2099 SongYanBin All Rights Reserved.
 * @since 2023/12/16
 **/
@Component
public class EmailHelper implements InitializingBean {

    private static JavaMailSender javaMailSenderStatic;
    private static MailProperties mailPropertiesStatic;

    private final JavaMailSender javaMailSender;
    private final MailProperties mailProperties;

    @Autowired
    public EmailHelper(JavaMailSender javaMailSender, MailProperties mailProperties) {
        this.javaMailSender = javaMailSender;
        this.mailProperties = mailProperties;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        javaMailSenderStatic = this.javaMailSender;
        mailPropertiesStatic = this.mailProperties;
    }

    /**
     * 发送简单文字邮件
     *
     * @param to      收件人邮箱
     * @param subject 标题
     * @param content 内容
     */
    public static void sendSimple(String to, String subject, String content) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom(mailPropertiesStatic.getUsername());
        mail.setTo(to);
        mail.setSubject(subject);
        mail.setText(content);
        mail.setSentDate(new Date());
        javaMailSenderStatic.send(mail);
    }
}
