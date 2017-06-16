package cn.itcast.weather;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private TextView select, select2, select_temp, select_wind,
			select_pm;
	private Map<String, String> map;
	private List<Map<String, String>> list;
	private String temp, weather, name, pm, wind;
	private ImageView icon;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化文本控件
		select = (TextView) findViewById(R.id.select);
		select2 = (TextView) findViewById(R.id.select2);
		select_temp = (TextView) findViewById(R.id.femp);
		select_wind = (TextView) findViewById(R.id.wd);
		select_pm = (TextView) findViewById(R.id.pn);
		icon = (ImageView) findViewById(R.id.tubiao);

		findViewById(R.id.ctsh).setOnClickListener(this);
		findViewById(R.id.ctbj).setOnClickListener(this);
		findViewById(R.id.ctjl).setOnClickListener(this);

		try {
			// 调用上边写好的解析方法,weather.xml就在类的目录下，使用类加载器进行加载
			// infos就是每个城市的天气信息集合，里边有我们所需要的所有数据。
			List<WeatherInfo> infos = WeatherService
					.getWeatherInfos(MainActivity.class.getClassLoader()
							.getResourceAsStream("weather.xml"));
			// 循环读取infos中的每一条数据
			list = new ArrayList<Map<String, String>>();
			for (WeatherInfo info : infos) {
				map = new HashMap<String, String>();
				map.put("femp", info.getTemp());
				map.put("weather", info.getWeather());
				map.put("name", info.getName());
				map.put("pn", info.getPm());
				map.put("wd", info.getWind());
				list.add(map);
			}
			// 显示天气信息到文本控件中
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "解析信息失败", 0).show();
		}

		getMap(1, R.drawable.sun);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ctsh:
			getMap(0, R.drawable.cloud_sun);
			break;
		case R.id.ctbj:
			getMap(1, R.drawable.sun);
			break;
		case R.id.ctjl:
			getMap(2, R.drawable.clouds);
			break;
		}
	}

	private void getMap(int number, int iconNumber) {
		Map<String, String> bjMap = list.get(number);
		temp = bjMap.get("femp");
		weather = bjMap.get("weather");
		name = bjMap.get("name");
		pm = bjMap.get("pn");
		wind = bjMap.get("wd");
		select.setText(name);
		select2.setText(weather);
		select_temp.setText("" + temp);
		select_wind.setText("风力  : " + wind);
		select_pm.setText("pm: " + pm);
		icon.setImageResource(iconNumber);
	}
}
