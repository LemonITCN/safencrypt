package cn.lemonit.safencrypt.springboot;

import com.google.gson.Gson;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@RestController
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @RequestMapping("/client-msg")
    public Object client_msg(@RequestBody Map<String, Object> msg) {
        System.out.println("client-msg: " + msg);
        HashMap map = new HashMap();
        map.put("result", "hi, safencrypt have receive your client msg - " + msg.get("msg"));
        return map;
    }

    @RequestMapping(value = "/user-msg", method = RequestMethod.POST)
    public Object user_msg(@RequestBody Map<String, Object> msg) {
        System.out.println("user-msg: " + msg);
        HashMap map = new HashMap();
        map.put("result", "hi, safencrypt have receive your client msg - " + msg.get("msg"));
        return map;
    }

    @RequestMapping("/get")
    public Object get_msg(@RequestParam String name, @RequestParam String msg) {
        System.out.println("name:" + name + " , get-msg: " + msg);
        HashMap<String, String> result = new HashMap<>();
        result.put("result", "hi " + name + ",your msg = " + msg);
        return result;
    }

    @RequestMapping(value = "/put-info", method = RequestMethod.PUT)
    public Object put_info(@RequestBody Test test) {
        return new Gson().toJson(test);
    }

}
