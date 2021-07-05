package com.example.entities.player.util.attributes;

import com.example.db.interfaces.player.AttributeInterface;
import com.example.entities.player.util.PlayerConstants;

import java.util.HashMap;
import java.util.Map;


// TODO - Attribute names are capitalised with underscores
public class PlayerAttributes extends HashMap<Attribute, Integer> {
    private static final Map<String, Attribute> attributeMap = AttributeInterface.getAttributeMap();

    public PlayerAttributes() {
        for (Attribute attribute : AttributeInterface.getAttributes()) {
            put(attribute, PlayerConstants.MIN_ATTRIBUTE_VALUE);
        }
    }

    public PlayerAttributes(Map<Attribute, Integer> attributes) {
        for (Map.Entry<Attribute, Integer> entry : attributes.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
    }

    public PlayerAttributes copy() {
        PlayerAttributes playerAttributes = new PlayerAttributes();
        for (Map.Entry<Attribute, Integer> entry : entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
        return playerAttributes;
    }

    public Map<Attribute, Integer> toMap() {
        return this;
    }

    public Integer getAttribute(String name) {
        return get(AttributeInterface.getAttributeMap().get(name));
    }

    public int getPostHook() {
        return get(attributeMap.get(""));
    }

    public void setPostHook(int postHook) {
        put(attributeMap.get(""), postHook);
    }

    public int getPostFadeaway() {
        return get(attributeMap.get(""));
    }

    public void setPostFadeaway(int postFadeaway) {
        put(attributeMap.get(""), postFadeaway);
    }

    public int getPostMoves() {
        return get(attributeMap.get(""));
    }

    public void setPostMoves(int postMoves) {
        put(attributeMap.get(""), postMoves);
    }

    public int getStandingLayup() {
        return get(attributeMap.get(""));
    }

    public void setStandingLayup(int standingLayup) {
        put(attributeMap.get(""), standingLayup);
    }

    public int getDrivingLayup() {
        return get(attributeMap.get(""));
    }

    public void setDrivingLayup(int drivingLayup) {
        put(attributeMap.get(""), drivingLayup);
    }

    public int getStandingDunk() {
        return get(attributeMap.get(""));
    }

    public void setStandingDunk(int standingDunk) {
        put(attributeMap.get(""), standingDunk);
    }

    public int getDrivingDunk() {
        return get(attributeMap.get(""));
    }

    public void setDrivingDunk(int drivingDunk) {
        put(attributeMap.get(""), drivingDunk);
    }

    public int getContactDunk() {
        return get(attributeMap.get(""));
    }

    public void setContactDunk(int contactDunk) {
        put(attributeMap.get(""), contactDunk);
    }

    public int getShotClose() {
        return get(attributeMap.get(""));
    }

    public void setShotClose(int shotClose) {
        put(attributeMap.get(""), shotClose);
    }

    public int getFreeThrow() {
        return get(attributeMap.get(""));
    }

    public void setFreeThrow(int freeThrow) {
        put(attributeMap.get(""), freeThrow);
    }

    public int getMidRangeOpenStanding() {
        return get(attributeMap.get(""));
    }

    public void setMidRangeOpenStanding(int midRangeOpenStanding) {
        put(attributeMap.get(""), midRangeOpenStanding);
    }

    public int getMidRangeOffDribbling() {
        return get(attributeMap.get(""));
    }

    public void setMidRangeOffDribble(int midRangeOffDribble) {
        put(attributeMap.get(""), midRangeOffDribble);
    }

    public int getMidRangeContested() {
        return get(attributeMap.get(""));
    }

    public void setMidRangeContested(int midRangeContested) {
        put(attributeMap.get(""), midRangeContested);
    }

    public int getThreePointOpenStanding() {
        return get(attributeMap.get(""));
    }

    public void setThreePointOpenStanding(int threePointOpenStanding) {
        put(attributeMap.get(""), threePointOpenStanding);
    }

    public int getThreePointOffDribble() {
        return get(attributeMap.get(""));
    }

    public void setThreePointOffDribble(int threePointOffDribble) {
        put(attributeMap.get(""), threePointOffDribble);
    }

    public int getThreePointContested() {
        return get(attributeMap.get(""));
    }

    public void setThreePointContested(int threePointContested) {
        put(attributeMap.get(""), threePointContested);
    }

    public int getPassingAccuracy() {
        return get(attributeMap.get(""));
    }

    public void setPassingAccuracy(int passingAccuracy) {
        put(attributeMap.get(""), passingAccuracy);
    }

    public int getPassingVision() {
        return get(attributeMap.get(""));
    }

    public void setPassingVision(int passingVision) {
        put(attributeMap.get(""), passingVision);
    }

    public int getBallHandling() {
        return get(attributeMap.get(""));
    }

    public void setBallHandling(int ballHandling) {
        put(attributeMap.get(""), ballHandling);
    }

    public int getSpeedWithBall() {
        return get(attributeMap.get(""));
    }

    public void setSpeedWithBall(int speedWithBall) {
        put(attributeMap.get(""), speedWithBall);
    }

    public int getPassingIQ() {
        return get(attributeMap.get(""));
    }

    public void setPassingIQ(int passingIQ) {
        put(attributeMap.get(""), passingIQ);
    }

    public int getOffensiveRebound() {
        return get(attributeMap.get(""));
    }

    public void setOffensiveRebound(int offensiveRebound) {
        put(attributeMap.get(""), offensiveRebound);
    }

    public int getDefensiveRebound() {
        return get(attributeMap.get(""));
    }

    public void setDefensiveRebound(int defensiveRebound) {
        put(attributeMap.get(""), defensiveRebound);
    }

    public int getBoxOut() {
        return get(attributeMap.get(""));
    }

    public void setBoxOut(int boxOut) {
        put(attributeMap.get(""), boxOut);
    }

    public int getLateralQuickness() {
        return get(attributeMap.get(""));
    }

    public void setLateralQuickness(int lateralQuickness) {
        put(attributeMap.get(""), lateralQuickness);
    }

    public int getDefensiveAwareness() {
        return get(attributeMap.get(""));
    }

    public void setDefensiveAwareness(int defensiveAwareness) {
        put(attributeMap.get(""), defensiveAwareness);
    }

    public int getPlayRecognition() {
        return get(attributeMap.get(""));
    }

    public void setPlayRecognition(int playRecognition) {
        put(attributeMap.get(""), playRecognition);
    }

    public int getSteal() {
        return get(attributeMap.get(""));
    }

    public void setSteal(int steal) {
        put(attributeMap.get(""), steal);
    }

    public int getBlock() {
        return get(attributeMap.get(""));
    }

    public void setBlock(int block) {
        put(attributeMap.get(""), block);
    }

    public int getPassPerception() {
        return get(attributeMap.get(""));
    }

    public void setPassPerception(int passPerception) {
        put(attributeMap.get(""), passPerception);
    }

    public int getShotContest() {
        return get(attributeMap.get(""));
    }

    public void setShotContest(int shotContest) {
        put(attributeMap.get(""), shotContest);
    }

    public int getInteriorDefense() {
        return get(attributeMap.get(""));
    }

    public void setInteriorDefense(int interiorDefense) {
        put(attributeMap.get(""), interiorDefense);
    }

    public int getPerimeterDefense() {
        return get(attributeMap.get(""));
    }

    public void setPerimeterDefense(int perimeterDefense) {
        put(attributeMap.get(""), perimeterDefense);
    }

    public int getSpeed() {
        return get(attributeMap.get(""));
    }

    public void setSpeed(int speed) {
        put(attributeMap.get(""), speed);
    }

    public int getStamina() {
        return get(attributeMap.get(""));
    }

    public void setStamina(int stamina) {
        put(attributeMap.get(""), stamina);
    }

    public int getStrength() {
        return get(attributeMap.get(""));
    }

    public void setStrength(int strength) {
        put(attributeMap.get(""), strength);
    }

    public int getVertical() {
        return get(attributeMap.get(""));
    }

    public void setVertical(int vertical) {
        put(attributeMap.get(""), vertical);
    }

    public int getDurability() {
        return get(attributeMap.get(""));
    }

    public void setDurability(int durability) {
        put(attributeMap.get(""), durability);
    }

    public int getAgility() {
        return get(attributeMap.get(""));
    }

    public void setAgility(int agility) {
        put(attributeMap.get(""), agility);
    }

    public int getAcceleration() {
        return get(attributeMap.get(""));
    }

    public void setAcceleration(int acceleration) {
        put(attributeMap.get(""), acceleration);
    }

    public int getShotIQ() {
        return get(attributeMap.get(""));
    }

    public void setShotIQ(int shotIQ) {
        put(attributeMap.get(""), shotIQ);
    }

    public int getBasketballIQ() {
        return get(attributeMap.get(""));
    }

    public void setBasketballIQ(int basketballIQ) {
        put(attributeMap.get(""), basketballIQ);
    }

    public int getOffensiveConsistency() {
        return get(attributeMap.get(""));
    }

    public void setOffensiveConsistency(int offensiveConsistency) {
        put(attributeMap.get(""), offensiveConsistency);
    }

    public int getDefensiveConsistency() {
        return get(attributeMap.get(""));
    }

    public void setDefensiveConsistency(int defensiveConsistency) {
        put(attributeMap.get(""), defensiveConsistency);
    }

    public int getConfidence() {
        return get(attributeMap.get(""));
    }

    public void setConfidence(int confidence) {
        put(attributeMap.get(""), confidence);
    }

    public int getReactionTime() {
        return get(attributeMap.get(""));
    }

    public void setReactionTime(int reactionTime) {
        put(attributeMap.get(""), reactionTime);
    }

    public int getHands() {
        return get(attributeMap.get(""));
    }

    public void setHands(int hands) {
        put(attributeMap.get(""), hands);
    }

    public int getDrawFoul() {
        return get(attributeMap.get(""));
    }

    public void setDrawFoul(int drawFoul) {
        put(attributeMap.get(""), drawFoul);
    }
}
