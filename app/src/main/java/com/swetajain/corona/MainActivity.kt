package com.swetajain.corona

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.leo.simplearcloader.SimpleArcLoader
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    var tvCases: TextView? = null
    var tvRecovered: TextView? = null
//    var tvCritical: TextView? = null
    var critical:String = ""
    var tvActive: TextView? = null
  //  var tvTodayCases: TextView? = null
    var todayCases = ""
    var tvTotalDeaths: TextView? = null
 //   var tvTodayDeaths: TextView? = null
    var todayDeaths = ""
    var tvAffectedCountries: TextView? = null
    var affectedCountries = ""
    var simpleArcLoader: SimpleArcLoader? = null
  //  var scrollView: ScrollView? = null
    var pieChart: PieChart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        tvCases = findViewById(R.id.tvCases)
        tvRecovered = findViewById(R.id.tvRecovered)
     //   tvCritical = findViewById(R.id.tvCritical)
        tvActive = findViewById(R.id.tvActive)
     //   tvTodayCases = findViewById(R.id.tvTodayCases)
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths)
      //  tvTodayDeaths = findViewById(R.id.tvTodayDeaths)
       tvAffectedCountries = findViewById(R.id.tvAffectedCountries)
        simpleArcLoader = findViewById(R.id.loader)
   //     scrollView = findViewById(R.id.scrollStats)
        pieChart = findViewById(R.id.piechart)
        fetchData()
    }

    private fun fetchData() {
        val url = "https://corona.lmao.ninja/v2/all/"
        simpleArcLoader?.start()
        val request = StringRequest(Request.Method.GET, url,
            Response.Listener { response ->
                try {
                    val jsonObject = JSONObject(response!!)
                    tvCases!!.text = jsonObject.getString("cases")
                    tvRecovered!!.text = jsonObject.getString("recovered")
               //     tvCritical!!.text = jsonObject.getString("critical")
                    critical = jsonObject.getString("critical")
                    tvActive!!.text = jsonObject.getString("active")
               //     tvTodayCases!!.text = jsonObject.getString("todayCases")
                    todayCases = jsonObject.getString("todayCases")
                    tvTotalDeaths!!.text = jsonObject.getString("deaths")
                   // tvTodayDeaths!!.text = jsonObject.getString("todayDeaths")
                    todayDeaths = jsonObject.getString("todayDeaths")
                    tvAffectedCountries!!.text = jsonObject.getString("affectedCountries")
                  //  affectedCountries = jsonObject.getString("affectedCountries")
                    pieChart?.addPieSlice(
                        PieModel(
                            "Cases",
                            tvCases!!.text.toString().toInt().toFloat(),
                            Color.parseColor("#00BCD4")
                        )
                    )
                    pieChart?.addPieSlice(
                        PieModel(
                            "Active",
                            tvActive!!.text.toString().toInt().toFloat(),
                            Color.parseColor("#FFEB3B")
                        )
                    )
                    pieChart?.addPieSlice(
                        PieModel(
                            "Recovered",
                            tvRecovered!!.text.toString().toInt().toFloat(),
                            Color.parseColor("#0EF118")
                        )
                    )
                    pieChart?.addPieSlice(
                        PieModel(
                            "Deaths",
                            tvTotalDeaths!!.text.toString().toInt().toFloat(),
                            Color.parseColor("#FF0315")
                        )
                    )

                    pieChart?.startAnimation()
                    simpleArcLoader?.stop()
                    simpleArcLoader?.visibility = View.GONE
                  //  scrollView!!.visibility = View.VISIBLE
                } catch (e: JSONException) {
                    e.printStackTrace()
                    simpleArcLoader?.stop()
                    simpleArcLoader?.visibility = View.GONE
                 //   scrollView!!.visibility = View.VISIBLE
                }
            }, Response.ErrorListener { error ->
                simpleArcLoader?.stop()
                simpleArcLoader?.visibility = View.GONE
              //  scrollView!!.visibility = View.VISIBLE
                Toast.makeText(this@MainActivity, error.message, Toast.LENGTH_SHORT).show()
            })
        val requestQueue: RequestQueue = Volley.newRequestQueue(this)
        requestQueue.add(request)
    }

    fun goTrackCountries(view: View?) {
        startActivity(Intent(applicationContext, AffectedCountries::class.java))
    }
}