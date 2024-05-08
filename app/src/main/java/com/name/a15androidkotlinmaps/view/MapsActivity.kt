package com.name.a15androidkotlinmaps.view

import PlaceDatabase
import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.room.Room

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import com.name.a15androidkotlinmaps.R
import com.name.a15androidkotlinmaps.databinding.ActivityMapsBinding
import com.name.a15androidkotlinmaps.model.Place
import com.name.a15androidkotlinmaps.roomdb.PlaceDao

class MapsActivity : AppCompatActivity(), OnMapReadyCallback , GoogleMap.OnMapLongClickListener{

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher : ActivityResultLauncher<String>
    private lateinit var sharedPreferences: SharedPreferences
    private var trackBoolean: Boolean?=null
    private var selectedLatitude: Double? = null
    private var selectedLongitude: Double? = null

    private lateinit var db: PlaceDatabase
    private lateinit var placeDao: PlaceDao






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        registerLauncher()

        sharedPreferences=this.getSharedPreferences("com.name.a15androidkotlinmaps", MODE_PRIVATE)
        trackBoolean=false

        selectedLatitude=0.0
        selectedLongitude=0.0

        db=Room.databaseBuilder(applicationContext,PlaceDatabase::class.java,"Places").build()
        placeDao=db.placeDao()

    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)


        /*
        //48.858518304726864, 2.294513484602791
        val eiffel=LatLng(48.858518304726864,2.294513484602791)
        mMap.addMarker(MarkerOptions().position(eiffel).title("Eiffel Tower"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(eiffel,13f))
         */

        locationManager=this.getSystemService(LOCATION_SERVICE) as LocationManager


        locationListener=object : LocationListener {
            override fun onLocationChanged(location: Location) {
                trackBoolean=sharedPreferences.getBoolean("trackBoolean",false)
                if (trackBoolean==false){
                    val userLocation=LatLng(location.latitude, location.longitude)
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation,13f))
                    sharedPreferences.edit().putBoolean("trackBoolean", true).apply()
                }


            }
            override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                super.onStatusChanged(provider, status, extras)
            }

        }

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)) {
                Snackbar.make(binding.root,"Permission needed for location", Snackbar.LENGTH_INDEFINITE).setAction("Giver Permission"){
                    //request permission
                    permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }.show()
            } else {
                //request permission
                permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            }

        } else {

            //permissioon granted
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
            val lastLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastLocation!=null){
                val lastUserLocation=LatLng(lastLocation.latitude,lastLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,13f))
            }
            mMap.isMyLocationEnabled=true

        }
    }

    private fun registerLauncher() {

        permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestPermission()){result ->
            if (result){
                if (ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                    //permission granted
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0f,locationListener)
                    val lastLocation=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (lastLocation!=null){
                        val lastUserLocation=LatLng(lastLocation.latitude,lastLocation.longitude)
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastUserLocation,13f))
                    }
                    mMap.isMyLocationEnabled=true
                }

            } else {
                //permission denied
                Toast.makeText(this@MapsActivity,"Permission needed ! ", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapLongClick(p0: LatLng) {

        mMap.clear()
        mMap.addMarker(MarkerOptions().position(p0))
        selectedLatitude=p0.latitude
        selectedLongitude=p0.longitude


    }

    fun save (view : View) {

        if (selectedLongitude!=null && selectedLatitude!=null){
            val place=Place(binding.placeText.text.toString(),selectedLatitude!!,selectedLongitude!!)
            placeDao.insert(place)


        } else {
            Toast.makeText(this, "Seçilen konum bulunamadı", Toast.LENGTH_SHORT).show()
        }



        /*
        if (selectedLatitude!= null) {
    // selectedLatitude değişkeni null değilse işlemleri gerçekleştir
} else {
    // selectedLatitude değişkeni null ise kullanıcıya bir uyarı göster
    Toast.makeText(context, "Seçilen konum bulunamadı", Toast.LENGTH_SHORT).show()
}
         */

        /*
        if (selectedLatitude!= null) {
    // selectedLatitude değişkeni null değilse işlemleri gerçekleştir
} else {
    // selectedLatitude değişkeni null ise loglama yap
    Log.e(TAG, "Seçilen konum null olduğu için işlem yapılamadı")
}

         */

        /*
        if (selectedLatitude != null) {
    // selectedLatitude değişkeni null değilse işlemleri gerçekleştir
} else {
    // selectedLatitude değişkeni null ise alternatif bir ekran aç
    val intent = Intent(context, AlternatifActivity::class.java)
    startActivity(intent)
}

         */

        //ELvis Operatoru ile
        /*
        val latitude = selectedLatitude ?: DEFAULT_LATITUDE
val longitude = selectedLongitude ?: DEFAULT_LONGITUDE
val place = Place(binding.placeText.text.toString(), latitude!!, longitude!!)

         */


    }


    fun delete (view : View) {



    }
}