package com.android.nimbus.model

import com.google.gson.annotations.SerializedName

data class TopStoriesModel(
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
    @SerializedName("multimedia") var topStoriesMultimediaModel: ArrayList<TopStoriesMultimediaModel> = arrayListOf(),
    @SerializedName("short_url") var shortUrl: String? = null
)