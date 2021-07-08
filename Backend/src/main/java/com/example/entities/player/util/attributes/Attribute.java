package com.example.entities.player.util.attributes;

import org.apache.commons.text.WordUtils;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Attribute {
    private final String name;
    private final String formattedName;
    private final String description;
    private final AttributeCategory category;

    public Attribute(String name, String description, AttributeCategory category) {
        this.name = name;
        this.formattedName = WordUtils.capitalizeFully(name.replaceAll("_", " ").toLowerCase(Locale.ROOT));
        this.description = description;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public String getFormattedName() {
        return formattedName;
    }

    public String getDescription() {
        return description;
    }

    public AttributeCategory getCategory() {
        return category;
    }

    public enum AttributeCategory {
        POST_SCORING("Post Scoring"), INSIDE_SCORING("Inside Scoring"),
        OUTSIDE_SCORING("Outside Scoring"), PLAYMAKING("Playmaking"),
        REBOUNDING("Rebounding"), DEFENDING("Defending"),
        PHYSICAL("Physical"), MENTAL("Mental");

        public static final AttributeCategory[] categories = values();
        private static final Map<String, AttributeCategory> categoryMap;

        static {
            categoryMap = new HashMap<>();
            for (AttributeCategory category : categories) {
                categoryMap.put(category.getLabel(), category);
            }
        }

        private final String label;

        AttributeCategory(String label) {
            this.label = label;
        }

        public static AttributeCategory getAttributeCategoryFromString(String name) {
            return categoryMap.getOrDefault(name, null);
        }

        public String getLabel() {
            return label;
        }
    }
}
