package com.circle.circlemod.utils.direction;

public class DirectionPlayerAndEntityModel {
    private boolean isLeft;
    private double angle;

    public DirectionPlayerAndEntityModel(boolean isLeft, double angle) {
        this.isLeft = isLeft;
        this.angle = angle;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}
