package com.android.nimbus.model

import com.google.gson.annotations.SerializedName

data class TopStoriesModel(
    @SerializedName("status") var status: String? = null,
    @SerializedName("copyright") var copyright: String? = null,
    @SerializedName("section") var section: String? = null,
    @SerializedName("last_updated") var lastUpdated: String? = null,
    @SerializedName("num_results") var numResults: Int? = null,
    @SerializedName("results") var results: ArrayList<Results> = arrayListOf()
)

data class Results(
    @SerializedName("section") var section: String? = null,
    @SerializedName("subsection") var subsection: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("abstract") var abstract: String? = null,
    @SerializedName("url") var url: String? = null,
    @SerializedName("uri") var uri: String? = null,
    @SerializedName("byline") var byline: String? = null,
    @SerializedName("item_type") var itemType: String? = null,
    @SerializedName("updated_date") var updatedDate: String? = null,
    @SerializedName("created_date") var createdDate: String? = null,
    @SerializedName("published_date") var publishedDate: String? = null,
    @SerializedName("material_type_facet") var materialTypeFacet: String? = null,
    @SerializedName("kicker") var kicker: String? = null,
    @SerializedName("des_facet") var desFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("org_facet") var orgFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("per_facet") var perFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("geo_facet") var geoFacet: ArrayList<String> = arrayListOf(),
    @SerializedName("multimedia") var multimedia: ArrayList<Multimedia> = arrayListOf(),
    @SerializedName("short_url") var shortUrl: String? = null
) {
    fun toFeedsModel(): FeedsModel {
        return FeedsModel(
            title = "Top Stories",
            headline = title ?: "",
            summary = abstract ?: "",
            author = byline ?: "",
            publishedAt = publishedDate ?: "",
            url = url ?: ""
        )
    }
}

data class Multimedia(
    @SerializedName("url") var url: String? = null,
    @SerializedName("format") var format: String? = null,
    @SerializedName("height") var height: Int? = null,
    @SerializedName("width") var width: Int? = null,
    @SerializedName("type") var type: String? = null,
    @SerializedName("subtype") var subtype: String? = null,
    @SerializedName("caption") var caption: String? = null,
    @SerializedName("copyright") var copyright: String? = null
)