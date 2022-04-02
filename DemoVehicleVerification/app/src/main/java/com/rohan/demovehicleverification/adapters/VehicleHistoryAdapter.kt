package com.rohan.demovehicleverification.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.rohan.demovehicleverification.R
import com.rohan.demovehicleverification.data.db.VehicleInfo
import com.rohan.demovehicleverification.databinding.HistoryItemElementBinding
import com.rohan.demovehicleverification.other.Utility.millsToText


class VehicleHistoryAdapter(
    private val c: Context,
    private val iSelectItem: ISelectItem,
) : RecyclerView.Adapter<VehicleHistoryAdapter.VehicleInfoViewHolder>() {

    val diffCallback = object : DiffUtil.ItemCallback<VehicleInfo>() {

        override fun areItemsTheSame(oldItem: VehicleInfo, newItem: VehicleInfo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VehicleInfo, newItem: VehicleInfo): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<VehicleInfo>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleInfoViewHolder {
        val bindind = HistoryItemElementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return VehicleInfoViewHolder(bindind)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat", "ResourceType")
    override fun onBindViewHolder(holder: VehicleInfoViewHolder, position: Int) {
        val item = differ.currentList[position]
        with(holder) {

            //reg num

            binding.tvRegNumber.text = HtmlCompat.fromHtml(
                "<b>${c.getString(R.string.registrationNumber)}:</b>\t\t${item.registrationNumber}",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )

            //status
            val statusColor: String
            val statusText: String

            if (!item.found) {
                statusText = c.getString(R.string.not_found)
                statusColor = c.resources.getString(R.color.wrong)
                binding.holder.alpha = 0.5f
            } else {
                binding.holder.alpha = 1f

                if (item.approved) {
                    statusText = c.getString(R.string.approved)
                    statusColor = c.resources.getString(R.color.correct)
                } else {
                    statusText = c.getString(R.string.disproved)
                    statusColor = c.resources.getString(R.color.ok)
                }
            }


            binding.tvSatus.text = HtmlCompat.fromHtml(
                "<b>${c.getString(R.string.status)}:</b>\t\t <font color='${
                    statusColor.removeRange(
                        1,
                        3
                    )
                }'>${statusText} </font>",
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )



            //date
            val dateString = millsToText(item.createdDate)

            binding.tvDate.text =
                HtmlCompat.fromHtml(
                    "<b>${c.getString(R.string.verification_date)}:</b>\t\t$dateString",
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )

            binding.holder.setOnClickListener {
                if (item.found) {
                    iSelectItem.itemSelect(item.id ?: -1)
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class VehicleInfoViewHolder(val binding: HistoryItemElementBinding) :
        RecyclerView.ViewHolder(binding.root)

}
