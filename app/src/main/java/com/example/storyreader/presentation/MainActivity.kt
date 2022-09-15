package com.example.storyreader.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.storyreader.R
import com.example.storyreader.data.localdatabase.AppDatabase
import com.example.storyreader.data.localdatabase.models.CategoryDbModel
import com.example.storyreader.databinding.ActivityMainBinding
import com.example.storyreader.presentation.fragments.CategoryListFragment
import com.example.storyreader.presentation.fragments.FavouriteStoryListFragment
import com.example.storyreader.presentation.fragments.StoryListFragment

class MainActivity: AppCompatActivity(), ActionBarActivity {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var toggle: ActionBarDrawerToggle

    //private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = AppDatabase.getInstance(this)
        val dao = db.storyDao()
        dao.addCategory(
            CategoryDbModel(
                1,
                "Картинг"
            )
        )
        val res = dao.getStoriesOfCategory(1)
        println(res.value)
        setContentView(binding.root)
        binding.navView.setCheckedItem(R.id.story_item)
        setupNavMenu()
        //setupSlideMenu()
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /*
    private fun setupSlideMenu() {
        val drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this,
            drawerLayout,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView = binding.navView

        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.storyListFragment -> {
                    val fragment = StoryListFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.categoryListFragment -> {
                    val fragment = CategoryListFragment.newInstance()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit()
                }
                R.id.favouriteStoryListFragment -> {
                    val fragment = FavouriteStoryListFragment.newInstance()
                    /*
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.main_container, fragment)
                        .addToBackStack(null)
                        .commit()

                     */
                    startFragment(fragment)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

     */

    /*
    fun setupActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
        val drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

     */

    private fun setupNavMenu() {
        val navView = binding.navView
        val drawerLayout = binding.drawerLayout
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.story_item -> {
                    val fragment = StoryListFragment.newInstance()
                    startFragment(fragment)
                    navView.setCheckedItem(R.id.story_item)
                }
                R.id.categoryListFragment -> {
                    val fragment = CategoryListFragment.newInstance()
                    startFragment(fragment)
                    navView.setCheckedItem(R.id.categoryListFragment)
                }
                R.id.favouriteStoryListFragment -> {
                    val fragment = FavouriteStoryListFragment.newInstance()
                    startFragment(fragment)
                    navView.setCheckedItem(R.id.favouriteStoryListFragment)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    /*
    private fun setupSlideMenu() {
        setSupportActionBar(binding.toolbar.root)
        val navView = binding.navView
        val drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this,
            drawerLayout,
            binding.toolbar.root,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.story_item -> {
                    val fragment = StoryListFragment.newInstance()
                    startFragment(fragment)
                    navView.setCheckedItem(R.id.story_item)
                }
                R.id.categoryListFragment -> {
                    val fragment = CategoryListFragment.newInstance()
                    startFragment(fragment)
                    navView.setCheckedItem(R.id.categoryListFragment)
                }
                R.id.favouriteStoryListFragment -> {
                    val fragment = FavouriteStoryListFragment.newInstance()
                    startFragment(fragment)
                    navView.setCheckedItem(R.id.favouriteStoryListFragment)
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

     */

    private fun startFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.main_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setupActionBar(toolbar: Toolbar, fragmentCode: Int) {
        //setSupportActionBar(toolbar)
        val drawerLayout = binding.drawerLayout
        toggle = ActionBarDrawerToggle(this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        with(binding) {
            when(fragmentCode){
                ActionBarActivity.STORY_LIST_FRAGMENT_CODE -> {
                    navView.setCheckedItem(R.id.story_item)
                }
                ActionBarActivity.CATEGORY_LIST_FRAGMENT_CODE -> {
                    navView.setCheckedItem(R.id.categoryListFragment)
                }
                ActionBarActivity.FAVOURITE_LIST_FRAGMENT_CODE -> {
                    navView.setCheckedItem(R.id.favouriteStoryListFragment)
                }
            }
        }

    }

    override fun setupActionBarWithoutMenu(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }
}