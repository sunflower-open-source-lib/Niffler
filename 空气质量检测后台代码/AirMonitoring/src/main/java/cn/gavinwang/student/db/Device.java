package cn.gavinwang.student.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "device")
public class Device {
    @Id
    @Column(name = "device_id")
    private String deviceId;

    @Column(name = "room_id")
    private int roomId;

}
