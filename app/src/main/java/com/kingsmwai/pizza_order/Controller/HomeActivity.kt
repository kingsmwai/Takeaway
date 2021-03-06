package com.kingsmwai.pizza_order.Controller

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.kingsmwai.pizza_order.Adapter.MenuViewHolder
import com.kingsmwai.pizza_order.Interface.ItemClickListener
import com.kingsmwai.pizza_order.Model.Category
import com.kingsmwai.pizza_order.R
import com.kingsmwai.pizza_order.Utils.CATEGORY_EXTRA
import com.kingsmwai.pizza_order.Utils.Common
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var database: FirebaseDatabase
    lateinit var category: DatabaseReference
    lateinit var viewHolder: FirebaseRecyclerAdapter<Category, MenuViewHolder>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        toolbar.title = "Menu"
        setSupportActionBar(toolbar)

        //firebase init
        database = FirebaseDatabase.getInstance()
        category = database.getReference(CATEGORY_EXTRA)

        fab.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)


        //user name
        val view: View = nav_view.getHeaderView(0)
        view.userLogged.text = Common.currentUser!!.name


        val manager = LinearLayoutManager(this)
        recyclerview.layoutManager = manager
        recyclerview.setHasFixedSize(true)
        loadMenuItems()

    }

    private fun loadMenuItems() {
        val categoryQuery = category.orderByKey()
        val categoryOption = FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(categoryQuery, Category::class.java).build()

        viewHolder = object : FirebaseRecyclerAdapter<Category, MenuViewHolder>(categoryOption) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.menu_item, parent, false)

                val name = view.findViewById<TextView>(R.id.categoryName)
                val img = view.findViewById<ImageView>(R.id.categoryImage)
                return MenuViewHolder(view, img, name)
            }

            override fun onBindViewHolder(holder: MenuViewHolder, position: Int, model: Category) {
                holder.categoryName.text = model.name

                Picasso.get()
                        .load(model.image)
                        .placeholder(R.drawable.pizza_one)
                        .error(R.drawable.pizza_one)
                        .into(holder.categoryImg)

                val itemClickListener = object : ItemClickListener {
                    override fun onClick(view: View, position: Int, isLongClick: Boolean) {
                        val intent = Intent(this@HomeActivity, ProductListActivity::class.java)
                        intent.putExtra(CATEGORY_EXTRA, viewHolder.getRef(position).key)
                        startActivity(intent)
                    }
                }
                holder.setitemClickListener(itemClickListener)
            }
        }
        recyclerview.adapter = viewHolder
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_menu -> {
                // Handle the camera action
            }
            R.id.nav_cart -> {
                // Handle the camera action
            }
            R.id.nav_orders -> {
                // Handle the camera action
            }
            R.id.nav_log_out -> {
                // Handle the camera action
            }

        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStart() {
        super.onStart()
        viewHolder.startListening()
    }

    override fun onStop() {
        super.onStop()
        viewHolder.stopListening()
    }

    fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
        return Toast.makeText(context, this.toString(), duration).apply { show() }
    }


}
