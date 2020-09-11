package com.example.android.todokotlin.fragments.list.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.todokotlin.data.model.ToDoData
import com.example.android.todokotlin.databinding.RowLayoutBinding

class ToDoListAdapter : RecyclerView.Adapter<ToDoListAdapter.MyViewHolder>() {


    // tododata is the class that will populate the recyclerview list
    var dataList = emptyList<ToDoData>()

    class MyViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoData: ToDoData) {
            binding.toDoData = toDoData
            // from doc executePendingBindings ->
            // Evaluates the pending bindings, updating any Views that have expressions
            // bound to modified variables.
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // R.layout.row_layout is the layout that will be populate in the recyclerview list - the method below is
        // for without binding, since we use binding we return the MyViewHolder from the companion object fun above
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
//        return MyViewHolder(view)
        return MyViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }



    // to access each of the item layout
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentToDoTask = dataList[position]
        holder.bind(currentToDoTask)
        // this is before we bind the row layout to MyViewHolder
//        holder.itemView.itemTitleTextView.text = dataList[position].title
//        holder.itemView.itemDescriptionextView.text = dataList[position].description
//        // onclick listener to update fragment
//        holder.itemView.setOnClickListener{
//            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
//            holder.itemView.findNavController().navigate(action)
//        }
//
//        when (dataList[position].priority) {
//            Priority.DAILY_TASK -> (holder.itemView.imageCirclePriority.background as GradientDrawable)
//                .setColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.yellow, null))
//
//            Priority.LEISURE_TASK -> (holder.itemView.imageCirclePriority.background as GradientDrawable)
//                .setColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.green, null))
//
//            Priority.WORK_TASK -> (holder.itemView.imageCirclePriority.background as GradientDrawable)
//                .setColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.red, null))
//
//            Priority.ERR_TASK -> (holder.itemView.imageCirclePriority.background as GradientDrawable)
//                .setColor(ResourcesCompat.getColor(holder.itemView.resources, R.color.darkGrey, null))
//        }
    }

    fun setData(updatedToDoDataList: List<ToDoData>) {
        val toDoDiffUtil = ToDoDiffUtil(dataList,updatedToDoDataList)
        val toDoDIffUtilResult = DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList = updatedToDoDataList
        toDoDIffUtilResult.dispatchUpdatesTo(this)
        // notifyDataSetChanged() - instead of this we use DiffUtil to increase the performance
    }
}
