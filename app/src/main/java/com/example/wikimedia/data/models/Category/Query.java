package com.example.wikimedia.data.models.Category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Query {

        @SerializedName("allcategories")
        @Expose
        private List<Allcategory> allcategories;

        /**
         * No args constructor for use in serialization
         *
         */
        public Query() {
        }

        /**
         *
         * @param allcategories
         */
        public Query(List<Allcategory> allcategories) {
            super();
            this.allcategories = allcategories;
        }

        public List<Allcategory> getAllcategories() {
            return allcategories;
        }

        public void setAllcategories(List<Allcategory> allcategories) {
            this.allcategories = allcategories;
        }
}
