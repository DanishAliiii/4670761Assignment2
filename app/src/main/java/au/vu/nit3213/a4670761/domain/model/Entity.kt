package au.vu.nit3213.a4670761.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Entity(
    val property1: String? = null,
    val property2: String? = null,
    val description: String? = null
) : Parcelable
