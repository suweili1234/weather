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
	private TextView selectsu, selectsu2, select_temp, select_wind,
			select_pm;
	private Map<String, String> map;
	private List<Map<String, String>> list;
	private String temp, weather, name, pm, wind;
	private ImageView icon;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化文本控件
		selectsu = (TextView) findViewById(R.id.selectsu);
		selectsu2 = (TextView) findViewById(R.id.selectsu2);
		select_temp = (TextView) findViewById(R.id.femp1);
		select_wind = (TextView) findViewById(R.id.wd1);
		select_pm = (TextView) findViewById(R.id.pn1);
		icon = (ImageView) findViewById(R.id.tb);

		findViewById(R.id.wenzhou).setOnClickListener(this);
		findViewById(R.id.loudi).setOnClickListener(this);
		findViewById(R.id.cq).setOnClickListener(this);

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
				map.put("femp1", info.getTemp());
				map.put("weather", info.getWeather());
				map.put("name", info.getName());
				map.put("pn1", info.getPm());
				map.put("wd1", info.getWind());
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
		case R.id.wenzhou:
			getMap(0, R.drawable.cloud_sun);
			break;
		case R.id.loudi:
			getMap(1, R.drawable.sun);
			break;
		case R.id.cq:
			getMap(2, R.drawable.clouds);
			break;
		}
	}

	private void getMap(int number, int iconNumber) {
		Map<String, String> bjMap = list.get(number);
		temp = bjMap.get("femp1");
		weather = bjMap.get("weather");
		name = bjMap.get("name");
		pm = bjMap.get("pn1");
		wind = bjMap.get("wd1");
		selectsu.setText(name);
		selectsu2.setText(weather);
		select_temp.setText("" + temp);
		select_wind.setText("风力  : " + wind);
		select_pm.setText("pm: " + pm);
		icon.setImageResource(iconNumber);
	}
}
