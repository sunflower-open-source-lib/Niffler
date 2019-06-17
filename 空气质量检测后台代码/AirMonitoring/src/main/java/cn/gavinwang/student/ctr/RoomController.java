package cn.gavinwang.student.ctr;

import cn.gavinwang.student.db.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
public class RoomController {
    private final FamilyRepository familyRepository;

    private final UserRepository userRepository;

    private final UserToFamilyRepository userToFamilyRepository;

    private final RoomRepository roomRepository;

    private final DeviceRepository deviceRepository;

    @Autowired
    public RoomController(FamilyRepository familyRepository, UserRepository userRepository, UserToFamilyRepository userToFamilyRepository,RoomRepository roomRepository,DeviceRepository deviceRepository) {
        this.familyRepository = familyRepository;
        this.userRepository = userRepository;
        this.userToFamilyRepository = userToFamilyRepository;
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
    }

    /**
     * 创建房间接口(并绑定设备）
     * @param roomName 家庭名称
     * @param deviceId 设备号
     * @param fid 家庭id
     * @param request 携带用户session信息
     * @return {code:0/1,msg:新建房间失败/新建房间成功}
     */
    @RequestMapping(value = "/addroom", params={"roomName","deviceId","fid"},method = RequestMethod.POST)
    @ResponseBody
    public Object addRoom(String roomName,String deviceId,int fid,HttpServletRequest request) {
        System.out.println("进入新建房间接口");
        Map map  = new HashMap<>();
        try {
            Room room = new Room();
            room.setRoomName(roomName);
            room.setFid(fid);
            Room room0 = roomRepository.save(room);
            Device device = new Device();
            device.setDeviceId(deviceId);
            device.setRoomId(room0.getRoomId());
            deviceRepository.save(device);
            map.put("code",1);
            map.put("msg","新建房间成功");
        } catch (Exception e) {
            map.put("code",0);
            map.put("msg","新建房间失败");
            e.printStackTrace();
        } finally {
            return map;
        }
    }
}
