package com.example.appsale200220233.presentation.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target
import com.example.appsale200220233.R
import com.example.appsale200220233.common.AppConstant
import com.example.appsale200220233.common.utils.StringUtils
import com.example.appsale200220233.data.model.Product


/**
 * Created by pphat on 6/26/2023.
 */
class ProductAdapter(
    private var listProducts: MutableList<Product> = mutableListOf(),
    private var context: Context? = null,
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    private var onItemClickProduct: OnItemClickProduct? = null

    fun updateListProduct(data: List<Product>?) {
        if (listProducts.size > 0) {
            listProducts.clear()
        }
        listProducts.addAll(data!!)
        notifyDataSetChanged()
    }

    fun getListProducts(): List<Product> {
        return listProducts
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.layout_product_item, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(context, listProducts[position])
    }

    override fun getItemCount(): Int {
        return listProducts.size
    }

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var buttonAdd = view.findViewById<LinearLayout>(R.id.button_add)
        private var textViewName = view.findViewById<TextView>(R.id.textViewName)
        private var textViewAddress = view.findViewById<TextView>(R.id.textViewAddress)
        private var textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        private var imageView = view.findViewById<ImageView>(R.id.imageView)
        init {
            buttonAdd.setOnClickListener {
                if (onItemClickProduct != null) {
                    onItemClickProduct!!.onClick(adapterPosition)
                }
            }
        }

        fun bind(context: Context?, product: Product) {
            textViewName.text = product.name
            textViewAddress.text = product.address
            textViewPrice.setText(
                String.format(
                    "%s VND",
                    StringUtils.formatCurrency(product.price.toInt())
                )
            )
            val context = context ?: return
            Glide.with(context)
                .load(AppConstant.BASE_URL + product.image)
                .placeholder(R.drawable.ic_logo)
                .into(imageView)
        }
    }

    fun setOnItemClickFood(onItemClickProduct: OnItemClickProduct?) {
        this.onItemClickProduct = onItemClickProduct
    }

    interface OnItemClickProduct {
        fun onClick(position: Int)
    }
}
