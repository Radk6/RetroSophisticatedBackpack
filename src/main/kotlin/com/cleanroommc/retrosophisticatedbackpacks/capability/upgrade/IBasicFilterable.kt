package com.cleanroommc.retrosophisticatedbackpacks.capability.upgrade

import com.cleanroommc.retrosophisticatedbackpacks.inventory.ExposedItemStackHandler
import net.minecraft.item.ItemStack

interface IBasicFilterable {
    companion object {
        const val FILTER_ITEMS_TAG = "FilterItems"
        const val FILTER_TYPE_TAG = "FilterType"
    }

    val filterItems: ExposedItemStackHandler
    var filterType: FilterType

    fun checkFilter(stack: ItemStack): Boolean = when (filterType) {
        FilterType.WHITELIST -> filterItems.inventory.any { ItemStack.areItemsEqualIgnoreDurability(it, stack) }
        FilterType.BLACKLIST -> filterItems.inventory.none { ItemStack.areItemsEqualIgnoreDurability(it, stack) }
    }

    enum class FilterType {
        WHITELIST,
        BLACKLIST;
    }

    object Impl : IBasicFilterable {
        override val filterItems: ExposedItemStackHandler
            get() = ExposedItemStackHandler(0)
        override var filterType: FilterType
            get() = FilterType.WHITELIST
            set(_) {}

        override fun checkFilter(itemStack: ItemStack): Boolean =
            false
    }
}