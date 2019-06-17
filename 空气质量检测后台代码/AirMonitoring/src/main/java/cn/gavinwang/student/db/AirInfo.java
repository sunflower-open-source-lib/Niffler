package cn.gavinwang.student.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "airinfo")
public class AirInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "device_id")
    private String deviceId;

    //@Column(name="time",columnDefinition="timestamp default current_timestamp")
    @Column(name="time")
    private Date date;

    @Column(name = "temperature")
    private double temperature;

    @Column(name = "humidity")
    private double humidity;

    @Column(name = "pm25")
    private double pm25;

    @Column(name = "tvoc")
    private double tvoc;
}
