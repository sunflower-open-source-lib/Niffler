package cn.gavinwang.student.ctr;
import cn.gavinwang.student.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping
public class UserController {

    private final UserRepository userRepository;

    private final FamilyRepository familyRepository;

    @Autowired
    public UserController(UserRepository userRepository, FamilyRepository familyRepository) {
        this.userRepository = userRepository;
        this.familyRepository = familyRepository;
    }

    /**
     * 注册接口
     * @param username 用户名
     * @param password 密码
     * @return {code:0/1/2,msg:注册失败/注册成功/该用户名已被注册}
     */
    @RequestMapping(value = "/register", params={"username","password"},method = RequestMethod.POST)
    @ResponseBody
    public Object register(String username,String password) {
        System.out.println("进入注册接口");
        Map map  = new HashMap<>();
        User user = null;
        try {
            User user0 = userRepository.findByUserName(username);
            if(user0 != null){
                map.put("code",2);
                map.put("msg","该用户名已被注册");
            }else{
                user = new User();
                user.setUserName(username);
                user.setPassword(password);
                userRepository.save(user);
                map.put("code",1);
                map.put("msg","注册成功");
            }
        } catch (Exception e) {
            map.put("code",0);
            map.put("msg","注册失败");
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    /**
     *
     * @param username 用户名
     * @param password 密码
     * @param request
     * @return {code:0/1/2/3,msg:登录失败/登录成功/用户名不存在/密码错误}
     */
    @RequestMapping(value = "/login", params={"username","password"},method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username, String password, HttpServletRequest request) {
        System.out.println("进入登录接口");
        Map<String,Object> map = new HashMap<>();
        try {
            User user = new User();
            user = userRepository.findByUserName(username);
            if(user == null){
                map.put("msg","用户名不存在");
                map.put("code",2);
            }else{
                if(user.getPassword().equals(password)){
                    HttpSession session = request.getSession();
                    session.setAttribute("user", user.getUserId());
                    session.setMaxInactiveInterval(2*24*60*60);
                    map.put("msg","登录成功");
                    map.put("code",1);
                }else{
                    map.put("msg","密码错误");
                    map.put("code",3);
                }
            }
        } catch (Exception e) {
            map.put("msg","登录失败");
            map.put("code",0);
            e.printStackTrace();
        } finally {
            return map;
        }
    }
}
