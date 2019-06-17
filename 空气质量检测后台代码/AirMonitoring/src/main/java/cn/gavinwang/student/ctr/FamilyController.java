package cn.gavinwang.student.ctr;

import cn.gavinwang.student.db.*;
import cn.gavinwang.student.utils.QrCodeUtil;
import cn.gavinwang.student.utils.UpLoadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.*;

@CrossOrigin(origins = "*",maxAge = 3600)
@RestController
@RequestMapping
public class FamilyController {
    private final FamilyRepository familyRepository;

    private final UserRepository userRepository;

    private final UserToFamilyRepository userToFamilyRepository;

    private final RoomRepository roomRepository;

    private final DeviceRepository deviceRepository;

    private final AirInfoRepository airInfoRepository;

    @Autowired
    public FamilyController(FamilyRepository familyRepository, UserRepository userRepository,UserToFamilyRepository userToFamilyRepository,RoomRepository roomRepository,DeviceRepository deviceRepository,AirInfoRepository airInfoRepository) {
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
        this.userToFamilyRepository = userToFamilyRepository;
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
        this.airInfoRepository = airInfoRepository;
    }

    /**
     * 获取我所在的所有家庭信息
     * @param request 携带用户sesssion信息
     * @return {code:0/1,msg:获取家庭信息失败/获取家庭信息成功,data:-/数据信息} data数据格式:[{},{}]
     */
    @RequestMapping(value = "/getmyfamilies", params={},method = RequestMethod.POST)
    @ResponseBody
    public Object getMyFamilies(HttpServletRequest request) {
        System.out.println("进入获取所有家庭接口");
        Map map  = new HashMap<>();
        try {
            HttpSession session = request.getSession();
            int uid = (int)session.getAttribute("user");
            List<Family> families = new ArrayList<>();
            List<UserToFamily> userToFamilies = userToFamilyRepository.findAllByUserId(uid);
            for(int i = 0; i < userToFamilies.size(); i++){
                UserToFamily userToFamily = userToFamilies.get(i);
                int fid = userToFamily.getFid();
                Family family = familyRepository.findByFid(fid);
                families.add(family);
            }
            map.put("code",1);
            map.put("msg","获取家庭信息成功");
            map.put("data",families);
        } catch (Exception e) {
            map.put("code",0);
            map.put("msg","获取家庭信息失败");
            e.printStackTrace();
        } finally {
            return map;
        }
    }


    //@Transactional

    /**
     *创建家庭接口
     * @param familyName 家庭名称
     * @param request 携带用户sesssion信息
     * @return {code:0/1,msg:家庭创建失败/家庭创建成功}
     */
    @RequestMapping(value = "/createfamily", params={"familyName"},method = RequestMethod.POST)
    @ResponseBody
    public Object createFamily(String familyName,HttpServletRequest request) {
        System.out.println("进入创建家庭接口");
        Map map  = new HashMap<>();
        try {
            HttpSession session = request.getSession();
            int uid = (int)session.getAttribute("user");
            User user = userRepository.findByUserId(uid);

            Family family = new Family();
            family.setFamilyName(familyName);
            String uuid = getUUID();
            String fileName = uuid+".jpg";
            family.setQrcodeUrl("http://gavinwang.cn/"+fileName);
            Family family1= familyRepository.save(family);
            //生成家庭二维码
            int fid = family1.getFid();
            System.out.println("fid:=="+fid);
            QrCodeUtil qrCodeUtil = new QrCodeUtil();
            String url = "fid="+fid;
            String path = "D://testQrcode//";
            qrCodeUtil.createQrCode(url, path, fileName);
            //存储到七牛云
            UpLoadUtil upLoadUtil = new UpLoadUtil();
            upLoadUtil.setKey(fileName);
            upLoadUtil.setLocalFilePath("D://testQrcode//"+fileName);
            System.out.println("D://testQrcode//"+fileName);
            upLoadUtil.upload();

            UserToFamily userToFamily = new UserToFamily();
            userToFamily.setUserId(uid);
            userToFamily.setFid(fid);
            userToFamilyRepository.save(userToFamily);

            map.put("code",1);
            map.put("msg","家庭创建成功");

        } catch (Exception e) {
            map.put("code",0);
            map.put("msg","家庭创建失败");
            e.printStackTrace();
        } finally {
            return map;
        }
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     *加入家庭接口
     * @param fid 加入家庭
     * @param request 携带用户sesssion信息
     * @return {code:0/1/3,msg:加入家庭失败/加入家庭成功/要加入的家庭不存在}
     */
    @RequestMapping(value = "/joinfamily", params={"fid"},method = RequestMethod.POST)
    @ResponseBody
    public Object joinFamily(int fid,HttpServletRequest request) {
        System.out.println("进入加入家庭接口");
        Map map  = new HashMap<>();
        try {
            HttpSession session = request.getSession();
            int uid = (int)session.getAttribute("user");
            Family family = familyRepository.findByFid(fid);
            if(family == null){
                map.put("code",2);
                map.put("msg","要加入的家庭不存在");
            }else{
                UserToFamily userToFamily = new UserToFamily();
                userToFamily.setFid(fid);
                userToFamily.setUserId(uid);
                userToFamilyRepository.save(userToFamily);
                map.put("code",1);
                map.put("msg","加入家庭成功");
            }
        } catch (Exception e) {
            map.put("code",0);
            map.put("msg","加入家庭失败");
            e.printStackTrace();
        } finally {
            return map;
        }
    }
}
