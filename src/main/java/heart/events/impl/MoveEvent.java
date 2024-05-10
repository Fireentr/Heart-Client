package heart.events.impl;

import heart.events.Cancellable;

public class MoveEvent extends Cancellable {
    public Direction direction;
    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getLastYaw() {
        return lastYaw;
    }

    public void setLastYaw(float lastYaw) {
        this.lastYaw = lastYaw;
    }

    public float getLastPitch() {
        return lastPitch;
    }

    public void setLastPitch(float lastPitch) {
        this.lastPitch = lastPitch;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getLastX() {
        return lastX;
    }

    public void setLastX(double lastX) {
        this.lastX = lastX;
    }

    public double getLastY() {
        return lastY;
    }

    public void setLastY(double lastY) {
        this.lastY = lastY;
    }

    public double getLastZ() {
        return lastZ;
    }

    public void setLastZ(double lastZ) {
        this.lastZ = lastZ;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public void setOnGround(boolean onGround) {
        this.onGround = onGround;
    }


    float yaw, pitch, lastYaw, lastPitch;
    double x, y , z, minY, lastX, lastY, lastZ;
    boolean onGround;

    public MoveEvent(double x, double y, double z, double lastX, double lastY, double lastZ, double minY, float yaw, float pitch, float lastYaw, float lastPitch, boolean ground, Direction direction) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.lastX = lastX;
        this.lastY = lastY;
        this.lastZ = lastZ;
        this.minY = minY;
        this.yaw = yaw;
        this.pitch = pitch;
        this.lastYaw = lastYaw;
        this.lastPitch = lastPitch;
        this.onGround = ground;
        this.direction = direction;
    }

}