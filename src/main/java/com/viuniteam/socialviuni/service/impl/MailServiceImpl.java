package com.viuniteam.socialviuni.service.impl;

import com.viuniteam.socialviuni.entity.Mail;
import com.viuniteam.socialviuni.enumtype.SendCodeType;
import com.viuniteam.socialviuni.exception.BadRequestException;
import com.viuniteam.socialviuni.exception.OKException;
import com.viuniteam.socialviuni.repository.MailRepository;
import com.viuniteam.socialviuni.service.MailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Random;

@Service
@AllArgsConstructor
public class MailServiceImpl implements MailService {


    private final int MIN_CODE = 10106383, MAX_CODE = 99898981;

    private final int MINUTE_LIMIT = 5;

    private final JavaMailSender emailSender;

    private final Random random;

    private final MailRepository mailRepository;


    @Override
    public void sendCode(String email, String username, SendCodeType sendCodeType) {
        String title = "Xác nhận %s Social Viuni";
        String content = "<h1 style='color:red;'>Xin chào "+username+"!</h1>\n" +
                "<p>Mã bảo mật của bạn là: <i><b><u>%s</u></b></i></p>"+
                "<p>Để xác nhận yêu cầu %s của bạn trên Social Viuni, chúng tôi cần xác minh địa chỉ email của bạn. Hãy dán mã này vào trình duyệt.</p>" +
                "<p>Đây là mã dùng một lần và thời gian sử dụng tối đa 5 phút.</p>"+
                "<p>Trân trọng cảm ơn!</p>" +
                "<p>-The Social Viuni Security Team-</p>";
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
            if((new Date().getTime() - time)/1000 < MINUTE_LIMIT*60)
                throw new BadRequestException("Không thể tạo mã mới trong khi mã cũ chưa hết hạn");
            else {
                deleteByEmail(email);
                saveCode(email,title,content,code);
            }
        }
        else
            saveCode(email,title,content,code);
    }


    public void saveCode(String email,String title,String content,String code){
        sendSimpleMessage(email,title, content);
        mailRepository.save(Mail.builder().email(email).code(code).build());
        throw new OKException("Đã gửi mã xác nhận đến email: "+email);
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
                if((new Date().getTime() - time)/1000 < MINUTE_LIMIT *60) return true;
            }
            return false;
        }
        return false;
    }


    private int renderRandom(){
        return random.nextInt((MAX_CODE - MIN_CODE) + 1) + MIN_CODE;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        try {
            /*SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("social.viuni@gmail.com");
            message.setTo(to);
            message.setSubject(subject);
            message.setText(text);
            emailSender.send(message);*/

            MimeMessage message = emailSender.createMimeMessage();
            message.setSubject(subject,"UTF-8");
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(message, true,"UTF-8");
            helper.setFrom("social.viuni@gmail.com");
            helper.setTo(to);
            helper.setText(text, true);
            emailSender.send(message);
        }
        catch (Exception ex){
            throw new BadRequestException("Gửi mã thất bại");
        }
    }
}
