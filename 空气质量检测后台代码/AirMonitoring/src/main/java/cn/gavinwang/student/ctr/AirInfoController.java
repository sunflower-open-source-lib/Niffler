package cn.gavinwang.student.ctr;

import cn.gavinwang.student.bean.Data;
import cn.gavinwang.student.bean.RoomData;
import cn.gavinwang.student.bean.RoomData1;
import cn.gavinwang.student.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping
public class AirInfoController {
    private final FamilyRepository familyRepository;

    private final UserRepository userRepository;

    private final UserToFamilyRepository userToFamilyRepository;

    private final RoomRepository roomRepository;

    private final DeviceRepository deviceRepository;

    private final AirInfoRepository airInfoRepository;

    @Autowired
    public AirInfoController(FamilyRepository familyRepository, UserRepository userRepository,UserToFamilyRepository userToFamilyRepository,RoomRepository roomRepository,DeviceRepository deviceRepository,AirInfoRepository airInfoRepository) {
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
        this.userToFamilyRepository = userToFamilyRepository;
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
        this.airInfoRepository = airInfoRepository;
    }

    /**
     *传输数据接口
     * @param deviceId 设备号
     * @param temperature 温度
     * @param humidity 湿度
     * @param pm25 pm2.5
     * @param tvoc tvoc
     * @return {code:0/1,msg:数据上传失败/数据上传成功}
     */
    @RequestMapping(value = "/postData",params={"deviceId","temperature","humidity","pm25","tvoc"},method = RequestMethod.POST)
    @ResponseBody
    public Object postData(String deviceId,Double temperature,Double humidity,Double pm25,Double tvoc){
        Map<String,Object> map = new HashMap<>();
        try {
            AirInfo airInfo = new AirInfo();
            airInfo.setDeviceId(deviceId);
            airInfo.setTemperature(temperature);
            airInfo.setHumidity(humidity);
            airInfo.setPm25(pm25);
            airInfo.setTvoc(tvoc);
            //airInfo.setDate(new Date());
            airInfoRepository.save(airInfo);
            map.put("code",1);
            map.put("message","数据上传成功");
        } catch (Exception e) {
            map.put("code",0);
            map.put("message","数据上传失败");
            e.printStackTrace();
        }finally{
            return map;
        }
    }

    /*@RequestMapping(value = "/getallairinfo", params={},method = RequestMethod.POST)
    @ResponseBody
    public Object getMyFamily(HttpServletRequest request) {
        System.out.println("进入首页信息接口");
        Map map  = new HashMap<>();
        try {
            List<Data> datas = new ArrayList<Data>();
            List<RoomData> roomDatas = new ArrayList<RoomData>();
            HttpSession session = request.getSession();
            int uid = (int)session.getAttribute("user");
            User user = userRepository.findByUserId(uid);
            List<UserToFamily> userToFamilies = userToFamilyRepository.findAllByUserId(uid);
            for(int i = 0; i < userToFamilies.size(); i++){
                Data data = new Data();
                UserToFamily userToFamily = userToFamilies.get(i);
                int fid = userToFamily.getFid();
                data.setFid(fid);
                List<Room> rooms = roomRepository.findAllByFid(fid);
                for(int j = 0; j < rooms.size(); j++){
                    RoomData roomData = new RoomData();
                    Room room = rooms.get(j);
                    roomData.setRoom(room);
                    int roomId = room.getRoomId();
                    Device device = deviceRepository.findByRoomId(roomId);
                    String deviceId = device.getDeviceId();
                    AirInfo airInfo = airInfoRepository.findByDeviceId(deviceId);
                    roomData.setAirInfo(airInfo);
                    roomDatas.add(roomData);
                }
                data.setRoomData(roomDatas);
                datas.add(data);
            }
            map.put("code",0);
            map.put("msg","获取首页信息成功");
            map.put("data",datas);
        } catch (Exception e) {
            map.put("code",0);
            map.put("msg","获取数据失败");
            e.printStackTrace();
        } finally {
            return map;
        }
    }*/

    /**
     *获取指定家庭数据信息
     * @param fid 家庭id
     * @param request 携带用户sesssion信息
     * @return {code:1/0,msg:获取数据成功/获取数据失败,data:数据信息/-} data格式:[{"room":{},{"airInfos":[{...},{...}]}},{...}]
     */
    //获取指定家庭数据数据信息
    @RequestMapping(value = "/getairinfo", params={"fid"},method = RequestMethod.POST)
    @ResponseBody
    public Object getAirInfo(int fid,HttpServletRequest request) {
        System.out.println("进入获取指定家庭信息接口");
        Map map  = new HashMap<>();
        try {
            HttpSession session = request.getSession();
            int uid = (int)session.getAttribute("user");
            List<RoomData1> roomDatas1 = new ArrayList<>();
            List<Room> rooms = roomRepository.findAllByFid(fid);
            for(int i = 0; i < rooms.size(); i++){
                RoomData1 roomData1 = new RoomData1();
                Room room = rooms.get(i);
                roomData1.setRoom(room);
                int roomId = room.getRoomId();
                Device device = deviceRepository.findByRoomId(roomId);
                String deviceId = device.getDeviceId();
                List<AirInfo> airInfos = airInfoRepository.findAllByDeviceId(deviceId);
                roomData1.setAirInfos(airInfos);
                roomDatas1.add(roomData1);
            }
            map.put("code",1);
            map.put("msg","获取数据成功");
            map.put("data",roomDatas1);
        } catch (Exception e) {
            map.put("code",0);
            map.put("msg","获取数据失败");
        } finally {
            return map;
        }
    }
}
