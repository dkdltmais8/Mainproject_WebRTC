package com.metastudy.Common.Response;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApiModel("TimeResponse")
public class TimeResponse implements Comparable<TimeResponse>{

    long userpk;
    String username;
    long hour;
    long min;
    long sec;

    public static TimeResponse res(long diff,long userpk,String name)
    {
        TimeResponse time = new TimeResponse();
        diff = Math.abs(diff);

        time.setHour(diff/3600);
        time.setMin(diff % 3600 / 60);
        time.setSec(diff % 3600%60);

        time.setUserpk(userpk);
        time.setUsername(name);

        return time;
    }

    @Override
    public String toString() {
        return "TimeResponse{" +
                "userpk=" + userpk +
                ", username='" + username + '\'' +
                ", hour=" + hour +
                ", min=" + min +
                ", sec=" + sec +
                '}';
    }

    @Override
    public int compareTo(TimeResponse timeResponse) {
        if (this.hour > timeResponse.hour)
        {
            return 1;
        }
        else if(this.hour == timeResponse.hour)
        {
            if(this.min > timeResponse.min)
            {
                return 1;
            }
            else if(this.min == timeResponse.min)
            {
                if(this.sec >= timeResponse.sec)
                {
                    return 1;
                }
                else
                    return -1;
            }
            else
                return -1;
        }
        else
            return -1;
    }
}
