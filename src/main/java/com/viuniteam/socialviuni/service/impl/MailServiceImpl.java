package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.entity.Mail;
import com.viuniteam.socialviuni.enumtype.SendCodeType;
import com.viuniteam.socialviuni.exception.JsonException;
import com.viuniteam.socialviuni.repository.MailRepository;
import com.viuniteam.socialviuni.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender emailSender;

    private final Random random;

    private final int min = 10106383, max = 99898981;

    private final MailRepository mailRepository;

    private final int minuteLimit = 5;


    @Override
    public ResponseEntity<?> sendCode(String email, String username, SendCodeType sendCodeType) {
        String title = "Xác nhận %s Social Viuni";
        String content = "Xin chào "+username+",\n" +
                "Mã bảo mật của bạn là: %s \n"+
                "Để xác nhận yêu cầu %s của bạn trên Social Viuni, chúng tôi cần xác minh địa chỉ email của bạn. Hãy dán mã này vào trình duyệt.\n" +
                "Đây là mã dùng một lần và thời gian sử dụng tối đa 5 phút."+"\n\n"+
                "Thanks,\n" +
                "-The Social Viuni Security Team-";
        String code  = String.valueOf(renderRandom());

        String type;

        if (sendCodeType == SendCodeType.REGISTER)
            type = SendCodeType.REGISTER.getName();
        else if(sendCodeType == SendCodeType.RECOVERY)
            type = SendCodeType.RECOVERY.getName();
        else
            type = SendCodeType.CHANGEEMAIL.getName();

        title= String.format(title,type);
        content = String.format(content,code,type);

        Mail mail = mailRepository.findOneByEmail(email);
        if(mail != null){
            long time = mail.getCreatedDate().getTime();
            if((new Date().getTime() - time)/1000 < minuteLimit*60)
                return new ResponseEntity<>(new JsonException(400,"Không thể tạo mã mới trong khi mã cũ chưa hết hạn"), HttpStatus.BAD_REQUEST);
            else {
                deleteByEmail(email);
                return saveCode(email,title,content,code);
            }
        }
        else
            return saveCode(email,title,content,code);
    }


    public ResponseEntity<?> saveCode(String email,String title,String content,String code){
        try{
            System.out.println("vao day ne con lol");
            sendSimpleMessage(email,title, content);
            mailRepository.save(Mail.builder().email(email).code(code).build());
            return new ResponseEntity<>(new JsonException(200,"Đã gửi mã xác nhận đến email: "+email), HttpStatus.CREATED);
        }
        catch (Exception e){
            System.out.println("loi ne");
            return new ResponseEntity<>(new JsonException(400,"Gửi mã thất bại"), HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void deleteByEmail(String email) {
        mailRepository.deleteByEmail(email);
    }

    @Override
    public boolean hasCode(String email, String code) {
        Mail mail = mailRepository.findOneByEmail(email);
        //return mail != null ? mail.getCode().equals(code) ? true : false : false;
        if(mail!=null){
            if(mail.getCode().equals(code)){
                long time = mail.getCreatedDate().getTime();
                if((new Date().getTime() - time)/1000 < minuteLimit*60) return true;
            }
            return false;
        }
        return false;
    }


    private int renderRandom(){
        return random.nextInt((max - min) + 1) + min;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("social.viuni@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }
}
