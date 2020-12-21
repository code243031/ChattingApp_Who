package server;

import java.time.LocalDate;
import java.time.LocalTime;

public class ServerTime { // 서버측의 시간을 나타내는 클래스

	static LocalDate nowDate;
	static LocalTime nowTime;
	Thread time_refresh;
	
	public ServerTime() {
		nowDate = LocalDate.now();
		nowTime = LocalTime.now();
		time_refresh = new Thread() {
			@Override
			public void run() {
				while(true) {
					nowDate = LocalDate.now();
					nowTime = LocalTime.now();
				}
			}
		};
	}

	public static String getDate() {
		String res = nowDate.toString();
		return res;
	}
	
	public static String getTime() {
		String res = "T"+nowTime.getHour()+":"+nowTime.getMinute()+":"+nowTime.getSecond();
		return res;
	}
}
