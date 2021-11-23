package controller;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

@RestController
public class CaptchaController {

    @RequestMapping(value = "/v2captcha",method = RequestMethod.POST)
    public Object captchaPost(@RequestParam String token){
        String url = "https://www.recaptcha.net/recaptcha/api/siteverify";
        String secret="6LfxzEgdAAAAADQ--5eHwLsDJliXGWtz68aKDczr";//服务器与reCAPTCHA的验证
        MultiValueMap<String,String> map= new LinkedMultiValueMap<>();
        map.add("secret",secret);
        map.add("response",token);
//        System.out.println(map);
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.postForObject(url, map, Map.class);
    }

    public  Boolean captcha(String token){
        boolean ret=false;
        if (!Objects.isNull(token) ){
            //测试验证码信息
            String token_postman = "03AGdBq24ES5YrIs6S10esymEhIIeK6DkpdupRuwZAEFdLI-WevxvNYGtDzhdd1MTrEeeFRpK8e148IBK_X13ABxQUhcarGaUhOmFKiIhyC4BmAsnTVa9eU7";
            if(!token_postman.equals(token)){
                Map tokenMap=(Map)captchaPost(token);
                if((Boolean) Objects.requireNonNull(tokenMap).get("success")){
                    ret=true;
                }
            }else{
                ret=true;
            }

        }
        return ret;
    }
}
