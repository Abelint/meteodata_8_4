import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class WeatherService {
    private val client = OkHttpClient()

    fun fetchWeatherData(callback: (List<String>, List<Double>) -> Unit) {
        val url = "https://api.open-meteo.com/v1/forecast?latitude=55.7522&longitude=37.6156&hourly=temperature_2m"

        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                response.body?.let {
                    val jsonData = it.string()
                    val jsonObject = JSONObject(jsonData)
                    val hourly = jsonObject.getJSONObject("hourly")
                    val timeArray = hourly.getJSONArray("time")
                    val temperatureArray = hourly.getJSONArray("temperature_2m")

                    // Преобразуем JSON-данные в списки
                    val times = mutableListOf<String>()
                    val temperatures = mutableListOf<Double>()

                    for (i in 0 until timeArray.length()) {
                        times.add(timeArray.getString(i))
                        temperatures.add(temperatureArray.getDouble(i))
                    }

                    callback(times, temperatures)
                }
            }
        })
    }
}
