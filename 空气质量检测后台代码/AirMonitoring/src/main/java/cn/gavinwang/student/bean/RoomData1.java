package cn.gavinwang.student.bean;

import cn.gavinwang.student.db.AirInfo;
import cn.gavinwang.student.db.Room;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class RoomData1 {
    private Room room;
    private List<AirInfo> airInfos;
}
