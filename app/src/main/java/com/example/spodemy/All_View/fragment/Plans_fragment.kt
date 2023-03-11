package com.example.spodemy.All_View.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.spodemy.Adapter.ViewPagerAdapter
import com.example.spodemy.All_View.Task.AddTask
import com.example.spodemy.All_View.Task.TaskList
import com.example.spodemy.TodoDatabase.Todo
import com.example.spodemy.TodoDatabase.TodoDatabase
import com.example.spodemy.Repository.TodoRepository
import com.example.spodemy.ViewModel.TodoModel
import com.example.spodemy.ViewModel.ViewModelFactory
import com.example.spodemy.databinding.FragmentPlansFragmentBinding
import com.google.android.material.search.SearchView.Behavior
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_plans_fragment.*


class Plans_fragment : Fragment() {
 lateinit var todoModel:TodoModel
 private lateinit var todolist: ArrayList<Todo>
    @SuppressLint("FragmentLiveDataObserve")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding=FragmentPlansFragmentBinding.inflate(inflater,container,false)
        binding.c1.setOnClickListener{ binding.tv.text = "subhajit" }
        todolist= ArrayList<Todo>()
//        view model implementation
//        val dao=TodoDatabase.getDatabase(requireContext()).todoDao()
//        val repo= TodoRepository(dao)
//        todoModel=ViewModelProvider(this,ViewModelFactory(repo)).get(TodoModel::class.java)
//        todoModel.getTodo().observe(this, Observer { todo->
//            for (data in todo)
//            {
//                todolist.add(data)
//            }
//        })
        val tabLayout: TabLayout =binding.tabLayout
        val viewpager: ViewPager =binding.viewPager
        tabLayout.addTab(tabLayout.newTab().setText("Plans"))
        tabLayout.addTab(tabLayout.newTab().setText("Add Plans"))
        tabLayout.tabGravity= TabLayout.GRAVITY_FILL
        setupViewPager(viewpager)
        tabLayout.setupWithViewPager(viewpager)
        return binding.root
    }
    private fun setupViewPager(viewpager: ViewPager) {
        var adapter: ViewPagerAdapter =
            ViewPagerAdapter(childFragmentManager)
        adapter.addFragment(TaskList(), "Plans")
        adapter.addFragment(AddTask(), "Add Plans")
        viewpager.setAdapter(adapter)
    }
}