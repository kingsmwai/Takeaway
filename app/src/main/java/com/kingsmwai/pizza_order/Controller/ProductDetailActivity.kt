//package com.kingsmwai.pizza_order.Controller
//
//import android.content.Context
//import android.os.Bundle
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import com.google.firebase.database.*
//import com.kingsmwai.pizza_order.DB.Database
//import com.kingsmwai.pizza_order.Model.Order
//import com.kingsmwai.pizza_order.Model.Product
//import com.kingsmwai.pizza_order.Utils.PRODUCT_EXTRA
//import com.squareup.picasso.Picasso
//import kotlinx.android.synthetic.main.activity_product_detail.*
//
//class ProductDetailActivity : AppCompatActivity() {
//
//    lateinit var productId: String
//    lateinit var database: FirebaseDatabase
//    lateinit var productRef: DatabaseReference
//    lateinit var product: Product
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_product_detail)
//
//        //firebase init
//        database = FirebaseDatabase.getInstance()
//        productRef = database.getReference(PRODUCT_EXTRA)
//
//        productId = intent.getStringExtra(PRODUCT_EXTRA).toString()
//
//        loadDetailProduct(productId)
//        saveToCart()
//    }
//
//    private fun loadDetailProduct(productId: String) {
//        val valueEventListener = object : ValueEventListener {
//            override fun onCancelled(databaseError: DatabaseError) {
//
//            }
//
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//
//                product = dataSnapshot.getValue(Product::class.java)!!
//
//                Picasso.get()
//                        .load(product.image)
//                        .placeholder(R.mipmap.bg_home)
//                        .error(R.mipmap.bg_home)
//                        .into(product_img)
//
//                collapsing.title = product.name
//
//                product_name.text = product.name
//                product_price.text = product.price
//                product_description.text = product.description
//
//            }
//
//        }
//        productRef.child(productId).addValueEventListener(valueEventListener)
//    }
//
//    private fun saveToCart() {
//        btn_add_cart.setOnClickListener {
//            Database(this)
//                    .addToCart(
//                        Order(
//                            productId,
//                            product.name!!,
//                            btn_number.number,
//                            product.price!!,
//                            product.discount!!
//                    )
//                    )
//
//            "${product.name} added to cart".toast(this)
//        }
//    }
//
//    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
//        return Toast.makeText(context, this.toString(), duration).apply { show() }
//    }
//
//}
