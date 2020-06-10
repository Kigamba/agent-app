package com.stavros.demo.android.form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.stavros.demo.android.R
import io.reactivex.rxjava3.core.Observable
import timber.log.Timber

class LandlordListActivity : AppCompatActivity() {

    var landlordList : ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landlord_list)

        setTitle(getString(R.string.landlords_list))

        landlordList = findViewById(R.id.lv_landlordList)

        val observableLandlords = fetchLandlords();

        observableLandlords.toList()
                .subscribe { listOfLandlords ->
                    Timber.i("Inside the subscription")
                    Timber.i("List of landlords %d", listOfLandlords.size)
                    landlordList?.adapter = object: ArrayAdapter<String>(this, R.layout.landlord_list_item, arrayOfNulls<String>(listOfLandlords.size)) {
                        override fun getView(position: Int, convertView1: View?, parent: ViewGroup): View {
                            var convertView: View? = convertView1
                            Timber.i("inside getView()")
                            if (convertView == null) {
                                Timber.i("inside getView() ----> inflating layout")
                                convertView = LayoutInflater.from(this@LandlordListActivity).inflate(R.layout.landlord_list_item, null)
                            }

                            val fName = convertView?.findViewById<TextView>(R.id.textView)
                            val lName = convertView?.findViewById<TextView>(R.id.textView2)
                            val email = convertView?.findViewById<TextView>(R.id.textView3)
                            val gender = convertView?.findViewById<TextView>(R.id.textView4)
                            val idNo = convertView?.findViewById<TextView>(R.id.textView5)
                            val phone = convertView?.findViewById<TextView>(R.id.textView6)

                            val landlord = listOfLandlords[position]
                            fName?.setText(landlord.get("first_name") as String?)
                            lName?.setText(landlord.get("last_name") as String?)
                            email?.setText(landlord.get("email") as String?)
                            //gender?.setText(landlord.get("gender") as String?)
                            idNo?.setText(landlord.get("id_no") as String?)
                            phone?.setText(landlord.get("phone_number") as String?)

                            return convertView!!
                        }
                    }
                }
/*
        landlordList?.adapter = object: ArrayAdapter<String>(this, R.layout.landlord_list_item, ArrayList<String>(landlords.size)) {
            override fun getView(position: Int, convertView1: View?, parent: ViewGroup): View {
                var convertView: View? = convertView1
                if (convertView == null) {
                    convertView = LayoutInflater.from(this@LandlordListActivity).inflate(R.layout.landlord_list_item, null)
                }

                val fName = convertView?.findViewById<TextView>(R.id.textView)
                val lName = convertView?.findViewById<TextView>(R.id.textView2)
                val email = convertView?.findViewById<TextView>(R.id.textView3)
                val gender = convertView?.findViewById<TextView>(R.id.textView4)
                val idNo = convertView?.findViewById<TextView>(R.id.textView5)
                val phone = convertView?.findViewById<TextView>(R.id.textView6)

                val landlord = landlords[position]
                fName?.setText(landlord.get("firstName") as String)
                lName?.setText(landlord.get("lastName") as String)
                email?.setText(landlord.get("email") as String)
                gender?.setText(landlord.get("gender") as String)
                idNo?.setText(landlord.get("id_number") as String)
                phone?.setText(landlord.get("phone") as String)

                return super.getView(position, convertView, parent)
            }
        }*/
    }

    fun fetchLandlords() : Observable<HashMap<String, Any>> {
        return Observable.create<HashMap<String, Any>> { emitter ->
            FirebaseDatabase.getInstance().getReference("Users").child("Landlords").addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(p0: DataSnapshot) {
                    p0.children.forEach {
                        //landlords.add(it.getValue() as HashMap<String, Any>)
                        if (it.getValue() is HashMap<*, *>) {
                            Timber.i("The object is a HashMap")
                            emitter.onNext(it.getValue() as HashMap<String, Any>)
                        } else {
                            Timber.i("The object is a HashMap")
                        }
                    }

                    emitter.onComplete()
                }
            });
        }
        /*

        val landlords = ArrayList<HashMap<String, Any>>()
        var landlordRef = FirebaseDatabase.getInstance().getReference("Users").child("Landlords").addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
            }

            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    landlords.add(it.getValue() as HashMap<String, Any>)
                }

            }
        });

        return landlords*/
    }

    override fun onResume() {
        super.onResume()
        fetchLandlords()
    }
}
