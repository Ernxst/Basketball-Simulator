package com.example.db.interfaces.player;

import com.example.db.interfaces.AbstractInterface;
import com.example.entities.player.util.attributes.Attribute;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttributeInterface extends AbstractInterface {
    private static final String selectAttributeNamesStmt = "SELECT NAME FROM ATTRIBUTE";
    private static final String selectAttributesStmt = "SELECT * FROM ATTRIBUTE";

    private static final List<Attribute> attributes;
    private static final Map<String, Attribute> attributeMap;

    static {
        attributeMap = getAllAttributes();
        if (attributeMap.size() > 0)
            attributes = (List<Attribute>) attributeMap.values();
        else
            attributes = new ArrayList<>();
    }

    private static List<String> getAllAttributeNames() {
        List<String> names = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAttributeNamesStmt)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                names.add(resultSet.getString(1));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return names;
    }

    private static Map<String, Attribute> getAllAttributes() {
        Map<String, Attribute> attributes = new HashMap<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectAttributesStmt)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString(1);
                String description = resultSet.getString(2);
                String categoryString = resultSet.getString(3);
                Attribute.AttributeCategory category = Attribute.AttributeCategory.getAttributeCategoryFromString(categoryString);
                attributes.put(name, new Attribute(name, description, category));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return attributes;
    }

    public static List<Attribute> getAttributes() {
        return attributes;
    }

    public static Map<String, Attribute> getAttributeMap() {
        return attributeMap;
    }
}
