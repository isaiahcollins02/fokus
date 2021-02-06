package com.isaiahvonrundstedt.fokus.features.archived.task

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.isaiahvonrundstedt.fokus.R
import com.isaiahvonrundstedt.fokus.components.extensions.android.getCompoundDrawableAtStart
import com.isaiahvonrundstedt.fokus.components.extensions.android.setCompoundDrawableAtStart
import com.isaiahvonrundstedt.fokus.components.extensions.android.setStrikeThroughEffect
import com.isaiahvonrundstedt.fokus.components.extensions.android.setTextColorFromResource
import com.isaiahvonrundstedt.fokus.databinding.LayoutItemArchivedTaskBinding
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BaseAdapter
import com.isaiahvonrundstedt.fokus.features.shared.abstracts.BaseEditor
import com.isaiahvonrundstedt.fokus.features.task.TaskAdapter
import com.isaiahvonrundstedt.fokus.features.task.TaskPackage

class ArchivedTaskAdapter
    : BaseAdapter<TaskPackage, ArchivedTaskAdapter.ArchivedTaskViewHolder>(TaskPackage.DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArchivedTaskAdapter.ArchivedTaskViewHolder {
        val binding = LayoutItemArchivedTaskBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return ArchivedTaskViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ArchivedTaskAdapter.ArchivedTaskViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class ArchivedTaskViewHolder(itemView: View): BaseViewHolder(itemView) {
    private val binding = LayoutItemArchivedTaskBinding.bind(itemView)

        override fun <T> onBind(t: T) {
            if (t is TaskPackage) {
                with(t.task) {
                    binding.root.transitionName = BaseEditor.TRANSITION_ELEMENT_ROOT + taskID

                    val textColorRes = if (isFinished)
                        R.color.color_secondary_text
                    else R.color.color_primary_text

                    binding.taskNameView.text = name
                    binding.taskNameView.setTextColorFromResource(textColorRes)
                    binding.taskNameView.setStrikeThroughEffect(isFinished)

                    if (hasDueDate())
                        binding.dueDateView.text = formatDueDate(binding.root.context)
                    else binding.dueDateView.isVisible = false
                }

                if (t.subject != null) {
                    with(binding.subjectView) {
                        text = t.subject?.code
                        setCompoundDrawableAtStart(t.subject?.tintDrawable(getCompoundDrawableAtStart()))
                    }
                } else binding.subjectView.isVisible = false
            }
        }
    }
}