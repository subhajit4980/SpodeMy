package com.example.spodemy.All_View.Task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.spodemy.Adapter.TodoAdapter
import com.example.spodemy.Application.HMApplicaton
import com.example.spodemy.ViewModel.TodoModel
import com.example.spodemy.ViewModel.ViewModelFactory
import com.example.spodemy.databinding.FragmentTaskListBinding

class TaskList : Fragment() {
    private var _binding: FragmentTaskListBinding?=null
    private val binding get() = _binding!!
    private lateinit var  todoAdapter:TodoAdapter
    private val TodoViewModel: TodoModel by viewModels{
        ViewModelFactory((requireActivity().application as HMApplicaton).repository)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding=FragmentTaskListBinding.inflate(inflater,container,false)
        val view= binding.root
        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.rev?.layoutManager =LinearLayoutManager(activity)
        todoAdapter=TodoAdapter(this@TaskList)
        binding!!.rev.adapter=todoAdapter
        TodoViewModel.allplans.observe(viewLifecycleOwner)
        {
            plans->
            plans.let {
                if(it.isNotEmpty())
                {
                    binding.noplan.visibility=View.GONE
                    binding!!.rev.visibility=View.VISIBLE
                    todoAdapter.planslist(it)
                }else{
                    binding.noplan.visibility=View.VISIBLE
                    binding.rev.visibility=View.GONE
                }
            }
        }

    }
}