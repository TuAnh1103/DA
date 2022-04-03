package com.viuniteam.socialviuni.controller.api;

import com.viuniteam.socialviuni.dto.response.user.UserInfoResponse;
import com.viuniteam.socialviuni.service.UserService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @GetMapping("/home")
    @ResponseBody
    public List<UserInfoResponse> index(HttpServletRequest request){
        PageRequest pageRequest = PageRequest.of(0,100);
        Page<UserInfoResponse> userInfoResponsePage = userService.findAll(pageRequest);
        List<UserInfoResponse> userInfoResponseList = userInfoResponsePage.toList();
        return userInfoResponseList;
    }
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String top1(HttpServletRequest request){
        request.setAttribute("userAgent","Trình duyệt của mày là: " +
                request.getHeader("User-Agent"));
        String ip = request.getRemoteAddr();
        request.setAttribute("IP", "IP: "+ip+" --- Vị trí: "+getPublicIP(ip));
        return "top1";
    }
    public String getPublicIP(String ip)
    {
        try {
            Document doc = Jsoup.connect("https://www.geolocation.com/vi?ip="+ip).get();
            Elements geolocation = doc.getElementsByClass("table table-ip table-striped").first().select("tbody").first().select("tr").get(1).select("td");
            String address = geolocation.get(0).text() +" - "+ geolocation.get(1).text();
            return address;
        }
        catch (IOException exception){
            return "";
        }
    }
}
